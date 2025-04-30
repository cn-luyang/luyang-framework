package com.luyang.framework.starter.web.config;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.context.annotation.Bean;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 自定义 Jackson 对象映射器（ObjectMapper）的构建
 *
 * @author yang.lu
 */
@ConditionalOnClass(ObjectMapper.class)
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class JacksonConfiguration {

	private final JacksonProperties properties;

	public JacksonConfiguration(JacksonProperties properties) {
		this.properties = properties;
	}

	@Bean
	@ConditionalOnMissingBean
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
		return builder -> {

			// 设置默认的区域信息,若未配置则使用中国区
			builder.locale(ObjectUtil.defaultIfNull(properties.getLocale(), Locale.CHINA));

			// 设置默认的时区,若未配置则使用系统默认时区
			builder.timeZone(ObjectUtil.defaultIfNull(
				properties.getTimeZone(),
				TimeZone.getTimeZone(ZoneId.systemDefault())
			));

			// 针对 Date 类型,设置默认的日期格式,若未配置则使用 yyyy-MM-dd HH:mm:ss
			builder.simpleDateFormat(ObjectUtil.defaultIfNull(
				properties.getDateFormat(),
				DatePattern.NORM_DATETIME_PATTERN
			));

			// 若POJO对象的属性值为null，序列化时不进行显示
			builder.serializationInclusion(ObjectUtil.defaultIfNull(
				properties.getDefaultPropertyInclusion(), JsonInclude.Include.NON_NULL
			));

			// DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES相当于配置，JSON串含有未知字段时，反序列化依旧可以成功
			builder.failOnUnknownProperties(ObjectUtil.defaultIfNull(
				properties.getDeserialization().get(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES), false
			));
		};
	}
}
