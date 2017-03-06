package com.emiancang.emiancang.update;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.uitl.ZwyContextKeeper;
import com.emiancang.emiancang.uitl.ZwyFileOption;
import com.emiancang.emiancang.uitl.ZwyNetworkUtils;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class CommonUpdateService extends Service implements
        ZwyHttpClientUtils.ZwyDownloadListener {
    public final static String download_path = ZwyContextKeeper.getSDCardPath();
    public static final String download_name = "update.apk";

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    boolean auto_download_flag;
    public static boolean isDownloading = false;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(UpdateManager.updaue_action);
        registerReceiver(updatereceiver, filter);
        Intent intent = new Intent(CommonUpdateService.this,
                AlarmReceiver.class);
        // // 发送一个广播
        intent.setAction("repeating");
        PendingIntent sender = PendingIntent.getBroadcast(
                CommonUpdateService.this, 0, intent, 0);
        //
        // 随机一个时间点。当前时间+随即时间即为下次运行时间
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (ZwyPreferenceManager.getPlanUpdateTime(CommonUpdateService.this) <= 0) {
            Random r = new Random();
            ZwyPreferenceManager.setPlanUpdateTime(CommonUpdateService.this,
                    r.nextInt(19) * 60 * 60 * 1000 + r.nextInt(59) * 60 * 1000);
        }
        final Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        Date dd = new Date(calendar.getTime().getYear(), calendar.getTime()
                .getMonth(), calendar.getTime().getDate() + 1);
        long target_time = dd.getTime()
                + ZwyPreferenceManager
                .getPlanUpdateTime(CommonUpdateService.this);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP,
                target_time + 1000 * 60 * 2, 3 * 24 * 60 * 60 * 1000, sender);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(updatereceiver);
    }

    ProgressDialog progressDialog = null;
    Handler myHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case UpdateManager.BEGIN_DOWNLOAD_TAG:
                    Toast.makeText(ZwyContextKeeper.getInstance(), "开始下载",
                            Toast.LENGTH_LONG).show();
                    break;
                case UpdateManager.DOWNLOAD_OK_TAG:
                    Toast.makeText(ZwyContextKeeper.getInstance(), "下载完成",
                            Toast.LENGTH_LONG).show();
                    break;
                case UpdateManager.DOWNLOAD_ERROR_TAG:
                    Toast.makeText(ZwyContextKeeper.getInstance(), "下载失败,请稍后重试",
                            Toast.LENGTH_LONG).show();
                    break;
                case UpdateManager.UPDATE_BEGIN_TAG:
                    progressDialog = new ProgressDialog(CommonUpdateService.this);
                    progressDialog.setMessage("正在检查更新...");
                    progressDialog.getWindow().setType(
                            WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    progressDialog.show();
                    break;
                case UpdateManager.UPDATE_END_TAG:
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.cancel();
                    }
                    Toast.makeText(ZwyContextKeeper.getInstance(), "暂无版本信息,请稍后重试",
                            Toast.LENGTH_LONG).show();
                    break;
                case UpdateManager.UPDATE_ERROR_TAG:
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.cancel();
                    }
                    Toast.makeText(ZwyContextKeeper.getInstance(), "检查更新失败，请稍后重试",
                            Toast.LENGTH_LONG).show();
                    break;
                case UpdateManager.SHOW_UPDATE_TAG:
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.cancel();
                    }
                    showUpdate(ZwyPreferenceManager
                            .getUpdatepath(CommonUpdateService.this));
                    break;
                case UpdateManager.NO_UPDATE_TAG:
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.cancel();
                    }
                    String messsage = (String) msg.obj;
                    ZwyCommon.showSimpleDialog(CommonUpdateService.this, "更新信息",
                            "当前版本为最新", null);
                    break;
                case UpdateManager.AUTO_UPDATE_DOWNLOAD_TAG:
                    // 自动下载
                    if (ZwyFileOption.isExistSDCard()) {
                        auto_download_flag = true;
                        autoDownloadAPK();
                    }
                case UpdateManager.AUTO_UPDATE_INSTALL_TAG:
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.cancel();
                    }
                    showInstallDialog();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    public void showInstallDialog() {
        ZwyCommon.showTwoBtnDialog(CommonUpdateService.this, getResources()
                        .getString(R.string.app_name), "检测到有新的版本，是否安装？",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Intent installIntent = new Intent();

                        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        File f = new File(download_path + download_name);
                        if (f.exists() && f.isFile()) {
                            installIntent
                                    .setAction(Intent.ACTION_VIEW);
                            /* 调用getMIMEType()来取得MimeType */
                            String type = "application/vnd.android.package-archive";
							/* 设置intent的file与MimeType */
                            installIntent.setDataAndType(Uri.fromFile(f), type);
                            startActivity(installIntent);
                        } else {
                            ZwyCommon.showToast("安装包已损坏");
                        }
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        // ZwyPreferenceManager.setLastShowUpdateDate(CommonUpdateService.this,System.currentTimeMillis());
                    }
                });
    }

    private void showUpdate(final Element element) {
        if (ZwyPreferenceManager.isUpdateFlag(this)) {
            {
                if (element != null && element.mDownLoadPath != null
                        && !"error".equals(element.mDownLoadPath) && element.mVersionCode > ZwyPreferenceManager.getVersionCode()) {
                    // 提示有更新
                    String showInfo = "";
                    String app_des = element.mDesc;
                    String mVersion = element.mVersion;
                    if (!TextUtils.isEmpty(mVersion)) {
                        showInfo = "版本:" + mVersion + "\n";
                    }
                    if (app_des != null && !app_des.equals("kong")) {
                        showInfo = showInfo + app_des;
                    } else
                        showInfo = showInfo + "更新内容：暂无描述";
                    ZwyCommon.showTwoBtnDialog(CommonUpdateService.this,
                            "更新信息", showInfo,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    if (!ZwyNetworkUtils.isNetCanUse()) {
                                        ZwyCommon.showToast("网络不可用");
                                        return;
                                    }
                                    ;
                                    new Thread() {
                                        public void run() {
                                            if (isDownloading) {
                                                myHandler
                                                        .sendEmptyMessage(UpdateManager.DOWNLOAD_ERROR_TAG);
                                                return;
                                            }
                                            myHandler
                                                    .sendEmptyMessage(UpdateManager.BEGIN_DOWNLOAD_TAG);
                                            ZwyHttpClientUtils
                                                    .downLoadFile(
                                                            element.mDownLoadPath,
                                                            ZwyContextKeeper
                                                                    .getSDCardPath()
                                                                    + download_name,
                                                            CommonUpdateService.this
                                                                    .getString(R.string.app_name),
                                                            CommonUpdateService.this);
                                        }

                                        ;
                                    }.start();
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    // ZwyPreferenceManager.setLastShowUpdateDate(CommonUpdateService.this,System.currentTimeMillis());
                                }
                            });
                }
            }
        }

    }

    void checkUpdateAuto() {
        try {
            new Thread() {
                public void run() {
                    // 检查更新
                    UpdateManager updateManager = new UpdateManager(
                            CommonUpdateService.this, myHandler);
                    updateManager.updateAuto();
                }

                ;
            }.start();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void checkUpdateByUser() {
        try {
            new Thread() {
                public void run() {
                    // 检查更新
                    UpdateManager updateManager = new UpdateManager(
                            CommonUpdateService.this, myHandler);
                    updateManager.updateByUser();
                }

                ;
            }.start();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void autoDownloadAPK() {
        final Element element = ZwyPreferenceManager
                .getUpdatepath(CommonUpdateService.this);
        if (element != null && !isDownloading) {
            new Thread() {
                public void run() {
                    ZwyHttpClientUtils.downLoadFile(element.mDownLoadPath,
                            download_path + download_name,
                            CommonUpdateService.this
                                    .getString(R.string.app_name),
                            CommonUpdateService.this);
                }

                ;
            }.start();
        }

    }

    void autoDownloadAPK(String url) {
        if (!isDownloading) {
            new Thread() {
                public void run() {
                    ZwyHttpClientUtils.downLoadFile(url,
                            download_path + download_name,
                            CommonUpdateService.this
                                    .getString(R.string.app_name),
                            CommonUpdateService.this);
                }
            }.start();
        }
    }

    private void showUpdate() {
        new Thread() {
            public void run() {
                if (ZwyPreferenceManager.isUpdateFlag(CommonUpdateService.this)) {
                    final Element element = ZwyPreferenceManager
                            .getUpdatepath(CommonUpdateService.this);
                    if (element != null && element.mDownLoadPath != null) {
                        String apk_path = download_path + download_name;
                        File apkFile = new File(apk_path);
                        ZwyPreferenceManager.setLastShowUpdateDate(
                                CommonUpdateService.this,
                                System.currentTimeMillis());
                        if (!apkFile.exists()) {
                            // 不存在，展示有更新，然后用户点击下载
                            myHandler
                                    .sendEmptyMessage(UpdateManager.SHOW_UPDATE_TAG);
                        } else {
                            // 存在，判断版本
                            int apkVersion = ZwySystemUtil
                                    .getApkVersion(apk_path);
                            myHandler
                                    .sendEmptyMessage(UpdateManager.SHOW_UPDATE_TAG);
                            if (element.mVersionCode > apkVersion) {
                                // 服务器版本大于当前下载的版本
                                myHandler
                                        .sendEmptyMessage(UpdateManager.SHOW_UPDATE_TAG);
                            } else {
                                // 显示安装
                                myHandler
                                        .sendEmptyMessage(UpdateManager.AUTO_UPDATE_INSTALL_TAG);

                            }
                        }
                    }
                }
            }

            ;
        }.start();
    }

    BroadcastReceiver updatereceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(UpdateManager.updaue_action)) {
                String url = intent.getStringExtra("url");
                if(!TextUtils.isEmpty(url))
                    autoDownloadAPK(url);
//                // 标示更新的方式来源
//                boolean update_flag = intent.getBooleanExtra("update_flag",
//                        false);
//                // 显示是否是展示更新界面
//                boolean flag = intent.getBooleanExtra("show_update", false);
//                if (flag) {
//                    showUpdate();
//                } else {
//                    if (update_flag) {
//                        checkUpdateByUser();
//                    } else {
//                        checkUpdateAuto();
//                    }
//                }
            }
        }

        ;
    };

    @Override
    public void onProgressChanged(String name, int progress) {
        // TODO Auto-generated method stub
        if (auto_download_flag) {
            // 自动下载，不在通知栏显示
            // 等待用户下次打开显示
            if (progress == 100) {
                isDownloading = false;
            }
        } else {
            // 在通知栏显示
            ZwyNotificationsManager.showDownLoadNotification(this,
                    R.drawable.logo, download_name, progress);
            if (progress == 100) {
                myHandler.sendEmptyMessage(UpdateManager.DOWNLOAD_OK_TAG);
                isDownloading = false;
            } else if (progress == -1) {
                myHandler.sendEmptyMessage(UpdateManager.DOWNLOAD_ERROR_TAG);
                ZwyNotificationsManager.closeNotification(this,
                        ZwyNotificationsManager.DOWNLOAD_FLAG);
            }
        }

    }

    @Override
    public void onError(String error) {
        // TODO Auto-generated method stub
        isDownloading = false;
        if (auto_download_flag) {
            // 自动下载，错误了删除apk包
            String apk_path = download_path + download_name;
            File apkFile = new File(apk_path);
            if (!apkFile.exists()) {
                // 不存在 即没开始下载
            } else {
                // 存在，则删除
                apkFile.delete();
            }
        } else {
            ZwyNotificationsManager.closeNotification(this,
                    ZwyNotificationsManager.DOWNLOAD_FLAG);
            myHandler.sendEmptyMessage(UpdateManager.DOWNLOAD_ERROR_TAG);
        }

    }

}
