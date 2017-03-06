/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */
package me.maxwin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;


public abstract class XListViewHeader extends LinearLayout {
    private View mContainer;
    private int mState = STATE_NORMAL;
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;

    public XListViewHeader(Context context) {
        super(context);
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public XListViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        // 初始情况，设置下拉刷新view高度为0
        LayoutParams lp = new LayoutParams(
                LayoutParams.FILL_PARENT, 0);
        mContainer = getContentView();
        addView(mContainer, lp);
        setGravity(Gravity.BOTTOM);

    }

    final void setState(int state) {
        if (state == mState) return ;
        updateState(state);
        switch (state) {
            case STATE_NORMAL:
                onNormal();
                break;
            case STATE_READY:
                onReady();
                break;
            case STATE_REFRESHING:
                onRefreshing();
                break;
            default:
        }

        mState = state;
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
    protected abstract void onRefreshing();


    /**
     * @param state STATE_NORMAL, STATE_READY, STATE_LOADING
     */
    protected void updateState(int state) {

    }

    final void setVisiableHeight(int height) {
        if (height < 0)
            height = 0;
        LayoutParams lp = (LayoutParams) mContainer
                .getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    final int getVisiableHeight() {
        return mContainer.getHeight();
    }


    /**
     * 获取自定义布局信息
     */
    protected abstract View getContentView();


}
