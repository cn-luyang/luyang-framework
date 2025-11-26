package io.github.luyang.base.util;

import java.util.Collection;
import java.util.Iterator;

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
	 * 使用指定的分隔符连接集合中的元素
	 * <pre>
	 *     join(Arrays.asList("a", "b", "c"), ",")  	= "a,b,c"
	 * </pre>
	 *
	 * @param <T>       集合元素类型
	 * @param coll      要连接的集合，如果为null或空则返回null
	 * @param delimiter 元素之间的分隔符，如果为null则视为空字符串
	 * @return 连接后的字符串，如果集合为null或空则返回null
	 * @author yang.lu
	 */
	public static <T> String join(Collection<T> coll, CharSequence delimiter) {
		if (isEmpty(coll)) {
			return null;
		}

		return join(coll, delimiter, StrUtil.EMPTY, StrUtil.EMPTY);
	}

	/**
	 * 使用指定的分隔符、前缀和后缀连接集合中的元素
	 * 示例：
	 * <pre>
	 *     join(Arrays.asList("a", "b", "c"), ",","[", "]") 	= "[a],[b],[c]"
	 * </pre>
	 *
	 * @param <T>       集合元素类型
	 * @param coll      要连接的集合，如果为null或空则返回空字符串
	 * @param delimiter 元素之间的分隔符，如果为null则视为空字符串
	 * @param prefix    每个元素的前缀，如果为null则视为空字符串
	 * @param suffix    每个元素的后缀，如果为null则视为空字符串
	 * @return 连接后的字符串，如果集合为null或空则返回空字符串
	 * @author yang.lu
	 */
	public static <T> String join(Collection<T> coll, CharSequence delimiter, CharSequence prefix, CharSequence suffix) {

		if (isEmpty(coll)) {
			return StrUtil.EMPTY;
		}

		delimiter = StrUtil.blankToDefault(delimiter, StrUtil.EMPTY);
		prefix = StrUtil.blankToDefault(prefix, StrUtil.EMPTY);
		suffix = StrUtil.blankToDefault(suffix, StrUtil.EMPTY);

		int totalLength = coll.size() * (prefix.length() + suffix.length()) + (coll.size() - 1) * delimiter.length();
		for (Object element : coll) {
			totalLength += String.valueOf(element).length();
		}

		StringBuilder builder = new StringBuilder(totalLength);
		Iterator<?> it = coll.iterator();
		while (it.hasNext()) {
			builder.append(prefix)
				.append(it.next())
				.append(suffix);
			if (it.hasNext()) {
				builder.append(delimiter);
			}
		}
		return builder.toString();
	}
}
