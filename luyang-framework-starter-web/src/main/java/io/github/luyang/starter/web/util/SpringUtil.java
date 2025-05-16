package io.github.luyang.starter.web.util;

import cn.hutool.core.util.ArrayUtil;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Optional;

/**
 * Spring 工具类
 *
 * @author yang.lu
 */
@SuppressWarnings("NullableProblems")
public class SpringUtil implements ApplicationContextAware {

    /**
     * Spring应用上下文环境
     */
	@Getter
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
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
                .orElseThrow(() -> new IllegalStateException("ApplicationContext not initialized"));
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

    /**
     * 获取应用程序名称
     *
     * @return 应用程序名称
     * @author yang.lu
     */
    public static String getApplicationName() {
        return getProperty("spring.application.name");
    }

    /**
     * 获取配置文件配置项的值
     *
     * @param key 配置项key
     * @return 属性值
     * @author yang.lu
     */
    public static String getProperty(final String key) {
		return applicationContext.getEnvironment().getProperty(key);
    }
}
