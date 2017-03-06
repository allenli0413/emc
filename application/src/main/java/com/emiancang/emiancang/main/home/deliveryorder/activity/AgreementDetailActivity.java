package com.emiancang.emiancang.main.home.deliveryorder.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.main.home.deliveryorder.adapter.AgreementDetailListAdapter;
import com.emiancang.emiancang.main.home.deliveryorder.model.CustomAgreementDetailModel;
import com.emiancang.emiancang.main.home.deliveryorder.service.AgreementDetaiService;
import com.emiancang.emiancang.uitl.Util;
import com.mayigeek.frame.view.state.ViewControl;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;

/**
 * Author: liyh
 * E-mail: liyh@zhiwyl.com
 * Phone: 15033990609
 * Date: 2017/2/22  Time: 15:29
 * Description:合同订单详情页面
 */

public class AgreementDetailActivity extends TitleActivity {
    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;
    @InjectView(R.id.rl_agreement_detail)
    RelativeLayout rl_agreement_detail;
    private String[] itemNames = {"合同号", "捆号", "承储库", "成交重量(吨)", "原验重量(吨)"
            , "已签合同重量(吨)", "合同单价(元/吨)", "合同金额(元)", "成交日期", "签约日期"
            , "付款金额", "付款日期", "是否已开提货单", "已开提货单重量(吨)", "开提货单日期"
            , "出库日期", "是否开票", "开票金额(元)"};
    private AgreementDetailListAdapter mAdapter;
    private List<CustomAgreementDetailModel> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement_detail);
        ButterKnife.inject(this);
        initHead();
        initView();
        initData();
    }

    private void initData() {
        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        String agreementNum = getIntent().getStringExtra("agreementNum");
        Logger.i(agreementNum);
        AgreementDetaiService api = ApiUtil.createDefaultApi(AgreementDetaiService.class);
        ApiUtil.doDefaultApi(api.list(agreementNum), model -> {
            if (model == null) {
                showToast("获取数据失败");
            } else {
                String[] itemDatas = {model.spCkhtgzHth, model.spCkhtgzKh, model.spCkhtgzCck, String.valueOf(model.spCkhtgzCjzl)
                        , model.spCkhtgzYjzl, String.valueOf(model.spCkhtgzYqhtzl), String.valueOf(model.spCkhtgzHtdj)
                        , String.valueOf(model.spCkhtgzHtje), model.spCkhtgzCjrq, model.spCkhtgzQyrq, String.valueOf(model.spCkhtgzFkje), model.spCkhtgzFkrq, model.spCkhtgzSfykthd, String.valueOf(model.spCkhtgzYkthdzl)
                        , model.spCkhtgzKthdrq, model.spCkhtgzCkrq, model.spCkhtgzSfkp, String.valueOf(model.spCkhtgzKpje)};
                mList = new ArrayList<>();
                for (int i = 0; i < itemDatas.length; i++) {
                    mList.add(new CustomAgreementDetailModel(itemNames[i], itemDatas[i]));
                }
                mAdapter.clear();
                mAdapter.addList(mList);
                mAdapter.notifyDataSetChanged();
            }
        }, viewControl);
        nestRefreshLayout.setPullLoadEnable(false);
        nestRefreshLayout.setPullRefreshEnable(false);
    }


    private void initView() {
        mAdapter = new AgreementDetailListAdapter();
        listView.setAdapter(mAdapter);
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("成交合同进度", getResources().getColor(R.color.white));
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().hideRightText();
        getHeadBar().showLeftImage();
        getHeadBar().setLeftOnClickListner(v -> finish());
    }
}
