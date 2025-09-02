package io.github.luyang.starter.dubbo.config;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Configuration;

/**
 * @author yang.lu
 */
@Configuration(proxyBeanMethods = false)
@EnableDubbo(scanBasePackages = "${dubbo.scan.base-packages:}")
public class DubboAutoConfig {
}
