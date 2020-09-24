package iservice.sdk.net;

import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @author : ori
 * @date : 2020/9/24 10:31 上午
 */
public class HttpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private static class HttpClientHolder {
        private static final HttpClient INSTANCE = new HttpClient();
        private static final OkHttpClient CLIENT = new OkHttpClient();
    }

    private HttpClient() {
    }

    public HttpClient getInstance() {
        return HttpClientHolder.INSTANCE;
    }

    /**
     * 通用get请求
     */
    public static String get(String url, Map<String, String> headerMap) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        Request.Builder builder = new Request.Builder().url(url);
        if (headerMap != null && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = builder.build();
        try {
            String result = HttpClientHolder.CLIENT.newCall(request).execute().body().string();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = HttpClientHolder.CLIENT.newCall(request).execute()) {
            return response.body() != null ? response.body().string() : null;
        }
    }

    /**
     * POST请求(表单提交)
     */
    public static String postForm(String url, Map<String, String> params) {
        LOGGER.info("请求信息url:" + url + ",param:" + com.alibaba.fastjson.JSON.toJSONString(params));
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            return Objects.requireNonNull(HttpClientHolder.CLIENT.newCall(request).execute().body()).string();
        } catch (IOException e) {
            LOGGER.error("Method 'postForm' failed. url:{}, params:{}", url, params);
        }
        return null;
    }

}
