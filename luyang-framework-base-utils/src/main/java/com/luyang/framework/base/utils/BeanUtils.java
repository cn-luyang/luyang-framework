package com.luyang.framework.base.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * BeanUtil 工具类，用于在 JavaBean 之间复制属性。
 *
 * @author wangjixin
 */
public final class BeanUtils {

	private BeanUtils() {
		throw new AssertionError("禁止实例化工具类");
	}


	/**
	 * 将源对象的属性复制到目标对象。
	 *
	 * @param source 源对象
	 * @param target 目标对象
	 * @throws Exception 如果复制过程中出现异常
	 */
	public static void copyProperties(Object source, Object target) throws Exception {
		if (source == null || target == null) {
			return;
		}
		// 获取源对象的 BeanInfo
		BeanInfo sourceBeanInfo = Introspector.getBeanInfo(source.getClass());
		// 获取目标对象的 BeanInfo
		BeanInfo targetBeanInfo = Introspector.getBeanInfo(target.getClass());

		// 获取源对象的属性描述符
		PropertyDescriptor[] sourcePropertyDescriptors = sourceBeanInfo.getPropertyDescriptors();
		// 遍历源对象的属性描述符
		for (PropertyDescriptor sourceDescriptor : sourcePropertyDescriptors) {
			String propertyName = sourceDescriptor.getName();
			if ("class".equals(propertyName)) {
				continue;
			}
			// 获取源对象的读方法
			Method readMethod = sourceDescriptor.getReadMethod();
			if (readMethod != null) {
				// 获取目标对象的属性描述符
				PropertyDescriptor targetDescriptor = getPropertyDescriptor(targetBeanInfo, propertyName);
				if (targetDescriptor != null) {
					// 获取目标对象的写方法
					Method writeMethod = targetDescriptor.getWriteMethod();
					if (writeMethod != null) {
						// 调用源对象的读方法获取属性值
						Object value = readMethod.invoke(source);
						// 调用目标对象的写方法设置属性值
						writeMethod.invoke(target, value);
					}
				}
			}
		}
	}

	/**
	 * 将源对象的属性复制到目标对象，忽略指定的属性。
	 *
	 * @param source           源对象
	 * @param target           目标对象
	 * @param ignoreProperties 要忽略的属性名数组
	 * @throws Exception 如果复制过程中出现异常
	 */
	public static void copyProperties(Object source, Object target, String... ignoreProperties) throws Exception {
		if (source == null || target == null) {
			return;
		}
		Set<String> ignoreSet = ignoreProperties != null ? new HashSet<>(Arrays.asList(ignoreProperties)) : new HashSet<>();
		BeanInfo sourceBeanInfo = Introspector.getBeanInfo(source.getClass());
		BeanInfo targetBeanInfo = Introspector.getBeanInfo(target.getClass());
		PropertyDescriptor[] sourcePropertyDescriptors = sourceBeanInfo.getPropertyDescriptors();
		for (PropertyDescriptor sourceDescriptor : sourcePropertyDescriptors) {
			String propertyName = sourceDescriptor.getName();
			if ("class".equals(propertyName) || ignoreSet.contains(propertyName)) {
				continue;
			}
			Method readMethod = sourceDescriptor.getReadMethod();
			if (readMethod != null) {
				PropertyDescriptor targetDescriptor = getPropertyDescriptor(targetBeanInfo, propertyName);
				if (targetDescriptor != null) {
					Method writeMethod = targetDescriptor.getWriteMethod();
					if (writeMethod != null) {
						Object value = readMethod.invoke(source);
						writeMethod.invoke(target, value);
					}
				}
			}
		}
	}

	/**
	 * 复制 List 中的元素。
	 *
	 * @param sourceList  源 List
	 * @param targetClass 目标元素的类类型
	 * @param <S>         源元素的类型
	 * @param <T>         目标元素的类型
	 * @return 复制后的 List
	 * @throws Exception 如果复制过程中出现异常
	 */
	public static <S, T> List<T> copyListProperties(List<S> sourceList, Class<T> targetClass) throws Exception {
		if (sourceList == null || sourceList.isEmpty()) {
			return new ArrayList<>();
		}
		return sourceList.stream().map(source -> {
			try {
				T target = targetClass.getDeclaredConstructor().newInstance();
				copyProperties(source, target);
				return target;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).collect(Collectors.toList());
	}

	/**
	 * 将 JavaBean 转换为 Map。
	 *
	 * @param bean 要转换的 JavaBean 对象
	 * @return 包含 JavaBean 属性的 Map
	 * @throws Exception 如果转换过程中出现异常
	 */
	public static Map<String, Object> beanToMap(Object bean) throws Exception {
		if (bean == null) {
			return new HashMap<>();
		}
		Map<String, Object> map = new HashMap<>();
		// 获取 JavaBean 的 BeanInfo
		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
		// 获取 JavaBean 的属性描述符
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor descriptor : propertyDescriptors) {
			String propertyName = descriptor.getName();
			if (!"class".equals(propertyName)) {
				// 获取属性的读方法
				Method readMethod = descriptor.getReadMethod();
				if (readMethod != null) {
					// 调用读方法获取属性值
					Object value = readMethod.invoke(bean);
					map.put(propertyName, value);
				}
			}
		}
		return map;
	}

	/**
	 * 将 Map 转换为 JavaBean。
	 *
	 * @param map       包含属性的 Map
	 * @param beanClass JavaBean 的类类型
	 * @param <T>       JavaBean 的类型
	 * @return 转换后的 JavaBean 对象
	 * @throws Exception 如果转换过程中出现异常
	 */
	public static <T> T mapToBean(Map<String, Object> map, Class<T> beanClass) throws Exception {
		if (map == null) {
			return beanClass.getDeclaredConstructor().newInstance();
		}
		// 创建 JavaBean 实例
		T bean = beanClass.getDeclaredConstructor().newInstance();
		// 获取 JavaBean 的 BeanInfo
		BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
		// 获取 JavaBean 的属性描述符
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor descriptor : propertyDescriptors) {
			String propertyName = descriptor.getName();
			if (map.containsKey(propertyName)) {
				// 获取属性的写方法
				Method writeMethod = descriptor.getWriteMethod();
				if (writeMethod != null) {
					// 从 Map 中获取属性值
					Object value = map.get(propertyName);
					// 调用写方法设置属性值
					writeMethod.invoke(bean, value);
				}
			}
		}
		return bean;
	}

	private static PropertyDescriptor getPropertyDescriptor(BeanInfo beanInfo, String propertyName) {
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor descriptor : propertyDescriptors) {
			if (descriptor.getName().equals(propertyName)) {
				return descriptor;
			}
		}
		return null;
	}
}

