package com.dealchecker.poll;

import static org.junit.Assert.*
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

import com.dealchecker.cache.service.CacheService
import com.dealchecker.cache.service.NoValueForKeyCacheException
import com.dealchecker.model.Deal
import com.dealchecker.model.SupplierResult
import com.dealchecker.model.util.SupplierResults
import com.dealchecker.poll.service.PollingService
import com.dealchecker.poll.service.impl.PollingServiceImpl

@RunWith(MockitoJUnitRunner)
class PollingServiceTests {
	
	PollingService pollingService
	@Mock
	CacheService mockCacheService
	
	static final def TOKEN_WITH_RESULT = "token-with-result"
	static final def TOKEN_WITH_NO_RESULT = "token-with-no-result"

	@Before
	void setUp() throws Exception {
		pollingService = new PollingServiceImpl(mockCacheService)
		
		when(mockCacheService.get(TOKEN_WITH_RESULT)).thenReturn(SupplierResults.newResult([new Deal()]))
		doThrow(NoValueForKeyCacheException).when(mockCacheService).get(TOKEN_WITH_NO_RESULT)
	}

	@Test
	void shouldReturnSuccessfullMessageIfTokenHasResult() {
		// given
		def token = TOKEN_WITH_RESULT
		 
		// when
		SupplierResult result = pollingService.pollForToken(token)
		
		// then
		assertNotNull(result)
		assertEquals(SupplierResult.Status.COMPLETE, result.status)
		assertFalse(result.deals.isEmpty())
	}

	@Test(expected=NoValueForKeyCacheException)
	void shouldReturnErrorMessageIfTokenHasNoResult() {
		// given
		def token = TOKEN_WITH_NO_RESULT
		 
		// when
		pollingService.pollForToken(token)
	}
}
