package com.luyang.framework.base.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 日期工具类，提供日期和时间处理的静态方法。
 *
 * @author wangjixin
 */

public final class LocalDateTimeUtils {

	private LocalDateTimeUtils() {
		throw new AssertionError("禁止实例化工具类");
	}

	// 常用日期时间格式
	public static final String FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";
	public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_HOUR_MINUTE_SECOND = "HH:mm:ss";

	/**
	 * 将 Date 转换为 LocalDateTime
	 *
	 * @param date 要转换的 Date 对象
	 * @return 转换后的 LocalDateTime 对象
	 */
	public static LocalDateTime dateToLocalDateTime(Date date) {
		if (null == date) {
			return null;
		}
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		return instant.atZone(zoneId).toLocalDateTime();
	}

	/**
	 * 将 LocalDateTime 转换为 Date
	 *
	 * @param localDateTime 要转换的 LocalDateTime 对象
	 * @return 转换后的 Date 对象
	 */
	public static Date localDateTimeToDate(LocalDateTime localDateTime) {
		if (null == localDateTime) {
			return null;
		}
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zdt = localDateTime.atZone(zoneId);
		return Date.from(zdt.toInstant());
	}

	/**
	 * 将 LocalDateTime 格式化为字符串
	 *
	 * @param localDateTime 要格式化的 LocalDateTime 对象
	 * @param pattern       日期时间格式，如 "yyyy-MM-dd HH:mm:ss"
	 * @return 格式化后的字符串
	 */
	public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
		if (null == localDateTime) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return localDateTime.format(formatter);
	}

	/**
	 * 将字符串解析为 LocalDateTime
	 *
	 * @param dateTimeStr 要解析的日期时间字符串
	 * @param pattern     日期时间格式，如 "yyyy-MM-dd HH:mm:ss"
	 * @return 解析后的 LocalDateTime 对象
	 */
	public static LocalDateTime parseLocalDateTime(String dateTimeStr, String pattern) {
		if (null == dateTimeStr) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.parse(dateTimeStr, formatter);
	}

	/**
	 * 将 LocalDate 格式化为字符串
	 *
	 * @param localDate 要格式化的 LocalDate 对象
	 * @param pattern   日期格式，如 "yyyy-MM-dd"
	 * @return 格式化后的字符串
	 */
	public static String formatLocalDate(LocalDate localDate, String pattern) {
		if (null == localDate) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return localDate.format(formatter);
	}

	/**
	 * 将字符串解析为 LocalDate
	 *
	 * @param dateStr 要解析的日期字符串
	 * @param pattern 日期格式，如 "yyyy-MM-dd"
	 * @return 解析后的 LocalDate 对象
	 */
	public static LocalDate parseLocalDate(String dateStr, String pattern) {
		if (null == dateStr) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDate.parse(dateStr, formatter);
	}

	/**
	 * 将 LocalTime 格式化为字符串
	 *
	 * @param localTime 要格式化的 LocalTime 对象
	 * @param pattern   时间格式，如 "HH:mm:ss"
	 * @return 格式化后的字符串
	 */
	public static String formatLocalTime(LocalTime localTime, String pattern) {
		if (null == localTime) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return localTime.format(formatter);
	}

	/**
	 * 将字符串解析为 LocalTime
	 *
	 * @param timeStr 要解析的时间字符串
	 * @param pattern 时间格式，如 "HH:mm:ss"
	 * @return 解析后的 LocalTime 对象
	 */
	public static LocalTime parseLocalTime(String timeStr, String pattern) {
		if (null == timeStr) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalTime.parse(timeStr, formatter);
	}

	/**
	 * 获取当前的 LocalDateTime
	 *
	 * @return 当前的 LocalDateTime 对象
	 */
	public static LocalDateTime getCurrentLocalDateTime() {
		return LocalDateTime.now();
	}

	/**
	 * 在指定的 LocalDateTime 上增加指定的天数
	 *
	 * @param localDateTime 要操作的 LocalDateTime 对象
	 * @param days          要增加的天数
	 * @return 增加天数后的 LocalDateTime 对象
	 */
	public static LocalDateTime plusDays(LocalDateTime localDateTime, long days) {
		if (null == localDateTime) {
			return null;
		}
		return localDateTime.plusDays(days);
	}

	/**
	 * 在指定的 LocalDateTime 上减少指定的天数
	 *
	 * @param localDateTime 要操作的 LocalDateTime 对象
	 * @param days          要减少的天数
	 * @return 减少天数后的 LocalDateTime 对象
	 */
	public static LocalDateTime minusDays(LocalDateTime localDateTime, long days) {
		if (null == localDateTime) {
			return null;
		}
		return localDateTime.minusDays(days);
	}

	/**
	 * 计算两个 LocalDateTime 之间相差的天数
	 *
	 * @param start 开始的 LocalDateTime
	 * @param end   结束的 LocalDateTime
	 * @return 相差的天数
	 */
	public static long daysBetween(LocalDateTime start, LocalDateTime end) {
		if (null == start || null == end) {
			return 0;
		}
		LocalDate startDate = start.toLocalDate();
		LocalDate endDate = end.toLocalDate();
		return ChronoUnit.DAYS.between(startDate, endDate);
	}

	/**
	 * 获取指定日期的开始时间（00:00:00）
	 *
	 * @param localDateTime 指定的 LocalDateTime
	 * @return 指定日期的开始时间
	 */
	public static LocalDateTime getStartOfDay(LocalDateTime localDateTime) {
		if (null == localDateTime) {
			return null;
		}
		return localDateTime.with(LocalTime.MIN);
	}

	/**
	 * 获取指定日期的结束时间（23:59:59）
	 *
	 * @param localDateTime 指定的 LocalDateTime
	 * @return 指定日期的结束时间
	 */
	public static LocalDateTime getEndOfDay(LocalDateTime localDateTime) {
		if (null == localDateTime) {
			return null;
		}
		return localDateTime.with(LocalTime.MAX);
	}

	/**
	 * 判断指定日期是否为闰年
	 *
	 * @param localDateTime 指定的 LocalDateTime
	 * @return 如果是闰年返回 true，否则返回 false
	 */
	public static boolean isLeapYear(LocalDateTime localDateTime) {
		if (null == localDateTime) {
			return false;
		}
		return localDateTime.toLocalDate().isLeapYear();
	}

	/**
	 * 获取指定日期所在月份的总天数
	 *
	 * @param localDateTime 指定的 LocalDateTime
	 * @return 指定日期所在月份的总天数
	 */
	public static int lengthOfMonth(LocalDateTime localDateTime) {
		if (null == localDateTime) {
			return 0;
		}
		return localDateTime.toLocalDate().lengthOfMonth();
	}
}
