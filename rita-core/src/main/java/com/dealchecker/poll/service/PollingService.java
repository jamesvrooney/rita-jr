package com.dealchecker.poll.service;

import com.dealchecker.cache.service.NoValueForKeyCacheException;
import com.dealchecker.model.SupplierResult;

public interface PollingService {

	SupplierResult pollForToken(String pollToken) throws NoValueForKeyCacheException;
}
