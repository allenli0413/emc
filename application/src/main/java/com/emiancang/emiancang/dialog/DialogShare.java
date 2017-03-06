package com.emiancang.emiancang.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.emiancang.emiancang.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 22310 on 2016/4/23.
 */
public class DialogShare extends PopupWindow {

    public interface OnShareListener{
        public void PengYouQuan();
        public void WeiXin();
        public void QQ();
        public void WeiBo();
    }

    private TextView btn_cancel;
    private View mMenuView;
    public OnShareListener onShareListener;

    public DialogShare(Activity context,View.OnClickListener itemsOnClick) {
        super(context);
        this.onShareListener = onShareListener;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.dialog_share, null);
        btn_cancel = (TextView) mMenuView.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mMenuView.findViewById(R.id.share_pengyouquan).setOnClickListener(itemsOnClick);
        mMenuView.findViewById(R.id.share_weixin).setOnClickListener(itemsOnClick);
        mMenuView.findViewById(R.id.share_qq).setOnClickListener(itemsOnClick);
        mMenuView.findViewById(R.id.share_weibo).setOnClickListener(itemsOnClick);

        //设置按钮监听
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(WindowManager.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.anim.in_from_botton);
        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x33000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    public void setShareListener(OnShareListener onShareListener){

    }
}