package io.github.luyang.starter.base.error;

import io.github.luyang.starter.base.enums.IBaseEnum;

/**
 * 断言业务异常
 *
 * @author yang.lu
 */
public interface ExceptionAssert<T> extends IBaseEnum<T> {

	default void isFalse(boolean expression) {
		if (expression) {
			throw  new BusinessException(this);
		}
	}
}
