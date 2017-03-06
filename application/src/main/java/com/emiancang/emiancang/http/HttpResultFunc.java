package com.emiancang.emiancang.http;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.emiancang.emiancang.eventbean.ListHttpBean;
import com.zwyl.App;

import de.greenrobot.event.EventBus;
import rx.functions.Func1;

/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 */
public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {


    @Override
    public T call(HttpResult<T> httpResult) {
        String resultCode = httpResult.getResultCode();
        if (resultCode.equals("200")) {
            if(httpResult.getPage() != null && !httpResult.getPage().isEmpty())
                EventBus.getDefault().post(new ListHttpBean(httpResult.getPage()));
            //判断后台返回来的异常情况
            Log.i("ServerFail", "resultCode = " + httpResult.getResultCode() + "; resultMsg" + httpResult.getResultMsg());
            return httpResult.getResultInfo();
//        }else if (resultCode.equals("401")) {
//            Toast.makeText(App.getContext(), httpResult.getResultMsg(), Toast.LENGTH_SHORT).show();
            //tuken
//            return httpResult.getResultnfo();
        }else{
            Looper.prepare();
            Toast.makeText(App.getContext(), httpResult.getResultMsg(), Toast.LENGTH_SHORT).show();
            Looper.loop();
            return httpResult.getResultInfo();
        }
    }
}