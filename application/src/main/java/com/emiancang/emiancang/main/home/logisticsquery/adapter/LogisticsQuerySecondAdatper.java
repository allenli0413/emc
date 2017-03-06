package com.emiancang.emiancang.main.home.logisticsquery.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.main.home.logisticsquery.model.LogisticsQueryModel;
import com.emiancang.emiancang.main.home.logisticsquery.model.LogisticsQuerySecondModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yuanyueqing on 2017/2/22.
 */

public class LogisticsQuerySecondAdatper extends BaseAdapter {
    List<LogisticsQuerySecondModel> list = new ArrayList<>();

    public void addList(List<? extends LogisticsQuerySecondModel> allList){
        if (allList != null) {
            list.addAll(allList);
        }
    }

    public void clear(){
        list.clear();
    }

    public List<LogisticsQuerySecondModel> getList() {
        return list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public LogisticsQuerySecondModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogisticsQuerySecondModel item = list.get(position);
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_logistics_query_second, parent, false);
            viewHolder.tv_adapter_logistics_query_second_service = (TextView) convertView.findViewById(R.id.tv_adapter_logistics_query_second_service);
            viewHolder.tv_adapter_logistics_query_second_price = (TextView) convertView.findViewById(R.id.tv_adapter_logistics_query_second_price);
            viewHolder.tv_adapter_logistics_query_second_company = (TextView) convertView.findViewById(R.id.tv_adapter_logistics_query_second_company);
            viewHolder.tv_adapter_logistics_query_second_transport = (TextView) convertView.findViewById(R.id.tv_adapter_logistics_query_second_transport);
            viewHolder.tv_adapter_logistics_query_second_time = (TextView) convertView.findViewById(R.id.tv_adapter_logistics_query_second_time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_adapter_logistics_query_second_service.setText(item.getServiceName());
        viewHolder.tv_adapter_logistics_query_second_price.setText(item.getServicePrice() + "元/吨");
        viewHolder.tv_adapter_logistics_query_second_company.setText(item.getCustName());
        if(item.getTransportation().equals("1"))
            viewHolder.tv_adapter_logistics_query_second_transport.setText("公路");
        if(item.getTransportation().equals("2"))
            viewHolder.tv_adapter_logistics_query_second_transport.setText("铁路");
        viewHolder.tv_adapter_logistics_query_second_time.setText(item.getStartDate().split(" ")[0] + "～" + item.getEndDate().split(" ")[0]);

        return convertView;
    }

    class ViewHolder{
        TextView tv_adapter_logistics_query_second_service, tv_adapter_logistics_query_second_price,
                tv_adapter_logistics_query_second_company, tv_adapter_logistics_query_second_transport, tv_adapter_logistics_query_second_time;
    }
}
