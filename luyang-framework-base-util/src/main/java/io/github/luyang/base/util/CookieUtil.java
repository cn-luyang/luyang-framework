package io.github.luyang.base.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Cookie 工具类
 *
 * @author yang.lu
 */
public final class CookieUtil {

	/**
	 * 默认路径：根路径
	 */
	public static final String DEFAULT_PATH = StrUtil.SLASH;

	/**
	 * 会话级 Cookie 最大存活时间（浏览器关闭即失效）
	 */
	public static final int SESSION_MAX_AGE = -1;

	private CookieUtil() {
	}

	/**
	 * 创建会话级 Cookie（浏览器关闭失效）
	 *
	 * @param name  Cookie 名称
	 * @param value Cookie 值（自动 URL 编码）
	 * @return Cookie 对象
	 * @author yang.lu
	 */
	public static Cookie create(String name, String value) {
		return create(name, value, SESSION_MAX_AGE);
	}

	/**
	 * 创建 Cookie（指定存活时间）
	 *
	 * @param name   Cookie 名称
	 * @param value  Cookie 值（自动 URL 编码）
	 * @param maxAge 最大存活时间（秒），-1 表示会话级
	 * @return Cookie 对象
	 * @author yang.lu
	 */
	public static Cookie create(String name, String value, int maxAge) {
		return create(name, value, maxAge, DEFAULT_PATH);
	}

	/**
	 * 创建 Cookie（指定路径）
	 *
	 * @param name   Cookie 名称
	 * @param value  Cookie 值（自动 URL 编码）
	 * @param maxAge 最大存活时间（秒）
	 * @param path   路径（null 表示使用默认根路径）
	 * @return Cookie 对象
	 * @author yang.lu
	 */
	public static Cookie create(String name, String value, int maxAge, String path) {
		return create(name, value, maxAge, path, null);
	}

	/**
	 * 创建 Cookie（指定路径和域名）
	 *
	 * @param name   Cookie 名称
	 * @param value  Cookie 值（自动 URL 编码）
	 * @param maxAge 最大存活时间（秒）
	 * @param path   路径
	 * @param domain 域名（可为 null）
	 * @return Cookie 对象
	 * @author yang.lu
	 */
	public static Cookie create(String name, String value, int maxAge, String path, String domain) {
		return create(name, value, maxAge, path, domain, true);
	}

	/**
	 * 创建完整配置的 Cookie
	 *
	 * @param name     Cookie 名称
	 * @param value    Cookie 值（自动 URL 编码）
	 * @param maxAge   最大存活时间（秒）
	 * @param path     路径
	 * @param domain   域名（可为 null）
	 * @param httpOnly 是否仅 HTTP 访问（推荐 true）
	 * @return Cookie 对象
	 * @author yang.lu
	 */
	public static Cookie create(String name, String value, int maxAge, String path,
								String domain, boolean httpOnly) {
		return create(name, value, maxAge, path, domain, httpOnly, false);
	}

	/**
	 * 创建完全自定义的 Cookie（推荐使用）
	 *
	 * @param name     Cookie 名称
	 * @param value    Cookie 值（自动 URL 编码）
	 * @param maxAge   最大存活时间（秒）
	 * @param path     路径
	 * @param domain   域名（可为 null）
	 * @param httpOnly 是否仅 HTTP 访问
	 * @param secure   是否仅 HTTPS 传输
	 * @return Cookie 对象
	 * @author yang.lu
	 */
	public static Cookie create(String name, String value, int maxAge, String path,
								String domain, boolean httpOnly, boolean secure) {
		Cookie cookie = new Cookie(name, encodeValue(value));
		cookie.setMaxAge(maxAge);
		cookie.setPath(StrUtil.blankToDefault(path, DEFAULT_PATH));
		if (StrUtil.isNotBlank(domain)) {
			cookie.setDomain(domain);
		}
		cookie.setHttpOnly(httpOnly);
		cookie.setSecure(secure);
		return cookie;
	}

	/**
	 * 添加 Cookie 到响应
	 *
	 * @param response HttpServletResponse
	 * @param cookie   Cookie 对象
	 * @author yang.lu
	 */
	public static void add(HttpServletResponse response, Cookie cookie) {
		if (response != null && cookie != null) {
			response.addCookie(cookie);
		}
	}

	/**
	 * 添加会话级 Cookie
	 *
	 * @param response HttpServletResponse
	 * @param name     Cookie 名称
	 * @param value    Cookie 值
	 * @author yang.lu
	 */
	public static void addSession(HttpServletResponse response, String name, String value) {
		add(response, create(name, value));
	}

	/**
	 * 添加持久化 Cookie
	 *
	 * @param response HttpServletResponse
	 * @param name     Cookie 名称
	 * @param value    Cookie 值
	 * @author yang.lu
	 */
	public static void addPermanent(HttpServletResponse response, String name, String value) {
		add(response, create(name, value));
	}

	/**
	 * 删除 Cookie（通过设置 maxAge = 0）
	 *
	 * @param response HttpServletResponse
	 * @param name     Cookie 名称
	 * @author yang.lu
	 */
	public static void remove(HttpServletResponse response, String name) {
		if (response != null) {
			Cookie cookie = new Cookie(name, "");
			cookie.setMaxAge(0);
			cookie.setPath(DEFAULT_PATH);
			response.addCookie(cookie);
		}
	}

	/**
	 * 从请求中获取指定名称的 Cookie 对象
	 *
	 * @param request HttpServletRequest
	 * @param name    Cookie 名称
	 * @return Cookie 对象，未找到返回 null
	 * @author yang.lu
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		return readCookieMap(request).get(name);
	}

	/**
	 * 从请求中获取指定名称的 Cookie 值（自动解码）
	 *
	 * @param request HttpServletRequest
	 * @param name    Cookie 名称
	 * @return Cookie 值，未找到返回 null
	 * @author yang.lu
	 */
	public static String getValue(HttpServletRequest request, String name) {
		Cookie cookie = getCookie(request, name);
		return cookie != null ? decodeValue(cookie.getValue()) : null;
	}

	/**
	 * 获取所有 Cookie 转换为 Map（名称 -> Cookie）
	 *
	 * @param request HttpServletRequest
	 * @return Cookie Map，永不为 null
	 * @author yang.lu
	 */
	public static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		if (request == null || ArrayUtil.isEmpty(request.getCookies())) {
			return MapUtil.empty();
		}

		return Arrays.stream(request.getCookies())
			.collect(Collectors.toMap(
				Cookie::getName,
				Function.identity(),
				(oldVal, newVal) -> newVal
			));
	}

	/**
	 * URL 编码 Cookie 值（防止特殊字符问题）
	 *
	 * @param value 原始值
	 * @return 编码后的值
	 * @author yang.lu
	 */
	public static String encodeValue(String value) {
		if (ObjectUtil.isNull(value)) {
			return null;
		}
		return URLEncoder.encode(value, StandardCharsets.UTF_8);
	}

	/**
	 * URL 解码 Cookie 值
	 *
	 * @param encodedValue 已编码的值
	 * @return 解码后的原始值
	 * @author yang.lu
	 */
	public static String decodeValue(String encodedValue) {
		if (ObjectUtil.isNull(encodedValue)) {
			return null;
		}
		try {
			return URLDecoder.decode(encodedValue, StandardCharsets.UTF_8);
		} catch (Exception e) {
			return encodedValue; // 解码失败返回原值
		}
	}

	/**
	 * 检查请求中是否包含指定名称的 Cookie
	 *
	 * @param request HttpServletRequest
	 * @param name    Cookie 名称
	 * @return 是否存在
	 * @author yang.lu
	 */
	public static boolean contains(HttpServletRequest request, String name) {
		return getCookie(request, name) != null;
	}

	/**
	 * 获取所有 Cookie 名称
	 *
	 * @param request HttpServletRequest
	 * @return Cookie 名称数组
	 * @author yang.lu
	 */
	public static String[] getNames(HttpServletRequest request) {
		if (request == null || request.getCookies() == null) {
			return ArrayUtil.EMPTY_STRING_ARRAY;
		}
		return Arrays.stream(request.getCookies())
			.map(Cookie::getName)
			.toArray(String[]::new);
	}
}
