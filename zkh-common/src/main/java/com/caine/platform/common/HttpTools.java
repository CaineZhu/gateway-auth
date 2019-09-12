package com.caine.platform.common;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * @Author: CaineZhu
 * @Description: HTTP 请求工具类
 * @Date: Created in 18:14 2019/9/3
 * @Modified By:
 */
public class HttpTools {

    Logger logger = LoggerFactory.getLogger(HttpTools.class);

    OkHttpClient client;

    private HttpTools(){

    }

    public static HttpTools getInstance(){
        return SingletoneHolder.INSTANCE;
    }

    private static class SingletoneHolder{
        private static final HttpTools INSTANCE = new HttpTools();
    }

    public void get(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        request.header("application/json");
        client.newCall(request).enqueue(callback);
    }

    public String post(String url, Map<String, String> headers, Map<String, Object> param) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, JSONObject.toJSONString(param));
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        builder.post(requestBody);
        Request request = builder.build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            logger.error("IOException", e);
        }

        return null;
    }

    public String get(String url, Map<String, String> headers, Map<String, Object> param) {
        String fullUrl = url;
        Request.Builder builder = new Request.Builder();
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        StringBuffer sb = new StringBuffer(url);
        if (param != null && param.size() > 0) {
            sb.append("?");
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            fullUrl = sb.toString();
            if (fullUrl.endsWith("&")) {
                fullUrl = fullUrl.substring(0, fullUrl.length() - 1);
            }
        }
        System.out.println("fullUrl:"+fullUrl);
        builder.url(fullUrl);
        Request request = builder.build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            logger.error("IOException", e);
        }
        return null;
    }
}
