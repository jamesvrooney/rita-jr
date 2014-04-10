package com.dealchecker.cache.service.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

import com.dealchecker.cache.service.CacheService;
import com.dealchecker.cache.service.NoValueForKeyCacheException;
import com.google.common.cache.Cache;

public final class InMemoryCacheServiceImpl implements CacheService {
	
	private final Cache<String, Object> cache;
	
	public InMemoryCacheServiceImpl(Cache<String, Object> cache) {
		this.cache = cache;
	}

	@Override
	public <T> boolean put(String key, T value) {
		cache.put(key, value);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T putIfAbsent(String key, final T value) {
		final AtomicBoolean isAbsent = new AtomicBoolean(false);
		T result = null;
		try {
			result = (T) cache.get(key, new Callable<T>() {
				@Override
				public T call() throws Exception {
					isAbsent.compareAndSet(false, true);
					return value;
				}
			});
		} catch(Exception ex) {
			new RuntimeException(ex.getCause());
		}
		if(isAbsent.get()) {
			return null;
		}
		return result;
	}

	@Override
	public <T> T get(String key) throws NoValueForKeyCacheException {
		@SuppressWarnings("unchecked")
		T value = (T)cache.getIfPresent(key);
		
		if(value == null) {
			throw new NoValueForKeyCacheException(key);
		}
		return value;
	}
	
	public void clear() {
		cache.invalidateAll();
	}

}
