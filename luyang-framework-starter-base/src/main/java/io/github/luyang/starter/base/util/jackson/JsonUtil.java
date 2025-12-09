package io.github.luyang.starter.base.util.jackson;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.experimental.UtilityClass;

/**
 * Jackson Json 工具类
 *
 * @author yang.lu
 */
@UtilityClass
public class JsonUtil {

	public static boolean isJson(String text) {
		if (CharSequenceUtil.isBlank(text)) {
			return false;
		}

		String str = CharSequenceUtil.trim(text);
		return CharSequenceUtil.isWrap(str, '{', '}')
			|| CharSequenceUtil.isWrap(str, '[', ']');
	}
}
