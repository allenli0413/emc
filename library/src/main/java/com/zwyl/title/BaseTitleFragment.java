package com.zwyl.title;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zwyl.BaseFragment;

import butterknife.ButterKnife;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 基础baseTitleFragment
 * @date 2015/2/3 10:45
 */
public abstract class BaseTitleFragment<HeadBar extends BaseHeaderBar> extends BaseFragment {
    HeadBar mHeadBar;

    /**
     * 得到HeadBar
     */
    public HeadBar getHeadBar() {
        return mHeadBar;
    }

    /**
     * 初始化HeadBar
     */
    public abstract HeadBar initHeadBar(ViewGroup parent);

    public BaseTitleFragment() {
    }

    /**
     * 获取显示内容的父布局
     */
    private ViewGroup getContentLayout(Context context) {
        LinearLayout parent = new LinearLayout(context);
        parent.setOrientation(LinearLayout.VERTICAL);
        FrameLayout container = new FrameLayout(context);
        FrameLayout containerTitle = new FrameLayout(context);
        parent.addView(containerTitle, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        parent.addView(container, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mHeadBar = initHeadBar(containerTitle);
        return container;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup parent = getContentLayout(container.getContext());
        View contentView = onCreateContentView(inflater, parent, savedInstanceState);
        ButterKnife.inject(this, contentView);
        parent.addView(contentView);
        return (View) parent.getParent();
    }

    public void showToast(){

    }

    public abstract View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}
