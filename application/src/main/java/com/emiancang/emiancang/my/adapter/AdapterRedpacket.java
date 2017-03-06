package com.emiancang.emiancang.my.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.bean.MoneyInfo;
import com.emiancang.emiancang.bean.MoneyInfo.ListBean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: TODO
 * @date 16-9-1 下午3:17
 */
public class AdapterRedpacket extends BaseAdapter {

    private List<MoneyInfo.ListBean> list;

    public AdapterRedpacket() {
        list = new ArrayList<>();
    }

    public void addList(List<? extends MoneyInfo.ListBean> list) {
        if (list != null) {
            this.list.addAll(list);
        }
    }

    public void setData(List<MoneyInfo.ListBean> list){
        this.list = list;
    }

    public void setList(List<MoneyInfo.ListBean> list) {
        this.list = list;
    }

    public List<MoneyInfo.ListBean> getList() {
        return list;
    }

    public void clear() {
        list.clear();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public MoneyInfo.ListBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_red_packet, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        MoneyInfo.ListBean entity = list.get(position);
        viewHolder.redPacketDate.setText(entity.getEzHblcreateTime());
        viewHolder.redPacketMoney.setText(entity.getEzHblmoney()+"元");
        viewHolder.redPacketContent.setText(entity.getDicValue());
        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.red_packet_date)
        TextView redPacketDate;
        @InjectView(R.id.red_packet_money)
        TextView redPacketMoney;
        @InjectView(R.id.red_packet_content)
        TextView redPacketContent;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
