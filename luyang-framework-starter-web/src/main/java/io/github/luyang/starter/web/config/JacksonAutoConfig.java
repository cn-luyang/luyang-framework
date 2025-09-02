package io.github.luyang.starter.web.config;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.luyang.starter.base.util.jackson.databind.TimeModule;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 自定义 Jackson 对象映射器（ObjectMapper）的构建
 *
 * @author yang.lu
 */
@AutoConfiguration
@AllArgsConstructor
@ConditionalOnClass(ObjectMapper.class)
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class JacksonAutoConfig {

	private final JacksonProperties properties;

	@Bean
	@ConditionalOnMissingBean
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
		return builder -> {
			// 基础配置
			configureBasicSettings(builder);
			// 日期时间配置
			configureDateTimeSettings(builder);
			// 序列化配置
			configureSerializationSettings(builder);
			// 反序列化配置
			configureDeserializationSettings(builder);
			// 模块配置
			configureModules(builder);
		};
	}

	/**
	 * 配置基础设置
	 *
	 * @param builder Jackson2ObjectMapperBuilder
	 * @author yang.lu
	 */
	private void configureBasicSettings(Jackson2ObjectMapperBuilder builder) {
		// 设置默认的区域信息
		builder.locale(ObjectUtil.defaultIfNull(properties.getLocale(), Locale.CHINA));

		// 设置默认的时区
		builder.timeZone(ObjectUtil.defaultIfNull(properties.getTimeZone(), TimeZone.getTimeZone(ZoneId.systemDefault())));

		// 设置日期格式
		builder.simpleDateFormat(ObjectUtil.defaultIfNull(properties.getDateFormat(), DatePattern.NORM_DATETIME_PATTERN));
	}

	/**
	 * 日期时间设置
	 *
	 * @param builder Jackson2ObjectMapperBuilder
	 * @author yang.lu
	 */
	private void configureDateTimeSettings(Jackson2ObjectMapperBuilder builder) {
		// 禁用日期时间戳格式
		builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		// 配置 Java 8 日期时间模块
		builder.modulesToInstall(JavaTimeModule.class);
	}

	/**
	 * 序列化设置
	 *
	 * @param builder Jackson2ObjectMapperBuilder
	 * @author yang.lu
	 */
	private void configureSerializationSettings(Jackson2ObjectMapperBuilder builder) {

		// 若POJO对象的属性值为null，序列化时不进行显示
		builder.serializationInclusion(ObjectUtil.defaultIfNull(
			properties.getDefaultPropertyInclusion(), JsonInclude.Include.NON_NULL
		));
	}

	/**
	 * 配置反序列化相关设置
	 *
	 * @param builder Jackson2ObjectMapperBuilder
	 * @author yang.lu
	 */
	private void configureDeserializationSettings(Jackson2ObjectMapperBuilder builder) {

		// DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES 遇到未知属性处理，反序列化依旧可以成功
		builder.failOnUnknownProperties(ObjectUtil.defaultIfNull(
			properties.getDeserialization().get(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES), false
		));

		// 忽略 transient 字段
		builder.featuresToEnable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
	}

	/**
	 * 配置自定义模块
	 *
	 * @param builder Jackson2ObjectMapperBuilder
	 * @author yang.lu
	 */
	private void configureModules(Jackson2ObjectMapperBuilder builder) {
		builder.modules(new TimeModule());
	}
}
