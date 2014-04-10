package com.dealchecker.handler

import com.dealchecker.model.Deal
import com.dealchecker.model.Search
import com.dealchecker.model.SupplierInfo

class DoNothingHandler implements SupplierHandler {

	@Override
	public List<Deal> handle(Search search, SupplierInfo supplierInfo) {
		[]
	}

}
