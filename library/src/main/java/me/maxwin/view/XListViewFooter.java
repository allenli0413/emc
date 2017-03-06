/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package me.maxwin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;


public abstract class XListViewFooter extends LinearLayout {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_LOADING = 2;

    private View mContentView;

    public XListViewFooter(Context context) {
        super(context);
        initView();
    }

    public XListViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    final void setState(int state) {
        updateState(state);
        switch (state) {
            case STATE_NORMAL:
                onNormal();
                break;
            case STATE_READY:
                onReady();
                break;
            case STATE_LOADING:
                onLoading();
                break;
            default:
        }
    }

    /**
     * 正常状态
     */
    protected abstract void onNormal();

    /**
     * 准备加载
     */
    protected abstract void onReady();

    /**
     * 加载中
     */
    protected abstract void onLoading();


    /**
     * @param state STATE_NORMAL, STATE_READY, STATE_LOADING
     */
    protected void updateState(int state) {

    }


    final void setBottomMargin(int height) {
        if (height < 0) return;
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.bottomMargin = height;
        mContentView.setLayoutParams(lp);
    }

    final int getBottomMargin() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        return lp.bottomMargin;
    }


//    /**
//     * normal status
//     */
//    public void normal() {
//        mHintView.setVisibility(View.VISIBLE);
//        mProgressBar.setVisibility(View.GONE);
//    }


    /**
     * hide footer when disable pull load more
     */
    final void hide() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.height = 0;
        mContentView.setLayoutParams(lp);
    }

    /**
     * show footer
     */
    final void show() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.height = LayoutParams.WRAP_CONTENT;
        mContentView.setLayoutParams(lp);
    }

    private void initView() {
        mContentView = getContentView();
        addView(mContentView);
        mContentView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

    }

    /**
     * 获取自定义布局信息
     */
    protected abstract View getContentView();


}
