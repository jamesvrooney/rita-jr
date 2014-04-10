package com.dealchecker.handler.ebookers


import groovy.util.slurpersupport.GPathResult

import java.text.ParseException

import org.apache.http.Consts;
import org.apache.http.HttpHost
import org.apache.http.client.fluent.Executor
import org.apache.http.client.fluent.Request
import org.apache.http.entity.ContentType
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.dealchecker.handler.ResponseHandlingException
import com.dealchecker.handler.SimpleSupplierHandler
import com.dealchecker.handler.service.AirportResolutionService
import com.dealchecker.handler.service.MissingLocationException
import com.dealchecker.model.BoardBasis
import com.dealchecker.model.Deal
import com.dealchecker.model.Location
import com.dealchecker.model.Search
import com.dealchecker.model.SupplierInfo
import com.dealchecker.util.DateUtil
import com.google.common.base.Strings

class EbookersHolidayHandler extends SimpleSupplierHandler {
	
	final AirportResolutionService airportResolutionService
	
	static final Logger log = LoggerFactory.getLogger(EbookersHolidayHandler)
	
	EbookersHolidayHandler(AirportResolutionService airportResolutionService) {
		this.airportResolutionService = airportResolutionService
	}

	@Override
	String requestResults(Search search, SupplierInfo supplierInfo) {
		def xmlRequest = EbookersHolidays.generateRequestXml(search, supplierInfo)
		
		Executor executorForClient = setUpClientWithAuthentication()
		
		def apiResult = executorForClient.execute(Request.Post(EbookersHolidays.baseUrl)
			.useExpectContinue()
			.bodyString(xmlRequest, ContentType.APPLICATION_XML))
			.returnContent()
			.asString()

		apiResult
	}

	@Override
	List<Deal> handleResponse(String apiResult, Search search, SupplierInfo supplierInfo) {
		if(Strings.isNullOrEmpty(apiResult)) {
			throw new ResponseHandlingException("Ebookers apiResult is null or empty")
		} 
		if(apiResult.contains("<ErrorResponse")) {
			throw new ResponseHandlingException(getEbookersErrorMessage(apiResult))
		}
		
		def dynamicPackagingResponse = new XmlSlurper().parseText(apiResult)
		
		def numberOfNights = dynamicPackagingResponse.PackagingInfo.HotelSolutions.@numberOfNights.text()
		
		def flightsIndex = getFlights(dynamicPackagingResponse.PackagingInfo.AirSolutions.AirSolution)
		def hotelsIndex = getHotels(dynamicPackagingResponse.PackagingInfo.HotelSolutions.HotelSolution, numberOfNights)
		
		def deals = []
		def packageSolutions = dynamicPackagingResponse.PackagingInfo.PackageSolutions.PackageSolution

		packageSolutions.each { solution ->
			def flightPackage = flightsIndex.get(solution.@airRefId.text())
			def hotelPackage = hotelsIndex.get(solution.@hotelRefId.text())
			
			Deal deal = createDeal(solution, search, flightPackage, hotelPackage)
			
			if(deal != null) {
				deals.push(deal)
			} else {
				log.error("Could not create a deal for package [id: {}]", solution.@pkgId.text())
			}
		}
		
		deals
	}
	
	private Deal createDeal(GPathResult solution, Search search, Map<String, Object> flightPackage, Map<String, Object> hotelPackage) {
		if(flightPackage == null || hotelPackage == null) {
			return null
		}
		
		Date departureDate = null
		Date returnDate = null

		Map<String, ?> firstOutboundLeg = flightPackage.outbound.first()
		Map<String, ?> lastInboundLeg = flightPackage.inbound.last()
		
		Deal.Builder dealTemplate = EbookersHolidays.newDealTemplate()		
		
		dealTemplate.withDepartureDate(firstOutboundLeg.departureDate)
			.withReturnDate(lastInboundLeg.returnDate)
			.withDeparture(search.departure)
			.withDepartureId(search.departureId)
			.withDestination(search.destination)
			.withDestinationId(search.destinationId)
			.withPrice(new BigDecimal(solution.RateInformation.CostPerPerson.text()))
			.withCurrency(solution.RateInformation.CostPerPerson.@currency.text())
			.withDeeplink(getDeeplink(solution))
		
		dealTemplate.withAdditionalAttribute("flightInfo", flightPackage)
		dealTemplate.withAdditionalAttribute("hotelInfo", hotelPackage)
		
		Deal deal = dealTemplate.build()
		
		deal
	}
	
	
	private Map<String, Map<String, Object>> getFlights(GPathResult airSolutionNodes) {
		def flightsIndex = [:]

		airSolutionNodes.each { solution ->

			def refId = solution.@airRefId.text()
			
			def outboundLegs = solution.AirSlices.Slice[0].Legs.Leg
			def inboundLegs = solution.AirSlices.Slice[1].Legs.Leg
			
			def outboundInfo = []
			def inboundInfo = []
			
			try {
				outboundLegs.each { leg -> outboundInfo.add(getLegInfo(leg)) }
				inboundLegs.each { leg -> inboundInfo.add(getLegInfo(leg)) }
				
				def flightDealInfo = [
					"outbound":outboundInfo,
					"inbound":inboundInfo
				]
				
				flightsIndex.put(refId, flightDealInfo)
			} catch(MissingLocationException ex) {
				// FIXME should send us an email with the missing location
				log.error(ex.getMessage())
			} catch(ParseException ex) {
				log.error(ex.getMessage())
			}
		}

		flightsIndex
	}
	
	private Map<String, String> getLegInfo(GPathResult leg) throws MissingLocationException, ParseException {
		
		// normalize airports
		Location departureAirport = null
		Location returnAirport = null

		departureAirport = airportResolutionService.getByCode(leg.Departure.Airport.Code.text())
		returnAirport = airportResolutionService.getByCode(leg.Arrival.Airport.Code.text())
		
		// normalize dates
		def pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS"
			
		Date legDepartureDate = DateUtil.parse(leg.Departure.Time.text(), pattern)
		Date legReturnDate = DateUtil.parse(leg.Arrival.Time.text(), pattern)
		
		def legInfo = [
			"airlineName":leg.MarketedBy.text(),
			"airlineCode":leg.MarketedBy.@code.text(),
			"departureDate":legDepartureDate,
			"departureAirportName":departureAirport.displayName,
			"departureAirportCode":departureAirport.code,
			"returnDate":legReturnDate,
			"returnAirportName":returnAirport.displayName,
			"returnAirportCode":returnAirport.code
		]

		legInfo
	}
	
	private Map<String, Map<String, Object>> getHotels(GPathResult hotelSolutionNodes, String numberOfNights) {
		def hotelsIndex = [:]
		
		hotelSolutionNodes.each { solution ->
			def refId = solution.@hotelRefId.text()
			def description = solution.Content.Description.text()
			
			List<String> imageUrls = solution.Content.Media.collect { it.text() }
			List<String> address = [
				solution.Address.Street1.text(), 
				solution.Address.City.text(), 
				solution.Address.PostalCode.text()
			]
			
			def hotelDealInfo = [
				"name":solution.@name.text(),
				"address":address.join(", "),
				"starRating":solution.@starRating.text(),
				"description":description,
				"thumbnailImage":getThumbnailImage(solution.Content.Media),
				"images":imageUrls,
				"lat":solution.Geocode.Latitude.text(),
				"lng":solution.Geocode.Longitude.text(),
				"boardBasis":BoardBasis.UNKNOWN.getDisplayName(),
				"numberOfNights":numberOfNights
			]
			
			hotelsIndex.put(refId, hotelDealInfo)
		}
		
		hotelsIndex
	}	
	
	private String getThumbnailImage(GPathResult mediaNodes) {
		def imageUrl = ""
		
		// always try to fetch the thumbnail
		def thumbnail = mediaNodes.find { it.@type.text() == "thumbnail" }
		
		if(thumbnail == null) {
			def anyImage = mediaNodes.find {it.@type.text() != "thumbnail" }
			imageUrl = anyImage.text()
		} else {
			imageUrl = thumbnail.text()
		}
		
		imageUrl
	}
	
	private Executor setUpClientWithAuthentication() {
		Executor executorForClient = Executor.newInstance()
		.auth(new HttpHost("ws.orbitzworldwide.com"), EbookersHolidays.baseHttpUserName, EbookersHolidays.baseHttpPassword)
		
		executorForClient
	}
	
	private String getEbookersErrorMessage(String xml) {
		def errorResponse = new XmlSlurper().parseText(xml)
		
		def errorCode = errorResponse.Message.@errorCode.text()
		def errorMessage = errorResponse.Message.text()
		
		"Ebookers request error [code: $errorCode]: $errorMessage"
	}
	
	
	private String getDeeplink(GPathResult solution) {
		EbookersHolidays.trackingUrl + solution.DeepLink.text() + EbookersHolidays.gcId
	}
}
