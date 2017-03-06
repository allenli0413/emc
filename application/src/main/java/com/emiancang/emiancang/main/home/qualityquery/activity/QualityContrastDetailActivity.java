package com.emiancang.emiancang.main.home.qualityquery.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.emiancang.emiancang.main.home.redpapercotton.activity.RedPaperCottonActivity;
import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.LoginBaseActivity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.login.LoginActivity;
import com.emiancang.emiancang.main.home.model.ScanModel;
import com.emiancang.emiancang.main.home.qualityquery.model.QualityContrastDetailStatusModel;
import com.emiancang.emiancang.main.home.qualityquery.model.QualityQueryModel;
import com.emiancang.emiancang.main.home.qualityquery.service.QualityContrastDetailStatusService;
import com.emiancang.emiancang.main.home.redpapercotton.activity.RedPaperCottonDetailActivity;
import com.emiancang.emiancang.main.home.service.StoreService;
import com.emiancang.emiancang.nearby.NearbyDetailsErrorChatActivity;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.UserManager;
import com.emiancang.emiancang.view.ShareWindow;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by yuanyueqing on 2016/11/12.
 */

public class QualityContrastDetailActivity extends LoginBaseActivity {

    @InjectView(R.id.wv_quality_contrast_detail)
    WebView wv_quality_contrast_detail;
    @InjectView(R.id.tv_quality_contrast_detail_share)
    TextView tv_quality_contrast_detail_share;
    @InjectView(R.id.tv_quality_contrast_detail_cart)
    TextView tv_quality_contrast_detail_cart;

    String ph;
    int flag = 0;
    @InjectView(R.id.ll_quality_contrast_detail_bottom)
    LinearLayout llQualityContrastDetailBottom;
    @InjectView(R.id.myProgressBar)
    ProgressBar myProgressBar;

    Dialog dialog;
    AlertDialog loginDialog;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_contrast_detail);
        ButterKnife.inject(this);
        flag = getIntent().getIntExtra("flag", 0);
        QualityQueryModel item;
        ScanModel data;
        if(flag == 0) {
            item = (QualityQueryModel) getIntent().getSerializableExtra("data");
            ph = item.getGcmgyMhph();
        }
        if(flag == 1) {
            data = (ScanModel) getIntent().getSerializableExtra("data");
            ph = data.getHyJysjPh();
        }
        url = "http://www.emiancang.com/emc/web/quality_detail.html?ph=" + ph;
        initHead();
        initView();
    }

    private void initView() {
        initDialog();
        tv_quality_contrast_detail_share.setText("分享");
        tv_quality_contrast_detail_cart.setText("帮我找");
//        wv_quality_contrast_detail.loadUrl("file:///android_asset/quality_detail.html");
        wv_quality_contrast_detail.loadUrl(url);
        wv_quality_contrast_detail.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv_quality_contrast_detail.setWebChromeClient(new WebChromeClient());
        wv_quality_contrast_detail.getSettings().setJavaScriptEnabled(true);
        wv_quality_contrast_detail.getSettings().setAllowFileAccess(true);// 设置允许访问文件数据
        wv_quality_contrast_detail.getSettings().setSupportZoom(true);
        wv_quality_contrast_detail.getSettings().setBuiltInZoomControls(true);
        wv_quality_contrast_detail.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv_quality_contrast_detail.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wv_quality_contrast_detail.getSettings().setDomStorageEnabled(true);
        wv_quality_contrast_detail.getSettings().setDatabaseEnabled(true);

        wv_quality_contrast_detail.addJavascriptInterface(new JavaScriptinterface(), "android");

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        wv_quality_contrast_detail.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if(dialog != null) {
                    dialog.show();
                    createDialog();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(dialog != null)
                    dialog.dismiss();
            }
        });


        wv_quality_contrast_detail.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    myProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == myProgressBar.getVisibility()) {
                        myProgressBar.setVisibility(View.VISIBLE);
                    }
                    myProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    private void initDialog(){
        dialog = new Dialog(getActivity(), R.style.custom_dialog);
    }

    private void createDialog() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.html_view_loading, null, false);
        ImageView iamge = (ImageView) view.findViewById(R.id.animationIV);
        int[] images = { R.drawable.qb_tenpay_loading_1_go, R.drawable.qb_tenpay_loading_2_go};
        int SIGN = 17;
        final int[] num = {0};
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == SIGN) {
                    iamge.setImageResource(images[num[0]++]);
                    if (num[0] >= images.length) {
                        num[0] = 0;
                    }
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Message msg = new Message();
                msg.what = SIGN;
                handler.sendMessage(msg);
            }
        }, 100, 100);
        dialog.setContentView(view);
        dialog.setOnDismissListener(dialog1 -> timer.cancel());
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle(ph, getResources().getColor(R.color.white));
        getHeadBar().showLeftImage();
        getHeadBar().setLeftOnClickListner(v -> finish());
        getHeadBar().showRightImage();
        getHeadBar().setRightImage(R.drawable.share_white);
        getHeadBar().setRightOnClickListner(v -> showShareWindow());
    }

    private void showShareWindow() {
        ShareWindow shareWindow = new ShareWindow(getActivity(), v -> {
            switch (v.getId()) {
                case R.id.qq_share:
                    shareUrlToQQ("我发现一批棉花：" + ph, "找棉花来e棉仓", url);
                    break;
                case R.id.wechat_share:
                    shareWebpageWechat("我发现一批棉花：" + ph, "找棉花来e棉仓", url, true);
                    break;
                case R.id.wechatfriend_share:
                    shareWebpageWechat("我发现一批棉花：" + ph, "找棉花来e棉仓", url, false);
                    break;
                case R.id.qzone_share:
                    shareUrlToQZone("我发现一批棉花：" + ph, "找棉花来e棉仓", url);
                    break;
                case R.id.weibo_share:
//                    shareSina(url, "我发现一批棉花：" + ph, "找棉花来e棉仓", "找棉花来e棉仓", null);
                    Bitmap thumb = null;
                    if(thumb==null){
                        thumb = BitmapFactory.decodeResource(getActivity().getResources(),
                                R.drawable.logo_real);
                    }
                    Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb, 50, 50, true);
                    shareWebpage(createWebpageObj("我发现一批棉花：" + ph, "找棉花来e棉仓", thumbBmp, url), createTextObj("找棉花来e棉仓"), createImageObj(thumbBmp));
                    thumb.recycle();
                    thumbBmp.recycle();
                    break;
            }
        });
        shareWindow.showAtLocation(findViewById(R.id.ll_quality_contrast_detail_bottom), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @OnClick({R.id.tv_quality_contrast_detail_cart, R.id.tv_quality_contrast_detail_share})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_quality_contrast_detail_share:
                showShareWindow();
                break;
            case R.id.tv_quality_contrast_detail_cart:
                if(UserManager.getInstance().isLogin()) {
                    judgeStatus();
                }else{
                    isLogin();
                }
                break;
        }
    }

    private void judgeStatus() {
        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        StoreService api = ApiUtil.createDefaultApi(StoreService.class);
        ApiUtil.doDefaultApi(api.list(ph,"","","","","","","","","","","","","","","","","","","","","","","","","","1","100"), data -> {
            if(data == null || data.size() == 0){
                judgeFind();
            }else{
                Intent intent = new Intent(getActivity(), RedPaperCottonActivity.class);
                intent.putExtra("ph", ph);
                intent.putExtra("flag", 1);
                startActivity(intent);
            }
        }, viewControl);
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
            loginDialog.dismiss();
        });
        cancel_tv.setOnClickListener(v -> loginDialog.dismiss());
        loginDialog = new AlertDialog.Builder(getActivity()).setView(dialogView).create();
        loginDialog.show();
    }

    private void judgeFind() {
        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        QualityContrastDetailStatusService api = ApiUtil.createDefaultApi(QualityContrastDetailStatusService.class);
        ApiUtil.doDefaultApi(api.check("", ph),data -> {
            if(data.getSfbwz().equals("0")){
                Intent intent = createIntent(HelpMeFindActivity.class);
                intent.putExtra("ph", ph);
                startActivity(intent);
            }
            if(data.getSfbwz().equals("1")){
                goToFind(data);
            }
        },viewControl);
    }

    private void goToFind(QualityContrastDetailStatusModel data) {
        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        QualityContrastDetailStatusService api = ApiUtil.createDefaultApi(QualityContrastDetailStatusService.class);
        ApiUtil.doDefaultApi(api.findQuery(data.geteZSid()), data1 -> {
            //TODO 带data跳转到假对话页
            Intent errorChat = createIntent(NearbyDetailsErrorChatActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", data1);
            errorChat.putExtras(bundle);
            startActivity(errorChat);
        }, viewControl);
    }

    public class JavaScriptinterface {

        /**
         * 与js交互时用到的方法，在js里直接调用的
         */
        @JavascriptInterface
        public void mapCompany(String note_id) {
            //            Intent noteIntent = new Intent(WebActivity.this, NoteActivity.class);
            //            noteIntent.putExtra("noteId", note_id);
            //            noteIntent.putExtra("notetext", "");
            //            WebActivity.this.startActivity(noteIntent);
            showToast("map");
        }

        /**
         * 与js交互时用到的方法，在js里直接调用的
         */
        @JavascriptInterface
        public void telPhone(String telPhone) {
            Intent cooperationPhone = new Intent(Intent.ACTION_DIAL);
            cooperationPhone.setData(Uri.parse("tel:" + telPhone));
            startActivity(cooperationPhone);
        }

        /**
         * 与js交互时用到的方法，在js里直接调用的
         */
        @JavascriptInterface
        public void specialPhone(String specialPhone) {
            Intent cooperationPhone = new Intent(Intent.ACTION_DIAL);
            cooperationPhone.setData(Uri.parse("tel:" + specialPhone));
            startActivity(cooperationPhone);
        }
    }
}
