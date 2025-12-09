package io.github.luyang.starter.base.model;

import cn.hutool.core.util.ObjectUtil;
import io.github.luyang.starter.base.enums.ResultEnum;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 响应包装体操作
 * 简化对{@link Result}的操作
 *
 * @author yang.lu
 */
public class ResultOps<T> {

    /** 响应包装体 */
    private final Result<T> value;

    /**
     * 创建ResultOps实例
     *
     * @param value 结果集
     * @author yang.lu
     */
    private ResultOps(Result<T> value) {
		this.value = Objects.requireNonNull(value, "Result cannot be null");
    }

    /**
     * 响应包装体的code值进行相等比较
     *
     * @param code 待比较值
     * @return boolean true相等 false不等
     * @author yang.lu
     */
    private boolean codeEquals(String code) {
        return ObjectUtil.equals(this.value.getCode(), code);
    }

    /**
     * 响应包装体的code值进行非等比较
     *
     * @param code 待比较值
     * @return boolean true不等 false相等
     * @author yang.lu
     */
    private boolean codeNotEquals(String code) {
        return !codeEquals(code);
    }

    /**
     * 获取响应包装体的data字段值并用Optional包装
	 * 使用案例：
	 * ResultOps.of(Result.success("data"))
	 *         .getDataOps()
	 *         .ifPresent(data -> System.out.println("数据存在：" + data));
     *
     * @return Optional<T> Optional包装的data
     * @author yang.lu
     */
	public Optional<T> getDataOps() {
		return Optional.ofNullable(value.getData());
	}

	/**
	 * 直接获取data值，可能为null
	 * 使用案例：
	 * String data = ResultOps.of(result).getData();
	 *
	 * @return T data值
	 * @author yang.lu
	 */
	public T getData() {
		if (getDataOps().isPresent()) {
			return getDataOps().get();
		}

		return null;
	}

	/**
	 * 获取data值，如果为null则返回默认值
	 * 使用案例：
	 * String data = ResultOps.of(result).getOrElse("默认值");
	 *
	 * @param defaultValue 默认值
	 * @return T data值或默认值
	 * @author yang.lu
	 */
	public T getOrElse(T defaultValue) {
		return getDataOps().orElse(defaultValue);
	}

	/**
	 * 创建ResultOps实例
	 * 使用案例：
	 * ResultOps<String> ops = ResultOps.of(Result.success("成功"));
	 *
	 * @param value 响应包装体
	 * @return ResultOps<T> 操作实例
	 * @throws NullPointerException 参数为null时抛出
	 * @author yang.lu
	 */
	public static <T> ResultOps<T> of(Result<T> value) {
		return new ResultOps<>(value);
	}

	/**
	 * 当code不等于期望值时抛出异常
	 * 使用案例：
	 * ResultOps.of(result).codeNotEquals("200", r -> new RuntimeException("接口异常：" + r.getMessage()));
	 *
	 * @param expectCode 期望的code值
	 * @param func       异常生成函数
	 * @return ResultOps<T> 当前实例
	 * @throws X 自定义异常
	 * @author yang.lu
	 */
	public <X extends Throwable> ResultOps<T> codeNotEquals(String expectCode, Function<Result<T>, X> func) throws X {
		if (codeNotEquals(expectCode)) {
			throw func.apply(value);
		}
		return this;
	}

	/**
	 * 当code不等于期望值时抛出异常
	 * 使用案例：
	 * ResultOps.of(result).codeNotEquals("200", () -> new RuntimeException("接口调用失败"));
	 *
	 * @param expectCode    期望的code值
	 * @param errorSupplier 异常提供者
	 * @return ResultOps<T> 当前实例
	 * @throws X 自定义异常
	 * @author yang.lu
	 */
	public <X extends Throwable> ResultOps<T> codeNotEquals(String expectCode, Supplier<X> errorSupplier) throws X {
		if (codeNotEquals(expectCode)) {
			throw errorSupplier.get();
		}
		return this;
	}

	/**
	 * 当响应失败时抛出异常
	 * 使用案例：
	 * ResultOps.of(result).ifFailure(() -> new RuntimeException("接口调用失败"));
	 *
	 * @param errorSupplier 异常提供者
	 * @return ResultOps<T> 当前实例
	 * @throws X 自定义异常
	 * @author yang.lu
	 */
	public <X extends Throwable> ResultOps<T> ifFailure(Supplier<X> errorSupplier) throws X {
		return codeNotEquals(ResultEnum.SUCCESS.getCode(), errorSupplier);
	}

	/**
	 * 当响应失败时抛出异常
	 * 使用案例：
	 * ResultOps.of(Result.failure("失败")).ifFailure(r -> new RuntimeException(r.getMessage()));
	 *
	 * @param func 异常生成函数
	 * @return ResultOps<T> 当前实例
	 * @throws X 自定义异常
	 * @author yang.lu
	 */
	public <X extends Throwable> ResultOps<T> ifFailure(Function<Result<T>, X> func) throws X {
		return codeNotEquals(ResultEnum.SUCCESS.getCode(), func);
	}

	/**
	 * 当data为null时抛出异常
	 * 使用案例：
	 * ResultOps.of(result).dataIsNull(() -> new RuntimeException("数据为空"));
	 *
	 * @param errorSupplier 异常提供者
	 * @return ResultOps<T> 当前实例
	 * @throws X 自定义异常
	 * @author yang.lu
	 */
	public <X extends Throwable> ResultOps<T> dataIsNull(Supplier<X> errorSupplier) throws X {
		if (null == value.getData()) {
			throw errorSupplier.get();
		}
		return this;
	}

	/**
	 * 当data为null时抛出异常
	 * 使用案例：
	 * ResultOps.of(result).dataIsNull(r -> new RuntimeException("数据为空，code：" + r.getCode()));
	 *
	 * @param func 异常生成函数
	 * @return ResultOps<T> 当前实例
	 * @throws X 自定义异常

	 */
	public <X extends Throwable> ResultOps<T> dataIsNull(Function<Result<T>, X> func) throws X {
		if (Objects.isNull(value.getData())) {
			throw func.apply(value);
		}
		return this;
	}

	/**
	 * 当data为null时记录日志并抛出异常
	 * 使用案例：
	 * ResultOps.of(result)
	 * 		.dataIsNull(
	 * 			r -> logger.warn("数据为空，完整结果：{}", r),
	 * 			r -> new BusinessException("数据为空，msg：" + r.getMessage())
	 * 		);
	 *
	 * @param logAction         日志记录动作
	 * @param exceptionFunction 异常生成函数
	 * @return ResultOps<T> 当前实例
	 * @throws X 自定义异常
	 * @author yang.lu
	 */
	public <X extends Throwable> ResultOps<T> dataIsNull(Consumer<Result<T>> logAction,
														 Function<Result<T>, X> exceptionFunction) throws X {
		if (null == value.getData()) {
			logAction.accept(value);
			throw exceptionFunction.apply(value);
		}
		return this;
	}

	/**
	 * 获取data值，如果为null则抛出异常
	 * 使用案例：
	 * String data = ResultOps.of(result).getOrThrow(() -> new BusinessException("数据为空"));
	 *
	 * @param errorSupplier 异常提供者
	 * @return T data值
	 * @throws X 自定义异常
	 * @author yang.lu
	 */
	public <X extends Throwable> T getOrThrow(Supplier<X> errorSupplier) throws X {
		if (null == value.getData()) {
			throw errorSupplier.get();
		}
		return value.getData();
	}

	/**
	 * 获取data值，如果为null则抛出异常
	 * 使用案例：
	 * String data = ResultOps.of(result).getOrThrow(r -> new BusinessException("数据为空，code：" + r.getCode()));
	 *
	 * @param exceptionFunction 异常生成函数
	 * @return T data值
	 * @throws X 自定义异常
	 * @author yang.lu
	 */
	public <X extends Throwable> T getOrThrow(Function<Result<T>, X> exceptionFunction) throws X {
		if (null == value.getData()) {
			throw exceptionFunction.apply(value);
		}
		return value.getData();
	}
}
