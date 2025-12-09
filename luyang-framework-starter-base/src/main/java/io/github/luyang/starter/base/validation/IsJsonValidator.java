package io.github.luyang.starter.base.validation;

import cn.hutool.core.text.CharSequenceUtil;
import io.github.luyang.starter.base.util.jackson.JsonUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * JSON 格式验证器
 *
 * @author yang.lu
 */
public class IsJsonValidator implements ConstraintValidator<IsJson, String> {

	private IsJson annotation;

	@Override
	public void initialize(IsJson constraintAnnotation) {
		this.annotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(String jsonStr, ConstraintValidatorContext constraintValidatorContext) {

		// 允许为空且值为null 则放行
		if (annotation.allowEmpty() && CharSequenceUtil.isBlank(jsonStr)) {
			return true;
		}

		return JsonUtil.isJson(jsonStr);
	}
}
