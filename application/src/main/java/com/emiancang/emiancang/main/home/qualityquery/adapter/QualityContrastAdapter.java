package com.emiancang.emiancang.main.home.qualityquery.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.main.home.qualityquery.model.QualityContrastModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanyueqing on 2016/11/12.
 */

public class QualityContrastAdapter extends BaseAdapter {

    private final int ITEM_TITLE = 0;
    private final int ITEM_CONTENT = 1;

    List<QualityContrastModel> list = new ArrayList<>();

    public QualityContrastAdapter(List<QualityContrastModel> list) {
        this.list = list;
    }

    public void clear(){
        list.clear();
    }

    public void addList(){

    }

    public List<QualityContrastModel> getList() {
        return list;
    }

    public void setList(List<QualityContrastModel> list) {
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
//        switch (position){
//            case 0:
//                return ITEM_TITLE;
//            case 11:
//                return ITEM_TITLE;
//            case 16:
//                return ITEM_TITLE;
//            case 22:
//                return ITEM_TITLE;
//            case 28:
//                return ITEM_TITLE;
//            case 32:
//                return ITEM_TITLE;
//            case 36:
//                return ITEM_TITLE;
//            case 39:
//                return ITEM_TITLE;
//            case 41:
//                return ITEM_TITLE;
//            default:
//                return ITEM_CONTENT;
//        }
        if(TextUtils.isEmpty(list.get(position).getTitle_another())){
            return ITEM_TITLE;
        }else{
            return ITEM_CONTENT;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QualityContrastModel item = list.get(position);
        ViewHolder1 viewHolder1 = null;
        ViewHolder2 viewHolder2 = null;
        if(convertView == null){
            switch (getItemViewType(position)){
                case ITEM_TITLE:
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_quality_contrast_title, parent, false);
                    viewHolder1 = new ViewHolder1();
                    viewHolder1.tv_adapter_quality_contrast_title = (TextView) convertView.findViewById(R.id.tv_adapter_quality_contrast_title);
                    convertView.setTag(viewHolder1);
                    break;
                case ITEM_CONTENT:
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_quality_contrast_content, parent, false);
                    viewHolder2 = new ViewHolder2();
                    viewHolder2.tv_adapter_quality_contrast_content_title_first = (TextView) convertView.findViewById(R.id.tv_adapter_quality_contrast_content_title_first);
                    viewHolder2.tv_adapter_quality_contrast_content_content_first = (TextView) convertView.findViewById(R.id.tv_adapter_quality_contrast_content_content_first);
                    viewHolder2.tv_adapter_quality_contrast_content_title_second = (TextView) convertView.findViewById(R.id.tv_adapter_quality_contrast_content_title_second);
                    viewHolder2.tv_adapter_quality_contrast_content_content_second = (TextView) convertView.findViewById(R.id.tv_adapter_quality_contrast_content_content_second);
                    convertView.setTag(viewHolder2);
                    break;
            }
        }else{
            switch (getItemViewType(position)){
                case ITEM_TITLE:
                    viewHolder1 = (ViewHolder1) convertView.getTag();
                    break;
                case ITEM_CONTENT:
                    viewHolder2 = (ViewHolder2) convertView.getTag();
                    break;
            }
        }

        switch (getItemViewType(position)){
            case ITEM_TITLE:
                viewHolder1.tv_adapter_quality_contrast_title.setText(item.getTitle());
                break;
            case ITEM_CONTENT:
                viewHolder2.tv_adapter_quality_contrast_content_title_first.setText(item.getTitle());
                viewHolder2.tv_adapter_quality_contrast_content_title_second.setText(item.getTitle_another());
                viewHolder2.tv_adapter_quality_contrast_content_content_first.setText(item.getContent());
                viewHolder2.tv_adapter_quality_contrast_content_content_second.setText(item.getContent_another());
                break;
        }

        return convertView;
    }

    class ViewHolder1{
        TextView tv_adapter_quality_contrast_title;
    }

    class ViewHolder2{
        TextView tv_adapter_quality_contrast_content_title_first,
                tv_adapter_quality_contrast_content_content_first,
                tv_adapter_quality_contrast_content_title_second,
                tv_adapter_quality_contrast_content_content_second;
    }
}
