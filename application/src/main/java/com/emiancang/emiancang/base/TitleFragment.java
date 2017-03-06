package com.emiancang.emiancang.base;


import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.emiancang.emiancang.R;
import com.umeng.analytics.MobclickAgent;
import com.zwyl.title.BaseTitleFragment;

/**
 * 带有标题的fragment
 */
public abstract class TitleFragment extends BaseTitleFragment<TitleHeaderBar> {


    @Override
    public TitleHeaderBar initHeadBar(ViewGroup parent) {

        return new TitleHeaderBar(parent,getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("SplashScreen"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(getActivity());          //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("SplashScreen"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(getActivity());
    }
}
