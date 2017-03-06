package com.emiancang.emiancang.main.home.findmoney.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.main.home.findmoney.model.FindMoneyPublishTypeSelectModel;

import java.util.List;

/**
 * Created by yuanyueqing on 2016/11/23.
 */

public class FindMoneyPublishTypeSelectAdapter extends BaseAdapter {

    List<FindMoneyPublishTypeSelectModel> list;

    public FindMoneyPublishTypeSelectAdapter(List<FindMoneyPublishTypeSelectModel> list) {
        this.list = list;
    }

    public void setList(List<FindMoneyPublishTypeSelectModel> list) {
        this.list = list;
    }

    public List<FindMoneyPublishTypeSelectModel> getList() {
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
        FindMoneyPublishTypeSelectModel item = list.get(position);
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_find_money_publish_type_select, parent, false);
            viewHolder.tv_adapter_find_money_publish_type_select_name = (TextView) convertView.findViewById(R.id.tv_adapter_find_money_publish_type_select_name);
            viewHolder.iv_adapter_find_money_publish_type_select_status = (ImageView) convertView.findViewById(R.id.iv_adapter_find_money_publish_type_select_status);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_adapter_find_money_publish_type_select_name.setText(item.getName());
        if(item.isSelected()){
            viewHolder.iv_adapter_find_money_publish_type_select_status.setVisibility(View.VISIBLE);
        }else {
            viewHolder.iv_adapter_find_money_publish_type_select_status.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    class ViewHolder{
        TextView tv_adapter_find_money_publish_type_select_name;
        ImageView iv_adapter_find_money_publish_type_select_status;
    }
}
