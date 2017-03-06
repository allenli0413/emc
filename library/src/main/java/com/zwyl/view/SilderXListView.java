package com.zwyl.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.litesuits.android.log.Log;

public class SilderXListView extends SimpleXListView {


    private SliderView mFocusedItemView;

    float mX = 0;
    float mY = 0;
    private int mPosition = -1;
    boolean isSlider = false;

    public SilderXListView(Context context) {
        super(context);
    }

    public SilderXListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SilderXListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isSlider = false;
                mX = x;
                mY = y;
                int position = pointToPosition((int) x, (int) y);
//                if (mPosition != position) {
                    mPosition = position;
                    if (mFocusedItemView != null) {
                        mFocusedItemView.reset();
                    }
//                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mPosition != -1) {
                    if (Math.abs(mY - y) < 30 && Math.abs(mX - x) > 20) {
                        int first = this.getFirstVisiblePosition();
                        int index = mPosition - first;
                        try {
                            mFocusedItemView = (SliderView) getChildAt(index);
                            mFocusedItemView.onTouchEvent(event);
                            isSlider = true;
                        }catch (Exception ignored){
                        }
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isSlider) {
                    isSlider = false;
                    if (mFocusedItemView != null) {
                        mFocusedItemView.adjust(mX - x > 0);
                        mFocusedItemView.setPressed(false);
                        return true;
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}