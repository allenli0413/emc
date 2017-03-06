package com.emiancang.emiancang.main.home.logisticsquery.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.main.home.logisticsquery.model.LogisticsQueryModel;
import com.emiancang.emiancang.view.CircleImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yuanyueqing on 2016/11/9.
 */

public class LogisticsQueryAdapter extends BaseAdapter {

    List<LogisticsQueryModel> list = new ArrayList<>();

    public void addList(List<? extends LogisticsQueryModel> allList){
        if (allList != null) {
            list.addAll(allList);
        }
    }

    public void clear(){
        list.clear();
    }

    public List<LogisticsQueryModel> getList() {
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
        LogisticsQueryModel item = list.get(position);
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_logistics_query, parent, false);
            viewHolder.ll_adapter_logistics_query_head = (LinearLayout) convertView.findViewById(R.id.ll_adapter_logistics_query_head);
            viewHolder.view_adapter_logistics_query_split = convertView.findViewById(R.id.view_adapter_logistics_query_split);
            viewHolder.iv_adapter_logistics_query_avatar = (SimpleDraweeView) convertView.findViewById(R.id.iv_adapter_logistics_query_avatar);
            viewHolder.tv_adapter_logistics_query_name = (TextView) convertView.findViewById(R.id.tv_adapter_logistics_query_name);
            viewHolder.tv_adapter_logistics_query_number = (TextView) convertView.findViewById(R.id.tv_adapter_logistics_query_number);
            viewHolder.tv_adapter_logistics_query_start_place = (TextView) convertView.findViewById(R.id.tv_adapter_logistics_query_start_place);
            viewHolder.tv_adapter_logistics_query_end_place = (TextView) convertView.findViewById(R.id.tv_adapter_logistics_query_end_place);
            viewHolder.tv_adapter_logistics_query_single_price = (TextView) convertView.findViewById(R.id.tv_adapter_logistics_query_single_price);
            viewHolder.tv_adapter_logistics_query_start_time = (TextView) convertView.findViewById(R.id.tv_adapter_logistics_query_start_time);
            viewHolder.tv_adapter_logistics_query_end_time = (TextView) convertView.findViewById(R.id.tv_adapter_logistics_query_end_time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ll_adapter_logistics_query_head.setVisibility(View.VISIBLE);
        viewHolder.view_adapter_logistics_query_split.setVisibility(View.VISIBLE);

        //TODO 还有个头像
        if(!item.geteYhtx().isEmpty()) {
            viewHolder.iv_adapter_logistics_query_avatar.setImageURI(Uri.parse(item.geteYhtx()));
        }else{
            viewHolder.iv_adapter_logistics_query_avatar.setImageResource(R.drawable.default_avater);
        }
        viewHolder.tv_adapter_logistics_query_name.setText(item.geteSjzcXm());
        viewHolder.tv_adapter_logistics_query_number.setText(item.geteSjzcSjh());
        viewHolder.tv_adapter_logistics_query_start_place.setText(item.getStatrAddrName());
        viewHolder.tv_adapter_logistics_query_end_place.setText(item.getEndAddrName());
        viewHolder.tv_adapter_logistics_query_single_price.setText(item.getServicePrice() + "元/吨");
//        String startTime = item.getStartDate().split(" ")[0];
//        startTime.replaceFirst("\\-", "年");
//        startTime.replaceFirst("\\-", "月");
//        startTime = startTime + "日";
//        viewHolder.tv_adapter_logistics_query_start_time.setText(startTime);
//        String endTime = item.getEndDate().split(" ")[0];
//        endTime.replaceFirst("\\-", "年");
//        endTime.replaceFirst("\\-", "月");
//        endTime = endTime + "日";
//        viewHolder.tv_adapter_logistics_query_end_time .setText(endTime);

        try {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(item.getStartDate());
            viewHolder.tv_adapter_logistics_query_start_time.setText(new SimpleDateFormat("yyyy年MM月dd日").format(startDate));
            Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(item.getEndDate());
            viewHolder.tv_adapter_logistics_query_end_time .setText(new SimpleDateFormat("yyyy年MM月dd日").format(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    class ViewHolder{
        LinearLayout ll_adapter_logistics_query_head;
        View view_adapter_logistics_query_split;
        SimpleDraweeView iv_adapter_logistics_query_avatar;
        TextView tv_adapter_logistics_query_name, tv_adapter_logistics_query_number,
                tv_adapter_logistics_query_start_place, tv_adapter_logistics_query_end_place, tv_adapter_logistics_query_single_price, tv_adapter_logistics_query_start_time, tv_adapter_logistics_query_end_time;
    }
}
