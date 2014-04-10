package com.dealchecker.handler;

public class ResponseHandlingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResponseHandlingException(String message) {
		super(message);
	}
}
