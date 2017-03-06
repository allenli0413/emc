package com.emiancang.emiancang.my.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.bean.WarnEntity;

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
public class AdapterWarn extends BaseAdapter {

    private List<WarnEntity> list;

    public AdapterWarn() {
        list = new ArrayList<>();
    }

    public void addList(List<? extends WarnEntity> list) {
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
    public WarnEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_warn, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        WarnEntity entity = list.get(position);
        viewHolder.title.setText(entity.getTitle());
        viewHolder.date.setText(entity.getCreateTime());
        viewHolder.content.setText(entity.getSysContent());
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.title)
        TextView title;
        @InjectView(R.id.date)
        TextView date;
        @InjectView(R.id.content)
        TextView content;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
