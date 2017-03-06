package com.emiancang.emiancang.my.fragment;

import android.content.Intent;
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
import com.litesuits.android.log.Log;
import com.litesuits.common.utils.AppUtil;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.sina.weibo.sdk.constant.WBPageConstants;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.SelectImagInfo;
import com.emiancang.emiancang.base.TitleFragment;
import com.emiancang.emiancang.bean.AptitudeInfo;
import com.emiancang.emiancang.bean.EnterpriseInfo;
import com.emiancang.emiancang.dialog.ImageSelectDialog;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.my.EnterpriseAptitudeActivity;
import com.emiancang.emiancang.my.EnterpriseCheckActivity;
import com.emiancang.emiancang.my.MyEnterpriseAccountActivity;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.igexin.sdk.PushService.context;
import static com.litesuits.common.assist.LogReader.open;
import static com.emiancang.emiancang.uitl.SharedPrefsUtil.WAREHOUSEID;

/**
 * 主页选项卡
 * Created by 22310 on 2016/4/8.
 */
public class AptitudeCombinationFragment extends TitleFragment {

    public final int UPLOAD_INDUSTRY_TYPE = 10000;
    public final int UPLOAD_ENTERPRISE_TYPE = 20000;


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
    @InjectView(R.id.delete_ndustry)
    ImageView deleteNdustry;
    @InjectView(R.id.enterprise_ndustry)
    ImageView enterpriseNdustry;

    private boolean setImageIndustry = false;
    private String ImageIndustryPath = "";
    private boolean setImageEnterprise = false;
    private String ImageEnterprisePath = "";

    private String mCustId;

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_aptitude_combination, null);
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

    public void setCustId(String custId){
        this.mCustId = custId;
    }

    @OnClick({R.id.ndustry_layout, R.id.enterprise_layout, R.id.combination_next,R.id.delete_ndustry, R.id.enterprise_ndustry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ndustry_layout:
                openImageDialog(UPLOAD_INDUSTRY_TYPE);
                setImageIndustry = true;
                break;
            case R.id.enterprise_layout:
                openImageDialog(UPLOAD_ENTERPRISE_TYPE);
                setImageEnterprise = true;
                break;
            case R.id.delete_ndustry:
                industryImage.setImageResource(R.drawable.account_bitmap);
                industryImageAdd.setVisibility(View.VISIBLE);
                industryText.setVisibility(View.VISIBLE);
                deleteNdustry.setVisibility(View.GONE);
                setImageIndustry = false;
                break;
            case R.id.enterprise_ndustry:
                enterpriseImage.setImageResource(R.drawable.account_bitmap);
                enterpriseImageAdd.setVisibility(View.VISIBLE);
                enterpriseText.setVisibility(View.VISIBLE);
                enterpriseNdustry.setVisibility(View.GONE);
                setImageEnterprise = false;
                break;
            case R.id.combination_next:
                if(!setImageIndustry || !setImageEnterprise){
                    showToast("您提交信息不全，请补全缺少信息。");
                    return;
                }
                ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
                UserService service = ApiUtil.createDefaultApi(UserService.class);
                Map<String,String> map = new HashMap<>();
                map.put("custId",mCustId);
                map.put("loginType","szhy");
                ApiUtil.mapMultipart = map;
                File ImageIndustry = new File(ImageIndustryPath);
                File ImageEnterprise = new File(ImageEnterprisePath);
                if(ImageIndustry.exists() && ImageEnterprise.exists()){
                    RequestBody ImageIndustryFile =RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"),ImageIndustry);
                    MultipartBody.Part ImageIndustrybody = MultipartBody.Part.createFormData("szhygsyyzz", ImageIndustry.getName(), ImageIndustryFile);
                    RequestBody ImageEnterpriseFile =RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), ImageEnterprise);
                    MultipartBody.Part ImageEnterprisebody = MultipartBody.Part.createFormData("szhyqyrzsqs", ImageEnterprise.getName(), ImageEnterpriseFile);
                    ApiUtil.doDefaultApi(service.submitAptitudeInfo(ImageIndustrybody,ImageEnterprisebody), aptitudeInfo -> {
                        ApiUtil.mapMultipart = null;
                        SharedPrefsUtil.remove(getActivity(), SharedPrefsUtil.CUSTID);
                        EventBus.getDefault().post(new EnterpriseApplyTransfer());
                        startActivity(createIntent(EnterpriseCheckActivity.class));
                        getActivity().finish();
                    }, viewControl);
                }
                break;
        }
    }

    private void openImageDialog(int type) {
        ImageSelectDialog dialog = new ImageSelectDialog(getActivity(), false, type);
        dialog.show();
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
                        deleteNdustry.setVisibility(View.VISIBLE);
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
                        enterpriseNdustry.setVisibility(View.VISIBLE);
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
