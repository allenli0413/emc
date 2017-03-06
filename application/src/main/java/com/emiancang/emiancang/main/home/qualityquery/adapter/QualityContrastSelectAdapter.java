package com.emiancang.emiancang.main.home.qualityquery.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.main.home.qualityquery.model.QualityContrastSelectModel;
import com.emiancang.emiancang.store.model.MainStoreModel;
import com.emiancang.emiancang.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanyueqing on 2016/11/11.
 */

public class QualityContrastSelectAdapter extends BaseAdapter {

    List<MainStoreModel> list = new ArrayList<>();

    public void clear(){
        list.clear();
    }

    public void addList(){

    }

    public void setList(List<MainStoreModel> list) {
        this.list = list;
    }

    public List<MainStoreModel> getList() {
        return list;
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_quality_contrast_select, parent, false);
            viewHolder.iv_adapter_quality_contrast_select = (ImageView) convertView.findViewById(R.id.iv_adapter_quality_contrast_select);
            viewHolder.tv_adapter_quality_contrast_select_title = (TextView) convertView.findViewById(R.id.tv_adapter_quality_contrast_select_title);
            viewHolder.tv_adapter_quality_contrast_select_level = (TextView) convertView.findViewById(R.id.tv_adapter_quality_contrast_select_level);
            viewHolder.tv_adapter_quality_contrast_select_length = (TextView) convertView.findViewById(R.id.tv_adapter_quality_contrast_select_length);
            viewHolder.tv_adapter_quality_contrast_select_za = (TextView) convertView.findViewById(R.id.tv_adapter_quality_contrast_select_za);
            viewHolder.tv_adapter_quality_contrast_select_ma = (TextView) convertView.findViewById(R.id.tv_adapter_quality_contrast_select_ma);
            viewHolder.tv_adapter_quality_contrast_select_chao = (TextView) convertView.findViewById(R.id.tv_adapter_quality_contrast_select_chao);
            viewHolder.tv_adapter_quality_contrast_select_strength = (TextView) convertView.findViewById(R.id.tv_adapter_quality_contrast_select_strength);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(item.isSelected()){
            viewHolder.iv_adapter_quality_contrast_select.setVisibility(View.VISIBLE);
        }else {
            viewHolder.iv_adapter_quality_contrast_select.setVisibility(View.GONE);
        }

        viewHolder.tv_adapter_quality_contrast_select_title.setText(item.getGcmgyMhph() + "（" + item.getHyJysjZbs() + "）");
        viewHolder.tv_adapter_quality_contrast_select_level.setText(item.getZtYsj());
        viewHolder.tv_adapter_quality_contrast_select_length.setText(item.getHyJysjCdz());
        viewHolder.tv_adapter_quality_contrast_select_za.setText(item.getHyJysjHzl());
        viewHolder.tv_adapter_quality_contrast_select_ma.setText(item.getMklz());
        viewHolder.tv_adapter_quality_contrast_select_chao.setText(item.getHyJysjHcl());
        viewHolder.tv_adapter_quality_contrast_select_strength.setText(item.getHyJysjDlbqd());

        return convertView;
    }

    public void addList(List<? extends MainStoreModel> allList) {
        for (MainStoreModel mainStoreModel : allList) {
            mainStoreModel.setSelected(false);
        }
        list.addAll(allList);
    }

    class ViewHolder{
        ImageView iv_adapter_quality_contrast_select;
        TextView tv_adapter_quality_contrast_select_title, tv_adapter_quality_contrast_select_level,
                tv_adapter_quality_contrast_select_length, tv_adapter_quality_contrast_select_za, tv_adapter_quality_contrast_select_ma, tv_adapter_quality_contrast_select_chao,
                tv_adapter_quality_contrast_select_strength;
    }
}
