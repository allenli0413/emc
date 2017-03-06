/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */
package com.zwyl.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.zwyl.library.R;

import java.text.SimpleDateFormat;


public class SimpleXListViewHeader extends me.maxwin.view.XListViewHeader {
    private ImageView mArrowImageView;
    private ProgressBar mProgressBar;
    private TextView mHintTextView;
    private TextView xlistview_header_time;
    private int mState = STATE_NORMAL;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;

    private final int ROTATE_ANIM_DURATION = 180;

    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private static long refresh_time = System.currentTimeMillis();

    public SimpleXListViewHeader(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public SimpleXListViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void updateState(int state) {
        if (state == STATE_REFRESHING) {    // 显示进度
            mArrowImageView.clearAnimation();
            mArrowImageView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {    // 显示箭头图片
            mArrowImageView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onNormal() {
        xlistview_header_time.setText(dateFormat.format(refresh_time));
        if (mState == STATE_READY) {
            mArrowImageView.startAnimation(mRotateDownAnim);
        }
        if (mState == STATE_REFRESHING) {
            mArrowImageView.clearAnimation();
        }
        mHintTextView.setText(R.string.xlistview_header_hint_normal);

    }

    @Override
    protected void onReady() {
        if (mState != STATE_READY) {
            mArrowImageView.clearAnimation();
            mArrowImageView.startAnimation(mRotateUpAnim);
            mHintTextView.setText(R.string.xlistview_header_hint_ready);
        }
    }

    @Override
    protected void onRefreshing() {
        refresh_time = System.currentTimeMillis();
        mHintTextView.setText(R.string.xlistview_header_hint_loading);

    }

    @Override
    protected View getContentView() {
        View container = LayoutInflater.from(getContext()).inflate(
                R.layout.xlistview_header, null);
        mArrowImageView = (ImageView) container.findViewById(R.id.xlistview_header_arrow);
        mHintTextView = (TextView) container.findViewById(R.id.xlistview_header_hint_textview);
        mProgressBar = (ProgressBar) container.findViewById(R.id.xlistview_header_progressbar);
        xlistview_header_time = (TextView) container.findViewById(R.id.xlistview_header_time);
        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
        xlistview_header_time.setText(dateFormat.format(refresh_time));
        return container;
    }


}
