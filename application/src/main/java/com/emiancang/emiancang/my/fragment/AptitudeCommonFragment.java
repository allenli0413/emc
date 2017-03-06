package com.emiancang.emiancang.my.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.emiancang.emiancang.eventbean.EnterpriseApplyTransfer;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.SelectImagInfo;
import com.emiancang.emiancang.base.TitleFragment;
import com.emiancang.emiancang.bean.AptitudeInfo;
import com.emiancang.emiancang.dialog.ImageSelectDialog;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.my.EnterpriseCheckActivity;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.litesuits.common.assist.LogReader.open;

/**
 * 主页选项卡
 * Created by 22310 on 2016/4/8.
 */
public class AptitudeCommonFragment extends TitleFragment {

    public final int UPLOAD_ORGANIZATION_TYPE = 10000;
    public final int UPLOAD_REVENUE_TYPE = 20000;
    public final int UPLOAD_INDUSTRY_TYPE = 30000;
    public final int UPLOAD_ENTERPRISE_TYPE = 40000;


    @InjectView(R.id.organization_image)
    ImageView organizationImage;
    @InjectView(R.id.organization_image_add)
    ImageView organizationImageAdd;
    @InjectView(R.id.organization_text)
    TextView organizationText;
    @InjectView(R.id.organization_layout)
    RelativeLayout organizationLayout;
    @InjectView(R.id.revenue_image)
    ImageView revenueImage;
    @InjectView(R.id.revenue_image_add)
    ImageView revenueImageAdd;
    @InjectView(R.id.revenue_text)
    TextView revenueText;
    @InjectView(R.id.revenue_layout)
    RelativeLayout revenueLayout;
    @InjectView(R.id.industry_image)
    ImageView industryImage;
    @InjectView(R.id.industry_image_add)
    ImageView industryImageAdd;
    @InjectView(R.id.industry_text)
    TextView industryText;
    @InjectView(R.id.ndustry_layout)
    RelativeLayout ndustryLayout;
    @InjectView(R.id.enterprise_image)
    ImageView enterpriseImage;
    @InjectView(R.id.enterprise_image_add)
    ImageView enterpriseImageAdd;
    @InjectView(R.id.enterprise_text)
    TextView enterpriseText;
    @InjectView(R.id.enterprise_layout)
    RelativeLayout enterpriseLayout;
    @InjectView(R.id.combination_next)
    TextView combinationNext;
    @InjectView(R.id.delete_organization)
    ImageView deleteOrganization;
    @InjectView(R.id.delete_revenue)
    ImageView deleteRevenue;
    @InjectView(R.id.delete_industry)
    ImageView deleteIndustry;
    @InjectView(R.id.delete_enterprise)
    ImageView deleteEnterprise;

    private boolean ImageIndustry = false;
    private String ImageIndustryPath = "";
    private boolean ImageEnterprise = false;
    private String ImageEnterprisePath = "";
    private boolean ImageOrganization = false;
    private String ImageOrganizationPath = "";
    private boolean ImageRevenue = false;
    private String ImageRevenuePath = "";


    private String mCustId;

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_aptitude_common, null);
        ButterKnife.inject(this, view);
        getHeadBar().hideHeader();
        initView();
        EventBus.getDefault().register(this);
        return view;
    }

    private void initView() {
        Util.setStatusBarColor(getActivity(), R.color.green);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void openImageDialog(int type) {
        ImageSelectDialog dialog = new ImageSelectDialog(getActivity(), false, type);
        dialog.show();
    }

    public void setCustId(String custId){
        this.mCustId = custId;
    }

    @OnClick({R.id.organization_layout, R.id.revenue_layout, R.id.ndustry_layout, R.id.enterprise_layout, R.id.combination_next,R.id.delete_organization,R.id.delete_revenue,R.id.delete_enterprise,R.id.delete_industry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.organization_layout:
                openImageDialog(UPLOAD_ORGANIZATION_TYPE);
                ImageOrganization = true;
                break;
            case R.id.revenue_layout:
                openImageDialog(UPLOAD_REVENUE_TYPE);
                ImageRevenue = true;
                break;
            case R.id.ndustry_layout:
                openImageDialog(UPLOAD_INDUSTRY_TYPE);
                ImageIndustry = true;
                break;
            case R.id.enterprise_layout:
                openImageDialog(UPLOAD_ENTERPRISE_TYPE);
                ImageEnterprise = true;
                break;
            case R.id.delete_organization:
                organizationImage.setImageResource(R.drawable.account_bitmap);
                organizationImageAdd.setVisibility(View.VISIBLE);
                organizationText.setVisibility(View.VISIBLE);
                deleteOrganization.setVisibility(View.GONE);
                ImageOrganization = false;
                break;
            case R.id.delete_revenue:
                revenueImage.setImageResource(R.drawable.account_bitmap);
                revenueImageAdd.setVisibility(View.VISIBLE);
                revenueText.setVisibility(View.VISIBLE);
                deleteRevenue.setVisibility(View.GONE);
                ImageRevenue = false;
                break;
            case R.id.delete_industry:
                industryImage.setImageResource(R.drawable.account_bitmap);
                industryImageAdd.setVisibility(View.VISIBLE);
                industryText.setVisibility(View.VISIBLE);
                deleteIndustry.setVisibility(View.GONE);
                ImageIndustry = false;
                break;
            case R.id.delete_enterprise:
                enterpriseImage.setImageResource(R.drawable.account_bitmap);
                enterpriseImageAdd.setVisibility(View.VISIBLE);
                enterpriseText.setVisibility(View.VISIBLE);
                deleteEnterprise.setVisibility(View.GONE);
                ImageEnterprise = false;
                break;
            case R.id.combination_next:
                if(!ImageOrganization || !ImageRevenue || !ImageIndustry || !ImageEnterprise){
                    showToast("您提交信息不全，请补全缺少信息。");
                    return;
                }
                ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
                UserService service = ApiUtil.createDefaultApi(UserService.class);
                Map<String,String> map = new HashMap<>();
                map.put("custId",mCustId);
                map.put("loginType","ptyyzz");
                ApiUtil.mapMultipart = map;
                File ImageIndustry = new File(ImageIndustryPath);
                File mageEnterprise = new File(ImageEnterprisePath);
                File ImageOrganization = new File(ImageOrganizationPath);
                File ImageRevenue = new File(ImageRevenuePath);
                if(ImageIndustry.exists() && mageEnterprise.exists() && ImageOrganization.exists() && ImageRevenue.exists()){
                    RequestBody ImageIndustryFile =
                            RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), ImageIndustry);
                    MultipartBody.Part ImageIndustrybody = MultipartBody.Part.createFormData("szhygsyyzz", ImageIndustry.getName(), ImageIndustryFile);
                    RequestBody ImageEnterpriseFile =
                            RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"),mageEnterprise );
                    MultipartBody.Part ImageEnterprisebody = MultipartBody.Part.createFormData("szhygsyyzz", mageEnterprise.getName(), ImageEnterpriseFile);
                    RequestBody ImageOrganizationFile =
                            RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"),ImageOrganization);
                    MultipartBody.Part ImageOrganizationbody = MultipartBody.Part.createFormData("szhygsyyzz", ImageOrganization.getName(), ImageOrganizationFile);
                    RequestBody ImageRevenueFile =
                            RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), ImageRevenue);
                    MultipartBody.Part mageRevenuebody = MultipartBody.Part.createFormData("szhygsyyzz", ImageRevenue.getName(), ImageRevenueFile);
                    ApiUtil.doDefaultApi(service.submitAptitudeInfo2(ImageIndustrybody,ImageEnterprisebody,ImageOrganizationbody,mageRevenuebody), aptitudeInfo -> {
                        SharedPrefsUtil.remove(getActivity(), SharedPrefsUtil.CUSTID);
                        EventBus.getDefault().post(new EnterpriseApplyTransfer());
                        startActivity(createIntent(EnterpriseCheckActivity.class));
                        getActivity().finish();
                    }, viewControl);
                }
                break;
        }
    }

    public void onEventMainThread(SelectImagInfo imageInfo) {
        if (imageInfo != null) {
            switch (imageInfo.getType()) {
                case UPLOAD_INDUSTRY_TYPE:
                    String industry_url = imageInfo.getImagePath();
                    if (!TextUtils.isEmpty(industry_url)) {
                        open = true;
                        ImageIndustryPath = industry_url;
                        industryImage.setImageURI(Uri.parse(industry_url));
                        industryImageAdd.setVisibility(View.GONE);
                        industryText.setVisibility(View.GONE);
                        deleteIndustry.setVisibility(View.VISIBLE);
                    }
                    break;
                case UPLOAD_ENTERPRISE_TYPE:
                    String enterprise_url = imageInfo.getImagePath();
                    if (!TextUtils.isEmpty(enterprise_url)) {
                        open = true;
                        ImageEnterprisePath = enterprise_url;
                        enterpriseImage.setImageURI(Uri.parse(enterprise_url));
                        enterpriseImageAdd.setVisibility(View.GONE);
                        enterpriseText.setVisibility(View.GONE);
                        deleteEnterprise.setVisibility(View.VISIBLE);
                    }
                    break;

                case UPLOAD_ORGANIZATION_TYPE:
                    String organization_url = imageInfo.getImagePath();
                    if (!TextUtils.isEmpty(organization_url)) {
                        open = true;
                        ImageOrganizationPath = organization_url;
                        organizationImage.setImageURI(Uri.parse(organization_url));
                        organizationImageAdd.setVisibility(View.GONE);
                        organizationText.setVisibility(View.GONE);
                        deleteOrganization.setVisibility(View.VISIBLE);
                    }
                    break;
                case UPLOAD_REVENUE_TYPE:
                    String revenue_url = imageInfo.getImagePath();
                    if (!TextUtils.isEmpty(revenue_url)) {
                        open = true;
                        ImageRevenuePath = revenue_url;
                        revenueImage.setImageURI(Uri.parse(revenue_url));
                        revenueImageAdd.setVisibility(View.GONE);
                        revenueText.setVisibility(View.GONE);
                        deleteRevenue.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
