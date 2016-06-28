package com.nala.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * 
 * @author Kenny
 * 
 */

public class DateUtils extends org.apache.commons.lang.time.DateUtils {

	/**
	 * 时间间隔
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long DaysBetween(Date date1, Date date2) {
		if (date2 == null){
			date2 = new Date();
		}
		long minte = (date2.getTime() - date1.getTime()) / (60 * 1000);
		return minte;
	}
	
	/**
	 * 返回时间为 110501格式
	 * 
	 * @param date
	 * @return
	 */
	public static String parseToShortFormat(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		return df.format(date);
	}
	
	public static String parseToCommonFormat(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(date);
	}
	
	
	/**
	 * 时间显示为yyyy-MM-dd 24hh:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String parseToWebFormat2(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.CHINESE);
		if (date != null) {
			return df.format(date);
		} else {
			return "";
		}
	}

	/**
	 * 时间显示为yyyy-MM-dd 24hh:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String parseToWebFormat(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.CHINESE);
		if (date != null) {
			return df.format(date);
		} else {
			return "";
		}
	}

	/**
	 * 时间显示为yyyy-MM-dd 24hh
	 * 
	 * @param date
	 * @return
	 */
	public static String parseToFormat(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy  MM  dd HH",
				Locale.CHINESE);
		return df.format(date);
	}

	public static String parseToDayFormat(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String now() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.CHINESE);
		Date date = new Date();
		return df.format(date);
	}

	public static String sendGoodsTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
		Date date = new Date();
		String sendTime = df.format(date) + " 16:00:00";
		return sendTime;
	}

	public static Date parseToDayDate(String str) {
		String[] patters = new String[1];
		patters[0] = "yyyy-MM-dd";
		Date date = null;
		try {
			date = parseDate(str, patters);
		} catch (ParseException e) {
			// ignore
		}
		return date;
	}

	public static Date parseToDate(String str) {
		String[] patters = new String[1];
		patters[0] = "yyyy-MM-dd HH:mm";
		Date date = null;
		try {
			date = parseDate(str, patters);
		} catch (ParseException e) {
			// ignore
		}
		return date;
	}

	public static Date parseToDates(String str) {
		String[] patters = new String[1];
		patters[0] = "yyyy-MM-dd HH:mm:ss";
		Date date = null;
		try {
			date = parseDate(str, patters);
		} catch (ParseException e) {
			// ignore
		}
		return date;
	}
	
	/**
	 * 将时间的时分秒去掉
	 * 
	 * @param date
	 * @return
	 */
	public static Date reverseDate(Date date) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date);
		Calendar cal2 = Calendar.getInstance();
		cal2.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH),
				cal1.get(Calendar.DATE), 0, 0, 0);
		return cal2.getTime();
	}


	/**
	 * 获取当前时间与 起始时间的差按天统计
	 * 
	 * @param end
	 * @return 相差的天数
	 */
	public static int dateDifferDay(Date end) {
		end = reverseDate(end);
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(end);

		Calendar cal2 = Calendar.getInstance();
		Date cur = new Date();
		cal2.setTime(reverseDate(cur));
		long timeDiffer = cal2.getTimeInMillis() - cal1.getTimeInMillis();
		int day = (int) (timeDiffer / (3600 * 1000 * 24));
		return day;
	}

	public static Date getMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

		return calendar.getTime();
	}

	public static Date getMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		return calendar.getTime();
	}
	
	/**
	 * 一年后日期
	 * @return
	 */
	public static Date getOneYearLaterDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.YEAR,
				calendar.get(Calendar.YEAR)+1);

		return calendar.getTime();
	}
	
	/**
	 * 获取指定日期date往后的num天
	 * @param date
	 * @param num
	 * @return
	 */
	public static Date getDayLaterDefinedDay(Date date,int num){
		if(date == null){
			return new Date();
		}else{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+2);
			
			return calendar.getTime();
		}
	}
	
	/**
	 * 几天之前
	 * @param interval 天数
	 * @param dateFormat 返回格式
	 * @return
	 */
	public static String getDaysAgo(int interval, String dateFormat) {
		Date date = new Date();
		long time = (date.getTime() / 1000) - interval * 60 * 60 * 24;
		date.setTime(time * 1000);
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		try {
			return format.format(date);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return "";
	}
	
	/**
	 * 几天之前
	 * @param interval
	 * @return
	 */
	public static Date getDaysAgo(int interval) {
		Date date = new Date();
		long time = (date.getTime() / 1000) - interval * 60 * 60 * 24;
		date.setTime(time * 1000);
		
		return date;
		
	}
	
}
