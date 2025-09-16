package io.github.luyang.starter.web.support.filter;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.github.luyang.starter.web.common.constant.WebConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 链路追踪过滤器
 *
 * @author yang.lu
 */
public class TraceIdFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(TraceIdFilter.class);

	@Override
	@SuppressWarnings("NullableProblems")
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		String traceId = processTraceId(request, response);

		try {
			if (logger.isInfoEnabled()) {
				logger.info("Request started - traceId: {}, method: {}, uri: {}", traceId, request.getMethod(), request.getRequestURI());
			}
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			logger.error("Request failed - traceId: {}, uri: {}, error: {}", traceId, request.getRequestURI(), e.getMessage(), e);
		} finally {
			MDC.remove(WebConstant.TRACE_ID_MDC);
			if (logger.isDebugEnabled()) {
				logger.debug("Request completed - traceId: {}, status: {}", traceId, response.getStatus());
			}
		}
	}

	/**
	 * 处理TraceId
	 *
	 * @author yang.lu
	 */
	private String processTraceId(HttpServletRequest request, HttpServletResponse response) {

		String traceId = request.getHeader(WebConstant.TRACE_ID_HEADER);

		// 生成新的TraceId（如果不存在或无效）
		if (StrUtil.isBlank(traceId)) {
			traceId = IdUtil.simpleUUID();
		}

		// 设置响应头
		response.setHeader(WebConstant.TRACE_ID_HEADER, traceId);

		// 设置MDC上下文
		MDC.put(WebConstant.TRACE_ID_MDC, traceId);

		return traceId;
	}
}
