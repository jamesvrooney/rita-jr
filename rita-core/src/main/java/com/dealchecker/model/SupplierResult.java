package com.dealchecker.model;

import java.util.List;

public class SupplierResult {

	private final Status status;
	private final String message;
	private final List<Deal> deals;
	
	public SupplierResult(Status status, List<Deal> deals, String message) {
		this.status = status;
		this.deals = deals;
		this.message = message;
	}
	
	public Status getStatus() {
		return status;
	}

	public List<Deal> getDeals() {
		return deals;
	}

	public String getMessage() {
		return message;
	}

	public static enum Status {
		COMPLETE, RUNNING, ERROR
	}
}
