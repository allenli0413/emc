package com.zwyl.http;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.HashMap;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 网络请求,格式化为自定义的数据
 * @date 2015/1/27 20:18
 */
public class FormatHttpResponHandler extends AsyncHttpResponseHandler {
    private static final String TAG = "ProxyHttpResponHandler";
    HttpResponseHandlerInterface httpResponseHandler;

    public FormatHttpResponHandler(HttpResponseHandlerInterface httpResponseHandler) {
        this.httpResponseHandler = httpResponseHandler;
    }

    @Override
    public void onStart() {
        httpResponseHandler.onStart();
    }

    @Override
    public void onProgress(long bytesWritten, long totalSize) {
        httpResponseHandler.onProgress((int)bytesWritten, (int)totalSize);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        HashMap<String, String> headerMap = new HashMap<String, String>();
        if (headers != null) {
            for (Header header : headers) {
                headerMap.put(header.getName(), header.getValue());
            }
        }
        NetworkResponse response = new NetworkResponse(statusCode, responseBody, headerMap, false);
        httpResponseHandler.onSuccess(response);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        HashMap<String, String> headerMap = new HashMap<String, String>();
        if (headers != null) {
            for (Header header : headers) {
                headerMap.put(header.getName(), header.getValue());
            }
        }
        NetworkResponse response = new NetworkResponse(statusCode, responseBody, headerMap, false);
        httpResponseHandler.onFailure(response, error);
    }

    @Override
    public void onFinish() {
        httpResponseHandler.onFinish();
    }
}
