package com.emiancang.emiancang.main.home.logisticsquery.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.city.CityActivity;
import com.emiancang.emiancang.eventbean.CityTransfer;
import com.emiancang.emiancang.eventbean.LogisticsQueryFilterTransfer;
import com.emiancang.emiancang.uitl.EditInputFilter;
import com.emiancang.emiancang.uitl.Util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by yuanyueqing on 2017/2/23.
 */

public class LogisticsQueryFilterActivity extends TitleActivity {

    @InjectView(R.id.tv_logistics_filter_window_time_start)
    TextView tv_logistics_filter_window_time_start;
    @InjectView(R.id.tv_logistics_filter_window_time_end)
    TextView tv_logistics_filter_window_time_end;
    @InjectView(R.id.iv_logistics_filter_window_transport_road)
    ImageView iv_logistics_filter_window_transport_road;
    @InjectView(R.id.iv_logistics_filter_window_transport_train)
    ImageView iv_logistics_filter_window_transport_train;
    @InjectView(R.id.edit_logistics_filter_window_price_start)
    EditText edit_logistics_filter_window_price_start;
    @InjectView(R.id.edit_logistics_filter_window_price_end)
    EditText edit_logistics_filter_window_price_end;
    @InjectView(R.id.tv_logistics_filter_window_address_start)
    TextView tv_logistics_filter_window_address_start;
    @InjectView(R.id.tv_logistics_filter_window_address_end)
    TextView tv_logistics_filter_window_address_end;

    boolean road = false;
    boolean train = false;

    String startLocalId = "";
    String startLocalName = "";
    String endLocalId = "";
    String endLocalName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_query_filter);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
        initHead();
        LogisticsQueryFilterTransfer data = (LogisticsQueryFilterTransfer) getIntent().getSerializableExtra("data");
        initData(data);
//        initView();
    }

    private void initData(LogisticsQueryFilterTransfer data) {
        tv_logistics_filter_window_time_start.setText(data.getStartDate());
        tv_logistics_filter_window_time_end.setText(data.getEndDate());
        edit_logistics_filter_window_price_start.setText(data.getLowPrice());
        edit_logistics_filter_window_price_end.setText(data.getHighPrice());
        tv_logistics_filter_window_address_start.setText(data.getStartAddressName());
        tv_logistics_filter_window_address_end.setText(data.getEndAddressName());
        startLocalId = data.getStartAddressId();
        startLocalName = data.getStartAddressName();
        endLocalId = data.getEndAddressId();
        endLocalName = data.getEndAddressName();
        road = data.isRoad();
        train = data.isTrain();
        if(road)
            iv_logistics_filter_window_transport_road.setImageResource(R.drawable.icon_checked);
        else
            iv_logistics_filter_window_transport_road.setImageResource(R.drawable.icon_unchecked);
        if(train)
            iv_logistics_filter_window_transport_train.setImageResource(R.drawable.icon_checked);
        else
            iv_logistics_filter_window_transport_train.setImageResource(R.drawable.icon_unchecked);
    }

    public void onEventMainThread(CityTransfer cityTransfer){
        if(cityTransfer.getType() == 4){
            startLocalId = cityTransfer.getId();
            startLocalName = cityTransfer.getName();
            tv_logistics_filter_window_address_start.setText(startLocalName);
        }
        if(cityTransfer.getType() == 5){
            endLocalId = cityTransfer.getId();
            endLocalName = cityTransfer.getName();
            tv_logistics_filter_window_address_end.setText(endLocalName);
        }
    }

    private void initView() {
        InputFilter[] filters = { new EditInputFilter() };
        edit_logistics_filter_window_price_start.setFilters(filters);
        edit_logistics_filter_window_price_end.setFilters(filters);
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("筛选", getResources().getColor(R.color.white));
        getHeadBar().showLeftText();
        getHeadBar().setLeftTitle("取消", getResources().getColor(R.color.white));
        getHeadBar().setLeftOnClickListner(v -> finish());
        getHeadBar().showRightText();
        getHeadBar().setRightTitle("确认", getResources().getColor(R.color.white));
        getHeadBar().setRightOnClickListner(v -> confirm());
    }

    private void confirm() {
        String startDate = tv_logistics_filter_window_time_start.getText().toString().trim();
        String endDate = tv_logistics_filter_window_time_end.getText().toString().trim();
        String lowPrice = edit_logistics_filter_window_price_start.getText().toString().trim();
        String highPrice = edit_logistics_filter_window_price_end.getText().toString().trim();
        if(TextUtils.isEmpty(lowPrice) && !TextUtils.isEmpty(highPrice)){
            showToast("请输入正确价格区间");
            return;
        }
        if(!TextUtils.isEmpty(lowPrice) && TextUtils.isEmpty(highPrice)){
            showToast("请输入正确价格区间");
            return;
        }
        if(!TextUtils.isEmpty(lowPrice) && !TextUtils.isEmpty(highPrice) && Double.parseDouble(lowPrice) >= Double.parseDouble(highPrice)){
            showToast("请输入正确的价格区间");
            return;
        }
        if(!TextUtils.isEmpty(startLocalName) && TextUtils.isEmpty(endLocalName)){
            showToast("请选择到达地");
            return;
        }
        if(TextUtils.isEmpty(startLocalName) && !TextUtils.isEmpty(endLocalName)){
            showToast("请选择起始地");
            return;
        }
        EventBus.getDefault().post(new LogisticsQueryFilterTransfer(startLocalId, endLocalId, startLocalName, endLocalName, endDate, highPrice, lowPrice, road, startDate, train));
        finish();
    }

    @OnClick({R.id.ll_logistics_filter_window_time_start, R.id.ll_logistics_filter_window_time_end,R.id.ll_logistics_filter_window_transport_road,
            R.id.ll_logistics_filter_window_transport_train,R.id.ll_logistics_filter_window_address_start,R.id.ll_logistics_filter_window_address_end,
            R.id.tv_logistics_query_filter_reset})
    void onClick(View view){
        switch (view.getId()){
            case R.id.ll_logistics_filter_window_time_start:
                showWindowPacketDate();
                break;
            case R.id.ll_logistics_filter_window_time_end:
                showWindowPacketDate();
                break;
            case R.id.ll_logistics_filter_window_transport_road:
                road = !road;
                if(road)
                    iv_logistics_filter_window_transport_road.setImageResource(R.drawable.icon_checked);
                else
                    iv_logistics_filter_window_transport_road.setImageResource(R.drawable.icon_unchecked);
                break;
            case R.id.ll_logistics_filter_window_transport_train:
                train = !train;
                if(train)
                    iv_logistics_filter_window_transport_train.setImageResource(R.drawable.icon_checked);
                else
                    iv_logistics_filter_window_transport_train.setImageResource(R.drawable.icon_unchecked);
                break;
            case R.id.ll_logistics_filter_window_address_start:
//                showLoacationWindow();
                Intent startIntent = createIntent(CityActivity.class);
                startIntent.putExtra("type", 4);
                startActivity(startIntent);
                break;
            case R.id.ll_logistics_filter_window_address_end:
                Intent endIntent = createIntent(CityActivity.class);
                endIntent.putExtra("type", 5);
                startActivity(endIntent);
                break;
            case R.id.tv_logistics_query_filter_reset:
                reset();
                break;
        }
    }

    private void reset() {
        tv_logistics_filter_window_time_start.setText("");
        tv_logistics_filter_window_time_end.setText("");
        edit_logistics_filter_window_price_start.setText("");
        edit_logistics_filter_window_price_end.setText("");
        tv_logistics_filter_window_address_start.setText("");
        tv_logistics_filter_window_address_end.setText("");
        iv_logistics_filter_window_transport_road.setImageResource(R.drawable.icon_unchecked);
        iv_logistics_filter_window_transport_train.setImageResource(R.drawable.icon_unchecked);
        road = false;
        train = false;
        startLocalId = "";
        startLocalName = "";
        endLocalId = "";
        endLocalName = "";
    }

    private void showLoacationWindow() {
        PopupWindow popupWindow = new PopupWindow(getActivity());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_popwindow_logistics_query_filter_location, null);
        popupWindow.setContentView(contentView);
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0xFFFFFF);
        // 设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度-10
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWindow.showAtLocation(getActivity().findViewById(R.id.view_logistics_query_filter_bottom), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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
        ColorDrawable dw = new ColorDrawable(0xFFFFFF);
        // 设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度-10
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWindow.showAtLocation(getActivity().findViewById(R.id.view_logistics_query_filter_bottom), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

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
            tv_logistics_filter_window_time_start.setText(viewHolder.dpDateStart.getYear() + "-" + (viewHolder.dpDateStart.getMonth() + 1) + "-" + viewHolder.dpDateStart.getDayOfMonth());
            tv_logistics_filter_window_time_end.setText(viewHolder.dpDateEnd.getYear() + "-" + (viewHolder.dpDateEnd.getMonth() + 1) + "-" + viewHolder.dpDateEnd.getDayOfMonth());
            popupWindow.dismiss();
        });
        if (!tv_logistics_filter_window_time_start.getText().equals("")) {
            String text = tv_logistics_filter_window_time_start.getText().toString().trim();
            int year = Integer.parseInt(text.split("-")[0]);
            int month = Integer.parseInt(text.split("-")[1]) - 1;
            int day = Integer.parseInt(text.split("-")[2]);
            viewHolder.dpDateStart.init(year, month, day, null);
        }
        if (!tv_logistics_filter_window_time_end.getText().equals("")) {
            String text = tv_logistics_filter_window_time_end.getText().toString().trim();
            int year = Integer.parseInt(text.split("-")[0]);
            int month = Integer.parseInt(text.split("-")[1]) - 1;
            int day = Integer.parseInt(text.split("-")[2]);
            viewHolder.dpDateEnd.init(year, month, day, null);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
