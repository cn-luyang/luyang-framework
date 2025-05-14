package io.github.luyang.framework.base.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 列表工具类，提供了一系列对列表进行操作的静态方法。
 * @author wangjixin
 */
public final class ListUtils {

	private ListUtils() {
		throw new AssertionError("禁止实例化工具类");
	}

	// ================== 空值判断 ================== //

	/**
	 * 判断列表是否为空。
	 * @param list 要检查的列表
	 * @return 如果列表为 null 或者列表中没有元素，返回 true；否则返回 false
	 */
	public static <T> boolean isEmpty(List<T> list) {
		return null == list || list.isEmpty();
	}

	/**
	 * 判断列表是否不为空。
	 * @param list 要检查的列表
	 * @return 如果列表不为 null 且包含至少一个元素，返回 true；否则返回 false
	 */
	public static <T> boolean isNotEmpty(List<T> list) {
		return !isEmpty(list);
	}

	// ================== 列表创建 ================== //

	/**
	 * 创建一个包含指定元素的不可变列表。
	 * @param elements 要包含在列表中的元素
	 * @param <T> 元素的类型
	 * @return 包含指定元素的不可变列表
	 */
	@SafeVarargs
	public static <T> List<T> of(T... elements) {
		return Arrays.asList(elements);
	}

	// ================== 列表合并 ================== //

	/**
	 * 合并多个列表为一个列表。
	 * @param lists 要合并的列表数组
	 * @param <T> 列表元素的类型
	 * @return 合并后的列表
	 */
	@SafeVarargs
	public static <T> List<T> merge(List<T>... lists) {
		List<T> result = new ArrayList<>();
		for (List<T> list : lists) {
			if (isNotEmpty(list)) {
				result.addAll(list);
			}
		}
		return result;
	}

	// ================== 列表筛选 ================== //

	/**
	 * 根据指定的条件筛选列表中的元素。
	 * @param list 要筛选的列表
	 * @param predicate 筛选条件
	 * @param <T> 列表元素的类型
	 * @return 包含满足条件元素的新列表
	 */
	public static <T> List<T> filter(List<T> list, java.util.function.Predicate<T> predicate) {
		if (isEmpty(list)) {
			return new ArrayList<>();
		}
		return list.stream().filter(predicate).collect(Collectors.toList());
	}

	// ================== 列表映射 ================== //

	/**
	 * 将列表中的每个元素通过指定的映射函数进行转换。
	 * @param list 要进行映射的列表
	 * @param mapper 映射函数
	 * @param <T> 原始列表元素的类型
	 * @param <R> 映射后列表元素的类型
	 * @return 包含映射后元素的新列表
	 */
	public static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
		if (isEmpty(list)) {
			return new ArrayList<>();
		}
		return list.stream().map(mapper).collect(Collectors.toList());
	}

	// ================== 列表排序 ================== //

	/**
	 * 对列表进行排序。
	 * @param list 要排序的列表
	 * @param comparator 比较器，用于定义排序规则
	 * @param <T> 列表元素的类型
	 */
	public static <T> void sort(List<T> list, Comparator<T> comparator) {
		if (isNotEmpty(list)) {
			list.sort(comparator);
		}
	}

	// ================== 列表去重 ================== //

	/**
	 * 去除列表中的重复元素。
	 * @param list 要去重的列表
	 * @param <T> 列表元素的类型
	 * @return 去重后的列表
	 */
	public static <T> List<T> distinct(List<T> list) {
		if (isEmpty(list)) {
			return new ArrayList<>();
		}
		return list.stream().distinct().collect(Collectors.toList());
	}

	// ================== 列表分割 ================== //

	/**
	 * 将列表分割成指定大小的子列表。
	 * @param list 要分割的列表
	 * @param size 每个子列表的最大大小
	 * @param <T> 列表元素的类型
	 * @return 包含子列表的列表
	 */
	public static <T> List<List<T>> partition(List<T> list, int size) {
		if (isEmpty(list)) {
			return new ArrayList<>();
		}
		List<List<T>> partitions = new ArrayList<>();
		for (int i = 0; i < list.size(); i += size) {
			partitions.add(list.subList(i, Math.min(i + size, list.size())));
		}
		return partitions;
	}

	// ================== 列表查找 ================== //

	/**
	 * 查找列表中满足指定条件的第一个元素。
	 * @param list 要查找的列表
	 * @param predicate 查找条件
	 * @param <T> 列表元素的类型
	 * @return 满足条件的第一个元素，如果未找到则返回 null
	 */
	public static <T> T findFirst(List<T> list, java.util.function.Predicate<T> predicate) {
		if (isEmpty(list)) {
			return null;
		}
		return list.stream().filter(predicate).findFirst().orElse(null);
	}

	// ================== 列表统计 ================== //

	/**
	 * 统计列表中满足指定条件的元素数量。
	 * @param list 要统计的列表
	 * @param predicate 统计条件
	 * @param <T> 列表元素的类型
	 * @return 满足条件的元素数量
	 */
	public static <T> long count(List<T> list, java.util.function.Predicate<T> predicate) {
		if (isEmpty(list)) {
			return 0;
		}
		return list.stream().filter(predicate).count();
	}

	// ================== 列表转换为 Map ================== //

	/**
	 * 将列表转换为 Map，键由指定的键提取函数生成。
	 * @param list 要转换的列表
	 * @param keyMapper 键提取函数
	 * @param <T> 列表元素的类型
	 * @param <K> 键的类型
	 * @return 转换后的 Map
	 */
	public static <T, K> Map<K, T> toMap(List<T> list, Function<T, K> keyMapper) {
		if (isEmpty(list)) {
			return new HashMap<>(16);
		}
		return list.stream().collect(Collectors.toMap(keyMapper, Function.identity(), (existing, replacement) -> existing));
	}
}
