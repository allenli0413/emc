package com.emiancang.emiancang.http;

import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mayigeek.frame.view.state.ViewHttpState;
import com.emiancang.emiancang.R;

import org.jetbrains.annotations.NotNull;

import static android.R.attr.id;
import static com.emiancang.emiancang.R.id.iamge;
import static com.emiancang.emiancang.R.id.view;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 用来实现加载，失败情况展示的布局
 * @date 16-8-31 下午5:21
 */
public class SimpleViewHttpState implements ViewHttpState {

    @NotNull
    @Override
    public View loadingView(@NotNull ViewGroup parentView) {
        View view = LayoutInflater.from(parentView.getContext()).inflate(R.layout.view_loading, parentView, false);
        ImageView iamge = (ImageView) view.findViewById(R.id.animationIV);
        iamge.setBackgroundResource(R.drawable.load_animation);
        AnimationDrawable AniDraw = (AnimationDrawable)iamge.getBackground();
        AniDraw.start();
        return view;
    }
//
//    @NotNull
//    @Override
//    public View errorView(@NotNull ViewGroup parentView,String error_text) {
//        View view = LayoutInflater.from(parentView.getContext()).inflate(R.layout.view_error, parentView, false);
//        TextView textView = (TextView) view.findViewById(R.id.error_text);
//        textView.setText(error_text);
//        return view;
//    }
//
//    @NotNull
//    @Override
//    public View emptyView(@NotNull ViewGroup parentView,String error_text) {
//        View view = LayoutInflater.from(parentView.getContext()).inflate(R.layout.view_empty, parentView, false);
//        TextView textView = (TextView) view.findViewById(R.id.error_text);
//        textView.setText(error_text);
//        return view;
//    }

    @NotNull
    @Override
    public View emptyView(@NotNull ViewGroup parentView, @NotNull String error_text, @NotNull int id) {
        View view = LayoutInflater.from(parentView.getContext()).inflate(R.layout.view_empty, parentView, false);
        TextView textView = (TextView) view.findViewById(R.id.error_text);
        textView.setText(error_text);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        image.setImageDrawable(parentView.getContext().getResources().getDrawable(id));
        return view;
    }

    @NotNull
    @Override
    public View errorView(@NotNull ViewGroup parentView, @NotNull String error_text, @NotNull int id) {
        View view = LayoutInflater.from(parentView.getContext()).inflate(R.layout.view_error, parentView, false);
        TextView textView = (TextView) view.findViewById(R.id.error_text);
        textView.setText(error_text);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        image.setImageDrawable(parentView.getContext().getResources().getDrawable(id));
        return view;
    }
}
