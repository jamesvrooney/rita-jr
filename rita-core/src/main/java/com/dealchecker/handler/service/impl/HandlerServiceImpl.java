package com.dealchecker.handler.service.impl;

import java.util.Map;

import com.dealchecker.handler.SupplierHandler;
import com.dealchecker.handler.service.HandlerService;
import com.dealchecker.handler.service.MissingSupplierHandlerException;

public final class HandlerServiceImpl implements HandlerService {
	
	private final Map<Integer, SupplierHandler> subscribedHandlers;

	public HandlerServiceImpl(Map<Integer, SupplierHandler> subscribedHandlers) {
		this.subscribedHandlers = subscribedHandlers;
	}
	
	@Override
	public SupplierHandler get(Integer supplierId) throws MissingSupplierHandlerException {
		if(!subscribedHandlers.containsKey(supplierId)) {
			throw new MissingSupplierHandlerException("Missing handler for supplier with id: " + supplierId);
		}
		return subscribedHandlers.get(supplierId);
	}

}
