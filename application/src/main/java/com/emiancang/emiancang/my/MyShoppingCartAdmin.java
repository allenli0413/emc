package com.emiancang.emiancang.my;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.litesuits.android.log.Log;
import com.mayigeek.frame.http.state.HttpError;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.my.fragment.ShoppingCartAdminFragment;
import com.emiancang.emiancang.uitl.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;

import static com.emiancang.emiancang.R.id.listView;

/**
 * Created by 22310 on 2016/11/11.
 */

public class MyShoppingCartAdmin extends TitleActivity {

    @InjectView(R.id.viewpager)
    FrameLayout viewpager;

    ShoppingCartAdminFragment mShoppingCartAdminFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_shopping_cart_admin, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("购物车管理", getResources().getColor(R.color.white));
    }

    private void setDefaultFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.viewpager, fragment);
        transaction.commit();
    }


    private void initView() {
        mShoppingCartAdminFragment = new ShoppingCartAdminFragment();
        setDefaultFragment(mShoppingCartAdminFragment);
    }
}
