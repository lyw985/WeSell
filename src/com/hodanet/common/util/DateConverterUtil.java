package com.hodanet.common.util;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lance.lengcs
 * @version 2012-8-17 4:45:03
 * 
 *          <pre>
 * Spirngڸʽת
 *          </pre>
 */
public class DateConverterUtil extends PropertyEditorSupport {

	/**
	 * ڸʽ.Ĭϸʽ
	 */
	public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public static final String yyyy_MM_ddHHmmss = "yyyy-MM-dd HH:mm:ss";

	public static final String yyyyMMdd = "yyyyMMdd";

	public static final String yyyy_MM_dd = "yyyy-MM-dd";

	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";

	public static final String yyyyMMddHHmmss_SSS = "yyyyMMddHHmmss_SSS";

	public static final String yyyyYearMMMonthddDay = "yyyyMMdd";

	public static String format(Date date, String pattern) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			return dateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String format(String pattern) {
		return format(new Date(), pattern);
	}

	public static Date parse(String source, String pattern) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.parse(source);
	}

	@Override
	public String getAsText() {
		if (this.getValue() == null) {
			return "";
		} else {
			return FORMAT.format(this.getValue());
		}
	}

	@Override
	public void setAsText(String text) {
		try {
			Date date = FORMAT.parse(text);
			this.setValue(date);
		} catch (ParseException e) {
			this.setValue(null);
		}
	}
	
	public static Date floorDate(Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static void main(String[] args) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(yyyyMMddHHmmss);
		System.out.println(dateFormat.format(new Date()));
	}
}
