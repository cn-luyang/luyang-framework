package io.github.luyang.starter.feign.support.interceptor;

import cn.hutool.core.util.StrUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.github.luyang.starter.base.constant.BaseConstant;
import io.github.luyang.starter.base.util.TraceIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Feign 请求头透传拦截器
 *
 * @author yang.lu
 */
public class FeignHeaderRelayInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		// 获取当前请求上下文
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		// 非 Web 环境（如异步线程 / 定时任务）直接跳过
		if (null == attributes) {
			return;
		}

		// 传递 TraceId
		String traceId = TraceIdUtil.get();
		if (StrUtil.isNotBlank(traceId)) {
			template.header(TraceIdUtil.HEADER_NAME, traceId);
		}

		// 传递 Token
		HttpServletRequest request = attributes.getRequest();
		String token = StrUtil.blankToDefault(
			request.getHeader(HttpHeaders.AUTHORIZATION),
			request.getParameter(BaseConstant.ACCESS_TOKEN_PARAM)
		);
		if(StrUtil.isNotBlank(token)){
			template.header(HttpHeaders.AUTHORIZATION, token);
		}
	}
}
