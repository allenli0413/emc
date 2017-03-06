package com.emiancang.emiancang.main.home.findmoney.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.litesuits.android.log.Log;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.eventbean.FindMoneyPublishTransfer;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.main.home.findmoney.adapter.FindMoneyAdapter;
import com.emiancang.emiancang.main.home.findmoney.model.FindMoneyModel;
import com.emiancang.emiancang.main.home.findmoney.service.FindMoneySerivce;
import com.emiancang.emiancang.uitl.Util;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import de.greenrobot.event.EventBus;

/**
 * Created by yuanyueqing on 2016/11/4.
 */

public class FindMoneyActivity extends TitleActivity {

    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;

    FindMoneyAdapter adapter;

    ViewControl viewControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_money);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
        viewControl = ViewControlUtil.create2View(nestRefreshLayout, "没找到对应数据", "没找到对应数据", R.drawable.icon_content_empty);
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

    public void onEventMainThread(FindMoneyPublishTransfer findMoneyPublishTransfer) {
        initData();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("找资金", getResources().getColor(R.color.white));
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().showRightText();
        getHeadBar().setRightTitle("发布", getResources().getColor(R.color.white));
        getHeadBar().setRightOnClickListner(v -> startActivity(createIntent(FindMoneyPublishActivity.class)));
        getHeadBar().showLeftImage();
        getHeadBar().setLeftOnClickListner(v -> finish());
    }

    private void initData() {
        adapter = new FindMoneyAdapter(getActivity());
        listView.setAdapter(adapter);

        NestRefreshManager<FindMoneyModel> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, (page, perPage) -> {
            FindMoneySerivce api = ApiUtil.createDefaultApi(FindMoneySerivce.class);
            return api.list("" + (page + 1), "" + perPage).map(new HttpResultFunc<>());
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
            Intent intent = new Intent(this, FindMoneyDetailActivity.class);
            intent.putExtra("data", adapter.getItem(position - 1));
            startActivity(intent);
        });
    }

}
