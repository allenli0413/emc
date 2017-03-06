package com.emiancang.emiancang.my.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emiancang.emiancang.App;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.bean.Dictionary;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;

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
public class AdapterEnterpriseType extends BaseAdapter {

    private Activity mActivity;

    public AdapterEnterpriseType(Activity activity) {
        this.mActivity = activity;
    }

    List<Dictionary> list = new ArrayList<>();

    public void addList(List<? extends Dictionary> list) {
        if (list != null) {
            Dictionary dictionary = new Dictionary();
            dictionary.setId("-1");
            dictionary.setDicValue("不限企业类型");
            dictionary.setDicKey("");
            this.list.add(dictionary);
            for (Dictionary d:list){
                this.list.add(d);
            }
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
    public Dictionary getItem(int position) {
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
        Dictionary entity = list.get(position);
        viewHolder.name.setText(entity.getDicValue());
        if (!TextUtils.isEmpty(SharedPrefsUtil.getValue(mActivity, SharedPrefsUtil.ENTERPRISETYPE, "")) && SharedPrefsUtil.getValue(mActivity, SharedPrefsUtil.ENTERPRISETYPE, "").equals(entity.getId()))
            viewHolder.name.setTextColor(App.getContext().getResources().getColor(R.color.green));
        else
            viewHolder.name.setTextColor(App.getContext().getResources().getColor(R.color.black));
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
