package com.zwyl.title;

import android.view.View;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: Title逻辑控制
 * @date 2015/1/24 16:59
 */
public interface BaseHeaderBar {
    /**
     * 获取左边布局
     */
    View getLeftViewContianer();
    /**
     * 获取中间布局
     */
    View getCenterViewContainer();
    /**
     * 获取右边布局
     */
    View getRightViewContainer();

    /**
     * 设置左边的回调事件
     */
    void setLeftOnClickListner(View.OnClickListener listner);
    /**
     * 设置中间的回调事件
     */
    void setCenterOnClickListner(View.OnClickListener listner);
    /**
     * 设置右边的回调事件
     */
    void setRightOnClickListner(View.OnClickListener listner);
    /**
     * 设置右边的回调事件
     */
    void setRight2OnClickListner(View.OnClickListener listner);
}