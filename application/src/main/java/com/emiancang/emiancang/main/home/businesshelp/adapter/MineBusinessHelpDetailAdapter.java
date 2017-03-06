package com.emiancang.emiancang.main.home.businesshelp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.main.home.businesshelp.model.MineBusinessHelpDetailStatusModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanyueqing on 2016/11/9.
 */

public class MineBusinessHelpDetailAdapter extends BaseAdapter {

    List<MineBusinessHelpDetailStatusModel.EfxkkList> list = new ArrayList<>();

    public MineBusinessHelpDetailAdapter(List<MineBusinessHelpDetailStatusModel.EfxkkList> list) {
        this.list = list;
    }

    public void addList(List<? extends MineBusinessHelpDetailStatusModel.EfxkkList> allList){
        if(allList != null)
            list.addAll(allList);
    }

    public void clear(){
        list.clear();
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
        MineBusinessHelpDetailStatusModel.EfxkkList item = list.get(position);
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_find_money_detail_status, parent, false);
            viewHolder.tv_adapter_find_money_detail_status_content = (TextView) convertView.findViewById(R.id.tv_adapter_find_money_detail_status_content);
            viewHolder.tv_adapter_find_money_detail_status_time = (TextView) convertView.findViewById(R.id.tv_adapter_find_money_detail_status_time);
            viewHolder.view_find_money_detail_flip = convertView.findViewById(R.id.view_find_money_detail_flip);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_adapter_find_money_detail_status_time.setText(item.geteFkxxFksj());
        viewHolder.tv_adapter_find_money_detail_status_content.setText(item.geteFkxxFknr());
        if(position == list.size() - 1) {
            viewHolder.view_find_money_detail_flip.setVisibility(View.GONE);
        }else{
            viewHolder.view_find_money_detail_flip.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    class ViewHolder{

        TextView tv_adapter_find_money_detail_status_time;
        TextView tv_adapter_find_money_detail_status_content;
        View view_find_money_detail_flip;

    }
}
