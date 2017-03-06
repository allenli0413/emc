package com.zwyl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.litesuits.common.assist.Check;
import com.litesuits.common.assist.Toastor;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: Activity基类
 * @date 2015/1/22 21:45
 */
public class BaseActivity extends AutoLayoutActivity {

    private FrameLayout mParentView;//父布局

    Toastor toastor;

    void clearFragment() {
        List<Fragment> list = getSupportFragmentManager().getFragments();
        if (!Check.isEmpty(list)) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            for (Fragment fragment : list) {
                fragmentTransaction.remove(fragment);
            }
            fragmentTransaction.commitAllowingStateLoss();
        }
    }


    public void showToast(String text) {
        toastor.showToast(text);
    }

    public void showToast(int resid) {
        toastor.showToast(resid);
    }


    /**
     * 设置通用的背景
     */
    public void setBackgroundResource(int resid) {
        mParentView.setBackgroundResource(resid);
    }


    private boolean isClear = true;

    public void needClearFragment(boolean b) {
        this.isClear = b;
    }

    @Override
    public void setContentView(int layoutResID) {
        mParentView = new FrameLayout(this);
        View container = getLayoutInflater().inflate(layoutResID, mParentView, false);
        mParentView.addView(container);
        super.setContentView(mParentView);
        ButterKnife.inject(this);
        if (isClear)
            clearFragment();
    }

    @Override
    public void setContentView(View view) {
        mParentView = new FrameLayout(this);
        mParentView.addView(view);
        super.setContentView(mParentView);
        ButterKnife.inject(this);
        if (isClear)
            clearFragment();
    }

    public FrameLayout getParentView() {
        return mParentView;
    }

    public Activity getActivity() {
        return this;
    }

    /**
     * 快速创建Intent
     */
    public Intent createIntent(Class<? extends Activity> cls) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(getIntent());
        return intent;
    }


    /**
     * Get intent extra
     *
     * @param name
     * @return serializable
     */
    @SuppressWarnings("unchecked")
    protected <V extends Serializable> V getSerializableExtra(final String name) {
        return (V) getIntent().getSerializableExtra(name);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return serializable
     */
    @SuppressWarnings("unchecked")
    protected <V extends Parcelable> V getParcelableExtra(final String name) {
        return (V) getIntent().getParcelableExtra(name);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return int
     */
    protected int getIntExtra(final String name) {
        return getIntent().getIntExtra(name, -1);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return string
     */
    protected String getStringExtra(final String name) {
        return getIntent().getStringExtra(name);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return string array
     */
    protected String[] getStringArrayExtra(final String name) {
        return getIntent().getStringArrayExtra(name);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toastor = new Toastor(this);
        ActivityManager.getInstance().add(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().remove(this);
    }

//    public void smoonImage(View view, String uri) {
//        Intent intent = new Intent(this, SpaceImageDetailActivity.class);
//        intent.putExtra("uri", uri);
//        int[] location = new int[2];
//        view.getLocationOnScreen(location);
//        intent.putExtra("locationX", location[0]);
//        intent.putExtra("locationY", location[1]);
//        intent.putExtra("width", view.getWidth());
//        intent.putExtra("height", view.getHeight());
//
//        startActivity(intent);
//        overridePendingTransition(0, 0);
//    }

}
