package com.emiancang.emiancang.nearby;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.uitl.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 22310 on 2016/11/14.
 */

public class NearbySiteActivity extends TitleActivity implements AMap.OnMapClickListener {

    @InjectView(R.id.map)
    MapView mapView;
    @InjectView(R.id.navi)
    TextView navi;

    private AMap aMap;
    private String elon = "";
    private String elat = "";
    private String slon = "";
    private String slat = "";
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    public AMapLocationClient mlocationClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_nearby_site, null);
        setContentView(view);
        ButterKnife.inject(this);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        initHead();
        initView();
    }

    private void initView() {
        elon = getStringExtra("lon");
        elat = getStringExtra("lat");

        if(TextUtils.isEmpty(elat)){
            elat = ""+39.989974;
        }
        if(TextUtils.isEmpty(elon)){
            elon = ""+116.323048;
        }

        if (aMap == null) {
            aMap = mapView.getMap();
        }
        //绘制marker
        Marker marker = aMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(elat),Double.parseDouble(elon)))
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.drawable.location_marker)))
                .draggable(true));
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(Double.valueOf(elat),Double.valueOf(elon)), 18, 0, 0)));
        aMap.setOnMapClickListener(this);
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("企业位置", getResources().getColor(R.color.white));
    }

    @Override
    public void onMapClick(LatLng latLng) {
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        if(Utils.isOpenGPS(this)){
            initLocation();
        }else{
            Utils.showGPSDialog(this);
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @OnClick(R.id.navi)
    public void onClick() {
        if(TextUtils.isEmpty(slon) || TextUtils.isEmpty(slat)){
            showToast("定位延迟，请稍后再试");
            return;
        }
        Intent SiteNavi = createIntent(NearbySiteNaviActivity.class);
        SiteNavi.putExtra("elon",elon);
        SiteNavi.putExtra("elat",elat);
        SiteNavi.putExtra("slon",slon);
        SiteNavi.putExtra("slat",slat);
        startActivity(SiteNavi);
    }


    private void initLocation() {
        mlocationClient = new AMapLocationClient(getActivity());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true);
        //设置定位监听
        mlocationClient.setLocationListener(aMapLocation -> {
            slon = ""+aMapLocation.getLongitude();
            slat = ""+aMapLocation.getLatitude();
            mlocationClient.stopLocation();
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
}
