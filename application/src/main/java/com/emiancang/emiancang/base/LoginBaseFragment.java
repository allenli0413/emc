package com.emiancang.emiancang.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.wxapi.WechatToken;
import com.emiancang.emiancang.wxapi.WeiChatConstants;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.emiancang.emiancang.decode.ProgressDialogManager;
import com.emiancang.emiancang.dialog.DialogLogin;
import com.emiancang.emiancang.http.SimpleResponHandler;
import com.emiancang.emiancang.qq.QQConstants;
import com.emiancang.emiancang.sina.AccessTokenKeeper;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;

import org.json.JSONObject;

import java.util.Map;

import de.greenrobot.event.EventBus;


/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 分享    新浪 微信 QQ分享  禁止新浪 5.0会出错   extends SinaShareActivity
 * @date 15-9-21 上午11:12
 */
public abstract class LoginBaseFragment extends SinaShareFragment{


    /**
     * SSO 授权认证实例`
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

    public DialogLogin mDialog;
    private TimeCount mTimeCount;
    int max = 60;
    private static int LOGIN_SINA_TYPE = 111;
    public AuthListener mLoginListener = new AuthListener();

    int loginType ;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == GET_SINA_TOKEN){
                ProgressDialogManager.showWaiteDialog(getActivity(), "正在登录...");
                Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(getActivity());
                long uid = Long.parseLong(accessToken.getUid());
                uploadSinaData(accessToken.getToken());//uid+"";
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    void initView() {
        // 创建授权认证信息
        mAuthInfo = new AuthInfo(getActivity(), com.emiancang.emiancang.sina.Constants.APP_KEY, com.emiancang.emiancang.sina.Constants.REDIRECT_URL, com.emiancang.emiancang.sina.Constants.SCOPE);
        mSsoHandler = new SsoHandler(getActivity(), mAuthInfo);
        //qq
        mTencent = Tencent.createInstance(QQConstants.APP_ID, getActivity().getApplicationContext());

        //weichat
        wxApi = WXAPIFactory.createWXAPI(getActivity(), WeiChatConstants.APP_ID, false);
        wxApi.registerApp(WeiChatConstants.APP_ID);
        EventBus.getDefault().register(this);
        mTimeCount = new TimeCount(max * 1000, 1000);// 构造CountDownTimer对象
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void showDialog(View view){
        mDialog = new DialogLogin(getActivity(),itemsOnClick);
        mDialog.showAtLocation(view, Gravity.BOTTOM,0,0);
    }

    private boolean isWXAppInstalledAndSupported() {
        boolean sIsWXAppInstalledAndSupported = wxApi.isWXAppInstalled()
                && wxApi.isWXAppSupportAPI();
        return sIsWXAppInstalledAndSupported;
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener(){

        public void onClick(View v) {
            mDialog.dismiss();
            switch (v.getId()) {
                case R.id.login_register:
//                    startActivity(createIntent(RegisterActivity.class));
                    break;
                case R.id.login:
//                    startActivity(createIntent(LoginActivity.class));
                    break;
                case R.id.login_weixin:
                    if(isWXAppInstalledAndSupported()){
                        ProgressDialogManager.showWaiteDialog(getActivity(),"正在登录...");
                        Util.wechatLoginActivity = false;
                        Util.isWechatLogin = true;
                        final SendAuth.Req req = new SendAuth.Req();
                        req.scope = "snsapi_userinfo";
                        req.state = "wechat_sdk_demo_test";
                        wxApi.sendReq(req);
                        break;
                    }else{
                        showToast("没有安装微信，请先安装微信");
                        return;
                    }

                case R.id.login_qq:
                    loginType = 1010;
                    if (!mTencent.isSessionValid()) {
                        mTencent.login(LoginBaseFragment.this, "all", loginListener);
                    }else{
                        ProgressDialogManager.showWaiteDialog(getActivity(),"正在登录...");
                        uploadQqData(mTencent.getAccessToken(), mTencent.getOpenId());
                    }
                    break;
                case R.id.login_weibo:

                    if (mSsoHandler != null) {
                        loginType = LOGIN_SINA_TYPE;
                        mSsoHandler.authorize(mLoginListener);
                    }else{
                        showToast("微博初始化失败");
                    }
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 登入按钮的监听器，接收授权结果。
     */
    private class AuthListener implements WeiboAuthListener {
        @Override
        public void onComplete(Bundle values) {
            Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(values);
            if (accessToken != null && accessToken.isSessionValid()) {
                AccessTokenKeeper.writeAccessToken(getActivity(), accessToken);
                handler.sendEmptyMessage(GET_SINA_TOKEN);
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            showToast("授权失败，请重试");
            e.printStackTrace();
        }

        @Override
        public void onCancel() {
            showToast("发送取消");
        }
    }

    /**
     * 当 SSO 授权 Activity 退出时，该函数被调用。
     *
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(loginType == LOGIN_SINA_TYPE){
            if (mSsoHandler != null) {
                mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
            }
        }else {
            if (requestCode == com.tencent.connect.common.Constants.REQUEST_LOGIN) {
                mTencent.onActivityResultData(requestCode, resultCode, data, loginListener);
            }
        }
    }

    public void onEventMainThread(WechatToken token) {
        if(Util.isWechatLogin){
            // 刷新
            Util.isWechatLogin = false;
            mTimeCount.start();
            if (token != null){
                if(isLoad){
                    return;
                }
                isLoad = true;
                uploadWeichatData(token.getToken());
            }
        }
    }

    public void uploadWeichatData(String code){
//        UserApi userApi =  UserApi.login(getActivity(), code,UserApi.LOGIN_AUTH_WIEXIN, getSimpleHttpResponHandler());
//        userApi.start();
    }

    public void uploadQqData(String access_token,String openid){
//        UserApi userApi =  UserApi.login(getActivity(), access_token,openid,UserApi.LOGIN_AUTH_QQ, getSimpleHttpResponHandler());
//        userApi.start();
    }

    public void uploadSinaData(String accessToken){
//        UserApi userApi =  UserApi.login(getActivity(), accessToken,UserApi.LOGIN_AUTH_WEIBO, getSimpleHttpResponHandler());
//        userApi.start();
    }


    public IUiListener loginListener = new IUiListener() {
        @Override
        public void onComplete(Object response) {
            doComplete((JSONObject) response);
        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }


        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        protected void doComplete(JSONObject values) {
            if(!getActivity().isDestroyed()){
                ProgressDialogManager.showWaiteDialog(getActivity(), "正在登录...");
                initOpenidAndToken(values);
            }

        }

        public void initOpenidAndToken(JSONObject jsonObject) {
            try {
                String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
                String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
                String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
                uploadQqData(token,openId);
            } catch(Exception e) {
            }
        }
    };

    public SimpleResponHandler<User> getSimpleHttpResponHandler() {

        return new SimpleResponHandler<User>() {
            @Override
            public void onSucessEmpty(Map<String, String> headers) {

            }

            @Override
            public void onSucess(Map<String, String> headers, final User user) {
                ProgressDialogManager.cancelWaiteDialog();

                UserManager.getInstance().add(user);
//                startActivity(createIntent(MainActivity.class));
            }
            @Override
            public void onFailure(String message) {
                ProgressDialogManager.cancelWaiteDialog();
                showToast(message);
            }

            @Override
            public void onException(Throwable error) {
                ProgressDialogManager.cancelWaiteDialog();
                showToast("登录异常，请重试尝试");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                isLoad = false;
                ProgressDialogManager.cancelWaiteDialog();
            }
        };
    }

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            Util.isWechatLogin = true;
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
        }
    }
}
