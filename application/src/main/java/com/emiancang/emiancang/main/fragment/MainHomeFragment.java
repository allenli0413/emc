package com.emiancang.emiancang.main.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.devsmart.android.ui.HorizontalListView;
import com.emiancang.emiancang.App;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleFragment;
import com.emiancang.emiancang.city.CityActivity;
import com.emiancang.emiancang.eventbean.CityTransfer;
import com.emiancang.emiancang.eventbean.MainNearByTransfer;
import com.emiancang.emiancang.eventbean.MainStoreTransfer;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.hx.activity.ConversationActivity;
import com.emiancang.emiancang.login.LoginActivity;
import com.emiancang.emiancang.main.WebpageActivity;
import com.emiancang.emiancang.main.home.activity.SearchMapActivity;
import com.emiancang.emiancang.main.home.activity.ServiceActivity;
import com.emiancang.emiancang.main.home.adapter.CottonEnterprisesAdapter;
import com.emiancang.emiancang.main.home.adapter.MainHomeControlAdapter;
import com.emiancang.emiancang.main.home.businesshelp.activity.BusinessHelpActivity;
import com.emiancang.emiancang.main.home.deliveryorder.activity.DeliveryOrderActivity;
import com.emiancang.emiancang.main.home.findmoney.activity.FindMoneyActivity;
import com.emiancang.emiancang.main.home.logisticsquery.activity.LogisticsQuerySecondActivity;
import com.emiancang.emiancang.main.home.model.BannerModel;
import com.emiancang.emiancang.main.home.model.Category;
import com.emiancang.emiancang.main.home.model.CottonEnterprisesModel;
import com.emiancang.emiancang.main.home.model.MainHomeControlModel;
import com.emiancang.emiancang.main.home.qualityquery.activity.QualityQueryActivity;
import com.emiancang.emiancang.main.home.redpapercotton.activity.RedPaperCottonActivity;
import com.emiancang.emiancang.main.home.redpapercotton.activity.RedPaperCottonDetailActivity;
import com.emiancang.emiancang.main.home.service.BannerService;
import com.emiancang.emiancang.main.home.service.CottonEnterprisesService;
import com.emiancang.emiancang.main.home.service.StoreService;
import com.emiancang.emiancang.nearby.NearbyDetailsActivity;
import com.emiancang.emiancang.store.adapter.MainStoreAdapter;
import com.emiancang.emiancang.store.model.MainStoreModel;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.uitl.Utils;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;
import com.emiancang.emiancang.view.KeyboardLayout;
import com.hyphenate.chat.EMClient;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.zwyl.view.NetworkImageHolderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import de.greenrobot.event.EventBus;

/**
 * 主页选项卡
 * Created by 22310 on 2016/4/8.
 */
public class MainHomeFragment extends TitleFragment {

    @InjectView(R.id.kl_home)
    KeyboardLayout kl_home;
    @InjectView(R.id.ll_home_fake)
    LinearLayout ll_home_fake;
    //    @InjectView(R.id.sv_home)
    //    ScrollView sv_home;
    //    @InjectView(R.id.ll_home_real)
    //    LinearLayout ll_home_real;
    //    @InjectView(R.id.ll_home_top)
    //    LinearLayout ll_home_top;
    @InjectView(R.id.tv_home_location)
    TextView tv_home_location;
    //    @InjectView(R.id.iv_home_location_arrow)
    //    ImageView iv_home_location_arrow;
    @InjectView(R.id.iv_home_service_tips)
    ImageView iv_home_service_tips;
    @InjectView(R.id.hlv_home_main_control)
    HorizontalListView hlv_home_main_control;
    @InjectView(R.id.ll_home_nimasile)
    LinearLayout ll_home_nimasile;
    @InjectView(R.id.edit_home_search)
    EditText edit_home_search;
    @InjectView(R.id.tv_home_search)
    TextView tv_home_search;
    @InjectView(R.id.rl_home_banner)
    RelativeLayout rl_home_banner;
    @InjectView(R.id.banner_home_top)
    ConvenientBanner banner_home_top;
    @InjectView(R.id.hlv_home_cotton_enterprises)
    HorizontalListView hlv_home_cotton_enterprises;
    @InjectView(R.id.ll_home_cotton_enterprises_error)
    LinearLayout ll_home_cotton_enterprises_error;
    @InjectView(R.id.tv_home_cotton_enterprises_error_info)
    TextView tv_home_cotton_enterprises_error_info;
    //    @InjectView(R.id.lv_home_cotton_recommend)
    //    ListView lv_home_cotton_recommend;
    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;

    @InjectView(R.id.rl_home_real)
    RelativeLayout rl_home_real;
    @InjectView(R.id.ll_home_top_real)
    LinearLayout ll_home_top_real;
    @InjectView(R.id.ll_home_location_real)
    LinearLayout ll_home_location_real;
    @InjectView(R.id.tv_home_location_real)
    TextView tv_home_location_real;
    @InjectView(R.id.iv_home_location_arrow_real)
    ImageView iv_home_location_arrow_real;
    @InjectView(R.id.ll_home_nimasile_real)
    LinearLayout ll_home_nimasile_real;
    @InjectView(R.id.edit_home_search_real)
    EditText edit_home_search_real;
    @InjectView(R.id.tv_home_search_real)
    TextView tv_home_search_real;
    @InjectView(R.id.iv_home_service_real)
    ImageView iv_home_service_real;
    @InjectView(R.id.iv_home_service_tips_real)
    ImageView iv_home_service_tips_real;

    TopHolder topHolder;

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    public AMapLocationClient mlocationClient = null;
    AMapLocation aMapLocation;

    MainHomeControlAdapter mainHomeControlAdapter;
    CottonEnterprisesAdapter cottonEnterprisesAdapter;
    MainStoreAdapter adapter;

    private AlertDialog mShowLoginDialog;

    boolean autoLocation = true;

    boolean loadSuccess = false;

    ViewControl viewControl;

    String localId = "";
    String localName = "城市";

    List<CottonEnterprisesModel> cottonEnterprises;

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_main_home, null);
        ButterKnife.inject(this, view);
        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
        getHeadBar().hideHeader();
        //        viewControl = ViewControlUtil.create2View(nestRefreshLayout, false);
        viewControl = ViewControlUtil.create2Dialog(getActivity());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
        localId = SharedPrefsUtil.getValue(getActivity(), SharedPrefsUtil.KEEP_CITY_ID, "");
        localName = SharedPrefsUtil.getValue(getActivity(), SharedPrefsUtil.KEEP_CITY, "城市");
        if (localId.equals("")) {
            if (Utils.isOpenGPS(getActivity())) {
                initLocation();
            } else {
                initCottonEnterprises(aMapLocation);
                Utils.showGPSDialog(getActivity());
            }
        } else {
            tv_home_location.setText(localName);
            tv_home_location_real.setText(localName);
            initCottonEnterprises(aMapLocation);
            initCottonRecommend(aMapLocation);
        }
    }

    private void initLocation() {
        mlocationClient = new AMapLocationClient(getActivity());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true);
        //设置定位监听
        mlocationClient.setLocationListener(aMapLocation -> {
            if (autoLocation) {
                if (aMapLocation.getProvince().equals(aMapLocation.getCity())) {
                    tv_home_location_real.setText(aMapLocation.getCity());
                    tv_home_location.setText(aMapLocation.getCity());
                    localName = aMapLocation.getDistrict();
                } else {
                    tv_home_location_real.setText(aMapLocation.getCity());
                    tv_home_location.setText(aMapLocation.getCity());
                    localName = aMapLocation.getCity();
                }
            }
            mlocationClient.stopLocation();
            this.aMapLocation = aMapLocation;
            initCottonEnterprises(aMapLocation);
            initCottonRecommend(aMapLocation);
            SharedPrefsUtil.putValue(App.getContext(), "lat", aMapLocation.getLatitude() + "");
            SharedPrefsUtil.putValue(App.getContext(), "lng", aMapLocation.getLongitude() + "");
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

    private void resetLocation() {
        mlocationClient = new AMapLocationClient(getActivity());
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setNeedAddress(true);
        mlocationClient.setLocationListener(aMapLocation -> {
            mlocationClient.stopLocation();
            this.aMapLocation = aMapLocation;
            initCottonEnterprises(aMapLocation);
            SharedPrefsUtil.putValue(App.getContext(), "lat", aMapLocation.getLatitude() + "");
            SharedPrefsUtil.putValue(App.getContext(), "lng", aMapLocation.getLongitude() + "");
        });
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(2000);
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
    }

    @OnClick({R.id.ll_home_location_real, R.id.iv_home_service_real, R.id.ll_home_nimasile_real, R.id.tv_home_search_real,
            R.id.ll_home_location, R.id.iv_home_service, R.id.ll_home_nimasile, R.id.tv_home_search,
            R.id.ll_home_quality_query, R.id.ll_home_logistics_query, R.id.ll_home_find_money, R.id.ll_home_business_help,
            R.id.ll_home_red_paper_cotton, R.id.ll_home_search_map, R.id.tv_home_cotton_enterprises_more, R.id.ll_home_cotton_enterprises_error, R.id.tv_home_cotton_recommend_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home_location_real://首页选择地区
                Intent city = createIntent(CityActivity.class);
                city.putExtra("type", 1);
                startActivity(city);
                break;
            case R.id.iv_home_service_real://首页客服
                if (UserManager.getInstance().isLogin()) {
                    User user = UserManager.getInstance().getUser();
                    //                    if(user.getESjzcHylb().equals("3")){
                    //                        startActivity(new Intent(createIntent(ConversationActivity.class)));
                    //                    }else{
                    //                        startActivity(createIntent(ServiceActivity.class));
                    //                    }
                    if (user.getKflx().equals("0"))
                        startActivity(createIntent(ServiceActivity.class));
                    else
                        startActivity(new Intent(createIntent(ConversationActivity.class)));
                } else {
                    isLogin();
                }
                break;
            case R.id.ll_home_nimasile_real://你妈死了
                //                    getActivity().runOnUiThread(() -> {
                //                        topHolder.edit_home_search.performClick();
                //                        topHolder.edit_home_search.performClick();
                //                    });
                edit_home_search_real.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                break;
            case R.id.tv_home_search_real:
                if (edit_home_search_real.getText().toString().trim().equals("")) {
                    showToast("请输入搜索批号");
                } else {
                    String text = edit_home_search_real.getText().toString().trim();
                    SharedPrefsUtil.putValue(App.getContext(), "search", text);
                    MainStoreTransfer transfer = new MainStoreTransfer();
                    transfer.setContent(edit_home_search_real.getText().toString().trim());
                    EventBus.getDefault().post(transfer);
                    edit_home_search_real.setText("");
                }
                break;
            //                case R.id.sv_home:
            //                    InputMethodManager imm = (InputMethodManager)
            //                            getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            //                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            //                    break;
            case R.id.ll_home_location://首页选择地区
                Intent intent = createIntent(CityActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.iv_home_service://首页客服
                if (UserManager.getInstance().isLogin()) {
                    User user = UserManager.getInstance().getUser();
                    //                    if(user.getESjzcHylb().equals("3")){
                    //                        startActivity(new Intent(createIntent(ConversationActivity.class)));
                    //                    }else{
                    //                        startActivity(createIntent(ServiceActivity.class));
                    //                    }
                    if (user.getKflx().equals("0"))
                        startActivity(createIntent(ServiceActivity.class));
                    else
                        startActivity(new Intent(createIntent(ConversationActivity.class)));
                } else {
                    isLogin();
                }
                break;
            case R.id.ll_home_nimasile://你妈死了
                //                edit_home_search.performClick();
                //                edit_home_search.performClick();
                edit_home_search.requestFocus();
                break;
            case R.id.tv_home_search:
                if (edit_home_search.getText().toString().trim().equals("")) {
                    showToast("请输入搜索批号");
                } else {
                    String text = edit_home_search.getText().toString().trim();
                    SharedPrefsUtil.putValue(App.getContext(), "search", text);
                    MainStoreTransfer transfer = new MainStoreTransfer();
                    transfer.setContent(edit_home_search.getText().toString().trim());
                    EventBus.getDefault().post(transfer);
                    edit_home_search.setText("");
                }
                break;
            case R.id.ll_home_quality_query://查质量
                if (aMapLocation == null) {
                    startActivity(createIntent(QualityQueryActivity.class));
                } else {
                    Intent qualityIntent = new Intent(getActivity(), QualityQueryActivity.class);
                    qualityIntent.putExtra("lat", aMapLocation.getLatitude());
                    qualityIntent.putExtra("lng", aMapLocation.getLongitude());
                    startActivity(qualityIntent);
                }
                break;
            case R.id.ll_home_logistics_query://查物流
                startActivity(createIntent(LogisticsQuerySecondActivity.class));
                break;
            case R.id.ll_home_find_money://找资金
                if (UserManager.getInstance().isLogin()) {
                    startActivity(createIntent(FindMoneyActivity.class));
                } else {
                    isLogin();
                }
                break;
            case R.id.ll_home_business_help://买卖帮
                startActivity(createIntent(BusinessHelpActivity.class));
                break;
            case R.id.ll_home_red_paper_cotton://红包棉
                Intent redIntent = new Intent(getActivity(), RedPaperCottonActivity.class);
                if (aMapLocation != null) {
                    redIntent.putExtra("lat", aMapLocation.getLatitude());
                    redIntent.putExtra("lng", aMapLocation.getLongitude());
                }
                redIntent.putExtra("flag", 0);
                startActivity(redIntent);
                break;
            case R.id.ll_home_search_map://查地图
                startActivity(createIntent(SearchMapActivity.class));
                break;
            case R.id.tv_home_cotton_enterprises_more://棉企更多
                //                startActivity(createIntent(CottonEnterPrisesActivity.class));
                EventBus.getDefault().post(new MainNearByTransfer());
                break;
            case R.id.ll_home_cotton_enterprises_error:
                initCottonEnterprises(aMapLocation);
                break;
            case R.id.tv_home_cotton_recommend_more://棉花推荐更多
                //                startActivity(createIntent(CottonRecommendActivity.class));
                EventBus.getDefault().post(new MainStoreTransfer());
                break;
        }
    }


    private void initView() {
        loadSuccess = false;
        Util.setStatusBarColor(getActivity(), R.color.red);
        ll_home_fake.setVisibility(View.VISIBLE);
        //        sv_home.setVisibility(View.VISIBLE);
        rl_home_real.setVisibility(View.GONE);
        ViewGroup.LayoutParams para1;
        para1 = rl_home_banner.getLayoutParams();
        para1.height = getActivity().getWindowManager().getDefaultDisplay().getWidth() / 2;
        rl_home_banner.setLayoutParams(para1);

        if (EMClient.getInstance().chatManager().getUnreadMsgsCount() > 0) {
            iv_home_service_tips_real.setVisibility(View.VISIBLE);
            iv_home_service_tips.setVisibility(View.VISIBLE);
        } else {
            iv_home_service_tips_real.setVisibility(View.INVISIBLE);
            iv_home_service_tips.setVisibility(View.INVISIBLE);
        }

        if (listView.getHeaderViewsCount() == 0) {
            adapter = new MainStoreAdapter();
            MainStoreModel empty = new MainStoreModel("", "", "", "", "", null, "", "", "", "", "", "", "", "0", "", "", "", "", "", "", false, "", "");
            //        MainStoreModel empty = new MainStoreModel();
            List<MainStoreModel> list = new ArrayList<MainStoreModel>();
            list.add(empty);
            adapter.addList(list);
            View head = View.inflate(getActivity(), R.layout.activity_home_head, null);
            topHolder = new TopHolder(head);
            listView.addHeaderView(head);
            listView.setAdapter(adapter);
        }
        initMainControl();

        kl_home.setOnkbdStateListener(state -> {
            switch (state) {
                case KeyboardLayout.KEYBOARD_STATE_HIDE:
                    edit_home_search_real.clearFocus();
                    edit_home_search.clearFocus();
                    tv_home_search_real.setVisibility(View.GONE);
                    ll_home_nimasile_real.setGravity(Gravity.CENTER);
                    break;
                case KeyboardLayout.KEYBOARD_STATE_SHOW:
                    tv_home_search_real.setVisibility(View.VISIBLE);
                    ll_home_nimasile_real.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                    break;
            }
        });
        edit_home_search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                if (edit_home_search.getText().toString().trim().equals("")) {
                    showToast("请输入搜索批号");
                } else {
                    String text = edit_home_search.getText().toString().trim();
                    SharedPrefsUtil.putValue(App.getContext(), "search", text);
                    MainStoreTransfer transfer = new MainStoreTransfer();
                    transfer.setContent(edit_home_search.getText().toString().trim());
                    EventBus.getDefault().post(transfer);
                    edit_home_search.setText("");
                }
                return true;
            }
            return false;
        });
        edit_home_search_real.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                if (edit_home_search_real.getText().toString().trim().equals("")) {
                    showToast("请输入搜索批号");
                } else {
                    String text = edit_home_search_real.getText().toString().trim();
                    SharedPrefsUtil.putValue(App.getContext(), "search", text);
                    MainStoreTransfer transfer = new MainStoreTransfer();
                    transfer.setContent(edit_home_search_real.getText().toString().trim());
                    EventBus.getDefault().post(transfer);
                    edit_home_search_real.setText("");
                }
                return true;
            }
            return false;
        });
        edit_home_search.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                ll_home_nimasile.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                tv_home_search.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
            if (!hasFocus) {
                ll_home_nimasile.setGravity(Gravity.CENTER);
                tv_home_search.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edit_home_search.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        //        topHolder.edit_home_search.setOnFocusChangeListener((v, hasFocus) -> {
        //            if(hasFocus)
        //                getActivity().runOnUiThread(()-> topHolder.ll_home_nimasile.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL));
        //            if(!hasFocus)
        //                getActivity().runOnUiThread(()-> topHolder.ll_home_nimasile.setGravity(Gravity.CENTER));
        //        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int[] location = new int[2];
                topHolder.banner_home_top.getLocationInWindow(location); //获取在当前窗口内的绝对坐标
                topHolder.banner_home_top.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
                int[] enterprises_location = new int[2];
                topHolder.hlv_home_cotton_enterprises.getLocationInWindow(enterprises_location);
                topHolder.hlv_home_cotton_enterprises.getLocationOnScreen(enterprises_location);
                int result = 0;
                int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    result = getResources().getDimensionPixelSize(resourceId);
                }
                int cha = result + ll_home_top_real.getMeasuredHeight() - topHolder.banner_home_top.getMeasuredHeight();
                if (location[1] < cha && loadSuccess) {
                    ll_home_top_real.setBackgroundResource(R.color.green);
                    Util.setStatusBarColor(getActivity(), R.color.green);
                } else {
                    if(location[1] == 0 && enterprises_location[1] == 0 && loadSuccess){
                        ll_home_top_real.setBackgroundResource(R.color.green);
                        Util.setStatusBarColor(getActivity(), R.color.green);
                    } else {
                        ll_home_top_real.setBackgroundResource(R.color.transparent);
                        Util.setStatusBarColor(getActivity(), R.color.red);
                    }
                }
            }
        });
        initBanner();
        //        initCottonEnterprises();
        //        initCottonRecommend();
    }

    private void initMainControl() {
        List<MainHomeControlModel> list = new ArrayList<>();
        MainHomeControlModel qualityQuryModel = new MainHomeControlModel(Category.QUALITYQUERY.getName());
        list.add(qualityQuryModel);
        MainHomeControlModel logisticsQueryModel = new MainHomeControlModel(Category.LOGISTICSQUERY.getName());
        list.add(logisticsQueryModel);
        MainHomeControlModel findMoneyModel = new MainHomeControlModel(Category.FINDMONEY.getName());
        list.add(findMoneyModel);
        MainHomeControlModel bilOfLadingModel = new MainHomeControlModel(Category.BILOFLADING.getName());
        list.add(bilOfLadingModel);
        MainHomeControlModel businessHelpModel = new MainHomeControlModel(Category.BUSINESSHELP.getName());
        list.add(businessHelpModel);
        mainHomeControlAdapter = new MainHomeControlAdapter(list, getActivity());
        hlv_home_main_control.setAdapter(mainHomeControlAdapter);
        hlv_home_main_control.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0://查质量
                    if (aMapLocation == null) {
                        startActivity(createIntent(QualityQueryActivity.class));
                    } else {
                        Intent qualityIntent = new Intent(getActivity(), QualityQueryActivity.class);
                        qualityIntent.putExtra("lat", aMapLocation.getLatitude());
                        qualityIntent.putExtra("lng", aMapLocation.getLongitude());
                        startActivity(qualityIntent);
                    }
                    break;
                case 1://查物流
                    startActivity(createIntent(LogisticsQuerySecondActivity.class));
                    break;
                case 2://找资金
                    if (UserManager.getInstance().isLogin()) {
                        startActivity(createIntent(FindMoneyActivity.class));
                    } else {
                        isLogin();
                    }
                    break;
                case 3://提货单
                    //                    startActivity(createIntent(DeliveryOrderActivity.class));

                    if (UserManager.getInstance().isLogin()) {
                        //判断是否为企业账户
//                        if (UserManager.getInstance().getUserCategory().equals("1")) {
                            startActivity(createIntent(DeliveryOrderActivity.class));
//                        } else {
//                            showToast("抱歉,此账户不是企业账户");
//                        }
                    } else {
                        isLogin();
                    }
                    break;
                case 4://买卖帮
                    startActivity(createIntent(BusinessHelpActivity.class));
                    break;

                case 5://描述棉
                    break;
                case 6://绿卡棉
                    break;
            }
        });
        topHolder.hlv_home_main_control.setAdapter(mainHomeControlAdapter);
        topHolder.hlv_home_main_control.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0://查质量
                    if (aMapLocation == null) {
                        startActivity(createIntent(QualityQueryActivity.class));
                    } else {
                        Intent qualityIntent = new Intent(getActivity(), QualityQueryActivity.class);
                        qualityIntent.putExtra("lat", aMapLocation.getLatitude());
                        qualityIntent.putExtra("lng", aMapLocation.getLongitude());
                        startActivity(qualityIntent);
                    }
                    break;
                case 1://查物流
                    startActivity(createIntent(LogisticsQuerySecondActivity.class));
                    break;
                case 2://找资金
                    if (UserManager.getInstance().isLogin()) {
                        startActivity(createIntent(FindMoneyActivity.class));
                    } else {
                        isLogin();
                    }
                    break;
                case 3://提货单
                    //                    startActivity(createIntent(DeliveryOrderActivity.class));

                    if (UserManager.getInstance().isLogin()) {
                        //判断是否为企业账户
                        if (UserManager.getInstance().getUserCategory().equals("1")) {
                            startActivity(createIntent(DeliveryOrderActivity.class));
                        } else {
                            showToast("抱歉,此账户不是企业账户");
                        }
                    } else {
                        isLogin();
                    }
                    break;
                case 4://买卖帮
                    startActivity(createIntent(BusinessHelpActivity.class));
                    break;
                case 5://描述棉
                    break;
                case 6://绿卡棉
                    break;
            }
        });
    }

    /**
     * 棉花推荐
     */
    private void initCottonRecommend(AMapLocation aMapLocation) {
        String lat = "";
        String lng = "";
        if (aMapLocation != null) {
            lat = aMapLocation.getLatitude() + "";
            lng = aMapLocation.getLongitude() + "";
        }
        adapter = new MainStoreAdapter();
        listView.setAdapter(adapter);
        String finalLat = lat;
        String finalLng = lng;
        NestRefreshManager<MainStoreModel> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, (page, perPage) -> {
            StoreService api = ApiUtil.createDefaultApi(StoreService.class);
            return api.list("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "7", finalLng, finalLat, (page + 1) + "", perPage + "").map(new HttpResultFunc<>());
        });
        //        nestRefreshManager.isNeedError(false);
        nestRefreshManager.setPullLoadEnable(true);
        nestRefreshManager.setHttpError(e -> {
            adapter.clear();
            MainStoreModel empty = new MainStoreModel("", "", "", "", "", null, "", "", "", "", "", "", "", "0", "", "", "", "", "", "", false, "", "");
            List<MainStoreModel> temp_list = new ArrayList<MainStoreModel>();
            temp_list.add(empty);
            adapter.addList(temp_list);
            adapter.notifyDataSetInvalidated();
            nestRefreshManager.getRefreshLayout().resetView();
        });
        nestRefreshManager.setCallBack((allList, currentList) -> {
            adapter.clear();
            adapter.addList(allList);
            adapter.notifyDataSetChanged();
            rl_home_real.setVisibility(View.VISIBLE);
            ll_home_fake.setVisibility(View.GONE);
            //            sv_home.setVisibility(View.GONE);
            if (!edit_home_search.getText().toString().trim().equals("")) {
                edit_home_search_real.setText(edit_home_search.getText().toString().trim());
            }
            loadSuccess = true;
        });
        nestRefreshManager.doApi();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            //            if(UserManager.getInstance().isLogin()) {
            Intent intent = new Intent(getActivity(), RedPaperCottonDetailActivity.class);
            intent.putExtra("data", adapter.getList().get(position - listView.getHeaderViewsCount()));
            startActivity(intent);
            //            }else{
            //                isLogin();
            //            }
        });
    }

    /**
     * 附近棉企
     */
    private void initCottonEnterprises(AMapLocation aMapLocation) {
        String lat = "";
        String lng = "";
        String province = "";
        String city = "";
        String countie = "";
        if (aMapLocation != null) {
            lat = aMapLocation.getLatitude() + "";
            lng = aMapLocation.getLongitude() + "";
            if (localId.equals("")) {
                province = aMapLocation.getProvince();
                city = aMapLocation.getCity();
                countie = aMapLocation.getDistrict();
            }
        }
        hlv_home_cotton_enterprises.setVisibility(View.VISIBLE);
        ll_home_cotton_enterprises_error.setVisibility(View.GONE);
        topHolder.hlv_home_cotton_enterprises.setVisibility(View.VISIBLE);
        topHolder.ll_home_cotton_enterprises_error.setVisibility(View.GONE);
        CottonEnterprisesService api = ApiUtil.createDefaultApi(CottonEnterprisesService.class);
        ApiUtil.doDefaultApi(api.list("", TextUtils.isEmpty(localId) ? "" : localName, localId, province, city, countie, lng, lat, "", "0", "1", "100"), data -> {
            if (data != null && !data.isEmpty()) {
                cottonEnterprises = data;
                cottonEnterprisesAdapter = new CottonEnterprisesAdapter(getActivity(), data);
                hlv_home_cotton_enterprises.setAdapter(cottonEnterprisesAdapter);
                hlv_home_cotton_enterprises.setOnItemClickListener((parent, view, position, id) -> {
                    if (cottonEnterprisesAdapter.isItemClick()) {
                        Intent intent = new Intent(getActivity(), NearbyDetailsActivity.class);
                        intent.putExtra("id", cottonEnterprisesAdapter.getItem(position).getCustId());
                        startActivity(intent);
                    } else {
                        cottonEnterprisesAdapter.setItemClick(true);
                    }
                });
                topHolder.hlv_home_cotton_enterprises.setAdapter(cottonEnterprisesAdapter);
                topHolder.hlv_home_cotton_enterprises.setOnItemClickListener((parent, view, position, id) -> {
                    if (cottonEnterprisesAdapter.isItemClick()) {
                        Intent intent = new Intent(getActivity(), NearbyDetailsActivity.class);
                        intent.putExtra("id", cottonEnterprisesAdapter.getItem(position).getCustId());
                        startActivity(intent);
                    } else {
                        cottonEnterprisesAdapter.setItemClick(true);
                    }
                });
            }
            if (data == null || data.isEmpty()) {
                cottonEnterprisesAdapter = new CottonEnterprisesAdapter(getActivity(), new ArrayList<CottonEnterprisesModel>());
                hlv_home_cotton_enterprises.setAdapter(cottonEnterprisesAdapter);
                hlv_home_cotton_enterprises.setVisibility(View.INVISIBLE);
                ll_home_cotton_enterprises_error.setVisibility(View.VISIBLE);
                tv_home_cotton_enterprises_error_info.setText("附近没有找到棉企");
                topHolder.hlv_home_cotton_enterprises.setAdapter(cottonEnterprisesAdapter);
                topHolder.hlv_home_cotton_enterprises.setVisibility(View.INVISIBLE);
                topHolder.ll_home_cotton_enterprises_error.setVisibility(View.VISIBLE);
                topHolder.tv_home_cotton_enterprises_error_info.setText("附近没有找到棉企");
            }
        });
    }

    /**
     * banner
     */
    private void initBanner() {
        BannerService api = ApiUtil.createDefaultApi(BannerService.class);
        ApiUtil.doDefaultApi(api.list(), data -> {
            if (data != null && !data.isEmpty()) {
                List<String> picList = new ArrayList<String>();
                List<String> urlList = new ArrayList<String>();
                for (BannerModel bannerModel : data) {
                    picList.add(bannerModel.getEzAimg());
                    urlList.add(bannerModel.getEzAurl());
                }
                banner_home_top.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, picList)
                        .startTurning(2000)
                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                        .setPageIndicator(new int[]{R.drawable.page_indicator, R.drawable.page_indicator_focused})
                        .setOnItemClickListener(position -> {
                            Intent intent = new Intent(getActivity(), WebpageActivity.class);
                            intent.putExtra("data", data.get(position));
                            startActivity(intent);
                        });
                topHolder.banner_home_top.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, picList)
                        .startTurning(2000)
                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                        .setPageIndicator(new int[]{R.drawable.page_indicator, R.drawable.page_indicator_focused})
                        .setOnItemClickListener(position -> {
                            Intent intent = new Intent(getActivity(), WebpageActivity.class);
                            intent.putExtra("data", data.get(position));
                            startActivity(intent);
                        });
                //                ll_home_top_real.bringToFront();
            }
        });
    }

    public void onEventMainThread(CityTransfer cityTransfer) {
        if (cityTransfer.getType() == 1) {
            SharedPrefsUtil.putValue(getActivity(), SharedPrefsUtil.KEEP_CITY, cityTransfer.getName());
            SharedPrefsUtil.putValue(getActivity(), SharedPrefsUtil.KEEP_CITY_ID, cityTransfer.getId());
            localId = cityTransfer.getId();
            localName = cityTransfer.getName();
            tv_home_location_real.setText(cityTransfer.getName());
            tv_home_location.setText(cityTransfer.getName());
            autoLocation = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    //判断用户是否登陆
    private void isLogin() {
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_servicelist_dialog, null);
        TextView title_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_title_tv);
        TextView content_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_content_tv);
        TextView verify_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_verify_tv);
        TextView cancel_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_cancel_tv);
        title_tv.setText("提示");
        content_tv.setText("未登录状态，没有权限查看该内容，是否选择登录？");
        verify_tv.setOnClickListener(v -> {
            //登录页面
            startActivity(createIntent(LoginActivity.class));
            mShowLoginDialog.dismiss();
        });
        cancel_tv.setOnClickListener(v -> mShowLoginDialog.dismiss());
        mShowLoginDialog = new AlertDialog.Builder(getActivity()).setView(dialogView).create();
        mShowLoginDialog.show();
    }

    class TopHolder {
        @InjectView(R.id.rl_home_banner)
        RelativeLayout rl_home_banner;
        @InjectView(R.id.banner_home_top)
        ConvenientBanner banner_home_top;
        @InjectView(R.id.hlv_home_main_control)
        HorizontalListView hlv_home_main_control;
        @InjectView(R.id.hlv_home_cotton_enterprises)
        HorizontalListView hlv_home_cotton_enterprises;
        @InjectView(R.id.ll_home_cotton_enterprises_error)
        LinearLayout ll_home_cotton_enterprises_error;
        @InjectView(R.id.tv_home_cotton_enterprises_error_info)
        TextView tv_home_cotton_enterprises_error_info;

        public TopHolder(View view) {
            ButterKnife.inject(this, view);
            ViewGroup.LayoutParams para2;
            para2 = rl_home_banner.getLayoutParams();
            para2.height = getActivity().getWindowManager().getDefaultDisplay().getWidth() / 2;
            rl_home_banner.setLayoutParams(para2);
        }

        @OnClick({R.id.ll_home_quality_query, R.id.ll_home_logistics_query, R.id.ll_home_find_money, R.id.ll_home_business_help, R.id.ll_home_cotton_enterprises_error,
                R.id.ll_home_red_paper_cotton, R.id.ll_home_search_map, R.id.tv_home_cotton_enterprises_more, R.id.tv_home_cotton_recommend_more})
        public void onClick(View view) {
            switch (view.getId()) {
                //                case R.id.sv_home:
                //                    InputMethodManager imm = (InputMethodManager)
                //                            getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                //                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                //                    break;
                case R.id.ll_home_quality_query://查质量
                    if (aMapLocation == null) {
                        startActivity(createIntent(QualityQueryActivity.class));
                    } else {
                        Intent intent = new Intent(getActivity(), QualityQueryActivity.class);
                        intent.putExtra("lat", aMapLocation.getLatitude());
                        intent.putExtra("lng", aMapLocation.getLongitude());
                        startActivity(intent);
                    }
                    break;
                case R.id.ll_home_logistics_query://查物流
                    startActivity(createIntent(LogisticsQuerySecondActivity.class));
                    break;
                case R.id.ll_home_find_money://找资金
                    if (UserManager.getInstance().isLogin()) {
                        startActivity(createIntent(FindMoneyActivity.class));
                    } else {
                        isLogin();
                    }
                    break;
                case R.id.ll_home_business_help://买卖帮
                    startActivity(createIntent(BusinessHelpActivity.class));
                    break;
                case R.id.ll_home_red_paper_cotton://红包棉
                    Intent intent = new Intent(getActivity(), RedPaperCottonActivity.class);
                    if (aMapLocation != null) {
                        intent.putExtra("lat", aMapLocation.getLatitude());
                        intent.putExtra("lng", aMapLocation.getLongitude());
                    }
                    intent.putExtra("flag", 0);
                    startActivity(intent);
                    break;
                case R.id.ll_home_search_map://查地图
                    startActivity(createIntent(SearchMapActivity.class));
                    break;
                case R.id.tv_home_cotton_enterprises_more://棉企更多
                    //                startActivity(createIntent(CottonEnterPrisesActivity.class));
                    EventBus.getDefault().post(new MainNearByTransfer());
                    break;
                case R.id.ll_home_cotton_enterprises_error:
                    initCottonEnterprises(aMapLocation);
                    break;
                case R.id.tv_home_cotton_recommend_more://棉花推荐更多
                    //                startActivity(createIntent(CottonRecommendActivity.class));
                    EventBus.getDefault().post(new MainStoreTransfer());
                    break;
            }
        }
    }
}
