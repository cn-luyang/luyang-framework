package com.luyang.framework.base.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志工具类，提供简单的日志记录功能。
 * @author wangjixin
 */
public final class LogUtils {

	private LogUtils() {
		throw new AssertionError("禁止实例化工具类");
	}

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 记录信息级别的日志。
	 *
	 * @param message 日志消息
	 */
	public static void info(String message) {
		log("INFO", message);
	}

	/**
	 * 记录警告级别的日志。
	 *
	 * @param message 日志消息
	 */
	public static void warn(String message) {
		log("WARN", message);
	}

	/**
	 * 记录错误级别的日志。
	 *
	 * @param message 日志消息
	 */
	public static void error(String message) {
		log("ERROR", message);
	}

	private static void log(String level, String message) {
		String timestamp = DATE_FORMAT.format(new Date());
		System.out.printf("[%s] [%s] %s%n", timestamp, level, message);
	}
}
