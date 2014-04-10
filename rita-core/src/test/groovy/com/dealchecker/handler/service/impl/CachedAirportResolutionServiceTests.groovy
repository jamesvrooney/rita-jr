package com.dealchecker.handler.service.impl;

import static org.junit.Assert.*
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

import com.dealchecker.dao.LocationDao
import com.dealchecker.handler.service.AirportResolutionService
import com.dealchecker.handler.service.MissingLocationException
import com.dealchecker.model.Location
import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder

@RunWith(MockitoJUnitRunner)
class CachedAirportResolutionServiceTests {
	
	private AirportResolutionService airportResolutionService;
	private Cache<String,Location> cache;

	@Mock
	private LocationDao mockLocationDao;


	@Before
	public void setUp() throws Exception {
		cache = CacheBuilder.newBuilder().maximumSize(10).build()
		
		airportResolutionService = new CachedAirportResolutionService(mockLocationDao, cache)
	}

	@Test(expected=MissingLocationException)
	void shouldThrowMissingLocationExceptionIfAirportDoesNotExist() {
		// given
		def missingLocationCode = "XXX"
		when(mockLocationDao.getLocationByCode(missingLocationCode)).thenReturn(null)
		
		// when
		airportResolutionService.getByCode(missingLocationCode)
		
		// then
		fail("Should have thrown MissingLocationException")
	}
	
	@Test
	void shouldRetrieveFromDaoIfLocationNotInCache() {
		// given
		def someLocationCode = "LON"
		when(mockLocationDao.getLocationByCode(someLocationCode)).thenReturn(new Location.Builder().withCode(someLocationCode).build())
		
		// when
		Location retrieved = airportResolutionService.getByCode(someLocationCode)
		
		// then
		verify(mockLocationDao).getLocationByCode(someLocationCode)
		assertEquals(someLocationCode, retrieved.code)
	}

	@Test
	void shouldRetrieveFromCacheIfAlreadyCalculated() {
		// given
		def someLocationCode = "LON"
		when(mockLocationDao.getLocationByCode(someLocationCode)).thenReturn(new Location.Builder().withCode(someLocationCode).build())
		
		// when
		airportResolutionService.getByCode(someLocationCode)
		Location retrieved = airportResolutionService.getByCode(someLocationCode)
		
		// then
		verify(mockLocationDao).getLocationByCode(someLocationCode) // NOTE: called only once
		assertEquals(someLocationCode, retrieved.code)
	}
	
	@Test
	void shouldClearAllElementsFromCacheWhenRequested() {
		// given
		def someLocationCode = "LON"
		when(mockLocationDao.getLocationByCode(someLocationCode)).thenReturn(new Location.Builder().withCode(someLocationCode).build())
		
		// when
		airportResolutionService.getByCode(someLocationCode)
		airportResolutionService.refresh()
		airportResolutionService.getByCode(someLocationCode)
		
		// then
		verify(mockLocationDao, times(2)).getLocationByCode(someLocationCode) // NOTE: called only once
	}
}
