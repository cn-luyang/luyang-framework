package io.github.luyang.base.util;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 数字工具类
 *
 * @author yang.lu
 */
public final class NumberUtil {

	private NumberUtil() {
	}

	/**
	 * 比较两个数字是否相等
	 * 自动处理 BigDecimal 的特殊比较逻辑，避免精度位数导致的误判
	 *
	 * @param num1 第一个数字，可为 null
	 * @param num2 第二个数字，可为 null
	 * @return 如果两个数字相等（BigDecimal 使用 compareTo 比较）返回 true，否则返回 false
	 * @author yang.lu
	 */
	public static boolean equals(Number num1, Number num2) {
		// 同一对象或都为 null
		if (Objects.equals(num1, num2)) {
			return true;
		}

		// 任一为 null 或类型不同时的快速判断
		if (num1 == null || num2 == null) {
			return false;
		}

		// 特殊处理 BigDecimal 比较
		if (num1 instanceof BigDecimal && num2 instanceof BigDecimal) {
			return equals((BigDecimal) num1, (BigDecimal) num2);
		}

		// 其他 Number 类型使用标准 equals 比较
		return num1.equals(num2);
	}

	/**
	 * 比较两个 BigDecimal 是否数值相等
	 * 使用 compareTo 方法比较，忽略精度和小数位数的差异（例如 2.0 和 2.00 视为相等）
	 *
	 * @param num1 第一个 BigDecimal，可为 null
	 * @param num2 第二个 BigDecimal，可为 null
	 * @return 如果数值相等返回 true，否则返回 false
	 * @author yang.lu
	 */
	public static boolean equals(BigDecimal num1, BigDecimal num2) {
		// 快速路径：同一对象或都为 null
		if (Objects.equals(num1, num2)) {
			return true;
		}

		// 任一为 null 时不等
		if (num1 == null || num2 == null) {
			return false;
		}

		// 使用 compareTo 比较数值，忽略精度差异
		return num1.compareTo(num2) == 0;
	}
}
