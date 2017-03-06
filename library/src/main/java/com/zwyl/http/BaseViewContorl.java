package com.zwyl.http;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Map;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 控制刷新界面
 * @date 2015/1/31 17:24
 */
public class BaseViewContorl<T> implements Contorlable<T> {


    private FrameLayout mParent;
    private View mContentView;//正常显示数据的界面
    protected View mEmptyView;//返回数据为空时显示的界面
    protected View mExceptionView;//数据异常显示的界面
    protected View mLoadView;//加载数据的界面...

    private boolean isContorl = true;//是否控制界面变化

    private int lockNumber;//解锁次数，设置后，数据成功返回多次才会控制界面显示
    private int recordedLockNumber;//记录解锁数据

    public void resetlockNumber() {
        recordedLockNumber = 0;
    }

    /**
     * 设置需要解锁的次数
     *
     * @param number 解锁次数
     */
    public void setLockNumber(int number) {
        lockNumber = number;
    }

    public void setContorl(boolean contorl) {
        isContorl = contorl;
    }

    public LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mParent.getContext());
    }

    public FrameLayout getParent() {
        return mParent;
    }

    public BaseViewContorl(FrameLayout parent, View contentView) {
        this.mParent = parent;
        this.mContentView = contentView;
        setLoadingView(new View(mParent.getContext()));
        setEmptyView(new View(mParent.getContext()));
        setExceptionView(new View(mParent.getContext()));

    }

    /**
     * 设置加载数据的布局
     *
     * @param view 自定义布局
     */
    public void setLoadingView(View view) {
        addView(mLoadView, view);
        mLoadView = view;
    }

    /**
     * 设置加载数据的布局
     *
     * @param view 自定义布局
     */
    public void setEmptyView(View view) {
        addView(mEmptyView, view);
        mEmptyView = view;
    }

    /**
     * 设置加载数据的布局
     *
     * @param view 自定义布局
     */
    public void setExceptionView(View view) {
        addView(mExceptionView, view);
        mExceptionView = view;
    }


    /**
     * 添加到父布局
     */
    public void addParent(View view) {
        mParent.addView(view);
        view.setVisibility(View.GONE);
    }

    /**
     * 添加View
     */
    private void addView(View oldView, View view) {
        if (oldView != null) {
            mParent.removeView(oldView);
        }
        addParent(view);
    }


    @Override
    public String getTag() {
        return "default";
    }

    @Override
    public void onStart() {
        if (isContorl) {
            mLoadView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
            mExceptionView.setVisibility(View.GONE);
            mContentView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onSucess(Map<String, String> headers, T t) {
        if (lockNumber != 0 && lockNumber != recordedLockNumber) {
            recordedLockNumber++;
        }
        if (lockNumber == recordedLockNumber) {
            mLoadView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.GONE);
            mExceptionView.setVisibility(View.GONE);
            mContentView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSucessEmpty(Map<String, String> headers) {
        if (isContorl) {
            mLoadView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
            mExceptionView.setVisibility(View.GONE);
            mContentView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure(String message) {
        if (isContorl) {
            mLoadView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.GONE);
            mExceptionView.setVisibility(View.GONE);
            mContentView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onException(Throwable error) {
        if (isContorl) {
            mLoadView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.GONE);
            mExceptionView.setVisibility(View.VISIBLE);
            mContentView.setVisibility(View.GONE);
        }
    }

}
