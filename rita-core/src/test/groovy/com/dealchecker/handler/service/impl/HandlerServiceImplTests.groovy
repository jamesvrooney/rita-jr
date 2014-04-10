package com.dealchecker.handler.service.impl;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import com.dealchecker.handler.SupplierHandler
import com.dealchecker.handler.service.HandlerService
import com.dealchecker.handler.service.MissingSupplierHandlerException;
import com.dealchecker.handler.utils.Handlers;

class HandlerServiceImplTests {
	
	private HandlerService handlerService
	
	private static final Integer EXISTING_SUPPLIER = 1
	
	@Before
	void setUp() throws Exception {
		def subscribedHandlers = [(EXISTING_SUPPLIER):Handlers.newHandlerThatDoesNothing()]
		handlerService = new HandlerServiceImpl(subscribedHandlers)
	}

	@Test(expected=MissingSupplierHandlerException.class)
	void obtainingAnHandlerForAnUnknownSupplierIdShouldThrow() {
		// given
		def unknownSupplierId = -1
		
		// when
		handlerService.get(unknownSupplierId)
		
		// then
		fail('Should have thrown MissingHandlerException')
	}
	
	@Test
	void obtainingAnExistingHandlerShouldBeSuccessful() {
		// given
		// an existing supplier id
		
		// when
		SupplierHandler handler = handlerService.get(EXISTING_SUPPLIER)
		
		// then
		assertNotNull(handler)
	}

}
