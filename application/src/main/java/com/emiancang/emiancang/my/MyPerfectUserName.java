package com.emiancang.emiancang.my;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.litesuits.common.utils.InputMethodUtils;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.eventbean.PerfectUserNameTransfer;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.service.UserService;
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

public class MyPerfectUserName extends TitleActivity {

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
        String name = getIntent().getExtras().getString("name","");
        registerPwdEt.setText(name);

    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("用户姓名", getResources().getColor(R.color.white));
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
                    EventBus.getDefault().post(new PerfectUserNameTransfer(name));
                    finish();
                }else{
                    showToast("用户姓名不能为空");
                }
            }
        });
    }

    @OnClick(R.id.iv_xianshi)
    public void onClick() {
        registerPwdEt.setText("");
    }
}
