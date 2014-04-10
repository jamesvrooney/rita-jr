package com.dealchecker.handler.ebookers;

import static org.junit.Assert.*

import org.junit.Test

import com.dealchecker.model.Search
import com.dealchecker.model.SupplierInfo

class EbookersHolidayHandlerRequestTest {
	
	@Test
	void requestDatesShouldBeCorrectlyFormated() {
		// given
		def startDate = "2014/03/02"
		def endDate = "2014/03/05"
		def search = new Search.Builder().withDepartureDate(startDate).withReturnDate(endDate).build()
		
		// when
		String requestXml = EbookersHolidays.generateRequestXml(search, null)
		
		// then
		assertTrue(requestXml.contains("<StartDate>2014-03-02</StartDate>"))
		assertTrue(requestXml.contains("<EndDate>2014-03-05</EndDate>"))
	}
	
	@Test
	void requestDestinationShouldUseSupplierKey() {
		// given
		def supplierKey = "PAR"
		def supplierInfo = new SupplierInfo(EbookersHolidays.supplierId, supplierKey)
		def search = newDefaultSearchBuilder().build()
		
		// when
		String requestXml = EbookersHolidays.generateRequestXml(search, supplierInfo)
		
		// then
		assertTrue(requestXml.contains("""<Destination code="$supplierKey"/>"""))
	}
	
	@Test
	void requestDepartureShouldBeCorrectlyHandled() {
		// given
		def departure = "LON"
		def search = newDefaultSearchBuilder().withDeparture(departure).build()
		
		// when
		String requestXml = EbookersHolidays.generateRequestXml(search, null)
		
		// then
		assertTrue(requestXml.contains("""<Origin code="$departure"/>"""))
	}
	
	private Search.Builder newDefaultSearchBuilder() {
		new Search.Builder().withDepartureDate("2014/03/02").withReturnDate("2014/03/05")
	}
}
