package com.emiancang.emiancang.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by wuxu on 16/1/21.
 */
public class BaseDialog extends Dialog {


    public BaseDialog(Context context) {
        super(context);
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = View.inflate(getContext(),layoutResID,null);
        setContentView(view);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        AutoUtils.autoSize(view);
    }
}
