package com.emiancang.emiancang.nearby;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.bean.CompanyDetails;
import com.emiancang.emiancang.bean.CompanyErrorInfo;
import com.emiancang.emiancang.bean.ResEntity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.main.home.qualityquery.activity.QualityContrastDetailActivity;
import com.emiancang.emiancang.main.home.qualityquery.model.QualityQueryModel;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.UserManager;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.zwyl.ZwyOSInfo;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 22310 on 2016/11/13.
 */

public class NearbyDetailsActivity extends TitleActivity {


    @InjectView(R.id.enterprise_avater)
    SimpleDraweeView enterpriseAvater;
    @InjectView(R.id.enterprise_name)
    TextView enterpriseName;
    @InjectView(R.id.enterprise_user_name)
    TextView enterpriseUserName;
    @InjectView(R.id.enterprise_site_name)
    TextView enterpriseSiteName;
    @InjectView(R.id.enterprise_site_layout)
    LinearLayout enterpriseSiteLayout;
    @InjectView(R.id.enterprise_mobile_phone_number)
    TextView enterpriseMobilePhoneNumber;
    @InjectView(R.id.enterprise_mobile_phone_layout)
    LinearLayout enterpriseMobilePhoneLayout;
    @InjectView(R.id.enterprise_phone_number)
    TextView enterprisePhoneNumber;
    @InjectView(R.id.enterprise_phone_layout)
    LinearLayout enterprisePhoneLayout;
    @InjectView(R.id.enterprise_fax_phone_number)
    TextView enterpriseFaxPhoneNumber;
    @InjectView(R.id.enterprise_fax_phone_layout)
    LinearLayout enterpriseFaxPhoneLayout;
    @InjectView(R.id.enterprise_email_number)
    TextView enterpriseEmailNumber;
    @InjectView(R.id.enterprise_email_layout)
    LinearLayout enterpriseEmailLayout;
    @InjectView(R.id.enterprise_brief)
    TextView enterpriseBrief;
    @InjectView(R.id.enterprise_resource_layout)
    LinearLayout enterpriseResourceLayout;
    @InjectView(R.id.product_layout)
    LinearLayout productLayout;
    @InjectView(R.id.enterprise_error)
    TextView enterpriseError;
    private String lon = "";
    private String lat = "";
    private String mCustId = "";
    private CompanyDetails.CompanyBean mCompanyBean;

    private boolean b = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_nearby_details, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();
    }

    private void initView() {
        String custId = getIntent().getStringExtra("id");

        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        UserService api = ApiUtil.createDefaultApi(UserService.class);
        String timeStamp  = ZwyOSInfo.getStringToDate();
        String nonce = ZwyOSInfo.getRandomString();
        Map map = new HashMap();
        ApiUtil.doDefaultApi(api.getCompanyDetails(custId, UserManager.getInstance().isLogin() ? UserManager.getInstance().getToken() : "", UserManager.getInstance().isLogin() ? UserManager.getInstance().getUser().getESjzcNm() : "", nonce, timeStamp, ApiUtil.getSign(map,timeStamp,nonce)), new HttpSucess<CompanyDetails>() {
            @Override
            public void onSucess(CompanyDetails companyDetails) {
                if (!TextUtils.isEmpty(companyDetails.getCompany().getLevelLogo()))
                    enterpriseAvater.setImageURI(Uri.parse(companyDetails.getCompany().getLevelLogo()));
                enterpriseName.setText(companyDetails.getCompany().getCustName());
                enterpriseUserName.setText(companyDetails.getCompany().getESjzcXm());
                enterpriseSiteName.setText(companyDetails.getCompany().getCompanyAddress());
                enterpriseMobilePhoneNumber.setText(companyDetails.getCompany().getESjzcSjh());
                enterprisePhoneNumber.setText(companyDetails.getCompany().getGroupContactPhone());
                enterpriseFaxPhoneNumber.setText(companyDetails.getCompany().getFaxNbr());
                enterpriseEmailNumber.setText(companyDetails.getCompany().getEmail());
                enterpriseBrief.setText(companyDetails.getCompany().getEnterpriseScope());
                mCompanyBean = companyDetails.getCompany();
                if(mCompanyBean.getSfsc() == 0) {
                    getHeadBar().setRightImage(R.drawable.interact_coolect_normal);
                    b = true;
                } else {
                    getHeadBar().setRightImage(R.drawable.interact_coolect_select);
                    b = false;
                }

                lon = companyDetails.getCompany().getLng();
                lat = companyDetails.getCompany().getLat();
                mCustId = companyDetails.getCompany().getCustId();
                if (companyDetails.getProductList() != null && companyDetails.getProductList().size() > 0) {
                    productLayout.setVisibility(View.VISIBLE);
                    for (int i = 0; i < companyDetails.getProductList().size(); i++) {
                        CompanyDetails.ProductListBean productListBean = companyDetails.getProductList().get(i);
                        View view = View.inflate(NearbyDetailsActivity.this, R.layout.activity_nearby_details_productitem, null);
                        ViewHolder viewHolder = new ViewHolder(view);
                        viewHolder.enterpriseResourceNumber.setText(productListBean.getGcmgyMhph());
//                        if(productListBean.getESpbsSfhbm() != null && productListBean.getESpbsSfhbm().equals("1")) {
//                            viewHolder.enterpriseResourceRedPaper.setVisibility(View.VISIBLE);
//                            viewHolder.enterpriseResourceRedPaper.setText("" + productListBean.getRedPackageMoney());
//                        } else {
//                            viewHolder.enterpriseResourceRedPaper.setVisibility(View.GONE);
//                        }
                        viewHolder.enterpriseResourceRedPaper.setText(productListBean.getZtYsj() + " 长度:" + productListBean.getHyJysjCdz() + " 马克隆值:" + productListBean.getMklz());
                        viewHolder.layout.setOnClickListener(v -> {
//                                Intent nearby = createIntent(NearbySiteActivity.class);
//                                nearby.putExtra("lon", productListBean.getLng());
//                                nearby.putExtra("lat", productListBean.getLat());
//                                startActivity(nearby);
                            Intent intent = createIntent(QualityContrastDetailActivity.class);
                            QualityQueryModel model = new QualityQueryModel();
                            model.setGcmgyMhph(productListBean.getGcmgyMhph());
                            intent.putExtra("data", model);
                            startActivity(intent);
                        });
                        enterpriseResourceLayout.addView(view);
                    }
                } else {
                    productLayout.setVisibility(View.GONE);
                }


            }
        }, viewControl);
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("企业详情", getResources().getColor(R.color.white));
        getHeadBar().showRightImage();
        getHeadBar().setRightImage(R.drawable.interact_coolect_normal);
        getHeadBar().setRightOnClickListner(v -> {
            if (b) {
                ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
                UserService api = ApiUtil.createDefaultApi(UserService.class);
                ApiUtil.doDefaultApi(api.collect("2", mCustId), resEntity -> {
                    b = false;
                    getHeadBar().setRightImage(R.drawable.interact_coolect_select);
                }, viewControl);
            } else {
                ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
                UserService api = ApiUtil.createDefaultApi(UserService.class);
                ApiUtil.doDefaultApi(api.cancelCollect("2", mCustId), resEntity -> {
                    b = true;
                    getHeadBar().setRightImage(R.drawable.interact_coolect_normal);
                }, viewControl);
            }
        });
    }

//, R.id.enterprise_resource_number_1_layout, R.id.enterprise_resource_number_2_layout, R.id.enterprise_resource_number_3_layout, R.id.enterprise_resource_number_4_layout, R.id.enterprise_resource_number_5_layout
    @OnClick({R.id.enterprise_mobile_phone_layout, R.id.enterprise_site_layout, R.id.enterprise_phone_layout, R.id.enterprise_fax_phone_layout, R.id.enterprise_error})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.enterprise_site_layout:
                Intent nearby = createIntent(NearbySiteActivity.class);
                nearby.putExtra("lon", lon);
                nearby.putExtra("lat", lat);
                startActivity(nearby);
                break;
            case R.id.enterprise_mobile_phone_layout:
                if (TextUtils.isEmpty(mCompanyBean.getESjzcSjh())) {
                    showToast("该企业没有设置手机");
                    return;
                }
                Intent mobilePhone = new Intent(Intent.ACTION_DIAL);
                mobilePhone.setData(Uri.parse("tel:" + mCompanyBean.getESjzcSjh()));
                startActivity(mobilePhone);
                break;
            case R.id.enterprise_phone_layout:
                if (TextUtils.isEmpty(mCompanyBean.getGroupContactPhone())) {
                    showToast("该企业没有设置座机");
                    return;
                }
                Intent phone = new Intent(Intent.ACTION_DIAL);
                phone.setData(Uri.parse("tel:" + mCompanyBean.getGroupContactPhone()));
                startActivity(phone);
                break;
            case R.id.enterprise_fax_phone_layout:
                if (TextUtils.isEmpty(mCompanyBean.getFaxNbr())) {
                    showToast("该企业没有设置传真");
                    return;
                }
                Intent faxPhone = new Intent(Intent.ACTION_DIAL);
                faxPhone.setData(Uri.parse("tel:" + mCompanyBean.getFaxNbr()));
                startActivity(faxPhone);
                break;
            case R.id.enterprise_error:
                if(mCompanyBean == null)
                    return;
                ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
                UserService api = ApiUtil.createDefaultApi(UserService.class);
                ApiUtil.doDefaultApi(api.getCompanyErrorInfo(mCompanyBean.getCustId()), companyErrorInfo -> {
                    if (companyErrorInfo != null && !TextUtils.isEmpty(companyErrorInfo.getFinderroeContent())) {
                        Intent errorChat = createIntent(NearbyDetailsErrorChatActivity.class);
                        errorChat.putExtra("id", mCompanyBean.getCustId());
                        startActivity(errorChat);
                    } else {
                        Intent error = createIntent(NearbyDetailsErrorActivity.class);
                        error.putExtra("id", mCompanyBean.getCustId());
                        startActivity(error);
                    }
                }, viewControl);
                break;
//            case R.id.enterprise_resource_number_1_layout:
//                startActivity(createIntent(NearbySiteActivity.class));
//                break;
//            case R.id.enterprise_resource_number_2_layout:
//                startActivity(createIntent(NearbySiteActivity.class));
//
//                break;
//            case R.id.enterprise_resource_number_3_layout:
//                startActivity(createIntent(NearbySiteActivity.class));
//
//                break;
//            case R.id.enterprise_resource_number_4_layout:
//                startActivity(createIntent(NearbySiteActivity.class));
//
//                break;
//            case R.id.enterprise_resource_number_5_layout:
//                startActivity(createIntent(NearbySiteActivity.class));
//                break;
        }
    }

    static class ViewHolder {
        @InjectView(R.id.enterprise_resource_number_1)
        TextView enterpriseResourceNumber;
        @InjectView(R.id.enterprise_resource_red_paper_1)
        TextView enterpriseResourceRedPaper;
        @InjectView(R.id.enterprise_resource_number_1_layout)
        LinearLayout layout;
        @InjectView(R.id.rl_enterprise_location)
        RelativeLayout rl_enterprise_location;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
