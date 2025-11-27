package io.github.luyang.base.util;

import java.nio.charset.StandardCharsets;

/**
 * Content-Type 常量类
 *
 * @author yang.lu
 */
public final class ContentType {

	private ContentType() {
	}

	/**
	 * 密封接口，所有 Content-Type 都必须实现它
	 */
	public sealed interface Type permits WithCharset, WithoutCharset {
		String value();
	}

	/**
	 * 带 charset 的类型（推荐）
	 */
	public record WithCharset(String mime, String charset) implements Type {
		public WithCharset(String mime) {
			this(mime, "UTF-8");
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
	 * 不带 charset 的类型（如图片流、文件下载）
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

	// ====================== 常用文本类型（自动带 UTF-8） ======================
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

	// ====================== 视频/音频 ======================
	public static final Type VIDEO_MP4 = new WithoutCharset("video/mp4");
	public static final Type VIDEO_WEBM = new WithoutCharset("video/webm");
	public static final Type AUDIO_MPEG = new WithoutCharset("audio/mpeg");
	public static final Type AUDIO_OGG = new WithoutCharset("audio/ogg");

	// ====================== 字体 ======================
	public static final Type FONT_WOFF = new WithoutCharset("font/woff");
	public static final Type FONT_WOFF2 = new WithoutCharset("font/woff2");
	public static final Type FONT_TTF = new WithoutCharset("font/ttf");


	/**
	 * 快速生成带 UTF-8 的 JSON
	 */
	public static String json() {
		return APPLICATION_JSON.value();
	}

	/**
	 * 自定义 charset 的 JSON
	 */
	public static String json(String charset) {
		return new WithCharset("application/json", charset).value();
	}

	/**
	 * 快速生成 text/html
	 */
	public static String html() {
		return TEXT_HTML.value();
	}

	/**
	 * 快速生成 text/plain
	 */
	public static String text() {
		return TEXT_PLAIN.value();
	}

	/**
	 * 文件下载（附件）
	 */
	public static String attachment(String filename) {
		return "attachment; filename=\"%s\"; filename*=UTF-8''%s".formatted(
			filename, java.net.URLEncoder.encode(filename, StandardCharsets.UTF_8)
		);
	}
}
