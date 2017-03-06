package com.emiancang.emiancang.base;

import android.os.Bundle;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.emiancang.emiancang.qq.QQConstants;
import com.emiancang.emiancang.wxapi.WeiChatConstants;

import de.greenrobot.event.EventBus;

/**
 * Created by 22310 on 2016/5/8.
 */
public class ThridPartLoginActivity extends TitleActivity {

    /**
     * SSO 授权认证实例
     */
    public SsoHandler mSsoHandler;
    //qq
    public Tencent mTencent;

    //weichat
    public IWXAPI wxApi;
    /** 登陆认证对应的listener */
    AuthInfo mAuthInfo;

    public boolean isLoad;

    public static final int GET_SINA_TOKEN = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    void initView() {
        // 创建授权认证信息
        mAuthInfo = new AuthInfo(this, com.emiancang.emiancang.sina.Constants.APP_KEY, com.emiancang.emiancang.sina.Constants.REDIRECT_URL, com.emiancang.emiancang.sina.Constants.SCOPE);
        mSsoHandler = new SsoHandler(this, mAuthInfo);
        //qq
        mTencent = Tencent.createInstance(QQConstants.APP_ID, this.getApplicationContext());

        //weichat
        wxApi = WXAPIFactory.createWXAPI(this, WeiChatConstants.APP_ID, false);
        wxApi.registerApp(WeiChatConstants.APP_ID);
        EventBus.getDefault().register(this);
    }




}
