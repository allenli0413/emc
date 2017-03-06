package com.emiancang.emiancang.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.litesuits.android.log.Log;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.App;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.bean.ResEntity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.requestcheck.RegisterCheck;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.MyCountDownTimer;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserLogin;
import com.emiancang.emiancang.user.UserManager;
import com.zwyl.http.SimpleHttpResponHandler;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/23.
 */
public class ForgetPasswrodActivity extends TitleActivity implements View.OnClickListener {


    @InjectView(R.id.et_phone)
    EditText etPhone;
    @InjectView(R.id.et_verification)
    EditText etVerification;
    @InjectView(R.id.tv_send_verification)
    TextView tvSendVerification;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.iv_xianshi)
    ImageView ivXianshi;
    @InjectView(R.id.password_repet_edit)
    EditText passwordRepetEdit;
    @InjectView(R.id.password_repet_xianshi)
    ImageView passwordRepetXianshi;
    @InjectView(R.id.tv_register)
    TextView tvRegister;

    boolean hide = false;
    boolean hideRepet = false;

    private MyCountDownTimer mTimer;
    private String mPwdStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        ButterKnife.inject(this);
        initHead();
        initView();
    }
    private void initHead() {
        Util.setStatusBarColor(getActivity(),R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("忘记密码",getResources().getColor(R.color.white));
    }

    private void initView() {
        mTimer = new MyCountDownTimer(60000, 1000, tvSendVerification);
        mTimer.setListener(new MyCountDownTimer.CountDownTimerClickListener() {
            @Override
            public void TimerFinish() {
//                etVerification.setText("");
//                etVerification.setEnabled(false);
                etVerification.clearFocus();
            }
        });
        tvSendVerification.setEnabled(false);
        tvRegister.setEnabled(false);
        etVerification.setEnabled(false);
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = etPhone.getText().toString().trim();
                if(Util.compileExChar(str)){
                    str = str.substring(0,str.length()-1);
                    etPhone.setText(str);
                    etPhone.setSelection(str.length());
                }
                if(etPhone.getText().toString().trim().length() == 11){
                    tvSendVerification.setEnabled(true);
                }else{
                    tvSendVerification.setEnabled(false);
                }
                refreshButton();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etVerification.addTextChangedListener(getTextWatcher());
        etPassword.addTextChangedListener(getTextWatcher());
        passwordRepetEdit.addTextChangedListener(getTextWatcher());
    }

    public void refreshButton(){
        String phone = etPhone.getText().toString().trim();
        String verification = etVerification.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String passwordEdit = passwordRepetEdit.getText().toString().trim();

        if(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(verification) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(passwordEdit)){
            tvRegister.setEnabled(true);
        }else{
            tvRegister.setEnabled(false);
        }
    }


    private TextWatcher getTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                refreshButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }


    @OnClick({R.id.tv_send_verification, R.id.iv_xianshi, R.id.password_repet_xianshi,R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                String mobile = etPhone.getText().toString();
                final String verificationStr = etVerification.getText().toString().trim();
                if (TextUtils.isEmpty(verificationStr)) {
                    showToast("请先获取验证码");
                    return;
                }
                mPwdStr = etPassword.getText().toString().trim();
                String repeatPassword = passwordRepetEdit.getText().toString().trim();
                RegisterCheck registerCheck = new RegisterCheck(mobile, verificationStr, mPwdStr, repeatPassword);
                String info = registerCheck.getCheckInfo();
                if (!TextUtils.isEmpty(info)) {
                    showToast(info);
                    return;
                }
                ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
                UserService api = ApiUtil.createDefaultApi(UserService.class,ApiUtil.SMURL);
                ApiUtil.doDefaultApi(api.forgetpassword(mobile,mPwdStr,verificationStr), new HttpSucess<ResEntity>() {
                    @Override
                    public void onSucess(ResEntity resEntity) {
                        if(resEntity.getResCode().equals("0")) {
                            showToast("修改成功");
                            finish();
                        }else{
                            showToast(resEntity.getResMsg());
                        }
                    }
                }, viewControl);
                break;
            case R.id.tv_send_verification:
                String phone = etPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    showToast("请输入手机号！");
                } else if (phone.length() != 11) {
                    showToast("请输入11位手机号！");
                } else if (!Util.isPhone(phone)) {
                    showToast("请输入合法手机号!");
                } else {
                    ViewControl viewControl1 = ViewControlUtil.create2Dialog(getActivity());
                    UserService api1 = ApiUtil.createDefaultApi(UserService.class,ApiUtil.SMURL);
                    ApiUtil.doDefaultApi(api1.verification(phone,"1"), new HttpSucess<ResEntity>() {
                        @Override
                        public void onSucess(ResEntity resEntity) {
                            if(resEntity.getResCode().equals("0")){
                                mTimer.start();
                                etVerification.setEnabled(true);
                            }else{
                                showToast(resEntity.getResMsg());
                            }
                        }
                    }, viewControl1);
                }
                break;
            case R.id.iv_xianshi:
                if (hide) {
                    ivXianshi.setImageResource(R.drawable.hide);
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    ivXianshi.setImageResource(R.drawable.show);
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                hide = !hide;
                etPassword.postInvalidate();
                etPassword.setSelection(etPassword.getText().toString().length());
                break;
            case R.id.password_repet_xianshi:
                if (hideRepet) {
                    passwordRepetXianshi.setImageResource(R.drawable.hide);
                    passwordRepetEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    passwordRepetXianshi.setImageResource(R.drawable.show);
                    passwordRepetEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                hideRepet = !hideRepet;
                passwordRepetEdit.postInvalidate();
                passwordRepetEdit.setSelection(passwordRepetEdit.getText().toString().length());
                break;
        }
    }
}
