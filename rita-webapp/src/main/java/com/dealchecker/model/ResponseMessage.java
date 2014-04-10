package com.dealchecker.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public final class ResponseMessage {
	
	private final Status status;
	private final String message;
	private final Map<String, ? extends Object> payload;
	
	public ResponseMessage(Status status, String message, Map<String, ? extends Object> payload) {
		this.status = status;
		this.message = message;
		this.payload = payload;
	}
	
	public Status getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public Map<String, ? extends Object> getPayload() {
		return payload;
	}

	public enum Status {
		ERROR, SUCCESS
	}
}
