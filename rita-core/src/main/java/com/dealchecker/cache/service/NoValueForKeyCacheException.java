package com.dealchecker.cache.service;

public class NoValueForKeyCacheException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NoValueForKeyCacheException(String missingKey) {
		super("No cache entry for key " + missingKey);
	}
}
