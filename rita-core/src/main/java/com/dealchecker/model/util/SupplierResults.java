package com.dealchecker.model.util;

import java.util.Collections;
import java.util.List;

import com.dealchecker.model.Deal;
import com.dealchecker.model.SupplierResult;
import com.dealchecker.model.SupplierResult.Status;

public final class SupplierResults {

	public static final SupplierResult newRunning() {
		return new SupplierResult(Status.RUNNING, Collections.<Deal>emptyList(), null);
	}
	
	public static final SupplierResult newError(String reason) {
		return new SupplierResult(Status.ERROR, Collections.<Deal>emptyList(), reason);
	}
	
	public static final SupplierResult newResult(List<Deal> deals) {
		return new SupplierResult(Status.COMPLETE, deals, null);
	}
	
	private SupplierResults() {}
}
