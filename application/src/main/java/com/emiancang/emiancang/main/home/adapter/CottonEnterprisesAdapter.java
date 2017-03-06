package com.emiancang.emiancang.main.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.main.home.model.CottonEnterprisesModel;
import com.emiancang.emiancang.nearby.NearbyDetailsActivity;
import com.emiancang.emiancang.nearby.NearbySiteActivity;
import com.emiancang.emiancang.uitl.DoubiUtils;

import java.util.List;

/**
 * Created by yuanyueqing on 2016/11/7.
 */

public class CottonEnterprisesAdapter extends BaseAdapter {

    Context mContext;
    List<CottonEnterprisesModel> list;
    private boolean itemClick = true;

    public CottonEnterprisesAdapter(Context mContext, List<CottonEnterprisesModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public boolean isItemClick() {
        return itemClick;
    }

    public void setItemClick(boolean itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CottonEnterprisesModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CottonEnterprisesModel item = list.get(position);
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_cotton_enterprises,
                    parent, false);
            viewHolder.ll_adapter_cotton_enterprieses_main = (LinearLayout) convertView.findViewById(R.id.ll_adapter_cotton_enterprieses_main);
            viewHolder.tv_adapter_cotton_enterprises_name = (TextView) convertView.findViewById(R.id.tv_adapter_cotton_enterprises_name);
            viewHolder.tv_adapter_cotton_enterprises_location = (TextView) convertView.findViewById(R.id.tv_adapter_cotton_enterprises_location);
            viewHolder.tv_adapter_cotton_enterprises_distance = (TextView) convertView.findViewById(R.id.tv_adapter_cotton_enterprises_distance);
            viewHolder.rl_adapter_cotton_enterprises_start = (RelativeLayout) convertView.findViewById(R.id.rl_adapter_cotton_enterprises_start);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_adapter_cotton_enterprises_name.setText(item.getCustName());
        viewHolder.tv_adapter_cotton_enterprises_location.setText(item.getCompanyAddress());
        viewHolder.tv_adapter_cotton_enterprises_distance.setText(DoubiUtils.hehe(item.getDistance()));
        viewHolder.rl_adapter_cotton_enterprises_start.setOnClickListener(v -> {
            Intent nearby = new Intent(mContext,NearbySiteActivity.class);
            nearby.putExtra("lon", item.getLng());
            nearby.putExtra("lat", item.getLat());
            mContext.startActivity(nearby);
            itemClick = false;
        });
//        viewHolder.ll_adapter_cotton_enterprieses_main.setOnClickListener(v -> {
//            Intent intent = new Intent(mContext, NearbyDetailsActivity.class);
//            intent.putExtra("id", item.getCustId());
//            mContext.startActivity(intent);
//        });

        return convertView;
    }

    class ViewHolder{
        TextView tv_adapter_cotton_enterprises_name, tv_adapter_cotton_enterprises_location, tv_adapter_cotton_enterprises_distance;
        RelativeLayout rl_adapter_cotton_enterprises_start;
        LinearLayout ll_adapter_cotton_enterprieses_main;
    }
}
