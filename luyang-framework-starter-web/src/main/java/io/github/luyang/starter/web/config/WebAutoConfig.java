package io.github.luyang.starter.web.config;

import io.github.luyang.starter.web.context.initializer.BannerInitializer;
import io.github.luyang.starter.web.support.advice.GlobalExceptionAdvice;
import io.github.luyang.starter.web.support.filter.TraceIdFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;

/**
 * Web 应用自动配置
 *
 * @author yang.lu
 */
@AutoConfiguration
public class WebAutoConfig {

    @Bean
    public FilterRegistrationBean<TraceIdFilter> traceIdFilter() {
        FilterRegistrationBean<TraceIdFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TraceIdFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }

    /**
     * 注册全局异常处理器
     * 统一处理控制器层异常
     *
     * @author yang.lu
     */
    @Bean
    public GlobalExceptionAdvice globalExceptionAdvice() {
        return new GlobalExceptionAdvice();
    }

    /**
     * 注册Banner初始化器
     * 负责应用启动时的Banner显示
     *
     * @author yang.lu
     */
    @Bean
    public BannerInitializer bannerInitializer(Environment environment, ApplicationContext context) {
        return new BannerInitializer(environment, context);
    }
}
