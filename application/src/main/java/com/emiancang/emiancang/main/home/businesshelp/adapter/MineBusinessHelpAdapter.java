package com.emiancang.emiancang.main.home.businesshelp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.main.home.businesshelp.model.BusinessHelpModel;
import com.emiancang.emiancang.main.home.businesshelp.model.MineBusinessHelpModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanyueqing on 2016/11/9.
 */

public class MineBusinessHelpAdapter extends BaseAdapter {

    List<BusinessHelpModel> list = new ArrayList<>();

    public void clear(){
        list.clear();
    }

    public void addList(List<? extends BusinessHelpModel> allList){
        if(allList != null)
            list.addAll(allList);
    }

    public List<BusinessHelpModel> getList() {
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
        BusinessHelpModel item = list.get(position);
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_find_money, parent, false);
            viewHolder.tv_adapter_find_money_content = (TextView) convertView.findViewById(R.id.tv_adapter_find_money_content);
            viewHolder.tv_adapter_find_money_time = (TextView) convertView.findViewById(R.id.tv_adapter_find_money_time);
            viewHolder.tv_adapter_find_money_status = (TextView) convertView.findViewById(R.id.tv_adapter_find_money_status);
            viewHolder.tv_adapter_business_help_type_find_moeny = (TextView) convertView.findViewById(R.id.tv_adapter_business_help_type_find_moeny);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_adapter_business_help_type_find_moeny.setVisibility(View.VISIBLE);
        if(item.geteCgxqType().equals("0"))
            viewHolder.tv_adapter_business_help_type_find_moeny.setText("购买需求");
        if(item.geteCgxqType().equals("1"))
            viewHolder.tv_adapter_business_help_type_find_moeny.setText("销售需求");
        viewHolder.tv_adapter_find_money_content.setText(item.geteCgxqAppxqxq());
        viewHolder.tv_adapter_find_money_time.setText(item.geteCgxqCjsj());
        if(item.geteCgxqFkzt().equals("00"))
            viewHolder.tv_adapter_find_money_status.setText("未反馈");
        if(item.geteCgxqFkzt().equals("01"))
            viewHolder.tv_adapter_find_money_status.setText("已反馈");

        return convertView;
    }

    class ViewHolder{
        TextView tv_adapter_find_money_content;
        TextView tv_adapter_find_money_time;
        TextView tv_adapter_find_money_status;
        TextView tv_adapter_business_help_type_find_moeny;
    }
}
