package com.emiancang.emiancang.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.view.Window;

import com.umeng.analytics.MobclickAgent;
import com.zwyl.title.BaseTitleActivity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class TitleActivity extends com.zwyl.title.BaseTitleActivity<TitleHeaderBar> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }
    @Override
    public TitleHeaderBar initHeadBar(ViewGroup parent) {
        return new TitleHeaderBar(parent, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
