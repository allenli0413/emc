package com.emiancang.emiancang.main.home.findmoney.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.litesuits.android.log.Log;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.main.home.findmoney.adapter.FindMoneyDetailStatusAdapter;
import com.emiancang.emiancang.main.home.findmoney.model.FindMoneyDetailStatusModel;
import com.emiancang.emiancang.main.home.findmoney.model.FindMoneyModel;
import com.emiancang.emiancang.main.home.findmoney.service.FindMoneyDetailService;
import com.emiancang.emiancang.uitl.Util;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;

/**
 * Created by yuanyueqing on 2016/11/8.
 */

public class FindMoneyDetailActivity extends TitleActivity {

    @InjectView(R.id.tv_find_money_detail_content)
    TextView tv_find_money_detail_content;
    @InjectView(R.id.find_money_detail_split)
    View find_money_detail_split;
    @InjectView(R.id.tv_find_money_detail_requirement)
    TextView tv_find_money_detail_requirement;
    @InjectView(R.id.lv_mine_business_help_detail)
    ListView lv_mine_business_help_detail;

//    @InjectView(R.id.listView)
//    ListView listView;
//    @InjectView(R.id.nestRefreshLayout)
//    NestRefreshLayout nestRefreshLayout;

    FindMoneyDetailStatusAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_money_detail);
        ButterKnife.inject(this);
        FindMoneyModel data = (FindMoneyModel) getIntent().getSerializableExtra("data");
        initHead(data);
        initData(data);
    }

    private void initData(FindMoneyModel findMoneyModel) {
        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        FindMoneyDetailService api = ApiUtil.createDefaultApi(FindMoneyDetailService.class);
        ApiUtil.doDefaultApi(api.list(findMoneyModel.geteJrsqId()), data -> {
            if(data.getEfxkkList() != null && !data.getEfxkkList().isEmpty()) {
                lv_mine_business_help_detail.setVisibility(View.VISIBLE);
                adapter = new FindMoneyDetailStatusAdapter(data.getEfxkkList());
                lv_mine_business_help_detail.setAdapter(adapter);
            } else {
                lv_mine_business_help_detail.setVisibility(View.GONE);
            }
        }, viewControl);
//        adapter = new FindMoneyDetailStatusAdapter();
//        listView.setAdapter(adapter);
//        ViewControl viewControl = ViewControlUtil.create2View(nestRefreshLayout);
//        NestRefreshManager<FindMoneyDetailStatusModel> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, (page, perPage) -> {
//            FindMoneyDetailService api = ApiUtil.createDefaultApi(FindMoneyDetailService.class);
//            return api.list(detailId).map(new HttpResultFunc<>());
//        });
//        nestRefreshManager.setPullLoadEnable(true);
//        nestRefreshManager.setHttpError(e -> Log.i("test", e.toString()));
//        nestRefreshManager.setCallBack((allList, currentList) -> {
//            adapter.clear();
////                adapter.addList(getData());
//            adapter.notifyDataSetChanged();
//        });
//        nestRefreshManager.doApi();
//
//        listView.setOnItemClickListener((parent, view, position, id) -> finish());
    }

    private void initHead(FindMoneyModel data) {
        Util.setStatusBarColor(getActivity(),R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("详情", getResources().getColor(R.color.white));
        getHeadBar().showLeftImage();
        getHeadBar().setLeftOnClickListner(v -> finish());

        find_money_detail_split.setVisibility(View.VISIBLE);
//        tv_find_money_detail_requirement.setVisibility(View.GONE);
        switch (data.geteJrsqJrfwlx()){
            case "00":
                tv_find_money_detail_requirement.setText("质押融资 " + data.geteJrsqSqsj());
                break;
            case "01":
                tv_find_money_detail_requirement.setText("交易配资 " + data.geteJrsqSqsj());
                break;
            case "02":
                tv_find_money_detail_requirement.setText("棉仓白条 " + data.geteJrsqSqsj());
                break;
            case "03":
                tv_find_money_detail_requirement.setText("运费补贴 " + data.geteJrsqSqsj());
                break;
        }
        tv_find_money_detail_content.setText(data.geteJrsqNr());
    }
}
