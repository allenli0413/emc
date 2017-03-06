package com.emiancang.emiancang.http;

import android.text.TextUtils;
import android.util.Log;

import com.emiancang.emiancang.App;
import com.emiancang.emiancang.user.UserManager;
import com.litesuits.common.utils.EncryptionUtils;
import com.mayigeek.frame.http.ApiManager;
import com.mayigeek.frame.http.log.LogLevel;
import com.mayigeek.frame.http.state.HttpFinish;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.zwyl.ZwyOSInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.Request;
import rx.Observable;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 设置一些Api的通用参数
 * @date 16-8-26 下午7:14
 */
public class ApiUtil {

    private static final String SIGN = "12345678#";
    private static String TAG;//过滤log的关键词

    //    public static String HEHE_URL = "http://www.emiancang.com:80/emc/";
    public static String HEHE_URL = "http://new.emiancang.com:8080/emc/";
    //神码服务器url
    //    public static String SMURL = "http://www.emiancang.com:80/work/app/";
    public static String SMURL = "http://new.emiancang.com:8080/work/app/";

    private ApiUtil() {
        TAG = getClass().getSimpleName();
    }

    /**
     * 默认对数据进行签名的算法
     */
    private static String getSignString(List<BasicNameValuePair> params) {
        if (params != null) {
            Collections.sort(params, (o1, o2) -> {
                if (o1.getName().equals(o2.getName())) {
                    return (o1.getValue().compareTo(o2.getValue()));
                } else {
                    return (o1.getName().compareTo(o2.getName()));
                }

            });
            StringBuilder signBuffer = new StringBuilder();
            for (int i = 0; i < params.size(); i++) {
                BasicNameValuePair pair = params.get(i);
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


    public static Map<String, String> mapMultipart;

    /**
     * 默认创建带log的api
     */
    public static <T> T createDefaultApi(final Class<T> service) {
        String BASE_URL = HEHE_URL + "api/";
        //        String BASE_URL = "http://192.168.4.172:9080/emc/api/";//api的url
        //        String BASE_URL = "http://www.emiancang.com:80/emc/api/";//api的url
        //        String BASE_URL = "http://192.168.3.225:8080/emc/api/";//api的url
        //        String BASE_URL = "http://new.emiancang.com:8080/work/app/";//api的url
        ApiManager apiManager = new ApiManager(BASE_URL);
        apiManager.setLogLevel(LogLevel.BODY);//默认打印log
        apiManager.setRequestHook(builder -> {
                    Request request = builder.build();
                    if ("POST".equals(request.method())) {
                        FormBody.Builder newForBody = new FormBody.Builder();
                        MultipartBody.Builder newMultipartBody = new MultipartBody.Builder();
                        if (request.body() instanceof FormBody) {
                            Map<String, String> map = new HashMap<String, String>();
                            FormBody body = (FormBody) request.body();
                            for (int i = 0; i < body.size(); i++) {
                                if (body.name(i).equals("uid"))
                                    continue;
                                newForBody.addEncoded(body.name(i), body.value(i));
                                map.put(body.name(i), body.value(i));
                            }
                            if (UserManager.getInstance().isLogin()) {
                                newForBody.addEncoded("token", UserManager.getInstance().getUser().getToken());
                                newForBody.addEncoded("eSjzcNm", UserManager.getInstance().getUser().getESjzcNm());
                                //                                newForBody.addEncoded("userId",UserManager.getInstance().getUser().getESjzcNm());
                                map.put("eSjzcNm", UserManager.getInstance().getUser().getESjzcNm());
                                map.put("token", UserManager.getInstance().getUser().getToken());
                            } else {
                                if (TextUtils.isEmpty(App.TOKEN) && TextUtils.isEmpty(App.USERID)) {
                                    newForBody.addEncoded("token", "");
                                    newForBody.addEncoded("eSjzcNm", "");
                                    //                                    newForBody.addEncoded("userId","");
                                    map.put("eSjzcNm", "");
                                    map.put("token", "");
                                } else {
                                    newForBody.addEncoded("token", App.TOKEN);
                                    newForBody.addEncoded("eSjzcNm", App.USERID);
                                    //                                    newForBody.addEncoded("userId",App.USERID);
                                    map.put("eSjzcNm", App.USERID);
                                    map.put("token", App.TOKEN);
                                }
                            }

                            String timeStamp = ZwyOSInfo.getStringToDate();
                            String nonce = ZwyOSInfo.getRandomString();
                            newForBody.addEncoded("nonce", nonce);
                            newForBody.addEncoded("timeStamp", timeStamp);
                            newForBody.addEncoded("sign", getSign(map, timeStamp, nonce));

                            builder.method(request.method(), newForBody.build());
                            //                            builder.addHeader("Content-Type","text/html;charset=UTF-8");
                        } else if (request.body() instanceof MultipartBody) {
                            MultipartBody body = (MultipartBody) request.body();
                            List<MultipartBody.Part> parts = body.parts();
                            for (int i = 0; i < parts.size(); i++) {
                                newMultipartBody.addPart(parts.get(i));
                            }
                            Iterator it = mapMultipart.keySet().iterator();
                            while (it.hasNext()) {
                                String key;
                                String value;
                                key = it.next().toString();
                                value = mapMultipart.get(key);
                                newMultipartBody.addFormDataPart(key, value);
                            }
                            if (UserManager.getInstance().isLogin()) {
                                newMultipartBody.addFormDataPart("token", UserManager.getInstance().getUser().getToken());
                                newMultipartBody.addFormDataPart("eSjzcNm", UserManager.getInstance().getUser().getESjzcNm());
                                //                                newMultipartBody.addFormDataPart("userId",UserManager.getInstance().getUser().getESjzcNm());
                                mapMultipart.put("eSjzcNm", UserManager.getInstance().getUser().getESjzcNm());
                                mapMultipart.put("token", UserManager.getInstance().getUser().getToken());
                            } else {
                                if (TextUtils.isEmpty(App.TOKEN) && TextUtils.isEmpty(App.USERID)) {
                                    newMultipartBody.addFormDataPart("token", "");
                                    newMultipartBody.addFormDataPart("eSjzcNm", "");
                                    //                                    newMultipartBody.addFormDataPart("userId","");
                                    mapMultipart.put("eSjzcNm", "");
                                    mapMultipart.put("token", "");
                                } else {
                                    newMultipartBody.addFormDataPart("token", App.TOKEN);
                                    newMultipartBody.addFormDataPart("eSjzcNm", App.USERID);
                                    //                                    newMultipartBody.addFormDataPart("userId",App.USERID);
                                    mapMultipart.put("eSjzcNm", App.USERID);
                                    mapMultipart.put("token", App.TOKEN);
                                }
                            }
                            String timeStamp = ZwyOSInfo.getStringToDate();
                            String nonce = ZwyOSInfo.getRandomString();
                            newMultipartBody.addFormDataPart("nonce", nonce);
                            newMultipartBody.addFormDataPart("timeStamp", timeStamp);
                            newMultipartBody.addFormDataPart("sign", getSign(mapMultipart, timeStamp, nonce));
                            builder.method(request.method(), newMultipartBody.build());
                            builder.addHeader("Content-Type", "text/html;charset=UTF-8");
                        }
                    }
                }
        );
        apiManager.setResponseHook(null);
        //https证书信息
        String CER = "-----BEGIN CERTIFICATE-----\n" +
                "MIIDBjCCAe4CCQCnu4M+vpwXrTANBgkqhkiG9w0BAQsFADBFMQswCQYDVQQGEwJB\n" +
                "VTETMBEGA1UECAwKU29tZS1TdGF0ZTEhMB8GA1UECgwYSW50ZXJuZXQgV2lkZ2l0\n" +
                "cyBQdHkgTHRkMB4XDTE2MTAxMDEwMTUxM1oXDTE3MTAxMDEwMTUxM1owRTELMAkG\n" +
                "A1UEBhMCQVUxEzARBgNVBAgMClNvbWUtU3RhdGUxITAfBgNVBAoMGEludGVybmV0\n" +
                "IFdpZGdpdHMgUHR5IEx0ZDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEB\n" +
                "AO46UnvEa1OwEzxzhEhiZ+5O3p3QntxkFFyV1qc9ZZXIM4Qwr97ubjh5lusqM1CA\n" +
                "3Wl5IGF4Cuz/1hV1F9YIRpjVCNljP8OmlBle9p0Kjd+JWurYV82/iH1FUNVydFt4\n" +
                "MRmfbxhocaxtunCWRpd/5EWbf6L0h1/M31VoxxfLH2E6rJ3aaoBCzDys99wwlyXA\n" +
                "tHesqqc0+hOL26sCfYGu6PNo1VUemkkVVrl7l9uNO7eM09gz3DsEt4ddNtGofgOY\n" +
                "f6pek4ACGVyslzINDsfZCUli+v44JDnc/Hk8Y09IBCR721GRR+Wk/1trWQkubXqX\n" +
                "vvNwATN+5WRLaKEKQ0r2qiECAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAf220d3f1\n" +
                "CmbyGha3bevn5/U6nYS5pspKQicFHdcLKvPguZybR73gJHXzh5jVAA8u9LcUSxyw\n" +
                "vyWbydzKZwIKdjh3gFeHfYYRbB2XpiBagpYQnX3eeCVyddz8oxMUF5hFADZht7Ne\n" +
                "YHaMIzoqW3phdkCkUYRyQ/C9F4rpU2mBZKb0NZgHH+F0n47LcYc9ETjbbFoYlLRP\n" +
                "HjVra9T4XtvIK6C1DmbqqhzJkEBT2qEQjIi1H1L1buruv1pUxRVVXNHeeyZlMbXq\n" +
                "TWfVOVC2uOxYLJYgeJo2rAVu0mPVfYp65uS3UKwSNYJpkcgIOYZDZXz3x8JM6TEn\n" +
                "JAW8lIpG94aRJQ==\n" +
                "-----END CERTIFICATE-----";
        apiManager.addCer(CER);
        apiManager.setHostnameVerify(false);
        return apiManager.createApi(service);
    }

    /**
     * 默认创建带log的api
     */
    public static <T> T createDefaultApi(final Class<T> service, String BASE_URL) {
        ApiManager apiManager = new ApiManager(BASE_URL);
        apiManager.setLogLevel(LogLevel.BODY);//默认打印log
        apiManager.setRequestHook(builder -> {
                    Request request = builder.build();

                    if ("POST".equals(request.method())) {
                        //                        List<BasicNameValuePair> params = new ArrayList<>();
                        Map<String, String> map = new HashMap<String, String>();
                        FormBody.Builder newForBody = new FormBody.Builder();
                        MultipartBody.Builder newMultipartBody = new MultipartBody.Builder();
                        if (request.body() instanceof FormBody) {
                            FormBody body = (FormBody) request.body();
                            for (int i = 0; i < body.size(); i++) {
                                if (body.name(i).equals("uid"))
                                    continue;
                                newForBody.addEncoded(body.name(i), body.value(i));
                                map.put(body.name(i), body.value(i));
                            }
                            if (UserManager.getInstance().isLogin()) {
                                newForBody.addEncoded("token", UserManager.getInstance().getUser().getToken());
                                //                                newForBody.addEncoded("eSjzcNm",UserManager.getInstance().getUser().getESjzcNm());
                                newForBody.addEncoded("userId", UserManager.getInstance().getUser().getESjzcNm());
                                //                                map.put("eSjzcNm",UserManager.getInstance().getUser().getESjzcNm());
                                //                                map.put("token",UserManager.getInstance().getUser().getToken());
                            } else {
                                if (TextUtils.isEmpty(App.TOKEN) && TextUtils.isEmpty(App.USERID)) {
                                    newForBody.addEncoded("token", "");
                                    //                                    newForBody.addEncoded("eSjzcNm","");
                                    newForBody.addEncoded("userId", "");
                                    //                                    map.put("eSjzcNm","");
                                    //                                    map.put("token","");
                                } else {
                                    newForBody.addEncoded("token", App.TOKEN);
                                    //                                    newForBody.addEncoded("eSjzcNm",App.USERID);
                                    newForBody.addEncoded("userId", App.USERID);
                                    //                                    map.put("eSjzcNm",App.USERID);
                                    //                                    map.put("token",App.TOKEN);
                                }
                            }

                            //                            String timeStamp  = ZwyOSInfo.getStringToDate();
                            //                            String nonce = ZwyOSInfo.getRandomString();
                            //                            newForBody.addEncoded("nonce",nonce);
                            //                            newForBody.addEncoded("timeStamp",timeStamp);
                            //                            newForBody.addEncoded("sign",getSign(map,timeStamp,nonce));
                            builder.method(request.method(), newForBody.build());
                        } else if (request.body() instanceof MultipartBody) {
                            MultipartBody body = (MultipartBody) request.body();
                            List<MultipartBody.Part> parts = body.parts();
                            for (int i = 0; i < parts.size(); i++) {
                                newMultipartBody.addPart(parts.get(i));
                            }
                            Iterator it = mapMultipart.keySet().iterator();
                            while (it.hasNext()) {
                                String key;
                                String value;
                                key = it.next().toString();
                                value = mapMultipart.get(key);
                                newMultipartBody.addFormDataPart(key, value);
                            }
                            if (UserManager.getInstance().isLogin()) {
                                newMultipartBody.addFormDataPart("token", UserManager.getInstance().getUser().getToken());
                                newMultipartBody.addFormDataPart("userId", UserManager.getInstance().getUser().getESjzcNm());
                                //                                newMultipartBody.addFormDataPart("userId",UserManager.getInstance().getUser().getESjzcNm());
                                mapMultipart.put("userId", UserManager.getInstance().getUser().getESjzcNm());
                                mapMultipart.put("token", UserManager.getInstance().getUser().getToken());
                            } else {
                                if (TextUtils.isEmpty(App.TOKEN) && TextUtils.isEmpty(App.USERID)) {
                                    newMultipartBody.addFormDataPart("token", "");
                                    newMultipartBody.addFormDataPart("userId", "");
                                    //                                    newMultipartBody.addFormDataPart("userId","");
                                    mapMultipart.put("userId", "");
                                    mapMultipart.put("token", "");
                                } else {
                                    newMultipartBody.addFormDataPart("token", App.TOKEN);
                                    newMultipartBody.addFormDataPart("userId", App.USERID);
                                    //                                    newMultipartBody.addFormDataPart("userId",App.USERID);
                                    mapMultipart.put("userId", App.USERID);
                                    mapMultipart.put("token", App.TOKEN);
                                }
                            }
                            //                            String timeStamp  = ZwyOSInfo.getStringToDate();
                            //                            String nonce = ZwyOSInfo.getRandomString();
                            //                            newMultipartBody.addFormDataPart("nonce",nonce);
                            //                            newMultipartBody.addFormDataPart("timeStamp",timeStamp);
                            //                            newMultipartBody.addFormDataPart("sign",getSign(mapMultipart,timeStamp,nonce));
                            builder.method(request.method(), newMultipartBody.build());
                            builder.addHeader("Content-Type", "text/html;charset=UTF-8");
                        }
                        //                        builder.header("sign", getSignString(params));
                    }
                }
        );
        apiManager.setResponseHook(null);
        //https证书信息
        String CER = "-----BEGIN CERTIFICATE-----\n" +
                "MIIDBjCCAe4CCQCnu4M+vpwXrTANBgkqhkiG9w0BAQsFADBFMQswCQYDVQQGEwJB\n" +
                "VTETMBEGA1UECAwKU29tZS1TdGF0ZTEhMB8GA1UECgwYSW50ZXJuZXQgV2lkZ2l0\n" +
                "cyBQdHkgTHRkMB4XDTE2MTAxMDEwMTUxM1oXDTE3MTAxMDEwMTUxM1owRTELMAkG\n" +
                "A1UEBhMCQVUxEzARBgNVBAgMClNvbWUtU3RhdGUxITAfBgNVBAoMGEludGVybmV0\n" +
                "IFdpZGdpdHMgUHR5IEx0ZDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEB\n" +
                "AO46UnvEa1OwEzxzhEhiZ+5O3p3QntxkFFyV1qc9ZZXIM4Qwr97ubjh5lusqM1CA\n" +
                "3Wl5IGF4Cuz/1hV1F9YIRpjVCNljP8OmlBle9p0Kjd+JWurYV82/iH1FUNVydFt4\n" +
                "MRmfbxhocaxtunCWRpd/5EWbf6L0h1/M31VoxxfLH2E6rJ3aaoBCzDys99wwlyXA\n" +
                "tHesqqc0+hOL26sCfYGu6PNo1VUemkkVVrl7l9uNO7eM09gz3DsEt4ddNtGofgOY\n" +
                "f6pek4ACGVyslzINDsfZCUli+v44JDnc/Hk8Y09IBCR721GRR+Wk/1trWQkubXqX\n" +
                "vvNwATN+5WRLaKEKQ0r2qiECAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAf220d3f1\n" +
                "CmbyGha3bevn5/U6nYS5pspKQicFHdcLKvPguZybR73gJHXzh5jVAA8u9LcUSxyw\n" +
                "vyWbydzKZwIKdjh3gFeHfYYRbB2XpiBagpYQnX3eeCVyddz8oxMUF5hFADZht7Ne\n" +
                "YHaMIzoqW3phdkCkUYRyQ/C9F4rpU2mBZKb0NZgHH+F0n47LcYc9ETjbbFoYlLRP\n" +
                "HjVra9T4XtvIK6C1DmbqqhzJkEBT2qEQjIi1H1L1buruv1pUxRVVXNHeeyZlMbXq\n" +
                "TWfVOVC2uOxYLJYgeJo2rAVu0mPVfYp65uS3UKwSNYJpkcgIOYZDZXz3x8JM6TEn\n" +
                "JAW8lIpG94aRJQ==\n" +
                "-----END CERTIFICATE-----";
        apiManager.addCer(CER);
        apiManager.setHostnameVerify(false);
        return apiManager.createApi(service);
    }

    /**
     * 执行默认的api
     */
    public static <T> void doDefaultApi(Observable<HttpResult<T>> api, HttpSucess<T> httpSucess) {
        doDefaultApi(api, httpSucess, null, null);
    }

    /**
     * 执行默认的api
     */
    public static <T extends List> void doDefaultApi(Observable<HttpResult<T>> api, HttpSucess<T> httpSucess, HttpFinish httpFinish) {
        doDefaultApi(api, httpSucess, httpFinish, null);
    }

    /**
     * 执行默认的api
     */
    public static <T> void doDefaultApi(Observable<HttpResult<T>> api, HttpSucess<T> httpSucess, ViewControl viewControl) {
        doDefaultApi(api, httpSucess, null, viewControl);
    }

    /**
     * 执行默认的api
     */
    public static <T> void doDefaultApi(Observable<HttpResult<T>> api, HttpSucess<T> httpSucess, HttpFinish httpFinish, ViewControl viewControl) {
        HttpResultFunc<T> httpResultFunc = new HttpResultFunc<T>();
        ApiManager.Companion.doApi(api.map(httpResultFunc), httpSucess, e -> {
            //打印异常
            Log.i(TAG, e.toString());
        }, httpFinish, viewControl);
    }


    public static String getSign(Map<String, String> data, String timeStamp, String nonce) {
        String sign = "";
        String key = "12345678";
        Iterator<String> iterator = data.keySet().iterator();
        ArrayList<String> list = new ArrayList<String>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        Collections.sort(list);
        String md5str = "";
        for (int i = 0; i < list.size(); i++) {
            md5str += data.get(list.get(i));
        }
        md5str += timeStamp + nonce + key;
        try {
            sign = EncryptionUtils.md5Encode(md5str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign.toUpperCase();
    }

}
