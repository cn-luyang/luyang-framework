package io.github.luyang.starter.base.validation;

import cn.hutool.core.util.StrUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * {@link InValues} 注解的验证器实现
 *
 * @author yang.lu
 */
public class InValuesValidator implements ConstraintValidator<InValues, String> {

	private InValues annotation;

	@Override
	public void initialize(InValues constraintAnnotation) {
		this.annotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(String val, ConstraintValidatorContext context) {
		boolean allowEmpty = annotation.allowEmpty();
		// 允许为空且值为null 则放行
		if (allowEmpty && val == null) {
			return true;
		}

		String[] values = annotation.values();
		for (String value : values) {
			if (StrUtil.equals(value, val)) {
				return true;
			}
		}

		return false;
	}
}
