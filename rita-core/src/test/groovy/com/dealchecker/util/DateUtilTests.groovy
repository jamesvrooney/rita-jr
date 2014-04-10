package com.dealchecker.util;

import static org.junit.Assert.*

import org.joda.time.DateTime
import org.junit.Test

class DateUtilTests {


	@Test
	void testParseDateShouldReturnOkDate() {
		
		// given
		def dateStr = "2014-03-12T07:10:00.000"
		def format = "yyyy-MM-dd'T'HH:mm:ss.SSS"
		
		// when
		Date date = DateUtil.parse(dateStr, format)
		
		// then
		DateTime dt = new DateTime(date)
		
		assertEquals(2014, dt.getYear())
		assertEquals(3, dt.getMonthOfYear())
		assertEquals(12, dt.getDayOfMonth())
		assertEquals(7, dt.getHourOfDay())
		assertEquals(10, dt.getMinuteOfHour())
		assertEquals(0, dt.getSecondOfMinute())
		assertEquals(0, dt.getMillisOfSecond())
	}

}
