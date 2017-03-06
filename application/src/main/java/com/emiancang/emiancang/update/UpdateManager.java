package com.emiancang.emiancang.update;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;


import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.uitl.ZwyContextKeeper;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UpdateManager {
//    public static final String url = "http://101.200.128.155:8080/house/version/versionUpdating.action";
    public static String url = ApiUtil.HEHE_URL + "home/getAppVersion";
    public static final int BEGIN_DOWNLOAD_TAG = 1000;
    public static final int DOWNLOAD_OK_TAG = 2000;
    public static final int DOWNLOAD_ERROR_TAG = 3000;
    public static final int UPDATE_BEGIN_TAG = 4000;
    public static final int UPDATE_END_TAG = 5000;
    public static final int NO_UPDATE_TAG = 6000;
    public static final int SHOW_UPDATE_TAG = 7000;
    public static final int UPDATE_ERROR_TAG = 8000;
    public static final int AUTO_UPDATE_DOWNLOAD_TAG = 9000;
    public static final int AUTO_UPDATE_INSTALL_TAG = 9001;
    public static String updaue_action = ZwyContextKeeper.getInstance().getPackageName() + UpdateManager.class.getName();
    public static String download_action = ZwyContextKeeper.getInstance().getPackageName() + "down_load_file";
    Context mContext = null;
    Handler myHandler;

    public UpdateManager(Context context, Handler handler) {
        mContext = context;
        myHandler = handler;
    }

    public static void setUrl(String url) {
        UpdateManager.url = url;
    }

    /**
     * @param @param  context
     * @param @param  flag 是否显示更新对话框
     * @param @return
     * @return Element
     * @Title: update
     * @Description:
     * @author anony_god
     * @date 2013-6-13 下午08:04:00
     * @version V1.0
     */

    public Element updateByUser() {
        myHandler.sendEmptyMessage(UPDATE_BEGIN_TAG);
        long time = System.currentTimeMillis();
//		String url = "http://dl.dadashunfengche.cn/dada/app_version/dada.conf";
        String res = ZwyHttpClientUtils.getUpdateInfo(url);
        if (res != null) {
            List<Element> list = parserResult(res);
            if (list == null || list.size() <= 0) {
                // meiyou
            }
            for (final Element ele : list) {
                if (ele.mGid.equals(ZwyContextKeeper.getInstance()
                        .getPackageName())) {
                    // 需要更新
                    if (ele.mVersionCode > ZwyPreferenceManager
                            .getVersionCode()) {
                        // 需要更新
                        ZwyPreferenceManager.setUpdatePath(
                                ZwyContextKeeper.getInstance(), ele);
                        ZwyPreferenceManager.setUpdateFlag(
                                ZwyContextKeeper.getInstance(), true);
                        if (System.currentTimeMillis() - time < 1000) {
                            SystemClock.sleep(1000);
                        }
                        String apk_path = CommonUpdateService.download_path + CommonUpdateService.download_name;
                        File apkFile = new File(apk_path);
                        if (!apkFile.exists()) {
                            //不存在，则直接下载
                            myHandler.sendEmptyMessage(SHOW_UPDATE_TAG);
                        } else {
                            //存在，判断版本
                            int apkVersion = ZwySystemUtil.getApkVersion(apk_path);
                            if (ele.mVersionCode > apkVersion) {
                                //服务器版本大于当前下载的版本
                                myHandler.sendEmptyMessage(SHOW_UPDATE_TAG);
                            } else {
                                //显示安装
                                myHandler.sendEmptyMessage(AUTO_UPDATE_INSTALL_TAG);

                            }
                        }
                        return ele;
                    } else {
                        // 不需要更新
                        // ZwyPreferenceManager.setUpdatePath(context, null);
                        ZwyPreferenceManager.setUpdateFlag(
                                ZwyContextKeeper.getInstance(), false);
                        if (System.currentTimeMillis() - time < 1000) {
                            SystemClock.sleep(1000);
                        }
                        myHandler.sendEmptyMessage(NO_UPDATE_TAG);

                        return null;
                    }

                }
            }

        } else {
            if (System.currentTimeMillis() - time < 1000) {
                SystemClock.sleep(1000);
            }
            myHandler.sendEmptyMessage(UPDATE_ERROR_TAG);
            return null;
        }
        if (System.currentTimeMillis() - time < 1000) {
            SystemClock.sleep(1000);
        }
        myHandler.sendEmptyMessage(UPDATE_END_TAG);
        return null;
    }

    /**
     * @param @param  context
     * @param @param  flag 是否显示更新对话框
     * @param @return
     * @return Element
     * @Title: update
     * @Description:
     * @author anony_god
     * @date 2013-6-13 下午08:04:00
     * @version V1.0
     */

    public Element updateAuto() {
        long time = System.currentTimeMillis();
//		String url = "http://dl.dadashunfengche.cn/dada/app_version/dada.conf";
        String res = ZwyHttpClientUtils.getUpdateInfo(url);
        if (res != null) {
            List<Element> list = parserResult(res);
            if (list == null || list.size() <= 0) {
                // meiyou
            }
            for (final Element ele : list) {
                if (ele.mGid.equals(ZwyContextKeeper.getInstance()
                        .getPackageName())) {
                    // 需要更新
                    if (ele.mVersionCode > ZwyPreferenceManager
                        .getVersionCode()) {
                            // 需要更新
                        ZwyPreferenceManager.setUpdatePath(
                                ZwyContextKeeper.getInstance(), ele);
                        ZwyPreferenceManager.setUpdateFlag(
                                ZwyContextKeeper.getInstance(), true);
                        if (System.currentTimeMillis() - time < 1000) {
                            SystemClock.sleep(1000);
                        }
                        String apk_path = CommonUpdateService.download_path + CommonUpdateService.download_name;
                        File apkFile = new File(apk_path);
                        if (!apkFile.exists()) {
                            //不存在，则直接下载
                            myHandler.sendEmptyMessage(AUTO_UPDATE_DOWNLOAD_TAG);
                        } else {
                            //存在，判断版本
                            int apkVersion = ZwySystemUtil.getApkVersion(apk_path);
                            if (ele.mVersionCode > apkVersion) {
                                //服务器版本大于当前下载的版本
                                myHandler.sendEmptyMessage(AUTO_UPDATE_DOWNLOAD_TAG);
                            }
                        }
                        return ele;
                    } else {
                        // 不需要更新
//						 ZwyPreferenceManager.setUpdatePath(context, null);
                        ZwyPreferenceManager.setUpdateFlag(
                                ZwyContextKeeper.getInstance(), false);
                        if (System.currentTimeMillis() - time < 1000) {
                            SystemClock.sleep(1000);
                        }

                        return null;
                    }

                }
            }

        } else {
            return null;
        }
        return null;
    }

    public List<Element> parserResult(String aRes) {
        List<Element> res = new ArrayList<Element>();
        try {
            JSONObject parentObj = new JSONObject(aRes);
            JSONObject obj = new JSONObject(parentObj.getString("info"));
            Element ele = new Element();
            ele.mGid = obj.getString("pname");
            ele.mVersion = obj.getString("vname");
            ele.mVersionCode = obj.getInt("vcode");
            ele.mDesc = obj.getString("content");
            ele.mDownLoadPath = obj.getString("downpath");
//            ele.mDownLoadPath = "http://dl.dadashunfengche.cn/dada/app_version/ddsfc1.3.apk";
            res.add(ele);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

}
