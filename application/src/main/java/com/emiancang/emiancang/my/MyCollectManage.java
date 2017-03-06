package com.emiancang.emiancang.my;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwyl.BaseActivity;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.my.fragment.CollectCottonEnterpriseFragment;
import com.emiancang.emiancang.my.fragment.CollectCottonFragment;
import com.emiancang.emiancang.uitl.Util;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 22310 on 2016/11/10.
 */

public class MyCollectManage extends BaseActivity {

    @InjectView(R.id.left_image)
    ImageView leftImage;
    @InjectView(R.id.collect_cotton)
    TextView collectCotton;
    @InjectView(R.id.collect_enterprise)
    TextView collectEnterprise;
    @InjectView(R.id.collect_layout)
    LinearLayout collectLayout;
    @InjectView(R.id.viewpager)
    FrameLayout viewpager;

    CollectCottonFragment mCollectCottonFragment;
    CollectCottonEnterpriseFragment mCollectCottonEnterpriseFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_collect_manage, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
    }

    private void initView() {
        mCollectCottonFragment = new CollectCottonFragment();
        mCollectCottonEnterpriseFragment = new CollectCottonEnterpriseFragment();
        setDefaultFragment(mCollectCottonFragment);
    }

    private void setDefaultFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.viewpager, fragment);
        transaction.commit();
    }


    @OnClick({R.id.left_image, R.id.collect_cotton, R.id.collect_enterprise})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_image:
                //返回
                finish();
                break;
            case R.id.collect_cotton:
                //棉花
                collectCotton.setBackground(getResources().getDrawable(R.drawable.background_collect_btn_select));
                collectCotton.setTextColor(getResources().getColor(R.color.green));
                collectEnterprise.setBackground(getResources().getDrawable(R.drawable.background_collect_btn_not_select_1));
                collectEnterprise.setTextColor(getResources().getColor(R.color.white));
                setDefaultFragment(mCollectCottonFragment);
                break;
            case R.id.collect_enterprise:
                //棉花企业
                collectEnterprise.setBackground(getResources().getDrawable(R.drawable.background_collect_btn_select_1));
                collectEnterprise.setTextColor(getResources().getColor(R.color.green));
                collectCotton.setBackground(getResources().getDrawable(R.drawable.background_collect_btn_not_select));
                collectCotton.setTextColor(getResources().getColor(R.color.white));
                setDefaultFragment(mCollectCottonEnterpriseFragment);
                break;
        }
    }
}
