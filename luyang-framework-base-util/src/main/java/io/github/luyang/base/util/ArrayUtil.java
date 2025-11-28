package io.github.luyang.base.util;

import java.util.Arrays;

/**
 * 数组工具类
 *
 * @author yang.lu
 */
public class ArrayUtil {

	/**
	 * 空字符串数组常量
	 */
	public static final String[] EMPTY_STRING_ARRAY = {};

	private ArrayUtil() {
	}

	/**
	 * 检查数组是否为空（null 或长度为 0）
	 *
	 * @param array 待检查的数组
	 * @return 如果数组为空则返回 true，否则返回 false
	 * @author yang.lu
	 */
	public static <T> boolean isEmpty(T[] array) {
		return null == array || array.length == 0;
	}

	/**
	 * 检查数组是否不为空（非 null 且长度大于 0）
	 *
	 * @param array 待检查的数组
	 * @return 如果数组不为空则返回 true，否则返回 false
	 * @author yang.lu
	 */
	public static <T> boolean isNotEmpty(T[] array) {
		return !isEmpty(array);
	}

	/**
	 * 检查对象是否为数组
	 *
	 * @param obj 待检查的对象
	 * @return 如果对象是数组则返回 true，否则返回 false
	 * @author yang.lu
	 */
	public static boolean isArray(Object obj) {
		return null != obj && obj.getClass().isArray();
	}

	/**
	 * 将数组转换为字符串表示形式
	 *
	 * @param obj 数组对象
	 * @return 数组的字符串表示，如果对象为 null 则返回 null
	 * @author yang.lu
	 */
	public static String toString(Object obj) {
		if (null == obj) {
			return null;
		}

		return switch (obj) {
			case long[] array -> Arrays.toString(array);
			case int[] array -> Arrays.toString(array);
			case short[] array -> Arrays.toString(array);
			case char[] array -> Arrays.toString(array);
			case byte[] array -> Arrays.toString(array);
			case boolean[] array -> Arrays.toString(array);
			case float[] array -> Arrays.toString(array);
			case double[] array -> Arrays.toString(array);
			case Object[] array -> Arrays.deepToString(array);
			default -> obj.toString();
		};
	}
}
