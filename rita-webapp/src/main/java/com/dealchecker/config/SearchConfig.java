package com.dealchecker.config;

import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dealchecker.cache.service.CacheService;
import com.dealchecker.handler.service.HandlerService;
import com.dealchecker.poll.service.PollingService;
import com.dealchecker.poll.service.impl.PollingServiceImpl;
import com.dealchecker.search.service.SearchService;
import com.dealchecker.search.service.impl.MultiThreadedSearchServiceImpl;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

@Configuration
public class SearchConfig {
	@Autowired
	private CacheService cacheService;
	@Autowired
	private HandlerService handlerService;
	
	// TODO externalize thread pool size and configuration
	private ListeningExecutorService pool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
	
	@Bean
	public SearchService multiThreadedSearchService() {
		MultiThreadedSearchServiceImpl searchService = new MultiThreadedSearchServiceImpl(cacheService, handlerService, pool);
		return searchService;
	}
	
	@Bean
	public PollingService pollingService() {
		PollingService pollingService = new PollingServiceImpl(cacheService);
		return pollingService;
	}
}
