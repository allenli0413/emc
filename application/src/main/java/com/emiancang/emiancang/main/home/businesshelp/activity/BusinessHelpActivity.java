package com.emiancang.emiancang.main.home.businesshelp.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.litesuits.android.log.Log;
import com.mayigeek.frame.http.state.HttpError;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.login.LoginActivity;
import com.emiancang.emiancang.main.home.businesshelp.adapter.BusinessHelpAdapter;
import com.emiancang.emiancang.main.home.businesshelp.model.BusinessHelpModel;
import com.emiancang.emiancang.main.home.businesshelp.service.BusinessHelpService;
import com.emiancang.emiancang.service.EnterpriseTypeService;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.UserManager;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import rx.Observable;

/**
 * Created by yuanyueqing on 2016/11/4.
 */

public class BusinessHelpActivity extends TitleActivity {

    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;

    BusinessHelpAdapter adapter;

    private AlertDialog mShowLoginDialog;

    ViewControl viewControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_help);
        ButterKnife.inject(this);
        viewControl = ViewControlUtil.create2View(nestRefreshLayout, "加载失败", "尚无已发布需求", R.drawable.icon_content_empty);
        initHead();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        adapter = new BusinessHelpAdapter(getActivity());
        listView.setAdapter(adapter);
        NestRefreshManager<BusinessHelpModel> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, (page, perPage) -> {
            BusinessHelpService api = ApiUtil.createDefaultApi(BusinessHelpService.class);
            return api.list("" + (page + 1), "" + perPage, "0").map(new HttpResultFunc<>());
        });
        nestRefreshManager.setPullLoadEnable(true);
        nestRefreshManager.setHttpError(e -> Log.i("test", e.toString()));
        nestRefreshManager.setCallBack((allList, currentList) -> {
            adapter.clear();
            adapter.addList(allList);
            adapter.notifyDataSetChanged();
        });
        nestRefreshManager.doApi();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getActivity(), BusinessHelpDetailActivity.class);
            intent.putExtra("model", adapter.getItem(position));
            startActivity(intent);
        });
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(),R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("买卖帮", getResources().getColor(R.color.white));
        getHeadBar().showLeftImage();
        getHeadBar().setLeftOnClickListner(v -> finish());
        getHeadBar().showRightText();
        getHeadBar().setRightTitle("我的", getResources().getColor(R.color.white));
        getHeadBar().setRightOnClickListner(v -> {
            if(UserManager.getInstance().isLogin()) {
                startActivity(createIntent(MineBusinessHelpActivity.class));
            }else{
                isLogin();
            }
        });
    }

    //判断用户是否登陆
    private void isLogin() {
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_servicelist_dialog, null);
        TextView title_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_title_tv);
        TextView content_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_content_tv);
        TextView verify_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_verify_tv);
        TextView cancel_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_cancel_tv);
        title_tv.setText("提示");
        content_tv.setText("未登录状态，没有权限查看该内容，是否选择登录？");
        verify_tv.setOnClickListener(v -> {
            //登录页面
            startActivity(createIntent(LoginActivity.class));
            mShowLoginDialog.dismiss();
        });
        cancel_tv.setOnClickListener(v -> mShowLoginDialog.dismiss());
        mShowLoginDialog = new AlertDialog.Builder(getActivity()).setView(dialogView).create();
        mShowLoginDialog.show();
    }
}
