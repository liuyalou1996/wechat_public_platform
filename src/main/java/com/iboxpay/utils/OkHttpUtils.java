package com.iboxpay.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(OkHttpUtils.class);

	public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");

	public static final MediaType OCTET_STREAM = MediaType.get("application/octet-stream");

	public static final String USER_AGENT =
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";

	public static final int TIMEOUT = 10;

	public static OkHttpClient getOkHttpClient() {
		return getOkHttpClient(TIMEOUT);
	}

	public static OkHttpClient getOkHttpClient(int timeout) {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
		builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
		builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
		return builder.build();
	}

	public static String sendGet(String url, Map<String, Object> params) throws IOException {
		return sendGet(url, params, TIMEOUT);
	}

	/**
	 * 发送get请求
	 * 
	 * @param url
	 *            网址
	 * @param params
	 *            请求参数
	 * @param timeout
	 *            超时时长
	 * @return
	 * @throws IOException
	 */
	public static String sendGet(String url, Map<String, Object> params, int timeout) throws IOException {
		if (StringUtils.isBlank(url)) {
			throw new IllegalArgumentException("request url can not be null");
		}

		OkHttpClient client = getOkHttpClient(timeout);
		HttpUrl.Builder urlBuilder = HttpUrl.get(url).newBuilder();

		if (!CollectionUtils.isEmpty(params)) {
			for (Entry<String, Object> entry : params.entrySet()) {
				urlBuilder.addQueryParameter(entry.getKey(), (String) entry.getValue());
			}
		}

		HttpUrl httpUrl = urlBuilder.build();
		Request request = new Request.Builder().url(httpUrl).addHeader("User-Agent", USER_AGENT).get().build();
		return getResponse(url, client, request);
	}

	public static String sendPostWithKeyValue(String url, Map<String, Object> params) throws IOException {
		return sendPostWithKeyValue(url, params, TIMEOUT);
	}

	/**
	 * 发送post请求，请求参数格式为键值对
	 * 
	 * @param url
	 *            网址
	 * @param params
	 *            请求参数
	 * @param timeout
	 *            超时时长
	 * @return
	 * @throws IOException
	 */
	public static String sendPostWithKeyValue(String url, Map<String, Object> params, int timeout) throws IOException {
		if (StringUtils.isBlank(url)) {
			throw new IllegalArgumentException("request url can not be null");
		}

		OkHttpClient client = getOkHttpClient(timeout);
		FormBody.Builder formBuilder = new FormBody.Builder(Charset.forName("UTF-8"));

		if (!CollectionUtils.isEmpty(params)) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				formBuilder.add(entry.getKey(), (String) entry.getValue());
			}
		}

		FormBody formBody = formBuilder.build();
		Request request = new Request.Builder().url(url).addHeader("User-Agent", USER_AGENT).post(formBody).build();

		return getResponse(url, client, request);
	}

	public static String sendPostWithJson(String url, Map<String, Object> params) throws IOException {
		return sendPostWithJson(url, params, TIMEOUT);
	}

	/**
	 * 发送post请求，请求参数格式为json格式
	 * 
	 * @param url
	 *            网址
	 * @param params
	 *            参数
	 * @param timeout
	 *            超时时长
	 * @return
	 * @throws IOException
	 */
	public static String sendPostWithJson(String url, Map<String, Object> params, int timeout) throws IOException {
		if (StringUtils.isBlank(url)) {
			throw new IllegalArgumentException("request url can not be null");
		}

		OkHttpClient client = getOkHttpClient(timeout);
		RequestBody requestBody = RequestBody.create(JSON, JsonUtils.toJsonString(params));

		if (CollectionUtils.isEmpty(params)) {
			requestBody = RequestBody.create(JSON, "");
		}

		Request request = new Request.Builder().url(url).addHeader("User-Agent", USER_AGENT).post(requestBody).build();
		return getResponse(url, client, request);
	}

	public static String uploadFile(String url, List<MultipartFile> files) throws IOException {
		return uploadFile(url, files, TIMEOUT);
	}

	/**
	 * 上传文件
	 * 
	 * @param url
	 *            网址
	 * @param files
	 *            文件列表
	 * @param timeout
	 *            超时时长
	 * @return
	 * @throws IOException
	 */
	public static String uploadFile(String url, List<MultipartFile> files, int timeout) throws IOException {
		if (StringUtils.isBlank(url)) {
			throw new IllegalArgumentException("request url can not be null");
		}

		OkHttpClient client = getOkHttpClient(timeout);
		MultipartBody.Builder multiBuilder = new MultipartBody.Builder();
		multiBuilder.setType(MultipartBody.FORM);

		if (!CollectionUtils.isEmpty(files)) {
			for (MultipartFile multipartFile : files) {
				File file = new File(multipartFile.getPath());
				String fieldName = multipartFile.getFieldName();
				String fileName = file.getName();
				multiBuilder.addFormDataPart(fieldName, fileName, RequestBody.create(OCTET_STREAM, file));
			}
		}

		MultipartBody requestBody = multiBuilder.build();
		Request request = new Request.Builder().url(url).addHeader("User-Agent", USER_AGENT).post(requestBody).build();
		return getResponse(url, client, request);
	}

	public static byte[] downloadFile(String url) throws IOException {
		return downloadFile(url, TIMEOUT);
	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 *            网址
	 * @param timeout
	 *            超时时长
	 * @return
	 * @throws IOException
	 */
	public static byte[] downloadFile(String url, int timeout) throws IOException {
		if (StringUtils.isBlank(url)) {
			throw new IllegalArgumentException("request url can not be null");
		}

		OkHttpClient client = getOkHttpClient(timeout);
		Request request = new Request.Builder().url(url).addHeader("User-Agent", USER_AGENT).get().build();

		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				return null;
			}

			return response.body().bytes();
		} catch (IOException e) {
			LOGGER.error("fail to establish the connection with " + url, e);
			throw e;
		}
	}

	private static String getResponse(String url, OkHttpClient client, Request request) throws IOException {
		// 确保Response和ResponseBody关闭
		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				return null;
			}

			return response.body().string();
		} catch (IOException e) {
			LOGGER.error("fail to establish the connection with " + url, e);
			throw e;
		}
	}

	public static class MultipartFile {

		/**
		 * 文件域名，相当于表单中文件域名
		 */
		private String fieldName;
		/**
		 * 文件路径
		 */
		private String path;

		public MultipartFile() {
			super();
		}

		public MultipartFile(String fieldName, String path) {
			super();
			this.fieldName = fieldName;
			this.path = path;
		}

		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

	}
}
