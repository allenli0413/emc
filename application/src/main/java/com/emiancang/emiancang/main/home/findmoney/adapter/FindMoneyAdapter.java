package com.emiancang.emiancang.main.home.findmoney.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.main.home.findmoney.model.FindMoneyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanyueqing on 2016/11/8.
 */

public class FindMoneyAdapter extends BaseAdapter {

    Context context;
    List<FindMoneyModel> list = new ArrayList<>();

    public FindMoneyAdapter(Context context) {
        this.context = context;
    }

    public void addList(List<? extends FindMoneyModel> allList) {
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
    public FindMoneyModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FindMoneyModel item = list.get(position);
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

        viewHolder.tv_adapter_business_help_type_find_moeny.setVisibility(View.GONE);

        viewHolder.tv_adapter_find_money_content.setText(item.geteJrsqNr());
        viewHolder.tv_adapter_find_money_time.setText(item.geteJrsqSqsj());
        if(item.geteJrsqFkzt().equals("0"))
            viewHolder.tv_adapter_find_money_status.setText("未反馈");
        if(item.geteJrsqFkzt().equals("1"))
            viewHolder.tv_adapter_find_money_status.setText("已反馈");
        viewHolder.tv_adapter_find_money_status.setTextColor(context.getResources().getColor(R.color.green));

        return convertView;
    }

    class ViewHolder{

        TextView tv_adapter_find_money_content;
        TextView tv_adapter_find_money_time;
        TextView tv_adapter_find_money_status;
        TextView tv_adapter_business_help_type_find_moeny;

    }
}
