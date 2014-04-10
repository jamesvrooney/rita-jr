package com.dealchecker.model;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import com.fasterxml.jackson.databind.ObjectMapper

class SearchJsonTests {
	
	private static ObjectMapper mapper = new ObjectMapper()

	@Test
	void flightSearchShouldDeserializeFine() {
		
		def params = [
			type:"RT-FLT",
			departure:"LON",
			departureId:14344,
			destination:"PAR",
			destinationId:14504,
			departureDate:"2014/03/03",
			returnDate:"2014/03/10",
			adults:2,
			children:0,
			infants:0,
			ticketType:"R",
			cabinClass:"E"
		]
		
		def searchJson = """
		{
		    "type": "${params.type}",
		    "departure": "${params.departure}",
		    "departureId": "${params.departureId}",
		    "destination": "${params.destination}",
		    "destinationId": "${params.destinationId}",
		    "departureDate": "${params.departureDate}",
		    "returnDate": "${params.returnDate}",
		    "adults": ${params.adults},
		    "children": ${params.children},
		    "infants": ${params.infants},
		    "ticketType": "${params.ticketType}",
		    "cabinClass": "${params.cabinClass}"
		}
		"""
		
		// when
		Search search = mapper.readValue(searchJson, Search)
		
		// then
		assertEquals(params.type, search.type)
		assertEquals(params.departure, search.departure)
		assertEquals(params.departureId, search.departureId)
		assertEquals(params.destination, search.destination)
		assertEquals(params.destinationId, search.destinationId)
		assertEquals(params.departureDate, search.departureDate)
		assertEquals(params.returnDate, search.returnDate)
		assertEquals(params.adults, search.adults)
		assertEquals(params.children, search.children)
		assertEquals(params.infants, search.infants)
		assertEquals(params.ticketType, search.ticketType)
		assertEquals(params.cabinClass, search.cabinClass)
		
		assertNull(search.noRooms)
		assertNull(search.starRating)
	}
	
	@Test
	void holidaySearchShouldDeserializeFine() {
		
		def params = [
			type:"HDD",
			departure:"LHR",
			departureId:14330,
			destination:"Paris",
			destinationId:3804,
			departureDate:"2014/03/03",
			returnDate:"2014/03/10",
			adults:2,
			children:0,
			infants:0,
			starRating:4,
			boardType:"AI",
			dateRange:"1-3"
		]
		
		def searchJson = """
		{
		    "type": "${params.type}",
		    "departure": "${params.departure}",
		    "departureId": "${params.departureId}",
		    "destination": "${params.destination}",
		    "destinationId": "${params.destinationId}",
		    "departureDate": "${params.departureDate}",
		    "returnDate": "${params.returnDate}",
		    "adults": ${params.adults},
		    "children": ${params.children},
		    "infants": ${params.infants},
		    "starRating": "${params.starRating}",
		    "boardType": "${params.boardType}",
			"dateRange": "${params.dateRange}"
		}
		"""
		
		// when
		Search search = mapper.readValue(searchJson, Search)
		
		// then
		assertEquals(params.type, search.type)
		assertEquals(params.departure, search.departure)
		assertEquals(params.departureId, search.departureId)
		assertEquals(params.destination, search.destination)
		assertEquals(params.destinationId, search.destinationId)
		assertEquals(params.departureDate, search.departureDate)
		assertEquals(params.returnDate, search.returnDate)
		assertEquals(params.adults, search.adults)
		assertEquals(params.children, search.children)
		assertEquals(params.infants, search.infants)
		assertEquals(params.starRating, search.starRating)
		assertEquals(params.boardType, search.boardType)
		assertEquals(params.dateRange, search.dateRange)

		assertNull(search.ticketType)
		assertNull(search.cabinClass)
		
	}

}
