package io.github.luyang.base.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 集合工具类，提供了一系列对集合（Set）进行操作的静态方法。
 * @author wangjixin
 */
public final class SetUtils {

	private SetUtils() {
		throw new AssertionError("禁止实例化工具类");
	}

	// ================== 空值判断 ================== //

	/**
	 * 判断集合是否为空。
	 *
	 * @param set 要检查的集合
	 * @param <T> 集合元素的类型
	 * @return 如果集合为 null 或者集合中没有元素，返回 true；否则返回 false
	 */
	public static <T> boolean isEmpty(Set<T> set) {
		return null == set || set.isEmpty();
	}

	/**
	 * 判断集合是否不为空。
	 *
	 * @param set 要检查的集合
	 * @param <T> 集合元素的类型
	 * @return 如果集合不为 null 且包含至少一个元素，返回 true；否则返回 false
	 */
	public static <T> boolean isNotEmpty(Set<T> set) {
		return !isEmpty(set);
	}

	// ================== 集合创建 ================== //

	/**
	 * 创建一个包含指定元素的不可变集合。
	 *
	 * @param elements 要包含在集合中的元素
	 * @param <T>      元素的类型
	 * @return 包含指定元素的不可变集合
	 */
	@SafeVarargs
	public static <T> Set<T> of(T... elements) {
		return Set.of(elements);
	}

	// ================== 集合合并 ================== //

	/**
	 * 合并多个集合为一个集合。
	 *
	 * @param sets 要合并的集合数组
	 * @param <T>  集合元素的类型
	 * @return 合并后的集合
	 */
	@SafeVarargs
	public static <T> Set<T> merge(Set<T>... sets) {
		Set<T> result = new HashSet<>();
		for (Set<T> set : sets) {
			if (isNotEmpty(set)) {
				result.addAll(set);
			}
		}
		return result;
	}

	// ================== 集合筛选 ================== //

	/**
	 * 根据指定的条件筛选集合中的元素。
	 *
	 * @param set       要筛选的集合
	 * @param predicate 筛选条件
	 * @param <T>       集合元素的类型
	 * @return 包含满足条件元素的新集合
	 */
	public static <T> Set<T> filter(Set<T> set, Predicate<T> predicate) {
		if (isEmpty(set)) {
			return new HashSet<>();
		}
		return set.stream().filter(predicate).collect(Collectors.toSet());
	}

	// ================== 集合映射 ================== //

	/**
	 * 将集合中的每个元素通过指定的映射函数进行转换。
	 *
	 * @param set    要进行映射的集合
	 * @param mapper 映射函数
	 * @param <T>    原始集合元素的类型
	 * @param <R>    映射后集合元素的类型
	 * @return 包含映射后元素的新集合
	 */
	public static <T, R> Set<R> map(Set<T> set, Function<T, R> mapper) {
		if (isEmpty(set)) {
			return new HashSet<>();
		}
		return set.stream().map(mapper).collect(Collectors.toSet());
	}

	// ================== 集合交集 ================== //

	/**
	 * 计算两个集合的交集。
	 *
	 * @param set1 第一个集合
	 * @param set2 第二个集合
	 * @param <T>  集合元素的类型
	 * @return 两个集合的交集
	 */
	public static <T> Set<T> intersection(Set<T> set1, Set<T> set2) {
		if (isEmpty(set1) || isEmpty(set2)) {
			return new HashSet<>();
		}
		Set<T> result = new HashSet<>(set1);
		result.retainAll(set2);
		return result;
	}

	// ================== 集合并集 ================== //

	/**
	 * 计算两个集合的并集。
	 *
	 * @param set1 第一个集合
	 * @param set2 第二个集合
	 * @param <T>  集合元素的类型
	 * @return 两个集合的并集
	 */
	public static <T> Set<T> union(Set<T> set1, Set<T> set2) {
		Set<T> result = new HashSet<>();
		if (isNotEmpty(set1)) {
			result.addAll(set1);
		}
		if (isNotEmpty(set2)) {
			result.addAll(set2);
		}
		return result;
	}

	// ================== 集合差集 ================== //

	/**
	 * 计算两个集合的差集（set1 中存在但 set2 中不存在的元素）。
	 *
	 * @param set1 第一个集合
	 * @param set2 第二个集合
	 * @param <T>  集合元素的类型
	 * @return 两个集合的差集
	 */
	public static <T> Set<T> difference(Set<T> set1, Set<T> set2) {
		if (isEmpty(set1)) {
			return new HashSet<>();
		}
		Set<T> result = new HashSet<>(set1);
		if (isNotEmpty(set2)) {
			result.removeAll(set2);
		}
		return result;
	}

	// ================== 集合查找 ================== //

	/**
	 * 查找集合中满足指定条件的第一个元素。
	 *
	 * @param set       要查找的集合
	 * @param predicate 查找条件
	 * @param <T>       集合元素的类型
	 * @return 满足条件的第一个元素，如果未找到则返回 null
	 */
	public static <T> T findFirst(Set<T> set, Predicate<T> predicate) {
		if (isEmpty(set)) {
			return null;
		}
		return set.stream().filter(predicate).findFirst().orElse(null);
	}

	// ================== 集合统计 ================== //

	/**
	 * 统计集合中满足指定条件的元素数量。
	 *
	 * @param set       要统计的集合
	 * @param predicate 统计条件
	 * @param <T>       集合元素的类型
	 * @return 满足条件的元素数量
	 */
	public static <T> long count(Set<T> set, Predicate<T> predicate) {
		if (isEmpty(set)) {
			return 0;
		}
		return set.stream().filter(predicate).count();
	}

	// ================== 集合转换为 List ================== //

	/**
	 * 将集合转换为列表。
	 *
	 * @param set 要转换的集合
	 * @param <T> 集合元素的类型
	 * @return 转换后的列表
	 */
	public static <T> List<T> toList(Set<T> set) {
		if (isEmpty(set)) {
			return new ArrayList<>();
		}
		return new ArrayList<>(set);
	}
}
