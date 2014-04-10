package com.dealchecker.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dealchecker.cache.service.CacheService;
import com.dealchecker.cache.service.impl.InMemoryCacheServiceImpl;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Configuration
public class InMemoryCacheConfig {
	
	@Bean
	public CacheService inMemoryCacheService() {
		// TODO expire time need to be externalized
		Cache<String, Object> cache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build();
		InMemoryCacheServiceImpl cacheService = new InMemoryCacheServiceImpl(cache);
		return cacheService;
	}
}
