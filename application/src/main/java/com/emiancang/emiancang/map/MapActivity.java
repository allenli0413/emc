package com.emiancang.emiancang.map;

import android.os.Bundle;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.uitl.Util;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 22310 on 2016/11/14.
 */

public class MapActivity extends TitleActivity implements AMap.OnMapClickListener {

    @InjectView(R.id.map)
    MapView mapView;

    private AMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_map, null);
        setContentView(view);
        ButterKnife.inject(this);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        initHead();
        initView();
    }

    private void initView() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        double lon = getIntent().getDoubleExtra("lon",116.323048);
        double lat = getIntent().getDoubleExtra("lat",39.989974);
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(lat,lon ), 18, 0, 0)));
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

}
