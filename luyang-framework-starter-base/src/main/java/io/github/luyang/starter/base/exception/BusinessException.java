package io.github.luyang.starter.base.exception;

import cn.hutool.core.text.CharSequenceUtil;
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

	private BusinessException(String code, String message, Object[] args, Object data, Throwable cause) {
		super(CharSequenceUtil.format(message, args), cause);
		this.code = StrUtil.blankToDefault(code, ResultEnum.FAILURE.getCode());
		this.args = args;
		this.data = data;
	}

	public static BusinessException of(String message, String... args) {
		return new BusinessException(null, message, args,  null, null);
	}

	public static BusinessException of(String code, String message, String... args) {
		return new BusinessException(code, message, args,  null, null);
	}

	public static BusinessException of(IBaseEnum<?> baseEnum, String... args) {
		return new BusinessException(StrUtil.toStringOrNull(baseEnum.getCode()), baseEnum.getMessage(), args,  null, null);
	}

	public BusinessException data(Object data) {
		this.data = data;
		return this;
	}
}
