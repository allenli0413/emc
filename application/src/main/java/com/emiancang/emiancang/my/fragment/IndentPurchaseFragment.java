package com.emiancang.emiancang.my.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 *  采购订单
 * Created by 22310 on 2016/11/10.
 */

public class IndentPurchaseFragment extends TitleFragment {

    @InjectView(R.id.indent_all)
    TextView indentAll;
    @InjectView(R.id.indent_all_select)
    ImageView indentAllSelect;
    @InjectView(R.id.indent_all_layout)
    LinearLayout indentAllLayout;
    @InjectView(R.id.indent_payment)
    TextView indentPayment;
    @InjectView(R.id.indent_payment_select)
    ImageView indentPaymentSelect;
    @InjectView(R.id.indent_payment_layout)
    LinearLayout indentPaymentLayout;
    @InjectView(R.id.indent_delivery)
    TextView indentDelivery;
    @InjectView(R.id.indent_delivery_select)
    ImageView indentDeliverySelect;
    @InjectView(R.id.indent_delivery_layout)
    LinearLayout indentDeliveryLayout;
    @InjectView(R.id.indent_accomplish)
    TextView indentAccomplish;
    @InjectView(R.id.indent_accomplish_select)
    ImageView indentAccomplishSelect;
    @InjectView(R.id.indent_accomplish_layout)
    LinearLayout indentAccomplishLayout;
    @InjectView(R.id.indent_violate)
    TextView indentViolate;
    @InjectView(R.id.indent_violate_select)
    ImageView indentViolateSelect;
    @InjectView(R.id.indent_violate_layout)
    LinearLayout indentViolateLayout;
    @InjectView(R.id.indent_viewpager)
    FrameLayout indentViewpager;

    @InjectView(R.id.indent_timeout)
    TextView indentTimeout;
    @InjectView(R.id.indent_timeout_select)
    ImageView indentTimeoutSelect;
    @InjectView(R.id.indent_timeout_layout)
    LinearLayout indentTimeoutLayout;

    private ImageView mPresentImage;
    private TextView mPresentText;

    private IndentItemFragment mIndentAll;
    private IndentItemFragment mIndentPayment;
    private IndentItemFragment mIndentDelivery;
    private IndentItemFragment mIndentAccomplish;
    private IndentItemFragment mIndentViolate;
    private IndentItemFragment mIndentTimeout;

    private int type;

    public IndentPurchaseFragment() {
    }

//    public IndentPurchaseFragment(int type) {
//        this.type = type;
//    }

    public static IndentPurchaseFragment newInstance(int type){
        IndentPurchaseFragment fragment = new IndentPurchaseFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        this.type = bundle.getInt("type");
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_indent_purchase, null);
        ButterKnife.inject(this, view);
        getHeadBar().hideHeader();
        initView();
        return view;
    }

    private void initView() {
        mIndentAll = IndentItemFragment.newInstance("00");
        mIndentPayment = IndentItemFragment.newInstance("01");
        mIndentDelivery = IndentItemFragment.newInstance("02");
        mIndentAccomplish = IndentItemFragment.newInstance("03");
        mIndentViolate = IndentItemFragment.newInstance("04");
        mIndentTimeout = IndentItemFragment.newInstance("05");

        indentAll.setTextColor(getResources().getColor(R.color.green));
        indentAllSelect.setVisibility(View.VISIBLE);
        mPresentText = indentAll;
        mPresentImage = indentAllSelect;
        switch (type) {
            case 0:
                setDefaultFragment(mIndentAll);
                refreshUI(indentAll, indentAllSelect);
                break;
            case 1:
                setDefaultFragment(mIndentPayment);
                refreshUI(indentPayment, indentPaymentSelect);
                break;
            case 2:
                setDefaultFragment(mIndentDelivery);
                refreshUI(indentDelivery, indentDeliverySelect);
                break;
            case 3:
                setDefaultFragment(mIndentAccomplish);
                refreshUI(indentAccomplish, indentAccomplishSelect);
                break;
            case 4:
                setDefaultFragment(mIndentViolate);
                refreshUI(indentViolate, indentViolateSelect);
                break;
            default:
                setDefaultFragment(mIndentAll);
                refreshUI(indentAll, indentAllSelect);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.indent_all_layout, R.id.indent_payment_layout, R.id.indent_delivery_layout, R.id.indent_accomplish_layout, R.id.indent_violate_layout,R.id.indent_timeout_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.indent_all_layout:
                //全部
                refreshUI(indentAll, indentAllSelect);
                setDefaultFragment(mIndentAll);
                break;
            case R.id.indent_payment_layout:
                //待付款
                refreshUI(indentPayment, indentPaymentSelect);
                setDefaultFragment(mIndentPayment);
                break;
            case R.id.indent_delivery_layout:
                //等收货
                refreshUI(indentDelivery, indentDeliverySelect);
                setDefaultFragment(mIndentDelivery);
                break;
            case R.id.indent_accomplish_layout:
                //已完成
                refreshUI(indentAccomplish, indentAccomplishSelect);
                setDefaultFragment(mIndentAccomplish);
                break;
            case R.id.indent_violate_layout:
                //已违约
                refreshUI(indentViolate, indentViolateSelect);
                setDefaultFragment(mIndentViolate);
                break;
            case R.id.indent_timeout_layout:
                //超时未付款
                refreshUI(indentTimeout, indentTimeoutSelect);
                setDefaultFragment(mIndentTimeout);
                break;
        }
    }

    public void refreshUI(TextView textView, ImageView imageView) {
        if (mPresentImage != null)
            mPresentImage.setVisibility(View.GONE);
        if (mPresentText != null)
            mPresentText.setTextColor(getResources().getColor(R.color.c4));

        textView.setTextColor(getResources().getColor(R.color.green));
        imageView.setVisibility(View.VISIBLE);

        mPresentImage = imageView;
        mPresentText = textView;
    }

    private void setDefaultFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.indent_viewpager, fragment);
        transaction.commit();
    }
}
