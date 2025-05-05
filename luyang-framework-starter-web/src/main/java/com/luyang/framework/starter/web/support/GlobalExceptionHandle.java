package com.luyang.framework.starter.web.support;

import com.luyang.framework.starter.base.api.Result;
import com.luyang.framework.starter.base.enums.ResultEnum;
import com.luyang.framework.starter.base.error.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author yang.lu
 */
@RestControllerAdvice
@ResponseStatus(HttpStatus.OK)
@PropertySource(value = "classpath:rest-error.properties", encoding = "UTF-8")
public class GlobalExceptionHandle {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandle.class);

	/**
	 * 处理业务异常
	 *
	 * @param e 未知异常
	 * @return Result<?> 响应包装体，包含通用失败信息和异常消息
	 * @author yang.lu
	 */
	@ExceptionHandler(BusinessException.class)
	public Result<?> handleError(BusinessException e, HttpServletRequest request) {
		logger.error("Business exceptions, uri:{}", request.getRequestURI(), e);
		return Result.failure(e.getCode(), e.getMessage(), e.getData());
	}

	/**
	 * 处理其他异常
	 *
	 * @param e 未知异常
	 * @return Result<?> 响应包装体，包含通用失败信息和异常消息
	 * @author yang.lu
	 */
	@ExceptionHandler(Throwable.class)
	public Result<?> handleError(Throwable e) {
		logger.error("Unknown exception", e);
		return Result.failure(ResultEnum.FAILURE, e.getMessage());
	}
}
