package io.github.luyang.starter.base.util;

import cn.hutool.core.util.StrUtil;
import org.slf4j.MDC;

/**
 * 链路追踪ID工具类
 *
 * @author yang.lu
 */
public final class TraceIdUtil {

	/** HTTP请求头名称，用于在服务间传递traceId */
	public static final String HEADER_NAME = "X-Request-Id";

	/** MDC中的键名，日志配置中使用 */
	public static final String MDC_KEY = "traceId";

	private TraceIdUtil() {
	}

	public static String get() {
		return MDC.get(MDC_KEY);
	}

	public static void set(String traceId) {
		if (StrUtil.isNotBlank(traceId)) {
			MDC.put(MDC_KEY, traceId);
		}
	}

	public static void clear() {
		MDC.remove(MDC_KEY);
	}
}
