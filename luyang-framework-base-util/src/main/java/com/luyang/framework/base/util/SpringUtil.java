package com.luyang.framework.base.util;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Spring 工具类
 *
 * @author yang.lu
 */
@SuppressWarnings({"NullableProblems", "unchecked"})
public class SpringUtil implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	/**
	 * Spring应用上下文环境
	 */
	private static ConfigurableApplicationContext applicationContext;

	@Override
	public void initialize(final ConfigurableApplicationContext applicationContext) {
		SpringUtil.applicationContext = applicationContext;
	}
}
