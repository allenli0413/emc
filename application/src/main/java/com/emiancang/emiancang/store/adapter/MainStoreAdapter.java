package com.emiancang.emiancang.store.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.store.model.MainStoreModel;
import com.emiancang.emiancang.uitl.DoubiUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanyueqing on 2016/11/14.
 */

public class MainStoreAdapter extends BaseAdapter {

    List<MainStoreModel> list = new ArrayList<>();

    public List<MainStoreModel> getList() {
        return list;
    }

    public void setList(List<MainStoreModel> list) {
        this.list = list;
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
        MainStoreModel item = list.get(position);
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cotton_recommend,
                    parent, false);
            viewHolder.ll_adapter_cotton_recommend_item = (LinearLayout) convertView.findViewById(R.id.ll_adapter_cotton_recommend_item);
            viewHolder.tv_adapter_cotton_recommend_order_number = (TextView) convertView.findViewById(R.id.tv_adapter_cotton_recommend_order_number);
            viewHolder.tv_adapter_cotton_recommend_order_detail = (TextView) convertView.findViewById(R.id.tv_adapter_cotton_recommend_order_detail);
            viewHolder.tv_adapter_cotton_recommend_order_price = (TextView) convertView.findViewById(R.id.tv_adapter_cotton_recommend_order_price);
            viewHolder.tv_adapter_cotton_recommend_order_price_front = (TextView) convertView.findViewById(R.id.tv_adapter_cotton_recommend_order_price_front);
            viewHolder.tv_adapter_cotton_recommend_order_price_back = (TextView) convertView.findViewById(R.id.tv_adapter_cotton_recommend_order_price_back);
            viewHolder.tv_adapter_cotton_recommend_order_supplier = (TextView) convertView.findViewById(R.id.tv_adapter_cotton_recommend_order_supplier);
            viewHolder.tv_adapter_cotton_recommend_order_red_paper = (TextView) convertView.findViewById(R.id.tv_adapter_cotton_recommend_order_red_paper);
            viewHolder.tv_adapter_cotton_recommend_order_repetory = (TextView) convertView.findViewById(R.id.tv_adapter_cotton_recommend_order_repetory);
            viewHolder.tv_adapter_cotton_recommend_order_repetory_distance = (TextView) convertView.findViewById(R.id.tv_adapter_cotton_recommend_order_repetory_distance);
            viewHolder.tv_adapter_cotton_recommend_order_publish_time = (TextView) convertView.findViewById(R.id.tv_adapter_cotton_recommend_order_publish_time);
            viewHolder.iv_adapter_cotton_recommend_online = (ImageView) convertView.findViewById(R.id.iv_adapter_cotton_recommend_online);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(item.getGcmgyMhph() == null) {
            viewHolder.ll_adapter_cotton_recommend_item.setVisibility(View.GONE);
        }else {
            viewHolder.ll_adapter_cotton_recommend_item.setVisibility(View.VISIBLE);

            if (item.getIsOnlineTrade().equals("0")) {
                viewHolder.iv_adapter_cotton_recommend_online.setVisibility(View.GONE);
            }
            if (item.getIsOnlineTrade().equals("1")) {
                viewHolder.iv_adapter_cotton_recommend_online.setVisibility(View.VISIBLE);
            }

            viewHolder.tv_adapter_cotton_recommend_order_number.setText(item.getGcmgyMhph() + "（" + item.getHyJysjZbs() + "包）");
            viewHolder.tv_adapter_cotton_recommend_order_detail.setText(item.getZtYsj() + " 长度：" + item.getHyJysjCdz() + " 强力：" + item.getQl() + " 马值：" + item.getMklz());
            viewHolder.tv_adapter_cotton_recommend_order_price_front.setText("仓库自提价：");
            viewHolder.tv_adapter_cotton_recommend_order_price.setText(item.getProductPrice());
            viewHolder.tv_adapter_cotton_recommend_order_price_back.setText("元/吨  公重");
            viewHolder.tv_adapter_cotton_recommend_order_supplier.setText("供货商：" + item.getCustName());
            if (item.geteSpbsSfhbm() != null) {
                if (item.geteSpbsSfhbm().equals("")) {
                    viewHolder.tv_adapter_cotton_recommend_order_red_paper.setVisibility(View.GONE);
                }
                if (item.geteSpbsSfhbm().equals("0")) {
                    viewHolder.tv_adapter_cotton_recommend_order_red_paper.setVisibility(View.GONE);
                }
                if (item.geteSpbsSfhbm().equals("1")) {
                    viewHolder.tv_adapter_cotton_recommend_order_red_paper.setVisibility(View.VISIBLE);
                    viewHolder.tv_adapter_cotton_recommend_order_red_paper.setText(item.geteSpbsHbje());
                }
            } else {
                viewHolder.tv_adapter_cotton_recommend_order_red_paper.setVisibility(View.GONE);
            }
            viewHolder.tv_adapter_cotton_recommend_order_repetory.setText("存放仓库：" + item.getHyCkbmc());
            if (!TextUtils.isEmpty(item.getDistance())) {
                viewHolder.tv_adapter_cotton_recommend_order_repetory_distance.setText(DoubiUtils.hehe(item.getDistance()));
            } else {
                viewHolder.tv_adapter_cotton_recommend_order_repetory_distance.setText("");
            }
            viewHolder.tv_adapter_cotton_recommend_order_publish_time.setText("发布日期：" + item.getPublishDate());
        }
        return convertView;
    }

    public void clear() {
        list.clear();
    }

    public void addList(List<? extends MainStoreModel> allList) {
        list.addAll(allList);
    }

    class ViewHolder {
        LinearLayout ll_adapter_cotton_recommend_item;
        TextView tv_adapter_cotton_recommend_order_number, tv_adapter_cotton_recommend_order_detail, tv_adapter_cotton_recommend_order_price, tv_adapter_cotton_recommend_order_price_front, tv_adapter_cotton_recommend_order_price_back, tv_adapter_cotton_recommend_order_supplier,
                tv_adapter_cotton_recommend_order_red_paper, tv_adapter_cotton_recommend_order_repetory, tv_adapter_cotton_recommend_order_repetory_distance, tv_adapter_cotton_recommend_order_publish_time;
        ImageView iv_adapter_cotton_recommend_online;
    }
}
