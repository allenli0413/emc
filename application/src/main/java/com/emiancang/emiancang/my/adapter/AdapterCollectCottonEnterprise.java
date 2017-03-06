package com.emiancang.emiancang.my.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.emiancang.emiancang.uitl.DoubiUtils;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.zwyl.App;
import com.zwyl.BaseListAdapter;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.bean.CollectCottonEnterpriseEntity;
import com.emiancang.emiancang.bean.ResEntity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;
import com.zwyl.view.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static butterknife.ButterKnife.Finder.ACTIVITY;
import static com.emiancang.emiancang.update.ZwyCommon.showToast;


/**
 * @version V1.0
 * @Description: TODO
 * @date 16-9-1 下午3:17
 */
public class AdapterCollectCottonEnterprise extends BaseAdapter {

    private List<CollectCottonEnterpriseEntity> list = new ArrayList<>();
    private boolean mEnabled = true;
    private Activity mActivity;

    public AdapterCollectCottonEnterprise(Activity activity){
        this.mActivity = activity;
    }

    public void addList(List<? extends CollectCottonEnterpriseEntity> list) {
        if (list != null) {
            this.list.addAll(list);
        }
    }

    public List<CollectCottonEnterpriseEntity> getList(){
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
    public CollectCottonEnterpriseEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        SliderView slideView = new SliderView(parent.getContext());
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_collect_cotton_enterprise, parent, false);
        slideView.setContentView(convertView);
        ViewHolder viewHolder = new ViewHolder(convertView,slideView);
        viewHolder.deleteHolder = slideView.findViewById(R.id.holder);
        viewHolder.viewcontent = (LinearLayout) slideView.findViewById(R.id.view_content);
        viewHolder.mTvDelete = (TextView) slideView.findViewById(R.id.delete);
        viewHolder.mTvDelete.setText("取消收藏");
        viewHolder.slideView.shrink();
        CollectCottonEnterpriseEntity entity = list.get(position);
        viewHolder.enterpriseName.setText(entity.getCustName());
        viewHolder.enterpriseSite.setText(entity.getCompanyAddress());
        viewHolder.enterpriseDistance.setText(DoubiUtils.hehe(entity.getDistance()));
        viewHolder.deleteHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewControl viewControl = ViewControlUtil.create2Dialog(mActivity);
                UserService api = ApiUtil.createDefaultApi(UserService.class);
                ApiUtil.doDefaultApi(api.cancelCollect("2",entity.getHyGzqyNm()), new HttpSucess<ResEntity>() {
                    @Override
                    public void onSucess(ResEntity resEntity) {
                        showToast("取消成功");
                        list.remove(position);
                        notifyDataSetChanged();
                    }
                }, viewControl);
            }
        });
        return slideView;
    }

    static class ViewHolder extends BaseListAdapter.ViewHolder{
        @InjectView(R.id.enterprise_name)
        TextView enterpriseName;
        @InjectView(R.id.enterprise_distance)
        TextView enterpriseDistance;
        @InjectView(R.id.enterprise_site)
        TextView enterpriseSite;

        SliderView slideView;
        View deleteHolder;
        TextView mTvDelete;
        LinearLayout viewcontent;
        View itemView;

        ViewHolder(View view,SliderView slideView) {
            super(slideView);
            this.slideView = slideView;
            this.itemView = view;
            ButterKnife.inject(this, view);
        }
    }
}
