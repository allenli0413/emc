package com.zwyl;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.util.Set;

/**
 * @ Title MainActivity.java
 * @ Author Wan,Faxue
 * @ Date 14-5-7 下午4:28
 * @ Description: log for app
 * @ Version V1.0
 */
public class Logger {

    static String className;
    static String methodName;
    static int lineNumber;
    static String TAG = "Logger";

    public static boolean DEBUG = true;

    private Logger() {
        /* Protect from instantiations */
    }

    public static boolean isDebuggable() {
//        Log.i("Logger build config :", "" + BuildConfig.DEBUG);
//        return BuildConfig.DEBUG;
        return DEBUG || Log.isLoggable(TAG, Log.VERBOSE);
    }

    private static String createLog(String log) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(methodName);
        buffer.append(":");
        buffer.append(lineNumber);
        buffer.append("]");
        buffer.append(log);

        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        try {
            className = sElements[1].getFileName().substring(0, sElements[1].getFileName().lastIndexOf("."));

        } catch (Exception e) {
            className = sElements[1].getFileName();

        }
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(String message) {
        e(className, message);
    }

    public static void e(String tag, String message) {
        if (!isDebuggable())
            return;

        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        Log.e(tag, createLog(message));
    }

    public static void i(String message) {
        i(className, message);
    }


    public static void i(String tag, String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.i(tag, createLog(message));
    }

    public static void d(String message) {
        d(className, message);
    }


    public static void d(String tag, String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.d(tag, createLog(message));
    }

    public static void d(Intent intent) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(buildIntent(intent)));
    }

    public static void d(Bundle bundle) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(buildBundle(bundle)));
    }

    public static void v(String message) {
        v(className, message);
    }


    public static void v(String tag, String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.v(tag, createLog(message));
    }

    public static void w(String message) {
        w(className, message);
    }

    public static void w(String tag, String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.w(tag, createLog(message));
    }

    public static void wtf(String message) {
        wtf(className, message);
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    public static void wtf(String tag, String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(tag, createLog(message));
    }

    // -------------------------------------------------------------------------------------------

    /**
     * @param intent
     * @return
     */
    private static String buildIntent(Intent intent) {
        StringBuffer sb = new StringBuffer();
        if (intent == null) {
            sb.append("Intent is null.");
            return sb.toString();
        }
        sb.append("Intent action = " + intent.getAction() + '\n');

        // build extras
        Bundle extras = intent.getExtras();
        sb.append("Intent bundl e= " + buildBundle(extras));
        return sb.toString();
    }

    /**
     * build extras
     *
     * @param bundle
     * @return
     */
    private static String buildBundle(Bundle bundle) {
        StringBuffer sb = new StringBuffer();
        if (bundle != null) {
            Set<String> keys = bundle.keySet();
            if (keys == null || keys.isEmpty()) {
                sb.append("bundle: empty");
            } else {
                for (String key : keys) {
                    Object value = bundle.get(key);
                    if (value instanceof Bundle) {
                        sb.append(buildBundle(key, (Bundle) value));
                    } else {
                        sb.append(key);
                        sb.append("=");
                        sb.append(value.getClass().getName());
                        sb.append("-->");
                        sb.append(value);
                    }
                    sb.append('\n');
                }
            }
        } else {
            sb.append("bundle: null");
        }
        return sb.toString();
    }

    /**
     * @param bundleKey
     * @param bundle
     * @return
     */
    private static StringBuffer buildBundle(String bundleKey, Bundle bundle) {
        StringBuffer sb = new StringBuffer();
        sb.append(bundleKey);
        sb.append('\n');

        // build extras
        sb.append(buildBundle(bundle));
        return sb;
    }
    // -------------------------------------------------------------------------------------------
}
