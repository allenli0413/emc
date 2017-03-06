package com.emiancang.emiancang.main.home.findmoney.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.eventbean.FindMoneyPublishTransfer;
import com.emiancang.emiancang.eventbean.FindMoneyPublishTypeTransfer;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.main.home.findmoney.service.FindMoneyRequirementPublishService;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.UserManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by yuanyueqing on 2016/11/8.
 */

public class FindMoneyPublishActivity extends TitleActivity {

    @InjectView(R.id.edit_find_money_publish)
    EditText edit_find_money_publish;
    @InjectView(R.id.tv_find_money_publish_type)
    TextView tv_find_money_publish_type;
    @InjectView(R.id.tv_find_money_publish_submit)
    TextView tv_find_money_publish_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_money_publish);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
        tv_find_money_publish_submit.setEnabled(false);
        initHead();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(new FindMoneyPublishTransfer());
        EventBus.getDefault().unregister(this);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive())
            ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void onEventMainThread(FindMoneyPublishTypeTransfer findMoneyPublishTypeTransfer){
        tv_find_money_publish_type.setText(findMoneyPublishTypeTransfer.getName());
        if(!edit_find_money_publish.getText().toString().trim().equals(""))
            tv_find_money_publish_submit.setEnabled(true);
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(),R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("发布找资金需求", getResources().getColor(R.color.white));
        getHeadBar().showLeftImage();
        getHeadBar().setLeftOnClickListner(v -> finish());

        edit_find_money_publish.setHint("请输入您希望平台帮您解决的需求，平台会有客服联系您");
        edit_find_money_publish.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!edit_find_money_publish.getText().toString().trim().equals("") && !tv_find_money_publish_type.getText().toString().trim().equals("选择类型")) {
                    tv_find_money_publish_submit.setEnabled(true);
                }else{
                    tv_find_money_publish_submit.setEnabled(false);
                }
            }
        });
    }

    @OnClick({R.id.tv_find_money_publish_submit, R.id.ll_find_money_publish_type})
    void onClick(View view){
        switch (view.getId()){
            case R.id.tv_find_money_publish_submit:
                String content = edit_find_money_publish.getText().toString().trim();
                String type_text = tv_find_money_publish_type.getText().toString().trim();
                String type = "";
                if(type_text.equals("选择类型")){
                    showToast("请选择类型");
                    break;
                }
                if(type_text.equals("质押融资"))
                    type = "00";
                if(type_text.equals("交易配资"))
                    type = "01";
                if(type_text.equals("白条"))
                    type = "02";
                if(type_text.equals("运费补贴"))
                    type = "03";
                ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
                FindMoneyRequirementPublishService api = ApiUtil.createDefaultApi(FindMoneyRequirementPublishService.class);
                ApiUtil.doDefaultApi(api.list(type, content), data -> {
//                    if(data.isSuccess()) {
                        showToast("发布成功");
                        finish();
//                    }
                },viewControl);
                break;
            case R.id.ll_find_money_publish_type:
                Intent intent = new Intent(getActivity(), FindMoneyPublishTypeSelectActivity.class);
                String in_type_text = tv_find_money_publish_type.getText().toString().trim();
                intent.putExtra("type", in_type_text);
                startActivity(intent);
                break;
        }
    }
}
