package com.luyang.framework.starter.web.desensitize;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

/**
 * 字段脱敏序列化器
 *
 * @author yang.lu
 */
public class DesensitizeSerializer extends JsonSerializer<String> implements ContextualSerializer {

	private static final Logger logger = LoggerFactory.getLogger(DesensitizeSerializer.class);

	private final DesensitizeRule desensitizeRule;

	public DesensitizeSerializer(DesensitizeRule desensitizeRule) {
		this.desensitizeRule = desensitizeRule;
	}

	public DesensitizeSerializer() {
		this.desensitizeRule = null;
	}

	/**
	 * 序列化逻辑：对目标字符串按规则脱敏处理
	 *
	 * @param value              原始字符串值
	 * @param jsonGenerator      JSON 生成器，用于写出序列化结果
	 * @param serializerProvider 序列化上下文
	 * @author yang.lu
	 */
	@Override
	public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		if (StrUtil.isBlank(value) || null == desensitizeRule) {
			jsonGenerator.writeString(value);
			return;
		}

		jsonGenerator.writeString(desensitizeRule.getPattern().matcher(value).replaceAll(desensitizeRule.getReplacement()));
	}

	/**
	 * 上下文创建序列化器：从字段注解中读取脱敏规则
	 *
	 * @param serializerProvider Jackson 序列化器上下文
	 * @param beanProperty       当前字段属性对象，可能含有脱敏注解
	 * @return 返回一个新的带有脱敏规则的序列化器实例；如果字段无注解则返回默认序列化器
	 * @author yang.lu
	 */
	@Override
	public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
		if (null == beanProperty) {
			return serializerProvider.findNullValueSerializer(null);
		}

		Desensitize annotation = Optional.ofNullable(beanProperty.getAnnotation(Desensitize.class))
			.orElse(beanProperty.getContextAnnotation(Desensitize.class));

		if (annotation == null || !Enum.class.isAssignableFrom(annotation.enumClass())) {
			return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
		}

		try {
			Class<? extends Enum<? extends DesensitizeRule>> enumClass = annotation.enumClass();
			Enum<?> enumInstance = Enum.valueOf(enumClass.asSubclass(Enum.class), annotation.enumName());
			return new DesensitizeSerializer((DesensitizeRule) enumInstance);
		} catch (Exception e) {
			logger.warn("字段 [{}] 脱敏序列化失败: {}", beanProperty.getName(), e.getMessage(), e);
			return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
		}
	}
}
