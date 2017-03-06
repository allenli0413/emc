package com.emiancang.emiancang.my;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.emiancang.emiancang.eventbean.SalesmanBindTransfer;
import com.litesuits.android.log.Log;
import com.mayigeek.frame.http.state.HttpError;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.bean.SalesmanMangeEntity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.my.adapter.AdapterSalesmanMange;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.UserManager;
import com.zwyl.view.SilderListView;
import com.zwyl.view.SilderXListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import de.greenrobot.event.EventBus;
import rx.Observable;

/**
 * Created by 22310 on 2016/11/7.
 */

public class MySalesmanManageActivity extends TitleActivity {


    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;
    @InjectView(R.id.listView)
    SilderXListView listView;

    private AdapterSalesmanMange mAdapter;

    ViewControl viewControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_my_enterprise_type, null);
        setContentView(view);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
        initHead();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    public void onEventMainThread(SalesmanBindTransfer salesmanBindTransfer){
        initView();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("业务员管理", getResources().getColor(R.color.white));
        getHeadBar().showRightText();
        getHeadBar().setRightTitle("绑定", getResources().getColor(R.color.white));
        getHeadBar().setRightOnClickListner(v -> startActivity(createIntent(MySalesmanBindingActivity.class)));
        viewControl = ViewControlUtil.create2View(nestRefreshLayout,"您还没有绑定业务员，请绑定！","您还没有绑定业务员，请绑定！",R.drawable.icon_content_empty);
    }

    private void initView() {
        mAdapter = new AdapterSalesmanMange(this);
        listView.setAdapter(mAdapter);
        NestRefreshLayout nestRefreshLayout = (NestRefreshLayout) findViewById(R.id.nestRefreshLayout);
        NestRefreshManager<SalesmanMangeEntity> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, (page, perPage) -> {
            UserService api = ApiUtil.createDefaultApi(UserService.class);
            return api.getSalesmanListCustId(UserManager.getInstance().getUser().getESjzcHynm()).map(new HttpResultFunc<>());
        });
        nestRefreshManager.setPullLoadEnable(true);
        nestRefreshManager.setHttpError(e -> Log.i("test", e.toString()));
        nestRefreshManager.setCallBack((allList, currentList) -> {
            mAdapter.clear();
            mAdapter.addList(allList);
            mAdapter.notifyDataSetChanged();
        });
        nestRefreshManager.doApi();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
