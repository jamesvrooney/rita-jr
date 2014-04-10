package com.dealchecker.model.util;

import java.util.Map;

import com.dealchecker.model.ResponseMessage;
import com.dealchecker.model.SupplierResult;
import com.dealchecker.model.ResponseMessage.Status;
import com.google.common.collect.ImmutableMap;

public final class ResponseMessages {
	
	public static final ResponseMessage newErrorMessage(String message) {
		return new ResponseMessage(Status.ERROR, message, null);
	}

	public static final ResponseMessage newErrorMessage(String message, Map<String, ? extends Object> extraInfo) {
		return new ResponseMessage(Status.ERROR, message, extraInfo);
	}
	
	public static final ResponseMessage newMessageForSupplier(String message, SupplierResult supplierResult) {
		return new ResponseMessage(Status.SUCCESS, message, ImmutableMap.<String, SupplierResult>builder()
				.put("supplierResult", supplierResult)
				.build());
	}
	
	public static final ResponseMessage newMessage(String message) {
		return new ResponseMessage(Status.SUCCESS, message, null);
	}
	
	public static final ResponseMessage newMessageWithExtraInfo(String message, Map<String, ? extends Object> extraInfo) {
		return new ResponseMessage(Status.SUCCESS, message, extraInfo);
	}

	private ResponseMessages() {}
}
