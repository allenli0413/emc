package com.zwyl.view;

import android.content.Context;
import android.util.AttributeSet;

import me.maxwin.view.XListView;
import me.maxwin.view.XListViewFooter;
import me.maxwin.view.XListViewHeader;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 简单的XlistView下拉刷新
 * @date 2015/2/2 17:38
 */
public class SimpleXListView extends XListView {
    public SimpleXListView(Context context) {
        super(context);
        setPullLoadEnable(false);
    }

    public SimpleXListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPullLoadEnable(false);
    }

    public SimpleXListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setPullLoadEnable(false);
    }

    @Override
    public XListViewHeader createHeader(Context context) {
        return new SimpleXListViewHeader(context);
    }

    @Override
    public XListViewFooter createFooter(Context context) {
        return new SimpleXListViewFooter(context);
    }

    public void onFinish(){
        stopRefresh();
        stopLoadMore();
    }
}
