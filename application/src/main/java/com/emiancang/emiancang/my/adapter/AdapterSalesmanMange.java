package com.emiancang.emiancang.my.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emiancang.emiancang.base.TitleActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.zwyl.BaseListAdapter;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.bean.MoneyInfo;
import com.emiancang.emiancang.bean.OrbindSalesman;
import com.emiancang.emiancang.bean.SalesmanMangeEntity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.login.LoginActivity;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.user.UserManager;
import com.zwyl.view.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * @version V1.0
 * @Description: TODO
 * @date 16-9-1 下午3:17
 */
public class AdapterSalesmanMange extends BaseAdapter {

    List<SalesmanMangeEntity> list = new ArrayList<>();
    private TitleActivity activity;
    private AlertDialog mShowLoginDialog;

    public AdapterSalesmanMange(TitleActivity activity){
        this.activity = activity;
    }

    public void addList(List<? extends SalesmanMangeEntity> list) {
        if (list != null) {
            this.list.addAll(list);
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
    public SalesmanMangeEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        SliderView slideView = new SliderView(parent.getContext());
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_selesman_mange_item, parent, false);
        slideView.setContentView(convertView);
        ViewHolder viewHolder = new ViewHolder(convertView,slideView);
        viewHolder.deleteHolder = slideView.findViewById(R.id.holder);
        viewHolder.viewcontent = (LinearLayout) slideView.findViewById(R.id.view_content);
        viewHolder.mTvDelete = (TextView) slideView.findViewById(R.id.delete);
        viewHolder.slideView.shrink();
        SalesmanMangeEntity entity = list.get(position);
        if(!TextUtils.isEmpty(entity.getEYhtx()))
            viewHolder.userAvater.setImageURI(Uri.parse(entity.getEYhtx()));
        viewHolder.userName.setText(entity.getESjzcXm());
        viewHolder.userPhone.setText(""+entity.getESjzcSjh());
        viewHolder.deleteHolder.setOnClickListener(v -> relieve(""+entity.getESjzcNm(),position));
        return slideView;
    }

    private void relieve(String id,int position) {
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.activity_servicelist_dialog, null);
        TextView title_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_title_tv);
        TextView content_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_content_tv);
        TextView verify_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_verify_tv);
        TextView cancel_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_cancel_tv);
        title_tv.setText("解除绑定");
        content_tv.setText("是否要解除业务员姓名（电话号码）的与本公司的绑定？");
        cancel_tv.setText("解除绑定");
        verify_tv.setText("不解除");
        cancel_tv.setTextColor(activity.getResources().getColor(R.color.red));
        cancel_tv.setOnClickListener(v -> {
            ViewControl viewControl = ViewControlUtil.create2Dialog(activity);
            UserService api = ApiUtil.createDefaultApi(UserService.class);
            ApiUtil.doDefaultApi(api.rmOrbindSalesman("0",id, UserManager.getInstance().getUser().getESjzcHynm()), data -> {
                mShowLoginDialog.dismiss();
                switch (data.getBandCode()){
                    case "0":
                        activity.showToast("业务员绑定成功");
                        break;
                    case "1":
                        activity.showToast("业务员解绑成功");
                        list.remove(position);
                        notifyDataSetChanged();
                        break;
                    case "2":
                        activity.showToast("要绑定的用户不存在");
                        break;
                    case "3":
                        activity.showToast("要绑定的用户已经是其他企业的业务员，不能绑定再次绑定");
                        break;
                    case "4":
                        activity.showToast("不能解绑自己的账号");
                        break;
                }
            }, viewControl);
        });
        verify_tv.setOnClickListener(v -> mShowLoginDialog.dismiss());
        mShowLoginDialog = new AlertDialog.Builder(activity).setView(dialogView).create();
        mShowLoginDialog.show();
    }


    static class ViewHolder extends BaseListAdapter.ViewHolder {
        @InjectView(R.id.user_avater)
        SimpleDraweeView userAvater;
        @InjectView(R.id.user_name)
        TextView userName;
        @InjectView(R.id.user_phone)
        TextView userPhone;
        SliderView slideView;
        View deleteHolder;
        TextView mTvDelete;
        LinearLayout viewcontent;
        View itemView;


        ViewHolder(View view, SliderView slideView) {
            super(slideView);
            this.slideView = slideView;
            this.itemView = view;
            ButterKnife.inject(this, view);
        }
    }
}
