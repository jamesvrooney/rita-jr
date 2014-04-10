package com.dealchecker.model;

import java.util.Set;


public final class SearchRequest {

	private final Search search;
	private final String searchToken;
	private final Set<SupplierInfo> supplierInfo;
	
	protected SearchRequest() {
		this.search = null;
		this.searchToken = null;
		this.supplierInfo = null;
	}
	
	public SearchRequest(Search search, String searchToken, Set<SupplierInfo> supplierInfo) {
		this.search = search;
		this.searchToken = searchToken;
		this.supplierInfo = supplierInfo;
	}

	public Search getSearch() {
		return search;
	}

	public String getSearchToken() {
		return searchToken;
	}

	public Set<SupplierInfo> getSupplierInfo() {
		return supplierInfo;
	}
	
}
