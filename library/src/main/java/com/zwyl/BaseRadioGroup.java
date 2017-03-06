package com.zwyl;

import android.view.View;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 支持单选View
 * @date 2014/12/27 11:41
 */
public class BaseRadioGroup implements View.OnClickListener {

    public View mSelectView;
    private View.OnClickListener mListener;

    public View getCurrentView() {
        return mSelectView;
    }

    public void setListener(View.OnClickListener l) {
        mListener = l;
    }

    @Override
    public void onClick(View v) {
        if (!selectView(v)) {
            if (mListener != null) {
                mListener.onClick(v);
            }
        }
    }

    public boolean selectView(View view) {
        if (view == mSelectView) {
            return true;
        }
        if (mSelectView != null) {
            mSelectView.setSelected(false);
        }
        view.setSelected(true);
        mSelectView = view;
        return false;
    }


}
