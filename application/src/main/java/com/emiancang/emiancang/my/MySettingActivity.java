package com.emiancang.emiancang.my;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emiancang.emiancang.App;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.LoginBaseActivity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.login.ForgetPasswrodActivity;
import com.emiancang.emiancang.login.LoginActivity;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.update.UpdateManager;
import com.emiancang.emiancang.update.ZwyCommon;
import com.emiancang.emiancang.user.UserManager;
import com.emiancang.emiancang.view.ShareWindow;
import com.hyphenate.EMCallBack;
import com.litesuits.common.utils.PackageUtil;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.emiancang.emiancang.update.UpdateManager.url;

/**
 * Created by 22310 on 2016/11/7.
 */

public class MySettingActivity extends LoginBaseActivity {


    @InjectView(R.id.setting_update_pwd)
    RelativeLayout settingUpdatePwd;
    @InjectView(R.id.setting_agreement)
    RelativeLayout settingAgreement;
    @InjectView(R.id.setting_correlation)
    RelativeLayout settingCorrelation;
    @InjectView(R.id.setting_invite)
    RelativeLayout settingInvite;
    @InjectView(R.id.my_indent_number)
    TextView myIndentNumber;
    @InjectView(R.id.icon_my_indent_goto)
    ImageView iconMyIndentGoto;
    @InjectView(R.id.setting_versions)
    RelativeLayout settingVersions;
    @InjectView(R.id.setting_exit)
    TextView settingExit;

    private AlertDialog mShowLoginDialog;

    boolean needUpdate = false;
    String downUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_my_setting, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(),R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("设置",getResources().getColor(R.color.white));
        getHeadBar().showRightImage();
        getHeadBar().setRightImage(R.drawable.icon_qiye_fenxiang);
        getHeadBar().setRightOnClickListner(v -> showShareWindow());
    }

    private void initView() {
        if(UserManager.getInstance().isLogin()){
            settingExit.setVisibility(View.VISIBLE);
        }else{
            settingExit.setVisibility(View.GONE);
        }
        UserService api = ApiUtil.createDefaultApi(UserService.class);
        ApiUtil.doDefaultApi(api.getAppVersion("123"), data -> {
            if(data != null && PackageUtil.getAppVersionCode(App.getContext()) < Integer.parseInt(data.getVcode())){
                needUpdate = true;
                myIndentNumber.setVisibility(View.VISIBLE);
                UpdateManager.setUrl(data.getDownpath());
                downUrl = data.getDownpath();
                if(data.getIsCompulsiveUpdate().equals("1"))
                    settingVersions.performClick();
            }
        });
    }

    @OnClick({R.id.setting_update_pwd, R.id.setting_agreement, R.id.setting_correlation, R.id.setting_invite, R.id.setting_versions, R.id.setting_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_update_pwd:
                if(UserManager.getInstance().isLogin())
                    startActivity(createIntent(ForgetPasswrodActivity.class));
                else
                    isLogin();
                break;
            case R.id.setting_agreement:
                //用户协议
                Intent agreement = createIntent(MyAgreementActivity.class);
                agreement.putExtra("url","http://www.emiancang.com:80/help/yhxy.htm");
                startActivity(agreement);
                break;
            case R.id.setting_correlation:
                //关于我们
                Intent correlation = createIntent(MyCorrelationActivity.class);
                correlation.putExtra("url","http://www.emiancang.com:80/emc/web/About_us.html");
                startActivity(correlation);
                break;
            case R.id.setting_invite:
                //邀请码
                if(UserManager.getInstance().isLogin())
                    startActivity(createIntent(MyInviteActivity.class));
                else
                    isLogin();
                break;
            case R.id.setting_versions:
                if(needUpdate) {
                    showInstallDialog();
                } else {
                    showToast("已是最新版本");
                }
                break;
            case R.id.setting_exit:
                removeDialog();
                break;
        }
    }
    private void removeDialog() {
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_servicelist_dialog, null);
        TextView title_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_title_tv);
        TextView content_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_content_tv);
        TextView verify_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_verify_tv);
        TextView cancel_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_cancel_tv);
        title_tv.setText("提示");
        content_tv.setText("是否退出当前用户？");
        cancel_tv.setText("退出");
        cancel_tv.setTextColor(getResources().getColor(R.color.half_red_color1));
        verify_tv.setText("取消");
        verify_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowLoginDialog.dismiss();
            }
        });
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        mShowLoginDialog = new AlertDialog.Builder(getActivity()).setView(dialogView).create();
        mShowLoginDialog.show();
    }

    //判断用户是否登陆
    private void isLogin() {
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_servicelist_dialog, null);
        TextView title_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_title_tv);
        TextView content_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_content_tv);
        TextView verify_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_verify_tv);
        TextView cancel_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_cancel_tv);
        title_tv.setText("提示");
        content_tv.setText("未登录状态，没有权限查看该内容，是否选择登录？");
        verify_tv.setOnClickListener(v -> {
            //登录页面
            startActivity(createIntent(LoginActivity.class));
            mShowLoginDialog.dismiss();
        });
        cancel_tv.setOnClickListener(v -> mShowLoginDialog.dismiss());
        mShowLoginDialog = new AlertDialog.Builder(getActivity()).setView(dialogView).create();
        mShowLoginDialog.show();
    }


    private void logout() {
        final ProgressDialog pd = new ProgressDialog(MySettingActivity.this);
        String st = getResources().getString(R.string.Are_logged_out);
        pd.setMessage(st);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        App.getInstance().logout(false,new EMCallBack() {

            @Override
            public void onSuccess() {
                runOnUiThread(() -> {
                    pd.dismiss();
                    UserManager.getInstance().remove();
                    showToast("退出当前用户");
                    finish();
                    mShowLoginDialog.dismiss();
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                runOnUiThread(() -> {
                    // TODO Auto-generated method stub
                    pd.dismiss();
                    Toast.makeText(MySettingActivity.this, "unbind devicetokens failed", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void showShareWindow() {
        ShareWindow shareWindow = new ShareWindow(getActivity(), v -> {
            switch (v.getId()) {
                case R.id.qq_share:
                    shareUrlToQQ(getString(R.string.app_name), "给大家分享一个很好用的APP：" + getString(R.string.app_name), url);
                    break;
                case R.id.wechat_share:
                    shareWebpageWechat(getString(R.string.app_name), "给大家分享一个很好用的APP：" + getString(R.string.app_name), url, true);
                    break;
                case R.id.wechatfriend_share:
                    shareWebpageWechat(getString(R.string.app_name), "给大家分享一个很好用的APP：" + getString(R.string.app_name), url, false);
                    break;
                case R.id.qzone_share:
                    shareUrlToQZone(getString(R.string.app_name), "给大家分享一个很好用的APP：" + getString(R.string.app_name), url);
                    break;
                case R.id.weibo_share:
//                    shareSina(url, getString(R.string.app_name), "给大家分享一个很好用的APP：" + getString(R.string.app_name), "给大家分享一个很好用的APP：" + getString(R.string.app_name), null);
                    Bitmap thumb = null;
                    if(thumb==null){
                        thumb = BitmapFactory.decodeResource(getActivity().getResources(),
                                R.drawable.logo_real);
                    }
                    Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb, 50, 50, true);
                    shareWebpage(createWebpageObj(getString(R.string.app_name), "给大家分享一个很好用的APP：" + getString(R.string.app_name), thumbBmp, url), createTextObj("给大家分享一个很好用的APP：" + getString(R.string.app_name)), createImageObj(thumbBmp));
                    thumb.recycle();
                    thumbBmp.recycle();
                    break;
            }
        });
        shareWindow.showAtLocation(findViewById(R.id.setting_exit), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public void showInstallDialog() {
        ZwyCommon.showTwoBtnDialog(getActivity(), getResources()
                        .getString(R.string.app_name), "检测到有新的版本，是否安装？",
                (dialog, which) -> {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent();
                    intent.setAction(UpdateManager.updaue_action);
                    intent.putExtra("update_flag", true);
                    intent.putExtra("url", downUrl);
                    sendBroadcast(intent);
                }, (dialog, which) -> {
                    // TODO Auto-generated method stub
                    // ZwyPreferenceManager.setLastShowUpdateDate(CommonUpdateService.this,System.currentTimeMillis());
                    dialog.dismiss();
                });
    }
}
