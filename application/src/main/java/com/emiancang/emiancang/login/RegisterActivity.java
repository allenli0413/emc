package com.emiancang.emiancang.login;

import android.content.Intent;
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

import com.emiancang.emiancang.App;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.requestcheck.RegisterCheck;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.MyCountDownTimer;
import com.emiancang.emiancang.uitl.Util;
import com.hyphenate.util.NetUtils;
import com.mayigeek.frame.view.state.ViewControl;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 注册
 */
public class RegisterActivity extends TitleActivity implements View.OnClickListener {

    @InjectView(R.id.register_mobile_et)
    EditText registerMobileEt;
    @InjectView(R.id.register_verification_et)
    EditText registerVerificationEt;
    @InjectView(R.id.tv_send_verification)
    TextView tvSendVerification;
    @InjectView(R.id.register_pwd_et)
    EditText registerPwdEt;
    @InjectView(R.id.iv_xianshi)
    ImageView ivXianshi;
    @InjectView(R.id.repeat_pwd_et)
    EditText repeatPwdEt;
    @InjectView(R.id.repeat_iv_xianshi)
    ImageView repeatIvXianshi;
    @InjectView(R.id.repeat_code_et)
    EditText repeatCodeEt;
    @InjectView(R.id.error_message)
    TextView errorMessage;
    @InjectView(R.id.icon_user_protocol)
    ImageView iconUserProtocol;
    @InjectView(R.id.user_protocol)
    TextView userProtocol;
    @InjectView(R.id.tv_register)
    TextView tvRegister;


    String verificationStr, repeatPassword, pwdStr, info;
    RegisterCheck registerCheck;
    String mobileStr;
    boolean hideRepet = false;
    boolean hide = false;
    private MyCountDownTimer mTimer;
    private boolean mOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        initHead();
        initView();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(),R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("注册", getResources().getColor(R.color.white));
        getHeadBar().showRightText();
        getHeadBar().setRightTitle("登录", getResources().getColor(R.color.white));
        getHeadBar().setRightOnClickListner(v -> startActivity(createIntent(LoginActivity.class)));
    }

    private void initView() {
        initTextWatcher();
        mTimer = new MyCountDownTimer(60000, 1000, tvSendVerification);
        mTimer.setTickTxt("秒后重新发送");
        mTimer.setListener(() -> {
            errorMessage.setVisibility(View.VISIBLE);
            errorMessage.setText("短信验证码无效，请重新点击发送验证码");
//            registerVerificationEt.setText("");
//                registerVerificationEt.setEnabled(false);
            registerVerificationEt.clearFocus();
        });
        tvRegister.setEnabled(false);
        tvSendVerification.setEnabled(false);
        registerVerificationEt.setEnabled(false);
    }

    private void initTextWatcher() {
        registerMobileEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = registerMobileEt.getText().toString().trim();
                if(Util.compileExChar(str)){
                    str = str.substring(0,str.length()-1);
                    registerMobileEt.setText(str);
                    registerMobileEt.setSelection(str.length());
                }
                if(registerMobileEt.getText().toString().trim().length() == 11){
                    tvSendVerification.setEnabled(true);
                }else{
                    tvSendVerification.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        registerVerificationEt.addTextChangedListener(getTextWatcher());
        registerPwdEt.addTextChangedListener(getTextWatcher());
        repeatPwdEt.addTextChangedListener(getTextWatcher());
    }



    private TextWatcher getTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                errorMessage.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    @OnClick({R.id.iv_xianshi, R.id.repeat_iv_xianshi, R.id.tv_register,R.id.tv_send_verification,R.id.user_protocol,R.id.icon_user_protocol})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send_verification:
                //发送验证码
                mobileStr = registerMobileEt.getText().toString().trim();
                if (TextUtils.isEmpty(mobileStr)) {
                    showToast("请输入手机号！");
                } else if (mobileStr.length() != 11) {
                    showToast("请输入11位手机号！");
                } else if (!Util.isPhone(mobileStr)) {
                    showToast("请输入合法手机号!");
                } else {
                    if(!NetUtils.hasNetwork(App.getContext())){
                        showToast("获取验证码失败，请检查网络");
                        return;
                    }
                    ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
                    UserService api = ApiUtil.createDefaultApi(UserService.class,ApiUtil.SMURL);
                    ApiUtil.doDefaultApi(api.verification(mobileStr,"1"), resEntity -> {
                        if(resEntity.getResCode().equals("0")){
                            registerVerificationEt.setEnabled(true);
                            mTimer.start();
                        }else{
                            showToast(resEntity.getResMsg());
                        }
                    }, viewControl);
                }
                break;
            case R.id.iv_xianshi:
                if (hide) {
                    ivXianshi.setImageResource(R.drawable.hide);
                    registerPwdEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    ivXianshi.setImageResource(R.drawable.show);
                    registerPwdEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                hide = !hide;
                registerPwdEt.postInvalidate();
                registerPwdEt.setSelection(registerPwdEt.getText().toString().length());
                break;
            case R.id.repeat_iv_xianshi:
                if (hideRepet) {
                    repeatIvXianshi.setImageResource(R.drawable.hide);
                    repeatPwdEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    repeatIvXianshi.setImageResource(R.drawable.show);
                    repeatPwdEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                hideRepet = !hideRepet;
                repeatPwdEt.postInvalidate();
                repeatPwdEt.setSelection(repeatPwdEt.getText().toString().length());
                break;
            case R.id.tv_register:
                if (!mOpen) {
                    showToast("请先阅读用户协议，点击勾选用户协议图标");
                    return;
                }
                verificationStr = registerVerificationEt.getText().toString().trim();
                repeatPassword = repeatPwdEt.getText().toString().trim();
                pwdStr = registerPwdEt.getText().toString().trim();
                mobileStr = registerMobileEt.getText().toString().trim();
                String codeet = repeatCodeEt.getText().toString().trim();
                if (TextUtils.isEmpty(verificationStr)) {
                    errorMessage.setVisibility(View.VISIBLE);
                    errorMessage.setText("请先获取验证码");
                    return;
                }
                registerCheck = new RegisterCheck(mobileStr, verificationStr, pwdStr, repeatPassword);
                info = registerCheck.getCheckInfo();
                if (!TextUtils.isEmpty(info)) {
                    errorMessage.setVisibility(View.VISIBLE);
                    errorMessage.setText(info);
                    return;
                }
                ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
                UserService api = ApiUtil.createDefaultApi(UserService.class, ApiUtil.SMURL);
                UserService red_api = ApiUtil.createDefaultApi(UserService.class, ApiUtil.HEHE_URL);
                ApiUtil.doDefaultApi(api.register(mobileStr,verificationStr,codeet,pwdStr), user -> {
                    if(user.getResCode().equals("0")) {
                        if(!TextUtils.isEmpty(codeet)){
                            ApiUtil.doDefaultApi(red_api.redpaper("1", "1", codeet, "", ""), data -> {
                                if(data.getCode().equals("0"))
                                    showToast(data.getMessage());
                                if(data.getCode().equals("1")) {
                                    App.USERID = user.getE_sjzc_nm();
                                    App.TOKEN = user.getToken();
                                    Intent userData = createIntent(PerfectUserDataActivity.class);
                                    userData.putExtra("phone", mobileStr);
                                    userData.putExtra("pwd", pwdStr);
                                    startActivity(userData);
                                    finish();
                                }
                                if(data.getCode().equals("2"))
                                    showToast(data.getMessage());
                            }, viewControl);
                        }
                    }else{
                        showToast(user.getResMsg());
                    }
                }, viewControl);
                break;
            case R.id.user_protocol:
                showToast("用户协议");
                break;
            case R.id.icon_user_protocol:
                if (mOpen) {
                    iconUserProtocol.setImageDrawable(getResources().getDrawable(R.drawable.protocol));
                } else {
                    iconUserProtocol.setImageDrawable(getResources().getDrawable(R.drawable.protocol_select));
                }
                mOpen = !mOpen;
                refreshButton();
                break;
        }
    }

    public void refreshButton(){
        if(!mOpen){
            tvRegister.setEnabled(false);
        }else{
            tvRegister.setEnabled(true);
        }
    }
}
