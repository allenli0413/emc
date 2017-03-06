package com.emiancang.emiancang.main.home.logisticsquery.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.emiancang.emiancang.App;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.eventbean.LogisticsQueryFilterTransfer;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.main.home.logisticsquery.adapter.LogisticsQuerySecondAdatper;
import com.emiancang.emiancang.main.home.logisticsquery.model.LogisticsQuerySecondModel;
import com.emiancang.emiancang.main.home.logisticsquery.service.LogisticsQuerySecondService;
import com.emiancang.emiancang.main.home.logisticsquery.service.LogisticsQueryService;
import com.emiancang.emiancang.main.home.logisticsquery.service.ShowAnotheWindow;
import com.emiancang.emiancang.uitl.EditInputFilter;
import com.emiancang.emiancang.uitl.Util;
import com.litesuits.android.log.Log;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import de.greenrobot.event.EventBus;

/**
 * Created by yuanyueqing on 2017/2/22.
 */

public class LogisticsQuerySecondActivity extends TitleActivity implements ShowAnotheWindow {

    @InjectView(R.id.edit_title_search_middle)
    EditText edit_title_search_middle;
    @InjectView(R.id.ll_title_search_right)
    LinearLayout ll_title_search_right;
    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;

    LogisticsQuerySecondAdatper adapter;

    ViewControl viewControl;

    boolean road = false;
    boolean train = false;

//    PopupWindow popupWindow;
//    View contentView;

    private String startDate = "";
    private String endDate = "";
    private String startAddressId = "";
    private String startAddressName = "";
    private String endAddressId = "";
    private String endAddressName = "";
    private String serviceName = "";
    private String startPrice = "";
    private String endPrice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_query_second);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
        initHead();
        initView();
        initData();
    }

    private void initView() {
        viewControl = ViewControlUtil.create2View(nestRefreshLayout, "没找到对应数据", "没找到对应数据",R.drawable.icon_content_empty);
        adapter = new LogisticsQuerySecondAdatper();
        listView.setAdapter(adapter);

        edit_title_search_middle.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId== EditorInfo.IME_ACTION_SEARCH ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER)) {
                if(edit_title_search_middle.getText().toString().trim().equals("")){
                    showToast("请输入搜索内容");
                }else {
                    initData();
                }
                return true;
            }
            return false;
        });

//        popupWindow = new PopupWindow(getActivity());
//        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        contentView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_logistics_filter_popwindow, null);
//        popupWindow.setContentView(contentView);
//        // 使其聚集
//        popupWindow.setFocusable(true);
//        // 设置允许在外点击消失
//        popupWindow.setOutsideTouchable(true);
//        ColorDrawable dw = new ColorDrawable(0xFFFFFF);
//        // 设置SelectPicPopupWindow弹出窗体的背景
//        popupWindow.setBackgroundDrawable(dw);
//        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
    }

    private void initData() {
        NestRefreshManager<LogisticsQuerySecondModel> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, (page, perPage) -> {
            String transport = "";
            if(road && train)
                transport = "1,2";
            else {
                if(road)
                    transport = "1";
                if(train)
                    transport = "2";
                if(!road && !train)
                    transport = "1,2";
            }
            serviceName = edit_title_search_middle.getText().toString().trim();
            LogisticsQuerySecondService api = ApiUtil.createDefaultApi(LogisticsQuerySecondService.class);
            return api.list("" + (page + 1), "" + perPage, serviceName, startDate, endDate, transport, startPrice, endPrice, startAddressId, endAddressId).map(new HttpResultFunc<>());
        });
        nestRefreshManager.setPullLoadEnable(true);
        nestRefreshManager.setHttpError(e -> Log.i("test", e.toString()));
        nestRefreshManager.setCallBack((allList, currentList) -> {
            adapter.clear();
            adapter.addList(allList);
            adapter.notifyDataSetChanged();
        });
        nestRefreshManager.doApi();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = createIntent(LogisticsQuerySecondDetailActivity.class);
            intent.putExtra("serviceId", adapter.getItem(position - listView.getHeaderViewsCount()).getServiceId());
            startActivity(intent);
        });
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("查物流", getResources().getColor(R.color.white));
        getHeadBar().showLeftImage();
        getHeadBar().setLeftOnClickListner(v -> finish());
        getHeadBar().showRightText();
        getHeadBar().setRightTitle("物流需求", getResources().getColor(R.color.white));
        getHeadBar().setRightOnClickListner(v -> startActivity(createIntent(LogisticsQueryActivity.class)));

        ll_title_search_right.setOnClickListener(v -> {
            Intent intent = createIntent(LogisticsQueryFilterActivity.class);
            intent.putExtra("data",
                    new LogisticsQueryFilterTransfer(startAddressId, endAddressId, startAddressName, endAddressName, endDate, endPrice, startPrice, road, startDate, train));
            startActivity(intent);
        });
    }

    private void showFilterWindow() {
//        final PopupWindow popupWindow = new PopupWindow(getActivity());
//        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_logistics_filter_popwindow, null);
//        popupWindow.setContentView(contentView);
//        // 使其聚集
//        popupWindow.setFocusable(true);
//        // 设置允许在外点击消失
//        popupWindow.setOutsideTouchable(true);
//        ColorDrawable dw = new ColorDrawable(0xFFFFFF);
//        // 设置SelectPicPopupWindow弹出窗体的背景
//        popupWindow.setBackgroundDrawable(dw);
//        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//        popupWindow.showAsDropDown(getActivity().findViewById(R.id.ll_title_search));
//        popupWindow.setOnDismissListener(() -> initData());

//        contentView.setOnTouchListener((v, event) -> {
//            int height = contentView.findViewById(R.id.ll_logistics_filter_window_content).getMeasuredHeight();
//            int y = (int) event.getY();
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                if (y > height) {
//                    popupWindow.dismiss();
//                }
//            }
//            return true;
//        });
//
//        LinearLayout ll_logistics_filter_window_time_start = (LinearLayout) contentView.findViewById(R.id.ll_logistics_filter_window_time_start);
//        ll_logistics_filter_window_time_start.setOnClickListener(v -> {
//            showDatePick();
//        });
//        LinearLayout ll_logistics_filter_window_time_end = (LinearLayout) contentView.findViewById(R.id.ll_logistics_filter_window_time_end);
//        ll_logistics_filter_window_time_end.setOnClickListener(v -> {
//
//        });
//        LinearLayout ll_logistics_filter_window_address_start = (LinearLayout) contentView.findViewById(R.id.ll_logistics_filter_window_address_start);
//        ll_logistics_filter_window_address_start.setOnClickListener(v -> {
//
//        });
//        LinearLayout ll_logistics_filter_window_address_end = (LinearLayout) contentView.findViewById(R.id.ll_logistics_filter_window_address_end);
//        ll_logistics_filter_window_address_end.setOnClickListener(v -> {
//
//        });
//        ImageView iv_logistics_filter_window_transport_road = (ImageView) contentView.findViewById(R.id.iv_logistics_filter_window_transport_road);
//        LinearLayout ll_logistics_filter_window_transport_road = (LinearLayout) contentView.findViewById(R.id.ll_logistics_filter_window_transport_road);
//        ll_logistics_filter_window_transport_road.setOnClickListener(v -> {
//            road = !road;
//            if(road)
//                iv_logistics_filter_window_transport_road.setImageResource(R.drawable.icon_checked);
//            else
//                iv_logistics_filter_window_transport_road.setImageResource(R.drawable.icon_unchecked);
//        });
//        ImageView iv_logistics_filter_window_transport_train = (ImageView) contentView.findViewById(R.id.iv_logistics_filter_window_transport_train);
//        LinearLayout ll_logistics_filter_window_transport_train = (LinearLayout) contentView.findViewById(R.id.ll_logistics_filter_window_transport_train);
//        ll_logistics_filter_window_transport_train.setOnClickListener(v -> {
//            train = !train;
//            if(train)
//                iv_logistics_filter_window_transport_train.setImageResource(R.drawable.icon_checked);
//            else
//                iv_logistics_filter_window_transport_train.setImageResource(R.drawable.icon_unchecked);
//        });
//        InputFilter[] filters = { new EditInputFilter() };
//        EditText edit_logistics_filter_window_price_start = (EditText) contentView.findViewById(R.id.edit_logistics_filter_window_price_start);
//        EditText edit_logistics_filter_window_price_end = (EditText) contentView.findViewById(R.id.edit_logistics_filter_window_price_end);
//        edit_logistics_filter_window_price_start.setFilters(filters);
//        edit_logistics_filter_window_price_end.setFilters(filters);
    }

    @Override
    public void showDatePick() {
        final PopupWindow popupWindow = new PopupWindow(getActivity());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_store_sort_popupwindow, null);
        popupWindow.setContentView(contentView);
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0xFFFFFF);
        // 设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWindow.showAtLocation(getActivity().findViewById(R.id.nestRefreshLayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void showLocationPick() {

    }

    public void onEventMainThread(LogisticsQueryFilterTransfer logisticsQueryFilterTransfer){
        startAddressId = logisticsQueryFilterTransfer.getStartAddressId();
        startAddressName = logisticsQueryFilterTransfer.getStartAddressName();
        endAddressId = logisticsQueryFilterTransfer.getEndAddressId();
        endAddressName = logisticsQueryFilterTransfer.getEndAddressName();
        startDate = logisticsQueryFilterTransfer.getStartDate();
        endDate = logisticsQueryFilterTransfer.getEndDate();
        startPrice = logisticsQueryFilterTransfer.getLowPrice();
        endPrice = logisticsQueryFilterTransfer.getHighPrice();
        road = logisticsQueryFilterTransfer.isRoad();
        train = logisticsQueryFilterTransfer.isTrain();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
