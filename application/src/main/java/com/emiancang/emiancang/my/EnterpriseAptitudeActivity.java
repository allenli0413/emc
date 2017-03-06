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

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.main.fragment.MainHomeFragment;
import com.emiancang.emiancang.main.fragment.MainNearbyFragment;
import com.emiancang.emiancang.main.fragment.MainScanFragment;
import com.emiancang.emiancang.main.fragment.MainStoreFragment;
import com.emiancang.emiancang.main.fragment.MainUserInforFragment;
import com.emiancang.emiancang.my.fragment.AptitudeCombinationFragment;
import com.emiancang.emiancang.my.fragment.AptitudeCommonFragment;
import com.emiancang.emiancang.uitl.Util;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static android.R.id.list;

/**
 * Created by 22310 on 2016/11/8.
 */

public class EnterpriseAptitudeActivity extends TitleActivity {

    @InjectView(R.id.combination_title)
    TextView combinationTitle;
    @InjectView(R.id.combination_select)
    ImageView combinationSelect;
    @InjectView(R.id.combination)
    LinearLayout combination;
    @InjectView(R.id.common_title)
    TextView commonTitle;
    @InjectView(R.id.common_select)
    ImageView commonSelect;
    @InjectView(R.id.common)
    LinearLayout common;
    @InjectView(R.id.viewpager)
    FrameLayout viewpager;


    AptitudeCombinationFragment aptitudeCombinationFragment;
    AptitudeCommonFragment aptitudeCommonFragment;
    String mCustId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_enterprise_aptitude, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("企业资质", getResources().getColor(R.color.white));
    }

    private void initView() {
        mCustId = getIntent().getStringExtra("custId");
        combinationTitle.setTextColor(getResources().getColor(R.color.green));
        combinationSelect.setBackgroundColor(getResources().getColor(R.color.green));
        aptitudeCombinationFragment = new AptitudeCombinationFragment();
        aptitudeCombinationFragment.setCustId(mCustId);
        aptitudeCommonFragment = new AptitudeCommonFragment();
        aptitudeCommonFragment.setCustId(mCustId);
        setDefaultFragment(aptitudeCombinationFragment);
    }

    private void setDefaultFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.viewpager, fragment);
        transaction.commit();
    }

    @OnClick({R.id.combination, R.id.common})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.combination:
                combinationTitle.setTextColor(getResources().getColor(R.color.green));
                combinationSelect.setBackgroundColor(getResources().getColor(R.color.green));
                commonTitle.setTextColor(getResources().getColor(R.color.black));
                commonSelect.setBackgroundColor(getResources().getColor(R.color.white));
                setDefaultFragment(aptitudeCombinationFragment);
                break;
            case R.id.common:
                combinationTitle.setTextColor(getResources().getColor(R.color.black));
                combinationSelect.setBackgroundColor(getResources().getColor(R.color.white));
                commonTitle.setTextColor(getResources().getColor(R.color.green));
                commonSelect.setBackgroundColor(getResources().getColor(R.color.green));
                setDefaultFragment(aptitudeCommonFragment);
                break;
        }
    }
}
