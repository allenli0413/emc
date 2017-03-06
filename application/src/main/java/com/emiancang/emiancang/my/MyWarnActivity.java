package com.emiancang.emiancang.my;

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
import com.emiancang.emiancang.bean.WarnEntity;
import com.emiancang.emiancang.eventbean.SYSMSGTransfer;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.my.adapter.AdapterRedpacket;
import com.emiancang.emiancang.my.adapter.AdapterWarn;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import de.greenrobot.event.EventBus;
import rx.Observable;

/**
 * Created by 22310 on 2016/11/14.
 */

public class MyWarnActivity extends TitleActivity {

    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;

    private AdapterWarn mAdapter = new AdapterWarn();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_warn, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();

    }
    private void initView() {
        listView.setAdapter(mAdapter);
        ViewControl viewControl = ViewControlUtil.create2View(nestRefreshLayout,"没有系统消息哦！","没有系统消息哦！",R.drawable.icon_content_empty);
        NestRefreshManager<WarnEntity> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, new NestRefreshManager.CreateApi<WarnEntity>() {
            @Override
            public Observable<List<WarnEntity>> run(int page, int perPage) {
                UserService api = ApiUtil.createDefaultApi(UserService.class);
                return api.getSysMessageList("" + (page+1), "" + perPage).map(new HttpResultFunc<>());
            }
        });
        nestRefreshManager.setPullLoadEnable(true);
        nestRefreshManager.setHttpError(new HttpError() {
            @Override
            public void onError(Throwable e) {
                Log.i("test", e.toString());
            }
        });
        nestRefreshManager.setCallBack(new NestRefreshManager.CallBack<WarnEntity>() {
            @Override
            public void call(List<? extends WarnEntity> allList, List<? extends WarnEntity> currentList) {
                mAdapter.clear();
                mAdapter.addList(allList);
                mAdapter.notifyDataSetChanged();
            }
        });
        nestRefreshManager.doApi();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detail = createIntent(MyWarnDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("warn", mAdapter.getItem(position));
                detail.putExtras(bundle);
                startActivity(detail);
            }
        });
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("系统消息", getResources().getColor(R.color.white));
        getHeadBar().hideRightImage();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().post(new SYSMSGTransfer(false));
    }
}
