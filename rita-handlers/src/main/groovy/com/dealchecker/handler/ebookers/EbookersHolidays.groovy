package com.dealchecker.handler.ebookers

import com.dealchecker.model.Deal
import com.dealchecker.model.Search
import com.dealchecker.model.SupplierInfo
import com.dealchecker.util.DateUtil

final class EbookersHolidays {
	
	static final String baseUrl = "https://ws.orbitzworldwide.com/xml/service"
	static final String baseHttpUserName = "52a2daf567964287a58609bcb1ba963c"
	static final String baseHttpPassword = "e888bdce1a4a436781d04dd356a7839f"
	
	static final Integer supplierId = 2576
	static final String supplierName = "ebookersHolidayFeed"
	static final String datePattern = "yyyy-MM-dd"

	static final String trackingUrl = "http://clkuk.tradedoubler.com/click?p=94180&amp;a=1604056&amp;g=19842626&amp;url="
	static final String gcId = "&amp;gcid=16618"

	static String generateRequestXml(Search search, SupplierInfo info) {
		
		def dcDatePattern = "yyyy/MM/dd"
		def formattedDepartureDate = DateUtil.format(search.departureDate, dcDatePattern, datePattern)
		def formattedReturnDate = DateUtil.format(search.returnDate, dcDatePattern, datePattern)
		
		def params = [
			departure:"$search.departure",
			destination:"${info?.supplierKey}",
			departureDate:"$formattedDepartureDate",
			returnDate:"$formattedReturnDate"
		]
		
		def requestXml = """
		<DynamicPackagingRequest xmlns="http://ws.orbitz.com/schemas/2008/DynamicPackaging">
		  <PointOfSale>EBUK</PointOfSale>
		  <Origin code="$params.departure"/>
		  <Destination code="$params.destination"/>
		  <TravelerInfo>
		    <Adult count="2"/>
		  </TravelerInfo>
		  <NumberOfRooms>1</NumberOfRooms>
		  <StartDate>$params.departureDate</StartDate>
		  <EndDate>$params.returnDate</EndDate>
		  <SuggestDisambiguation>true</SuggestDisambiguation>
		  <Package>
		    <ProductType code="AIR"/>
		    <ProductType code="HOT"/>
		  </Package>
		  <DistributionPartnerDetails/>
		</DynamicPackagingRequest>
		"""
		
		requestXml
	}
	
	static Deal.Builder newDealTemplate() {
		new Deal.Builder()
		.withSupplierId(supplierId)
		.withSupplierName(supplierName)
	}
	
	// prevent utility class instantiation
	private EbookersHolidays() {}
}
