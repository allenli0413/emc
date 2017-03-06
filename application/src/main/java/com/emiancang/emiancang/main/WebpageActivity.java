package com.emiancang.emiancang.main;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.main.home.model.BannerModel;
import com.emiancang.emiancang.uitl.Util;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yuanyueqing on 2016/12/26.
 */

public class WebpageActivity extends TitleActivity {

    @InjectView(R.id.wv_webpage)
    WebView wv_webpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage);
        ButterKnife.inject(this);
        BannerModel data = (BannerModel) getIntent().getSerializableExtra("data");
        initHead(data);
        initView(data);
    }

    private void initView(BannerModel data) {
        wv_webpage.loadUrl(data.getEzAurl());
        wv_webpage.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv_webpage.setWebChromeClient(new WebChromeClient());
        wv_webpage.getSettings().setJavaScriptEnabled(true);
        wv_webpage.getSettings().setAllowFileAccess(true);// 设置允许访问文件数据
        wv_webpage.getSettings().setSupportZoom(true);
        wv_webpage.getSettings().setBuiltInZoomControls(true);
        wv_webpage.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv_webpage.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wv_webpage.getSettings().setDomStorageEnabled(true);
        wv_webpage.getSettings().setDatabaseEnabled(true);
    }

    private void initHead(BannerModel data) {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle(data.getEzAtitle(), getResources().getColor(R.color.white));
        getHeadBar().showLeftImage();
        getHeadBar().setLeftOnClickListner(v -> finish());
    }
}
