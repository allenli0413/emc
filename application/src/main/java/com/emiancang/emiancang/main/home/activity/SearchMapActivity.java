package com.emiancang.emiancang.main.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.emiancang.emiancang.main.home.model.CottonEnterprisesModel;
import com.litesuits.android.log.Log;
import com.mayigeek.frame.http.state.HttpError;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.bean.NearEnterprise;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.nearby.NearbyDetailsActivity;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.uitl.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import rx.Observable;

/**
 * Created by yangzheng on 2016/11/4.
 */

public class SearchMapActivity extends TitleActivity implements AMap.OnInfoWindowClickListener,AMap.OnMarkerClickListener {


    @InjectView(R.id.map)
    MapView mapView;
    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;

    private AMap aMap;
    ArrayList<MarkerOptions> markerOptionlst = new ArrayList<MarkerOptions>();

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    public AMapLocationClient mlocationClient = null;

    List<NearEnterprise> nearEnterpriseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_search_map, null);
        setContentView(view);
        ButterKnife.inject(this);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        initHead();
        initView();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("企业位置", getResources().getColor(R.color.white));
    }

    private void initView() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
//                LatLng latLng = cameraPosition.target;
//                refresh("" + latLng.longitude, "" + latLng.latitude);
            }
        });
//        for (int i = 0; i < nearEnterpriseList.size(); i++) {
//            NearEnterprise nearEnterprise = nearEnterpriseList.get(i);
//            if(!TextUtils.isEmpty(nearEnterprise.getLat()) && !TextUtils.isEmpty(nearEnterprise.getLng())) {
//                MarkerOptions markerOption = new MarkerOptions().anchor(0.5f, 0.5f)
//                        .position(new LatLng(Double.valueOf(nearEnterprise.getLat()), Double.valueOf(nearEnterprise.getLng())))
//                        .snippet(nearEnterprise.getCustName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
//                        .draggable(true).period(50).infoWindowEnable(true);
//                markerOptionlst.add(markerOption);
//            }
//        }
//        aMap.addMarkers(markerOptionlst, true);
//        aMap.setOnMarkerClickListener(SearchMapActivity.this);// 设置点击marker事件监听器
//        aMap.setOnInfoWindowClickListener(SearchMapActivity.this);// 设置点击infoWindow事件监听器

//        MarkerOptions markerOption2 = new MarkerOptions().anchor(0.5f, 0.5f)
//                .position(new LatLng(39.989846,116.324643))
//                .snippet("北京市利华棉业股份有限公司第四棉花加工厂").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
//                .draggable(true).period(50).infoWindowEnable(true);
//        MarkerOptions markerOption3 = new MarkerOptions().anchor(0.5f, 0.5f)
//                .position(new LatLng(39.990181,116.321917))
//                .snippet("北京市利华棉业股份有限公司第四棉花加工厂").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
//                .draggable(true).period(50).infoWindowEnable(true);
//        MarkerOptions markerOption4 = new MarkerOptions().anchor(0.5f, 0.5f)
//                .position(new LatLng(39.989241,116.324993))
//                .snippet("北京市利华棉业股份有限公司第四棉花加工厂").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
//                .draggable(true).period(50).infoWindowEnable(true);
//        MarkerOptions markerOption5 = new MarkerOptions().anchor(0.5f, 0.5f)
//                .position(new LatLng(39.989639,116.323183))
//                .snippet("北京市利华棉业股份有限公司第四棉花加工厂").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
//                .draggable(true).period(50).infoWindowEnable(true);
//        MarkerOptions markerOption6 = new MarkerOptions().anchor(0.5f, 0.5f)
//                .position(new LatLng(39.990364,116.321548))
//                .snippet("北京市利华棉业股份有限公司第四棉花加工厂").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
//                .draggable(true).period(50).infoWindowEnable(true);
//        MarkerOptions markerOption7 = new MarkerOptions().anchor(0.5f, 0.5f)
//                .position(new LatLng(39.988941,116.32206))
//                .snippet("北京市利华棉业股份有限公司第四棉花加工厂").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
//                .draggable(true).period(50).infoWindowEnable(true);
//        MarkerOptions markerOption8 = new MarkerOptions().anchor(0.5f, 0.5f)
//                .position(new LatLng(39.9895,116.322739))
//                .snippet("北京市利华棉业股份有限公司第四棉花加工厂").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
//                .draggable(true).period(50).infoWindowEnable(true);
//        MarkerOptions markerOption9 = new MarkerOptions().anchor(0.5f, 0.5f)
//                .position(new LatLng(39.988042,116.324243))
//                .snippet("北京市利华棉业股份有限公司第四棉花加工厂").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
//                .draggable(true).period(50).infoWindowEnable(true);
//        MarkerOptions markerOption10 = new MarkerOptions().anchor(0.5f, 0.5f)
//                .position(new LatLng(39.99033,116.323381))
//                .snippet("北京市利华棉业股份有限公司第四棉花加工厂").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
//                .draggable(true).period(50).infoWindowEnable(true);
//        markerOptionlst.add(markerOption2);
//        markerOptionlst.add(markerOption3);
//        markerOptionlst.add(markerOption4);
//        markerOptionlst.add(markerOption5);
//        markerOptionlst.add(markerOption6);
//        markerOptionlst.add(markerOption7);
//        markerOptionlst.add(markerOption8);
//        markerOptionlst.add(markerOption9);
//        markerOptionlst.add(markerOption10);
//        aMap.addMarkers(markerOptionlst, true);
//        aMap.setOnMarkerClickListener(SearchMapActivity.this);// 设置点击marker事件监听器
//        aMap.setOnInfoWindowClickListener(SearchMapActivity.this);// 设置点击infoWindow事件监听器
    }

    private void initLocation() {
        mlocationClient = new AMapLocationClient(getActivity());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true);
        //设置定位监听
        mlocationClient.setLocationListener(aMapLocation -> {
            aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 18, 0, 0)));
            refresh("" + aMapLocation.getLongitude(), "" + aMapLocation.getLatitude());
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





    public void refresh(String lon, String lat) {
        ViewControl viewControl = ViewControlUtil.create2View(nestRefreshLayout);
        NestRefreshManager<NearEnterprise> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, (page, perPage) -> {
            UserService api = ApiUtil.createDefaultApi(UserService.class);
            return api.getNearEnterpriseByDistance("" + lon, "" + lat, "200", "0").map(new HttpResultFunc<>());
        });
        nestRefreshManager.setPullLoadEnable(true);
        nestRefreshManager.setHttpError(e -> Log.i("test", e.toString()));
        nestRefreshManager.setCallBack((allList, currentList) -> {
            for (int i = 0; i < allList.size(); i++) {
                NearEnterprise nearEnterprise = allList.get(i);
                if(!TextUtils.isEmpty(nearEnterprise.getLat()) && !TextUtils.isEmpty(nearEnterprise.getLng())) {
                    MarkerOptions markerOption = new MarkerOptions().anchor(0.5f, 0.5f)
                            .position(new LatLng(Double.valueOf(nearEnterprise.getLat()), Double.valueOf(nearEnterprise.getLng())))
                            .snippet(nearEnterprise.getCustName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                            .draggable(true).period(50).infoWindowEnable(true);
                    markerOptionlst.add(markerOption);
                    nearEnterpriseList.add(allList.get(i));
                }
            }
            aMap.addMarkers(markerOptionlst, true);
            aMap.setOnMarkerClickListener(SearchMapActivity.this);// 设置点击marker事件监听器
            aMap.setOnInfoWindowClickListener(SearchMapActivity.this);// 设置点击infoWindow事件监听器
        });
        nestRefreshManager.doApi();
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

    /**
     * 监听点击infowindow窗口事件回调
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(getActivity(), NearbyDetailsActivity.class);
        for (NearEnterprise nearEnterprise : nearEnterpriseList) {
            if(marker.getSnippet().equals(nearEnterprise.getCustName()))
                intent.putExtra("id", nearEnterprise.getCustId());
        }
        startActivity(intent);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
//        startActivity(createIntent(NearbyDetailsActivity.class));
        return false;
    }
}
