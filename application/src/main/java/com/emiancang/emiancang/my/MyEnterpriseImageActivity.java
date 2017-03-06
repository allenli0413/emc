package com.emiancang.emiancang.my;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.uitl.Util;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 22310 on 2016/11/7.
 */

public class MyEnterpriseImageActivity extends TitleActivity {


    @InjectView(R.id.my_user_avater)
    SimpleDraweeView myUserAvater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_enterprise_image, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));

    }

    private void initView() {
        String title = getIntent().getStringExtra("title");
        String iamge = getIntent().getStringExtra("iamge");
        getHeadBar().setCenterTitle(title, getResources().getColor(R.color.white));
        myUserAvater.setImageURI(Uri.parse(iamge));
    }

}
