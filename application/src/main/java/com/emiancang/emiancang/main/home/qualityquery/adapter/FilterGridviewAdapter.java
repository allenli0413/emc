package com.emiancang.emiancang.main.home.qualityquery.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zwyl.BaseListAdapter;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.main.home.qualityquery.model.FilterGridviewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanyueqing on 2016/11/11.
 */

public class FilterGridviewAdapter extends BaseAdapter {

    List<FilterGridviewModel> list;

    public FilterGridviewAdapter(List<FilterGridviewModel> list) {
        this.list = list;
    }

    public void setList(List<FilterGridviewModel> list) {
        this.list = list;
    }

    public List<FilterGridviewModel> getList() {
        return list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FilterGridviewModel item = list.get(position);
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_filter_store, parent, false);
            viewHolder.tv_adapter_filter_store = (TextView) convertView.findViewById(R.id.tv_adapter_filter_store);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_adapter_filter_store.setText(item.getAreaName());
        viewHolder.tv_adapter_filter_store.setSelected(item.isSelected());

        return convertView;
    }

    class ViewHolder{
        TextView tv_adapter_filter_store;
    }
}
