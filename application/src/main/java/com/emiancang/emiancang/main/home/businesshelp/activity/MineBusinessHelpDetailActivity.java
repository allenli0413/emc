package com.emiancang.emiancang.main.home.businesshelp.activity;

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
import com.emiancang.emiancang.main.home.businesshelp.adapter.MineBusinessHelpDetailAdapter;
import com.emiancang.emiancang.main.home.businesshelp.model.BusinessHelpModel;
import com.emiancang.emiancang.main.home.businesshelp.model.MineBusinessHelpDetailStatusModel;
import com.emiancang.emiancang.main.home.businesshelp.service.MineBusinessHelpDetailStatusService;
import com.emiancang.emiancang.uitl.Util;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;

/**
 * Created by yuanyueqing on 2016/11/9.
 */

public class MineBusinessHelpDetailActivity extends TitleActivity {

    @InjectView(R.id.tv_find_money_detail_content)
    TextView tv_find_money_detail_content;
    @InjectView(R.id.find_money_detail_split)
    View find_money_detail_split;
    @InjectView(R.id.tv_find_money_detail_requirement)
    TextView tv_find_money_detail_requirement;
    @InjectView(R.id.lv_mine_business_help_detail)
    ListView lv_mine_business_help_detail;

    MineBusinessHelpDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_money_detail);
        ButterKnife.inject(this);
        BusinessHelpModel data = (BusinessHelpModel) getIntent().getSerializableExtra("data");
        String id = data.geteCgxqId();
        initHead();
        initStatus(data);
        initData(id);
    }

    private void initStatus(BusinessHelpModel data) {
        tv_find_money_detail_content.setText(data.geteCgxqAppxqxq());
        String sth = "";
        if(data.geteCgxqType().equals("0"))
            sth = "购买需求  ";
        if(data.geteCgxqType().equals("1"))
            sth = "销售需求  ";
        sth = sth + data.geteCgxqCjsj();
        tv_find_money_detail_requirement.setText(sth);
    }

    private void initData(String orderId) {
        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        MineBusinessHelpDetailStatusService api = ApiUtil.createDefaultApi(MineBusinessHelpDetailStatusService.class);
        ApiUtil.doDefaultApi(api.list(orderId), data -> {
            if(data.getEfkxxList() != null && !data.getEfkxxList().isEmpty()) {
                lv_mine_business_help_detail.setVisibility(View.VISIBLE);
                adapter = new MineBusinessHelpDetailAdapter(data.getEfkxxList());
                lv_mine_business_help_detail.setAdapter(adapter);
            } else {
                lv_mine_business_help_detail.setVisibility(View.GONE);
            }
        }, viewControl);
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(),R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("详情", getResources().getColor(R.color.white));
        getHeadBar().showLeftImage();
        getHeadBar().setLeftOnClickListner(v -> finish());

        find_money_detail_split.setVisibility(View.VISIBLE);
        tv_find_money_detail_requirement.setVisibility(View.VISIBLE);
    }
}
