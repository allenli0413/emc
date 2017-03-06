package com.emiancang.emiancang.main.home.logisticsquery.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.main.home.logisticsquery.service.LogisticsQuerySecondService;
import com.emiancang.emiancang.uitl.Util;
import com.mayigeek.frame.view.state.ViewControl;

import butterknife.InjectView;

/**
 * Created by yuanyueqing on 2017/2/22.
 */

public class LogisticsQuerySecondDetailActivity extends TitleActivity {

    @InjectView(R.id.tv_activity_logistics_query_second_detail_start_address)
    TextView tv_activity_logistics_query_second_detail_start_address;
    @InjectView(R.id.tv_activity_logistics_query_second_detail_end_address)
    TextView tv_activity_logistics_query_second_detail_end_address;
    @InjectView(R.id.tv_activity_logistics_query_second_detail_transport_type)
    TextView tv_activity_logistics_query_second_detail_transport_type;
    @InjectView(R.id.tv_activity_logistics_query_second_detail_standard)
    TextView tv_activity_logistics_query_second_detail_standard;
    @InjectView(R.id.tv_activity_logistics_query_second_detail_transport_ability)
    TextView tv_activity_logistics_query_second_detail_transport_ability;
    @InjectView(R.id.tv_activity_logistics_query_second_detail_valid_period)
    TextView tv_activity_logistics_query_second_detail_valid_period;
    @InjectView(R.id.tv_activity_logistics_query_second_detail_contact)
    TextView tv_activity_logistics_query_second_detail_contact;
    @InjectView(R.id.tv_activity_logistics_query_second_detail_phone)
    TextView tv_activity_logistics_query_second_detail_phone;
    @InjectView(R.id.tv_activity_logistics_query_second_detail_enterprise_address)
    TextView tv_activity_logistics_query_second_detail_enterprise_address;
    @InjectView(R.id.tv_activity_logistics_query_second_detail_enterprise_phone)
    TextView tv_activity_logistics_query_second_detail_enterprise_phone;
    @InjectView(R.id.tv_activity_logistics_query_second_detail_fax)
    TextView tv_activity_logistics_query_second_detail_fax;
    @InjectView(R.id.tv_activity_logistics_query_second_detail_zip_code)
    TextView tv_activity_logistics_query_second_detail_zip_code;
    @InjectView(R.id.tv_activity_logistics_query_second_detail_email)
    TextView tv_activity_logistics_query_second_detail_email;

    ViewControl viewControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_query_second_detail);
        String serviceId = getIntent().getStringExtra("serviceId");
        initHead();
        initView();
        initData(serviceId);
    }

    private void initView() {
        viewControl = new ViewControlUtil().create2Dialog(getActivity());
    }

    private void initData(String serviceId) {
        LogisticsQuerySecondService api = ApiUtil.createDefaultApi(LogisticsQuerySecondService.class);
        ApiUtil.doDefaultApi(api.detail(serviceId), data -> {
            getHeadBar().setCenterTitle(data.getServiceCustName(), getResources().getColor(R.color.white));
            tv_activity_logistics_query_second_detail_start_address.setText(data.getStatrAddrName());
            tv_activity_logistics_query_second_detail_end_address.setText(data.getEndAddrName());
            if(data.getTransportation().equals("1"))
                tv_activity_logistics_query_second_detail_transport_type.setText("公路");
            if(data.getTransportation().equals("2"))
                tv_activity_logistics_query_second_detail_transport_type.setText("铁路");
            tv_activity_logistics_query_second_detail_standard.setText(data.getServicePrice() + "元/吨");
            tv_activity_logistics_query_second_detail_transport_ability.setText(data.getTransportcapacity() + "吨");
            tv_activity_logistics_query_second_detail_valid_period.setText(data.getStartDate() + "～" + data.getEndDate());
            tv_activity_logistics_query_second_detail_contact.setText(data.geteSjzcXm());
            tv_activity_logistics_query_second_detail_phone.setText(data.geteSjzcSjh());
            tv_activity_logistics_query_second_detail_enterprise_address.setText(data.getCompanyAddress());
            tv_activity_logistics_query_second_detail_enterprise_phone.setText(data.getGroupContactPhone());
            tv_activity_logistics_query_second_detail_fax.setText(data.getFaxNbr());
            tv_activity_logistics_query_second_detail_zip_code.setText(data.getPostCode());
            tv_activity_logistics_query_second_detail_email.setText(data.getEmail());
        }, viewControl);
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle(" ", getResources().getColor(R.color.white));
        getHeadBar().showLeftImage();
        getHeadBar().setLeftOnClickListner(v -> finish());
    }
}
