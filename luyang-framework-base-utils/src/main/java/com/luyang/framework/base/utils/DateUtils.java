package com.luyang.framework.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类，提供日期和时间处理的静态方法。
 * @author wangjixin
 */
public final class DateUtils {

	private DateUtils() {
		throw new AssertionError("禁止实例化工具类");
	}

	/**
	 * 将日期对象格式化为指定格式的字符串。
	 * @param date 要格式化的日期对象
	 * @param format 日期格式，如 "yyyy-MM-dd"
	 * @return 格式化后的日期字符串
	 */
	public static String formatDate(Date date, String format) {
		if (null == date || null == format) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 将日期字符串解析为日期对象。
	 * @param dateStr 日期字符串
	 * @param format 日期格式，如 "yyyy-MM-dd"
	 * @return 解析后的日期对象，如果解析失败返回 null
	 */
	public static Date parseDate(String dateStr, String format) {
		if (null == dateStr || null == format) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			System.err.println("日期解析失败: " + e.getMessage());
			return null;
		}
	}

	/**
	 * 获取指定日期加上指定天数后的日期。
	 * @param date 原始日期
	 * @param days 要添加的天数
	 * @return 计算后的日期对象
	 */
	public static Date addDays(Date date, int days) {
		if (null == date) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}

	/**
	 * 获取两个日期之间的天数差。
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return 天数差
	 */
	public static long getDaysDifference(Date startDate, Date endDate) {
		if (null == startDate || null == endDate) {
			return 0;
		}
		long diff = endDate.getTime() - startDate.getTime();
		return diff / (24 * 60 * 60 * 1000);
	}
}
