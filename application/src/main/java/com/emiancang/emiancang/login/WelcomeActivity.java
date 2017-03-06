package com.emiancang.emiancang.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.igexin.sdk.PushManager;
import com.emiancang.emiancang.update.CommonUpdateService;
import com.zwyl.title.BaseHeaderBar;
import com.zwyl.title.BaseTitleActivity;

/**
 * Created by 22310 on 2016/4/8.
 */
public class WelcomeActivity extends BaseTitleActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent rongService = new Intent();
        rongService.setClass(this, CommonUpdateService.class);
        startService(rongService);

    }

    @Override
    public BaseHeaderBar initHeadBar(ViewGroup parent) {
        return null;
    }
}
