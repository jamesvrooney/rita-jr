package com.dealchecker.handler

import groovy.transform.TupleConstructor

import com.dealchecker.model.Deal
import com.dealchecker.model.Search
import com.dealchecker.model.SupplierInfo

@TupleConstructor
class DummyHandler implements SupplierHandler {

	final List<Deal> deals;
	final Throwable throwable;
	
	static DummyHandler thatThrows(Throwable t) {
		DummyHandler handler = new DummyHandler(null, t)
		handler
	}
	
	static DummyHandler thatReturns(List<Deal> deals) {
		DummyHandler handler = new DummyHandler(deals, null)
		handler
	}
	
	@Override
	List<Deal> handle(Search search, SupplierInfo supplierInfo) {
		if(deals == null) {
			throw throwable
		}
		deals
	}

}
