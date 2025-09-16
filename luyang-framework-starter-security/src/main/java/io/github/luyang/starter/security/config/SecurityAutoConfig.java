package io.github.luyang.starter.security.config;

import io.github.luyang.starter.security.properties.SecurityProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Security自动配置入口类
 * 作为Spring Boot自动配置的入口点，导入所有相关配置
 *
 * @author yang.lu
 */
@AutoConfiguration
@EnableWebSecurity
@EnableMethodSecurity
@EnableConfigurationProperties(SecurityProperties.class)
@Import({
	SecurityBeanConfig.class,
	SecurityFilterChainConfig.class
})
public class SecurityAutoConfig {

}
