package com.dealchecker.handler.service;

public class MissingLocationException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public MissingLocationException(String message) {
		super(message);
	}
	
	public MissingLocationException(String message, Throwable t) {
		super(message, t);
	}
}
