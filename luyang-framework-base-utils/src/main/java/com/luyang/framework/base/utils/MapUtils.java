package com.luyang.framework.base.utils;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Map工具类，提供了一系列对Map进行操作的静态方法。
 * @author wangjixin
 */
public final class MapUtils {

	private MapUtils() {
		throw new AssertionError("禁止实例化工具类");
	}

	// ================== 空值判断 ================== //

	/**
	 * 判断Map是否为空。
	 * @param map 要检查的Map
	 * @param <K> Map键的类型
	 * @param <V> Map值的类型
	 * @return 如果Map为null或者不包含任何键值对，返回true；否则返回false
	 */
	public static <K, V> boolean isEmpty(Map<K, V> map) {
		return null == map || map.isEmpty();
	}

	/**
	 * 判断Map是否不为空。
	 * @param map 要检查的Map
	 * @param <K> Map键的类型
	 * @param <V> Map值的类型
	 * @return 如果Map不为null且包含至少一个键值对，返回true；否则返回false
	 */
	public static <K, V> boolean isNotEmpty(Map<K, V> map) {
		return !isEmpty(map);
	}

	// ================== 合并Map ================== //

	/**
	 * 合并多个Map为一个Map。
	 * @param maps 要合并的Map数组
	 * @param <K> Map键的类型
	 * @param <V> Map值的类型
	 * @return 合并后的Map
	 */
	@SafeVarargs
	public static <K, V> Map<K, V> merge(Map<K, V>... maps) {
		Map<K, V> result = new HashMap<>();
		for (Map<K, V> map : maps) {
			if (isNotEmpty(map)) {
				result.putAll(map);
			}
		}
		return result;
	}

	// ================== 筛选Map ================== //

	/**
	 * 根据指定条件筛选Map中的键值对。
	 * @param map 要筛选的Map
	 * @param predicate 筛选条件，接收键和值作为参数
	 * @param <K> Map键的类型
	 * @param <V> Map值的类型
	 * @return 包含满足条件键值对的新Map
	 */
	public static <K, V> Map<K, V> filter(Map<K, V> map, BiPredicate<K, V> predicate) {
		if (isEmpty(map)) {
			return new HashMap<>();
		}
		return map.entrySet().stream()
			.filter(entry -> predicate.test(entry.getKey(), entry.getValue()))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	// ================== 映射Map的值 ================== //

	/**
	 * 对Map中的每个值应用映射函数。
	 * @param map 要进行映射的Map
	 * @param mapper 映射函数，接收值作为参数并返回新的值
	 * @param <K> Map键的类型
	 * @param <V> 原始Map值的类型
	 * @param <R> 映射后Map值的类型
	 * @return 包含映射后键值对的新Map
	 */
	public static <K, V, R> Map<K, R> mapValues(Map<K, V> map, Function<V, R> mapper) {
		if (isEmpty(map)) {
			return new HashMap<>();
		}
		return map.entrySet().stream()
			.collect(Collectors.toMap(Map.Entry::getKey, entry -> mapper.apply(entry.getValue())));
	}

	// ================== 获取键的集合 ================== //

	/**
	 * 获取Map中所有键的集合。
	 * @param map 要获取键集合的Map
	 * @param <K> Map键的类型
	 * @param <V> Map值的类型
	 * @return 包含Map所有键的集合
	 */
	public static <K, V> Set<K> keySet(Map<K, V> map) {
		if (isEmpty(map)) {
			return new HashSet<>();
		}
		return map.keySet();
	}

	// ================== 获取值的集合 ================== //

	/**
	 * 获取Map中所有值的集合。
	 * @param map 要获取值集合的Map
	 * @param <K> Map键的类型
	 * @param <V> Map值的类型
	 * @return 包含Map所有值的集合
	 */
	public static <K, V> Collection<V> values(Map<K, V> map) {
		if (isEmpty(map)) {
			return new ArrayList<>();
		}
		return map.values();
	}

	// ================== 获取指定键的值，若不存在则返回默认值 ================== //

	/**
	 * 获取Map中指定键的值，若键不存在则返回默认值。
	 * @param map 要查找的Map
	 * @param key 要查找的键
	 * @param defaultValue 键不存在时返回的默认值
	 * @param <K> Map键的类型
	 * @param <V> Map值的类型
	 * @return 键对应的值，若键不存在则返回默认值
	 */
	public static <K, V> V getOrDefault(Map<K, V> map, K key, V defaultValue) {
		if (isEmpty(map)) {
			return defaultValue;
		}
		return map.getOrDefault(key, defaultValue);
	}

	// ================== 反转Map（键值对互换） ================== //

	/**
	 * 反转Map，即键变为值，值变为键。
	 * 注意：如果值不是唯一的，可能会导致信息丢失。
	 * @param map 要反转的Map
	 * @param <K> 原始Map键的类型
	 * @param <V> 原始Map值的类型
	 * @return 反转后的Map
	 */
	public static <K, V> Map<V, K> invert(Map<K, V> map) {
		if (isEmpty(map)) {
			return new HashMap<>();
		}
		return map.entrySet().stream()
			.collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
	}

	// ================== 根据值查找键 ================== //

	/**
	 * 根据值查找Map中的键。
	 * 如果有多个键对应相同的值，返回第一个找到的键；若未找到，返回null。
	 * @param map 要查找的Map
	 * @param value 要查找的值
	 * @param <K> Map键的类型
	 * @param <V> Map值的类型
	 * @return 对应值的键，若未找到则返回null
	 */
	public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
		if (isEmpty(map)) {
			return null;
		}
		for (Map.Entry<K, V> entry : map.entrySet()) {
			if (Objects.equals(entry.getValue(), value)) {
				return entry.getKey();
			}
		}
		return null;
	}

	// ================== 统计Map中满足条件的键值对数量 ================== //

	/**
	 * 统计Map中满足指定条件的键值对数量。
	 * @param map 要统计的Map
	 * @param predicate 统计条件，接收键和值作为参数
	 * @param <K> Map键的类型
	 * @param <V> Map值的类型
	 * @return 满足条件的键值对数量
	 */
	public static <K, V> long count(Map<K, V> map, BiPredicate<K, V> predicate) {
		if (isEmpty(map)) {
			return 0;
		}
		return map.entrySet().stream()
			.filter(entry -> predicate.test(entry.getKey(), entry.getValue()))
			.count();
	}
}
