package com.emiancang.emiancang.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.bean.EnterpriseInfo;
import com.emiancang.emiancang.bean.ResEntity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.emiancang.emiancang.R.id.phone;

/**
 * Created by 22310 on 2016/11/7.
 */

public class MyEnterpriseActivity extends TitleActivity {

    @InjectView(R.id.enterprise_name)
    TextView enterpriseName;
    @InjectView(R.id.enterprise_name_layout)
    RelativeLayout enterpriseNameLayout;
    @InjectView(R.id.enterprise_type)
    TextView enterpriseType;
    @InjectView(R.id.enterprise_type_layout)
    RelativeLayout enterpriseTypeLayout;
    @InjectView(R.id.enterprise_location)
    TextView enterpriseLocation;
    @InjectView(R.id.enterprise_location_layout)
    RelativeLayout enterpriseLocationLayout;
    @InjectView(R.id.enterprise_site)
    TextView enterpriseSite;
    @InjectView(R.id.enterprise_site_layout)
    RelativeLayout enterpriseSiteLayout;
    @InjectView(R.id.enterprise_phone)
    TextView enterprisePhone;
    @InjectView(R.id.enterprise_phone_layout)
    RelativeLayout enterprisePhoneLayout;
    @InjectView(R.id.enterprise_code_goto)
    ImageView enterpriseCodeGoto;
    @InjectView(R.id.enterprise_code_layout)
    RelativeLayout enterpriseCodeLayout;
    @InjectView(R.id.enterprise_revenue_goto)
    ImageView enterpriseRevenueGoto;
    @InjectView(R.id.enterprise_revenue_layout)
    RelativeLayout enterpriseRevenueLayout;
    @InjectView(R.id.enterprise_business_goto)
    ImageView enterpriseBusinessGoto;
    @InjectView(R.id.enterprise_business_layout)
    RelativeLayout enterpriseBusinessLayout;
    @InjectView(R.id.enterprise_authorization_goto)
    ImageView enterpriseAuthorizationGoto;
    @InjectView(R.id.enterprise_authorization_layout)
    RelativeLayout enterpriseAuthorizationLayout;

    private EnterpriseInfo mEnterpriseInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_my_enterprise, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("企业信息", getResources().getColor(R.color.white));
    }

    private void initView() {
        enterpriseCodeLayout.setVisibility(View.GONE);
        enterpriseRevenueLayout.setVisibility(View.GONE);
        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        UserService api = ApiUtil.createDefaultApi(UserService.class);
        ApiUtil.doDefaultApi(api.getEnterpriseInfo(UserManager.getInstance().getUser().getESjzcHynm()), new HttpSucess<EnterpriseInfo>() {
            @Override
            public void onSucess(EnterpriseInfo enterpriseInfo) {
                mEnterpriseInfo = enterpriseInfo;
                enterpriseName.setText(enterpriseInfo.getCustName());
                // 企业类型
                enterpriseType.setText(enterpriseInfo.getTypeName());
                enterpriseLocation.setText(enterpriseInfo.getAreaName());
                enterpriseSite.setText(enterpriseInfo.getCompanyAddress());
                enterprisePhone.setText(""+enterpriseInfo.getGroupContactPhone());
                if(enterpriseInfo.getLoginType().equals("ptyyzz")){
                    enterpriseCodeLayout.setVisibility(View.VISIBLE);
                    enterpriseRevenueLayout.setVisibility(View.VISIBLE);
                }
            }
        }, viewControl);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    @OnClick({R.id.enterprise_phone_layout, R.id.enterprise_code_layout, R.id.enterprise_revenue_layout, R.id.enterprise_business_layout, R.id.enterprise_authorization_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.enterprise_phone_layout:
                if(mEnterpriseInfo != null){
                    Intent connectionPhone = new Intent(Intent.ACTION_DIAL);
                    connectionPhone.setData(Uri.parse("tel:" + mEnterpriseInfo.getGroupContactPhone()));
                    startActivity(connectionPhone);
                }else{
                    showToast("获取企业信息失败");
                }
                break;
            case R.id.enterprise_code_layout:
                if(mEnterpriseInfo != null){
                    if(mEnterpriseInfo.getLoginType().equals("szhy")){
                        showToast("当前企业申请的是三证合一不包含企业组织机构代码");
                    }else{
                        if(TextUtils.isEmpty(mEnterpriseInfo.getQyzzjgImg())){
                            showToast("该企业没有上传企业组织机构代码");
                        }else{
                            Intent code = createIntent(MyEnterpriseImageActivity.class);
                            code.putExtra("title","企业组织机构代码");
                            code.putExtra("iamge",mEnterpriseInfo.getQyzzjgImg());
                            startActivity(code);
                        }
                    }
                }else{
                    showToast("获取企业信息失败");
                }
                break;
            case R.id.enterprise_revenue_layout:
                if(mEnterpriseInfo != null){
                    if(TextUtils.isEmpty(mEnterpriseInfo.getLoginType())){
                        showToast("该企业没有上传税务登记证");
                    }else{
                        if(mEnterpriseInfo.getLoginType().equals("szhy")){
                            showToast("当前企业申请的是三证合一不包含税务登记证");
                        }else{
                            if(TextUtils.isEmpty(mEnterpriseInfo.getSwdjzhImg())){
                                showToast("该企业没有上传税务登记证");
                            }else{
                                Intent revenue = createIntent(MyEnterpriseImageActivity.class);
                                revenue.putExtra("title","税务登记证");
                                revenue.putExtra("iamge",mEnterpriseInfo.getSwdjzhImg());
                                startActivity(revenue);
                            }
                        }
                    }

                }else{
                    showToast("获取企业信息失败");
                }
                break;
            case R.id.enterprise_business_layout:
                if(TextUtils.isEmpty(mEnterpriseInfo.getLoginType())){
                    showToast("该企业没有上传工商营业执照");
                }else{
                    if(mEnterpriseInfo.getLoginType().equals("szhy")){
                        if(TextUtils.isEmpty(mEnterpriseInfo.getSzhygsyyzz())){
                            showToast("该企业没有上传工商营业执照");
                        }else{
                                Intent revenue = createIntent(MyEnterpriseImageActivity.class);
                                revenue.putExtra("title","工商营业执照");
                                revenue.putExtra("iamge",mEnterpriseInfo.getSzhygsyyzz());
                                startActivity(revenue);
                        }
                    }else{
                            if(TextUtils.isEmpty(mEnterpriseInfo.getGszzImg())){
                                showToast("该企业没有上传工商营业执照");
                            }else{
                                Intent revenue = createIntent(MyEnterpriseImageActivity.class);
                                revenue.putExtra("title","工商营业执照");
                                revenue.putExtra("iamge",mEnterpriseInfo.getGszzImg());
                                startActivity(revenue);
                            }
                    }
                }

                break;
            case R.id.enterprise_authorization_layout:
                if(TextUtils.isEmpty(mEnterpriseInfo.getLoginType())){
                    showToast("该企业没有上传企业认证授权书");
                }else{
                    if(mEnterpriseInfo.getLoginType().equals("szhy")){
                        if(TextUtils.isEmpty(mEnterpriseInfo.getSzhyqyrzsqs())){
                            showToast("该企业没有上传企业认证授权书");
                        }else{
                            Intent revenue = createIntent(MyEnterpriseImageActivity.class);
                            revenue.putExtra("title","企业认证授权书");
                            revenue.putExtra("iamge",mEnterpriseInfo.getSzhyqyrzsqs());
                            startActivity(revenue);
                        }
                    }else{
                        if(TextUtils.isEmpty(mEnterpriseInfo.getQyrzsqs())){
                            showToast("该企业没有上传企业认证授权书");
                        }else{
                            Intent revenue = createIntent(MyEnterpriseImageActivity.class);
                            revenue.putExtra("title","企业认证授权书");
                            revenue.putExtra("iamge",mEnterpriseInfo.getQyrzsqs());
                            startActivity(revenue);
                        }
                    }
                }
                break;
        }
    }
}
