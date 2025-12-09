package io.github.luyang.starter.base.util;

import cn.hutool.core.collection.CollUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.experimental.UtilityClass;

import java.util.Set;

/**
 * 校验工具类
 *
 * @author yang.lu
 */
@UtilityClass
public class ValidationUtil {

	private static final Validator VALIDATOR;

	static {
		try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
			VALIDATOR = validatorFactory.getValidator();
		}
	}

	/**
	 * 校验对象
	 *
	 * @param object 待校验对象
	 * @param groups 校验分组
	 * @author yang.lu
	 */
	public static void valid(Object object, Class<?>... groups) {
		valid(VALIDATOR.validate(object, groups));
	}

	/**
	 * 校验对象的指定字段
	 *
	 * @param object    待校验对象
	 * @param fieldName 待校验字段
	 * @param groups    校验分组
	 * @author yang.lu
	 */
	public static void valid(Object object, String fieldName, Class<?>... groups) {
		valid(VALIDATOR.validateProperty(object, fieldName, groups));
	}

	/**
	 * 处理校验结果
	 *
	 * @param constraintViolations 校验结果集合
	 * @author yang.lu
	 */
	private static void valid(Set<ConstraintViolation<Object>> constraintViolations) {
		if (CollUtil.isNotEmpty(constraintViolations)) {
			throw new ConstraintViolationException(constraintViolations);
		}
	}
}
