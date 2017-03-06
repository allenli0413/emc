package com.emiancang.emiancang.my.adapter;

import android.app.Activity;
import android.text.TextUtils;
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
import com.zwyl.BaseListAdapter;
import com.emiancang.emiancang.App;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.bean.CollectCottonEnterpriseEntity;
import com.emiancang.emiancang.bean.CollectCottonEntity;
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

import static com.emiancang.emiancang.R.id.tv_adapter_cotton_recommend_order_number;
import static com.emiancang.emiancang.update.ZwyCommon.showToast;


/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: TODO
 * @date 16-9-1 下午3:17
 */
public class AdapterCollectCotton extends BaseAdapter {

    private List<CollectCottonEntity> list = new ArrayList<>();
    private boolean mEnabled = true;
    private Activity mActivity;

    public AdapterCollectCotton(Activity activity) {
        this.mActivity = activity;
    }

    public void addList(List<? extends CollectCottonEntity> list) {
        if (list != null) {
            this.list.addAll(list);
        }
    }

    public List<CollectCottonEntity> getList() {
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

    public void setEnabled(boolean b) {
        mEnabled = b;
    }

    @Override
    public CollectCottonEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        SliderView slideView = new SliderView(parent.getContext());
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_collect_cotton, parent, false);
        slideView.setContentView(convertView);
        ViewHolder viewHolder = new ViewHolder(convertView,slideView);
        viewHolder.deleteHolder = slideView.findViewById(R.id.holder);
        viewHolder.viewcontent = (LinearLayout) slideView.findViewById(R.id.view_content);
        viewHolder.mTvDelete = (TextView) slideView.findViewById(R.id.delete);
        viewHolder.mTvDelete.setText("取消收藏");
        viewHolder.slideView.shrink();
        CollectCottonEntity entity = list.get(position);
        viewHolder.tvAdapterCottonRecommendOrderNumber.setText(entity.getHyJysjPh()+"("+entity.getHyJysjZbs()+")");
        viewHolder.tvAdapterCottonRecommendOrderDetail.setText(entity.getHyJysjYsj()+" 长度："+entity.getHyJysjCdz()+" 强力："+entity.getHyJysjDlbqd()+" 马值："+entity.getHyJysjMklz());
        viewHolder.tvAdapterCottonRecommendOrderPrice.setText(""+entity.getProductPrice());
        viewHolder.tvAdapterCottonRecommendOrderSupplier.setText("供货商："+entity.getCustName());
        viewHolder.tvAdapterCottonRecommendOrderRepetory.setText("存放仓库："+entity.getHyCkbmc());
        viewHolder.tvAdapterCottonRecommendOrderPublishTime.setText("发布日期："+entity.getPublishDate());
        viewHolder.tvAdapterCottonRecommendOrderRepetoryDistance.setText(DoubiUtils.hehe(entity.getDistance()));
        if(TextUtils.isEmpty(entity.getESpbsHbje()))
            viewHolder.tvAdapterCottonRecommendOrderRedPaper.setVisibility(View.GONE);
        else
            viewHolder.tvAdapterCottonRecommendOrderRedPaper.setVisibility(View.VISIBLE);
        viewHolder.tvAdapterCottonRecommendOrderRedPaper.setText(entity.getESpbsHbje());
        viewHolder.deleteHolder.setOnClickListener(v -> {
            ViewControl viewControl = ViewControlUtil.create2Dialog(mActivity);
            UserService api = ApiUtil.createDefaultApi(UserService.class);
            ApiUtil.doDefaultApi(api.cancelCollect("1",""+entity.getHyGzcpNm()), resEntity -> {
                showToast("取消成功");
                list.remove(position);
                notifyDataSetChanged();
            }, viewControl);
        });
        return slideView;
    }

    static class ViewHolder extends BaseListAdapter.ViewHolder{
        @InjectView(tv_adapter_cotton_recommend_order_number)
        TextView tvAdapterCottonRecommendOrderNumber;
        @InjectView(R.id.tv_adapter_cotton_recommend_order_detail)
        TextView tvAdapterCottonRecommendOrderDetail;
        @InjectView(R.id.tv_adapter_cotton_recommend_order_price)
        TextView tvAdapterCottonRecommendOrderPrice;
        @InjectView(R.id.tv_adapter_cotton_recommend_order_supplier)
        TextView tvAdapterCottonRecommendOrderSupplier;
        @InjectView(R.id.tv_adapter_cotton_recommend_order_red_paper)
        TextView tvAdapterCottonRecommendOrderRedPaper;
        @InjectView(R.id.tv_adapter_cotton_recommend_order_repetory)
        TextView tvAdapterCottonRecommendOrderRepetory;
        @InjectView(R.id.tv_adapter_cotton_recommend_order_repetory_distance)
        TextView tvAdapterCottonRecommendOrderRepetoryDistance;
        @InjectView(R.id.tv_adapter_cotton_recommend_order_publish_time)
        TextView tvAdapterCottonRecommendOrderPublishTime;

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
