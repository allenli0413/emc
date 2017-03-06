package com.emiancang.emiancang.main.home.businesshelp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.main.home.businesshelp.model.BusinessHelpModel;
import com.emiancang.emiancang.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanyueqing on 2016/11/8.
 */

public class BusinessHelpAdapter extends BaseAdapter {

    Context context;
    List<BusinessHelpModel> list = new ArrayList<>();

    public void addList(List<? extends BusinessHelpModel> list) {
        if (list != null) {
            this.list.addAll(list);
        }
    }

    public List<BusinessHelpModel> getList() {
        return list;
    }

    public void clear() {
        list.clear();
    }

    public BusinessHelpAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public BusinessHelpModel getItem(int position) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_business_help, parent, false);
            viewHolder.iv_adapter_business_help_avatar = (CircleImageView) convertView.findViewById(R.id.iv_adapter_business_help_avatar);
            viewHolder.tv_adapter_business_help_name = (TextView) convertView.findViewById(R.id.tv_adapter_business_help_name);
            viewHolder.tv_adapter_business_help_time = (TextView) convertView.findViewById(R.id.tv_adapter_business_help_time);
            viewHolder.tv_adapter_business_help_content = (TextView) convertView.findViewById(R.id.tv_adapter_business_help_content);
            viewHolder.iv_adapter_business_help_type = (ImageView) convertView.findViewById(R.id.iv_adapter_business_help_type);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.iv_adapter_business_help_avatar.setVisibility(View.VISIBLE);

        if(item.geteCgxqType().equals("0"))
            viewHolder.iv_adapter_business_help_type.setImageResource(R.drawable.buy_requirement);
        if(item.geteCgxqType().equals("1"))
            viewHolder.iv_adapter_business_help_type.setImageResource(R.drawable.sell_requirement);
        viewHolder.tv_adapter_business_help_name.setText(item.geteCgxqCjrName());
        viewHolder.tv_adapter_business_help_time.setText(item.geteCgxqCjsj());
        viewHolder.tv_adapter_business_help_content.setText(item.geteCgxqAppxqxq());

        return convertView;
    }

    class ViewHolder{


        CircleImageView iv_adapter_business_help_avatar;

        TextView tv_adapter_business_help_name;

        TextView tv_adapter_business_help_time;

        TextView tv_adapter_business_help_content;

        ImageView iv_adapter_business_help_type;


    }
}
