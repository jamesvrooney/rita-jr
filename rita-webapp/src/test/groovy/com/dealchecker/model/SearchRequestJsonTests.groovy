package com.dealchecker.model;

import static org.junit.Assert.*

import org.junit.Test

import com.fasterxml.jackson.databind.ObjectMapper

class SearchRequestJsonTests {
	
	static ObjectMapper mapper = new ObjectMapper();

	@Test
	void testHolidaySearchDeserialization() {
		def json = """{"search":{"departure":"LON","departureId":"14344","destination":"Paris","destinationId":"3804","departureDate":"2014/03/18","returnDate":"2014/03/25","adults":"2","children":"0","infants":"0","boardType":"ANY","starRating":"3"},"searchToken":"123","supplierInfo":[{"supplierId":"2576","supplierKey":"PAR"}]}"""
		
		SearchRequest sr = mapper.readValue(json, SearchRequest)
		
		assertNotNull(sr)
	}

}
