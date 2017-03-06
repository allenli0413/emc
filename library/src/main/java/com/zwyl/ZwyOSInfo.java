package com.zwyl;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.litesuits.common.utils.MD5Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 获取设备系统信息
 */
public class ZwyOSInfo {
    static public DisplayMetrics getDisplayMetrics() {
        return App.getContext().getResources()
                .getDisplayMetrics();
    }

    static public int getPhoneWidth() {
        DisplayMetrics dis = getDisplayMetrics();
        int screenWidth = dis.widthPixels;
        return screenWidth;
    }

    static public int getPhoneHeight() {
        DisplayMetrics dis = getDisplayMetrics();
        int screenHeight = dis.heightPixels;
        return screenHeight;
    }

    static String imei = null;

    /**
     * 得到imei
     *
     * @return
     * @author ForLyp
     */
    static public String getImei() {
        if (imei == null) {
            try {
                Context context = App.getContext();
                TelephonyManager mTelephonyMgr = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);

                imei = mTelephonyMgr.getDeviceId();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (TextUtils.isEmpty(imei)) {
                    imei = "null_imei";
                }
            }
        }

        return imei;

    }

    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    public static String getPhoneVesion() {
        return android.os.Build.VERSION.SDK;
    }

    public static String getPhoneVersionName() {
        return android.os.Build.VERSION.RELEASE;
    }


    public static int getSDKVersionNumber() {
        int sdkVersion;
        try {
            sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            sdkVersion = 0;
        }
        return sdkVersion;
    }

    public static String mAppName = null;

    public static String getAppName() {
        if (TextUtils.isEmpty(mAppName)) {
            PackageManager pm = App.getContext()
                    .getPackageManager();
            try {
                ApplicationInfo applicationInfo = null;
                try {
                    applicationInfo = pm.getApplicationInfo(
                            App.getContext().getPackageName(), 0);
                } catch (NameNotFoundException e) {
                    applicationInfo = null;
                }
                mAppName = (String) pm.getApplicationLabel(applicationInfo);
            } catch (Exception e) {
                mAppName = "";
            }
        }
        return mAppName;
    }

    private static int mVerCode;

    /**
     * @methods name: getVersionCode
     * @Descripition : 程序当前版本号
     * @date ：2012-7-23 下午04:50:16
     * @author ：wuxu
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public static int getVersionCode() {
        if (mVerCode == 0) {
            PackageManager pm =
                    App.getContext()
                            .getPackageManager();
            PackageInfo pkInfo = null;
            try {
                pkInfo = pm.getPackageInfo(App.getContext()
                                .getPackageName(),
                        PackageManager.GET_UNINSTALLED_PACKAGES);
            } catch (NameNotFoundException e) {
            }
            if (pkInfo == null) {
                return 0;
            }

            mVerCode = pkInfo.versionCode;
        }
        return mVerCode;
    }

    private static String mVerName = null;

    public static String getVersionName() {
        if (mVerName == null) {
            PackageManager pm = App.getContext()
                    .getPackageManager();
            PackageInfo pkInfo = null;
            try {
                pkInfo = pm.getPackageInfo(App.getContext()
                                .getPackageName(),
                        PackageManager.GET_UNINSTALLED_PACKAGES);
            } catch (NameNotFoundException e) {
            }
            if (pkInfo == null) {
                return null;
            }

            mVerName = pkInfo.versionName;
        }
        return mVerName;
    }

    public static String sign;

    public static Signature[] getSignature() {

        PackageManager pm = App.getContext().getPackageManager();
        PackageInfo packageinfo;
        try {
            packageinfo = pm.getPackageInfo(App.getContext()
                    .getPackageName(), PackageManager.GET_SIGNATURES);
            return packageinfo.signatures;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getMd5Sign() {
        if (!TextUtils.isEmpty(sign)) {
            return sign;
        }
        Signature[] signs = getSignature();
        if ((signs == null) || (signs.length == 0)) {
            return null;
        } else {
            Signature sign = signs[0];
            String signMd5 = new String(MD5Util.md5(sign.toByteArray()));
            return signMd5;
        }
    }

    /*获取系统时间 格式为："yyyy/MM/dd "*/
    public static String getCurrentDate() {
        Date d = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return sf.format(d);
    }

    /*将字符串转为时间戳*/
    public static String getStringToDate() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date();
        try {
            date = sf.parse(getCurrentDate());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime()+"";
    }

    /**
     * 生成随机字符串
     * @return
     */
    public static String getRandomString() { //length表示生成字符串的长度
        int max=128;
        int min=1;
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        int length = random.nextInt(max)%(max-min+1) + min;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
