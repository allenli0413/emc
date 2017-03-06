package com.emiancang.emiancang.main.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.litesuits.android.log.Log;
import com.mayigeek.frame.http.state.HttpError;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.bean.ServiceEntity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.hx.activity.ChatActivity;
import com.emiancang.emiancang.hx.activity.ConversationActivity;
import com.emiancang.emiancang.main.home.adapter.AdapterService;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import rx.Observable;

/**
 * Created by yuanyueqing on 2016/11/4.
 */

public class ServiceActivity extends TitleActivity {

    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;

    private AdapterService mAdapter= new AdapterService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.inject(this);
        initHead();
        initView();
    }

    private void initView() {
        listView.setAdapter(mAdapter);
        ViewControl viewControl = ViewControlUtil.create2View(nestRefreshLayout,"暂时还没有客服人员，请稍后再试","暂时还没有客服人员，请稍后再试",R.drawable.icon_content_empty);
        NestRefreshManager<ServiceEntity> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, new NestRefreshManager.CreateApi<ServiceEntity>() {
            @Override
            public Observable<List<ServiceEntity>> run(int page, int perPage) {
                UserService api = ApiUtil.createDefaultApi(UserService.class);
                return api.getCustomServiceList("1").map(new HttpResultFunc<>());
            }
        });
        nestRefreshManager.setPullLoadEnable(true);
        nestRefreshManager.setHttpError(e -> Log.i("test", e.toString()));
        nestRefreshManager.setCallBack((allList, currentList) -> {
            mAdapter.clear();
            mAdapter.addList(allList);
            mAdapter.notifyDataSetChanged();
        });
        nestRefreshManager.doApi();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            // 进入聊天页面
            Intent intent = new Intent(ServiceActivity.this, ChatActivity.class);
            intent.putExtra("username", mAdapter.getItem(position).getCustomername());
            intent.putExtra("key",mAdapter.getItem(position).getImusername());
            intent.putExtra("flag",0);
            startActivity(intent);
        });
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("客服", getResources().getColor(R.color.white));
    }

}
