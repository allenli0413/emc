package com.emiancang.emiancang.base;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.litesuits.common.utils.InputMethodUtils;
import com.zhy.autolayout.utils.AutoUtils;
import com.emiancang.emiancang.R;
import com.zwyl.title.BaseHeaderBar;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 标题管理
 * @date 2015/1/24 17:16
 */
public class TitleHeaderBar implements BaseHeaderBar {

    ViewGroup mParentView;
    @InjectView(R.id.left_image)
    ImageView leftImage;
    @InjectView(R.id.left_text)
    TextView leftText;
    @InjectView(R.id.title_text)
    TextView titleText;
    @InjectView(R.id.right_text)
    TextView rightText;
    @InjectView(R.id.right_image)
    ImageView rightImage;
    @InjectView(R.id.right_image2)
    ImageView rightImage2;

    public View child;

    public TitleHeaderBar(ViewGroup parentView, final Activity activity) {
        this.mParentView = parentView;
        LayoutInflater layoutInflater = LayoutInflater.from(parentView.getContext());
        child= layoutInflater.inflate(R.layout.activity_title_view, parentView, false);
        AutoUtils.autoSize(child);
        ButterKnife.inject(this, child);
        mParentView.addView(child);
        leftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodUtils.hideSoftInput(activity);
                activity.finish();
            }
        });
    }

    public void hideHeader(){
        child.setVisibility(View.GONE);
    }

    public void hideLeftImage() {
        leftImage.setVisibility(View.INVISIBLE);
    }

    public void hideRightImage() {
        rightImage.setVisibility(View.INVISIBLE);
    }
    public void hideRightImage2() {
        rightImage2.setVisibility(View.INVISIBLE);
    }

    public void showLeftImage() {
        leftImage.setVisibility(View.VISIBLE);
    }

    public void showRightImage() {
        rightImage.setVisibility(View.VISIBLE);
    }
    public void showRightImage2() {
        rightImage2.setVisibility(View.VISIBLE);
    }


    public void hideLeftText() {
        leftText.setVisibility(View.INVISIBLE);
    }

    public void hideRightText() {
        rightText.setVisibility(View.INVISIBLE);
    }
    public void showLeftText() {
        leftText.setVisibility(View.VISIBLE);
    }

    public void showRightText() {
        rightText.setVisibility(View.VISIBLE);
    }

    @Override
    public View getLeftViewContianer() {
        return leftImage;
    }

    @Override
    public View getCenterViewContainer() {
        return titleText;
    }

    @Override
    public View getRightViewContainer() {
        return rightImage;
    }

    @Override
    public void setLeftOnClickListner(View.OnClickListener listner) {
        leftText.setOnClickListener(listner);
        leftImage.setOnClickListener(listner);
    }

    @Override
    public void setCenterOnClickListner(View.OnClickListener listner) {
        titleText.setOnClickListener(listner);
    }

    @Override
    public void setRightOnClickListner(View.OnClickListener listner) {
        rightImage.setOnClickListener(listner);
        rightText.setOnClickListener(listner);
    }

    public void setRight2OnClickListner(View.OnClickListener listner) {
        rightImage2.setOnClickListener(listner);
    }

    public void setBackground(int color){
        child.setBackgroundColor(color);
    }

    public void setCenterTitle(String str) {
        titleText.setText(str);
    }

    public void setCenterTitle(String str,int color) {
        titleText.setText(str);
        titleText.setTextColor(color);
    }

    public void setCenterTitle(int res) {
        titleText.setText(res);
    }

    public void setRightTitle(String str) {
        rightText.setText(str);
    }
    public void setRightTitle(String str,int color) {
        rightText.setText(str);
        rightText.setTextColor(color);
    }

    public void setRightTitle(int res) {
        rightText.setText(res);
    }

    public void setRightImage(int res) {
        rightImage.setImageResource(res);
    }
    public void setRightImage2(int res) {
        rightImage2.setImageResource(res);
    }

    public void setLeftImage(int res) {
        leftImage.setImageResource(res);
    }

    public void setLeftTitle(String str) {
        leftText.setText(str);
    }

    public void setLeftTitle(String str, int color){
        leftText.setText(str);
        leftText.setTextColor(color);
    }

    public void setLeftTitle(int res) {
        leftText.setText(res);
    }
}
