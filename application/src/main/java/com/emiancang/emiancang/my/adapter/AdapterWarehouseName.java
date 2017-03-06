package com.emiancang.emiancang.my.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.bean.WarehouseNameEntity;
import com.emiancang.emiancang.user.UserManager;

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
public class AdapterWarehouseName extends BaseAdapter {

    List<WarehouseNameEntity> list = new ArrayList<>();
    List<String> mSelectList = new ArrayList<>();

    public void addList(List<? extends WarehouseNameEntity> list) {
        if (list != null) {
            this.list.addAll(list);
        }
    }

    public void setSelectList(List<String> list){
        this.mSelectList = list;
    }

    public void clear() {
        list.clear();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public WarehouseNameEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_warehouse_name_select, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        WarehouseNameEntity entity = list.get(position);
        viewHolder.enterpriseTypeSelectName.setText(entity.getHyCkbCkmc());
        viewHolder.select.setVisibility(View.GONE);
        for (int i = 0;i<mSelectList.size();i++){
            if(mSelectList.get(i).equals( entity.getHyCkbNm())){
                viewHolder.select.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.enterprise_type_select_name)
        TextView enterpriseTypeSelectName;
        @InjectView(R.id.select)
        ImageView select;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
