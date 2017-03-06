package com.emiancang.emiancang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by yuanyueqing on 2016/11/4.
 */

public class InnerScrollListview extends ListView {
    public InnerScrollListview(Context context) {
        super(context);
    }

    public InnerScrollListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InnerScrollListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
