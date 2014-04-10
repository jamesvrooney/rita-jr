package com.dealchecker.handler.utils

import com.dealchecker.handler.DoNothingHandler;
import com.dealchecker.handler.DummyHandler
import com.dealchecker.handler.SupplierHandler
import com.dealchecker.model.Deal


/**
 * Utility class that builds handlers for test usage.
 * @author lpedrosa
 */
final class Handlers {

	/**
	 * Returns a new handler that will always return the given list of deals.
	 * @param deals the list of deals the handler will return
	 * @return an instance of an handler that returns the given list of deals
	 */
	static SupplierHandler newHandlerThatReturns(List<Deal> deals) {
		SupplierHandler handler = DummyHandler.thatReturns(deals)
		handler
	}
	
	/**
	 * Returns a new handler that will always throw the given exception.
	 * @param t the Throwable the handler will throw
	 * @return an instance of an handler that throws the given exception
	 */
	static SupplierHandler newHandlerThatThrows(Throwable t) {
		SupplierHandler handler = DummyHandler.thatThrows(t)
		handler
	}

	/**
	 * Returns a new handler that will do nothing.
	 * @return an instance of an handler that does nothing
	 */
	static SupplierHandler newHandlerThatDoesNothing() {
		SupplierHandler handler = new DoNothingHandler()
		handler
	}
}
