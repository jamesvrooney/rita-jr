package com.dealchecker.poll.service.impl;

import com.dealchecker.cache.service.CacheService;
import com.dealchecker.cache.service.NoValueForKeyCacheException;
import com.dealchecker.model.SupplierResult;
import com.dealchecker.poll.service.PollingService;

public final class PollingServiceImpl implements PollingService {
	
	private final CacheService cache;
	
	public PollingServiceImpl(CacheService cache) {
		this.cache = cache;
	}

	@Override
	public SupplierResult pollForToken(String pollToken) throws NoValueForKeyCacheException {
		return cache.get(pollToken);
	}
}
