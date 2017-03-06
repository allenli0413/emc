package com.emiancang.emiancang.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.emiancang.emiancang.App;
import com.emiancang.emiancang.bean.ResEntity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.hx.db.DemoDBManager;
import com.emiancang.emiancang.hx.db.EaseUser;
import com.emiancang.emiancang.login.RegisterActivity;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.igexin.sdk.PushManager;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuanyueqing on 2017/1/17.
 */

public class EasemobService extends Service {

//    public interface EasemobStart{
//        void registEasemob(String phone,Context context);
//        void loginEasemob(String phone);
//        void splashEasemob(User user);
//    }

    private static final String TAG = "EasemobSerivce";

    private EasemobBinder easemobBinder = new EasemobBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return easemobBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        if(intent != null && intent.getAction()!= null && !TextUtils.isEmpty(intent.getAction())){
//            switch (intent.getAction()){
//                case REGISTER:
//                    if(intent.getStringExtra("phone") != null) {
//                        regist(intent.getStringExtra("phone"),App.getContext());
//                    } else {
//                        stopSelf();
//                    }
//                    break;
//                case LOGIN:
//                    if(intent.getStringExtra("phone") != null) {
//                        login(intent.getStringExtra("phone"));
//                    } else {
//                        stopSelf();
//                    }
//                    break;
//                case SPLASH:
//                    if(UserManager.getInstance().getUser() != null) {
//                        splash(UserManager.getInstance().getUser());
//                    } else {
//                        stopSelf();
//                    }
//                    break;
//            }
//        }
        return Service.START_NOT_STICKY;
    }

    public void regist(String mPhone,Context context, boolean recheck) {
        Looper.prepare();
        ViewControl viewControl = null;
        if(context != null)
            viewControl= ViewControlUtil.create2Dialog(context);
        UserService api = ApiUtil.createDefaultApi(UserService.class);
        PushManager pushManager = PushManager.getInstance();
        ApiUtil.doDefaultApi(api.refreshClientid(pushManager.getClientid(context),mPhone,App.HXPASSWORD), new HttpSucess<ResEntity>() {
            @Override
            public void onSucess(ResEntity resEntity) {
// close it before login to make sure DemoDB not overlap
                DemoDBManager.getInstance().closeDB();
                // reset current user name before login
                App.getInstance().setCurrentUserName(mPhone);
                // 调用sdk登陆方法登陆聊天服务器
                EMClient.getInstance().login(mPhone, App.HXPASSWORD, new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                        // ** manually load all local groups and
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        getFriends();
                        if(recheck)
                            login(mPhone);
                        else
                            stopSelf();
                    }

                    @Override
                    public void onProgress(int progress, String status) {
                    }

                    @Override
                    public void onError(final int code, final String message) {
                        regist(mPhone, context, false);
                    }
                });
            }
        }, viewControl);
        Looper.loop();
    }

    public void login(String phone){
        DemoDBManager.getInstance().closeDB();
        App.getInstance().setCurrentUserName(phone);
        // 调用sdk登陆方法登陆聊天服务器
        EMClient.getInstance().login(phone, App.HXPASSWORD, new EMCallBack() {

            @Override
            public void onSuccess() {
                // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                // ** manually load all local groups and
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                getFriends();

                stopSelf();
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(final int code, final String message) {
                if(code == 204)
                    regist(phone, null, true);
                else
                    login(phone);
            }
        });
    }

    public void splash(User user){
        // close it before login to make sure DemoDB not overlap
        DemoDBManager.getInstance().closeDB();
        // reset current user name before login
        App.getInstance().setCurrentUserName(user.getESjzcSjh());
        // 调用sdk登陆方法登陆聊天服务器
        EMClient.getInstance().login(user.getESjzcSjh(), App.HXPASSWORD, new EMCallBack() {
            @Override
            public void onSuccess() {
                // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                // ** manually load all local groups and
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                getFriends();
                PushManager pushManager = PushManager.getInstance();
                //用户头像没有
                if(TextUtils.isEmpty(user.getEYhtx()) || TextUtils.isEmpty(user.getESjzcXm())){
                }else{
                    if (EMClient.getInstance().isLoggedInBefore()) {
                        // ** 免登陆情况 加载所有本地群和会话
                        //不是必须的，不加sdk也会自动异步去加载(不会重复加载)；
                        //加上的话保证进了主页面会话和群组都已经load完毕
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                    }
                }
                stopSelf();
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(final int code, final String message) {
                splash(user);
            }
        });
    }

    private void getFriends() {
        try {
            List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
            Map<String, EaseUser> users = new HashMap<String, EaseUser>();
            for (String username : usernames) {
                EaseUser user = new EaseUser(username);
                users.put(username, user);
            }
            App.getInstance().setContactList(users);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    public class EasemobBinder extends Binder {
        public EasemobService getService(){
            return EasemobService.this;
        }

//        @Override
//        public void registEasemob(String phone,Context context) {
//            regist(phone,context);
//        }
//
//        @Override
//        public void loginEasemob(String phone) {
//            login(phone);
//        }
//
//        @Override
//        public void splashEasemob(User user) {
//            splash(user);
//        }
    }

}
