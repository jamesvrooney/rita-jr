package com.dealchecker.handler;

import static org.junit.Assert.*

import java.text.SimpleDateFormat

import org.joda.time.DateTime
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import com.dealchecker.config.AppConfig
import com.dealchecker.config.HandlerConfig
import com.dealchecker.handler.ebookers.EbookersHolidayHandler
import com.dealchecker.handler.ebookers.EbookersHolidays
import com.dealchecker.handler.service.HandlerService
import com.dealchecker.model.BoardBasis
import com.dealchecker.model.Deal
import com.dealchecker.model.Search
import com.dealchecker.model.SupplierInfo

@Ignore
@RunWith(SpringJUnit4ClassRunner)
@SpringApplicationConfiguration(classes = [AppConfig, HandlerConfig])
class EbookersHolidayHandlerIntegrationTests {
	
	@Autowired
	private HandlerService handlerService

	@Test
	void testRequest() {
		def searchToken = "someToken"
		def supplierInfo = new SupplierInfo(EbookersHolidays.supplierId, "PAR")
		
		def departureDate = new DateTime().plusDays(1).toDate();
		def returnDate = new DateTime(departureDate).plusDays(7).toDate()
		def sdf = new SimpleDateFormat("yyyy/MM/dd")
		
		def search = new Search.Builder().withDeparture("LON")
			.withDepartureId(14344)
			.withDestination("Paris")
			.withDestinationId(3804)
			.withDepartureDate(sdf.format(departureDate))
			.withReturnDate(sdf.format(returnDate))
			.withAdults(2)
			.withBoardType(BoardBasis.ANY.code)
			.withStarRating(3)
			.build()
		
		EbookersHolidayHandler ebookersHandler = handlerService.get(EbookersHolidays.supplierId)

		String xmlResult = ebookersHandler.requestResults(search, supplierInfo)
		assertNotNull(xmlResult)
		
		List<Deal> deals = ebookersHandler.handleResponse(xmlResult, search, supplierInfo)
		assertFalse(deals.isEmpty())
	}
}
