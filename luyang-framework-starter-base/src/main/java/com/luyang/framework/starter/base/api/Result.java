package com.luyang.framework.starter.base.api;

import com.luyang.framework.starter.base.enums.IBaseEnum;
import com.luyang.framework.starter.base.enums.ResultEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 响应包装体
 *
 * @author yang.lu
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Result<T> implements Serializable {

	@Serial
	private static final long serialVersionUID = 2491076974985477952L;

	private String code;
	private String message;
	private boolean success;
	private T data;

	private Result(IBaseEnum<String> IBaseEnum) {
		this(IBaseEnum, IBaseEnum.getMessage(), null);
	}

	private Result(IBaseEnum<String> IBaseEnum, String message) {
		this(IBaseEnum, message, null);
	}

	private Result(IBaseEnum<String> IBaseEnum, String message, T data) {
		this(IBaseEnum.getMessage(), message, data);
	}

	private Result(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.success = ResultEnum.SUCCESS.equals(code);
		this.data = data;
	}

	public static <T> Result<T> success() {
		return success(null);
	}

	public static <T> Result<T> success(T data) {
		return new Result<T>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data);
	}

	public static <T> Result<T> failure(String message) {
		return new Result<>(ResultEnum.FAILURE, message);
	}

	public static <T> Result<T> failure(String code, String message) {
		return new Result<>(code, message, null);
	}

	public static <T> Result<T> failure(String code, String message, T data) {
		return new Result<>(code, message, data);
	}
}
