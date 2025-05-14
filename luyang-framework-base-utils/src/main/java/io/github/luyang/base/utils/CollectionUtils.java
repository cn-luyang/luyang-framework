package io.github.luyang.base.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 集合工具类，提供了一系列对集合进行操作的静态方法。
 *
 * @author wangjixin
 */
public final class CollectionUtils {

	private CollectionUtils() {
		throw new AssertionError("禁止实例化工具类");
	}

	/**
	 * 判断集合是否为空。
	 *
	 * @param collection 要判断的集合
	 * @return 如果集合为 null 或者集合中没有元素，返回 true；否则返回 false
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return null == collection || collection.isEmpty();
	}

	/**
	 * 判断集合是否不为空。
	 *
	 * @param collection 要判断的集合
	 * @return 如果集合不为 null 并且集合中有元素，返回 true；否则返回 false
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	/**
	 * 判断 Map 是否为空。
	 *
	 * @param map 要判断的 Map
	 * @return 如果 Map 为 null 或者 Map 中没有键值对，返回 true；否则返回 false
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return null == map || map.isEmpty();
	}

	/**
	 * 判断 Map 是否不为空。
	 *
	 * @param map 要判断的 Map
	 * @return 如果 Map 不为 null 并且 Map 中有键值对，返回 true；否则返回 false
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	/**
	 * 合并多个集合为一个新的集合。
	 *
	 * @param collections 要合并的多个集合
	 * @param <T>         集合中元素的类型
	 * @return 合并后的新集合
	 */
	@SafeVarargs
	public static <T> Collection<T> mergeCollections(Collection<T>... collections) {
		List<T> mergedList = new ArrayList<>();
		if (null == collections) {
			return mergedList;
		}
		for (Collection<T> collection : collections) {
			if (isNotEmpty(collection)) {
				mergedList.addAll(collection);
			}
		}
		return mergedList;
	}

	/**
	 * 获取集合的大小。
	 *
	 * @param collection 要获取大小的集合
	 * @return 如果集合为 null，返回 0；否则返回集合的大小
	 */
	public static int size(Collection<?> collection) {
		return null == collection ? 0 : collection.size();
	}

	/**
	 * 获取 Map 的大小。
	 *
	 * @param map 要获取大小的 Map
	 * @return 如果 Map 为 null，返回 0；否则返回 Map 中键值对的数量
	 */
	public static int size(Map<?, ?> map) {
		return null == map ? 0 : map.size();
	}

	/**
	 * 判断集合中是否包含指定元素。
	 *
	 * @param collection 要检查的集合
	 * @param element    要查找的元素
	 * @param <T>        集合中元素的类型
	 * @return 如果集合不为 null 并且包含指定元素，返回 true；否则返回 false
	 */
	public static <T> boolean contains(Collection<T> collection, T element) {
		return isNotEmpty(collection) && collection.contains(element);
	}

	/**
	 * 反转列表中的元素顺序。
	 *
	 * @param list 要反转的列表
	 * @param <T>  列表中元素的类型
	 */
	public static <T> void reverse(List<T> list) {
		if (isNotEmpty(list)) {
			Collections.reverse(list);
		}
	}

	/**
	 * 对列表进行排序。
	 *
	 * @param list 要排序的列表
	 * @param <T>  列表中元素的类型，该类型必须实现 Comparable 接口
	 */
	public static <T extends Comparable<? super T>> void sort(List<T> list) {
		if (isNotEmpty(list)) {
			Collections.sort(list);
		}
	}

	/**
	 * 对列表进行自定义排序。
	 *
	 * @param list       要排序的列表
	 * @param comparator 自定义的比较器
	 * @param <T>        列表中元素的类型
	 */
	public static <T> void sort(List<T> list, Comparator<? super T> comparator) {
		if (isNotEmpty(list)) {
			list.sort(comparator);
		}
	}

	/**
	 * 将 List 转换为 Set。
	 *
	 * @param list 要转换的 List
	 * @param <T>  列表中元素的类型
	 * @return 转换后的 Set，如果输入列表为 null，则返回空的 HashSet
	 */
	public static <T> Set<T> listToSet(List<T> list) {
		return isNotEmpty(list) ? new HashSet<>(list) : new HashSet<>();
	}

	/**
	 * 将 Set 转换为 List。
	 *
	 * @param set 要转换的 Set
	 * @param <T> 集合中元素的类型
	 * @return 转换后的 List，如果输入集合为 null，则返回空的 ArrayList
	 */
	public static <T> List<T> setToList(Set<T> set) {
		return isNotEmpty(set) ? new ArrayList<>(set) : new ArrayList<>();
	}

	/**
	 * 将 Map 的键转换为 Set。
	 *
	 * @param map 要转换的 Map
	 * @param <K> 键的类型
	 * @param <V> 值的类型
	 * @return 包含 Map 键的 Set，如果输入 Map 为 null，则返回空的 HashSet
	 */
	public static <K, V> Set<K> mapKeysToSet(Map<K, V> map) {
		return isNotEmpty(map) ? map.keySet() : new HashSet<>();
	}

	/**
	 * 将 Map 的值转换为 List。
	 *
	 * @param map 要转换的 Map
	 * @param <K> 键的类型
	 * @param <V> 值的类型
	 * @return 包含 Map 值的 List，如果输入 Map 为 null，则返回空的 ArrayList
	 */
	public static <K, V> List<V> mapValuesToList(Map<K, V> map) {
		return isNotEmpty(map) ? new ArrayList<>(map.values()) : new ArrayList<>();
	}

	/**
	 * 将 List 转换为 Map，使用元素作为键，默认值为 null。
	 *
	 * @param list 要转换的 List
	 * @param <K>  键的类型
	 * @return 转换后的 Map，如果输入列表为 null，则返回空的 HashMap
	 */
	public static <K> Map<K, Object> listToMap(List<K> list) {
		return isNotEmpty(list) ? list.stream().collect(Collectors.toMap(k -> k, k -> null)) : new HashMap<>(16);
	}

	/**
	 * 过滤集合中的元素。
	 *
	 * @param collection 要过滤的集合
	 * @param predicate  过滤条件
	 * @param <T>        集合中元素的类型
	 * @return 过滤后的集合，如果输入集合为 null，则返回空的 ArrayList
	 */
	public static <T> Collection<T> filter(Collection<T> collection, java.util.function.Predicate<T> predicate) {
		return isNotEmpty(collection) ? collection.stream().filter(predicate).collect(Collectors.toList()) : new ArrayList<>();
	}

	/**
	 * 对集合中的元素进行映射转换。
	 *
	 * @param collection 要转换的集合
	 * @param mapper     映射函数
	 * @param <T>        原集合中元素的类型
	 * @param <R>        转换后集合中元素的类型
	 * @return 转换后的集合，如果输入集合为 null，则返回空的 ArrayList
	 */
	public static <T, R> Collection<R> map(Collection<T> collection, java.util.function.Function<T, R> mapper) {
		return isNotEmpty(collection) ? collection.stream().map(mapper).collect(Collectors.toList()) : new ArrayList<>();
	}

	/**
	 * 对集合中的元素进行归约操作。
	 *
	 * @param collection  要归约的集合
	 * @param identity    初始值
	 * @param accumulator 累加器函数
	 * @param <T>         集合中元素的类型
	 * @return 归约后的结果
	 */
	public static <T> T reduce(Collection<T> collection, T identity, java.util.function.BinaryOperator<T> accumulator) {
		return isNotEmpty(collection) ? collection.stream().reduce(identity, accumulator) : identity;
	}
}
