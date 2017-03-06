package com.zwyl;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;

import java.lang.reflect.Field;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: Application, 如果要使用此类，请先初始化，如果是继承，不需要手动调用init方法
 * @date 15-6-9 下午8:35
 */
public class App extends Application {

    protected static Context mContext;
    static boolean isDebug;//是否为debug

    static String TAG = "zwylApp";//通用log

    @Override
    public void onCreate() {
        super.onCreate();
        init(this, false, TAG);
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setAppChannel("ebtang");
        strategy.setAppVersion("v1.0");
        CrashReport.initCrashReport(this, "900034413", true,strategy);
    }


    /**
     * 初始化
     *
     * @param context
     */
    public static void init(Context context, boolean debug, String tag) {
        mContext = context;
        isDebug = debug;
        if (tag != null && tag.length() >0){
            TAG = tag;
        }

        //初始化logger的参数
        Logger
                .init(TAG)  // default PRETTYLOGGER or use just init()
//                .setMethodCount(3)            // default 2
//                .hideThreadInfo()             // default shown
                .setLogLevel(isDebug ? LogLevel.FULL : LogLevel.NONE);
        // default LogLevel.FULL
//                .setMethodOffset(2);

    }


    /**
     * Gets a field from the project's BuildConfig. This is useful when, for example, flavors
     * are used at the project level to set custom fields.
     *
     * @param fieldName The name of the field-to-access
     * @return The value of the field, or {@code null} if the field is not found.
     */
    public static Object getBuildConfigValue(String fieldName) {
        try {
            Class<?> clazz = Class.forName(mContext.getPackageName() + ".BuildConfig");
            Field field = clazz.getField(fieldName);
            return field.get(null);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static Context getContext() {
        return mContext;
    }
}
