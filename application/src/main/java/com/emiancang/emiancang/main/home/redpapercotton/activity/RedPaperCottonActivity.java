package com.emiancang.emiancang.main.home.redpapercotton.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.emiancang.emiancang.uitl.DataUtils;
import com.emiancang.emiancang.uitl.EditInputFilter;
import com.emiancang.emiancang.view.KeyboardLayout;
import com.litesuits.android.log.Log;
import com.litesuits.common.utils.InputMethodUtils;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.login.LoginActivity;
import com.emiancang.emiancang.main.home.qualityquery.activity.QualityContrastDetailActivity;
import com.emiancang.emiancang.main.home.qualityquery.adapter.FilterGridviewAdapter;
import com.emiancang.emiancang.main.home.qualityquery.model.FilterGridviewModel;
import com.emiancang.emiancang.main.home.qualityquery.service.QualityQueryService;
import com.emiancang.emiancang.main.home.redpapercotton.adapter.RedPaperCottonAdapter;
import com.emiancang.emiancang.main.home.redpapercotton.model.RedPaperCottonModel;
import com.emiancang.emiancang.main.home.redpapercotton.service.RedPaperCottonService;
import com.emiancang.emiancang.main.home.service.StoreService;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.store.adapter.MainStoreAdapter;
import com.emiancang.emiancang.store.model.MainStoreModel;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.uitl.Utils;
import com.emiancang.emiancang.user.UserManager;
import com.emiancang.emiancang.view.ScrollViewGridview;
import com.emiancang.emiancang.view.SeekBarPressure;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;

/**
 * Created by yuanyueqing on 2016/11/4.
 */

public class RedPaperCottonActivity extends TitleActivity {

    @InjectView(R.id.kl_store)
    KeyboardLayout kl_store;
    @InjectView(R.id.tv_title_search_left)
    TextView tv_title_search_left;
    @InjectView(R.id.iv_title_search_right)
    ImageView iv_title_search_right;
    @InjectView(R.id.edit_title_search_middle)
    EditText edit_title_search_middle;
    @InjectView(R.id.tv_title_search_button)
    TextView tv_title_search_button;
    @InjectView(R.id.tv_title_search_right)
    TextView tv_title_search_right;
    @InjectView(R.id.iv_store_main_length)
    ImageView iv_store_main_length;
    @InjectView(R.id.iv_store_main_ma)
    ImageView iv_store_main_ma;
    @InjectView(R.id.iv_store_main_strength)
    ImageView iv_store_main_strength;
    @InjectView(R.id.iv_store_main_origin)
    ImageView iv_store_main_origin;
    @InjectView(R.id.iv_store_main_sort)
    ImageView iv_store_main_sort;

//    @InjectView(R.id.gray_layout_fragment_main_store)
//    View gray_layout;

    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;

    MainStoreAdapter adapter;

    List<FilterGridviewModel> length_list;
    FilterGridviewAdapter length_adapter;
    List<FilterGridviewModel> ma_list;
    FilterGridviewAdapter ma_adapter;
    List<FilterGridviewModel> strength_list;
    FilterGridviewAdapter strength_adapter;
    List<FilterGridviewModel> origin_list;
    FilterGridviewAdapter origin_adapter;
    List<FilterGridviewModel> sortList;
    List<FilterGridviewModel> typeList;
    FilterGridviewAdapter typeAdapter;
    List<FilterGridviewModel> yearList;
    FilterGridviewAdapter yearAdapter;
    List<FilterGridviewModel> colorLevelList;
    FilterGridviewAdapter colorLevelAdapter;

    double lat = 0;
    double lng = 0;

    int reversion_min;
    int reversion_max;
    int inclusion_min;
    int inclusion_max;
    int break_min;
    int break_max;
    int length_uniformity_min;
    int length_uniformity_max;

    String ph = "";
    String length = "";
    String ma = "";
    String strength = "";
    String origin = "";
    String sort = "";
    String type = "";
    String pickType = "";
    String year = "";
    String color = "";

    String micronaire_value_min = "";
    String micronaire_value_max = "";
    String price_min = "";
    String price_max = "";

    private AlertDialog mShowLoginDialog;

    ViewControl viewControl;

    View headerView;

    int flag = 0;

    boolean refresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_store);
        ButterKnife.inject(this);
        viewControl = ViewControlUtil.create2View(nestRefreshLayout, "没找到推荐棉花", "没找到推荐棉花",R.drawable.icon_content_empty);
        initStatus();
        initHead();
        initView();
    }

    private void initStatus() {
        flag = getIntent().getIntExtra("flag", 0);
        if(flag == 1) {
            ph = getIntent().getStringExtra("ph");
            edit_title_search_middle.setText(ph);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        filterData();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(),R.color.green);
        getHeadBar().hideHeader();
        tv_title_search_left.setVisibility(View.GONE);
        iv_title_search_right.setImageResource(R.drawable.back_icon_selector);
        iv_title_search_right.setOnClickListener(v -> finish());
        edit_title_search_middle.setHint("请输入批号/供货商/加工厂/仓库");
        edit_title_search_middle.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                tv_title_search_button.setVisibility(View.VISIBLE);
            }else{
                tv_title_search_button.setVisibility(View.GONE);
            }
        });
        edit_title_search_middle.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId== EditorInfo.IME_ACTION_SEARCH ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER)) {
                if(edit_title_search_middle.getText().toString().trim().equals("")){
                    showToast("请输入搜索内容");
                }else {
                    filterData();
                }
                return true;
            }
            return false;
        });
        tv_title_search_right.setOnClickListener(v -> {
            showFilterWindow();
        });
        kl_store.setOnkbdStateListener(state -> {
            switch (state) {
                case KeyboardLayout.KEYBOARD_STATE_HIDE:
                    edit_title_search_middle.clearFocus();
                    tv_title_search_button.setVisibility(View.GONE);
                    break;
                case KeyboardLayout.KEYBOARD_STATE_SHOW:
                    tv_title_search_button.setVisibility(View.VISIBLE);
                    break;
            }
        });
    }

    private void initView() {
        lat = getIntent().getDoubleExtra("lat", 0);
        lng = getIntent().getDoubleExtra("lng", 0);
        reversion_min = 0;
        reversion_max = 10;
        inclusion_min = 0;
        inclusion_max = 10;
        break_min = 21;
        break_max = 33;
        length_uniformity_min = 73;
        length_uniformity_max = 100;
        length_list = DataUtils.getData(R.id.ll_store_main_length);
        length_adapter = new FilterGridviewAdapter(length_list);
        ma_list = DataUtils.getData(R.id.ll_store_main_ma);
        ma_adapter = new FilterGridviewAdapter(ma_list);
        strength_list = DataUtils.getData(R.id.ll_store_main_strength);
        strength_adapter = new FilterGridviewAdapter(strength_list);
        origin_list = DataUtils.getData(R.id.ll_store_main_origin);
        origin_adapter = new FilterGridviewAdapter(origin_list);

        sortList = DataUtils.getSortData();
        typeList = DataUtils.getFilterData(DataUtils.TYPE);
        typeAdapter = new FilterGridviewAdapter(typeList);
        yearList = DataUtils.getFilterData(DataUtils.YEAR);
        yearAdapter = new FilterGridviewAdapter(yearList);
        colorLevelList = DataUtils.getFilterData(DataUtils.COLORLEVEL);
        colorLevelAdapter = new FilterGridviewAdapter(colorLevelList);
    }

    private void showFilterWindow() {
        final PopupWindow popupWindow = new PopupWindow(getActivity());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int popWidth = (int) Math.round(width * 0.9);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_store_filter_popwindow, null);
        popupWindow.setContentView(contentView);
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        ColorDrawable dw = new ColorDrawable(0xFFFFFF);
        // 设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度-10
//        int xPos = windowManager.getDefaultDisplay().getWidth();
//        popupWindow.showAsDropDown(getActivity().findViewById(id), xPos, 5);
        popupWindow.showAtLocation(getActivity().findViewById(R.id.tv_title_search_right), Gravity.RIGHT, 0, 0);
//        gray_layout.setVisibility(View.VISIBLE);
        contentView.setOnTouchListener((v, event) -> {
            int sv_width = contentView.findViewById(R.id.sv_filter).getMeasuredWidth();
            int black_width = width - sv_width;
            int x = (int) event.getX();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (x < black_width) {
                    popupWindow.dismiss();
                }
            }
            return true;
        });

        ScrollView sv_filter = (ScrollView) contentView.findViewById(R.id.sv_filter);
        ViewGroup.LayoutParams params = sv_filter.getLayoutParams();
        params.width = popWidth;
        sv_filter.setLayoutParams(params);
        ScrollViewGridview gv_store_filter_cotton_type = (ScrollViewGridview) contentView.findViewById(R.id.gv_store_filter_cotton_type);
        gv_store_filter_cotton_type.setAdapter(typeAdapter);
        gv_store_filter_cotton_type.setOnItemClickListener((parent, view, position, id) -> {
            for (int i = 0; i < typeList.size(); i++) {
                if(i == position)
                    typeList.get(i).setSelected(!typeList.get(i).isSelected());
            }
            typeAdapter.setList(typeList);
            typeAdapter.notifyDataSetInvalidated();
        });
        ScrollViewGridview gv_store_filter_year = (ScrollViewGridview) contentView.findViewById(R.id.gv_store_filter_year);
        gv_store_filter_year.setAdapter(yearAdapter);
        gv_store_filter_year.setOnItemClickListener((parent, view, position, id) -> {
            for (int i = 0; i < yearList.size(); i++) {
                if(i == position)
                    yearList.get(i).setSelected(!yearList.get(i).isSelected());
            }
            yearAdapter.setList(yearList);
            yearAdapter.notifyDataSetInvalidated();
        });
        ScrollViewGridview gv_store_filter_color_level = (ScrollViewGridview) contentView.findViewById(R.id.gv_store_filter_color_level);
        gv_store_filter_color_level.setAdapter(colorLevelAdapter);
        gv_store_filter_color_level.setOnItemClickListener((parent, view, position, id) -> {
            for (int i = 0; i < colorLevelList.size(); i++) {
                if(i == position)
                    colorLevelList.get(i).setSelected(!colorLevelList.get(i).isSelected());
            }
            colorLevelAdapter.setList(colorLevelList);
            colorLevelAdapter.notifyDataSetInvalidated();
        });
        SeekBarPressure seekbar_reversion = (SeekBarPressure) contentView.findViewById(R.id.seekbar_reversion);
        SeekBarPressure seekbar_inclusion = (SeekBarPressure) contentView.findViewById(R.id.seekbar_inclusion);
        SeekBarPressure seekbar_break = (SeekBarPressure) contentView.findViewById(R.id.seekbar_break);
        SeekBarPressure seekbar_length_uniformity = (SeekBarPressure) contentView.findViewById(R.id.seekbar_length_uniformity);
        seekbar_reversion.setDefaultMIN(0);
        seekbar_reversion.setDefaultMAX(10);
        seekbar_inclusion.setDefaultMIN(0);
        seekbar_inclusion.setDefaultMAX(10);
        seekbar_break.setDefaultMIN(21);
        seekbar_break.setDefaultMAX(33);
        seekbar_length_uniformity.setDefaultMIN(73);
        seekbar_length_uniformity.setDefaultMAX(100);
        seekbar_reversion.setProgressLow((double)reversion_min / 10 * 100);
        seekbar_reversion.setProgressHigh((double)reversion_max / 10 * 100);
        seekbar_inclusion.setProgressLow((double)inclusion_min / 10 * 100);
        seekbar_inclusion.setProgressHigh((double)inclusion_max / 10 * 100);
        seekbar_break.setProgressLow((double)(break_min - 21) / 12 * 100);
        seekbar_break.setProgressHigh((double)(break_max - 21) / 12 * 100);
        seekbar_length_uniformity.setProgressLow((double)(length_uniformity_min - 73) / 27 * 100);
        seekbar_length_uniformity.setProgressHigh((double)(length_uniformity_max - 73) / 27 * 100);
        EditText edit_store_filter_micronaire_value_min = (EditText) contentView.findViewById(R.id.edit_store_filter_micronaire_value_min);
        EditText edit_store_filter_micronaire_value_max = (EditText) contentView.findViewById(R.id.edit_store_filter_micronaire_value_max);
        EditText edit_store_price_min = (EditText) contentView.findViewById(R.id.edit_store_price_min);
        EditText edit_store_price_max = (EditText) contentView.findViewById(R.id.edit_store_price_max);
        edit_store_filter_micronaire_value_min.setText(micronaire_value_min);
        edit_store_filter_micronaire_value_max.setText(micronaire_value_max);
        edit_store_price_min.setText(price_min);
        edit_store_price_max.setText(price_min);
        InputFilter[] filters = { new EditInputFilter() };
        edit_store_price_min.setFilters(filters);
        edit_store_price_max.setFilters(filters);
        InputFilter[] micronaire_filters = { new EditInputFilter(1) };
        edit_store_filter_micronaire_value_min.setFilters(micronaire_filters);
        edit_store_filter_micronaire_value_max.setFilters(micronaire_filters);
        TextView tv_reset = (TextView) contentView.findViewById(R.id.tv_store_filter_reset);
        tv_reset.setOnClickListener(v -> {
            for (FilterGridviewModel filterGridviewModel : typeList) {
                filterGridviewModel.setSelected(false);
            }
            for (FilterGridviewModel filterGridviewModel : yearList) {
                filterGridviewModel.setSelected(false);
            }
            for (FilterGridviewModel filterGridviewModel : colorLevelList) {
                filterGridviewModel.setSelected(false);
            }
            edit_store_filter_micronaire_value_min.setText("");
            edit_store_filter_micronaire_value_max.setText("");
            edit_store_price_min.setText("");
            edit_store_price_max.setText("");
            reversion_min = 0;
            reversion_max = 10;
            inclusion_min = 0;
            inclusion_max = 10;
            break_min = 21;
            break_max = 33;
            length_uniformity_min = 73;
            length_uniformity_max = 100;
            refresh = true;
            if(!refresh) {
                reversion_min = (int) seekbar_reversion.getMin();
                reversion_max = (int) seekbar_reversion.getMax();
                inclusion_min = (int) seekbar_inclusion.getMin();
                inclusion_max = (int) seekbar_inclusion.getMax();
                break_min = (int) seekbar_break.getMin();
                break_max = (int) seekbar_break.getMax();
                length_uniformity_min = (int) seekbar_length_uniformity.getMin();
                length_uniformity_max = (int) seekbar_length_uniformity.getMax();
            }
            micronaire_value_min = edit_store_filter_micronaire_value_min.getText().toString().trim();
            micronaire_value_max = edit_store_filter_micronaire_value_max.getText().toString().trim();
            price_min = edit_store_price_min.getText().toString().trim();
            price_max = edit_store_price_max.getText().toString().trim();
            if(!TextUtils.isEmpty(micronaire_value_min) && TextUtils.isEmpty(micronaire_value_max)){
                showToast("最小马克隆值不能大于最大马克隆值");
                return;
            }
            if(!TextUtils.isEmpty(price_min) && TextUtils.isEmpty(price_max)){
                showToast("最小价格不能高于最大价格");
                return;
            }
            if(!TextUtils.isEmpty(micronaire_value_min) && !TextUtils.isEmpty(micronaire_value_max) &&Double.parseDouble(micronaire_value_min) > Double.parseDouble(micronaire_value_max)){
                showToast("最小马克隆值不能大于最大马克隆值");
                return;
            }
            if(!TextUtils.isEmpty(price_min) && !TextUtils.isEmpty(price_max) &&Double.parseDouble(price_min) > Double.parseDouble(price_max)){
                showToast("最小价格不能高于最大价格");
                return;
            }
            refresh = false;
            filterData();
            popupWindow.dismiss();
        });
        TextView tv_confirm = (TextView) contentView.findViewById(R.id.tv_store_filter_confirm);
        tv_confirm.setOnClickListener(v -> {
            if(!refresh) {
                reversion_min = (int) seekbar_reversion.getMin();
                reversion_max = (int) seekbar_reversion.getMax();
                inclusion_min = (int) seekbar_inclusion.getMin();
                inclusion_max = (int) seekbar_inclusion.getMax();
                break_min = (int) seekbar_break.getMin();
                break_max = (int) seekbar_break.getMax();
                length_uniformity_min = (int) seekbar_length_uniformity.getMin();
                length_uniformity_max = (int) seekbar_length_uniformity.getMax();
            }
            micronaire_value_min = edit_store_filter_micronaire_value_min.getText().toString().trim();
            micronaire_value_max = edit_store_filter_micronaire_value_max.getText().toString().trim();
            price_min = edit_store_price_min.getText().toString().trim();
            price_max = edit_store_price_max.getText().toString().trim();
            if(!TextUtils.isEmpty(micronaire_value_min) && TextUtils.isEmpty(micronaire_value_max)){
                showToast("最小马克隆值不能大于最大马克隆值");
                return;
            }
            if(!TextUtils.isEmpty(price_min) && TextUtils.isEmpty(price_max)){
                showToast("最小价格不能高于最大价格");
                return;
            }
            if(!TextUtils.isEmpty(micronaire_value_min) && !TextUtils.isEmpty(micronaire_value_max) &&Double.parseDouble(micronaire_value_min) > Double.parseDouble(micronaire_value_max)){
                showToast("最小马克隆值不能大于最大马克隆值");
                return;
            }
            if(!TextUtils.isEmpty(price_min) && !TextUtils.isEmpty(price_max) &&Double.parseDouble(price_min) > Double.parseDouble(price_max)){
                showToast("最小价格不能高于最大价格");
                return;
            }
            refresh = false;
            filterData();
            popupWindow.dismiss();
        });
    }

    @OnClick({R.id.ll_store_main_length, R.id.ll_store_main_ma, R.id.ll_store_main_strength, R.id.ll_store_main_origin, R.id.ll_store_main_sort, R.id.tv_title_search_button})
    void onClick(View view){
        switch (view.getId()){
            case R.id.ll_store_main_length:
                showWindow(R.id.ll_store_main_head, R.id.ll_store_main_length);
                break;
            case R.id.ll_store_main_ma:
                showWindow(R.id.ll_store_main_head, R.id.ll_store_main_ma);
                break;
            case R.id.ll_store_main_strength:
                showWindow(R.id.ll_store_main_head, R.id.ll_store_main_strength);
                break;
            case R.id.ll_store_main_origin:
                showWindow(R.id.ll_store_main_head, R.id.ll_store_main_origin);
                break;
            case R.id.ll_store_main_sort:
//                showWindow(R.id.ll_store_main_head, R.id.ll_store_main_sort);
                showSortWindow(R.id.ll_store_main_sort);
                break;
            case R.id.tv_title_search_button:
                filterData();
//                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(edit_title_search_middle.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                break;
        }
    }

    private void showSortWindow(int id) {
        final PopupWindow popupWindow = new PopupWindow(getActivity());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_store_sort_popupwindow, null);
        popupWindow.setContentView(contentView);
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        ColorDrawable dw = new ColorDrawable(0xFFFFFF);
        // 设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度-10
//        int xPos = windowManager.getDefaultDisplay().getWidth();
//        popupWindow.showAsDropDown(getActivity().findViewById(id), xPos, 5);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = contentView.getMeasuredWidth();
        int popupHeight = contentView.getMeasuredHeight();
        int[] location = new int[2];
        getActivity().findViewById(id).getLocationOnScreen(location);
//        popupWindow.showAtLocation(getActivity().findViewById(id), Gravity.NO_GRAVITY, (location[0] + getActivity().findViewById(id).getMeasuredWidth() / 2) - popupWidth / 2, location[1] + (popupHeight / 2));
        popupWindow.showAsDropDown(getActivity().findViewById(id));
//        gray_layout.setVisibility(View.VISIBLE);
        popupWindow.setOnDismissListener(() -> {
//            gray_layout.setVisibility(View.GONE);
            filterData();
        });
        contentView.setOnTouchListener((v, event) -> {
            int height = contentView.findViewById(R.id.ll_store_sort_content).getMeasuredHeight();
            int y = (int) event.getY();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (y > height) {
                    popupWindow.dismiss();
                }
            }
            return true;
        });

        //综合排序
        LinearLayout ll_store_sort_multiple = (LinearLayout) contentView.findViewById(R.id.ll_store_sort_multiple);
        ll_store_sort_multiple.setOnClickListener(v -> {
            for (FilterGridviewModel filterGridviewModel : sortList) {
                if(filterGridviewModel.getAreaName().equals("综合排序")){
                    filterGridviewModel.setSelected(true);
                }else{
                    filterGridviewModel.setSelected(false);
                }
            }
            popupWindow.dismiss();
        });
        //价格最低
        LinearLayout ll_store_sort_lowest_price = (LinearLayout) contentView.findViewById(R.id.ll_store_sort_lowest_price);
        ll_store_sort_lowest_price.setOnClickListener(v -> {
            for (FilterGridviewModel filterGridviewModel : sortList) {
                if(filterGridviewModel.getAreaName().equals("价格最低")){
                    filterGridviewModel.setSelected(true);
                }else{
                    filterGridviewModel.setSelected(false);
                }
            }
            popupWindow.dismiss();
        });
        //价格最高
        LinearLayout ll_store_sort_highest_price = (LinearLayout) contentView.findViewById(R.id.ll_store_sort_highest_price);
        ll_store_sort_highest_price.setOnClickListener(v -> {
            for (FilterGridviewModel filterGridviewModel : sortList) {
                if(filterGridviewModel.getAreaName().equals("价格最高")){
                    filterGridviewModel.setSelected(true);
                }else{
                    filterGridviewModel.setSelected(false);
                }
            }
            popupWindow.dismiss();
        });
        //存放仓库
        LinearLayout ll_store_sort_storge = (LinearLayout) contentView.findViewById(R.id.ll_store_sort_storge);
        ll_store_sort_storge.setOnClickListener(v -> {
            for (FilterGridviewModel filterGridviewModel : sortList) {
                if(filterGridviewModel.getAreaName().equals("存放仓库")){
                    filterGridviewModel.setSelected(true);
                }else{
                    filterGridviewModel.setSelected(false);
                }
            }
            popupWindow.dismiss();
        });
        //加工厂
        LinearLayout ll_store_sort_factory = (LinearLayout) contentView.findViewById(R.id.ll_store_sort_factory);
        ll_store_sort_factory.setOnClickListener(v -> {
            for (FilterGridviewModel filterGridviewModel : sortList) {
                if(filterGridviewModel.getAreaName().equals("加工厂")){
                    filterGridviewModel.setSelected(true);
                }else{
                    filterGridviewModel.setSelected(false);
                }
            }
            popupWindow.dismiss();
        });
        //发布时间
        LinearLayout ll_store_sort_publish_time = (LinearLayout) contentView.findViewById(R.id.ll_store_sort_publish_time);
        ll_store_sort_publish_time.setOnClickListener(v -> {
            for (FilterGridviewModel filterGridviewModel : sortList) {
                if(filterGridviewModel.getAreaName().equals("发布时间")){
                    filterGridviewModel.setSelected(true);
                }else{
                    filterGridviewModel.setSelected(false);
                }
            }
            popupWindow.dismiss();
        });
        //供货商
        LinearLayout ll_store_sort_suppiler = (LinearLayout) contentView.findViewById(R.id.ll_store_sort_suppiler);
        ll_store_sort_suppiler.setOnClickListener(v -> {
            for (FilterGridviewModel filterGridviewModel : sortList) {
                if(filterGridviewModel.getAreaName().equals("供货商")){
                    filterGridviewModel.setSelected(true);
                }else{
                    filterGridviewModel.setSelected(false);
                }
            }
            popupWindow.dismiss();
        });
    }

    private void showWindow(int id, int origin) {
        final PopupWindow popupWindow = new PopupWindow(getActivity());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_store_popwindow, null);
        popupWindow.setContentView(contentView);
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        ColorDrawable dw = new ColorDrawable(0xFFFFFF);
        // 设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度-10
//        int xPos = windowManager.getDefaultDisplay().getWidth();
//        popupWindow.showAsDropDown(getActivity().findViewById(id), xPos, 5);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = contentView.getMeasuredWidth();
        int popupHeight = contentView.getMeasuredHeight();
        int[] location = new int[2];
        getActivity().findViewById(id).getLocationOnScreen(location);
//        popupWindow.showAtLocation(getActivity().findViewById(id), Gravity.NO_GRAVITY, (location[0] + getActivity().findViewById(id).getMeasuredWidth() / 2) - popupWidth / 2, location[1] + (popupHeight / 2));
        popupWindow.showAsDropDown(getActivity().findViewById(id));
//        gray_layout.setVisibility(View.VISIBLE);
        popupWindow.setOnDismissListener(() -> {
//            gray_layout.setVisibility(View.GONE);
            filterData();
        });
        contentView.setOnTouchListener((v, event) -> {
            int height = contentView.findViewById(R.id.ll_store_content).getMeasuredHeight();
            int y = (int) event.getY();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (y > height) {
                    popupWindow.dismiss();
                }
            }
            return true;
        });

        TextView tv_reset = (TextView) contentView.findViewById(R.id.tv_store_reset);
        tv_reset.setOnClickListener(v -> {
            //TODO incompelete
            switch (origin){
                case R.id.ll_store_main_length:
                    for (FilterGridviewModel filterGridviewModel : length_list) {
                        filterGridviewModel.setSelected(false);
                        length_adapter.setList(length_list);
                        length_adapter.notifyDataSetInvalidated();
                    }
                    break;
                case R.id.ll_store_main_ma:
                    for (FilterGridviewModel filterGridviewModel : ma_list) {
                        filterGridviewModel.setSelected(false);
                        ma_adapter.setList(ma_list);
                        ma_adapter.notifyDataSetInvalidated();
                    }
                    break;
                case R.id.ll_store_main_strength:
                    for (FilterGridviewModel filterGridviewModel : strength_list) {
                        filterGridviewModel.setSelected(false);
                        strength_adapter.setList(strength_list);
                        strength_adapter.notifyDataSetInvalidated();
                    }
                    break;
                case R.id.ll_store_main_origin:
                    for (FilterGridviewModel filterGridviewModel : origin_list) {
                        filterGridviewModel.setSelected(false);
                        origin_adapter.setList(origin_list);
                        origin_adapter.notifyDataSetInvalidated();
                    }
                    break;
            }
            popupWindow.dismiss();
        });

        TextView tv_confirm = (TextView) contentView.findViewById(R.id.tv_store_confirm);
        tv_confirm.setOnClickListener(v -> {
            //TODO incompelete
            popupWindow.dismiss();
        });
        GridView gv_filter = (GridView) contentView.findViewById(R.id.gv_store_item);
        switch (origin){
            case R.id.ll_store_main_length:
                gv_filter.setAdapter(length_adapter);
                gv_filter.setOnItemClickListener((parent, view, position, id1) -> {
                    for (int i = 0; i < length_list.size(); i++) {
                        if(i == position)
                            {
                            length_list.get(i).setSelected(!length_list.get(i).isSelected());
                            }else{
                                length_list.get(i).setSelected(false);
                            }

                    }
                    length_adapter.setList(length_list);
                    length_adapter.notifyDataSetInvalidated();
                });
                break;
            case R.id.ll_store_main_ma:
                gv_filter.setAdapter(ma_adapter);
                gv_filter.setOnItemClickListener((parent, view, position, id1) -> {
                    for (int i = 0; i < ma_list.size(); i++) {
                        if(i == position)
                        {
                            ma_list.get(i).setSelected(!ma_list.get(i).isSelected());
                        }else{
                            ma_list.get(i).setSelected(false);
                        }

                    }
                    ma_adapter.setList(ma_list);
                    ma_adapter.notifyDataSetInvalidated();
                });
                break;
            case R.id.ll_store_main_strength:
                gv_filter.setAdapter(strength_adapter);
                gv_filter.setOnItemClickListener((parent, view, position, id1) -> {
                    for (int i = 0; i < strength_list.size(); i++) {
                        if(i == position)
                        {
                            strength_list.get(i).setSelected(!strength_list.get(i).isSelected());
                        }else{
                            strength_list.get(i).setSelected(false);
                        }
                    }
                    strength_adapter.setList(strength_list);
                    strength_adapter.notifyDataSetInvalidated();
                });
                break;
            case R.id.ll_store_main_origin:
                gv_filter.setAdapter(origin_adapter);
                gv_filter.setOnItemClickListener((parent, view, position, id1) -> {
                    for (int i = 0; i < origin_list.size(); i++) {
                        if(i == position)
                        {
                            origin_list.get(i).setSelected(!origin_list.get(i).isSelected());
                        }else{
                            origin_list.get(i).setSelected(false);
                        }
                    }
                    origin_adapter.setList(origin_list);
                    origin_adapter.notifyDataSetInvalidated();
                });
                break;
        }
    }

    void filterData(){

        InputMethodUtils.hideSoftInput(getActivity());

        if(listView.getHeaderViewsCount() > 0)
            listView.removeHeaderView(headerView);

        ph = edit_title_search_middle.getText().toString().trim();
        if(!Utils.isNumOnly(ph))
            ph = "";

        length = "";
        for (FilterGridviewModel filterGridviewModel : length_adapter.getList()) {
            if(filterGridviewModel.isSelected())
                length = length + filterGridviewModel.getAreaName() + ",";
        }
        if(length != null && length.length() > 0 && length.substring(length.length() - 1, length.length()).equals(","))
            length = length.substring(0, length.length()-1);

        ma = "";
        for (FilterGridviewModel filterGridviewModel : ma_adapter.getList()) {
            if(filterGridviewModel.isSelected())
                ma = ma + filterGridviewModel.getAreaName() + ",";
        }
        if(ma != null && ma.length() > 0 && ma.substring(ma.length() - 1, ma.length()).equals(","))
            ma = ma.substring(0, ma.length()-1);

        strength = "";
        for (FilterGridviewModel filterGridviewModel : strength_adapter.getList()) {
            if(filterGridviewModel.isSelected())
                strength = strength + filterGridviewModel.getAreaName() + ",";
        }
        if(strength != null && strength.length() > 0 && strength.substring(strength.length() - 1, strength.length()).equals(","))
            strength = strength.substring(0, strength.length()-1);

        origin = "";
        for (FilterGridviewModel filterGridviewModel : origin_adapter.getList()) {
            if(filterGridviewModel.isSelected())
                origin = origin + filterGridviewModel.getAreaName() + ",";
        }
        if(origin != null && origin.length() > 0 && origin.substring(origin.length() - 1, origin.length()).equals(","))
            origin = origin.substring(0, origin.length()-1);

        sort = "";
        for (FilterGridviewModel filterGridviewModel : sortList) {
            if(filterGridviewModel.isSelected()){
                if(filterGridviewModel.getAreaName().equals("综合排序")){
                    sort = "0";
                }
                if(filterGridviewModel.getAreaName().equals("价格最低")){
                    sort = "1";
                }
                if(filterGridviewModel.getAreaName().equals("价格最高")){
                    sort = "2";
                }
                if(filterGridviewModel.getAreaName().equals("存放仓库")){
                    sort = "3";
                }
                if(filterGridviewModel.getAreaName().equals("加工厂")){
                    sort = "4";
                }
                if(filterGridviewModel.getAreaName().equals("发布时间")){
                    sort = "5";
                }
                if(filterGridviewModel.getAreaName().equals("供货商")){
                    sort = "6";
                }
            }
        }

        pickType = "";
        type = "";
        for (int i = 0; i < typeList.size(); i++) {
            if(i < 2 && typeList.get(i).isSelected()) {
                if(typeList.get(i).getAreaName().equals("手摘棉"))
                    pickType = pickType + "1" + ",";
                if(typeList.get(i).getAreaName().equals("机采棉"))
                    pickType = pickType + "2" + ",";
            }
            if(i >= 2 && typeList.get(i).isSelected())
                type = type + typeList.get(i).getAreaName() + ",";
        }
        if(pickType != null && pickType.length() > 0 && pickType.substring(pickType.length() - 1, pickType.length()).equals(","))
            pickType = pickType.substring(0, pickType.length()-1);
        if(type != null && type.length() > 0 && type.substring(type.length() - 1, type.length()).equals(","))
            type = type.substring(0, type.length()-1);

        year = "";
        for (FilterGridviewModel filterGridviewModel : yearList) {
            if(filterGridviewModel.isSelected())
                year = year + filterGridviewModel.getAreaName() + ",";
        }
        if(year != null && year.length() > 0 && year.substring(year.length() - 1, year.length()).equals(","))
            year = year.substring(0, year.length()-1);

        color = "";
        for (FilterGridviewModel filterGridviewModel : colorLevelList) {
            if(filterGridviewModel.isSelected()) {
                if(filterGridviewModel.getAreaName().equals("无主体")){
                    color = color + "0,";
                } else {
                    color = color + filterGridviewModel.getAreaName() + ",";
                }
            }
        }
        if(color != null && color.length() > 0 && color.substring(color.length() - 1, color.length()).equals(","))
            color = color.substring(0, color.length()-1);

        adapter = new MainStoreAdapter();
        listView.setAdapter(adapter);

        NestRefreshManager<MainStoreModel> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, (page, perPage) -> {
            StoreService api = ApiUtil.createDefaultApi(StoreService.class);
            if(flag == 1){
                return api.list(ph, "", "", length, ma, strength, origin, type, pickType, year, color, reversion_min + "", reversion_max + "", inclusion_min + "", inclusion_max + "", break_min + "", break_max + "", length_uniformity_min + "", length_uniformity_max + "", micronaire_value_min, micronaire_value_max, price_min, price_max, sort, lng + "", lat + "", "" + (page + 1), "" + perPage).map(new HttpResultFunc<>());
            } else {
                return api.list(ph, "", "1", length, ma, strength, origin, type, pickType, year, color, reversion_min + "", reversion_max + "", inclusion_min + "", inclusion_max + "", break_min + "", break_max + "", length_uniformity_min + "", length_uniformity_max + "", micronaire_value_min, micronaire_value_max, price_min, price_max, sort, lng + "", lat + "", "" + (page + 1), "" + perPage).map(new HttpResultFunc<>());
            }
        });
        nestRefreshManager.setPullLoadEnable(true);
        nestRefreshManager.setHttpError(e -> Log.i("test", e.toString()));
        nestRefreshManager.setCallBack((allList, currentList) -> {
            adapter.clear();
            adapter.addList(allList);
            adapter.notifyDataSetChanged();
//            if(listView.getHeaderViewsCount() == 0)
//                showTip(allList.size());
        });
        nestRefreshManager.doApi();

        listView.setOnItemClickListener((parent, view, position, id) -> {
//            if(UserManager.getInstance().isLogin()) {
                Intent intent = new Intent(this, RedPaperCottonDetailActivity.class);
                intent.putExtra("data", adapter.getList().get(position - listView.getHeaderViewsCount()));
                startActivity(intent);
//            }else{
//                isLogin();
//            }
        });
    }

    private void showTip(int size) {
        headerView = View.inflate(getActivity(), R.layout.layout_load_tip, null);
        TextView tv_layout_tip = (TextView) headerView.findViewById(R.id.tv_layout_tip);
        tv_layout_tip.setText("找到" + size +"条相关批次");
        listView.addHeaderView(headerView);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(listView.getHeaderViewsCount() > 0)
                    getActivity().runOnUiThread(() -> listView.removeHeaderView(headerView));
            }
        };
        timer.schedule(timerTask, 5000);
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

}
