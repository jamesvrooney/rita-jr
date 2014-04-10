package com.dealchecker.cache.service;


// TODO need refactoring... probably does not need to return futures here
public interface CacheService {

	<T> boolean put(String key, T value);
	
	<T> T putIfAbsent(String key, T value);
	
	<T> T get(String key) throws NoValueForKeyCacheException;
	
	void clear();
	
}
