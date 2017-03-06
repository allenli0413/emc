package com.emiancang.emiancang.my;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.emiancang.emiancang.main.MainActivity;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.MyCountDownTimer;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 22310 on 2016/11/7.
 */

public class MyUpdatePhoneActivity extends TitleActivity {


    @InjectView(R.id.register_mobile_et)
    EditText registerMobileEt;
    @InjectView(R.id.imageView)
    TextView imageView;
    @InjectView(R.id.register_verification_et)
    EditText registerVerificationEt;
    @InjectView(R.id.tv_send_verification)
    TextView tvSendVerification;
    @InjectView(R.id.tv_register)
    TextView tvRegister;

    private MyCountDownTimer mTimer;
    String mobileStr;
    String verification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_my_update_phone, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("更换手机号码", getResources().getColor(R.color.white));
    }

    private void initView() {
        mTimer = new MyCountDownTimer(60000, 1000, tvSendVerification);
        mTimer.setListener(new MyCountDownTimer.CountDownTimerClickListener() {
            @Override
            public void TimerFinish() {
                showToast("短信验证码无效，请重新点击发送验证码");
            }
        });
    }


    @OnClick({R.id.tv_send_verification, R.id.tv_register})
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
                    ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
                    UserService api = ApiUtil.createDefaultApi(UserService.class,ApiUtil.SMURL);
                    ApiUtil.doDefaultApi(api.verification(mobileStr,"1"), new HttpSucess<ResEntity>() {
                        @Override
                        public void onSucess(ResEntity resEntity) {
                            if(resEntity.getResCode().equals("0")){
                                mTimer.start();
                            }else{
                                showToast(resEntity.getResMsg());
                            }
                        }
                    }, viewControl);
                }
                break;
            case R.id.tv_register:
                mobileStr = registerMobileEt.getText().toString().trim();
                verification = registerVerificationEt.getText().toString().trim();
                if(TextUtils.isEmpty(verification)){
                    showToast("请输入验证码");
                    return;
                }
                ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
                UserService api = ApiUtil.createDefaultApi(UserService.class);
                ApiUtil.doDefaultApi(api.changeMobile(mobileStr,verification), new HttpSucess<User>() {
                    @Override
                    public void onSucess(User user) {
                        startActivity(createIntent(MyUserDataActivity.class));
                        finish();
                    }
                }, viewControl);

                break;
        }
    }
}
