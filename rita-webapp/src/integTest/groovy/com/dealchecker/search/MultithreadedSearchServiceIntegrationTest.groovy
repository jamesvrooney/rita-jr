package com.dealchecker.search;

import static org.junit.Assert.*

import java.text.SimpleDateFormat

import org.joda.time.DateTime
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import com.dealchecker.cache.service.CacheService
import com.dealchecker.config.AppConfig
import com.dealchecker.config.HandlerConfig
import com.dealchecker.config.InMemoryCacheConfig
import com.dealchecker.config.SearchConfig
import com.dealchecker.handler.ebookers.EbookersHolidays
import com.dealchecker.model.BoardBasis
import com.dealchecker.model.Deal
import com.dealchecker.model.Search
import com.dealchecker.model.SearchRequest
import com.dealchecker.model.SupplierInfo
import com.dealchecker.model.SupplierResult
import com.dealchecker.model.SupplierResult.Status
import com.dealchecker.search.service.SearchService

@RunWith(SpringJUnit4ClassRunner)
@SpringApplicationConfiguration(classes = [AppConfig, SearchConfig, InMemoryCacheConfig, HandlerConfig])
class MultithreadedSearchServiceIntegrationTest {

	@Autowired
	private SearchService searchService
	
	@Autowired
	private CacheService cacheService
	
	@Test
	void searchServiceShouldStoreDealsFromDummyHandler() {
		// given
		def searchToken = "someToken"
		def supplierInfo = [SupplierInfo.dummySupplierInfo()] as Set<SupplierInfo>
		def searchRequest = new SearchRequest(new Search(), searchToken, supplierInfo)
		
		// when
		searchService.doSearchAndStoreDeals(searchRequest)
		
		// then
		SupplierResult result = cacheService.get(searchToken + "-" + 0)
		
		while(result.status == Status.RUNNING) {
			result = cacheService.get(searchToken + "-" + EbookersHolidays.supplierId)
		}
		
		List<Deal> deals = result.deals
		
		assertNotNull(deals)
		assertFalse(deals.isEmpty())
	}
}
