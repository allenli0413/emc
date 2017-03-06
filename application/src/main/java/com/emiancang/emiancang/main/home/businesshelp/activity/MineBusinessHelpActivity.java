package com.emiancang.emiancang.main.home.businesshelp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.litesuits.android.log.Log;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.eventbean.BusinessHelpPublishTransfer;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.main.home.businesshelp.adapter.MineBusinessHelpAdapter;
import com.emiancang.emiancang.main.home.businesshelp.model.BusinessHelpModel;
import com.emiancang.emiancang.main.home.businesshelp.model.MineBusinessHelpModel;
import com.emiancang.emiancang.main.home.businesshelp.service.MineBusinessHelpService;
import com.emiancang.emiancang.uitl.Util;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import de.greenrobot.event.EventBus;

/**
 * Created by yuanyueqing on 2016/11/8.
 */

public class MineBusinessHelpActivity extends TitleActivity {

    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;

    MineBusinessHelpAdapter adapter;

    ViewControl viewControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_business_help);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
        viewControl = ViewControlUtil.create2View(nestRefreshLayout, "加载失败", "您尚未发布需求", R.drawable.icon_content_empty);
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

    public void onEventMainThread(BusinessHelpPublishTransfer businessHelpPublishTransfer){
        initData();
    }

    private void initData() {
        adapter = new MineBusinessHelpAdapter();
        listView.setAdapter(adapter);
        NestRefreshManager<BusinessHelpModel> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, (page, perPage) -> {
            MineBusinessHelpService api = ApiUtil.createDefaultApi(MineBusinessHelpService.class);
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
            Intent intent = new Intent(getActivity(), MineBusinessHelpDetailActivity.class);
            intent.putExtra("data", adapter.getList().get(position));
            startActivity(intent);
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
        getHeadBar().setRightOnClickListner(v -> startActivity(createIntent(BusinessHelpPublishActivity.class)));
    }
}
