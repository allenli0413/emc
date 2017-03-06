package com.emiancang.emiancang.http;

import android.content.Intent;
import android.widget.Toast;

import com.zwyl.http.Contorlable;
import com.zwyl.http.SimpleHttpResponHandler;
import com.emiancang.emiancang.App;
import com.emiancang.emiancang.user.UserManager;

import java.util.Map;

/**
 * @version V1.0
 * @Description: 简单的获取数据实现
 * @date 15-6-14 下午11:34
 */
public class SimpleResponHandler<T> extends SimpleHttpResponHandler<T> implements Contorlable<T> {

    Contorlable<T> mViewContorl;//控制View

    String mTag= "default";


    public SimpleResponHandler() {
    }

    public void setViewContorl(Contorlable<T> viewContorl) {
        this.mViewContorl = viewContorl;
        if (mViewContorl != null) {
            this.mTag = viewContorl.getTag();
        }
    }

    public void setTag(String mTag) {
        this.mTag = mTag;
    }
    @Override
    public String getTag() {
        return mTag;
    }

    @Override
    public void onStart() {
        if (mViewContorl != null) {
            mViewContorl.onStart();
        }
    }

    @Override
    public void onFinish() {
        if (mViewContorl != null) {
            mViewContorl.onFinish();
        }
    }

    public void onSucess(Map<String, String> headers, String info) {

    }

    public void onSucessBody(String body) {

    }

    public void onlySucess() {

    }


    /**
     * 处理数据
     */
    public String handDate(String info) {
        return info;
    }


    @Override
    public void onSucess(Map<String, String> headers, T t) {
        if (mViewContorl != null) {
            mViewContorl.onSucess(headers, t);
        }
    }

    @Override
    public void onSucessEmpty(Map<String, String> headers) {
        if (mViewContorl != null) {
            mViewContorl.onSucessEmpty(headers);
        }
    }

    @Override
    public void onFailure(String message) {
        if (mViewContorl != null) {
            mViewContorl.onFailure(message);
        }
    }

    @Override
    public void onException(Throwable error) {
        if (mViewContorl != null) {
            mViewContorl.onException(error);
        }
    }


    /**
     * 处理token失效的情况
     */
    public void handleTokenFaile() {
        Toast.makeText(App.getContext(), "您的账号在其他地方登录，请重新登录", Toast.LENGTH_SHORT).show();
        UserManager.INSTANCE.remove();
//        Intent intent = new Intent(App.getContext(),LoginActivity.class);
//        intent.putExtra("isFinish",false);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        App.getContext().startActivity(intent);
    }
}
