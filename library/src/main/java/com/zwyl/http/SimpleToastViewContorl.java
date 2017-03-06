package com.zwyl.http;

import android.app.Activity;
import android.app.Dialog;
import android.widget.TextView;
import android.widget.Toast;

import com.zwyl.http.Contorlable;
import com.zwyl.library.R;

import java.util.Map;

/**
 * @param <T>
 * @author renxiao@zhiwy.com
 * @version V1.0
 */
public class SimpleToastViewContorl<T> implements Contorlable<T> {

    Dialog waiteDialog;
    TextView messageText;

    private Activity mActivity;
    private String loadMessage;
    private String sucessMessage;
    private String exceptionMessage;

    boolean isShowToast = true;
    boolean isShowSucessMessageToast = true;

    public SimpleToastViewContorl(Activity mActivity, boolean isShowToast) {
        this(mActivity);
        this.isShowToast = isShowToast;
    }

    public  SimpleToastViewContorl(Activity mActivity) {
        this.mActivity = mActivity;
        loadMessage = mActivity.getString(R.string.default_loading_toast_message);
        sucessMessage = mActivity.getString(R.string.default_sucess_toast_message);
        exceptionMessage = mActivity.getString(R.string.default_exception_toast_message);

        waiteDialog = new Dialog(mActivity, R.style.selectorDialog);
        waiteDialog.setContentView(R.layout.my_progress_view);
        waiteDialog.setCanceledOnTouchOutside(false);
        messageText = (TextView) waiteDialog.findViewById(R.id.message);

    }


    /**
     * 设置等待对话框
     */
    public void setWaiteDialog(Dialog dialog) {
        waiteDialog = dialog;
    }


    public void setLoadMessage(String loadMessage) {
        this.loadMessage = loadMessage;
        messageText.setText(loadMessage);
    }

    public void hideSucessMessage(){
        this.isShowSucessMessageToast = false;
    }

    public void showSucessMessage(){
        this.isShowSucessMessageToast = true;
    }

    public void setSucessMessage(String sucessMessage) {
        this.sucessMessage = sucessMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String getTag() {
        return "default";
    }

    @Override
    public void onStart() {
        if (!waiteDialog.isShowing()) {
            waiteDialog.show();
        }

    }

    @Override
    public void onFinish() {
        waiteDialog.cancel();
    }

    @Override
    public void onSucess(Map<String, String> headers, T t) {
        if (isShowToast && isShowSucessMessageToast) {
            Toast.makeText(mActivity, sucessMessage, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSucessEmpty(Map<String, String> headers) {
        if (isShowToast && isShowSucessMessageToast) {
            Toast.makeText(mActivity, sucessMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(String message) {
        if (isShowToast) {
            Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onException(Throwable error) {
        if (isShowToast) {
            Toast.makeText(mActivity, exceptionMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
