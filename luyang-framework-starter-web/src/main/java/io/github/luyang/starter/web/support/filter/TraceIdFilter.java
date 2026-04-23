package io.github.luyang.starter.web.support.filter;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.github.luyang.starter.base.util.TraceIdUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String traceId = request.getHeader(TraceIdUtil.HEADER_NAME);
		if (StrUtil.isBlank(traceId)) {
			traceId = IdUtil.fastSimpleUUID();
		}

		TraceIdUtil.set(traceId);

		try {
			filterChain.doFilter(request, response);
		} finally {
			TraceIdUtil.clear();
		}
	}
}
