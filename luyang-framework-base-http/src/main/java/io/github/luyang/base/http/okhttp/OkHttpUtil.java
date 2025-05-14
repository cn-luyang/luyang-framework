package io.github.luyang.base.http.okhttp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * OkHttp 工具类
 * <p>
 * 提供 HTTP 请求、文件上传等功能的封装，支持以下特性：
 * 1. 连接池复用和超时配置
 * 2. 安全的临时文件处理
 * 3. 自动化的异常处理和资源释放
 *
 * @author wangjixin
 * @date 2025-04-28
 */
@Slf4j
public class OkHttpUtil {

	// region 常量定义
	/**
	 * JSON 媒体类型
	 */
	public static final String MEDIA_TYPE_JSON = "application/json; charset=utf-8";
	/**
	 * 预定义的 MediaType 对象（优化性能）
	 */
	private static final MediaType MEDIA_TYPE_JSON_OBJ = MediaType.parse(MEDIA_TYPE_JSON);
	/**
	 * 临时文件前缀
	 */
	private static final String TEMP_FILE_PREFIX = "okhttp_upload_";
	// endregion

	private OkHttpUtil() {
		throw new UnsupportedOperationException("工具类不允许实例化");
	}

	// region OkHttpClient 配置

	/**
	 * 获取默认配置的 OkHttpClient
	 * <p>默认配置：
	 * - 连接超时: 60秒
	 * - 读取超时: 60秒
	 * - 写入超时: 60秒
	 * - 连接池: 最大5个空闲连接，存活时间5分钟
	 *
	 * @return 配置好的 OkHttpClient 实例
	 */
	public static OkHttpClient getOkHttpClient() {
		return getOkHttpClient(60, 60, 60);
	}

	/**
	 * 获取自定义配置的 OkHttpClient
	 *
	 * @param connectTimeout 连接超时时间（秒）
	 * @param readTimeOut    读取超时时间（秒）
	 * @param writeTimeOut   写入超时时间（秒）
	 * @return 配置好的 OkHttpClient 实例
	 */
	public static OkHttpClient getOkHttpClient(int connectTimeout, int readTimeOut, int writeTimeOut) {
		return new OkHttpClient().newBuilder()
			.connectTimeout(connectTimeout, TimeUnit.SECONDS)
			.readTimeout(readTimeOut, TimeUnit.SECONDS)
			.writeTimeout(writeTimeOut, TimeUnit.SECONDS)
			// 添加连接池
			.connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES))
			.build();
	}
	// endregion

	// region GET 请求

	/**
	 * 执行 GET 请求（自定义客户端和请求头）
	 *
	 * @param okHttpClient OkHttp 客户端实例
	 * @param url          请求地址
	 * @param headers      请求头（使用 Headers.Builder 构建）
	 * @return 响应体字符串
	 */
	public static String get(OkHttpClient okHttpClient, String url, Headers headers) {
		log.debug("发起 GET 请求 [{}]", url);
		Request request = new Request.Builder()
			.url(url)
			.headers(headers)
			.get()
			.build();

		String responseData = executeRequest(okHttpClient, request, url);
		log.debug("完成 GET 请求 [{}]", url);
		return responseData;
	}

	/**
	 * 执行 GET 请求（自定义客户端，默认请求头）
	 */
	public static String get(OkHttpClient okHttpClient, String url) {
		return get(okHttpClient, url, new Headers.Builder().build());
	}

	/**
	 * 执行 GET 请求（默认客户端和请求头）
	 */
	public static String get(String url) {
		return get(getOkHttpClient(), url);
	}
	// endregion

	// region POST 请求

	/**
	 * 执行 POST 请求（自定义客户端和请求头）
	 *
	 * @param okHttpClient OkHttp 客户端实例
	 * @param url          请求地址
	 * @param bodyJson     请求体 JSON 对象
	 * @param headers      请求头
	 * @return 响应体字符串
	 */
	public static String post(OkHttpClient okHttpClient, String url, JSONObject bodyJson, Headers headers) {
		if (log.isDebugEnabled()) {
			log.debug("发起 POST 请求 [{}]，数据大小: {} bytes", url, bodyJson.size());
		}

		RequestBody requestBody = RequestBody.create(
			JSON.toJSONString(bodyJson),
			MEDIA_TYPE_JSON_OBJ
		);

		Request request = new Request.Builder()
			.url(url)
			.headers(headers)
			.post(requestBody)
			.build();

		String responseData = executeRequest(okHttpClient, request, url);
		log.debug("完成 POST 请求 [{}]", url);
		return responseData;
	}

	// 其他重载方法...
	// endregion

	// region 文件上传

	/**
	 * 上传文件（完整参数版）
	 *
	 * @param okHttpClient OkHttp 客户端实例
	 * @param url          上传地址
	 * @param fileKey      表单文件字段名
	 * @param file         要上传的文件对象
	 * @param formDataJson 附加表单参数
	 * @param headers      请求头
	 * @return 响应体字符串
	 */
	public static String uploadFile(OkHttpClient okHttpClient, String url, String fileKey,
									File file, JSONObject formDataJson, Headers headers) {
		try {
			MultipartBody requestBody = buildMultipartBody(fileKey, file, formDataJson);
			Request request = new Request.Builder()
				.url(url)
				.headers(headers)
				.post(requestBody)
				.build();
			return executeRequest(okHttpClient, request, url);
		} finally {
			cleanupTempFile(file);
		}
	}

	/**
	 * 构建 Multipart 请求体
	 */
	private static MultipartBody buildMultipartBody(String fileKey, File file, JSONObject formDataJson) {
		RequestBody fileBody = RequestBody.create(file, MediaType.parse("application/octet-stream"));

		MultipartBody.Builder builder = new MultipartBody.Builder()
			.setType(MultipartBody.FORM)
			.addFormDataPart(fileKey, file.getName(), fileBody);

		for (Map.Entry<String, Object> entry : formDataJson.entrySet()) {
			builder.addFormDataPart(
				entry.getKey(),
				Objects.toString(entry.getValue(), "")
			);
		}
		return builder.build();
	}

	/**
	 * 清理临时文件（安全删除）
	 */
	private static void cleanupTempFile(File file) {
		if (file != null && file.exists()) {
			try {
				Files.delete(file.toPath());
			} catch (IOException e) {
				log.warn("临时文件删除失败 [{}]", file.getAbsolutePath(), e);
			}
		}
	}
	// endregion

	// region 核心执行方法

	/**
	 * 执行 HTTP 请求
	 *
	 * @param okHttpClient 客户端实例
	 * @param request      构建好的请求对象
	 * @param url          用于日志记录的请求地址
	 * @return 响应体字符串
	 */
	private static String executeRequest(OkHttpClient okHttpClient, Request request, String url) {
		try (Response response = okHttpClient.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				log.warn("请求失败 [{}]，状态码: {}", url, response.code());
			}

			ResponseBody body = response.body();
			return body != null ? body.string() : "";
		} catch (IOException e) {
			log.error("请求执行异常 [{}]", url, e);
			return "";
		}
	}
	// endregion

	// region 文件处理

	/**
	 * 将 MultipartFile 转换为临时文件
	 *
	 * @param multipartFile Spring 文件对象
	 * @return 临时文件对象
	 * @throws IllegalArgumentException 当文件为空时抛出
	 */
	public static File convertToFile(MultipartFile multipartFile) {
		if (multipartFile == null || multipartFile.isEmpty()) {
			throw new IllegalArgumentException("上传文件不能为空");
		}

		try {
			String originalFilename = Objects.requireNonNull(
				multipartFile.getOriginalFilename(),
				"文件名不能为空"
			);

			Path tempDir = Files.createTempDirectory(TEMP_FILE_PREFIX);
			Path targetPath = tempDir.resolve(sanitizeFilename(originalFilename));

			Files.copy(
				multipartFile.getInputStream(),
				targetPath,
				StandardCopyOption.REPLACE_EXISTING
			);

			return targetPath.toFile();
		} catch (IOException e) {
			throw new RuntimeException("文件转换失败", e);
		}
	}

	/**
	 * 文件名消毒处理（防止路径遍历）
	 */
	private static String sanitizeFilename(String filename) {
		return filename.replaceAll("[^a-zA-Z0-9.-]", "_");
	}
	// endregion
}
