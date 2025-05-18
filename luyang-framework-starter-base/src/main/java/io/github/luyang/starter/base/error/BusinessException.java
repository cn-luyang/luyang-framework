package io.github.luyang.starter.base.error;

import cn.hutool.core.util.StrUtil;
import io.github.luyang.starter.base.enums.IBaseEnum;
import io.github.luyang.starter.base.enums.ResultEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常
 *
 * @author yang.lu
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

	private String code;
	private Object data;
	private Object[] args;

	private static String getDefaultCode(String code) {
		return StrUtil.blankToDefault(code, ResultEnum.FAILURE.getMessage());
	}

	public BusinessException(String message) {
		this(null, message, null);
	}

	public BusinessException(String message, Throwable cause) {
		this(null, message, cause);
	}

	public BusinessException(String code, String message) {
		this(code, message, null);
	}

	public BusinessException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = getDefaultCode(code);
	}

	public BusinessException(IBaseEnum<?> baseEnum) {
		this(baseEnum, null);
	}

	public BusinessException(IBaseEnum<?> baseEnum, Throwable cause) {
		super(baseEnum.getMessage(), cause);
		this.code = StrUtil.toStringOrNull(baseEnum.getCode());
	}

	public static BusinessException of(String message) {
		return new BusinessException(message);
	}

	public static BusinessException of(String message, Throwable cause) {
		return new BusinessException(message, cause);
	}

	public static BusinessException of(String code, String message) {
		return new BusinessException(code, message);
	}

	public static BusinessException of(String code, String message, Throwable cause) {
		return new BusinessException(code, message, cause);
	}

	public static BusinessException of(IBaseEnum<?> baseEnum) {
		return new BusinessException(baseEnum);
	}

	public static BusinessException of(IBaseEnum<?> baseEnum, Throwable cause) {
		return new BusinessException(baseEnum, cause);
	}

	public BusinessException code(String code) {
		this.code = getDefaultCode(code);
		return this;
	}

	public BusinessException data(Object data) {
		this.data = data;
		return this;
	}

	public BusinessException args(Object... args) {
		this.args = args;
		return this;
	}
}
