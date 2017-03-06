/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package com.zwyl.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwyl.library.R;


public class SimpleXListViewFooter extends me.maxwin.view.XListViewFooter {

    private View mProgressBar;
    private TextView mHintView;

    public SimpleXListViewFooter(Context context) {
        super(context);
    }

    public SimpleXListViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void updateState(int state) {
        mHintView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mHintView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onNormal() {
        mHintView.setVisibility(View.VISIBLE);
        mHintView.setText(R.string.xlistview_footer_hint_normal);
    }

    @Override
    protected void onReady() {
        mHintView.setVisibility(View.VISIBLE);
        mHintView.setText(R.string.xlistview_footer_hint_ready);
    }

    @Override
    protected void onLoading() {
        mProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    protected View getContentView() {
        LinearLayout moreView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.xlistview_footer, null);
        mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar);
        mHintView = (TextView) moreView.findViewById(R.id.xlistview_footer_hint_textview);
        return moreView;
    }


}
