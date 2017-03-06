package com.emiancang.emiancang.my.adapter;

import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.bean.SalesmanMangeEntity;
import com.emiancang.emiancang.bean.SalesmanMangeEntity;

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
public class AdapterBindingContactsSearch extends BaseAdapter {

    List<SalesmanMangeEntity> list = new ArrayList<>();

    public void addList(List<? extends SalesmanMangeEntity> list) {
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
    public SalesmanMangeEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_selesman_mange_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        SalesmanMangeEntity entity = list.get(position);
//        if(!TextUtils.isEmpty(entity.getUrl()))
//            viewHolder.userAvater.setImageURI(Uri.parse(entity.getUrl()));
        viewHolder.userName.setText(entity.getESjzcXm());
        viewHolder.userPhone.setText(entity.getESjzcSjh());
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.user_avater)
        SimpleDraweeView userAvater;
        @InjectView(R.id.user_name)
        TextView userName;
        @InjectView(R.id.user_phone)
        TextView userPhone;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
