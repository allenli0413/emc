package com.emiancang.emiancang.my.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.emiancang.emiancang.App;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.bean.IndentDetailsTailEntity;

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
public class AdapterIndentDetailsTail extends BaseAdapter {

    private List<IndentDetailsTailEntity> list = new ArrayList<>();

    public AdapterIndentDetailsTail() {
    }

    public void addList(List<? extends IndentDetailsTailEntity> list) {
        if (list != null) {
            this.list.addAll(list);
        }
    }

    public List<IndentDetailsTailEntity> getList() {
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
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public IndentDetailsTailEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_indent_details_tail, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        IndentDetailsTailEntity entity = list.get(position);
        if (entity.isArrive()) {
            viewHolder.lineUp.setBackgroundColor(App.getContext().getResources().getColor(R.color.green));
            viewHolder.lineDown.setBackgroundColor(App.getContext().getResources().getColor(R.color.green));
            viewHolder.dot.setBackgroundColor(App.getContext().getResources().getColor(R.color.green));
            viewHolder.title.setText(entity.getName());
            viewHolder.title.setTextColor(App.getContext().getResources().getColor(R.color.c4));
            viewHolder.date.setText(entity.getDate());
            viewHolder.date.setTextColor(App.getContext().getResources().getColor(R.color.c4));
        } else {
            viewHolder.lineUp.setBackgroundColor(App.getContext().getResources().getColor(R.color.c6));
            viewHolder.lineDown.setBackgroundColor(App.getContext().getResources().getColor(R.color.c6));
            viewHolder.dot.setBackgroundColor(App.getContext().getResources().getColor(R.color.c6));
            viewHolder.title.setText(entity.getName());
            viewHolder.title.setTextColor(App.getContext().getResources().getColor(R.color.c6));
            viewHolder.date.setText("");
        }
        if (position == list.size() - 1) {
            viewHolder.lineDown.setVisibility(View.GONE);
        }
        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.line_up)
        ImageView lineUp;
        @InjectView(R.id.line_down)
        ImageView lineDown;
        @InjectView(R.id.dot)
        ImageView dot;
        @InjectView(R.id.title)
        TextView title;
        @InjectView(R.id.date)
        TextView date;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
