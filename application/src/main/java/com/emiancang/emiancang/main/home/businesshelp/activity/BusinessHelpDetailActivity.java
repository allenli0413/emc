package com.emiancang.emiancang.main.home.businesshelp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.main.home.businesshelp.adapter.MineBusinessHelpDetailAdapter;
import com.emiancang.emiancang.main.home.businesshelp.model.BusinessHelpModel;
import com.emiancang.emiancang.main.home.businesshelp.model.MineBusinessHelpDetailStatusModel;
import com.emiancang.emiancang.main.home.businesshelp.service.MineBusinessHelpDetailStatusService;
import com.emiancang.emiancang.uitl.Util;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yuanyueqing on 2016/11/9.
 */

public class BusinessHelpDetailActivity extends TitleActivity {

    @InjectView(R.id.tv_business_help_detail_content)
    TextView tv_business_help_detail_content;
    @InjectView(R.id.lv_business_help_detail)
    ListView lv_business_help_detail;

    MineBusinessHelpDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_help_detail);
        ButterKnife.inject(this);

        BusinessHelpModel model = (BusinessHelpModel) getIntent().getSerializableExtra("model");
        initHead(model);
        initView(model);
        initData(model);
    }

    private void initHead(BusinessHelpModel model) {
        Util.setStatusBarColor(getActivity(),R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        String type = "";
        if(model.geteCgxqType().equals("0"))
            type = "购买需求";
        if(model.geteCgxqType().equals("1"))
            type = "销售需求";
        getHeadBar().setCenterTitle(model.geteCgxqCjrName() + "." + type, getResources().getColor(R.color.white));
        getHeadBar().showLeftImage();
        getHeadBar().setLeftOnClickListner(v -> finish());
    }

    private void initView(BusinessHelpModel model) {
        tv_business_help_detail_content.setText(model.geteCgxqAppxqxq());
    }

    private void initData(BusinessHelpModel model) {
        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        MineBusinessHelpDetailStatusService api = ApiUtil.createDefaultApi(MineBusinessHelpDetailStatusService.class);
        ApiUtil.doDefaultApi(api.list(model.geteCgxqId()), data -> {
            if(data.getEfkxxList() != null) {
                initView(data);
            } else {
                lv_business_help_detail.setVisibility(View.GONE);
            }
        }, viewControl);
    }

    private void initView(MineBusinessHelpDetailStatusModel data) {
        lv_business_help_detail.setVisibility(View.VISIBLE);
        tv_business_help_detail_content.setText(data.getEcgxq().geteCgxqAppxqxq());
        if(data.getEfkxxList() != null) {
            adapter = new MineBusinessHelpDetailAdapter(data.getEfkxxList());
            lv_business_help_detail.setAdapter(adapter);
        }
    }

}
