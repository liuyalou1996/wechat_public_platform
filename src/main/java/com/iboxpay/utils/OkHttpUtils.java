package com.iboxpay.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

  public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";

  public static final int READ_TIMEOUT = 10;

  public static final int WRITE_TIMEOUT = 10;

  public static OkHttpClient getOkHttpClient() {
    return getOkHttpClient(READ_TIMEOUT, READ_TIMEOUT);
  }

  public static OkHttpClient getOkHttpClient(int readTimeout, int writeTimeout) {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.readTimeout(readTimeout, TimeUnit.SECONDS);
    builder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
    return builder.build();
  }

  public static String sendGet(String url, Map<String, Object> params) throws IOException {
    return sendGet(url, params, READ_TIMEOUT, WRITE_TIMEOUT);
  }

  public static String sendGet(String url, Map<String, Object> params, int readTimeout, int writeTimeout) throws IOException {
    OkHttpClient client = getOkHttpClient(readTimeout, writeTimeout);
    HttpUrl.Builder urlBuilder = HttpUrl.get(url).newBuilder();

    if (!CollectionUtils.isEmpty(params)) {
      for (Map.Entry<String, Object> entry : params.entrySet()) {
        urlBuilder.addQueryParameter(entry.getKey(), (String) entry.getValue());
      }
    }

    HttpUrl httpUrl = urlBuilder.build();
    Request request = new Request.Builder().url(httpUrl).addHeader("User-Agent", USER_AGENT).get().build();

    return getResponse(url, client, request);
  }

  public static String sendPostWithKeyValue(String url, Map<String, Object> params) throws IOException {
    return sendPostWithKeyValue(url, params, READ_TIMEOUT, WRITE_TIMEOUT);
  }

  public static String sendPostWithKeyValue(String url, Map<String, Object> params, int readTimeout, int writeTimeout) throws IOException {
    OkHttpClient client = getOkHttpClient(readTimeout, writeTimeout);
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
    return sendPostWithJson(url, params, READ_TIMEOUT, WRITE_TIMEOUT);
  }

  public static String sendPostWithJson(String url, Map<String, Object> params, int readTimeout, int writeTimeout) throws IOException {
    OkHttpClient client = getOkHttpClient(readTimeout, writeTimeout);

    if (CollectionUtils.isEmpty(params)) {
      throw new IllegalArgumentException("请求参数不能为空");
    }

    RequestBody requestBody = RequestBody.create(JSON, JsonUtils.toJsonString(params));
    Request request = new Request.Builder().url(url).addHeader("User-Agent", USER_AGENT).post(requestBody).build();
    return getResponse(url, client, request);
  }

  public static String uploadFile(String url, String path, String fieldname, String filename) throws IOException {
    return uploadFile(url, path, fieldname, filename, READ_TIMEOUT, WRITE_TIMEOUT);
  }

  public static String uploadFile(String url, String path, String fieldname, String filename, int readTimeout, int writeTimeout) throws IOException {
    OkHttpClient client = getOkHttpClient(readTimeout, writeTimeout);
    MultipartBody.Builder multiBuilder = new MultipartBody.Builder();
    multiBuilder.setType(MultipartBody.FORM);
    multiBuilder.addFormDataPart(fieldname, filename, RequestBody.create(OCTET_STREAM, new File(path)));
    MultipartBody requestBody = multiBuilder.build();

    Request request = new Request.Builder().url(url).addHeader("User-Agent", USER_AGENT).post(requestBody).build();
    return getResponse(url, client, request);
  }

  private static String getResponse(String url, OkHttpClient client, Request request) throws IOException {
    //确保Response和ResponseBody关闭
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

  public static void main(String[] args) throws Exception {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("name", "lyl");
    String response = OkHttpUtils.sendPostWithKeyValue("http://139.199.59.97:8888/wechat_public_platform/getMerchant.json", map);
    System.out.println(response);
  }
}
