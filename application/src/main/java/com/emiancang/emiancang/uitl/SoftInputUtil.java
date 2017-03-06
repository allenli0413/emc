package com.emiancang.emiancang.uitl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

/**
 * @author weixy@zhiwy.com
 * @version V1.0
 * @Description: com.emiancang.emiancang.uitl
 * @date 2016/5/13 17:43
 */
public class SoftInputUtil {

    private static int rootViewVisibleHeight;//纪录根视图的显示高度
    private static int inputHeight;
    private static View rootView;

    public static void getSoftInputHeight(final Activity activity) {
        rootView = activity.getWindow().getDecorView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            /**
             * the result is pixels
             */
            @Override
            public void onGlobalLayout() {

//                Rect r = new Rect();
//                rootView.getWindowVisibleDisplayFrame(r);
//
//                int screenHeight = rootView.getRootView().getHeight();
//                int heightDifference = screenHeight - (r.bottom - r.top);
//
//                Log.i("Keyboard Height", "screenHeight:" + screenHeight);
//                Log.e("Keyboard Height", "heightDifference:" + heightDifference);

                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int visibleHeight = r.height();
                if (rootViewVisibleHeight == 0) {
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }

                //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
                if (rootViewVisibleHeight == visibleHeight) {
                    return;
                }

                //根视图显示高度变小超过200，可以看作软键盘显示了
                if (rootViewVisibleHeight - visibleHeight > 200) {
                    inputHeight = rootViewVisibleHeight - visibleHeight;
                    rootViewVisibleHeight = visibleHeight;
                    Log.i("Keyboard Height", "inputHeight000: " + inputHeight);

                    if (SharedPrefsUtil.getValue(activity, "softinput_height", 0) == 0)
                        SharedPrefsUtil.putValue(activity, "softinput_height", inputHeight);
                    return;
                }

                //根视图显示高度变大超过200，可以看作软键盘隐藏了
                if (visibleHeight - rootViewVisibleHeight > 200) {
                    inputHeight = visibleHeight - rootViewVisibleHeight;
                    rootViewVisibleHeight = visibleHeight;
                    Log.i("Keyboard Height", "inputHeight111: " + inputHeight);

                    return;
                }
            }
        });
    }

    /**
     * 切换软键盘的状态
     * 如当前为收起变为弹出,若当前为弹出变为收起
     */
    public static void toggleInput(Context context) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 强制隐藏输入法键盘
     */
    public static void hideInput(Context context, View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
