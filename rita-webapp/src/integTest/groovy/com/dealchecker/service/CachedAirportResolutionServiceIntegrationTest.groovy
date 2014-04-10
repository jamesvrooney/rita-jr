package com.dealchecker.service;

import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import com.dealchecker.config.AppConfig
import com.dealchecker.config.HandlerConfig
import com.dealchecker.handler.service.AirportResolutionService
import com.dealchecker.handler.service.MissingLocationException
import com.dealchecker.model.Location

@RunWith(SpringJUnit4ClassRunner)
@SpringApplicationConfiguration(classes = [AppConfig, HandlerConfig])
class CachedAirportResolutionServiceIntegrationTest {
	
	@Autowired
	private AirportResolutionService airportResolutionService;

	@Test
	public void searchServiceShouldFetchCorrectAirport() {
		
		// given
		def code = "LON"
		
		// when
		Location location = airportResolutionService.getByCode(code);
		
		// then
		assertNotNull(location)
		assertEquals(code, location.code)
	}

	@Test(expected=MissingLocationException)
	void searchServiceShouldErrorOnUnknownAirport() {
		
		// given
		def code = "QWERTY"
		
		// when
		Location location = airportResolutionService.getByCode(code);
		
		// then
		fail("Should have thrown MissingLocationException")
	}
}
