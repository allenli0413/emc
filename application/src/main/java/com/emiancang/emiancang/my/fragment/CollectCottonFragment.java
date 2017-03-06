package com.emiancang.emiancang.my.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.emiancang.emiancang.main.home.redpapercotton.activity.RedPaperCottonDetailActivity;
import com.emiancang.emiancang.store.model.MainStoreModel;
import com.litesuits.android.log.Log;
import com.mayigeek.frame.http.state.HttpError;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleFragment;
import com.emiancang.emiancang.bean.CollectCottonEntity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.my.adapter.AdapterCollectCotton;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.Utils;
import com.zwyl.view.SilderXListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import rx.Observable;

/**
 * Created by 22310 on 2016/11/10.
 */

public class CollectCottonFragment extends TitleFragment {

    ;
    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;
    @InjectView(R.id.listView)
    SilderXListView listView;

    private AdapterCollectCotton mAdapter;

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    public AMapLocationClient mlocationClient = null;

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_collect_cotton, null);
        ButterKnife.inject(this, view);
        getHeadBar().hideHeader();
        initView();
        return view;
    }

    private void initView() {
        mAdapter = new AdapterCollectCotton(getActivity());
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CollectCottonEntity entity = mAdapter.getItem(position - listView.getHeaderViewsCount());
                Intent intent = new Intent(getActivity(), RedPaperCottonDetailActivity.class);
                MainStoreModel item = new MainStoreModel();
                item.setGcmgyMhph(entity.getHyJysjPh());
                item.setProductId(entity.getProductId());
                item.setCustId(entity.getCustId());
                intent.putExtra("data", item);
                startActivity(intent);
            }
        });
    }

    public void refresh(String lon, String lat) {
        ViewControl viewControl = ViewControlUtil.create2View(nestRefreshLayout,"当前没有收藏","当前没有收藏",R.drawable.icon_collection_empty);
        NestRefreshManager<CollectCottonEntity> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, new NestRefreshManager.CreateApi<CollectCottonEntity>() {
            @Override
            public Observable<List<CollectCottonEntity>> run(int page, int perPage) {
                UserService api = ApiUtil.createDefaultApi(UserService.class);
                return api.getCollectCottonList("" + (page + 1), "" + perPage, lon, lat).map(new HttpResultFunc<>());
            }
        });
        nestRefreshManager.setPullLoadEnable(true);
        nestRefreshManager.setHttpError(new HttpError() {
            @Override
            public void onError(Throwable e) {
                Log.i("test", e.toString());
            }
        });
        nestRefreshManager.setCallBack(new NestRefreshManager.CallBack<CollectCottonEntity>() {
            @Override
            public void call(List<? extends CollectCottonEntity> allList, List<? extends CollectCottonEntity> currentList) {
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
                refresh("" + lon, "" + lat);
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
