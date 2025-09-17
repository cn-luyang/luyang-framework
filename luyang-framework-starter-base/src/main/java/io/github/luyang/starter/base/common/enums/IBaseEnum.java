package io.github.luyang.starter.base.common.enums;

import cn.hutool.core.util.ObjectUtil;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 通用枚举接口
 *
 * @author yang.lu
 */
public interface IBaseEnum<T> extends Serializable {

	T getCode();
	String getMessage();

	/**
	 * 比较两个对象是否相等
	 *
	 * @param code 待匹配Code码
	 * @return boolean
	 * @author yang.lu
	 */
	default boolean equals(String code) {
		return ObjectUtil.equals(this.getCode(), code);
	}

	/**
	 * 根据Code码匹配对应枚举 <br/>
	 *
	 * <pre class="code">
	 *     ResultEnum SUCCESS_ENUM = IBaseEnum.getByCode(ResultEnum.class, 200);
	 * </pre>
	 *
	 * @param tClass 待匹配枚举类
	 * @param code   需匹配枚举的Code码
	 * @return T
	 * @author yang.lu
	 */
	static <T, E extends IBaseEnum<T>> E getByCode(Class<E> tClass, T code) {
		return Arrays.stream(tClass.getEnumConstants())
			.filter(v -> ObjectUtil.equals(v.getCode(), code))
			.findFirst()
			.orElse(null);
	}

	/**
	 * 根据Code匹配对应枚举 <br/>
	 *
	 * <pre class="code">
	 *     ResultEnum SUCCESS_ENUM = IIBaseEnum.getByCode(ResultEnum.class, 999, () -> new IllegalArgumentException(""));
	 * </pre>
	 *
	 * @param tClass        待匹配枚举类
	 * @param code          需匹配枚举的Code码
	 * @param errorSupplier 错误抛出异常附带的消息生产接口
	 * @return T
	 * @author yang.lu
	 */
	static <T, E extends IBaseEnum<T>, X extends Throwable> E getByCode(Class<E> tClass, T code, Supplier<X> errorSupplier) throws X {
		E anyEnum = getByCode(tClass, code);
		if (null == anyEnum) {
			throw errorSupplier.get();
		}

		return anyEnum;
	}

	/**
	 * 枚举实例的代码与给定的代码匹配，则执行指定的动作 <br/>
	 *
	 * <pre class="code">
	 *     IIBaseEnum.executeIfCodeMatches(ResultEnum.SUCCESS, 0, () -> {
	 * 			业务代码...
	 *     });
	 * </pre>
	 *
	 * @param enumInstance 待匹配枚举
	 * @param code         需匹配枚举的Code码
	 * @param action       业务代码
	 * @author yang.lu
	 */
	static <T, E extends IBaseEnum<T>> void executeIfCodeMatches(E enumInstance, T code, Runnable action) {
		if (ObjectUtil.equals(enumInstance.getCode(), code)) {
			action.run();
		}
	}

	/**
	 * 枚举实例的代码与给定的代码不匹配，则执行指定的动作 <br/>
	 *
	 * <pre class="code">
	 *     IIBaseEnum.executeIfCodeNotMatches(ResultEnum.SUCCESS, 1, () -> {
	 * 			业务代码...
	 *     });
	 * </pre>
	 *
	 * @param enumInstance 待匹配枚举
	 * @param code         需匹配枚举的Code码
	 * @param action       业务代码
	 * @author yang.lu
	 */
	static <T, E extends IBaseEnum<T>> void executeIfCodeNotMatches(E enumInstance, T code, Runnable action) {
		if (!ObjectUtil.equals(enumInstance.getCode(), code)) {
			action.run();
		}
	}

	/**
	 * 根据枚举属性值匹配对应的枚举 <br/>
	 *
	 * <pre class="code">
	 *     ResultEnum SUCCESS_ENUM = IIBaseEnum.getByProperty(ResultEnum.class, ResultEnum::getDesc, "success");
	 * </pre>
	 *
	 * @param tClass   待匹配枚举类
	 * @param property 待匹配枚举属性
	 * @param value    需匹配枚举的属性值
	 * @return T
	 * @author yang.lu
	 */
	static <T, E extends IBaseEnum<T>, P> E getByProperty(Class<E> tClass, Function<E, P> property, P value) {
		return Arrays.stream(tClass.getEnumConstants())
			.filter(v -> ObjectUtil.equals(property.apply(v), value))
			.findFirst()
			.orElse(null);
	}

	/**
	 * 根据枚举属性值匹配对应的枚举 <br/>
	 *
	 * <pre class="code">
	 *     ResultEnum SUCCESS_ENUM = IIBaseEnum.getByProperty(ResultEnum.class, ResultEnum::getDesc, "success", () -> new IllegalArgumentException(""));
	 * </pre>
	 *
	 * @param tClass        待匹配枚举类
	 * @param property      待匹配枚举属性
	 * @param value         需匹配枚举的属性值
	 * @param errorSupplier 错误抛出异常附带的消息生产接口
	 * @return T
	 * @author yang.lu
	 */
	static <T, E extends IBaseEnum<T>, P, X extends Throwable> E getByProperty(Class<E> tClass, Function<E, P> property, P value, Supplier<X> errorSupplier) throws X {
		E anyProperty = getByProperty(tClass, property, value);
		if (null == anyProperty) {
			throw errorSupplier.get();
		}

		return anyProperty;
	}
}
