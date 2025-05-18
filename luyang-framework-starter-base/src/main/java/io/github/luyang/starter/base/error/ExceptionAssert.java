package io.github.luyang.starter.base.error;

import cn.hutool.core.util.ObjectUtil;
import io.github.luyang.starter.base.enums.IBaseEnum;

/**
 * 断言业务异常
 *
 * @author yang.lu
 */
public interface ExceptionAssert<T> extends IBaseEnum<T> {

	/**
	 * 抛出当前枚举项对应的BusinessException异常
	 *
	 * @author yang.lu
	 */
	default void exception() {
		throw new BusinessException(this);
	}

	/**
	 * 断言是否为假，如果为 true 抛出BusinessException异常
	 *
	 * @param expression 布尔值
	 * @author yang.lu
	 */
	default void isFalse(boolean expression) {
		if (expression) {
			exception();
		}
	}

	/**
	 * 断言是否为真，如果为 false 抛出BusinessException异常
	 *
	 * @param expression 布尔值
	 * @author yang.lu
	 */
	default void isTrue(boolean expression) {
		if (!expression) {
			exception();
		}
	}

	/**
	 * 断言是否为真，如果为 false 抛出BusinessException异常，可指定错误消息模板和参数
	 *
	 * @param expression       布尔值
	 * @param errorMsgTemplate 错误消息模板
	 * @param params           错误消息参数
	 * @author yang.lu
	 */
	default void isTrue(boolean expression, String errorMsgTemplate, Object... params) {
		if (!expression) {
			throw new BusinessException(errorMsgTemplate).args(params);
		}
	}

	/**
	 * 断言对象是否不为null，如果为 null 抛出BusinessException异常
	 *
	 * @param obj 被检查对象
	 * @author yang.lu
	 */
	default void notNull(Object obj) {
		if (ObjectUtil.isNull(obj)) {
			exception();
		}
	}

	/**
	 * 断言对象是否不为null，如果为null 抛出BusinessException异常，并使用指定的函数获取错误信息返回
	 *
	 * @param obj              被检查对象
	 * @param errorMsgTemplate 错误消息模板
	 * @param params           错误消息参数
	 * @author yang.lu
	 */
	default void notNull(Object obj, String errorMsgTemplate, Object... params) {
		if (ObjectUtil.isNull(obj)) {
			throw new BusinessException(errorMsgTemplate).args(params);
		}
	}

	/**
	 * 断言对象是否为null，如果不为 null 抛出BusinessException异常
	 *
	 * @param obj 被检查对象
	 * @author yang.lu
	 */
	default void isNull(Object obj) {
		if (ObjectUtil.isNotNull(obj)) {
			exception();
		}
	}

	/**
	 * 断言对象是否为null，如果不为null 抛出BusinessException异常，并使用指定的函数获取错误信息返回
	 *
	 * @param obj              被检查对象
	 * @param errorMsgTemplate 错误消息模板
	 * @param params           错误消息参数
	 * @author yang.lu
	 */
	default void isNull(Object obj, String errorMsgTemplate, Object... params) {
		if (ObjectUtil.isNotNull(obj)) {
			throw new BusinessException(errorMsgTemplate).args(params);
		}
	}
}
