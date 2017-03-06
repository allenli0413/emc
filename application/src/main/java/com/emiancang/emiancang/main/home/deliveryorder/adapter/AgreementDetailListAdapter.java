package com.emiancang.emiancang.main.home.deliveryorder.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emiancang.emiancang.App;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.main.home.deliveryorder.model.CustomAgreementDetailModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Author: liyh
 * E-mail: liyh@zhiwyl.com
 * Phone: 15033990609
 * Date: 2017/2/22  Time: 18:06
 * Description:
 */

public class AgreementDetailListAdapter extends BaseAdapter {
    private List<CustomAgreementDetailModel> data = new ArrayList<>();

    public void clear() {
        data.clear();
    }

    public void addList(List<? extends CustomAgreementDetailModel> allList) {
        //        for (DeliveryOrderModel mainStoreModel : allList) {
        //            mainStoreModel.setSelected(false);
        //        }
        data.addAll(allList);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(App.getContext(), R.layout.item_agreement_detail, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CustomAgreementDetailModel model = data.get(position);
        holder.mTxtName.setText(model.name);
        holder.mTxtData.setText(model.data);
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.txt_name)
        TextView mTxtName;
        @InjectView(R.id.txt_data)
        TextView mTxtData;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
