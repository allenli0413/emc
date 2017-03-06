package com.emiancang.emiancang.main.home.logisticsquery.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.emiancang.emiancang.uitl.EditInputFilter;
import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.city.CityActivity;
import com.emiancang.emiancang.eventbean.CityTransfer;
import com.emiancang.emiancang.eventbean.DialogTransfer;
import com.emiancang.emiancang.eventbean.LogisticsQueryPublishTransfer;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.main.home.logisticsquery.service.LogisticsRequirementPublishService;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.uitl.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by yuanyueqing on 2016/11/9.
 */

public class LogisticsQueryPublishActivity extends TitleActivity {

    @InjectView(R.id.edit_logistics_query_publish_start_place)
    TextView edit_logistics_query_publish_start_place;
    @InjectView(R.id.edit_logistics_query_publish_end_place)
    TextView edit_logistics_query_publish_end_place;
    @InjectView(R.id.tv_logistics_query_publish_start_time)
    TextView tv_logistics_query_publish_start_time;
    @InjectView(R.id.tv_logistics_query_publish_end_time)
    TextView tv_logistics_query_publish_end_time;
    @InjectView(R.id.edit_logistics_query_publish_single_price)
    TextView edit_logistics_query_publish_single_price;
    @InjectView(R.id.tv_logistics_query_publish_submit)
    TextView tv_logistics_query_publish_submit;
    @InjectView(R.id.edit_logistics_query_publish_start_place_layout)
    LinearLayout editLogisticsQueryPublishStartPlaceLayout;
    @InjectView(R.id.edit_logistics_query_publish_end_place_layout)
    LinearLayout editLogisticsQueryPublishEndPlaceLayout;

    AlertDialog dialog;

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    public AMapLocationClient mlocationClient = null;
    AMapLocation aMapLocation;

    CityTransfer startTransfer;
    CityTransfer endTransfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_query_publish);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
        initHead();
        initView();
        if(Utils.isOpenGPS(getActivity())){
            startLocation();
        }else{
            Utils.showGPSDialog(getActivity());
        }
    }

    private void initView() {
        tv_logistics_query_publish_submit.setEnabled(false);
        InputFilter[] filters = { new EditInputFilter() };
        edit_logistics_query_publish_single_price.setFilters(filters);
        edit_logistics_query_publish_start_place.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(edit_logistics_query_publish_end_place.getText().toString().trim())
                        && !TextUtils.isEmpty(tv_logistics_query_publish_start_time.getText().toString().trim())
                        && !TextUtils.isEmpty(tv_logistics_query_publish_end_time.getText().toString().trim())
                        && !TextUtils.isEmpty(edit_logistics_query_publish_single_price.getText().toString().trim()))
                    tv_logistics_query_publish_submit.setEnabled(true);
                else
                    tv_logistics_query_publish_submit.setEnabled(false);
            }
        });
        edit_logistics_query_publish_end_place.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(edit_logistics_query_publish_end_place.getText().toString().trim())
                        && !TextUtils.isEmpty(tv_logistics_query_publish_start_time.getText().toString().trim())
                        && !TextUtils.isEmpty(tv_logistics_query_publish_end_time.getText().toString().trim())
                        && !TextUtils.isEmpty(edit_logistics_query_publish_single_price.getText().toString().trim()))
                    tv_logistics_query_publish_submit.setEnabled(true);
                else
                    tv_logistics_query_publish_submit.setEnabled(false);
            }
        });
        tv_logistics_query_publish_start_time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(edit_logistics_query_publish_end_place.getText().toString().trim())
                        && !TextUtils.isEmpty(tv_logistics_query_publish_start_time.getText().toString().trim())
                        && !TextUtils.isEmpty(tv_logistics_query_publish_end_time.getText().toString().trim())
                        && !TextUtils.isEmpty(edit_logistics_query_publish_single_price.getText().toString().trim()))
                    tv_logistics_query_publish_submit.setEnabled(true);
                else
                    tv_logistics_query_publish_submit.setEnabled(false);
            }
        });
        tv_logistics_query_publish_end_time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(edit_logistics_query_publish_end_place.getText().toString().trim())
                        && !TextUtils.isEmpty(tv_logistics_query_publish_start_time.getText().toString().trim())
                        && !TextUtils.isEmpty(tv_logistics_query_publish_end_time.getText().toString().trim())
                        && !TextUtils.isEmpty(edit_logistics_query_publish_single_price.getText().toString().trim()))
                    tv_logistics_query_publish_submit.setEnabled(true);
                else
                    tv_logistics_query_publish_submit.setEnabled(false);
            }
        });
        edit_logistics_query_publish_single_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(edit_logistics_query_publish_end_place.getText().toString().trim())
                        && !TextUtils.isEmpty(tv_logistics_query_publish_start_time.getText().toString().trim())
                        && !TextUtils.isEmpty(tv_logistics_query_publish_end_time.getText().toString().trim())
                        && !TextUtils.isEmpty(edit_logistics_query_publish_single_price.getText().toString().trim()))
                    tv_logistics_query_publish_submit.setEnabled(true);
                else
                    tv_logistics_query_publish_submit.setEnabled(false);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive())
            ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("发布物流需求", getResources().getColor(R.color.white));
        getHeadBar().showLeftImage();
        getHeadBar().setLeftOnClickListner(v -> finish());
    }

    @OnClick({R.id.tv_logistics_query_publish_start_time, R.id.tv_logistics_query_publish_end_time, R.id.tv_logistics_query_publish_submit,R.id.edit_logistics_query_publish_start_place_layout,R.id.edit_logistics_query_publish_end_place_layout})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_logistics_query_publish_start_time:
                showWindowPacketDate();
                break;
            case R.id.tv_logistics_query_publish_end_time:
                showWindowPacketDate();
                break;
            case R.id.tv_logistics_query_publish_submit:
                submit();
                break;
            case R.id.edit_logistics_query_publish_start_place_layout:
                Intent start = createIntent(CityActivity.class);
                start.putExtra("type",2);
                startActivity(start);
                break;
            case R.id.edit_logistics_query_publish_end_place_layout:
                Intent end = createIntent(CityActivity.class);
                end.putExtra("type",3);
                startActivity(end);
                break;
        }
    }

    private void submit() {
        String startAddr = edit_logistics_query_publish_start_place.getText().toString().trim();
        String endAddr = edit_logistics_query_publish_end_place.getText().toString().trim();
        String price = edit_logistics_query_publish_single_price.getText().toString().trim();
        String startTime = tv_logistics_query_publish_start_time.getText().toString().trim();
        String endTime = tv_logistics_query_publish_end_time.getText().toString().trim();
//        showToast(startAddr + "..." + endAddr + "..." + price + "..." + startTime + "..." + endTime);
        if(endAddr.equals("")){
            showToast("请输入到达地点");
            endAddr = "";
            return;
        }
        if(startTime.equals("")){
            showToast("请选择起运日期");
            return;
        }
        if(endTime.equals("")){
            showToast("请选择到达日期");
            return;
        }
        if(price.equals("")){
            showToast("请输入运输单价");
            return;
        }
        if(startAddr.equals("")){
            if(aMapLocation != null) {
                submit(endTransfer.getId(), aMapLocation.getProvince(), aMapLocation.getCity(), aMapLocation.getDistrict(), price, startTime, endTime);
                return;
            }else{
                showToast("定位失败,请选择起运地点");
                return;
            }
        }
        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        LogisticsRequirementPublishService api = ApiUtil.createDefaultApi(LogisticsRequirementPublishService.class);
        String startProvince = "";
        String startCity = "";
        String startDistrict = "";
        String endProvince = "";
        String endCity = "";
        String endDistrict = "";
        ApiUtil.doDefaultApi(api.list(startTransfer.getId(), endTransfer.getId(), startProvince, endProvince, startCity, endCity, startDistrict, endDistrict, price, price, startTime, endTime), data -> {
            EventBus.getDefault().post(new LogisticsQueryPublishTransfer());
            showToast("发布成功");
            finish();
        }, viewControl);
    }

    private void submit(String endId, String province, String city, String district, String price, String startTime, String endTime) {
        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        LogisticsRequirementPublishService api = ApiUtil.createDefaultApi(LogisticsRequirementPublishService.class);
        ApiUtil.doDefaultApi(api.list("", endId, province, "", city, "", district, "", price, price, startTime, endTime), data -> {
            EventBus.getDefault().post(new LogisticsQueryPublishTransfer());
            showToast("发布成功");
            finish();
        }, viewControl);
    }

    private void startLocation() {
        mlocationClient = new AMapLocationClient(getActivity());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true);
        //设置定位监听
        mlocationClient.setLocationListener(aMapLocation -> {
            mlocationClient.stopLocation();
            this.aMapLocation = aMapLocation;
            if(aMapLocation.getProvince().equals(aMapLocation.getCity()))
                edit_logistics_query_publish_start_place.setHint(aMapLocation.getDistrict());
            else
                edit_logistics_query_publish_start_place.setHint(aMapLocation.getCity());
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

    private void showDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_logitics_publish, null);
        dialog = new AlertDialog.Builder(this).create();
        dialog.setView(view);
        TextView textView = (TextView) view.findViewById(R.id.tv_dialog_logitics_publish);
        textView.setText("发布物流需求之后请稍等待，客服会和你联系");
        dialog.show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
                    EventBus.getDefault().post(new DialogTransfer());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void onEventMainThread(DialogTransfer dialogTransfer) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void onEventMainThread(CityTransfer cityTransfer) {
        switch(cityTransfer.getType()){
            case 2:
                startTransfer = cityTransfer;
                edit_logistics_query_publish_start_place.setText(cityTransfer.getName());
                break;
            case 3:
                endTransfer = cityTransfer;
                edit_logistics_query_publish_end_place.setText(cityTransfer.getName());
                break;
        }
    }

    //显示时间
    private void showWindowPacketDate() {
        PopupWindow popupWindow = new PopupWindow(getActivity());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_popwindow_redpacket_date_logistics, null);
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
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = contentView.getMeasuredWidth();
        int popupHeight = contentView.getMeasuredHeight();
        int[] location = new int[2];
//        getActivity().findViewById(id).getLocationOnScreen(location);
//        popupWindow.showAtLocation(getActivity().findViewById(id), Gravity.NO_GRAVITY, (location[0] + getActivity().findViewById(id).getMeasuredWidth() / 2) - popupWidth / 2, location[1] + (popupHeight / 2));
//        popupWindow.showAsDropDown(getActivity().findViewById(R.id.view_logistics_query_publish_top));
        popupWindow.showAtLocation(getActivity().findViewById(R.id.view_logistics_query_publish_bottom), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//        popupWindow.showAtLocation(findViewById(R.id.view_logistics_query_publish_top), Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);

        ViewHolderData viewHolder = new ViewHolderData(contentView);
        resizePikcer(viewHolder.dpDateStart);
//        setDatePickerDividerColor(viewHolder.dpDateStart);
        resizePikcer(viewHolder.dpDateEnd);
//        setDatePickerDividerColor(viewHolder.dpDateEnd);
        viewHolder.cancel.setOnClickListener(v -> popupWindow.dismiss());
        viewHolder.notarize.setOnClickListener(v -> popupWindow.dismiss());
        viewHolder.notarize.setOnClickListener(v -> {
            if(viewHolder.dpDateStart.getYear() > viewHolder.dpDateEnd.getYear()){
                showToast("起运日期不能晚于到达日期");
                return;
            }
            if(viewHolder.dpDateStart.getMonth() > viewHolder.dpDateEnd.getMonth() && viewHolder.dpDateStart.getYear() >= viewHolder.dpDateEnd.getYear()){
                showToast("起运日期不能晚于到达日期");
                return;
            }
            if(viewHolder.dpDateStart.getDayOfMonth() > viewHolder.dpDateEnd.getDayOfMonth() && viewHolder.dpDateStart.getMonth() >= viewHolder.dpDateEnd.getMonth() && viewHolder.dpDateStart.getYear() >= viewHolder.dpDateEnd.getYear()){
                showToast("起运日期不能晚于到达日期");
                return;
            }
            tv_logistics_query_publish_start_time.setText(viewHolder.dpDateStart.getYear() + "-" + (viewHolder.dpDateStart.getMonth() + 1) + "-" + viewHolder.dpDateStart.getDayOfMonth());
            tv_logistics_query_publish_end_time.setText(viewHolder.dpDateEnd.getYear() + "-" + (viewHolder.dpDateEnd.getMonth() + 1) + "-" + viewHolder.dpDateEnd.getDayOfMonth());
            popupWindow.dismiss();
        });
        if (!tv_logistics_query_publish_start_time.getText().equals("")) {
            String text = tv_logistics_query_publish_start_time.getText().toString().trim();
            int year = Integer.parseInt(text.split("-")[0]);
            int month = Integer.parseInt(text.split("-")[1]) - 1;
            int day = Integer.parseInt(text.split("-")[2]);
            viewHolder.dpDateStart.init(year, month, day, null);
        }
        if (!tv_logistics_query_publish_end_time.getText().equals("")) {
            String text = tv_logistics_query_publish_end_time.getText().toString().trim();
            int year = Integer.parseInt(text.split("-")[0]);
            int month = Integer.parseInt(text.split("-")[1]) - 1;
            int day = Integer.parseInt(text.split("-")[2]);
            viewHolder.dpDateEnd.init(year, month, day, null);
        }
    }


    static class ViewHolderData {
        @InjectView(R.id.dp_date_start)
        DatePicker dpDateStart;
        @InjectView(R.id.dp_date_end)
        DatePicker dpDateEnd;
        @InjectView(R.id.notarize)
        TextView notarize;
        @InjectView(R.id.cancel)
        TextView cancel;

        ViewHolderData(View view) {
            ButterKnife.inject(this, view);
        }
    }

    /**
     * 调整FrameLayout大小
     *
     * @param tp
     */
    private void resizePikcer(FrameLayout tp) {
        List<NumberPicker> npList = findNumberPicker(tp);
        for (NumberPicker np : npList) {
            resizeNumberPicker(np, tp, npList.size());
        }
    }

    /**
     * 得到viewGroup里面的numberpicker组件
     *
     * @param viewGroup
     * @return
     */
    private List<NumberPicker> findNumberPicker(ViewGroup viewGroup) {
        List<NumberPicker> npList = new ArrayList<NumberPicker>();
        View child = null;
        if (null != viewGroup) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                child = viewGroup.getChildAt(i);
                if (child instanceof NumberPicker) {
                    npList.add((NumberPicker) child);
                } else if (child instanceof LinearLayout) {
                    List<NumberPicker> result = findNumberPicker((ViewGroup) child);
                    if (result.size() > 0) {
                        return result;
                    }
                }
            }
        }
        return npList;
    }

    /*
 * 调整numberpicker大小
 */
    private void resizeNumberPicker(NumberPicker np, FrameLayout tp, int size) {
        int width = tp.getMeasuredWidth() / size;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 0, 0, 0);
        np.setLayoutParams(params);
    }

    /**
     * 设置时间选择器的分割线颜色
     *
     * @param datePicker
     */
    private void setDatePickerDividerColor(DatePicker datePicker) {
        // Divider changing:
        // 获取 mSpinners
        LinearLayout llFirst = (LinearLayout) datePicker.getChildAt(0);

        // 获取 NumberPicker
        LinearLayout mSpinners = (LinearLayout) llFirst.getChildAt(0);
        for (int i = 0; i < mSpinners.getChildCount(); i++) {
            NumberPicker picker = (NumberPicker) mSpinners.getChildAt(i);

            Field[] pickerFields = NumberPicker.class.getDeclaredFields();
            for (Field pf : pickerFields) {
                if (pf.getName().equals("mSelectionDivider")) {
                    pf.setAccessible(true);
                    try {
                        pf.set(picker, new ColorDrawable(getResources().getColor(R.color.c1)));
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

}
