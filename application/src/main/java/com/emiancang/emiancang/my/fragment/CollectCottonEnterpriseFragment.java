package com.emiancang.emiancang.my.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.litesuits.android.log.Log;
import com.mayigeek.frame.http.state.HttpError;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleFragment;
import com.emiancang.emiancang.bean.CollectCottonEnterpriseEntity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.my.adapter.AdapterCollectCottonEnterprise;
import com.emiancang.emiancang.my.adapter.AdapterEnterpriseLocation;
import com.emiancang.emiancang.nearby.NearbyDetailsActivity;
import com.emiancang.emiancang.nearby.NearbySiteActivity;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.Utils;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;
import com.zwyl.view.SilderXListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import rx.Observable;

/**
 * Created by 22310 on 2016/11/10.
 */

public class CollectCottonEnterpriseFragment extends TitleFragment {


    @InjectView(R.id.listView)
    SilderXListView listView;
    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;

    private AdapterCollectCottonEnterprise mAdapter= null;

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    public AMapLocationClient mlocationClient = null;

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_collect_cotton_enterprise, null);
        ButterKnife.inject(this, view);
        getHeadBar().hideHeader();
        initView();
        return view;
    }

    private void initView() {
        mAdapter= new AdapterCollectCottonEnterprise(getActivity());
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CollectCottonEnterpriseEntity collectCottonEnterpriseEntity = mAdapter.getItem(position - listView.getHeaderViewsCount());
                Intent detail = createIntent(NearbyDetailsActivity.class);
                detail.putExtra("id",""+collectCottonEnterpriseEntity.getCustId());
                startActivity(detail);
//                Intent nearby = createIntent(NearbySiteActivity.class);
//                nearby.putExtra("lon",collectCottonEnterpriseEntity.getECompanyJd());
//                nearby.putExtra("lat",collectCottonEnterpriseEntity.getECompanyWd());
//                startActivity(nearby);
            }
        });
    }

    public void refresh(String lon,String lat){
        ViewControl viewControl = ViewControlUtil.create2View(nestRefreshLayout,"当前没有收藏","当前没有收藏",R.drawable.icon_collection_empty);
        NestRefreshManager<CollectCottonEnterpriseEntity> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, new NestRefreshManager.CreateApi<CollectCottonEnterpriseEntity>() {
            @Override
            public Observable<List<CollectCottonEnterpriseEntity>> run(int page, int perPage) {
                UserService api = ApiUtil.createDefaultApi(UserService.class);
                return api.getCollectCompanyList("" + (page+1), "" + perPage,lon, lat).map(new HttpResultFunc<>());
            }
        });
        nestRefreshManager.setPullLoadEnable(true);
        nestRefreshManager.setHttpError(new HttpError() {
            @Override
            public void onError(Throwable e) {
                Log.i("test", e.toString());
            }
        });
        nestRefreshManager.setCallBack(new NestRefreshManager.CallBack<CollectCottonEnterpriseEntity>() {
            @Override
            public void call(List<? extends CollectCottonEnterpriseEntity> allList, List<? extends CollectCottonEnterpriseEntity> currentList) {
                mAdapter.clear();
                mAdapter.addList(allList);
                mAdapter.notifyDataSetChanged();
            }
        });
        nestRefreshManager.doApi();
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
                double lat = aMapLocation.getLatitude();
                double lon = aMapLocation.getLongitude();
                refresh(""+lon,""+lat);
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

    @Override
    public void onResume() {
        super.onResume();
        if(Utils.isOpenGPS(getActivity())){
            initLocation();
        }else{
            Utils.showGPSDialog(getActivity());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
