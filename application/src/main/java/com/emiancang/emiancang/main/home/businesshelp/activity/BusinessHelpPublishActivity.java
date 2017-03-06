package com.emiancang.emiancang.main.home.businesshelp.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.bean.ResEntity;
import com.emiancang.emiancang.eventbean.BusinessHelpPublishTransfer;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.main.home.businesshelp.service.BusinessHelpPublishService;
import com.emiancang.emiancang.uitl.Util;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by yuanyueqing on 2016/11/9.
 */

public class BusinessHelpPublishActivity extends TitleActivity {

    @InjectView(R.id.tv_business_help_publish_buy_requirement)
    TextView tv_business_help_publish_buy_requirement;
    @InjectView(R.id.tv_business_help_publish_sell_requirement)
    TextView tv_business_help_publish_sell_requirement;
    @InjectView(R.id.edit_business_help_publish)
    TextView edit_business_help_publish;
    @InjectView(R.id.tv_business_help_publish_submit)
    TextView tv_business_help_publish_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_help_publish);
        ButterKnife.inject(this);
        initHead();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive())
            ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(),R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("帮我找", getResources().getColor(R.color.white));
        getHeadBar().showLeftImage();
        getHeadBar().setLeftOnClickListner(v -> finish());

        tv_business_help_publish_sell_requirement.setSelected(true);
        tv_business_help_publish_buy_requirement.setSelected(false);
        tv_business_help_publish_submit.setEnabled(false);
        edit_business_help_publish.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!edit_business_help_publish.getText().toString().toString().equals("")) {
                    tv_business_help_publish_submit.setEnabled(true);
                }else{
                    tv_business_help_publish_submit.setEnabled(false);
                }
            }
        });
    }

    @OnClick({R.id.tv_business_help_publish_submit, R.id.tv_business_help_publish_sell_requirement, R.id.tv_business_help_publish_buy_requirement})
    void onClick(View v){
        switch (v.getId()){
            case R.id.tv_business_help_publish_submit:
                submit();
                break;
            case R.id.tv_business_help_publish_sell_requirement:
                tv_business_help_publish_buy_requirement.setSelected(false);
                tv_business_help_publish_sell_requirement.setSelected(true);
                break;
            case R.id.tv_business_help_publish_buy_requirement:
                tv_business_help_publish_buy_requirement.setSelected(true);
                tv_business_help_publish_sell_requirement.setSelected(false);
                break;
        }
    }

    private void submit() {
        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        BusinessHelpPublishService api = ApiUtil.createDefaultApi(BusinessHelpPublishService.class);
        String content = edit_business_help_publish.getText().toString().trim();
        if (content.equals("")){
            showToast("请输入发布内容");
            return;
        }
        if(tv_business_help_publish_sell_requirement.isSelected()){
            ApiUtil.doDefaultApi(api.publish("1", edit_business_help_publish.getText().toString().trim()), data -> {
//                if(data.isSuccess()) {
                EventBus.getDefault().post(new BusinessHelpPublishTransfer());
                    showToast("发布成功");
                    finish();
//                }
            }, viewControl);
        }
        if(tv_business_help_publish_buy_requirement.isSelected()){
            ApiUtil.doDefaultApi(api.publish("0", edit_business_help_publish.getText().toString().trim()), data -> {
//                if(data.isSuccess()) {
                EventBus.getDefault().post(new BusinessHelpPublishTransfer());
                    showToast("发布成功");
                    finish();
//                }
            }, viewControl);
        }
    }
}
