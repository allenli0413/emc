package com.emiancang.emiancang.my;

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
import com.emiancang.emiancang.bean.Dictionary;
import com.emiancang.emiancang.bean.EnterpriseTypeEntity;
import com.emiancang.emiancang.eventbean.EnterpriseAccountTransfer;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ShowService;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.my.adapter.AdapterEnterpriseType;
import com.emiancang.emiancang.my.adapter.ShowAdapter;
import com.emiancang.emiancang.service.EnterpriseTypeService;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;

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

public class MyEnterpriseTypeActivity extends TitleActivity {


    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;

    private AdapterEnterpriseType mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_my_enterprise_type, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("企业类型", getResources().getColor(R.color.white));
    }

    private void initView() {
        mAdapter = new AdapterEnterpriseType(this);
        listView.setAdapter(mAdapter);
        ViewControl viewControl = ViewControlUtil.create2View(nestRefreshLayout);
        NestRefreshManager<Dictionary> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, new NestRefreshManager.CreateApi<Dictionary>() {
            @Override
            public Observable<List<Dictionary>> run(int page, int perPage) {
                UserService api = ApiUtil.createDefaultApi(UserService.class);
                return api.getDictionaryByCode("COMPANY_TYPE").map(new HttpResultFunc<>());
            }
        });
        nestRefreshManager.setPullLoadEnable(true);
        nestRefreshManager.setHttpError(new HttpError() {
            @Override
            public void onError(Throwable e) {
                Log.i("test", e.toString());
            }
        });
        nestRefreshManager.setCallBack(new NestRefreshManager.CallBack<Dictionary>() {
            @Override
            public void call(List<? extends Dictionary> allList, List<? extends Dictionary> currentList) {
                mAdapter.clear();
                mAdapter.addList(allList);
                mAdapter.notifyDataSetChanged();
            }
        });
        nestRefreshManager.doApi();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dictionary entity = mAdapter.getItem(position-1);
                EventBus.getDefault().post(new EnterpriseAccountTransfer(entity.getDicValue(),EnterpriseAccountTransfer.ENTERPRISETYPE,""+entity.getDicKey()));
                finish();
            }
        });
    }
}
