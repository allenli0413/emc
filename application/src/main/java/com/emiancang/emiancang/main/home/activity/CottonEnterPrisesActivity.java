package com.emiancang.emiancang.main.home.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.emiancang.emiancang.R;
import com.zwyl.title.BaseHeaderBar;
import com.zwyl.title.BaseTitleActivity;

/**
 * Created by yuanyueqing on 2016/11/4.
 */

public class CottonEnterPrisesActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotton_enterprises);
    }

    @Override
    public BaseHeaderBar initHeadBar(ViewGroup parent) {
        return null;
    }
}
