package com.emiancang.emiancang.main.home.deliveryorder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.emiancang.emiancang.App;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.main.home.deliveryorder.adapter.DeliveryOrderListAdapter;
import com.emiancang.emiancang.main.home.deliveryorder.listener.DeliveryOrderItemListener;
import com.emiancang.emiancang.main.home.deliveryorder.model.DeliveryOrderModel;
import com.emiancang.emiancang.main.home.deliveryorder.service.CodeService;
import com.emiancang.emiancang.main.home.deliveryorder.service.DeliveryOrderService;
import com.emiancang.emiancang.uitl.MyCountDownTimer;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.UserManager;
import com.litesuits.android.log.Log;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;

/**
 * Author: liyh
 * E-mail: liyh@zhiwyl.com
 * Phone: 15033990609
 * Date: 2017/2/22  Time: 15:26
 * Description:提货单列表页面
 */

public class DeliveryOrderActivity extends TitleActivity implements DeliveryOrderItemListener {

    @InjectView(R.id.tv_title_search_left)
    TextView tv_title_search_left;
    @InjectView(R.id.iv_title_search_right)
    ImageView iv_title_search_right;
    @InjectView(R.id.ll_title_search_total_left)
    LinearLayout ll_title_search_total_left;
    @InjectView(R.id.edit_title_search_middle)
    EditText edit_title_search_middle;
    @InjectView(R.id.tv_title_search_button)
    TextView tv_title_search_button;
    @InjectView(R.id.tv_title_search_right)
    TextView tv_title_search_right;
    @InjectView(R.id.ll_title_search)
    LinearLayout ll_title_search;
    @InjectView(R.id.gray_layout)
    View gray_layout;
    @InjectView(R.id.elistView)
    ListView elistView;
    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;
    @InjectView(R.id.rl_delivery_order)
    RelativeLayout rl_delivery_order;

    ViewControl viewControl;
    private DeliveryOrderListAdapter mAdapter;
    private String mCustName;
    private List<DeliveryOrderModel> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_order);
        ButterKnife.inject(this);
        viewControl = ViewControlUtil.create2View(nestRefreshLayout, "没有找到对应数据", "没有找到对应数据", R.drawable.icon_content_empty);
        initHead();
        initView();
        initData();

    }

    private void initData() {
        mCustName = UserManager.getInstance().getUser().getCustName();
        if (TextUtils.isEmpty(mCustName)) {
            showToast("企业名称不存在");
            mCustName = "新疆博乐互益纺织有限公司";
        } else {
            requestData(mCustName, "");
        }
    }

    private void requestData(String custName, String agreementNum) {
        NestRefreshManager<DeliveryOrderModel> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, (page, perPage) -> {
            DeliveryOrderService api = ApiUtil.createDefaultApi(DeliveryOrderService.class, ApiUtil.SMURL);
            return api.list("" + (page + 1), "" + perPage, custName, agreementNum).map(new HttpResultFunc<>());
        });
        nestRefreshManager.setPerPage(15);
        nestRefreshManager.setPullLoadEnable(true);
        nestRefreshManager.setHttpError(e -> Log.i("test", e.toString()));
        nestRefreshManager.setCallBack((allList, currentList) -> {
            for (DeliveryOrderModel model : allList) {
                model.CODE_STATE = model.sfkyfsyzm.equals("是") ? 1 : 3;
            }
            mAdapter.clear();
            mAdapter.addList(allList);
            mAdapter.notifyDataSetChanged();
        });
        nestRefreshManager.doApi();
    }

    private void initView() {
        mAdapter = new DeliveryOrderListAdapter();
        mAdapter.setDeliveryOrderItemOnClickListener(this);
        elistView.setAdapter(mAdapter);
        elistView.setCacheColorHint(0x00000000);

    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().hideHeader();
        edit_title_search_middle.setHint("请输入合同号检索");

        ll_title_search_total_left.setBackgroundResource(R.drawable.back_icon_selector);
        ll_title_search_total_left.setOnClickListener(v -> finish());
        tv_title_search_left.setVisibility(View.GONE);
        iv_title_search_right.setVisibility(View.GONE);
        tv_title_search_right.setText("搜索");
        tv_title_search_right.setBackground(null);
        tv_title_search_right.setTextColor(getResources().getColor(R.color.white));
        tv_title_search_right.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_title_search_right.setOnClickListener(v -> {
            String agreementNum = edit_title_search_middle.getText().toString().trim();
            if (TextUtils.isEmpty(agreementNum)) {
                requestData(mCustName, "");
            } else {
                requestData(mCustName, agreementNum);
            }

        });
    }

    @Override
    public void numOnClick(View v) {
        DeliveryOrderModel model = (DeliveryOrderModel) v.getTag();
        Intent agreementIntent = new Intent(getActivity(), AgreementDetailActivity.class);
        agreementIntent.putExtra("agreementNum", model.hth);
        startActivity(agreementIntent);
    }

    @Override
    public void codeOnClick(View v, List<DeliveryOrderModel> list, MyCountDownTimer timer) {
        DeliveryOrderModel model = (DeliveryOrderModel) v.getTag();
        String username = UserManager.getInstance().getUser().getESjzcSjh();
        CodeService api = ApiUtil.createDefaultApi(CodeService.class, ApiUtil.SMURL);
        ApiUtil.doDefaultApi(api.list(model.hth, username), data -> {
            String phoneNum = data.mobile;
            if (!TextUtils.isEmpty(phoneNum) && isMobileNum(phoneNum)) {
                for (DeliveryOrderModel doModel : list) {
                    if (doModel.hth.equals(model.hth)) {
                        doModel.CODE_STATE = 5;
                        doModel.remainTime = 60000L;
                    } else {
                        switch (doModel.CODE_STATE) {
                            case 1:
                                doModel.CODE_STATE = 3;
                                break;
                            case 2:
                                doModel.CODE_STATE = 4;
                                break;
                        }
                    }
                }

                mAdapter.notifyDataSetChanged();//为了将其他按钮zhihui置灰
                long currentTime = System.currentTimeMillis();
                Logger.i("点击:" + currentTime);
                SharedPrefsUtil.putValue(App.getContext(), model.hth, currentTime);
                String maskNumber = phoneNum.substring(0, 3) + "****" + phoneNum.substring(7, phoneNum.length());
                showTip(maskNumber);
            } else {
                showToast("验证码发送失败,请重试!");
            }
        });
    }


    @Override
    public void arrowOnClick(View v) {
        DeliveryOrderModel model = (DeliveryOrderModel) v.getTag();
        model.isOpen = !model.isOpen;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void finishCountDown(List<DeliveryOrderModel> list) {//倒计时结束
        mAdapter.hasCountDown = false;
        for (DeliveryOrderModel doModel : list) {

            if (doModel.sfkyfsyzm.equals("是")) {
                switch (doModel.CODE_STATE) {
                    case 3:
                        doModel.CODE_STATE = 1;
                        break;
                    case 4:
                    case 5:
                        doModel.CODE_STATE = 2;
                        break;
                }
            }
            //            }
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 检查是否是电话号码
     *
     * @return
     */
    private boolean isMobileNum(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    private void showTip(String maskNumber) {
        View view = View.inflate(getActivity(), R.layout.layout_load_tip, null);
        TextView tv_layout_tip = (TextView) view.findViewById(R.id.tv_layout_tip);
        tv_layout_tip.setText("电子验证码已发送至" + maskNumber);
        elistView.addHeaderView(view);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (elistView.getHeaderViewsCount() > 0)
                    getActivity().runOnUiThread(() -> elistView.removeHeaderView(view));
            }
        };
        timer.schedule(timerTask, 5000);
    }
}
