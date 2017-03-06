package com.emiancang.emiancang.main.home.redpapercotton.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.main.home.redpapercotton.model.RedPaperCottonModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanyueqing on 2016/11/14.
 */

public class RedPaperCottonAdapter extends BaseAdapter {


    List<RedPaperCottonModel> list = new ArrayList<>();

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
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cotton_recommend,
                    parent, false);
            viewHolder.tv_adapter_cotton_recommend_order_number = (TextView) convertView.findViewById(R.id.tv_adapter_cotton_recommend_order_number);
            viewHolder.tv_adapter_cotton_recommend_order_detail = (TextView) convertView.findViewById(R.id.tv_adapter_cotton_recommend_order_detail);
            viewHolder.tv_adapter_cotton_recommend_order_price = (TextView) convertView.findViewById(R.id.tv_adapter_cotton_recommend_order_price);
            viewHolder.tv_adapter_cotton_recommend_order_supplier = (TextView) convertView.findViewById(R.id.tv_adapter_cotton_recommend_order_supplier);
            viewHolder.tv_adapter_cotton_recommend_order_red_paper = (TextView) convertView.findViewById(R.id.tv_adapter_cotton_recommend_order_red_paper);
            viewHolder.tv_adapter_cotton_recommend_order_repetory = (TextView) convertView.findViewById(R.id.tv_adapter_cotton_recommend_order_repetory);
            viewHolder.tv_adapter_cotton_recommend_order_repetory_distance = (TextView) convertView.findViewById(R.id.tv_adapter_cotton_recommend_order_repetory_distance);
            viewHolder.tv_adapter_cotton_recommend_order_publish_time = (TextView) convertView.findViewById(R.id.tv_adapter_cotton_recommend_order_publish_time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    public void clear() {
        list.clear();
    }

    class ViewHolder {
        TextView tv_adapter_cotton_recommend_order_number, tv_adapter_cotton_recommend_order_detail, tv_adapter_cotton_recommend_order_price, tv_adapter_cotton_recommend_order_supplier,
                tv_adapter_cotton_recommend_order_red_paper, tv_adapter_cotton_recommend_order_repetory, tv_adapter_cotton_recommend_order_repetory_distance, tv_adapter_cotton_recommend_order_publish_time;
    }

}
