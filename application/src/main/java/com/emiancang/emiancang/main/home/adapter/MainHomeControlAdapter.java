package com.emiancang.emiancang.main.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.main.home.model.Category;
import com.emiancang.emiancang.main.home.model.MainHomeControlModel;

import java.util.List;

/**
 * Created by yuanyueqing on 2017/2/22.
 */

public class MainHomeControlAdapter extends BaseAdapter {

    List<MainHomeControlModel> list;
    Context mContext;

    public MainHomeControlAdapter(List<MainHomeControlModel> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public MainHomeControlModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainHomeControlModel item = getItem(position);
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_main_home_control,
                    parent, false);
            viewHolder.ll_adapter_main_home_control = (LinearLayout) convertView.findViewById(R.id.ll_adapter_main_home_control);
            viewHolder.iv_adapter_main_home_control_icon = (ImageView) convertView.findViewById(R.id.iv_adapter_main_home_control_icon);
            viewHolder.tv_adapter_main_home_control_name = (TextView) convertView.findViewById(R.id.tv_adapter_main_home_control_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) viewHolder.ll_adapter_main_home_control.getLayoutParams();
        lp.width = width/5;
        viewHolder.ll_adapter_main_home_control.setLayoutParams(lp);

        viewHolder.tv_adapter_main_home_control_name.setText(item.getName());
        if(item.getName().equals(Category.QUALITYQUERY.getName()))
            viewHolder.iv_adapter_main_home_control_icon.setImageResource(R.drawable.quality_query);
        if(item.getName().equals(Category.LOGISTICSQUERY.getName()))
            viewHolder.iv_adapter_main_home_control_icon.setImageResource(R.drawable.logistics_query);
        if(item.getName().equals(Category.FINDMONEY.getName()))
            viewHolder.iv_adapter_main_home_control_icon.setImageResource(R.drawable.find_money);
        if(item.getName().equals(Category.BUSINESSHELP.getName()))
            viewHolder.iv_adapter_main_home_control_icon.setImageResource(R.drawable.business_help);
        if(item.getName().equals(Category.BILOFLADING.getName()))
            viewHolder.iv_adapter_main_home_control_icon.setImageResource(R.drawable.bil_of_lading);
        if(item.getName().equals(Category.DESCRIBECOTTON.getName()))
            viewHolder.iv_adapter_main_home_control_icon.setImageResource(R.drawable.describe_cotton);
        if(item.getName().equals(Category.GREENCARDCOTTON.getName()))
            viewHolder.iv_adapter_main_home_control_icon.setImageResource(R.drawable.green_card_cotton);

        return convertView;
    }

    class ViewHolder{
        LinearLayout ll_adapter_main_home_control;
        ImageView iv_adapter_main_home_control_icon;
        TextView tv_adapter_main_home_control_name;
    }
}
