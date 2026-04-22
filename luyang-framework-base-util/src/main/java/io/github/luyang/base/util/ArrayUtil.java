package io.github.luyang.base.util;

import java.util.Arrays;

/**
 * 数组工具类
 *
 * @author yang.lu
 */
public final class ArrayUtil {

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
	 * 检查数组中是否包含 null 元素
	 *
	 * @param <T>   数组元素类型
	 * @param array 待检查的数组
	 * @return 如果包含 null 元素或数组本身为 null 返回 true
	 * @author yang.lu
	 */
	@SafeVarargs
	public static <T> boolean hasNull(T... array) {
		if (array == null) {
			return true;
		}

		for (T element : array) {
			if (null == element) {
				return true;
			}
		}
		return false;
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
			case long[] a -> Arrays.toString(a);
			case int[] a -> Arrays.toString(a);
			case short[] a -> Arrays.toString(a);
			case char[] a -> Arrays.toString(a);
			case byte[] a -> Arrays.toString(a);
			case boolean[] a -> Arrays.toString(a);
			case float[] a -> Arrays.toString(a);
			case double[] a -> Arrays.toString(a);
			case Object[] a -> Arrays.deepToString(a);
			case String s -> s;
			default -> obj.toString();
		};
	}
}
