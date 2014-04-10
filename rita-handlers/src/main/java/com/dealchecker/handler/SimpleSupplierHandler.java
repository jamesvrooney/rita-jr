package com.dealchecker.handler;

import java.util.List;

import com.dealchecker.model.Deal;
import com.dealchecker.model.Search;
import com.dealchecker.model.SupplierInfo;

public abstract class SimpleSupplierHandler implements SupplierHandler {
	
	@Override
	public List<Deal> handle(Search search, SupplierInfo supplierInfo) {
		String apiResponse = requestResults(search, supplierInfo);
		List<Deal> processedDeals = handleResponse(apiResponse, search, supplierInfo);
		return processedDeals;
	}
	
	public abstract String requestResults(Search search, SupplierInfo supplierInfo);
	public abstract List<Deal> handleResponse(String apiResult, Search search, SupplierInfo supplierInfo);

}
