package com.emiancang.emiancang.my.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.bean.OrderInfo;

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
public class AdapterIndentMarket extends BaseAdapter {

    private List<OrderInfo> list = new ArrayList<>();

    public AdapterIndentMarket() {

    }

    public void addList(List<? extends OrderInfo> list) {
        if (list != null) {
            this.list.addAll(list);
        }
    }

    public List<OrderInfo> getList() {
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
    public OrderInfo getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_indent_market, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        OrderInfo entity = list.get(position);
        viewHolder.indentNumber.setText(entity.getGcmgyMhph()+" ("+entity.getEDdJs()+"包)");
        if(entity.getEDdDdzt().equals("01")){
            viewHolder.indentType.setText("代付款");
        }else if(entity.getEDdDdzt().equals("02")){
            viewHolder.indentType.setText("待收货");
        }else if(entity.getEDdDdzt().equals("03")){
            viewHolder.indentType.setText("已完成");
        }else if(entity.getEDdDdzt().equals("04")){
            viewHolder.indentType.setText("已违约");
        }else{
            viewHolder.indentType.setText("代付款");
        }
        viewHolder.indentSupplierName.setText(""+entity.getEDdGhsmc());
        viewHolder.indentContacts.setText(entity.getEDdGhsLxrmc()+" "+entity.getEDdGhsLxrDh());
        viewHolder.indentSalesman.setText(entity.getEDdZytsrxm()+" "+entity.getESjzcSjh());
        viewHolder.indentPrice.setText(""+entity.getEDdDj());
        viewHolder.indentWeight.setText(""+entity.getEDdJszl());
        viewHolder.indentRedpacket.setText(""+entity.getESpbsHbje());
        viewHolder.indentDate.setText(""+entity.getEDdXdsj());
        viewHolder.indentMoney.setText(""+entity.getEDdJe());
        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.indent_number)
        TextView indentNumber;
        @InjectView(R.id.indent_type)
        TextView indentType;
        @InjectView(R.id.indent_supplier_name)
        TextView indentSupplierName;
        @InjectView(R.id.indent_contacts)
        TextView indentContacts;
        @InjectView(R.id.indent_salesman)
        TextView indentSalesman;
        @InjectView(R.id.indent_price)
        TextView indentPrice;
        @InjectView(R.id.indent_weight)
        TextView indentWeight;
        @InjectView(R.id.indent_redpacket)
        TextView indentRedpacket;
        @InjectView(R.id.indent_date)
        TextView indentDate;
        @InjectView(R.id.indent_money)
        TextView indentMoney;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
