package io.github.luyang.framework.starter.web.util;

import cn.hutool.core.util.ArrayUtil;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

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
        final ConfigurableEnvironment environment = getEnvironment();
        return null == environment ? null : environment.getProperty(key);
    }

    /**
     * 获取环境属性
     *
     * @return {@link ConfigurableEnvironment}
     * @author yang.lu
     */
    public static ConfigurableEnvironment getEnvironment() {
        return null == applicationContext ? null : applicationContext.getEnvironment();
    }
}
