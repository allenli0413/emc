package com.emiancang.emiancang.main.home.qualityquery.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yuanyueqing on 2016/11/12.
 */

public class QualityContrastDetailEnterpriseInfoFragment extends TitleFragment {

    @InjectView(R.id.wv_fragment_quality_contrast_detail)
    WebView wv_fragment_quality_contrast_detail;

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_quality_contrast_detail, null);
        ButterKnife.inject(this, view);
        getHeadBar().hideHeader();
        initView();
        return view;
    }

    private void initView() {
        wv_fragment_quality_contrast_detail.getSettings().setJavaScriptEnabled(true);
        wv_fragment_quality_contrast_detail.loadUrl("http://www.baidu.com");
        wv_fragment_quality_contrast_detail.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

}
