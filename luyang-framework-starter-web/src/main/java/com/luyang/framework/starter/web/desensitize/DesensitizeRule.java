package com.luyang.framework.starter.web.desensitize;

import java.util.regex.Pattern;

/**
 * 脱敏规则接口
 *
 * @author yang.lu
 */
public interface DesensitizeRule {

	/**
	 * 正则匹配规则
	 *
	 * @author yang.lu
	 */
	Pattern getPattern();

	/**
	 * 替换内容
	 *
	 * @author yang.lu
	 */
	String getReplacement();
}
