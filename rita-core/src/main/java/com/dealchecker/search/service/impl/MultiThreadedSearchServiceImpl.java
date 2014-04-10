package com.dealchecker.search.service.impl;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dealchecker.cache.service.CacheService;
import com.dealchecker.handler.SupplierHandler;
import com.dealchecker.handler.service.HandlerService;
import com.dealchecker.handler.service.MissingSupplierHandlerException;
import com.dealchecker.model.Deal;
import com.dealchecker.model.Search;
import com.dealchecker.model.SearchRequest;
import com.dealchecker.model.SupplierInfo;
import com.dealchecker.model.SupplierResult;
import com.dealchecker.model.util.SupplierResults;
import com.dealchecker.search.service.SearchService;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

public final class MultiThreadedSearchServiceImpl implements SearchService {
	
	private final CacheService cacheService;
	private final HandlerService handlerService;
	private final ListeningExecutorService pool;
	
	private static final Logger log = LoggerFactory.getLogger(MultiThreadedSearchServiceImpl.class);
	
	public MultiThreadedSearchServiceImpl(CacheService cacheService, HandlerService handlerService, ListeningExecutorService pool) {
		this.cacheService = cacheService;
		this.handlerService = handlerService;
		this.pool = pool;
	}

	@Override
	public void doSearchAndStoreDeals(SearchRequest search) {
		Set<SupplierInfo> supplierInfo = search.getSupplierInfo();
		
		for(SupplierInfo si : supplierInfo) {
			try {
				Integer supplierId = si.getSupplierId();
				SupplierHandler handler = handlerService.get(supplierId);
				String storageKey = search.getSearchToken() + "-" + supplierId.toString();
				
				//cacheService
				SupplierResult result = cacheService.putIfAbsent(storageKey, SupplierResults.newRunning());
				if(result == null) {
					ListenableFuture<List<Deal>> deals = pool.submit(handleSearchCallableWrapper(search.getSearch(), si, handler));
					Futures.addCallback(deals, handleSupplierResults(storageKey, supplierId));
				}
				
			} catch(MissingSupplierHandlerException ex) {
				log.error("Error searching for supplier [id: {}]: {}", si.getSupplierId(), ex.getMessage());
			}
		}
	}
	
	private FutureCallback<List<Deal>> handleSupplierResults(final String storageKey, final Integer supplierId) { 
		return new FutureCallback<List<Deal>>() {
			@Override
			public void onSuccess(List<Deal> deals) {
				cacheService.put(storageKey, SupplierResults.newResult(deals));
			};
			@Override
			public void onFailure(Throwable t) {
				cacheService.put(storageKey, SupplierResults.newError(t.getMessage()));
				log.error("Error handling supplierId: {}. {}", supplierId, t);
			};
		};
	}
	
	private Callable<List<Deal>> handleSearchCallableWrapper(final Search search, final SupplierInfo supplierInfo, final SupplierHandler handler) {
		return new Callable<List<Deal>>() {
			@Override
			public List<Deal> call() throws Exception {
				return handler.handle(search, supplierInfo);
			}
		};
	}

}
