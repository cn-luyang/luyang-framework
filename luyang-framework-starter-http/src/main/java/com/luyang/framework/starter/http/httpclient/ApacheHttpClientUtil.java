package com.luyang.framework.starter.http.httpclient;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Apache HttpClient 工具类
 * 特点：功能全面、支持连接池、兼容性好
 * @author wangjixin
 */
public class ApacheHttpClientUtil {

	private static final CloseableHttpClient HTTP_CLIENT;
	private static final int TIMEOUT = 60_000;

	static {
		RequestConfig config = RequestConfig.custom()
			.setConnectTimeout(TIMEOUT)
			.setSocketTimeout(TIMEOUT)
			.build();

		HTTP_CLIENT = HttpClients.custom()
			.setDefaultRequestConfig(config)
			.build();
	}

	/**
	 * 执行 GET 请求
	 *
	 * @param url 请求地址
	 * @return 响应内容
	 */
	public static String get(String url) throws IOException {
		HttpGet httpGet = new HttpGet(url);
		try (CloseableHttpResponse response = HTTP_CLIENT.execute(httpGet)) {
			return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
		}
	}

	/**
	 * 执行 POST JSON 请求
	 *
	 * @param url  请求地址
	 * @param json 请求体 JSON 字符串
	 */
	public static String postJson(String url, String json) throws IOException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

		try (CloseableHttpResponse response = HTTP_CLIENT.execute(httpPost)) {
			return EntityUtils.toString(response.getEntity());
		}
	}

	/**
	 * 文件上传（Multipart/form-data）
	 *
	 * @param url      上传地址
	 * @param file     要上传的文件
	 * @param formData 附加表单参数
	 */
	public static String uploadFile(String url, File file, Map<String, String> formData) throws IOException {
		HttpPost httpPost = new HttpPost(url);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create()
			.addBinaryBody("file", file, ContentType.DEFAULT_BINARY, file.getName());

		formData.forEach(builder::addTextBody);
		httpPost.setEntity(builder.build());

		try (CloseableHttpResponse response = HTTP_CLIENT.execute(httpPost)) {
			return EntityUtils.toString(response.getEntity());
		}
	}
}

