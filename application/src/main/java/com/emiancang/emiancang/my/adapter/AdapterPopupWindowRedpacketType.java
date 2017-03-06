package com.emiancang.emiancang.my.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emiancang.emiancang.App;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.bean.RedPacketPopupWindowTypeEntity;

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
public class AdapterPopupWindowRedpacketType extends BaseAdapter {

    private List<RedPacketPopupWindowTypeEntity> list;
    private String mTypeSelect = "";

    public AdapterPopupWindowRedpacketType() {
        list = new ArrayList<>();
    }

    public void addList(List<? extends RedPacketPopupWindowTypeEntity> list) {
        if (list != null) {
            this.list.addAll(list);
        }
    }

    public void setmTypeSelect(String i){
        this.mTypeSelect = i;
    }

    public void setList(List<RedPacketPopupWindowTypeEntity> list) {
        this.list = list;
    }

    public List<RedPacketPopupWindowTypeEntity> getList() {
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
    public RedPacketPopupWindowTypeEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_red_packet_popupwindow_type, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        RedPacketPopupWindowTypeEntity entity = list.get(position);
        viewHolder.name.setText(entity.getDicValue());
        if(!TextUtils.isEmpty(mTypeSelect) && mTypeSelect.equals(entity.getDicKey())){
            viewHolder.name.setTextColor(App.getContext().getResources().getColor(R.color.green));
        }else{
            viewHolder.name.setTextColor(App.getContext().getResources().getColor(R.color.black));
        }
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
