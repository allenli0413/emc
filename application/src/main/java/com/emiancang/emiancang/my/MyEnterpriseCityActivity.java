package com.emiancang.emiancang.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.litesuits.android.log.Log;
import com.mayigeek.frame.http.state.HttpError;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.bean.City;
import com.emiancang.emiancang.eventbean.EnterpriseAccountTransfer;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.my.adapter.AdapterEnterpriseLocation;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.Util;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import de.greenrobot.event.EventBus;
import rx.Observable;

/**
 * Created by yuanyueqing on 2017/1/4.
 */

public class MyEnterpriseCityActivity extends TitleActivity {
    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;

    private AdapterEnterpriseLocation mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_my_enterprise_type, null);
        setContentView(view);
        ButterKnife.inject(this);
        City city = (City) getIntent().getSerializableExtra("data");
        initHead();
        initView(city);
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("企业所在地", getResources().getColor(R.color.white));
    }

    private void initView(City city) {
        mAdapter = new AdapterEnterpriseLocation();
        listView.setAdapter(mAdapter);
        NestRefreshLayout nestRefreshLayout = (NestRefreshLayout) findViewById(R.id.nestRefreshLayout);
        ViewControl viewControl = ViewControlUtil.create2View(nestRefreshLayout);
        NestRefreshManager<City> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, (page, perPage) -> {
            UserService api = ApiUtil.createDefaultApi(UserService.class);
            return api.getCityList(city.getAreaId()+"").map(new HttpResultFunc<>());
        });
        nestRefreshManager.setPullLoadEnable(false);
        nestRefreshManager.setPullRefreshEnable(false);
        nestRefreshManager.setHttpError(e -> Log.i("test", e.toString()));
        nestRefreshManager.setCallBack((allList, currentList) -> {
            mAdapter.clear();
            mAdapter.addList(allList);
            mAdapter.notifyDataSetChanged();
        });
        nestRefreshManager.doApi();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            City entity = mAdapter.getItem(position-1);
            EventBus.getDefault().post(new EnterpriseAccountTransfer(entity.getAreaName(),EnterpriseAccountTransfer.ENTERPRISELOCATION,""+entity.getAreaId()));
            finish();
        });
    }
}
