package com.emiancang.emiancang.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.emiancang.emiancang.R;

import java.util.HashMap;

/**
 * All rights Reserved, Designed By GeofferySun
 *
 * @Title: SelectPicPopupWindow.java
 * @Package sun.geoffery.uploadpic
 * @Description:从底部弹出或滑出选择菜单或窗口
 * @author: GeofferySun
 * @date: 2015年1月15日 上午1:21:01
 * @version V1.0
 */



public class ShareWindow extends PopupWindow {

    private LinearLayout shareQQ, shareQZone, shareChat, shareFriend, shareSina;
    private TextView cancel;
    private View mMenuView;
    private Context context;
    private int platform;
    OnClickListener onClickListener;

    @SuppressLint("InflateParams")
    public ShareWindow(Context context, OnClickListener onClickListener) {
        super(context);
        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.share_window, null);

        shareQQ = (LinearLayout) mMenuView.findViewById(R.id.qq_share);
        shareChat = (LinearLayout) mMenuView.findViewById(R.id.wechat_share);
        shareFriend = (LinearLayout) mMenuView.findViewById(R.id.wechatfriend_share);
		shareQZone = (LinearLayout) mMenuView.findViewById(R.id.qzone_share);
		shareSina = (LinearLayout) mMenuView.findViewById(R.id.weibo_share);

		cancel = (TextView) mMenuView.findViewById(R.id.cancel_share_buttom);
		cancel.setVisibility(View.GONE);

        shareQQ.setOnClickListener(onClickListener);
        shareChat.setOnClickListener(onClickListener);
        shareFriend.setOnClickListener(onClickListener);
        shareQZone.setOnClickListener(onClickListener);
        shareSina.setOnClickListener(onClickListener);

//        pickPhotoBtn = (Button) mMenuView.findViewById(R.id.pickPhotoBtn);
//        cancelBtn = (Button) mMenuView.findViewById(R.id.cancelBtn_pop);
        // 设置按钮监听
//        cancelBtn.setOnClickListener(itemsOnClick);
//        pickPhotoBtn.setOnClickListener(itemsOnClick);
//		takePhotoBtn.setOnClickListener(itemsOnClick);

        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.pop_anim);
        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0x80000000);
        ColorDrawable dw = new ColorDrawable(0xFFFFFF);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {

            @Override
            @SuppressLint("ClickableViewAccessibility")
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
}
