package com.emiancang.emiancang.my.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.bean.ContactEntity;

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
public class AdapterBindingContacts extends BaseAdapter {

    List<ContactEntity> list = new ArrayList<>();

    public void addList(List<? extends ContactEntity> list) {
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
    public ContactEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_binding_contacts, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        ContactEntity entity = list.get(position);
        viewHolder.name.setText(entity.getName());
        viewHolder.phone.setText(entity.getPhone());
        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.name)
        TextView name;
        @InjectView(R.id.phone)
        TextView phone;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
