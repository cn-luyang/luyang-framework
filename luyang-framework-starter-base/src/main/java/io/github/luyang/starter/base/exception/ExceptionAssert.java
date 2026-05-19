package io.github.luyang.starter.base.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.github.luyang.starter.base.enums.IBaseEnum;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 业务异常断言接口
 *
 * @author yang.lu
 */
public interface ExceptionAssert<T> extends IBaseEnum<T> {

	/**
	 * 创建当前枚举对应的业务异常（支持消息占位符参数）
	 *
	 * @param args 消息占位符参数
	 * @return BusinessException 实例
	 * @author yang.lu
	 */
	private BusinessException fail(String... args) {
		return BusinessException.of(this, args);
	}

	default void exception(String... args) {
		throw fail(args);
	}

	/**
	 * 异常处理方法
	 *
	 * @param condition         触发异常的条件（true时抛出异常）
	 * @param exceptionSupplier 异常对象提供函数
	 * @param exceptionHandler  异常处理器（可为null）
	 * @author yang.lu
	 */
	private void doThrow(boolean condition,
						 Supplier<BusinessException> exceptionSupplier,
						 Consumer<BusinessException> exceptionHandler) {
		if (condition) {
			BusinessException ex = exceptionSupplier.get();
			Optional.ofNullable(exceptionHandler).ifPresent(h -> h.accept(ex));
			throw ex;
		}
	}

	private void doThrow(boolean condition, Supplier<BusinessException> exceptionSupplier) {
		doThrow(condition, exceptionSupplier, null);
	}

	/**
	 * 断言表达式为 true，若为 false 则抛出异常
	 *
	 * @param expression 待判断的布尔表达式
	 * @param args       消息占位符参数
	 * @author yang.lu
	 */
	default void isTrue(boolean expression, String... args) {
		doThrow(!expression, () -> fail(args));
	}

	/**
	 * 断言表达式为 false，若为 true 则抛出异常
	 *
	 * @param expression 待判断的布尔表达式
	 * @param args       消息占位符参数
	 * @author yang.lu
	 */
	default void isFalse(boolean expression, String... args) {
		doThrow(expression, () -> fail(args));
	}

	/**
	 * 断言对象为 null，若不为 null 则抛出异常
	 *
	 * @param obj  被检查的对象
	 * @param args 消息占位符参数
	 * @author yang.lu
	 */
	default void isNull(Object obj, String... args) {
		doThrow(ObjectUtil.isNotNull(obj), () -> fail(args));
	}

	/**
	 * 断言对象不为 null，若为 null 则抛出异常
	 *
	 * @param obj  被检查的对象
	 * @param args 消息占位符参数
	 * @author yang.lu
	 */
	default void notNull(Object obj, String... args) {
		doThrow(ObjectUtil.isNull(obj), () -> fail(args));
	}

	/**
	 * 断言字符串不为空（不为 null 且不为空白字符串）
	 *
	 * @param str  被检查的字符串
	 * @param args 消息占位符参数
	 * @author yang.lu
	 */
	default void notBlank(String str, String... args) {
		doThrow(StrUtil.isBlank(str), () -> fail(args));
	}

	/**
	 * 断言字符串匹配指定的正则表达式
	 *
	 * @param str   被检查的字符串
	 * @param regex 正则表达式
	 * @param args  消息占位符参数（不匹配时使用）
	 * @author yang.lu
	 */
	default void matches(String str, String regex, String... args) {
		doThrow(str == null || !str.matches(regex), () -> fail(args));
	}

	/**
	 * 断言集合不为空（size > 0）
	 *
	 * @param collection 被检查的集合
	 * @param args       消息占位符参数
	 * @author yang.lu
	 */
	default void notEmpty(Collection<?> collection, String... args) {
		doThrow(CollUtil.isEmpty(collection), () -> fail(args));
	}

	/**
	 * 断言数组不为空（length > 0）
	 *
	 * @param array 被检查的数组
	 * @param args  消息占位符参数
	 * @author yang.lu
	 */
	default void notEmpty(Object[] array, String... args) {
		doThrow(ArrayUtil.isEmpty(array), () -> fail(args));
	}

	/**
	 * 断言数值大于指定值（value > min）
	 *
	 * @param value 被检查的数值
	 * @param min   最小值（不包含）
	 * @param args  消息占位符参数
	 * @author yang.lu
	 */
	default void greaterThan(Number value, Number min, String... args) {
		doThrow(value == null || value.doubleValue() <= min.doubleValue(), () -> fail(args));
	}

	/**
	 * 断言数值大于等于指定值（value >= min）
	 *
	 * @param value 被检查的数值
	 * @param min   最小值（包含）
	 * @param args  消息占位符参数
	 * @author yang.lu
	 */
	default void greaterThanOrEqual(Number value, Number min, String... args) {
		doThrow(value == null || value.doubleValue() < min.doubleValue(), () -> fail(args));
	}

	/**
	 * 断言数值在闭区间 [min, max] 内
	 *
	 * @param value 被检查的数值
	 * @param min   区间下界（包含）
	 * @param max   区间上界（包含）
	 * @param args  消息占位符参数
	 * @author yang.lu
	 */
	default void between(Number value, Number min, Number max, String... args) {
		doThrow(value == null
				|| value.doubleValue() < min.doubleValue()
				|| value.doubleValue() > max.doubleValue(),
			() -> fail(args));
	}

	/**
	 * 断言表达式为 true（支持异常被消费）
	 *
	 * @param expression 布尔表达式
	 * @param handler    异常处理器（如记录日志、埋点），可为 null
	 * @author yang.lu
	 */
	default void isTrue(boolean expression, Consumer<BusinessException> handler) {
		doThrow(!expression, this::fail, handler);
	}

	/**
	 * 断言对象不为 null（支持异常被消费）
	 *
	 * @param obj     被检查的对象
	 * @param handler 异常处理器，可为 null
	 * @author yang.lu
	 */
	default void notNull(Object obj, Consumer<BusinessException> handler) {
		doThrow(ObjectUtil.isNull(obj), this::fail, handler);
	}

	/**
	 * 断言对象是否为null，如果不为 null 抛出BusinessException异常（带异常处理）
	 *
	 * @param obj              被检查对象
	 * @param exceptionHandler 异常处理器
	 * @author yang.lu
	 */
	default void isNull(Object obj, Consumer<BusinessException> exceptionHandler) {
		doThrow(ObjectUtil.isNotNull(obj), this::fail, exceptionHandler);
	}
}
