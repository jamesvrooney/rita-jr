package com.dealchecker.cache

import static org.junit.Assert.*

import java.util.concurrent.TimeUnit

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import com.dealchecker.cache.service.CacheService
import com.dealchecker.cache.service.NoValueForKeyCacheException
import com.dealchecker.config.InMemoryCacheConfig
import com.dealchecker.model.Deal

@RunWith(SpringJUnit4ClassRunner)
@SpringApplicationConfiguration(classes = InMemoryCacheConfig)
class InMemoryCacheIntegrationTest {

	@Autowired
	private CacheService cacheService
	
	private static final Deal TEST_DEAL = new Deal()
	
	@Test
	void storedDealShouldBeRetrievable() {
		// given
		def deals = [TEST_DEAL]
		def someKey = 'someKey'
		
		// when
		cacheService.put(someKey, deals)
		List<Deal> retrivedDeals = cacheService.get(someKey)
		
		// then
		assertNotNull(retrivedDeals)
		assertFalse(retrivedDeals.isEmpty())
		
		Deal deal = retrivedDeals.get(0)
		assertEquals(TEST_DEAL, deal)
	}
	
	@Test(expected=NoValueForKeyCacheException)
	void storedDealShouldBeEvictedAutomaticallyAfter1Min() {
		// given
		def deals = [TEST_DEAL]
		def someKey = 'someKey'
		
		// when
		cacheService.put(someKey, deals)
		TimeUnit.MINUTES.sleep(1);
		
		// then
		cacheService.get(someKey)		
	}

	
}
