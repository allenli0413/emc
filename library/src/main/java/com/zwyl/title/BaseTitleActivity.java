package com.zwyl.title;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zwyl.BaseActivity;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 标题Activity
 * @date 2015/1/22 21:48
 */
public abstract class BaseTitleActivity<HeadBar extends BaseHeaderBar> extends BaseActivity {

    HeadBar mHeadBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


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

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater layoutInflater = getLayoutInflater();
        ViewGroup parent = getContentLayout();
        View child = layoutInflater.inflate(layoutResID, parent, false);
        parent.addView(child);
        super.setContentView((View) parent.getParent());
    }

    /**
     * 获取显示内容的父布局
     */
    private ViewGroup getContentLayout() {
        LinearLayout parent = new LinearLayout(this);
        parent.setOrientation(LinearLayout.VERTICAL);
        FrameLayout container = new FrameLayout(this);
        FrameLayout containerTitle = new FrameLayout(this);
        parent.addView(containerTitle, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        parent.addView(container, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mHeadBar = initHeadBar(containerTitle);
        return container;
    }

    @Override
    public void setContentView(View child) {
        ViewGroup parent = getContentLayout();
        parent.addView(child);
        super.setContentView((View) parent.getParent());
    }
}
