package com.dealchecker.handler.ebookers;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import com.dealchecker.handler.ResponseHandlingException
import com.dealchecker.handler.service.AirportResolutionService
import com.dealchecker.handler.service.impl.AirportResolutionServices
import com.dealchecker.model.Deal
import com.dealchecker.model.Location
import com.dealchecker.model.Search
import com.dealchecker.model.SupplierInfo

class EbookersHolidayHandlerResponseTest {
	
	private EbookersHolidayHandler handler 
	
	@Before
	void setUp() {
		AirportResolutionService dummyAirportService = AirportResolutionServices.newFromMap([
			"LHR":new Location.Builder().withCode("LHR").withDisplayName("London Heathrow Airport").build(),
			"CDG":new Location.Builder().withCode("CDG").withDisplayName("Charles de Gaulle Airport").build()
		])
		handler = new EbookersHolidayHandler(dummyAirportService)
	}

	@Test(expected=ResponseHandlingException)
	void shouldThrowExceptionIfEbookersGivesAnError() {
		// given
		String errorXml = EbookersHolidayHandlerResponseTest.getResourceAsStream("ebookers-invalid-response.xml").getText()
		
		// when
		handler.handleResponse(errorXml, new Search(), new SupplierInfo(EbookersHolidays.supplierId, ""))
		
		// then
		fail("Should have thrown ResponseHandlingException")
	}

	@Test
	void shouldConvertCorrectNumberOfDealsGivenCorrectRequest() {
		// given
		def xml = EbookersHolidayHandlerResponseTest.getResourceAsStream("ebooker-response-LHR-Paris.xml").getText()
		def search = new Search.Builder()
			.withDeparture("LHR")
			.withDepartureId(14330)
			.withDestination("Paris")
			.withDestinationId(3804)
			.withDepartureDate("2014-03-12")
			.withReturnDate("2014-04-03")
			.build()
			
		def supplierInfo = new SupplierInfo(EbookersHolidays.supplierId, "CDG")
		
		// when
		List<Deal> deals = handler.handleResponse(xml, search, supplierInfo)
		
		// then
		assertEquals(3, deals.size())
		deals.each { deal ->
			assertEquals(EbookersHolidays.supplierId, deal.supplierId)
			assertEquals(EbookersHolidays.supplierName, deal.supplierName)
			assertEquals("LHR", deal.departure)
			assertEquals(14330, deal.departureId)
			assertEquals("Paris", deal.destination)
			assertEquals(3804, deal.destinationId)
			assertNotNull(deal.price)
			assertNotNull(deal.departureDate)
			assertNotNull(deal.returnDate)
			assertNotNull(deal.deeplink)
			
			println deal
		}
	}
}
