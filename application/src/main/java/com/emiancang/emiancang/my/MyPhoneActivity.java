package com.emiancang.emiancang.my;

import android.os.Bundle;
import android.view.View;
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

public class MyPhoneActivity extends TitleActivity {


    @InjectView(R.id.my_phone_number)
    TextView myPhoneNumber;
    @InjectView(R.id.my_phone_update)
    TextView myPhoneUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_my_phone, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("手机号码", getResources().getColor(R.color.white));
    }

    private void initView() {
        myPhoneNumber.setText(UserManager.getInstance().getUser().getESjzcSjh());
    }

    @OnClick(R.id.my_phone_update)
    public void onClick() {
        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        UserService api = ApiUtil.createDefaultApi(UserService.class,ApiUtil.SMURL);
        ApiUtil.doDefaultApi(api.verification(UserManager.getInstance().getUser().getESjzcSjh(),"1"), new HttpSucess<ResEntity>() {
            @Override
            public void onSucess(ResEntity resEntity) {
                if(resEntity.getResCode().equals("0")){
                    startActivity(createIntent(MyVerificationCodeActivity.class));
                }else{
                    showToast(resEntity.getResMsg());
                }
            }
        }, viewControl);
    }
}
