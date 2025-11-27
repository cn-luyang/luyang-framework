package io.github.luyang.base.util;

import io.github.luyang.base.util.exception.UtilException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet 工具类
 *
 * @author yang.lu
 */
public class ServletUtil {

	/**
	 * 写入JSON格式响应
	 *
	 * @param response HTTP响应对象
	 * @param jsonText JSON格式的文本内容
	 * @author yang.lu
	 */
	public static void writeJson(HttpServletResponse response, String jsonText) {
		write(response, jsonText, ContentType.json());
	}

	/**
	 * 向HTTP响应写入文本内容
	 *
	 * @param response    HTTP响应对象
	 * @param text        要写入的文本内容
	 * @param contentType 响应内容类型，如："application/json;charset=UTF-8"
	 * @author yang.lu
	 */
	public static void write(HttpServletResponse response, String text, String contentType) {
		response.setContentType(contentType);
		try (var writer = response.getWriter()) {
			writer.append(text);
		} catch (IOException e) {
			throw new UtilException(e);
		}
	}
}
