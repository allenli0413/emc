package com.emiancang.emiancang.my;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.bean.ForgetPassword;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 22310 on 2016/11/29.
 */

public class MyEnterpriseIsBinding extends TitleActivity {

    @InjectView(R.id.my_enterprise_avater)
    SimpleDraweeView myEnterpriseAvater;
    @InjectView(R.id.enterprise_name)
    TextView enterpriseName;

    public String URL = "http://172.16.33.14:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_my_isbinding, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("企业绑定", getResources().getColor(R.color.white));
    }

    private void initView() {
        enterpriseName.setText(UserManager.getInstance().getUser().getCustName());
        String custId = UserManager.getInstance().getUser().getESjzcHynm();
        if(!TextUtils.isEmpty(custId)) {
            ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
            UserService api = ApiUtil.createDefaultApi(UserService.class);
    //        ApiUtil.doDefaultApi(api.getQyLogo(UserManager.getInstance().getUser().getESjzcHynm()), new HttpSucess<ForgetPassword>() {
    //            @Override
    //            public void onSucess(ForgetPassword forget) {
    //                if(forget.getResCode().equals("1")){
    //                    myEnterpriseAvater.setImageURI(Uri.parse(URL+forget.getLogoImg()));
    //                }
    //            }
    //        }, viewControl);
            ApiUtil.doDefaultApi(api.getCompanyDetails(custId), data -> {
                if (!TextUtils.isEmpty(data.getCompany().getLevelLogo()))
                    myEnterpriseAvater.setImageURI(Uri.parse(data.getCompany().getLevelLogo()));
            }, viewControl);
        }
    }

}
