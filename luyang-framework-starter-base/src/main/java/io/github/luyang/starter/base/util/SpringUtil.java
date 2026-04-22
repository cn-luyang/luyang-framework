package io.github.luyang.starter.base.util;

import cn.hutool.core.exceptions.UtilException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author yang.lu
 */
@Component
public class SpringUtil implements BeanFactoryPostProcessor, ApplicationContextAware {

	private static ConfigurableListableBeanFactory beanFactory;
	private static ApplicationContext applicationContext;

	private SpringUtil() {
	}

	@Override
	@SuppressWarnings("NullableProblems")
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		SpringUtil.beanFactory = beanFactory;
	}

	@Override
	@SuppressWarnings("NullableProblems")
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtil.applicationContext = applicationContext;
	}

	public static ListableBeanFactory getBeanFactory() {
		Object factory = (null == beanFactory) ? applicationContext : beanFactory;

		if (factory instanceof ListableBeanFactory lbf) {
			return lbf;
		}
		throw new UtilException("No BeanFactory or ApplicationContext injected!");
	}

	public static ConfigurableListableBeanFactory getConfigurableBeanFactory() {
		// 模式匹配处理 context 转 factory
		if (null != beanFactory) {
			return beanFactory;
		}

		if (applicationContext instanceof ConfigurableApplicationContext cac) {
			return cac.getBeanFactory();
		}
		throw new UtilException("No ConfigurableListableBeanFactory available!");
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) getBeanFactory().getBean(name);
	}

	public static <T> T getBean(Class<T> clazz) {
		return getBeanFactory().getBean(clazz);
	}

	public static <T> T getBean(String name, Class<T> clazz) {
		return getBeanFactory().getBean(name, clazz);
	}

	public static String getProperty(String key) {
		return Optional.ofNullable(applicationContext)
			.map(ctx -> ctx.getEnvironment().getProperty(key))
			.orElse(null);
	}

	public static String getApplicationName() {
		return getProperty("spring.application.name");
	}

	public static String[] getActiveProfiles() {
		return null == applicationContext ? null : applicationContext.getEnvironment().getActiveProfiles();
	}

	public static String getActiveProfile() {
		return Optional.ofNullable(getActiveProfiles())
			.filter(p -> p.length > 0)
			.map(p -> p[0])
			.orElse(null);
	}

	public static <T> void registerBean(String beanName, T bean) {
		ConfigurableListableBeanFactory factory = getConfigurableBeanFactory();
		factory.autowireBean(bean);
		factory.registerSingleton(beanName, bean);
	}

	public static void unregisterBean(String beanName) {
		if (getConfigurableBeanFactory() instanceof DefaultSingletonBeanRegistry registry) {
			registry.destroySingleton(beanName);
		} else {
			throw new UtilException("Can not unregister bean, factory is not a DefaultSingletonBeanRegistry");
		}
	}

	public static void publishEvent(Object event) {
		if (applicationContext != null) {
			applicationContext.publishEvent(event);
		}
	}
}
