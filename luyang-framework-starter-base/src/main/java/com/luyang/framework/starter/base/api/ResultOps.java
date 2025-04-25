package com.luyang.framework.starter.base.api;

import cn.hutool.core.util.ObjectUtil;
import com.luyang.framework.starter.base.enums.ResultEnum;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 响应包装体操作
 * 简化对{@link Result}的操作
 *
 * @author yang.lu
 */
public class ResultOps<T> {

	/**
	 * 响应包装体
	 */
	private final Result<T> value;

	/**
	 * 构造 响应包装体 的操作实例
	 *
	 * @param value 结果集
	 * @author yang.lu
	 */
	private ResultOps(Result<T> value) {
		this.value = value;
	}

	/**
	 * 对 响应包装体 的{@code code}值进行相等比较
	 *
	 * @param code 待比较值
	 * @return boolean {@code true}相等 {@code false}不等
	 * @author yang.lu
	 */
	private boolean codeEquals(String code) {
		return ObjectUtil.equals(this.value.getCode(), code);
	}

	/**
	 * 对 响应包装体 的{@code code}值进行非等比较
	 *
	 * @param code 待比较值
	 * @return boolean {@code true}不等 {@code false}相等
	 * @author yang.lu
	 */
	private boolean codeNotEquals(String code) {
		return !codeEquals(code);
	}

	/**
	 * 获取 响应包装体 的{@code data}字段值并用Optional包装
	 *
	 * <pre class="code">
	 *     ResultOps.of(Result.success())
	 *             .assertSuccess(r -> new RuntimeException("接口调用失败:" + r.getMessage))
	 *               .getData()
	 *               .orElseThrow(() -> new RuntimeException("接口响应数据为空"));
	 * </pre>
	 *
	 * @return Optional<T> Optional 包装的 {@code data}
	 * @author yang.lu
	 */
	public Optional<T> getData() {
		return Optional.ofNullable(value.getData());
	}

	/**
	 * 返回一个非{@code null}的 响应包装体
	 *
	 * <pre class="code">
	 *     ResultOps.of(Result.success());
	 * </pre>
	 *
	 * @param value 响应包装体
	 * @return ResultOps<T> 返回非{@code null}的操作实例
	 * @throws NullPointerException 为{@code null}抛出
	 * @author yang.lu
	 */
	public static <T> ResultOps<T> of(Result<T> value) {
		return new ResultOps<>(Objects.requireNonNull(value));
	}

	/**
	 * 断言 响应包装体 的{@code code}值是否与expectCode相等，不相等抛出指定异常
	 *
	 * <pre class="code">
	 *     ResultOps.of(Result.success()).assertCode(expect, () -> new RuntimeException("接口调用失败"));
	 * </pre>
	 *
	 * @param expectCode 预期的code值
	 * @param func       错误抛出指定异常
	 * @return ResultOps<T> 操作实例
	 * @throws X 断言失败时抛出
	 * @author yang.lu
	 */
	public <X extends Throwable> ResultOps<T> assertCode(String expectCode, Function<? super Result<T>, ? extends X> func) throws X {
		if (codeNotEquals(expectCode)) {
			throw func.apply(value);
		}

		return this;
	}

	/**
	 * 断言 响应包装体 的{@code code}值是否与expectCode相等，不相等抛出指定异常
	 *
	 * <pre class="code">
	 *     ResultOps.of(Result.success()).assertCode(expect, r -> new RuntimeException(r.getMessage()));
	 * </pre>
	 *
	 * @param expectCode    预期的code值
	 * @param errorSupplier 错误抛出指定异常
	 * @return ResultOps<T> 操作实例
	 * @throws X 断言失败时抛出
	 * @author yang.lu
	 */
	public <X extends Throwable> ResultOps<T> assertCode(String expectCode, Supplier<X> errorSupplier) throws X {
		if (codeNotEquals(expectCode)) {
			throw errorSupplier.get();
		}

		return this;
	}

	/**
	 * 断言 响应包装体 的{@code}为成功
	 *
	 * <pre class="code">
	 *     ResultOps.of(Result.failure("接口调用失败")).assertSuccess(r -> new RuntimeException(r.getMessage()));
	 * </pre>
	 *
	 * @param func 错误抛出指定异常
	 * @return ResultOps<T> 操作实例
	 * @throws X 断言失败时抛出
	 * @author yang.lu
	 */
	public <X extends Throwable> ResultOps<T> assertSuccess(Function<? super Result<T>, ? extends X> func) throws X {
		return assertCode(ResultEnum.SUCCESS.getCode(), func);
	}

	/**
	 * 断言 响应包装体 的{@code}为成功
	 *
	 * <pre class="code">
	 *     ResultOps.of(Result.success()).assertSuccess(() -> new RuntimeException("接口调用失败"));
	 * </pre>
	 *
	 * @param errorSupplier 错误抛出指定异常
	 * @return ResultOps<T> 操作实例
	 * @throws X 断言失败时抛出
	 * @author yang.lu
	 */
	public <X extends Throwable> ResultOps<T> assertSuccess(Supplier<X> errorSupplier) throws X {
		return assertCode(ResultEnum.SUCCESS.getCode(), errorSupplier);
	}

	/**
	 * 断言 响应包装体 {@code data}值不为{@code null},{@code null}则抛出指定异常
	 *
	 * <pre class="code">
	 *     ResultOps.of(Result.success())
	 *             .assertSuccess(() -> new RuntimeException("接口调用失败"))
	 *             .assertDataNotNull(r -> new RuntimeException("接口响应数据为null"));
	 * </pre>
	 *
	 * @param func 错误抛出指定异常
	 * @return ResultOps<T> 操作实例
	 * @author yang.lu
	 */
	public <X extends Throwable> ResultOps<T> assertDataNotNull(Function<? super Result<T>, ? extends X> func) throws X {
		if (Objects.isNull(value.getData())) {
			throw func.apply(value);
		}

		return this;
	}

	/**
	 * 断言 响应包装体 {@code data}值不为{@code null},{@code null}则抛出指定异常
	 *
	 * <pre class="code">
	 *     ResultOps.of(Result.success())
	 *             .assertSuccess(() -> new RuntimeException("接口调用失败"))
	 *             .assertDataNotNull(() -> new RuntimeException("接口响应数据为null"));
	 * </pre>
	 *
	 * @param errorSupplier 错误抛出指定异常
	 * @return ResultOps<T> 操作实例
	 * @throws X 断言失败时抛出
	 * @author yang.lu
	 */
	public <X extends Throwable> ResultOps<T> assertDataNotNull(Supplier<X> errorSupplier) throws X {
		if (Objects.isNull(value.getData())) {
			throw errorSupplier.get();
		}

		return this;
	}
}
