/*
 * Created on 7 déc. 2004
 */
package com.ap.util;

import java.util.Calendar;

/**
 * @author Jean Lazarou
 */
public class Dates {

	/**
	 * Creates a java.util.Date object...
	 * 
	 * @param year the year (full number, 2004 is not 04!)
	 * @param month the desired month (from 1 to 12)
	 * @param date the day date
	 * @return a java.util.Date object
	 */
	public static java.util.Date createUtilDate(int year, int month, int date) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, date, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * Creates a java.sql.Date object...
	 * 
	 * @param year the year (full number, 2004 is not 04!)
	 * @param month the desired month (from 1 to 12)
	 * @param date the day date
	 * @return a java.sql.Date object
	 */
	public static java.sql.Date createSQLDate(int year, int month, int date) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, date, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new java.sql.Date(cal.getTime().getTime());
	}
	
}
