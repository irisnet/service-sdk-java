package irita.sdk.util;


import com.alibaba.fastjson.JSON;
import irita.sdk.exception.ContractException;
import irita.sdk.module.base.WrappedRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpUtils {
    private static final int DEFAULT_TIME_OUT = 15000;

    // this get just for call lcd, if you want use a common get, you will need refactor
    public static String get(String uri) {
        HttpURLConnection connection;
        InputStream is;
        String result = null;
        URL url;

        try {
            url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(DEFAULT_TIME_OUT);
            connection.setReadTimeout(DEFAULT_TIME_OUT);
            // send req to server
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = connection.getInputStream();
                result = getResponse(is);
            } else if (http400or500(connection)) {
                is = connection.getErrorStream();
                result = getResponse(is);
            } else {
                throw new RuntimeException("connect error:" + connection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String getResponse(InputStream input) throws IOException {
        // return charset and save data
        BufferedReader br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        String temp = null;
        while ((temp = br.readLine()) != null) {
            builder.append(temp);
        }
        br.close();
        input.close();
        return builder.toString();
    }

    public static boolean http400or500(HttpURLConnection connection) throws IOException {
        return connection.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST || connection.getResponseCode() == HttpURLConnection.HTTP_INTERNAL_ERROR;
    }

    /**
     * common post, content-type: application/json
     *
     * @param uri
     * @param body json
     * @return res
     */
    public static String post(String uri, String body) throws IOException {
        HttpURLConnection connection;
        InputStream is;
        OutputStream os;
        String result;
        URL url;

        url = new URL(uri);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setConnectTimeout(DEFAULT_TIME_OUT);
        connection.setReadTimeout(DEFAULT_TIME_OUT);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.connect(); // require a connect

        os = connection.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        osw.write(body);
        osw.flush();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            is = connection.getInputStream();
            result = getResponse(is);
        } else {
            throw new IOException("connect error, httpCode:" + connection.getResponseCode());
        }
        return result;
    }

    // use this to send tx
    // TODO use lock
    public static <S extends com.google.protobuf.GeneratedMessageV3> String post(String uri, WrappedRequest<S> object) throws IOException {
        return post(uri, JSON.toJSONString(object));
    }
}
