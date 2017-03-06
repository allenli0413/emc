package com.emiancang.emiancang.my;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.bean.ResEntity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.UserManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 22310 on 2016/11/7.
 */

public class MyVerificationCodeActivity extends TitleActivity {

    @InjectView(R.id.my_verification_title)
    TextView myVerificationTitle;
    @InjectView(R.id.my_verification_code)
    EditText myVerificationCode;
    @InjectView(R.id.verificationcode_phone_update)
    TextView verificationcodePhoneUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_my_verificationcode, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("验证码", getResources().getColor(R.color.white));
    }

    private void initView() {
        myVerificationTitle.setText("请输入发送到" + UserManager.getInstance().getUser().getESjzcSjh() + "的手机验证码");
        verificationcodePhoneUpdate.setEnabled(false);
        myVerificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = myVerificationCode.getText().toString().trim();
                if(!TextUtils.isEmpty(str)){
                    verificationcodePhoneUpdate.setEnabled(true);
                }else{
                    verificationcodePhoneUpdate.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.verificationcode_phone_update)
    public void onClick() {
        String code = myVerificationCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            showToast("请输入短信验证码");
            return;
        }
        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        UserService api = ApiUtil.createDefaultApi(UserService.class);
        ApiUtil.doDefaultApi(api.checkPasscode(code), new HttpSucess<ResEntity>() {
            @Override
            public void onSucess(ResEntity resEntity) {
                startActivity(createIntent(MyUpdatePhoneActivity.class));
            }
        }, viewControl);
    }
}
