package com.dealchecker.handler.service.impl;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import com.dealchecker.handler.service.AirportResolutionService
import com.dealchecker.handler.service.MissingLocationException
import com.dealchecker.model.Location

class InMemoryAiportResolutionServiceTests {

	private AirportResolutionService ars

	@Before
	void setUp() throws Exception {
		ars = new InMemoryAiportResolutionServiceImpl([
			"LON" : new Location.Builder().withName("London").build()
		])
	}

	@Test
	void checkForCorrectReturnedLocation() {
		Location returnedLocation

		try{
			returnedLocation = ars.getByCode("LON")
		} catch (MissingLocationException e ) {
			fail("Should not throw an exception")
		}

		assertEquals("Could not find the correct airport", "London", returnedLocation.name )
	}

	@Test(expected=MissingLocationException)
	void checkForExceptionForMissingAirport() {
		final def RUBBISH_AIRPORT = "rubbish"
		ars.getByCode(RUBBISH_AIRPORT)
		fail("This should have thrown a MissingLocationException!")
	}

	@Test(expected=UnsupportedOperationException)
	void checkForReshExpection() {
		ars.refresh()
		fail("This should have thrown a UnsupportedOperationException!")
	}
}
