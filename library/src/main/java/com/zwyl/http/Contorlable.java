package com.zwyl.http;

import java.util.Map;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 请求数据，控制View
 * @date 2015/1/31 15:48
 */
public interface Contorlable<T> {

    /**
     * 获取当前的网络请求的TAG，用来标记是哪次请求
     */
    String getTag();
    /**
     * 开始加载数据
     */
    void onStart();

    /**
     * 请求结束
     */
    void onFinish();

    /**
     * 成功返回数据
     *
     * @param t 数据
     */
    void onSucess(Map<String, String> headers, T t);

    /**
     * 返回空数据
     */
    void onSucessEmpty(Map<String, String> headers);

    /**
     * 返回错误数据
     */
    void onFailure(String message);

    /**
     * 返回异常数据
     *
     * @param error 异常
     */
    void onException(Throwable error);
}
