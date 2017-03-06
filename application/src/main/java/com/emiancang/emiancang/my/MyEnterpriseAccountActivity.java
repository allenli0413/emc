package com.emiancang.emiancang.my;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.emiancang.emiancang.eventbean.EnterpriseApplyTransfer;
import com.google.gson.reflect.TypeToken;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.bean.EnterpriseInfo;
import com.emiancang.emiancang.eventbean.EnterpriseAccountTransfer;
import com.emiancang.emiancang.eventbean.SYSMSGTransfer;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.JsonUtil;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.uitl.Utils;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

import static com.emiancang.emiancang.R.id.tv_home_location;
import static com.emiancang.emiancang.R.id.view;
import static com.emiancang.emiancang.R.id.warn;
import static com.emiancang.emiancang.uitl.SharedPrefsUtil.WAREHOUSEID;

/**
 * Created by 22310 on 2016/11/7.
 */

public class MyEnterpriseAccountActivity extends TitleActivity {

    @InjectView(R.id.enterprise_name_goto)
    ImageView enterpriseNameGoto;
    @InjectView(R.id.enterprise_name)
    TextView enterpriseName;
    @InjectView(R.id.enterprise_name_layout)
    RelativeLayout enterpriseNameLayout;
    @InjectView(R.id.enterprise_type_goto)
    ImageView enterpriseTypeGoto;
    @InjectView(R.id.enterprise_type)
    TextView enterpriseType;
    @InjectView(R.id.enterprise_type_layout)
    RelativeLayout enterpriseTypeLayout;
    @InjectView(R.id.enterprise_location_goto)
    ImageView enterpriseLocationGoto;
    @InjectView(R.id.enterprise_location)
    TextView enterpriseLocation;
    @InjectView(R.id.enterprise_location_layout)
    RelativeLayout enterpriseLocationLayout;
    @InjectView(R.id.enterprise_site_goto)
    ImageView enterpriseSiteGoto;
    @InjectView(R.id.enterprise_site)
    TextView enterpriseSite;
    @InjectView(R.id.enterprise_site_layout)
    RelativeLayout enterpriseSiteLayout;
    @InjectView(R.id.warehouse_code_goto)
    ImageView warehouseCodeGoto;
    @InjectView(R.id.warehouse_name)
    TextView warehouseName;
    @InjectView(R.id.warehouse_name_layout)
    RelativeLayout warehouseNameLayout;
    @InjectView(R.id.enterprise_next)
    TextView enterpriseNext;
    @InjectView(R.id.enterprise_connect_layout)
    RelativeLayout enterprise_connect_layout;
    @InjectView(R.id.enterprise_connect)
    TextView enterprise_connect;
    @InjectView(R.id.enterprise_connect_goto)
    ImageView enterprise_connect_goto;

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    public AMapLocationClient mlocationClient = null;

    public double mLon = 0.0;
    public double mLat = 0.0;

    private String mEnterpriseName = "";
    private String mEnterpriseType = "";
    private String mEnterpriseLocationType = "";
    private String mEnterpriseSiteName = "";
    private String mWareHouseName = "";
    private String mEnterpriseNumber = "";
    private String custid = "";

    boolean needRepair = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_my_enterprise_account, null);
        setContentView(view);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
        initHead();
        initView();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("企业基本信息", getResources().getColor(R.color.white));
    }

    private void initView() {
        enterpriseName.setText("必填");
        enterpriseType.setText("必选");
        enterpriseLocation.setText("必选");
        enterpriseSite.setText("必填");
        enterprise_connect.setText("必填");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(UserManager.getInstance().getUser().getCustState()) && UserManager.getInstance().getUser().getCustState().equals("2")){
            custid = UserManager.getInstance().getUser().getESjzcHynm();
            if(needRepair) {
                UserService api = ApiUtil.createDefaultApi(UserService.class);
                ApiUtil.doDefaultApi(api.getEnterpriseInfo(UserManager.getInstance().getUser().getESjzcHynm()), enterpriseInfo -> {
                    enterpriseName.setText(enterpriseInfo.getCustName());
                    mEnterpriseName = enterpriseInfo.getCustName();
                    enterpriseType.setText(enterpriseInfo.getTypeName());
                    mEnterpriseType = enterpriseInfo.getTypeName();
                    enterpriseLocation.setText(enterpriseInfo.getAreaName());
                    mEnterpriseLocationType = enterpriseInfo.getAreaName();
                    enterpriseSite.setText(enterpriseInfo.getCompanyAddress());
                    mEnterpriseSiteName = enterpriseInfo.getCompanyAddress();
                    enterprise_connect.setText("" + enterpriseInfo.getGroupContactPhone());
                    mEnterpriseNumber = enterpriseInfo.getGroupContactPhone();
                    if (!TextUtils.isEmpty(enterpriseInfo.getHyCkbCkmc())) {
                        if (enterpriseInfo.getHyCkbCkmc().contains(","))
                            warehouseName.setText(enterpriseInfo.getHyCkbCkmc().split(",").length + "");
                        else
                            warehouseName.setText("1");
                        mWareHouseName = enterpriseInfo.getHyCkbNm();
                        SharedPrefsUtil.remove(MyEnterpriseAccountActivity.this, WAREHOUSEID);
                    }
                });
            }
        } else {
            if (!TextUtils.isEmpty(SharedPrefsUtil.getValue(this, WAREHOUSEID, ""))) {
                ArrayList<String> list = JsonUtil.fromJson(SharedPrefsUtil.getValue(MyEnterpriseAccountActivity.this, WAREHOUSEID, ""), new TypeToken<List<String>>() {
                }.getType());
                warehouseName.setText("" + list.size());
            } else {
                warehouseName.setText("可选");
            }
            custid = SharedPrefsUtil.getValue(MyEnterpriseAccountActivity.this, SharedPrefsUtil.CUSTID, "");
        }

        if(Utils.isOpenGPS(this)){
            initLocation();
        }else{
            Utils.showGPSDialog(this);
        }
    }

    private void initLocation() {
        mlocationClient = new AMapLocationClient(getActivity());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true);
        //设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                mLat = aMapLocation.getLatitude();
                mLon = aMapLocation.getLongitude();
                mlocationClient.stopLocation();
            }
        });
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    public void onEventMainThread(EnterpriseAccountTransfer enterpriseAccountTransfer) {
        needRepair = false;
        switch(enterpriseAccountTransfer.getType()){
            case EnterpriseAccountTransfer.ENTERPRISENAME:
                mEnterpriseName = enterpriseAccountTransfer.getName();
                enterpriseName.setText(mEnterpriseName);
                break;
            case EnterpriseAccountTransfer.ENTERPRISETYPE:
                mEnterpriseType = enterpriseAccountTransfer.getNumber();
                enterpriseType.setText(enterpriseAccountTransfer.getName());
                break;
            case EnterpriseAccountTransfer.ENTERPRISELOCATION:
                enterpriseLocation.setText(enterpriseAccountTransfer.getName());
                mEnterpriseLocationType = ""+enterpriseAccountTransfer.getNumber();
                break;
            case EnterpriseAccountTransfer.ENTERPRISESITE:
                mEnterpriseSiteName = enterpriseAccountTransfer.getName();
                enterpriseSite.setText(mEnterpriseSiteName);
                break;
            case EnterpriseAccountTransfer.ENTERNUMBER:
                mEnterpriseNumber = enterpriseAccountTransfer.getName();
                enterprise_connect.setText(mEnterpriseNumber);
                break;
        }
    }

    public void onEventMainThread(EnterpriseApplyTransfer enterpriseApplyTransfer){
        finish();
    }

    @OnClick({R.id.enterprise_name_layout, R.id.enterprise_type_layout, R.id.enterprise_location_layout, R.id.enterprise_site_layout, R.id.enterprise_connect_layout, R.id.warehouse_name_layout, R.id.enterprise_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.enterprise_name_layout:
                Intent intentName = createIntent(EnterpriseNameActivity.class);
                intentName.putExtra("name",mEnterpriseName);
                startActivity(intentName);
                break;
            case R.id.enterprise_type_layout:
                startActivity(createIntent(MyEnterpriseTypeActivity.class));
                break;
            case R.id.enterprise_location_layout:
                startActivity(createIntent(MyEnterpriseLocationActivity.class));
                break;
            case R.id.enterprise_site_layout:
                Intent intentSiteName = createIntent(EnterpriseSiteActivity.class);
                intentSiteName.putExtra("name",mEnterpriseSiteName);
                startActivity(intentSiteName);
                break;
            case R.id.enterprise_connect_layout:
                Intent intentConnect = createIntent(EnterpriseNumberActivity.class);
                intentConnect.putExtra("number",mEnterpriseNumber);
                startActivity(intentConnect);
                break;
            case R.id.warehouse_name_layout:
                startActivity(createIntent(WarehouseNameActivity.class));
                break;
            case R.id.enterprise_next:
                if(TextUtils.isEmpty(mEnterpriseName)){
                    showToast("请设置企业名称");
                    return;
                }
                if(TextUtils.isEmpty(mEnterpriseType)){
                    showToast("请设置企业类型");
                    return;
                }
                if(TextUtils.isEmpty(mEnterpriseLocationType)){
                    showToast("请设置企业所在地");
                    return;
                }
                if(TextUtils.isEmpty(mEnterpriseSiteName)){
                    showToast("请设置企业地址");
                    return;
                }
                if(TextUtils.isEmpty(mEnterpriseNumber)){
                    showToast("请设置企业联系方式");
                    return;
                }

                if(!TextUtils.isEmpty(SharedPrefsUtil.getValue(this, WAREHOUSEID,""))){
                    ArrayList<String> list = JsonUtil.fromJson(SharedPrefsUtil.getValue(MyEnterpriseAccountActivity.this, WAREHOUSEID,""),new TypeToken<List<String>>() {}.getType());
                    for (String name:list){
                        mWareHouseName += name+"@";
                    }
                    mWareHouseName = mWareHouseName.substring(0,mWareHouseName.length()-1);
                }
                ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
                UserService api = ApiUtil.createDefaultApi(UserService.class);
                ApiUtil.doDefaultApi(api.submitBaseInfo(custid,mEnterpriseName,mEnterpriseType,mEnterpriseLocationType,mEnterpriseSiteName,mWareHouseName,""+mLat,""+mLon, mEnterpriseNumber), enterpriseInfo -> {
                    SharedPrefsUtil.remove(MyEnterpriseAccountActivity.this, WAREHOUSEID);
                    if(enterpriseInfo == null){
                        if(UserManager.getInstance().getUser().getCustState().equals("2")) {
                            SharedPrefsUtil.putValue(MyEnterpriseAccountActivity.this,SharedPrefsUtil.CUSTID,""+custid);
                            Intent intent = createIntent(EnterpriseAptitudeActivity.class);
                            intent.putExtra("custId", custid);
                            startActivity(intent);
                        }
                    } else {
                        SharedPrefsUtil.putValue(MyEnterpriseAccountActivity.this,SharedPrefsUtil.CUSTID,""+enterpriseInfo.getCustId());
                        Intent intent = createIntent(EnterpriseAptitudeActivity.class);
                        intent.putExtra("custId", "" + enterpriseInfo.getCustId());
                        startActivity(intent);
                    }
                }, viewControl);
                break;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
        if(!TextUtils.isEmpty(UserManager.getInstance().getUser().getCustState()) && UserManager.getInstance().getUser().getCustState().equals("2"))
            SharedPrefsUtil.remove(MyEnterpriseAccountActivity.this, WAREHOUSEID);
    }
}
