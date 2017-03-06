package com.emiancang.emiancang.dialog;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.zwyl.BaseActivity;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.ZwyCameraActivity;

/**
 * @author weixy@zhiwy.com
 * @version V1.0
 * @Description: 头像选择对话框 拍照 or 相册
 * @date 2015/10/29 17:39
 */
public class ImageSelectDialog extends BaseDialog {

    private Activity mActivity;
    View.OnClickListener listener;
    private View.OnClickListener defaultListener;
    boolean enable_zoom;
    int imageType;

    public ImageSelectDialog(Activity activity, boolean enable_zoom, int imageType) {
        super(activity, R.style.dialog);
        this.mActivity = activity;
        this.enable_zoom = enable_zoom;
        this.imageType = imageType;

        setContentView(R.layout.zwyl_simple_dialog);
        WindowManager m = getWindow().getWindowManager();
        //并不充满全屏
//        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
//        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
//        p.width = (int) (d.getWidth());
//        getWindow().setAttributes(p);
        getWindow().setGravity(Gravity.BOTTOM);

        initListener();
        onCreateView();

    }
    private void initListener() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int message = (Integer) v.getTag();
                Intent intent = new Intent (mActivity,ZwyCameraActivity.class);
                ZwyCameraActivity.type = message;
                ZwyCameraActivity.enable_zoom = enable_zoom;
                ZwyCameraActivity.imageType = imageType;
                mActivity.startActivity(intent);
                dismiss();
            }
        };
        defaultListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        };
    }

    public void onCreateView() {
        TextView item1 = (TextView) findViewById(R.id.tv_item1);
        TextView item2 = (TextView) findViewById(R.id.tv_item2);
        TextView cancel = (TextView) findViewById(R.id.tv_cancel);

        item1.setTag(0);
        item2.setTag(1);

        item1.setOnClickListener(listener);
        item2.setOnClickListener(listener);
        cancel.setOnClickListener(defaultListener);
    }

}

