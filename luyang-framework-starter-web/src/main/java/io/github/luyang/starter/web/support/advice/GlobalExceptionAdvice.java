package io.github.luyang.starter.web.support.advice;

import io.github.luyang.starter.base.common.enums.ResultEnum;
import io.github.luyang.starter.base.common.exception.BusinessException;
import io.github.luyang.starter.base.common.model.Result;
import io.github.luyang.starter.web.common.constant.error.WebError;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理
 *
 * @author yang.lu
 */
@RestControllerAdvice
@ResponseStatus(HttpStatus.OK)
@PropertySource(value = "classpath:rest-error.properties", encoding = "UTF-8")
public class GlobalExceptionAdvice {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

	/**
	 * 处理缺失请求参数异常
	 *
	 * @param e 缺失请求参数异常
	 * @return Result<?> 响应包装体，包含错误信息
	 * @author yang.lu
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public Result<?> handleError(MissingServletRequestParameterException e) {
		String message = String.format("参数缺失: %s", e.getParameterName());
		return Result.failure(WebError.BAD_REQUEST, message);
	}

	/**
	 * 处理请求参数类型错误异常
	 *
	 * @param e 请求参数类型错误异常
	 * @return Result<?> 响应包装体，包含错误信息
	 * @author yang.lu
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public Result<?> handleError(MethodArgumentTypeMismatchException e) {
		String message = String.format("参数类型错误: %s", e.getName());
		return Result.failure(WebError.BAD_REQUEST, message);
	}

	/**
	 * 处理请求参数验证失败异常
	 *
	 * @param e 请求参数验证失败异常
	 * @return Result<?> 响应包装体，包含错误信息
	 * @author yang.lu
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Result<?> handleError(MethodArgumentNotValidException e) {
		String message = this.handleError(e.getBindingResult());
		return Result.failure(WebError.BAD_REQUEST, message);
	}

	/**
	 * 处理参数验证失败结果
	 *
	 * @param result 参数验证结果
	 * @return String 错误信息
	 * @author yang.lu
	 */
	private String handleError(BindingResult result) {
		FieldError error = result.getFieldError();
		assert error != null;
		return String.format("%s:%s", error.getField(), error.getDefaultMessage());
	}

	/**
	 * 处理请求资源未找到异常
	 *
	 * @param e 请求资源未找到异常
	 * @return Result<?> 响应包装体，包含错误信息
	 * @author yang.lu
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public Result<?> handleError(NoHandlerFoundException e) {
		return Result.failure(WebError.NOT_FOUND, String.format("请求的资源不存在或已删除: %s", e.getRequestURL()));
	}

	/**
	 * 处理请求体无法读取异常
	 *
	 * @param e 请求体无法读取异常
	 * @return Result<?> 响应包装体，包含错误信息
	 * @author yang.lu
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Result<?> handleError(HttpMessageNotReadableException e) {
		return Result.failure(WebError.BAD_REQUEST, String.format("无法解析请求体: %s", e.getMessage()));
	}

	/**
	 * 处理请求方法不支持异常
	 *
	 * @param e 请求方法不支持异常
	 * @return Result<?> 响应包装体，包含错误信息
	 * @author yang.lu
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Result<?> handleError(HttpRequestMethodNotSupportedException e) {
		return Result.failure(WebError.METHOD_NOT_ALLOWED, String.format("请求方法不被允许或不适用于请求的资源: %s", e.getMessage()));
	}

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
