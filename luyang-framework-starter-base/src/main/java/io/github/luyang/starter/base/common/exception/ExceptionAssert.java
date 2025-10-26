package io.github.luyang.starter.base.common.exception;

import io.github.luyang.base.util.ArrayUtil;
import io.github.luyang.base.util.CollUtil;
import io.github.luyang.base.util.ObjectUtil;
import io.github.luyang.base.util.StrUtil;
import io.github.luyang.starter.base.common.enums.IBaseEnum;

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
     * 创建业务异常实例
     *
     * @author yang.lu
     */
    default BusinessException createException() {
        return new BusinessException(this);
    }

    /**
     * 抛出当前枚举项对应的BusinessException异常
     *
     * @author yang.lu
     */
    default void exception() {
        throw createException();
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
     * 断言是否为假，如果为 true 抛出BusinessException异常
     *
     * @param expression 布尔值
     * @author yang.lu
     */
    default void isFalse(boolean expression) {
        doThrow(expression, this::createException);
    }

    /**
     * 断言是否为假，如果为 true 抛出BusinessException异常（带异常处理）
     *
     * @param expression       布尔值
     * @param exceptionHandler 异常处理器
     * @author yang.lu
     */
    default void isFalse(boolean expression, Consumer<BusinessException> exceptionHandler) {
        doThrow(expression, this::createException, exceptionHandler);
    }

    /**
     * 断言是否为真，如果为 false 抛出BusinessException异常
     *
     * @param expression 布尔值
     * @author yang.lu
     */
    default void isTrue(boolean expression) {
        doThrow(!expression, this::createException);
    }

    /**
     * 断言是否为真，如果为 false 抛出BusinessException异常（带异常处理）
     *
     * @param expression       布尔值
     * @param exceptionHandler 异常处理器
     * @author yang.lu
     */
    default void isTrue(boolean expression, Consumer<BusinessException> exceptionHandler) {
        doThrow(!expression, this::createException, exceptionHandler);
    }

    /**
     * 断言对象是否不为null，如果为 null 抛出BusinessException异常
     *
     * @param obj 被检查对象
     * @author yang.lu
     */
    default void notNull(Object obj) {
        doThrow(ObjectUtil.isNull(obj), this::createException);
    }

    /**
     * 断言对象是否不为null，如果为 null 抛出BusinessException异常（带异常处理）
     *
     * @param obj              被检查对象
     * @param exceptionHandler 异常处理器
     * @author yang.lu
     */
    default void notNull(Object obj, Consumer<BusinessException> exceptionHandler) {
        doThrow(ObjectUtil.isNull(obj), this::createException, exceptionHandler);
    }

    /**
     * 断言对象是否为null，如果不为 null 抛出BusinessException异常
     *
     * @param obj 被检查对象
     * @author yang.lu
     */
    default void isNull(Object obj) {
        doThrow(ObjectUtil.isNotNull(obj), this::createException);
    }

    /**
     * 断言对象是否为null，如果不为 null 抛出BusinessException异常（带异常处理）
     *
     * @param obj              被检查对象
     * @param exceptionHandler 异常处理器
     * @author yang.lu
     */
    default void isNull(Object obj, Consumer<BusinessException> exceptionHandler) {
        doThrow(ObjectUtil.isNotNull(obj), this::createException, exceptionHandler);
    }

    /**
     * 断言字符串非空（非null且非纯空格）
     */
    default void notBlank(String text) {
        doThrow(StrUtil.isBlank(text), this::createException);
    }

    /**
     * 断言字符串匹配正则表达式
     */
    default void matches(String text, String regex) {
        doThrow(text == null || !text.matches(regex), this::createException);
    }

    /**
     * 断言集合非空
     */
    default void notEmpty(Collection<?> collection) {
        doThrow(CollUtil.isEmpty(collection), this::createException);
    }

    /**
     * 断言数组非空
     */
    default void notEmpty(Object[] array) {
        doThrow(ArrayUtil.isEmpty(array), this::createException);
    }

    /**
     * 断言数值大于指定值
     */
    default void greaterThan(Number value, Number min) {
        doThrow(value == null || value.doubleValue() <= min.doubleValue(),
            this::createException, null);
    }

    /**
     * 断言数值在范围内[min, max]
     */
    default void between(Number value, Number min, Number max) {
        doThrow(value == null ||
                value.doubleValue() < min.doubleValue() ||
                value.doubleValue() > max.doubleValue(),
            this::createException, null);
    }

    /**
     * 断言是否为真，如果为 false 抛出BusinessException异常，可指定错误消息模板和参数
     *
     * @param expression      布尔值
     * @param messageTemplate 错误消息模板
     * @param params          错误消息参数
     * @author yang.lu
     */
    default void isTrue(boolean expression, String messageTemplate, Object... params) {
        doThrow(!expression, () -> new BusinessException(messageTemplate).args(params));
    }

    /**
     * 断言是否为真，如果为 false 抛出BusinessException异常，可指定错误消息模板和参数
     *
     * @param expression       布尔值
     * @param messageTemplate  错误消息模板
     * @param exceptionHandler 异常处理器
     * @param params           错误消息参数
     * @author yang.lu
     */
    default void isTrue(boolean expression,
                        String messageTemplate,
                        Consumer<BusinessException> exceptionHandler,
                        Object... params) {

        doThrow(!expression, () -> new BusinessException(messageTemplate).args(params), exceptionHandler);
    }

    /**
     * 断言对象是否不为null，如果为null 抛出BusinessException异常，并使用指定的函数获取错误信息返回
     *
     * @param obj             被检查对象
     * @param messageTemplate 错误消息模板
     * @param params          错误消息参数
     * @author yang.lu
     */
    default void notNull(Object obj, String messageTemplate, Object... params) {
        doThrow(ObjectUtil.isNull(obj), () -> new BusinessException(messageTemplate).args(params));
    }

    /**
     * 断言对象是否不为null，如果为null 抛出BusinessException异常，并使用指定的函数获取错误信息返回
     *
     * @param obj              被检查对象
     * @param messageTemplate  错误消息模板
     * @param exceptionHandler 异常处理器
     * @param params           错误消息参数
     * @author yang.lu
     */
    default void notNull(Object obj, String messageTemplate, Consumer<BusinessException> exceptionHandler, Object... params) {
        doThrow(ObjectUtil.isNull(obj), () -> new BusinessException(messageTemplate).args(params), exceptionHandler);
    }

    /**
     * 断言对象是否为null，如果不为null 抛出BusinessException异常，并使用指定的函数获取错误信息返回
     *
     * @param obj             被检查对象
     * @param messageTemplate 错误消息模板
     * @param params          错误消息参数
     * @author yang.lu
     */
    default void isNull(Object obj, String messageTemplate, Object... params) {
        doThrow(ObjectUtil.isNotNull(obj), () -> new BusinessException(messageTemplate).args(params));
    }

    /**
     * 断言对象是否为null，如果不为null 抛出BusinessException异常，并使用指定的函数获取错误信息返回
     *
     * @param obj              被检查对象
     * @param messageTemplate  错误消息模板
     * @param exceptionHandler 异常处理器
     * @param params           错误消息参数
     * @author yang.lu
     */
    default void isNull(Object obj, String messageTemplate, Consumer<BusinessException> exceptionHandler, Object... params) {
        doThrow(ObjectUtil.isNotNull(obj), () -> new BusinessException(messageTemplate).args(params), exceptionHandler);
    }
}
