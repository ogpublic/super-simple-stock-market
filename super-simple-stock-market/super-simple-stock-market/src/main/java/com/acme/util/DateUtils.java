package com.acme.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class defining some utilitary time operations.
 * 
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public class DateUtils {
	
	public static final boolean isNewerOrEqualTo(Date date, int ageInMinutes) {
		long milliseconds = ageInMinutes * 60 * 1000;

		long currentTime = System.currentTimeMillis();
		long timestamp = date.getTime();

		return timestamp >= currentTime - milliseconds;
	}

	public static final Date parseDate(String dateAsString, String pattern) {
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			return dateFormat.parse(dateAsString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
