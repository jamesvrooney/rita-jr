package com.dealchecker.cache;

import static org.junit.Assert.*

import java.util.concurrent.Callable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import com.dealchecker.cache.service.CacheService
import com.dealchecker.config.InMemoryCacheConfig
import com.dealchecker.model.Deal
import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.ListeningExecutorService
import com.google.common.util.concurrent.MoreExecutors

@RunWith(SpringJUnit4ClassRunner)
@SpringApplicationConfiguration(classes = InMemoryCacheConfig)
class InMemoryCachePutIfAbsentIntegrationTest {

	@Autowired
	private CacheService cacheService
	
	private static final Deal TEST_DEAL = new Deal()
	
	@Before
	void setUp() {
		cacheService.clear()
	}

	@Test
	void putIfAbsentShouldReturnNullIfValueIsNotPresent() {
		// given
		def deals = [TEST_DEAL]
		def someKey = 'someKey'
		
		// when
		List<Deal> result = cacheService.putIfAbsent(someKey, deals)
		List<Deal> retrievedDeals = cacheService.get(someKey)
		
		// then
		assertNull(result)
		assertNotNull(retrievedDeals)
		assertFalse(retrievedDeals.isEmpty())
		
		Deal deal = retrievedDeals.get(0)
		assertEquals(TEST_DEAL, deal)
	}

	@Test
	void putIfAbsentShouldReturnPreviousPresentValue() {
		// given
		def deals = [TEST_DEAL]
		def someKey = 'someKey'
		
		// when
		cacheService.put(someKey, deals)
		List<Deal> result = cacheService.putIfAbsent(someKey, deals)
		
		// then
		assertNotNull(result)
		assertFalse(result.isEmpty())
		
		Deal deal = result.get(0)
		assertEquals(TEST_DEAL, deal)
	}
	
	@Test
	void putIfAbsentShouldReturnPreviousPresentValueMultithreaded() {
		// given
		final def deals = [TEST_DEAL]
		final def someKey = 'someKey'
		
		ListeningExecutorService pool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(4))
		
		Callable<List<Deal>> putInCache = { cacheService.putIfAbsent(someKey, deals) }
		
		// when
		def futures = []
		for(int i in 0..100) {
			futures.add(pool.submit(putInCache))
		}
		
		ListenableFuture<List<List<Deal>>> futureList = Futures.allAsList(futures)
		
		// then
		final CountDownLatch latch = new CountDownLatch(1)
		Futures.addCallback(futureList, new FutureCallback<List<List<Deal>>>() {
			@Override
			void onSuccess(List<List<Deal>> dealRequests) {
				// one of them should be null
				// thats the one the registered the first value
				List<List<Deal>> firstSuccesfullPut = dealRequests.findAll { it == null }
				assertFalse(firstSuccesfullPut.isEmpty())
				assertNull(firstSuccesfullPut.first())
				latch.countDown()
			}		
			@Override
			void onFailure(Throwable t) {
				fail("Should have not failed to do the concurrent cache accesses")
				latch.countDown()
			}
		}, pool)

		boolean countedDown = latch.await(5, TimeUnit.SECONDS)
		if(!countedDown) {
			fail("Something is wrong with the test so I failed fast")
		}
	}
}
