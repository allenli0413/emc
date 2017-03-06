package com.emiancang.emiancang.http;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.mayigeek.frame.view.state.SimpleToastViewControl;
import com.mayigeek.frame.view.state.SimpleViewControl;
import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.R;

import java.util.Timer;
import java.util.TimerTask;

import static android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: ViewControl工具
 * @date 16-8-31 下午12:34
 */
public class ViewControlUtil {
    /**
     * 创建默认对话框形式
     */
    public static ViewControl create2Dialog(Context context) {
        View view = View.inflate(context, R.layout.layout_loading,null);
        ImageView iamge = (ImageView) view.findViewById(R.id.animationIV);
//        iamge.setBackgroundResource(R.drawable.load_animation);
//        AnimationDrawable AniDraw = (AnimationDrawable)iamge.getBackground();
//        AniDraw.start();

        int[] images = { R.drawable.qb_tenpay_loading_1_go, R.drawable.qb_tenpay_loading_2_go};
        int SIGN = 17;
        final int[] num = {0};
        final Handler handler = new Handler() {
             @Override
             public void handleMessage(Message msg) {
                 super.handleMessage(msg);
                 if (msg.what == SIGN) {
                     iamge.setImageResource(images[num[0]++]);
                     if (num[0] >= images.length) {
                         num[0] = 0;
                         }
                     }
                 }
             };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
             @Override
             public void run() {
                 // TODO Auto-generated method stub
                Message msg = new Message();
                msg.what = SIGN;
                handler.sendMessage(msg);
                }
            }, 100, 100);

//        AlertDialog dialog = new AlertDialog.Builder(context).create();
        Dialog dialog = new Dialog(context, R.style.custom_dialog);
        dialog.show();
        dialog.setContentView(view);
        dialog.setOnDismissListener(dialog1 -> {
//                AniDraw.stop();
            timer.cancel();
        });
        return new SimpleToastViewControl(dialog, () -> {
//            Toast.makeText(context, "sucess", Toast.LENGTH_SHORT).show();
        }, null, null).build();
    }

    /**
     * 创建自定义控制界面形式
     */
    public static ViewControl create2View(View view,String errorText,String emptyText,int id) {
        return new SimpleViewControl(view, new SimpleViewHttpState(),errorText,emptyText,id, true).build();
    }

    /**
     * 创建默认控制界面形式
     */
    public static ViewControl create2View(View view) {
        return new SimpleViewControl(view, new SimpleViewHttpState(),"","",R.drawable.icon_content_empty, true).build();
    }

    /**
     * 蛋疼
     */
    public static ViewControl create2View(View view, boolean needError) {
        return new SimpleViewControl(view, new SimpleViewHttpState(),"","",R.drawable.icon_content_empty, needError).build();
    }
}
