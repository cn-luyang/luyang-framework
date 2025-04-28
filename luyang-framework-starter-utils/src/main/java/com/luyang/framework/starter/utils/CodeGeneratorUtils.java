package com.luyang.framework.starter.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 编码规则生成的工具类,AtomicInteger原子性递增，保证全局唯一性
 *
 * @author wangjixin
 */
public final class CodeGeneratorUtils {

	private CodeGeneratorUtils() {
		throw new AssertionError("禁止实例化工具类");
	}

	// 日期时间格式
	public static final String DEFAULT_DATE_FORMAT = "yyyyMMddHHmmss";
	// 默认流水号长度
	public static final int DEFAULT_SEQUENCE_LENGTH = 4;

	// 原子整数，用于生成流水号，保证并发安全
	private static final AtomicInteger SEQUENCE = new AtomicInteger(0);

	/**
	 * 生成编码，使用默认日期格式和流水号长度
	 *
	 * @param prefix 编码前缀
	 * @return 生成的编码
	 */
	public static String generateCode(String prefix) {
		return generateCode(prefix, DEFAULT_DATE_FORMAT, DEFAULT_SEQUENCE_LENGTH);
	}

	/**
	 * 生成编码，可指定日期格式和流水号长度
	 *
	 * @param prefix         编码前缀
	 * @param dateFormat     日期格式
	 * @param sequenceLength 流水号长度
	 * @return 生成的编码
	 */
	public static String generateCode(String prefix, String dateFormat, int sequenceLength) {
		if (null == prefix) {
			prefix = "";
		}
		if (null == dateFormat) {
			dateFormat = DEFAULT_DATE_FORMAT;
		}
		if (sequenceLength < 1) {
			sequenceLength = DEFAULT_SEQUENCE_LENGTH;
		}

		// 获取当前日期时间并格式化
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
		String datePart = now.format(formatter);

		// 获取流水号
		int currentSequence = SEQUENCE.getAndIncrement();
		String sequencePart = String.format("%0" + sequenceLength + "d", currentSequence);

		// 拼接编码
		return prefix + datePart + sequencePart;
	}
}
