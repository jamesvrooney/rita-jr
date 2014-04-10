package com.dealchecker.handler.service;

import com.dealchecker.handler.SupplierHandler;

public interface HandlerService {
	
	SupplierHandler get(Integer supplierId) throws MissingSupplierHandlerException;
}
