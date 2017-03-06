package com.zwyl.http;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zwyl.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 处理返回数据, 主要是为了解析数据
 * @date 2015/1/27 18:25
 */
public class JsonHttpResponseHandler<T> implements HttpResponseHandlerInterface {
    private static final String TAG = "HttpResponse";
    TypeReference<T> clazz;

    SimpleHttpResponHandler<T> httpResponseHandler;

    String customTag ;

    public JsonHttpResponseHandler() {
    }

    public JsonHttpResponseHandler(SimpleHttpResponHandler<T> httpResponseHandler,
                                   TypeReference<T> clazz) {
        this.httpResponseHandler = httpResponseHandler;
        this.clazz = clazz;
        customTag = httpResponseHandler.getTag();
    }

    /**
     * 正确的情况
     *
     * @param headers Header
     * @param info    正确返回数据
     */
    public void onSuccess(Map<String, String> headers, String info) {
        if(httpResponseHandler != null){
            httpResponseHandler.onSucess(headers, info);
            info = httpResponseHandler.handDate(info);
        }
        if (TextUtils.isEmpty(info) || "[]".equals(info)) {
            onSuccessEmptyData(headers);
        } else {
            try {
                onSuccess(headers, JSON.parseObject(info, clazz));
            } catch (Exception e) {
                e.printStackTrace();
                onSuccessEmptyData(headers);
            }

        }
    }

    /**
     * 成功返回数据
     *
     * @param headers Headers
     * @param t       想要的数据
     */
    public void onSuccess(Map<String, String> headers, T t) {
        if (httpResponseHandler != null) {
            httpResponseHandler.onSucess(headers, t);
        }
    }

    /**
     * 返回数据为空
     *
     * @param headers
     */
    public void onSuccessEmptyData(Map<String, String> headers) {
        if (httpResponseHandler != null) {
            httpResponseHandler.onSucessEmpty(headers);
        }
    }


    @Override
    public void onStart() {
        if (httpResponseHandler != null) {
            httpResponseHandler.onStart();
        }
    }

    @Override
    public void onProgress(int bytesWritten, int totalSize) {

    }

    @Override
    public void onSuccess(NetworkResponse response) {
        byte[] responseBody = response.data;
        String body = "";
        if (responseBody != null) {
            body = new String(responseBody);
        }
        //后台不一定会过滤null 值，所以前端在获取接口的时候统一过滤一下
        if(!TextUtils.isEmpty(body)){

            if(body.contains(":null")){
                body = body.replaceAll(":null",":\"\"");
            }
//            if(body.contains("\\(\"\"\\)")){
//                body = body.replaceAll("\\(\"\"\\)","");
//            }
        }
        if (httpResponseHandler != null) {
            response.headers.put("TAG", customTag);//添加标记
            httpResponseHandler.onSucessBody(body);
        }
        Logger.i(TAG, "statusCode = " + response.statusCode);
        //正常返回的json数据
        com.orhanobut.logger.Logger.t(TAG).json(body);
//        com.orhanobut.logger.Logger.t(TAG).i(body);//添加 打印json串
        try {

            JSONObject jsonObject = new JSONObject(body);
            String result_code = jsonObject.getString("result_code");
            if ("200".equals(result_code)) {
                if (httpResponseHandler != null) {
                    httpResponseHandler.onlySucess();
                }
                try {
                    onSuccess(response.headers, jsonObject.getString("info"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if ("410".equals(result_code)) {//Token失效  找个房是418 token错误 失效
                if (httpResponseHandler != null) {
                    httpResponseHandler.handleTokenFaile();
                }
            } else {
                onErrorMessage(jsonObject.getString("info"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            onFailure(response, e);
        }
    }

    /**
     * 请求数据格式出错
     *
     * @param error_msg 错误信息
     */
    public void onErrorMessage(String error_msg) {
        if (httpResponseHandler != null) {
            httpResponseHandler.onFailure(error_msg);
        }
    }


    @Override
    public void onFailure(NetworkResponse response, Throwable error) {
        byte[] responseBody = response.data;
        String body = "";
        if (responseBody != null) {
            body = new String(responseBody);
        }
        Logger.i(TAG, "statusCode = " + response.statusCode + ";body = " + body);
        if (httpResponseHandler != null) {
            httpResponseHandler.onException(error);
        }
    }


    @Override
    public void onFinish() {
        if (httpResponseHandler != null) {
            httpResponseHandler.onFinish();
        }
    }



}
