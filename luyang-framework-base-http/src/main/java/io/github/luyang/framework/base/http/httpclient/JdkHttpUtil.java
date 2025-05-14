package io.github.luyang.framework.base.http.httpclient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.time.Duration;

/**
 * JDK 内置 HttpClient 工具类（要求 JDK 11+）
 * 特点：官方标准、支持 HTTP/2、支持异步
 * @author wangjixin
 */
public class JdkHttpUtil {
	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
		.connectTimeout(Duration.ofSeconds(30))
		.version(HttpClient.Version.HTTP_2)
		.build();

	/**
	 * 发送 GET 请求
	 *
	 * @param url 请求地址
	 */
	public static String get(String url) throws Exception {
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(url))
			.GET()
			.build();
		return HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString()).body();
	}

	/**
	 * 发送 POST JSON 请求
	 *
	 * @param url  请求地址
	 * @param json 请求体 JSON 字符串
	 */
	public static String postJson(String url, String json) throws Exception {
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(url))
			.header("Content-Type", "application/json")
			.POST(HttpRequest.BodyPublishers.ofString(json))
			.build();
		return HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString()).body();
	}

	/**
	 * 文件上传（Multipart）
	 *
	 * @param url      上传地址
	 * @param filePath 文件路径
	 */
	public static String uploadFile(String url, Path filePath) throws Exception {
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(url))
			.header("Content-Type", "multipart/form-data")
			.POST(HttpRequest.BodyPublishers.ofFile(filePath))
			.build();
		return HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString()).body();
	}
}

