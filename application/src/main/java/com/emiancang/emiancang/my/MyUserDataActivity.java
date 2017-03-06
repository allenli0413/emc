package com.emiancang.emiancang.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.igexin.push.core.g.U;
import static com.emiancang.emiancang.R.id.view;

/**
 * Created by 22310 on 2016/11/7.
 */

public class MyUserDataActivity extends TitleActivity {

    public final int UPLOAD_AVATAR_TYPE = 10000;


    @InjectView(R.id.iv_nicheng_goto)
    ImageView ivNichengGoto;
    @InjectView(R.id.my_user_data_avater)
    SimpleDraweeView myUserDataAvater;
    @InjectView(R.id.user_data_avater)
    RelativeLayout userDataAvater;
    @InjectView(R.id.user_name_goto)
    ImageView userNameGoto;
    @InjectView(R.id.my_user_data_name)
    TextView myUserDataName;
    @InjectView(R.id.user_data_name)
    RelativeLayout userDataName;
    @InjectView(R.id.user_phone_goto)
    ImageView userPhoneGoto;
    @InjectView(R.id.my_user_data_phone)
    TextView myUserDataPhone;
    @InjectView(R.id.user_data_phone)
    RelativeLayout userDataPhone;
    @InjectView(R.id.user_enterprise_goto)
    ImageView userEnterpriseGoto;
    @InjectView(R.id.my_user_data_enterprise)
    TextView myUserDataEnterprise;
    @InjectView(R.id.user_data_enterprise)
    RelativeLayout userDataEnterprise;
    @InjectView(R.id.user_salesman_goto)
    ImageView userSalesmanGoto;
    @InjectView(R.id.my_user_data_salesman)
    TextView myUserDataSalesman;
    @InjectView(R.id.user_data_salesman)
    RelativeLayout userDataSalesman;
    @InjectView(R.id.user_type_admin)
    LinearLayout userTypeAdmin;
    @InjectView(R.id.user_account_goto)
    ImageView userAccountGoto;
    @InjectView(R.id.my_user_data_account)
    TextView myUserDataAccount;
    @InjectView(R.id.user_data_account)
    RelativeLayout userDataAccount;
    @InjectView(R.id.user_binding_goto)
    ImageView userBindingGoto;
    @InjectView(R.id.my_user_data_binding)
    TextView myUserDataBinding;
    @InjectView(R.id.user_data_binding)
    RelativeLayout userDataBinding;
    @InjectView(R.id.my_user_type_salesman)
    LinearLayout myUserTypeSalesman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_my_userdata, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("账号管理", getResources().getColor(R.color.white));
    }

    private void initView() {
        if(UserManager.getInstance().getUser().getESjzcHylb().equals("1")){
            //管理员
            userTypeAdmin.setVisibility(View.VISIBLE);
            myUserTypeSalesman.setVisibility(View.GONE);
            userDataBinding.setVisibility(View.GONE);

            if (TextUtils.isEmpty(UserManager.getInstance().getUser().getCustState())){
                myUserDataEnterprise.setText("审核未通过");
            } else {
                if (UserManager.getInstance().getUser().getCustState().equals("0"))
                    myUserDataEnterprise.setText("审核中");
                if (UserManager.getInstance().getUser().getCustState().equals("1"))
                    myUserDataEnterprise.setText(UserManager.getInstance().getUser().getCustName());
                if (UserManager.getInstance().getUser().getCustState().equals("2"))
                    myUserDataEnterprise.setText("审核未通过");
            }
        }else{
            //业务员
            userDataBinding.setVisibility(View.VISIBLE);
            userTypeAdmin.setVisibility(View.GONE);
            myUserTypeSalesman.setVisibility(View.VISIBLE);
            if (UserManager.getInstance().getUser().getESjzcHylb().equals("0")) {
                userDataAccount.setVisibility(View.VISIBLE);
            } else {
                userDataAccount.setVisibility(View.GONE);
            }
        }
    }

    @OnClick({R.id.user_data_avater, R.id.user_data_name, R.id.user_data_phone, R.id.user_data_enterprise, R.id.user_data_salesman,R.id.user_data_account, R.id.user_data_binding})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_data_avater:
                startActivity(createIntent(MyUserAvaterActivity.class));
                break;
            case R.id.user_data_name:
                startActivity(createIntent(MyUserUpdateName.class));
                break;
            case R.id.user_data_phone:
                startActivity(createIntent(MyPhoneActivity.class));
                break;
            case R.id.user_data_enterprise:
                if(TextUtils.isEmpty(UserManager.getInstance().getUser().getCustState())){
                    startActivity(createIntent(MyEnterpriseAccountActivity.class));
                } else {
                    if (UserManager.getInstance().getUser().getCustState().equals("0")) {
                        Intent intent = createIntent(EnterpriseCheckActivity.class);
                        intent.putExtra("type", 0);
                        startActivity(intent);
                    }
                    if (UserManager.getInstance().getUser().getCustState().equals("1"))
                        startActivity(createIntent(MyEnterpriseActivity.class));
                    if (UserManager.getInstance().getUser().getCustState().equals("2"))
                        startActivity(createIntent(MyEnterpriseAccountActivity.class));
                }
                break;
            case R.id.user_data_salesman:
                startActivity(createIntent(MySalesmanManageActivity.class));
                break;
            case R.id.user_data_account:
                startActivity(createIntent(MyEnterpriseAccountActivity.class));
                break;
            case R.id.user_data_binding:
                //企业绑定
                if(TextUtils.isEmpty(UserManager.getInstance().getUser().getESjzcHynm())){
                    startActivity(createIntent(MyEnterpriseBindingActivity.class));
                }else{
                    startActivity(createIntent(MyEnterpriseIsBinding.class));
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = UserManager.getInstance().getUser();
        if (!TextUtils.isEmpty(user.getESjzcXm())) {
            myUserDataName.setText(user.getESjzcXm());
        }
        if (!TextUtils.isEmpty(user.getESjzcSjh())) {
            myUserDataPhone.setText(user.getESjzcSjh());
        }
        //用户头像
        if (!TextUtils.isEmpty(user.getEYhtx())) {
            myUserDataAvater.setImageURI(Uri.parse(user.getEYhtx()));
        }
        if(UserManager.getInstance().getUser().getESjzcHylb().equals("1")) {
            if(TextUtils.isEmpty(UserManager.getInstance().getUser().getCustState())){
                myUserDataEnterprise.setText("审核未通过");
            } else {
                if (UserManager.getInstance().getUser().getCustState().equals("0"))
                    myUserDataEnterprise.setText("审核中");
                if (UserManager.getInstance().getUser().getCustState().equals("1"))
                    myUserDataEnterprise.setText(UserManager.getInstance().getUser().getCustName());
                if (UserManager.getInstance().getUser().getCustState().equals("2"))
                    myUserDataEnterprise.setText("审核未通过");
            }
        }

        if(UserManager.getInstance().getUser().getESjzcHylb().equals("0")){
            myUserDataBinding.setText("未绑定");
        }else{
            myUserDataBinding.setText("已绑定");
        }
        myUserDataSalesman.setText(""+user.getSalesmanNum());
    }
}
