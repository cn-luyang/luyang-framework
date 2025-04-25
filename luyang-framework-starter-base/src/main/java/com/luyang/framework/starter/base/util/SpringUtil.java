package com.luyang.framework.starter.base.util;

import cn.hutool.core.util.ArrayUtil;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

/**
 * Spring 工具类
 *
 * @author yang.lu
 */
@SuppressWarnings("NullableProblems")
public class SpringUtil implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	/**
	 * Spring应用上下文环境
	 */
	private static ConfigurableApplicationContext applicationContext;

	@Override
	public void initialize(final ConfigurableApplicationContext applicationContext) {
		SpringUtil.applicationContext = applicationContext;
	}

	/**
	 * 获取{@link ListableBeanFactory}，可能为{@link ConfigurableListableBeanFactory} 或 {@link ApplicationContextAware}
	 *
	 * @return {@link ListableBeanFactory}
	 * @author yang.lu
	 */
	public static ListableBeanFactory getBeanFactory() {
		return Optional.ofNullable(applicationContext)
			.orElseThrow(() -> new RuntimeException("applicationContext is null"));
	}

	/**
	 * 通过class获取Bean
	 *
	 * @param clazz Bean类
	 * @param args  构造函数参数
	 * @return Bean对象
	 * @author yang.lu
	 */
	public static <T> T getBean(final Class<T> clazz, final Object... args) {
		final ListableBeanFactory beanFactory = getBeanFactory();
		if (ArrayUtil.isEmpty(args)) {
			return beanFactory.getBean(clazz);
		}
		return getBeanFactory().getBean(clazz, args);
	}
}
