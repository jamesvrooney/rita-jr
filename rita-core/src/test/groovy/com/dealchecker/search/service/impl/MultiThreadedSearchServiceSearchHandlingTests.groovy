package com.dealchecker.search.service.impl;

import static org.junit.Assert.*
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatcher
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

import com.dealchecker.cache.service.CacheService
import com.dealchecker.handler.service.HandlerService
import com.dealchecker.handler.utils.Handlers
import com.dealchecker.model.Deal
import com.dealchecker.model.Search
import com.dealchecker.model.SearchRequest
import com.dealchecker.model.SupplierInfo
import com.dealchecker.model.SupplierResult
import com.dealchecker.model.SupplierResult.Status
import com.dealchecker.search.service.SearchService
import com.google.common.util.concurrent.ListeningExecutorService
import com.google.common.util.concurrent.MoreExecutors

@RunWith(MockitoJUnitRunner.class)
class MultiThreadedSearchServiceSearchHandlingTests {
	
	private SearchService searchService
	
	@Mock
	private CacheService mockCacheService
	@Mock
	private HandlerService mockHandlerService
	
	// For tests it can run in same thread
	private ListeningExecutorService pool = MoreExecutors.sameThreadExecutor()
	
	private static final Integer SUPPLIER_THATS_OK = 1
	private static final Integer SUPPLIER_THAT_THROWS = 2
	private static final Deal TEST_DEAL = new Deal()
	
	@Before
	void setUp() {
		searchService = new MultiThreadedSearchServiceImpl(mockCacheService, mockHandlerService, pool)
		
		when(mockHandlerService.get(SUPPLIER_THATS_OK)).thenReturn(Handlers.newHandlerThatReturns([TEST_DEAL]))
		when(mockHandlerService.get(SUPPLIER_THAT_THROWS)).thenReturn(Handlers.newHandlerThatThrows(new Exception('error')))
		when(mockCacheService.put(anyString(), anyObject())).thenReturn(true)
	}

	@Test
	void simpleSearchShouldInteractWithCorrectComponents() {
		// given
		def supplierInfo = [new SupplierInfo(SUPPLIER_THATS_OK, '')] as Set<SupplierInfo>

		SearchRequest request = new SearchRequest(new Search(), 'some-token', supplierInfo)
		
		// when
		searchService.doSearchAndStoreDeals(request)
		
		// then
		verify(mockCacheService).put(anyString(), argThat(new IsSupplierResultWithStatus(statusToMatch: Status.COMPLETE)))
	}
	
	@Test
	void searchingForSupplierThatThrowsPropagatesException() {
		// given
		def supplierInfo = [new SupplierInfo(SUPPLIER_THAT_THROWS, '')] as Set<SupplierInfo>

		SearchRequest request = new SearchRequest(new Search(), 'some-token', supplierInfo)
		
		// when
		searchService.doSearchAndStoreDeals(request)
		
		// then
		verify(mockCacheService, never()).put(anyString(), argThat(new IsSupplierResultWithStatus(statusToMatch: Status.COMPLETE)))
	}

	@Test
	void ifOneSupplierThrowsShouldStillGetOthersFine() {
		// given
		def supplierInfo = [new SupplierInfo(SUPPLIER_THAT_THROWS, ''), new SupplierInfo(SUPPLIER_THATS_OK, '')] as Set<SupplierInfo>

		SearchRequest request = new SearchRequest(new Search(), 'some-token', supplierInfo)
		
		// when
		searchService.doSearchAndStoreDeals(request)
		
		// then
		verify(mockCacheService).put(anyString(), argThat(new IsSupplierResultWithStatus(statusToMatch: Status.ERROR)))
		verify(mockCacheService).put(anyString(), argThat(new IsSupplierResultWithStatus(statusToMatch: Status.COMPLETE)))
	}
	
	private class IsSupplierResultWithStatus extends ArgumentMatcher<List> {
		Status statusToMatch
		
		@Override
		boolean matches(Object argument) {
			return statusToMatch.equals(((SupplierResult) argument).status)
		}
	}
}
