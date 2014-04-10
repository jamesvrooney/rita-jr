package com.dealchecker.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.common.base.Preconditions;

public final class DateUtil {
	
	public static final String format(String source, String inputPattern, String outputPattern) throws ParseException {
		Preconditions.checkNotNull(source);
		
		SimpleDateFormat inputDf = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
		SimpleDateFormat outputDf = new SimpleDateFormat(outputPattern, Locale.ENGLISH);
		
		Date sourceDate = inputDf.parse(source);		
		return outputDf.format(sourceDate);
	}
	
	public static final Date parse(String source, String inputPattern) throws ParseException {
		Preconditions.checkNotNull(source);
		
		SimpleDateFormat inputDf = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
		Date sourceDate = inputDf.parse(source);

		return sourceDate;
	}
	
	private DateUtil() {}
}
