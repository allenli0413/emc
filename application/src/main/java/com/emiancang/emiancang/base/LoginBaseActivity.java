package com.emiancang.emiancang.base;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;

import com.tencent.connect.share.QzoneShare;
import com.tencent.open.t.Weibo;
import com.zwyl.Logger;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.wxapi.WechatToken;
import com.emiancang.emiancang.wxapi.WeiChatConstants;
import com.emiancang.emiancang.wxapi.WeichatShareManager;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zwyl.App;
import com.emiancang.emiancang.decode.ProgressDialogManager;
import com.emiancang.emiancang.dialog.DialogLogin;
import com.emiancang.emiancang.http.SimpleResponHandler;
import com.emiancang.emiancang.qq.QQConstants;
import com.emiancang.emiancang.sina.AccessTokenKeeper;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

import static com.tencent.connect.share.QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;


/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 分享    新浪 微信 QQ分享  禁止新浪 5.0会出错   extends SinaShareActivity
 * @date 15-9-21 上午11:12
 */
public class LoginBaseActivity extends SinaShareActivity {


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

    public DialogLogin mDialog;
//    private TimeCount mTimeCount;
    int max = 60;
    private static int LOGIN_SINA_TYPE = 111;
    public AuthListener mLoginListener = new AuthListener();

    public String PIC_URL = "http://www.emiancang.com/emc/upload/logo/Icon@2x.png";

    int loginType ;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == GET_SINA_TOKEN){
                ProgressDialogManager.showWaiteDialog(getActivity(), "正在登录...");
                Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(LoginBaseActivity.this);
                long uid = Long.parseLong(accessToken.getUid());
                uploadSinaData(accessToken.getToken());//uid+"";
            }
        }
    };

    WeichatShareManager weichatShareManager;//为什么不写成单例模式？？？


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

    public boolean ready() {
        if (mTencent == null) {
            return false;
        }
        boolean ready = mTencent.isSessionValid()
                && mTencent.getQQToken().getOpenId() != null;
        return ready;
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
                        ProgressDialogManager.showWaiteDialog(LoginBaseActivity.this,"正在登录...");

                        Util.wechatLoginActivity = true;
                        Util.isWechatLogin = true;
                        final SendAuth.Req req = new SendAuth.Req();
                        req.scope = "snsapi_userinfo";
                        req.state = "wechat_sdk_demo_test";
                        wxApi.sendReq(req);
                        ProgressDialogManager.cancelWaiteDialog();;
                        break;
                    }else{
                        showToast("没有安装微信，请先安装微信");
                        return;
                    }
                case R.id.login_qq:
                    if (!mTencent.isSessionValid()) {
                        mTencent.login(LoginBaseActivity.this, "all", loginListener);
                    }else{
                        ProgressDialogManager.showWaiteDialog(LoginBaseActivity.this,"正在登录...");
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
                AccessTokenKeeper.writeAccessToken(getApplicationContext(), accessToken);
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
        if (null != mTencent)
            mTencent.onActivityResult(requestCode, resultCode, data);
        else
            super.onActivityResult(requestCode, resultCode, data);
    }

    public void onEventMainThread(WechatToken token) {
        if(Util.wechatLoginActivity){
            if(Util.isWechatLogin){
                // 刷新
                Util.isWechatLogin = false;
//            mTimeCount.start();
                if (token != null){
                    if(isLoad){
                        return;
                    }
                    isLoad = true;
                    uploadWeichatData(token.getToken());
                }
            }
        }
    }

    public void uploadWeichatData(String code){
//        UserApi userApi =  UserApi.login(this, code,UserApi.LOGIN_AUTH_WIEXIN, getSimpleHttpResponHandler());
//        userApi.start();
    }

    public void uploadQqData(String access_token,String openid){
//        UserApi userApi =  UserApi.login(this, access_token,openid,UserApi.LOGIN_AUTH_QQ, getSimpleHttpResponHandler());
//        userApi.start();
    }

    public void uploadSinaData(String accessToken){
//        UserApi userApi =  UserApi.login(this, accessToken,UserApi.LOGIN_AUTH_WEIBO, getSimpleHttpResponHandler());
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
            if(!LoginBaseActivity.this.isDestroyed()){
                ProgressDialogManager.showWaiteDialog(LoginBaseActivity.this, "正在登录...");
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
//                UserManager.getInstance().add(user);
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

//    /* 定义一个倒计时的内部类 */
//    class TimeCount extends CountDownTimer {
//        public TimeCount(long millisInFuture, long countDownInterval) {
//            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
//        }
//
//        @Override
//        public void onFinish() {// 计时完毕时触发
//            Util.isWechatLogin = true;
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {// 计时过程显示
//        }
//    }


    /**
     * 分享图片到微信好友（朋友圈）
     */
    public void shareWechat(Bitmap bitmap, boolean mount_flag) {
        WeichatShareManager weichatShareManager = new WeichatShareManager(getActivity());
        weichatShareManager.shareImageBitmap(bitmap, mount_flag);
//          weichatShareManager.shareText("test",mount_flag);
    }

    /**
     * 分享应用(下载地址)到微信好友（朋友圈） 网页
     * moment_flag 是否分享到朋友圈
     */
    public void shareWechat(String webpageUrl, String title,
                            String description, boolean moment_flag) {
        WeichatShareManager weichatShareManager = new WeichatShareManager(getActivity());
        weichatShareManager.shareWebPage(webpageUrl, title, description, moment_flag);
    }

    /**
     * 默认分享
     *
     * @param moment_flag 是否分享到朋友圈
     */
    public void shareWechat(boolean moment_flag) {
        WeichatShareManager weichatShareManager = new WeichatShareManager(getActivity());
        String webpageUrl = "http://www.ebtang.com/";
        String title = getString(R.string.app_name);
        String description = getString(R.string.app_name);
        weichatShareManager.shareWebPage(webpageUrl, title, description, moment_flag);
    }

    public void shareWebpageWechat(String url, boolean moment_flag){
        WeichatShareManager weichatShareManager = new WeichatShareManager(getActivity());
        String title = getString(R.string.app_name);
        String description = getString(R.string.app_name);
        weichatShareManager.shareWebPage(url, title, description, moment_flag);
    }

    public void shareWebpageWechat(String title, String content, String url, boolean moment_flag){
        WeichatShareManager weichatShareManager = new WeichatShareManager(getActivity());
        String description = "找棉花来e棉仓";
        weichatShareManager.shareWebPage(url, title, content, moment_flag);
    }


    public void shareWechat(String text, boolean mount_flag) {
        WeichatShareManager weichatShareManager = new WeichatShareManager(getActivity());
        weichatShareManager.shareText(text, mount_flag);
    }

    /**
     * 分享图片到新浪微博
     */
    public void shareSina(Bitmap bitmap) {
        shareImage(bitmap);
    }

    public void shareSina(String text) {
        shareText(text);
    }

    public void shareSinaBitmapAndText(String text,int bitmap){
        shareTextAndImage(text,bitmap);
    }

    public void shareSina(String actionUrl,String title,String text,String description,Bitmap shareImage){
        Bitmap thumb = null;
        if(thumb==null){
            thumb = BitmapFactory.decodeResource(getActivity().getResources(),
                    R.drawable.logo_real);
        }
        Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb, 150, 150, true);
        WebpageObject webpageObject = new WebpageObject();
        webpageObject.actionUrl = actionUrl;
        webpageObject.title = title;
        webpageObject.defaultText = text;
        webpageObject.description = description;
        webpageObject.setThumbImage(thumbBmp);

        TextObject textObject = new TextObject();
        ImageObject imageObject = new ImageObject();
        shareWebpage(webpageObject,textObject,imageObject);
        thumb.recycle();
        thumbBmp.recycle();
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        // 设置 Bitmap 类型的图片到视频对象里
        return mediaObject;
    }


    /**
     * 分享到QQ  根据具体需求再做改动☆☆
     */

    //分享图文消息
    public void shareQQ() {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享的类型。图文分享(普通分享)填Tencent.SHARE_TO_QQ_TYPE_DEFAULT
        params.putString(QQShare.SHARE_TO_QQ_TITLE, getString(R.string.app_name));
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, getString(R.string.app_name));//分享的消息摘要，最长40个字。
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.qq.com/news/1.html");//这条分享消息被好友点击后的跳转URL。
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用222222");//手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替

        shareQQ(params);
    }

    //分享图文消息
    public void shareUrlToQQ(String url) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享的类型。图文分享(普通分享)填Tencent.SHARE_TO_QQ_TYPE_DEFAULT
        params.putString(QQShare.SHARE_TO_QQ_TITLE, getString(R.string.app_name));
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, getString(R.string.app_name));//分享的消息摘要，最长40个字。
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);//这条分享消息被好友点击后的跳转URL。
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "E棉仓");//手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替

        shareQQ(params);
    }

    //分享图文消息
    public void shareUrlToQQ(String title, String content, String url) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享的类型。图文分享(普通分享)填Tencent.SHARE_TO_QQ_TYPE_DEFAULT
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, content);//分享的消息摘要，最长40个字。
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);//这条分享消息被好友点击后的跳转URL。
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, PIC_URL);
//        Resources r = getActivity().getResources();
//        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + r.getResourcePackageName(R.drawable.logo_real) + "/" + r.getResourceTypeName(R.drawable.logo_real) + "/" + r.getResourceEntryName(R.drawable.logo_real));
//        String path = com.emiancang.emiancang.uitl.qq.Util.getPath(getActivity(), uri);
//        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, path);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getString(R.string.app_name));//手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替

        shareQQ(params);
    }

    //纯图片
    public void shareQQ(String url) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, url);
        shareQQ(params);
    }

    //分享音乐
    public void shareQQ(String url, String str2, String str3) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, getString(R.string.app_name));
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, getString(R.string.app_name));
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.qq.com/news/1.html");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, "音乐链接");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用222222");
        shareQQ(params);
    }

    /**
     * @param appname 应用名称
     * @param url     应用下载地址
     * @param str2    要分享的标题
     * @param str3    要分享的摘要
     */
    //分享应用
    public void shareQQ(String appname, String url, String str2, String str3) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, str2);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, str3);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appname);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, 0);
        shareQQ(params);
    }
    /**
     * @param appname 应用名称
     * @param url     应用下载地址
     * @param str2    要分享的标题
     * @param str3    要分享的摘要
     */
    //分享应用
    public void shareQQQone(String appname, String url, String str2, String str3) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, str2);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, str3);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appname);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        shareQQ(params);
    }

    public void shareQQ(Bundle params) {
        mTencent = Tencent.createInstance(QQConstants.APP_ID, App.getContext());
        //分享额外选项，两种类型可选（默认是不隐藏分享到QZone按钮且不自动打开分享到QZone的对话框）：
        // QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN，分享时自动打开分享到QZone的对话框。
        // QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE，分享时隐藏分享到QZone按钮
        mTencent.shareToQQ(getActivity(), params, iUiListener);
    }

    /**
     * 分享url到QZONE
     */
    public void shareUrlToQZone(String url){
        ArrayList<String> list = new ArrayList<>();
        list.add("http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, getString(R.string.app_name));//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, getString(R.string.app_name));//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);//必填
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, list);
        shareQZone(params);
    }

    public void shareUrlToQZone(String title, String content, String url){
        ArrayList<String> list = new ArrayList<>();
        list.add(PIC_URL);
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, content);//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);//必填
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, list);
        shareQZone(params);
    }

    public void shareQZone(Bundle params) {
        mTencent = Tencent.createInstance(QQConstants.APP_ID, App.getContext());
        mTencent.shareToQzone(getActivity(), params, iUiListener);
    }


    IUiListener iUiListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
//            EventBus.getDefault().post(new WeiChatShareInfo(true));

        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    };
}
