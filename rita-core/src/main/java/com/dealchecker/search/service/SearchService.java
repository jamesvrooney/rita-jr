package com.dealchecker.search.service;

import com.dealchecker.model.SearchRequest;

public interface SearchService {
	void doSearchAndStoreDeals(SearchRequest search);
}
