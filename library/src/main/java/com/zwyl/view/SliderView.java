package com.zwyl.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.zwyl.library.R;


public class SliderView extends LinearLayout {
    private static final int TAN = 2;
    private int mHolderWidth = 130;
    private float mLastX = 0;
    private float mLastY = 0;
    private Context mContext;
    private LinearLayout mViewContent;
    private Scroller mScroller;


    boolean isScrool = true;

    public void setScrool(boolean isScrool){
        this.isScrool = isScrool;
        if(!isScrool){
            reset();
        }
    }

    public SliderView(Context context, int resourcesId) {
        super(context);
        initView(resourcesId);
    }

    public SliderView(Context context) {
        super(context);
        initView(R.layout.slide_view);
    }

    public SliderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(R.layout.slide_view);
    }

    private void initView(int resourcesId) {
        setOrientation(LinearLayout.HORIZONTAL);
        mContext = getContext();
        mScroller = new Scroller(mContext);
        View.inflate(mContext, resourcesId, this);
        mViewContent = (LinearLayout) findViewById(R.id.view_content);
        mHolderWidth = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX, mHolderWidth, getResources()
                        .getDisplayMetrics()));
    }

    public void setContentView(View view) {
        mViewContent.addView(view);
    }

    public void shrink() {
        int offset = getScrollX();
        if (offset == 0) {
            return;
        }
        scrollTo(0, 0);
    }

    public void reset() {
        int offset = getScrollX();
        if (offset == 0) {
            return;
        }
        smoothScrollTo(0, 0);
    }

    public void adjust(boolean left) {
        int offset = getScrollX();
        if (offset == 0) {
            return;
        }
        if (offset < 20) {
            this.smoothScrollTo(0, 0);
        } else if (offset < mHolderWidth - 20) {
            if (left) {
                this.smoothScrollTo(mHolderWidth, 0);
            } else {
                this.smoothScrollTo(0, 0);
            }
        } else {
            this.smoothScrollTo(mHolderWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isScrool){
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    float x = event.getX();
                    float y = event.getY();
                    float deltaX = x - mLastX;
                    float delatY = y - mLastY;
                    mLastX = x;
                    mLastY = y;
                    if (Math.abs(deltaX) < Math.abs(delatY) * TAN) {
                        break;
                    }
                    if (deltaX != 0) {
                        float newScrollX = getScrollX() - deltaX;
                        if (newScrollX < 0) {
                            newScrollX = 0;
                        } else if (newScrollX > mHolderWidth) {
                            newScrollX = mHolderWidth;
                        }
                        this.scrollTo((int) newScrollX, 0);
                    }
                    break;
                case MotionEvent.ACTION_DOWN:

                    return false;
//                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    private void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        mScroller.startScroll(scrollX, 0, delta, 0, Math.abs(delta) * 3);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
