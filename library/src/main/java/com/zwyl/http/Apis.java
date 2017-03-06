package com.zwyl.http;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.TypeReference;
import com.litesuits.common.assist.Check;
import com.litesuits.common.utils.MD5Util;
import com.litesuits.common.utils.UserAgentUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.zwyl.App;
import com.zwyl.Logger;
import com.zwyl.ZwyOSInfo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 定义Api常量
 * @date 2015/1/22 21:43
 */
public abstract class Apis implements Runnable{

    private static final String TAG = "HttpResponse";
    private static String SIGN;
    private AsyncHttpClient mClient = new AsyncHttpClient();
    private boolean isRunning = false;

    //请求的参数
    Context context;
    String url;
    RequestParams requestParams;
    HttpResponseHandlerInterface responseHandlerInterface;


    /**
     * 初始化参数
     *
     * @param sign 　签名需要的参数
     */
    public static void init(String sign) {
        SIGN = sign;
    }

    /**
     * 默认对数据进行签名的算法
     */
    protected String getSignString(List<BasicNameValuePair> params) {
        if (params != null) {
            Collections.sort(params, new Comparator<BasicNameValuePair>() {
                public int compare(BasicNameValuePair o1, BasicNameValuePair o2) {
                    if (o1.getName().equals(o2.getName())) {
                        return (o1.getValue().compareTo(o2.getValue()));
                    } else {
                        return (o1.getName().compareTo(o2.getName()));
                    }

                }
            });
            StringBuilder signBuffer = new StringBuilder();
            for (int i = 0; i < params.size(); i++) {
                NameValuePair pair = params.get(i);
                String value = pair.getValue();
                signBuffer.append(value);

            }
            signBuffer.append(SIGN);
            String sign = signBuffer.toString();
            try {
                sign = MD5Util.md5(sign);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sign;
        }
        return null;
    }


    /**
     * 获取自定义Token
     */
    protected abstract String getToken();


    /**
     * 获取自定义Header
     */
    protected Map<String, String> getHeader() {
        return null;
    }


    /**
     * 参数转换格式Map-->RequestParams
     *
     * @param params map格式
     * @return RequestParams格式
     */
    protected RequestParams createBaseRequestParams(HashMap<String, String> params) {
        RequestParams requestParams = new RequestParams();
        Iterator<String> iterator = params.keySet().iterator();
        String key;
        String value;
        ArrayList<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        while (iterator.hasNext()) {
            key = iterator.next();
            value = params.get(key) == null ? "" : params.get(key);
            requestParams.put(key, value);
            list.add(new BasicNameValuePair(key, value));
        }
        list.add(new BasicNameValuePair("token", getToken()));
//        String app_version = ZwyOSInfo.getVersionName();
        String app_version = ZwyOSInfo.getVersionCode() + "";
        list.add(new BasicNameValuePair("version_code", app_version));
        addHeader(getToken(), getSignString(list));
        return requestParams;
    }

    /**
     * header添加自定义参数token和sign
     *
     * @param token 　用于判断是否登录
     * @param sign  　对发起请求的参数进行加密处理
     */
    private void addHeader(String token, String sign) {
        if (TextUtils.isEmpty(token)) {
            token = "";
        }
        if (TextUtils.isEmpty(sign)) {
            sign = "";
        }
        String device_id = ZwyOSInfo.getImei();
        String phone_model = ZwyOSInfo.getPhoneModel();
        String rom_info = ZwyOSInfo.getPhoneVersionName();
        String version_name = ZwyOSInfo.getVersionName();
        String version_code = ZwyOSInfo.getVersionCode() + "";
        String app_sign = ZwyOSInfo.getMd5Sign();
        String platform = "android";
        mClient.addHeader("TOKEN", token);
        mClient.addHeader("SIGN", sign);
        mClient.addHeader("devid", device_id);
        mClient.addHeader("rominfo", rom_info);
        mClient.addHeader("versioncode", version_code);
        mClient.addHeader("versionname", version_name);
        mClient.addHeader("appsign", app_sign);
        mClient.addHeader("phonemodel", phone_model);
        mClient.addHeader("platform", platform);
        mClient.addHeader("User-Agent", UserAgentUtils.get(App.getContext()));
        Map<String, String> headers = getHeader();
        if (!Check.isEmpty(headers)) {
            Iterator<String> iterator = headers.keySet().iterator();
            String key;
            String value;
            while (iterator.hasNext()) {
                key = iterator.next();
                value = headers.get(key);
                mClient.addHeader(key, value);
            }
        }
        Logger.i(TAG, "TOKEN==>" + token);
        Logger.i(TAG, "SIGN==>" + sign);
    }


    /**
     * 发起请求
     *
     * @param context       当前引用
     * @param url           　api接口地址
     * @param params        参数
     * @param viewContorl   　控制界面变化的具体实现
     * @param typeReference 返回参数的类型
     */
    protected <T> void post(Context context, String url,
                            HashMap<String, String> params, SimpleHttpResponHandler<T> viewContorl, TypeReference<T> typeReference) {
        post(context, url, createBaseRequestParams(params), viewContorl, typeReference);
    }


    /**
     * 发起请求
     *
     * @param context       当前引用
     * @param url           　api接口地址
     * @param requestParams 参数
     * @param viewContorl   　控制界面变化的具体实现
     * @param typeReference 返回参数的类型
     */
    protected <T> void post(Context context, String url,
                            RequestParams requestParams, SimpleHttpResponHandler<T> viewContorl, TypeReference<T> typeReference) {
        post(context, url, requestParams, new JsonHttpResponseHandler<T>(
                viewContorl, typeReference));
    }


    /**
     * 发起请求
     *
     * @param context                  当前引用
     * @param url                      　api接口地址
     * @param requestParams            参数
     * @param responseHandlerInterface 回调
     */
    protected void post(Context context, String url,
                        RequestParams requestParams, HttpResponseHandlerInterface responseHandlerInterface) {
        this.context = context;
        this.url = url;
        this.requestParams = requestParams;
        this.responseHandlerInterface = responseHandlerInterface;

    }


    @Override
    public void run() {
        Logger.i(TAG, "url==>" + url);
        Logger.i(TAG, "request==>" + requestParams.toString());
        if (!isRunning) {
            mClient.post(context, url, requestParams,
                    new FormatHttpResponHandler(responseHandlerInterface) {
                        @Override
                        public void onFinish() {
                            super.onFinish();
                            isRunning = false;
                            responseHandlerInterface=null;
                            cancle(context);
                        }
                    });
        }
        isRunning = true;
    }

    /**
     * 结束当前的网络请求
     *
     * @param context 当前的引用
     */
    public void cancle(Context context) {
        mClient.cancelRequests(context, true);
    }


}
