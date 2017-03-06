package com.emiancang.emiancang.main.home.logisticsquery.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.litesuits.android.log.Log;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.eventbean.LogisticsQueryPublishTransfer;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.main.home.logisticsquery.adapter.MineLogisticsQueryAdapter;
import com.emiancang.emiancang.main.home.logisticsquery.model.LogisticsQueryModel;
import com.emiancang.emiancang.main.home.logisticsquery.service.MineLogisticsQueryService;
import com.emiancang.emiancang.uitl.Util;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import de.greenrobot.event.EventBus;

/**
 * Created by yuanyueqing on 2016/11/9.
 */

public class MineLogisticsQueryActivity extends TitleActivity {

    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;

    MineLogisticsQueryAdapter adapter;

    ViewControl viewControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_query);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
        viewControl = ViewControlUtil.create2View(nestRefreshLayout, "没找到对应数据", "没找到对应数据",R.drawable.icon_content_empty);
        initHead();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(LogisticsQueryPublishTransfer logisticsQueryPublishTransfer){
        initData();
    }

    private void initData() {
        adapter = new MineLogisticsQueryAdapter();
        listView.setAdapter(adapter);

        NestRefreshManager<LogisticsQueryModel> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, (page, perPage) -> {
            MineLogisticsQueryService api = ApiUtil.createDefaultApi(MineLogisticsQueryService.class);
            return api.list("" + (page + 1), "" + perPage, "1").map(new HttpResultFunc<>());
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

        });
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(),R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("我的", getResources().getColor(R.color.white));
        getHeadBar().showLeftImage();
        getHeadBar().setLeftOnClickListner(v -> finish());
        getHeadBar().showRightText();
        getHeadBar().setRightTitle("发布", getResources().getColor(R.color.white));
        getHeadBar().setRightOnClickListner(v -> startActivity(createIntent(LogisticsQueryPublishActivity.class)));
    }
}
