
package com.zq.youxi.utils;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public final class DateUtil {
	public static final String LONG_ALL_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String LONG_DATE_PATTERN = "yyyy-MM-dd HH:mm";
	/** 短日期格式 */
	public static final String SHORT_DATE_PATTERN = "yyyy-MM-dd";
	/** 短日期格式 */
	public static final String SHORT_DATE_PATTERN_1 = "yyyyMMdd";
	/** 短日期格式 */
	public static final String SHORT_DATE_PATTERN_2 = "MM/dd/yyyy";
	/** 短日期格式 */
	public static final String SHORT_HH_MM_PATTERN = "HH:mm";
	/** 短日期正则表达式 */
	public static final String SHORT_DATE_REG = "\\d{4}-\\d{1,2}-\\d{1,2}";
	/** 长日期格式正则表达式 */
	public static final String LONG_DATE_REG = "[\\d]{4}[-][\\d]{2}[-][\\d]{2} [\\d]{1,2}:[\\d]{1,2}";

	public static final String LONG_DATE_REG_HAVE_SS = "[\\d]{4}[-][\\d]{2}[-][\\d]{2} [\\d]{1,2}:[\\d]{1,2}:[\\d]{1,2}";

	public static final String LONG_DATE_REG_HAVE_MS = "[\\d]{4}[-][\\d]{2}[-][\\d]{2} [\\d]{1,2}:[\\d]{1,2}:[\\d]{1,2}.[\\d]{1,2}";
	/** 星期日 */
	public static final int SUNDAY = Calendar.SUNDAY;
	/** 星期一 */
	public static final int MONDAY = Calendar.MONDAY;
	/** 星期二 */
	public static final int TUESDAY = Calendar.TUESDAY;
	/** 星期三 */
	public static final int WEDNESDAY = Calendar.WEDNESDAY;
	/** 星期四 */
	public static final int THURSDAY = Calendar.THURSDAY;
	/** 星期五 */
	public static final int FRIDAY = Calendar.FRIDAY;
	/** 星期六 */
	public static final int SATURDAY = Calendar.SATURDAY;
	/** 星期数组 */
	private static final String[] DAY_OF_WEEK = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	/*
	 * 获取当前系统时间戳
	 */
	public static long timestampNow() {
		return System.currentTimeMillis() / 1000L;
	}

	/**
	 * <PRE>
	 * 获取当前日期
	 * </PRE>
	 *
	 * @return Date today
	 */
	public static Date getNow() {
		Calendar calendar = new GregorianCalendar();
		return new Date(calendar.getTimeInMillis());
	}

	/**
	 * 获取简单格式的当前日期字符串，形式yyyy-mm-dd
	 * 
	 * @return String
	 */
	public static String getShortNow() {
		String sNow;
		Date now = new Date();
		sNow = dateFormater(now, SHORT_DATE_PATTERN);
		return sNow;
	}

	/**
	 * 获取简单格式的当前日期字符串，形式yyyymmdd
	 * 
	 * @return String
	 */
	public static String getShortNow1() {
		String sNow;
		Calendar calendar = new GregorianCalendar();
		Date now = new Date(calendar.getTimeInMillis());
		sNow = dateFormater(now, SHORT_DATE_PATTERN_1);
		return sNow;
	}

	/**
	 * 获取简单格式的当前日期字符串，形式yyyy-mm-dd hh:mm
	 * 
	 * @return String
	 */
	public static String getLongNow() {
		String sNow;
		Date now = new Date();
		sNow = dateFormater(now, LONG_DATE_PATTERN);
		return sNow;
	}

	/**
	 * 获取简单格式的当前日期字符串，形式yyyy-mm-dd
	 * 
	 * @return String
	 */
	public static String getHMTime() {
		String sNow;
		Date now = new Date();
		sNow = dateFormater(now, SHORT_HH_MM_PATTERN);
		return sNow;
	}

	/**
	 * 根据指定格式，获取指定日期的格式字符串
	 * 
	 * @param date
	 *            指定日期
	 * @param sPattern
	 *            日期格式
	 * @return String
	 */
	public static String dateFormater(Date date, String sPattern) {
		String sDate;
		if (null == date) {
			sDate = "";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(sPattern);
			sDate = sdf.format(date);
		}
		return sDate;
	}

	/**
	 * 根据指定格式，获取指定长日期的格式字符串
	 * 
	 * @param date
	 *            指定日期
	 * @return String
	 */
	public static String shortDateFormater(Date date) {
		String sDate;
		if (null == date) {
			sDate = "";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATE_PATTERN);
			sDate = sdf.format(date);
		}
		return sDate;
	}

	/**
	 * 根据指定格式，获取指定长日期的格式字符串
	 * 
	 * @param date
	 *            指定日期
	 * @return date
	 */
	public static Date shortDate(Date d) {
		Date date;
		if (null == d) {
			date = new Date();
		} else {
			String str = shortDateFormater(d);
			date = parseDate(str);
		}
		return date;
	}

	/**
	 * 根据指定格式，获取指定长日期的格式字符串
	 * 
	 * @param date
	 *            指定日期
	 * @return String
	 */
	public static String longDateFormater(Date date) {
		String sDate;
		if (null == date) {
			sDate = "";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(LONG_DATE_PATTERN);
			sDate = sdf.format(date);
		}
		return sDate;
	}

	/**
	 * 根据指定格式，日期字符串转换成DATE型
	 * 
	 * @param date
	 *            yyyy-mm-dd HH:MI:SS格式的日期字符串
	 * @return Date
	 */
	public static Date getLongDate(String sDate) {
		Date date = null;
		if (sDate.matches(LONG_DATE_REG_HAVE_SS)) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(sDate.split("-")[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(sDate.split("-")[1]) - 1);
			cal.set(Calendar.DATE,
					Integer.parseInt(sDate.substring(sDate.lastIndexOf('-') + 1, sDate.lastIndexOf(' '))));
			cal.set(Calendar.HOUR_OF_DAY,
					Integer.parseInt(sDate.substring(sDate.lastIndexOf(' ') + 1, sDate.indexOf(':'))));
			cal.set(Calendar.MINUTE, Integer.parseInt(sDate.split(":")[1]));
			cal.set(Calendar.SECOND, Integer.parseInt(sDate.split(":")[2]));
			date = cal.getTime();
		} else if (sDate.matches(LONG_DATE_REG_HAVE_MS)) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(sDate.split("-")[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(sDate.split("-")[1]) - 1);
			cal.set(Calendar.DATE,
					Integer.parseInt(sDate.substring(sDate.lastIndexOf('-') + 1, sDate.lastIndexOf(' '))));
			cal.set(Calendar.HOUR_OF_DAY,
					Integer.parseInt(sDate.substring(sDate.lastIndexOf(' ') + 1, sDate.indexOf(':'))));
			cal.set(Calendar.MINUTE, Integer.parseInt(sDate.split(":")[1]));
			cal.set(Calendar.SECOND,
					Integer.parseInt(sDate.substring(sDate.lastIndexOf(':') + 1, sDate.lastIndexOf('.'))));
			date = cal.getTime();
		}
		return date;
	}

	/**
	 * 将yyyy-mm-dd格式的日期字符串转化为日期对象
	 * 
	 * @param sDate
	 *            yyyy-mm-dd格式的日期字符串
	 * @return Date
	 */
	public static Date parseDate(String sDate) {
		if (!sDate.matches(SHORT_DATE_REG)) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(sDate.split("-")[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(sDate.split("-")[1]) - 1);
		cal.set(Calendar.DATE, Integer.parseInt(sDate.split("-")[2]));
		return cal.getTime();
	}

	/**
	 * 将yyyy-mm-dd mm:ss格式的日期字符串转化为日期对象
	 * 
	 * @param sDate
	 *            yyyy-mm-dd mm:ss格式的日期字符串
	 * @return Date
	 */
	public static Date parseLongDate(String sDate) {
		if (!sDate.matches(LONG_DATE_REG)) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(sDate.split("-")[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(sDate.split("-")[1]) - 1);
		cal.set(Calendar.DATE, Integer.parseInt(sDate.substring(sDate.lastIndexOf('-') + 1, sDate.lastIndexOf(' '))));
		cal.set(Calendar.HOUR_OF_DAY,
				Integer.parseInt(sDate.substring(sDate.lastIndexOf(' ') + 1, sDate.lastIndexOf(':'))));
		cal.set(Calendar.MINUTE, Integer.parseInt(sDate.split(":")[1]));
		return cal.getTime();
	}

	/**
	 * 获取制定日期的简单格式字符串，形式yyyy-mm-dd
	 * 
	 * @param date
	 *            指定日期
	 * @return String
	 */
	public static String getShortDate(Date date) {
		String sShortDate;
		if (null == date) {
			sShortDate = "";
		} else {
			sShortDate = dateFormater(date, SHORT_DATE_PATTERN);
		}
		return sShortDate;
	}

	/**
	 * 获取制定日期的简单格式字符串，形式hh:mm
	 * 
	 * @param date
	 *            指定日期
	 * @return String
	 */
	public static String getShortDateHHMM(Date date) {
		String sShortDate;
		if (null == date) {
			sShortDate = "";
		} else {
			sShortDate = dateFormater(date, SHORT_HH_MM_PATTERN);
		}
		return sShortDate;
	}

	/**
	 * 返回指定日期的毫秒值
	 * 
	 * @param date
	 *            指定日期
	 * @return long 毫秒值
	 */
	public static long getMillis(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * 获取当前日期的前一天（昨天）
	 * 
	 * @return Date
	 */
	public static Date getYesterday() {
		Date now = new Date();
		return addDate(now, -1);
	}

	/**
	 * 获取指定日期的前一天
	 * 
	 * @param date
	 *            yyyy-mm-dd格式的日期字符串
	 * @return Date
	 */
	public static Date getYesterday(String date) {
		Date currentDate = parseDate(date);
		return addDate(currentDate, -1);
	}

	/**
	 * 获取指定日期的前一天
	 * 
	 * @param date
	 *            yyyy-mm-dd格式的日期字符串
	 * @return Date
	 */
	public static Date getYesterday(Date date) {
		String sShortDate = "";
		if (null == date) {
			sShortDate = "";
		} else {
			sShortDate = dateFormater(date, SHORT_DATE_PATTERN);
		}

		if (null != date) {
			sShortDate = dateFormater(date, SHORT_DATE_PATTERN);
		}
		Date currentDate = parseDate(sShortDate);
		return addDate(currentDate, -1);
	}

	/**
	 * 获取指定日期加上任意天数之后的日期
	 * 
	 * @param date
	 *            指定日期
	 * @param day
	 *            增加的天数
	 * @return Date
	 */
	public static Date addDate(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) days) * 24 * 3600 * 1000);
		return c.getTime();
	}

	/**
	 * 获取指定日期加上任意天数之后的日期
	 * 
	 * @param date
	 *            指定日期
	 * @param day
	 *            增加的天数
	 * @return Date
	 */
	public static Date addDate(Date date, double days) {
		Calendar c = Calendar.getInstance();
		long lDays = (long) (days * 24 * 3600 * 1000);
		c.setTimeInMillis(getMillis(date) + lDays);
		return c.getTime();
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param dateFrom
	 *            开始日期
	 * @param dateTo
	 *            结束日期
	 * @return int 两个日期之间相差的天数
	 */
	public static int diffDate(Date dateFrom, Date dateTo) {
		return (int) ((getMillis(dateTo) - getMillis(dateFrom)) / (24 * 3600 * 1000));
	}

	/**
	 * 获取两个日期之间的日期
	 * 
	 * @param dateFrom
	 *            开始日期
	 * @param dateTo
	 *            结束日期
	 * @return List 两个日期之间的日期(包括开始日期和结束日期)
	 */
	public static List<String> getDateList(String dateFrom, String dateTo) {
		List<String> lstDate = new ArrayList<>();
		int iDiff = diffDate(parseDate(dateFrom), parseDate(dateTo));
		Date newDate;
		for (int i = 0; i <= iDiff; i++) {
			newDate = addDate(parseDate(dateFrom), i);
			lstDate.add(shortDateFormater(newDate));
		}
		return lstDate;
	}

	/**
	 * 获取当前日期之后一天的日期（明天）
	 * 
	 * @return Date
	 */
	public static Date getTomorrow() {
		Date now = new Date();
		return addDate(now, 1);
	}

	/**
	 * 获取指定日期之后一天的日期
	 * 
	 * @param date
	 *            yyyy-mm-dd格式的日期字符串
	 * @return Date
	 */
	public static Date getTomorrow(String date) {
		Date currentDate = parseDate(date);
		return addDate(currentDate, 1);
	}

	/**
	 * 获取指定日期之后一天的日期
	 * 
	 * @param date
	 *            yyyy-mm-dd mm:ss格式的日期字符串
	 * @return Date
	 */
	public static Date getLongTomorrow(String date) {
		Date currentDate = parseLongDate(date);
		return addDate(currentDate, 1);
	}

	/**
	 * 获取当前年
	 * 
	 * @return String
	 */
	public static String getYear() {
		Date now = new Date();
		return getYear(now);
	}

	/**
	 * 获取指定日期中的年
	 * 
	 * @param date
	 *            指定日期
	 * @return String
	 */
	public static String getYear(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(date);
	}

	/**
	 * 获取当前月
	 * 
	 * @return String
	 */
	public static String getMonth() {
		Date now = new Date();
		return getMonth(now);
	}

	/**
	 * 获取指定日期中的月
	 * 
	 * @param date
	 *            指定日期
	 * @return String
	 */
	public static String getMonth(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		return sdf.format(date);
	}

	/**
	 * 获取当前日
	 * 
	 * @return String
	 */
	public static String getDay() {
		return getDay(new Date());
	}

	/**
	 * 获取指定日期中的日
	 * 
	 * @param date
	 *            指定日期
	 * @return String
	 */
	public static String getDay(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		return sdf.format(date);
	}

	/**
	 * 获取当前日期的年月
	 * 
	 * @return String，格式为yyyy-mm的日期字符串
	 */
	public static String getYearMonth() {
		String year = getYear();
		String month = getMonth();
		return year + "-" + month;
	}

	/**
	 * 获取指定日期的年月
	 * 
	 * @param date
	 *            指定日期
	 * @return String，格式为yyyy-mm的日期字符串
	 */
	public static String getYearMonth(Date date) {
		return dateFormater(date, "yyyy-MM");
	}

	/**
	 * 获取当前日期的下一月
	 * 
	 * @return String，格式为yyyy-mm的日期字符串
	 */
	public static String getNextYearAndMonth() {
		String year = getYear();
		String month = getMonth();
		String date = year + "-" + month;
		String nextDate = null;
		if ("12".equals(date.substring(5, 7))) {
			nextDate = String.valueOf(Integer.parseInt(date.substring(0, 4)) + 1) + "-01";
		} else {
			Integer temp = Integer.parseInt(date.substring(5, 7)) + 1;
			if ((Integer.parseInt(date.substring(5, 6)) + 1) >= 10) {
				nextDate = date.substring(0, 4) + "-" + temp;
			} else {
				nextDate = date.substring(0, 4) + "-0" + temp;
			}
		}
		return nextDate;
	}

	/**
	 * 获取指定日期的下一月
	 * 
	 * @return String，格式为yyyy-mm的日期字符串
	 */
	public static String getNextYearAndMonth(String date) {
		String nextDate = null;
		if ("12".equals(date.substring(5, 7))) {
			nextDate = String.valueOf(Integer.parseInt(date.substring(0, 4)) + 1) + "-01";
		} else {
			int tmp = Integer.parseInt(date.substring(5, 7)) + 1;
			if ((Integer.parseInt(date.substring(5, 6)) + 1) >= 10) {
				nextDate = date.substring(0, 4) + "-" + tmp;
			} else {
				nextDate = date.substring(0, 4) + "-0" + tmp;
			}
		}
		return nextDate;
	}

	/**
	 * 获取当前月的第一天对应的日期，格式为yyyy-mm-dd
	 * 
	 * @return String，格式为yyyy-mm-dd的日期字符串
	 */
	public static String getMonthBegin() {
		Date now = getNow();
		return getMonthBegin(now);
	}

	/**
	 * 获取指定日期所在月份的第一天对应的日期
	 * 
	 * @param date
	 *            指定日期
	 * @return String，短格式的日期字符串
	 */
	public static String getMonthBegin(Date date) {
		String sYearMonth = dateFormater(date, "yyyy-MM");
		return sYearMonth + "-01";
	}

	/**
	 * 获取当前月份中的最后一天的日期
	 * 
	 * @return String，短格式的日期字符串
	 */
	public static String getMonthEnd() {
		Date now = getNow();
		return getMonthEnd(now);
	}

	/**
	 * 获取指定日期所在月份的最后一天对应的日期
	 * 
	 * @param date
	 *            指定日期
	 * @return String，短格式的日期字符串
	 */
	public static String getMonthEnd(Date date) {
		Date monthBegin = parseDate(getMonthBegin(date));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(monthBegin);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return getShortDate(calendar.getTime());
	}

	/**
	 * 获取指定日期所在周的周几的日期字符串
	 * 
	 * @param date
	 *            指定日期
	 * @param day
	 *            基于1的一周中的第几天
	 * @see SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY
	 * @return String 短格式的日期字符串
	 */
	public static String getWeekDate(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, day);
		Date sunday = c.getTime();
		return dateFormater(sunday, SHORT_DATE_PATTERN);
	}

	/**
	 * 获取指定日期对应的星期几
	 * 
	 * @param date
	 *            指定日期
	 * @return String
	 */
	public static String getDayOfWeek(Date date) {
		String sDayOfWeek;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		sDayOfWeek = DAY_OF_WEEK[day - 1];
		return sDayOfWeek;
	}

	/**
	 * 获取指定日期对应的星期几
	 * 
	 * @param date
	 *            指定日期
	 * @return String
	 */
	public static int getDayNumberOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获得某年某月的天数
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @return int
	 */
	public static int getDays(int year, int month) {
		int days = 0;
		switch (month) {
		case 2:
			days = ((0 == year % 4) && (0 != (year % 100))) || (0 == year % 400) ? 29 : 28;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			days = 30;
			break;
		default:
			days = 31;
			break;
		}
		return days;
	}

	/**
	 * 根据日期，对日期进行简单比较
	 * 
	 * @param date1
	 *            日期1
	 * @param date2
	 *            日期2
	 * @return int，1---日期1>日期2；-1---日期1<日期2；0---日期1=日期2
	 */
	public static int dateSimpleCompare(Date date1, Date date2) {
		int iResult = 0;
		int iDiff = diffDate(date1, date2);
		if (iDiff > 0) {
			iResult = 1;
		} else if (iDiff < 0) {
			iResult = -1;
		} else {
			iResult = 0;
		}
		return iResult;
	}

	/**
	 * 根据日期，对字符串代表的短格式日期进行简单比较
	 * 
	 * @param date1
	 *            日期1
	 * @param date2
	 *            日期2
	 * @return int，1---日期1>日期2；-1---日期1<日期2；0---日期1=日期2
	 */
	public static int dateSimpleCompare(String sDate1, String sDate2) {
		Date date1 = parseDate(sDate1);
		Date date2 = parseDate(sDate2);
		return dateSimpleCompare(date1, date2);
	}

	/**
	 * 根据日期，对日期进行复杂比较，精确到毫秒
	 * 
	 * @param date1
	 *            日期1
	 * @param date2
	 *            日期2
	 * @return int，1---日期1>日期2；-1---日期1<日期2；0---日期1=日期2
	 */
	public static int dateComplexCompare(Date date1, Date date2) {
		int iResult = 0;
		boolean bBefore = date1.before(date2);
		boolean bAfter = date1.after(date2);
		if (bBefore) {
			iResult = 1;
		} else if (bAfter) {
			iResult = -1;
		} else {
			iResult = 0;
		}
		return iResult;
	}

	/**
	 * 根据日期，对日期进行复杂比较，精确到毫秒
	 * 
	 * @param date1
	 *            日期1
	 * @param date2
	 *            日期2
	 * @return int，1---日期1>日期2；-1---日期1<日期2；0---日期1=日期2
	 */
	public static int dateComplexCompare(String sDate1, String sDate2) {
		Date date1 = parseDate(sDate1);
		Date date2 = parseDate(sDate2);
		if (null != date1 && null != date2) {
			return dateComplexCompare(date1, date2);
		} else if (null == date1 && null != date2) {
			return -1;
		}
		return 1;
	}

	/**
	 * 判断一个字符串是否代表一个符合指定格式的正确日期
	 * 
	 * @param sDate
	 *            日期格式字符串
	 * @param sPattern
	 *            日期格式
	 * @return true---是日期字符串,false---不是日期字符串
	 */
	public static boolean isDate(String sDate, String sPattern) {
		boolean bIsDate = false;

		try {
			if ((sDate != null) && (sDate.length() > 0)) {
				SimpleDateFormat fmt = new SimpleDateFormat(sPattern);
				Date dtCheck = fmt.parse(sDate);

				String sCheck = fmt.format(dtCheck);

				if (sDate.equals(sCheck)) {
					bIsDate = true;
				}
			}
		} catch (ParseException e) {
			bIsDate = false;
		}

		return bIsDate;
	}

	/**
	 * 判断一个字符串是否代表一个正确日期
	 * 
	 * @param sDate
	 *            日期格式字符串
	 * @return true---是日期字符串,false---不是日期字符串
	 */
	public static boolean isDate(String sDate) {
		List<String> dateFormatList = new ArrayList<>();
		dateFormatList.add("yyyy/MM/dd");
		dateFormatList.add("yyyy/M/dd");
		dateFormatList.add("yyyy/M/d");
		dateFormatList.add("yyyy/MM/d");
		dateFormatList.add("yyyy-MM-dd");
		dateFormatList.add("yyyy-M-dd");
		dateFormatList.add("yyyy-M-d");
		dateFormatList.add("yyyy-MM-d");
		for (String format : dateFormatList) {
			if (isDate(sDate, format)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 获取当前时间完整字符串
	 * 
	 * @return String
	 */
	public static String getNowDateTimeString() {
		Date currentTime = getNow();
		return dateFormater(currentTime, "yyyyMMddHHmmss");
	}

	/**
	 * 获取当前时间完整字符串
	 * 
	 * @return String
	 */
	public static String getNowDateTimeAllString() {
		Date currentTime = getNow();
		return dateFormater(currentTime, LONG_ALL_DATE_PATTERN);
	}

	/**
	 * 获取指定日期i个月后的日期
	 * 
	 * @param date
	 *            指定日期
	 * @return String，短格式的日期字符串
	 */
	public static Date getNextMonth(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, i);
		return calendar.getTime();
	}

	/**
	 * 时间换算String(yyyy-MM-dd HH:mm:ss)-> 时间戳
	 * 
	 * @param time
	 * @throws NoSuchAlgorithmException,UnsupportedEncodingException
	 * @author james.wu
	 */
	public static long timeFormat(String time) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = format.parse(time);
		return date.getTime() / 1000;
	}

	/**
	 * 获取当前系统时间的时间戳
	 * 
	 * @param time
	 * @throws NoSuchAlgorithmException,UnsupportedEncodingException
	 * @author james.wu
	 */
	public static Long getNowLong() throws Exception {
		Date now = new Date();
		return now.getTime() / 1000;
	}

	/**
	 * UTC获取MM/dd/yyyy格式时间
	 * 
	 * @param time
	 * @throws NoSuchAlgorithmException,UnsupportedEncodingException
	 * @author james.wu
	 */
	public static String formateUTC(long utcTime) {
		Long timestamp = utcTime * 1000;
		return new SimpleDateFormat(SHORT_DATE_PATTERN_2).format(new Date(timestamp));
	}
}
