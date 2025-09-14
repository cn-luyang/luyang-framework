package io.github.luyang.starter.web.filter;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.github.luyang.starter.web.constant.WebConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * TraceId 过滤器
 *
 * @author yang.lu
 */
public class TraceIdFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String traceId = request.getHeader(WebConstant.TRACE_ID_HEADER);
		if (StrUtil.isBlank(traceId)) {
			traceId = IdUtil.simpleUUID();
		}

		response.setHeader(WebConstant.TRACE_ID_HEADER, traceId);
		MDC.put(WebConstant.TRACE_ID_MDC, traceId);

		try {
			filterChain.doFilter(request, response);
		} finally {
			MDC.remove(WebConstant.TRACE_ID_MDC);
		}
	}
}
