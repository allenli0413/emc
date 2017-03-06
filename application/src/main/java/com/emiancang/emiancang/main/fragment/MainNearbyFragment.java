package com.emiancang.emiancang.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleFragment;
import com.emiancang.emiancang.bean.Dictionary;
import com.emiancang.emiancang.bean.DistanceInfo;
import com.emiancang.emiancang.bean.NearEnterprise;
import com.emiancang.emiancang.city.CityActivity;
import com.emiancang.emiancang.eventbean.CityTransfer;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.my.adapter.AdapterEnterpriseType;
import com.emiancang.emiancang.my.adapter.AdapterNearby;
import com.emiancang.emiancang.my.adapter.AdapterPopupWindowDistance;
import com.emiancang.emiancang.nearby.NearbyDetailsActivity;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.uitl.Utils;
import com.litesuits.android.log.Log;
import com.mayigeek.frame.http.state.HttpError;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import de.greenrobot.event.EventBus;
import rx.Observable;

import static android.R.attr.data;
import static com.litesuits.common.assist.LogReader.open;

/**
 * 附近选项卡
 * Created by 22310 on 2016/4/8.
 */
public class MainNearbyFragment extends TitleFragment {

    @InjectView(R.id.tv_title_search_left)
    TextView tvTitleSearchLeft;
    @InjectView(R.id.iv_title_search_right)
    ImageView ivTitleSearchRight;
    @InjectView(R.id.ll_title_search_total_left)
    LinearLayout llTitleSearchTotalLeft;
    @InjectView(R.id.edit_title_search_middle)
    EditText editTitleSearchMiddle;
    @InjectView(R.id.tv_title_search_right)
    TextView tvTitleSearchRight;
    @InjectView(R.id.ll_title_search)
    LinearLayout llTitleSearch;
    @InjectView(R.id.red_packet_type_name)
    TextView redPacketTypeName;
    @InjectView(R.id.red_packet_type_iamge)
    ImageView redPacketTypeIamge;
    @InjectView(R.id.nearby_type_layout)
    RelativeLayout nearbyTypeLayout;
    @InjectView(R.id.red_packet_order_name)
    TextView redPacketOrderName;
    @InjectView(R.id.red_packet_order_iamge)
    ImageView redPacketOrderIamge;
    @InjectView(R.id.nearby_distance_layout)
    RelativeLayout nearbyDistanceLayout;
    @InjectView(R.id.title_layout)
    LinearLayout titleLayout;
    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;

    private AdapterNearby mAdapter = new AdapterNearby(getActivity());
    private String mDistanceSelect = "0";

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    public AMapLocationClient mlocationClient = null;

    public double lat = 0.0;
    public double lon = 0.0;

    public String Type = "";
    public String sort = "";

    public String custName = "";
    public String eparchyCode = "";
    public String eparchyName = "";

    ViewControl viewControl;

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_main_nearby, null);
        ButterKnife.inject(this, view);
        EventBus.getDefault().register(this);
        viewControl = ViewControlUtil.create2View(nestRefreshLayout,"很抱歉，附近没有棉企","很抱歉，附近没有棉企",R.drawable.icon_content_empty);
        initTitle();
        initView();
        eparchyCode = SharedPrefsUtil.getValue(getActivity(), SharedPrefsUtil.KEEP_CITY_ID, "");
        eparchyName = SharedPrefsUtil.getValue(getActivity(), SharedPrefsUtil.KEEP_CITY, "");
        if(TextUtils.isEmpty(eparchyCode)) {
            if (Utils.isOpenGPS(getActivity())) {
                initLocation();
            } else {
                Utils.showGPSDialog(getActivity());
            }
        } else {
            tvTitleSearchLeft.setText(eparchyName);
            refresh();
        }
        return view;
    }


    private void initTitle() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().hideHeader();
        tvTitleSearchRight.setVisibility(View.INVISIBLE);
        llTitleSearchTotalLeft.setOnClickListener(v -> {
            Intent city = createIntent(CityActivity.class);
            city.putExtra("type",1);
            startActivity(city);
        });
    }

    public void onEventMainThread(CityTransfer cityTransfer) {
        if(cityTransfer.getType() == 1){
            SharedPrefsUtil.putValue(getActivity(), SharedPrefsUtil.KEEP_CITY, cityTransfer.getName());
            SharedPrefsUtil.putValue(getActivity(), SharedPrefsUtil.KEEP_CITY_ID, cityTransfer.getId());
            tvTitleSearchLeft.setText(cityTransfer.getName());
            eparchyName = cityTransfer.getName();
            eparchyCode = cityTransfer.getId();
            refresh();
        }
    }

    private void initView() {
        editTitleSearchMiddle.setHint("请输入涉棉企业搜索");
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent detail = createIntent(NearbyDetailsActivity.class);
            detail.putExtra("id",""+mAdapter.getItem(position).getCustId());
            startActivity(detail);
        });
        editTitleSearchMiddle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                custName = editTitleSearchMiddle.getText().toString().trim();
                refresh();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initLocation() {
        mlocationClient = new AMapLocationClient(getActivity());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true);
        //设置定位监听
        mlocationClient.setLocationListener(aMapLocation -> {
            mlocationClient.stopLocation();
            tvTitleSearchLeft.setText(aMapLocation.getCity());
            lat = aMapLocation.getLatitude();
            lon = aMapLocation.getLongitude();
            if(aMapLocation.getProvince().equals(aMapLocation.getCity())){
                eparchyName = aMapLocation.getDistrict();
            } else {
                eparchyName = aMapLocation.getCity();
            }
            refresh();
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

    public void refresh(){
        NestRefreshManager<NearEnterprise> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, (page, perPage) -> {
            UserService api = ApiUtil.createDefaultApi(UserService.class);
            return api.getNearEnterprise(custName, eparchyCode, eparchyName,""+lon,""+lat,Type,sort, "" + (page+1), "" + perPage).map(new HttpResultFunc<>());
        });
        nestRefreshManager.setPullLoadEnable(true);
        nestRefreshManager.setHttpError(e -> Log.i("test", e.toString()));
        nestRefreshManager.setCallBack((allList, currentList) -> {
            mAdapter.clear();
            mAdapter.addList(allList);
            mAdapter.notifyDataSetChanged();
        });
        nestRefreshManager.doApi();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.nearby_type_layout, R.id.nearby_distance_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nearby_type_layout:
                showWindowPacketType(R.id.nearby_type_layout);
                break;
            case R.id.nearby_distance_layout:
                showWindowPacketDistance(R.id.nearby_distance_layout);
                break;
        }
    }

    static class ViewHolderType {
        @InjectView(R.id.listView)
        ListView listView;
        @InjectView(R.id.nestRefreshLayout)
        NestRefreshLayout nestRefreshLayout;
        @InjectView(R.id.iamge_background)
        ImageView iamgeBackground;

        ViewHolderType(View view) {
            ButterKnife.inject(this, view);
        }
    }

    //显示类型
    private void showWindowPacketType(int id) {
        PopupWindow popupWindow = new PopupWindow(getActivity());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_popwindow_redpacket_type, null);
        popupWindow.setContentView(contentView);
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度-10
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = contentView.getMeasuredWidth();
        int popupHeight = contentView.getMeasuredHeight();
        int[] location = new int[2];
        getActivity().findViewById(id).getLocationOnScreen(location);
        popupWindow.showAsDropDown(getActivity().findViewById(id));
        popupWindow.setOnDismissListener(() -> {
            redPacketTypeName.setTextColor(getResources().getColor(R.color.black));
            redPacketTypeIamge.setImageDrawable(getResources().getDrawable(R.drawable.pull_down));
        });
        redPacketTypeName.setTextColor(getResources().getColor(R.color.green));
        redPacketTypeIamge.setImageDrawable(getResources().getDrawable(R.drawable.pull_down_up));
        ViewHolderType viewHolder = new ViewHolderType(contentView);
        AdapterEnterpriseType mAdapter = new AdapterEnterpriseType(getActivity());
        viewHolder.listView.setAdapter(mAdapter);
        viewHolder.iamgeBackground.setVisibility(View.VISIBLE);
        ViewControl viewControl = ViewControlUtil.create2View(viewHolder.nestRefreshLayout);
        NestRefreshManager<Dictionary> nestRefreshManager = new NestRefreshManager<>(viewHolder.nestRefreshLayout, viewControl, (page, perPage) -> {
            UserService api = ApiUtil.createDefaultApi(UserService.class);
            return api.getDictionaryByCode("COMPANY_TYPE").map(new HttpResultFunc<>());
        });
        nestRefreshManager.setHttpError(e -> Log.i("test", e.toString()));
        nestRefreshManager.setCallBack((allList, currentList) -> {
            mAdapter.clear();
            mAdapter.addList(allList);
            mAdapter.notifyDataSetChanged();
        });
        nestRefreshManager.doApi();

        viewHolder.listView.setOnItemClickListener((parent, view, position, id1) -> {
            Dictionary entity = mAdapter.getItem(position);
            SharedPrefsUtil.putValue(getActivity(),SharedPrefsUtil.ENTERPRISETYPE,""+entity.getId());
            Type = entity.getDicKey();
            popupWindow.dismiss();
            refresh();
        });
    }

    //显示距离
    private void showWindowPacketDistance(int id) {
        PopupWindow popupWindow = new PopupWindow(getActivity());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_popwindow_redpacket_type, null);
        popupWindow.setContentView(contentView);
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度-10
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = contentView.getMeasuredWidth();
        int popupHeight = contentView.getMeasuredHeight();
        int[] location = new int[2];
        getActivity().findViewById(id).getLocationOnScreen(location);
//        popupWindow.showAtLocation(getActivity().findViewById(id), Gravity.NO_GRAVITY, (location[0] + getActivity().findViewById(id).getMeasuredWidth() / 2) - popupWidth / 2, location[1] + (popupHeight / 2));
        popupWindow.showAsDropDown(getActivity().findViewById(id));
        popupWindow.setOnDismissListener(() -> {
            redPacketOrderName.setTextColor(getResources().getColor(R.color.black));
            redPacketOrderIamge.setImageDrawable(getResources().getDrawable(R.drawable.pull_down));
        });
        redPacketOrderName.setTextColor(getResources().getColor(R.color.green));
        redPacketOrderIamge.setImageDrawable(getResources().getDrawable(R.drawable.pull_down_up));
        ViewHolderType viewHolder = new ViewHolderType(contentView);
        AdapterPopupWindowDistance mAdapter = new AdapterPopupWindowDistance();
        viewHolder.listView.setAdapter(mAdapter);
        mAdapter.setmTypeSelect(mDistanceSelect);
        viewHolder.iamgeBackground.setVisibility(View.VISIBLE);
        mAdapter.addList(getDistanceData());
        mAdapter.notifyDataSetChanged();

        viewHolder.listView.setOnItemClickListener((parent, view, position, id1) -> {
            if(position == 0)
                sort = "0";
            if(position == 1)
                sort = "1";
            DistanceInfo entity = mAdapter.getItem(position);
            mDistanceSelect = entity.getId();
            refresh();
            popupWindow.dismiss();
        });
    }

    public List<DistanceInfo> getDistanceData() {
        List<DistanceInfo> data = new ArrayList<DistanceInfo>();
        if (mDistanceSelect.equals("0")) {
            data.add(new DistanceInfo("0", "距离由近及远", true));
        } else {
            data.add(new DistanceInfo("0", "距离由近及远", false));
        }
        if (mDistanceSelect.equals("1")) {
            data.add(new DistanceInfo("1", "距离由远及近", true));
        } else {
            data.add(new DistanceInfo("1", "距离由远及近", false));
        }
        return data;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}
