package com.emiancang.emiancang.my;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.eventbean.EnterpriseAccountTransfer;
import com.emiancang.emiancang.uitl.Util;
import com.litesuits.common.utils.InputMethodUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by yuanyueqing on 2017/1/16.
 */

public class EnterpriseNumberActivity extends TitleActivity {

    @InjectView(R.id.register_pwd_et)
    EditText registerPwdEt;
    @InjectView(R.id.iv_xianshi)
    LinearLayout ivXianshi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_user_update_number, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        String custNumber = getStringExtra("number");
        if(!TextUtils.isEmpty(custNumber))
            registerPwdEt.setText(custNumber);
        else
            registerPwdEt.setHint("请输入企业联系方式");
//        registerPwdEt.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("企业联系方式", getResources().getColor(R.color.white));
        getHeadBar().showRightText();
        getHeadBar().setRightTitle("确认", getResources().getColor(R.color.white));
        getHeadBar().hideLeftImage();
        getHeadBar().showLeftText();
        getHeadBar().setLeftTitle("取消");
        getHeadBar().setLeftOnClickListner(v -> {
            InputMethodUtils.hideSoftInput(getActivity());
            finish();
        });
        getHeadBar().setRightOnClickListner(v -> {
            String name = registerPwdEt.getText().toString().trim();
            if(!TextUtils.isEmpty(name)){
                EventBus.getDefault().post(new EnterpriseAccountTransfer(name,EnterpriseAccountTransfer.ENTERNUMBER));
                finish();
            }else{
                showToast("企业联系方式不能为空");
            }
        });
    }

    @OnClick(R.id.iv_xianshi)
    public void onClick() {
        registerPwdEt.setText("");
    }
}
