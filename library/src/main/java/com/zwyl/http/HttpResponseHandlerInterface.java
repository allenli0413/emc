package com.zwyl.http;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 网络请求接口
 * @date 2015/1/31 12:03
 */
public interface HttpResponseHandlerInterface {

    /**
     * 请求开始
     */
    void onStart();

    /**
     * 正在请求中
     *
     * @param bytesWritten 请求的字节数
     * @param totalSize    总字节数
     */
    void onProgress(int bytesWritten, int totalSize);

    /**
     * 数据正常返回
     *
     * @param response 返回值
     */
    void onSuccess(NetworkResponse response);

    /**
     * 请求失败
     *@param response 返回值
     * @param error   异常
     *
     */
    void onFailure(NetworkResponse response, Throwable error);

    /**
     * 请求结束
     */
    void onFinish();


}
