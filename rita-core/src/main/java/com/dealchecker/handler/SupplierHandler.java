package com.dealchecker.handler;

import java.util.List;

import com.dealchecker.model.Deal;
import com.dealchecker.model.Search;
import com.dealchecker.model.SupplierInfo;

public interface SupplierHandler {
	List<Deal> handle(Search search, SupplierInfo supplierInfo);
}
