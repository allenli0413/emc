package com.emiancang.emiancang.my.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.bean.City;

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
public class AdapterEnterpriseLocation extends BaseAdapter {

    List<City> list = new ArrayList<>();

    public void addList(List<? extends City> list) {
        if (list != null) {
            this.list.addAll(list);
        }
    }

    public void clear() {
        list.clear();
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public City getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_enterprise_type_select, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        City entity = list.get(position);
        viewHolder.name.setText(entity.getAreaName());
//        if(!TextUtils.isEmpty(UserManager.getInstance().getUser().getEnterprise_location_id()) && UserManager.getInstance().getUser().getEnterprise_location_id().equals(entity.getId()))
//            viewHolder.enterpriseTypeSelectSelect.setVisibility(View.VISIBLE);
//        else
//            viewHolder.enterpriseTypeSelectSelect.setVisibility(View.GONE);
        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.name)
        TextView name;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
