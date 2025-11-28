package io.github.luyang.base.util;

import java.util.Objects;

/**
 * 对象工具类
 *
 * @author yang.lu
 */
public final class ObjectUtil {

	private ObjectUtil() {
	}

	/**
	 * 检查对象是否为 null
	 *
	 * @param obj 待检查的对象
	 * @return 如果对象为 null，则返回 true，否则返回 false
	 * @author yang.lu
	 */
	public static boolean isNull(Object obj) {
		return obj == null;
	}

	/**
	 * 检查对象是否不为 null
	 *
	 * @param obj 待检查的对象
	 * @return 如果对象不为 null，则返回 true，否则返回 false
	 * @author yang.lu
	 */
	public static boolean isNotNull(Object obj) {
		return !isNull(obj);
	}

	/**
	 * 对象为 null 时返回默认值
	 *
	 * @param <T>          对象类型
	 * @param object       待检查的对象
	 * @param defaultValue 默认值
	 * @return 如果对象为 null，则返回默认值，否则返回对象本身
	 * @author yang.lu
	 */
	public static <T> T defaultIfNull(final T object, final T defaultValue) {
		return isNull(object) ? defaultValue : object;
	}

	/**
	 * 检查两个对象是否相等。
	 *
	 * @param obj1 第一个对象
	 * @param obj2 第二个对象
	 * @return 如果相等则返回 true，否则返回 false
	 * @author yang.lu
	 */
	public static boolean equals(Object obj1, Object obj2) {
		return equal(obj1, obj2);
	}

	/**
	 * 比较两个对象是否相等
	 * 自动处理 Number 类型的特殊比较逻辑（支持 BigDecimal 的数值比较）
	 *
	 * @param obj1 第一个对象，可为 null
	 * @param obj2 第二个对象，可为 null
	 * @return 如果两个对象相等返回 true，否则返回 false
	 * @author yang.lu
	 */
	public static boolean equal(Object obj1, Object obj2) {
		if (obj1 instanceof Number && obj2 instanceof Number) {
			return NumberUtil.equals((Number) obj1, (Number) obj2);
		}
		return Objects.equals(obj1, obj2);
	}

	/**
	 * 将对象转换为字符串，如果为 null 返回空字符串。
	 *
	 * @param obj 对象
	 * @return 字符串表示
	 * @author yang.lu
	 */
	public static String toString(Object obj) {
		return Objects.toString(obj, StrUtil.EMPTY);
	}
}
