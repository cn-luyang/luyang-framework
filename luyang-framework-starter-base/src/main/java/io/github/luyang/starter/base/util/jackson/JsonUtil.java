package io.github.luyang.starter.base.util.jackson;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.luyang.starter.base.util.jackson.databind.TimeModule;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Jackson Json 工具类
 *
 * @author yang.lu
 */
@UtilityClass
public class JsonUtil {

	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static ObjectMapper objectMapper;

	public static ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = Optional.ofNullable(SpringUtil.getBean(ObjectMapper.class))
				.orElseGet(JsonUtil::buildMapper);
		}
		return objectMapper;
	}

	private static ObjectMapper buildMapper() {

		JsonMapper.Builder builder = JsonMapper.builder();

		// 格式化输出，方便调试和查看
		builder.configure(SerializationFeature.INDENT_OUTPUT, true);
		// 忽略无法转换的对象，避免抛出异常，适合处理不完整的对象
		builder.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		// 禁止排序，保留原始的属性顺序
		builder.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, false);
		// 反序列化时忽略未知属性，适合接收不兼容的旧数据
		builder.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 允许单引号，便于某些格式的JSON输入
		builder.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		// 允许特殊字符和未转义的控制字符，适合特定数据格式
		builder.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
		// 使用注解配置，确保POJO类中的Jackson注解生效
		builder.configure(MapperFeature.USE_ANNOTATIONS, true);
		// 添加自定义模块，扩展Jackson功能
		builder.addModule(new TimeModule());
		// 不输出null字段，减少输出体积
		// builder.serializationInclusion(JsonInclude.Include.NON_NULL);

		return builder.build();
	}

	/**
	 * 将源对象转换为目标类型对象
	 *
	 * @param obj   源对象
	 * @param clazz 目标类型
	 * @return T 目标类型对象
	 * @author yang.lu
	 */
	public static <T> T convert(Object obj, Class<T> clazz) {
		return getObjectMapper().convertValue(obj, clazz);
	}

	/**
	 * 将对象转为 JSON 字符串
	 *
	 * @param obj 要转换的对象
	 * @return String JSON字符串，若对象为空则返回null
	 * @author yang.lu
	 */
	public static String toJsonString(Object obj) {
		if (Objects.isNull(obj)) {
			return null;
		}

		try {
			return getObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			logger.error("Object to JSON exception", e);
			return null;
		}
	}

	/**
	 * 将 JSON 字符串反序列化为目标对象
	 *
	 * @param jsonStr JSON字符串
	 * @param clazz   目标对象类型
	 * @return T 目标对象实例，若JSON为空则返回null
	 * @author yang.lu
	 */
	public static <T> T fromJson(String jsonStr, Class<T> clazz) {
		if (StrUtil.isBlank(jsonStr) || ObjectUtil.isNull(clazz)) {
			return null;
		}
		try {
			return getObjectMapper().readValue(jsonStr, clazz);
		} catch (Exception e) {
			logger.error("JSON object conversion exception", e);
			return null;
		}
	}

	/**
	 * 将 JSON 字符串反序列化为 Map
	 *
	 * @param jsonStr JSON字符串
	 * @param kClazz  Map键类型
	 * @param vClazz  Map值类型
	 * @return Map<K, V> 反序列化后的Map，若失败则返回空Map
	 * @author yang.lu
	 */
	public static <K, V> Map<K, V> toMap(String jsonStr, Class<K> kClazz, Class<V> vClazz) {
		if (StrUtil.isBlank(jsonStr) || ObjectUtil.hasNull(kClazz, vClazz)) {
			return Collections.emptyMap();
		}

		try {
			return getObjectMapper().readValue(jsonStr, getObjectMapper().getTypeFactory()
				.constructMapType(Map.class, kClazz, vClazz));
		} catch (JsonProcessingException e) {
			logger.error("JSON to Map exception", e);
			return Collections.emptyMap();
		}
	}

	/**
	 * 创建一个空的 JSON 对象
	 *
	 * @return ObjectNode 空的 JSON 对象
	 * @author yang.lu
	 */
	public static ObjectNode createObjectNode() {
		return getObjectMapper().createObjectNode();
	}

	/**
	 * 创建一个空的 JSON 数组
	 *
	 * @return ArrayNode 空的JSON数组
	 * @author yang.lu
	 */
	public static ArrayNode createArrayNode() {
		return getObjectMapper().createArrayNode();
	}

	/**
	 * 判断字符串是否为合法的JSON格式
	 *
	 * @param text 待判断的字符串
	 * @return boolean 是否为合法的JSON
	 * @author yang.lu
	 */
	public static boolean isJson(String text) {
		if (StrUtil.isBlank(text)) {
			return false;
		}

		String str = StrUtil.trim(text);
		return CharSequenceUtil.isWrap(str, '{', '}')
			|| CharSequenceUtil.isWrap(str, '[', ']');
	}
}
