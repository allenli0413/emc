package com.emiancang.emiancang.my;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwyl.BaseActivity;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.my.fragment.CollectCottonEnterpriseFragment;
import com.emiancang.emiancang.my.fragment.CollectCottonFragment;
import com.emiancang.emiancang.my.fragment.IndentMarketFragment;
import com.emiancang.emiancang.my.fragment.IndentPurchaseFragment;
import com.emiancang.emiancang.uitl.Util;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 22310 on 2016/11/10.
 */

public class MyIndentManage extends BaseActivity {

    @InjectView(R.id.left_image)
    ImageView leftImage;
    @InjectView(R.id.indent_purchase)
    TextView indentPurchase;
    @InjectView(R.id.indent_market)
    TextView indentMarket;

    IndentPurchaseFragment mIndentPurchaseFragment;
    IndentMarketFragment mIndentMarketFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_indent, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
    }

    private void initView() {
        int type = getIntent().getIntExtra("type",0);
        mIndentPurchaseFragment = IndentPurchaseFragment.newInstance(type);
        mIndentMarketFragment = new IndentMarketFragment();
        setDefaultFragment(mIndentPurchaseFragment);
    }

    private void setDefaultFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.viewpager, fragment);
        transaction.commit();
    }


    @OnClick({R.id.left_image, R.id.indent_purchase, R.id.indent_market})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_image:
                finish();
                break;
            case R.id.indent_purchase:
                //采购订单
                indentPurchase.setBackground(getResources().getDrawable(R.drawable.background_collect_btn_select));
                indentPurchase.setTextColor(getResources().getColor(R.color.green));
                indentMarket.setBackground(getResources().getDrawable(R.drawable.background_collect_btn_not_select_1));
                indentMarket.setTextColor(getResources().getColor(R.color.white));
                setDefaultFragment(mIndentPurchaseFragment);
                break;
            case R.id.indent_market:
                //销售订单
                indentMarket.setBackground(getResources().getDrawable(R.drawable.background_collect_btn_select_1));
                indentMarket.setTextColor(getResources().getColor(R.color.green));
                indentPurchase.setBackground(getResources().getDrawable(R.drawable.background_collect_btn_not_select));
                indentPurchase.setTextColor(getResources().getColor(R.color.white));
                setDefaultFragment(mIndentMarketFragment);
                break;
        }
    }
}
