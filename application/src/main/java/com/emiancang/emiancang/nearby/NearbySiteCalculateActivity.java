package com.emiancang.emiancang.nearby;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.overlay.BusRouteOverlay;
import com.amap.api.maps.overlay.DrivingRouteOverlay;
import com.amap.api.maps.overlay.WalkRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.litesuits.android.log.Log;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.uitl.Util;

import butterknife.ButterKnife;

/**
 * Created by 22310 on 2016/11/14.
 */

public class NearbySiteCalculateActivity extends TitleActivity implements RouteSearch.OnRouteSearchListener {

    private MapView mapView;
    private AMap aMap;
    private Boolean isFirst;
    private LatLng lat_star;
    private LatLng lat_end;
    private TextView text_address;
    private StringBuffer sb = new StringBuffer();
    private RouteSearch.DriveRouteQuery query;

    private TextView navi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_nearby_site_calculate, null);
        setContentView(view);
        initHead();
        String elon = getStringExtra("elon");
        String elat = getStringExtra("elat");
        String slon = getStringExtra("slon");
        String slat = getStringExtra("slat");
        navi = (TextView) findViewById(R.id.navi);
        navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SiteNavi = createIntent(NearbySiteNaviActivity.class);
                SiteNavi.putExtra("elon",elon);
                SiteNavi.putExtra("elat",elat);
                SiteNavi.putExtra("slon",slon);
                SiteNavi.putExtra("slat",slat);
                startActivity(SiteNavi);
            }
        });
        lat_star = new LatLng(Double.valueOf(slat),Double.valueOf(slon));
        lat_end = new LatLng(Double.valueOf(elat),Double.valueOf(elon));
        mapView = (MapView) findViewById(R.id.map_map01);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        rote("drive");
        isFirst = true;
    }


    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("位置导航", getResources().getColor(R.color.white));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * 路径规划
     */
    protected void rote(String str) {
        System.out.println("-----------------进入路线规划-----------------");
        RouteSearch routeSerch = new RouteSearch(NearbySiteCalculateActivity.this);
        routeSerch.setRouteSearchListener(NearbySiteCalculateActivity.this);
        LatLonPoint start_lat = new LatLonPoint(lat_star.latitude,
                lat_star.longitude);
        LatLonPoint end_lat = new LatLonPoint(lat_end.latitude,
                lat_end.longitude);
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(start_lat,
                end_lat);
        if ("bus".equals(str)) {
            RouteSearch.BusRouteQuery query=new RouteSearch.BusRouteQuery(fromAndTo,RouteSearch.BusDefault,null,0);
            routeSerch.calculateBusRouteAsyn(query);// 异步路径规划驾车模式查询 //
        } else if ("drive".equals(str)) {
            query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault,
                    null, null, "");
            routeSerch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询 //
        } else {
            RouteSearch.WalkRouteQuery query=new RouteSearch.WalkRouteQuery(fromAndTo, RouteSearch.WalkDefault);
            routeSerch.calculateWalkRouteAsyn(query);// 异步路径规划驾车模式查询 //
        }
    }

    // 公交
    @Override
    public void onBusRouteSearched(BusRouteResult result, int rCode) {

    }

    // 自驾
    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int rCode) {
        System.out.println("-------------------------驾车路径规划回调方法-------------------------"+ rCode + "---"+ result);

        if (rCode == 1000) {
            if (result != null && result.getPaths() != null
                    && result.getPaths().size() > 0) {
                DriveRouteResult driveRouteResult = result;
                DrivePath drivePath = driveRouteResult.getPaths().get(0);
                aMap.clear();// 清理地图上的所有覆盖物
                DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                        this,aMap, drivePath, driveRouteResult.getStartPos(),
                        driveRouteResult.getTargetPos());
                drivingRouteOverlay.removeFromMap();
                drivingRouteOverlay.addToMap();
                drivingRouteOverlay.zoomToSpan();

            } else {
                Toast.makeText(NearbySiteCalculateActivity.this, "对不起 没有搜索到相关数据", Toast.LENGTH_LONG).show();
            }
        } else if (rCode == 27) {
            Toast.makeText(NearbySiteCalculateActivity.this, "搜索失败 请检查网络连接", Toast.LENGTH_LONG).show();
        } else if (rCode == 32) {
            Toast.makeText(NearbySiteCalculateActivity.this, "验证无效", Toast.LENGTH_LONG).show();
        } else {

            Toast.makeText(getApplicationContext(), "错误码" + rCode, Toast.LENGTH_LONG).show();

        }

    }

    // 步行
    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int rCode) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }
}
