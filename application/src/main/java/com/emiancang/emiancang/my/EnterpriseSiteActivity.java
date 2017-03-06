package com.emiancang.emiancang.my;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.litesuits.common.utils.InputMethodUtils;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.eventbean.EnterpriseAccountTransfer;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by 22310 on 2016/11/7.
 */

public class EnterpriseSiteActivity extends TitleActivity {

    @InjectView(R.id.register_pwd_et)
    EditText registerPwdEt;
    @InjectView(R.id.iv_xianshi)
    LinearLayout ivXianshi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_user_update_name, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        String custName = getStringExtra("name");
        if(!TextUtils.isEmpty(custName))
            registerPwdEt.setText(custName);
        else
            registerPwdEt.setHint("请输入企业地址");
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("企业地址", getResources().getColor(R.color.white));
        getHeadBar().showRightText();
        getHeadBar().setRightTitle("确定", getResources().getColor(R.color.white));
        getHeadBar().hideLeftImage();
        getHeadBar().showLeftText();
        getHeadBar().setLeftTitle("取消");
        getHeadBar().setLeftOnClickListner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodUtils.hideSoftInput(getActivity());
                finish();
            }
        });
        getHeadBar().setRightOnClickListner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = registerPwdEt.getText().toString().trim();
                if(!TextUtils.isEmpty(name)){
                    EventBus.getDefault().post(new EnterpriseAccountTransfer(name,EnterpriseAccountTransfer.ENTERPRISESITE));
                    finish();
                }else{
                    showToast("企业地址不能为空");
                }
            }
        });
    }

    @OnClick(R.id.iv_xianshi)
    public void onClick() {
        registerPwdEt.setText("");
    }
}
