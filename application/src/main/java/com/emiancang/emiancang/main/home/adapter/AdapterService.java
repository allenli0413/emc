package com.emiancang.emiancang.main.home.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.bean.ServiceEntity;

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
public class AdapterService extends BaseAdapter {

    private List<ServiceEntity> list = new ArrayList<>();

    public AdapterService() {

    }

    public void addList(List<? extends ServiceEntity> list) {
        if (list != null) {
            this.list.addAll(list);
        }
    }

    public List<ServiceEntity> getList() {
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
    public ServiceEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_service, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        ServiceEntity entity = list.get(position);
        viewHolder.name.setText(entity.getCustomername());
        viewHolder.avater.setImageURI(Uri.parse(entity.getExtendsvalue()));
        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.avater)
        SimpleDraweeView avater;
        @InjectView(R.id.name)
        TextView name;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
