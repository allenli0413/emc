package com.zwyl;

import android.view.View;
import android.view.ViewGroup;

import com.zhy.autolayout.utils.AutoUtils;


/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 封装ListAdapter
 * @date 2014/11/20 16:32
 */
public abstract class BaseListAdapter<T, Holder
        extends BaseListAdapter.ViewHolder> extends IListAdapter<T> {

    @Override
    @SuppressWarnings("unchecked")
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = onCreateViewHolder(parent, position);
            convertView = holder.itemView;
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        onBindViewHolder(holder, position);
        return convertView;
    }

    public static class ViewHolder {
        View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
            AutoUtils.autoSize(itemView);
        }
    }

    /**
     * 创建ViewHolder
     *
     * @param parent   父布局
     * @param position 当前位置
     * @return Viewholder
     */
    protected abstract Holder onCreateViewHolder(ViewGroup parent, int position);

    /**
     * 绑定数据
     *
     * @param viewHolder 当前viewHolder
     * @param position   当前位置
     */
    protected abstract void onBindViewHolder(Holder viewHolder, int position);
}
