package com.luyang.framework.starter.base.error;

import com.luyang.framework.starter.base.enums.IBaseEnum;

/**
 * 断言业务异常
 *
 * @author yang.lu
 */
public interface ExceptionAssert<T> extends IBaseEnum<T> {

	default RuntimeException isFalse(boolean condition) {
		return new BusinessException(this);
	}
}
