package com.dealchecker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dealchecker.cache.service.NoValueForKeyCacheException;
import com.dealchecker.model.ResponseMessage;
import com.dealchecker.model.SearchRequest;
import com.dealchecker.model.SupplierResult;
import com.dealchecker.model.util.ResponseMessages;
import com.dealchecker.poll.service.PollingService;
import com.dealchecker.search.service.SearchService;

@RestController
@RequestMapping("/api")
public class SearchRestfullController {

	private final SearchService searchService;
	private final PollingService pollingService;
	
	@Autowired
	public SearchRestfullController(SearchService searchService, PollingService pollingService) {
		this.searchService = searchService;
		this.pollingService = pollingService;
	}
	
	@RequestMapping(value="/poll", method=RequestMethod.GET)
	public ResponseMessage poll(@RequestParam(required=true) String token) {
		SupplierResult result = null;
		try {
			result =  pollingService.pollForToken(token);
		} catch(NoValueForKeyCacheException ex) {
			return ResponseMessages.newErrorMessage("No result found for given token: "+token);
		}
		return ResponseMessages.newMessageForSupplier("Result for token: "+token, result);
	}
	
	@RequestMapping(value="/search", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseMessage search(@RequestBody SearchRequest searchRequest) {
		// TODO searchRequest validation!
		searchService.doSearchAndStoreDeals(searchRequest);
		return ResponseMessages.newMessage("Search started for search id: " + searchRequest.getSearchToken());
	}
}
