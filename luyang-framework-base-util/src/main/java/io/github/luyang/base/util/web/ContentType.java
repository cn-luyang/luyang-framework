package io.github.luyang.base.util.web;

import java.nio.charset.StandardCharsets;

/**
 * Content-Type 常量类
 *
 * @author yang.lu
 */

/**
 * Content-Type 常量与工具类
 * 提供常用 MIME 类型常量及便捷方法，支持带字符集和不带字符集的类型。
 *
 * @author yang.lu
 */
public final class ContentType {

	/**
	 * 私有构造方法，防止实例化
	 */
	private ContentType() {
		throw new UnsupportedOperationException("Utility class");
	}

	/**
	 * Content-Type 类型接口
	 */
	public sealed interface Type permits WithCharset, WithoutCharset {
		/**
		 * 返回完整的 Content-Type 字符串
		 */
		String value();
	}

	/**
	 * 带字符集的 Content-Type 类型
	 *
	 * @param mime    MIME 类型
	 * @param charset 字符集，默认 UTF-8
	 */
	public record WithCharset(String mime, String charset) implements Type {

		public WithCharset(String mime) {
			this(mime, StandardCharsets.UTF_8.name());
		}

		@Override
		public String value() {
			return mime + ";charset=" + charset;
		}

		@Override
		public String toString() {
			return value();
		}
	}

	/**
	 * 不带字符集的 Content-Type 类型
	 * 适用于图片、音视频等二进制流
	 *
	 * @param mime MIME 类型
	 */
	public record WithoutCharset(String mime) implements Type {
		@Override
		public String value() {
			return mime;
		}

		@Override
		public String toString() {
			return mime;
		}
	}

	// ====================== 常用文本类型（默认 UTF-8 字符集） ======================
	public static final Type TEXT_PLAIN = new WithCharset("text/plain");
	public static final Type TEXT_HTML = new WithCharset("text/html");
	public static final Type TEXT_CSS = new WithCharset("text/css");
	public static final Type TEXT_JAVASCRIPT = new WithCharset("text/javascript");
	public static final Type TEXT_XML = new WithCharset("text/xml");
	public static final Type TEXT_CSV = new WithCharset("text/csv");

	// ====================== 应用类型 ======================
	public static final Type APPLICATION_JSON = new WithCharset("application/json");
	public static final Type APPLICATION_XML = new WithCharset("application/xml");
	public static final Type APPLICATION_FORM = new WithCharset("application/x-www-form-urlencoded");
	public static final Type APPLICATION_OCTET = new WithoutCharset("application/octet-stream");
	public static final Type APPLICATION_PDF = new WithoutCharset("application/pdf");
	public static final Type APPLICATION_ZIP = new WithoutCharset("application/zip");
	public static final Type APPLICATION_GZIP = new WithoutCharset("application/gzip");

	// ====================== 多部分类型 ======================
	public static final Type MULTIPART_FORM = new WithCharset("multipart/form-data");
	public static final Type MULTIPART_MIXED = new WithoutCharset("multipart/mixed");

	// ====================== 图片类型 ======================
	public static final Type IMAGE_PNG = new WithoutCharset("image/png");
	public static final Type IMAGE_JPEG = new WithoutCharset("image/jpeg");
	public static final Type IMAGE_GIF = new WithoutCharset("image/gif");
	public static final Type IMAGE_WEBP = new WithoutCharset("image/webp");
	public static final Type IMAGE_SVG = new WithCharset("image/svg+xml");
	public static final Type IMAGE_ICON = new WithoutCharset("image/x-icon");

	// ====================== 视频/音频类型 ======================
	public static final Type VIDEO_MP4 = new WithoutCharset("video/mp4");
	public static final Type VIDEO_WEBM = new WithoutCharset("video/webm");
	public static final Type AUDIO_MPEG = new WithoutCharset("audio/mpeg");
	public static final Type AUDIO_OGG = new WithoutCharset("audio/ogg");

	// ====================== 字体类型 ======================
	public static final Type FONT_WOFF = new WithoutCharset("font/woff");
	public static final Type FONT_WOFF2 = new WithoutCharset("font/woff2");
	public static final Type FONT_TTF = new WithoutCharset("font/ttf");

	/**
	 * 返回 application/json;charset=UTF-8
	 */
	public static String json() {
		return APPLICATION_JSON.value();
	}

	/**
	 * 返回指定字符集的 application/json
	 *
	 * @param charset 字符集名称
	 */
	public static String json(String charset) {
		return new WithCharset("application/json", charset).value();
	}

	/**
	 * 返回 text/html;charset=UTF-8
	 */
	public static String html() {
		return TEXT_HTML.value();
	}

	/**
	 * 返回 text/plain;charset=UTF-8
	 */
	public static String text() {
		return TEXT_PLAIN.value();
	}

	/**
	 * 生成文件下载的 Content-Disposition 头值
	 *
	 * @param filename 文件名
	 * @return attachment 格式字符串
	 */
	public static String attachment(String filename) {
		String encoded = java.net.URLEncoder.encode(filename, StandardCharsets.UTF_8);
		return String.format("attachment; filename=\"%s\"; filename*=UTF-8''%s", filename, encoded);
	}
}
