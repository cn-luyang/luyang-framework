package io.github.luyang.base.util;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 集合相关工具类
 *
 * @author yang.lu
 */
public class CollUtil {

	/**
	 * 检查集合是否为空
	 *
	 * @param collection 待检查的集合
	 * @return 如果集合为null或空，则返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	/**
	 * 检查集合是否不为空
	 *
	 * @param collection 待检查的集合
	 * @return 如果集合不为null且不为空，则返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	/**
	 * 将集合元素以指定分隔符拼成字符串，跳过 null。
	 *
	 * @param collection 任意元素集合
	 * @param delimiter  分隔符，例如 " and "、" & "、", "
	 * @param <T>        元素类型
	 * @return 拼接后的字符串；集合为 empty 时返回 ""
	 * @author yang.lu
	 */
	public static <T> String join(Collection<T> collection, String delimiter) {
		if (collection == null || collection.isEmpty()) {
			return "";
		}
		return collection.stream()
			.filter(Objects::nonNull)
			.map(String::valueOf)
			.collect(Collectors.joining(delimiter));
	}
}
