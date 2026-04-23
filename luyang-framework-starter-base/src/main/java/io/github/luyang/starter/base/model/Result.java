package io.github.luyang.starter.base.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.github.luyang.starter.base.enums.IBaseEnum;
import io.github.luyang.starter.base.enums.ResultEnum;
import io.github.luyang.starter.base.util.TraceIdUtil;
import io.github.luyang.starter.base.util.jackson.JsonUtil;
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
@JsonPropertyOrder({"requestId", "code", "success", "message", "data"})
public class Result<T> implements Serializable {

	@Serial
	private static final long serialVersionUID = 2491076974985477952L;

	private String requestId;
	private String code;
	private String message;
	private boolean success;
	private T data;

	private Result(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.success = ResultEnum.SUCCESS.equals(code);
		this.requestId = TraceIdUtil.get();
		this.data = data;
	}

	public static <T> Result<T> success() {
		return success(null);
	}

	public static <T> Result<T> success(T data) {
		return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data);
	}

	public static <T> Result<T> failure(String message) {
		return failure(ResultEnum.FAILURE.getCode(), message);
	}

	public static <T> Result<T> failure(String code, String message) {
		return new Result<>(code, message, null);
	}

	public static <T> Result<T> failure(String code, String message, T data) {
		return new Result<>(code, message, data);
	}

	public static <T> Result<T> failure(IBaseEnum<String> baseEnum) {
		return new Result<>(baseEnum.getCode(), baseEnum.getMessage(), null);
	}

	public static <T> Result<T> failure(IBaseEnum<String> baseEnum, String message) {
		return new Result<>(baseEnum.getCode(), message, null);
	}

	@Override
	public String toString() {
		return JsonUtil.toJsonString(this);
	}
}
