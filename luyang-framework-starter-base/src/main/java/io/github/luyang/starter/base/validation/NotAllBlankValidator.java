package io.github.luyang.starter.base.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * {@link NotAllBlank} 注解实现
 *
 * @author yang.lu
 */
public class NotAllBlankValidator implements ConstraintValidator<NotAllBlank, Object> {

	private String[] fields;

	@Override
	public void initialize(NotAllBlank annotation) {
		this.fields = annotation.fields();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null) {
			return false;
		}

		// 如果未指定任何字段，默认校验通过
		if (ObjectUtils.isEmpty(fields)) {
			return true;
		}

		BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);

		for (String field : fields) {

			if (!beanWrapper.isReadableProperty(field)) {
				continue;
			}

			Object fieldValue = beanWrapper.getPropertyValue(field);

			if (fieldValue instanceof String str) {
				// 字符串类型：必须包含至少一个非空格字符
				if (StringUtils.hasText(str)) {
					return true;
				}
			} else if (fieldValue != null) {
				// 非字符串类型（如 Integer, List, Date 等）：只要不为 null 即视作有效
				return true;
			}
		}

		return false;
	}
}
