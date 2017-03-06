package com.emiancang.emiancang.my.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emiancang.emiancang.uitl.DoubiUtils;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.zwyl.BaseListAdapter;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.bean.NearEnterprise;
import com.emiancang.emiancang.bean.ResEntity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.service.UserService;
import com.zwyl.view.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.emiancang.emiancang.update.ZwyCommon.showToast;


/**
 * @version V1.0
 * @Description: TODO
 * @date 16-9-1 下午3:17
 */
public class AdapterNearby extends BaseAdapter {

    private List<NearEnterprise> list = new ArrayList<>();
    private boolean mEnabled = true;
    private Activity mActivity;

    public AdapterNearby(Activity activity){
        this.mActivity = activity;
    }

    public void addList(List<? extends NearEnterprise> list) {
        if (list != null) {
            this.list.addAll(list);
        }
    }

    public List<NearEnterprise> getList(){
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
        return mEnabled;
    }

    public void setEnabled(boolean b){
        mEnabled = b;
    }

    @Override
    public NearEnterprise getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_collect_cotton_enterprise, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        NearEnterprise entity = list.get(position);
        viewHolder.enterpriseName.setText(entity.getCustName());
        viewHolder.enterpriseSite.setText(entity.getCompanyAddress());
        viewHolder.enterpriseDistance.setText(DoubiUtils.hehe(entity.getDistance()));
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.enterprise_name)
        TextView enterpriseName;
        @InjectView(R.id.enterprise_distance)
        TextView enterpriseDistance;
        @InjectView(R.id.enterprise_site)
        TextView enterpriseSite;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
