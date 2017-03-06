package com.emiancang.emiancang;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.amap.api.navi.AMapNavi;
import com.emiancang.emiancang.hx.Constant;
import com.emiancang.emiancang.hx.db.EaseUser;
import com.emiancang.emiancang.hx.db.Myinfo;
import com.emiancang.emiancang.hx.db.UserDao;
import com.emiancang.emiancang.service.EasemobService;
import com.emiancang.emiancang.uitl.JsonUtil;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;
import com.emiancang.emiancang.uitl.ZwyContextKeeper;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class App extends com.zwyl.App {

    public static String USERID = "";
    public static String TOKEN = "";
    public static String HXPASSWORD = "EmianCaNg888";//1.17 去掉下划线,嗯,IOS说的

    String tag = "App";  //????

    private String username = "";
    private Map<String, EaseUser> contactList;

    private UserDao userDao;
    private boolean sdkInited = false;

    private static App instance;

    private EasemobService.EasemobBinder easemobBinder = null;
    private ServiceConnection serviceConnection = null;

    public static App getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init(this, BuildConfig.DEBUG, tag);
        Fresco.initialize(this);
        ZwyContextKeeper.init(this, null);

//        //极光
//        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);
        MultiDex.install(this);
        AMapNavi.setApiKey(this, "1a22319143cfef79fd28358b966c4565");
        String userJson = SharedPrefsUtil.getValue(App.getContext(),SharedPrefsUtil.USERKEY, "");
        if(!TextUtils.isEmpty(userJson)){
            User user = JsonUtil.fromJson(userJson,User.class);
            UserManager.getInstance().add(user);
        }

        init(getApplicationContext());
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                try {
                    easemobBinder = (EasemobService.EasemobBinder) service;
                } catch (ClassCastException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                easemobBinder = null;
            }
        };
        Intent intent = new Intent(getContext(), EasemobService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        unbindService(serviceConnection);
        SharedPrefsUtil.remove(getContext(), SharedPrefsUtil.KEEP_CITY);
        SharedPrefsUtil.remove(getContext(), SharedPrefsUtil.KEEP_CITY_ID);
    }

    public EasemobService.EasemobBinder getEasemobBinder() {
        return easemobBinder;
    }

    /*
	 * 第一步：sdk的一些参数配置 EMOptions 第二步：将配置参数封装类 传入SDK初始化
	 */

    public void init(Context context) {
        // 第一步
        EMOptions options = initChatOptions();
        // 第二步
        boolean success = initSDK(context, options);
        if (success) {
            // 设为调试模式，打成正式包时，最好设为false，以免消耗额外的资源
            EMClient.getInstance().setDebugMode(true);
            // 初始化数据库
            initDbDao(context);
        }
    }

    private EMOptions initChatOptions() {
        // 获取到EMChatOptions对象
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 设置是否需要已读回执
        options.setRequireAck(true);
        // 设置是否需要已送达回执
        options.setRequireDeliveryAck(false);
        return options;
    }

    public synchronized boolean initSDK(Context context, EMOptions options) {
        if (sdkInited) {
            return true;
        }

        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);

        // 如果app启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process
        // name就立即返回
        if (processAppName == null || !processAppName.equalsIgnoreCase(getApplicationContext().getPackageName())) {

            // 则此application::onCreate 是被service 调用的，直接返回
            return false;
        }
        if (options == null) {
            EMClient.getInstance().init(context, initChatOptions());
        } else {
            EMClient.getInstance().init(context, options);
        }
        sdkInited = true;
        return true;
    }

    private void initDbDao(Context context) {
        userDao = new UserDao(context);
    }

    /**
     * check the application process name if process name is not qualified, then
     * we think it is a service process and we will not init SDK
     *
     * @param pID
     * @return
     */
    @SuppressWarnings("rawtypes")
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {

                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
            }
        }
        return processName;
    }

    public void setCurrentUserName(String username) {
        this.username = username;
        Myinfo.getInstance(this).setUserInfo(Constant.KEY_USERNAME, username);
    }

    public String getCurrentUserName() {
        if (TextUtils.isEmpty(username)) {
            username = Myinfo.getInstance(this).getUserInfo(Constant.KEY_USERNAME);

        }
        return username;

    }

    public void setContactList(Map<String, EaseUser> contactList) {

        this.contactList = contactList;

        userDao.saveContactList(new ArrayList<EaseUser>(contactList.values()));

    }

    public Map<String, EaseUser> getContactList() {

        if (contactList == null) {

            contactList = userDao.getContactList();

        }
        return contactList;

    }


    /**
     * 退出登录
     *
     * @param unbindDeviceToken
     *            是否解绑设备token(使用GCM才有)
     * @param callback
     *            callback
     */
    public void logout(boolean unbindDeviceToken, final EMCallBack callback) {

        EMClient.getInstance().logout(unbindDeviceToken, new EMCallBack() {

            @Override
            public void onSuccess() {

                if (callback != null) {
                    callback.onSuccess();
                }

            }

            @Override
            public void onProgress(int progress, String status) {
                if (callback != null) {
                    callback.onProgress(progress, status);
                }
            }

            @Override
            public void onError(int code, String error) {

                if (callback != null) {
                    callback.onError(code, error);
                }
            }
        });
    }
}
