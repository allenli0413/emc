package com.emiancang.emiancang.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.igexin.sdk.PushManager;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.zwyl.BaseActivity;
import com.emiancang.emiancang.App;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.bean.ResEntity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.hx.activity.*;
import com.emiancang.emiancang.hx.db.DemoDBManager;
import com.emiancang.emiancang.hx.db.EaseUser;
import com.emiancang.emiancang.main.MainActivity;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.igexin.sdk.PushService.context;

public class SplashActivity extends BaseActivity {
    private static final String TAG = "SplashActivity";

    private int LOGIN_RESULT_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.splash_view);
        setBackgroundResource(R.drawable.bg_login_guide);
        handler.sendEmptyMessageDelayed(1, 2000);
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initUserState();
        }
    };

    //进应用先刷新用户状态
    private void initUserState(){
        if(UserManager.getInstance().isLogin()){
            User user = UserManager.getInstance().getUser();

            //用户头像没有
            if(TextUtils.isEmpty(user.getEYhtx()) ||TextUtils.isEmpty(user.getESjzcXm())){
                Intent intent = createIntent(PerfectUserDataActivity.class);
                intent.putExtra("type",0);
                startActivity(intent);
                finish();
            }else{
                startActivity(createIntent(MainActivity.class));
//                App.getInstance().getEasemobBinder().loginEasemob(user.getESjzcSjh());
                if(!EMClient.getInstance().isLoggedInBefore())
                    App.getInstance().getEasemobBinder().getService().login(user.getESjzcSjh());
                finish();
            }


//            // close it before login to make sure DemoDB not overlap
//            DemoDBManager.getInstance().closeDB();
//            // reset current user name before login
//            App.getInstance().setCurrentUserName(user.getESjzcSjh());
//            // 调用sdk登陆方法登陆聊天服务器
//            EMClient.getInstance().login(user.getESjzcSjh(), App.HXPASSWORD, new EMCallBack() {
//                @Override
//                public void onSuccess() {
//                    Log.d(TAG, "login: onSuccess");
//                    // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
//                    // ** manually load all local groups and
//                    EMClient.getInstance().groupManager().loadAllGroups();
//                    EMClient.getInstance().chatManager().loadAllConversations();
//                    getFriends();
//                    PushManager pushManager = PushManager.getInstance();
//                    Looper.prepare();
//                    //用户头像没有
//                    if(TextUtils.isEmpty(user.getEYhtx()) ||TextUtils.isEmpty(user.getESjzcXm())){
//                        Intent intent = createIntent(PerfectUserDataActivity.class);
//                        intent.putExtra("type",0);
//                        startActivity(intent);
//                        finish();
//                    }else{
//                        if (EMClient.getInstance().isLoggedInBefore()) {
//                            // ** 免登陆情况 加载所有本地群和会话
//                            //不是必须的，不加sdk也会自动异步去加载(不会重复加载)；
//                            //加上的话保证进了主页面会话和群组都已经load完毕
//                            long start = System.currentTimeMillis();
//                            EMClient.getInstance().groupManager().loadAllGroups();
//                            EMClient.getInstance().chatManager().loadAllConversations();
//                            //进入主页面
//                            startActivity(createIntent(MainActivity.class));
//                            finish();
//                        }else{
//                            startActivity(createIntent(MainActivity.class));
//                            finish();
//                        }
//                    }
//                    Looper.loop();
//                }
//
//                @Override
//                public void onProgress(int progress, String status) {
//                    Log.d(TAG, "login: onProgress");
//                }
//
//                @Override
//                public void onError(final int code, final String message) {
//                    Log.d(TAG, "login: onError: " + code);
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//                            Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            });


        }else{
            startActivity(createIntent(MainActivity.class));
            finish();
        }
    }

    private  void  getFriends(){
        try {
            List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
            Map<String ,EaseUser> users=new HashMap<String ,EaseUser>();
            for(String username:usernames){
                EaseUser user=new EaseUser(username);
                users.put(username, user);
            }
            App.getInstance().setContactList(users);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

}
