package com.emiancang.emiancang.my;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.litesuits.android.log.Log;
import com.mayigeek.frame.http.state.HttpError;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.bean.MoneyInfo;
import com.emiancang.emiancang.bean.RedPacketPopupWindowTypeEntity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.my.adapter.AdapterPopupWindowRedpacketType;
import com.emiancang.emiancang.my.fragment.RedPacketFragment;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.Util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import rx.Observable;

/**
 * Created by 22310 on 2016/11/11.
 */

public class MyRedPacketActivity extends TitleActivity {
    @InjectView(R.id.red_packet_type)
    RelativeLayout redPacketType;
    @InjectView(R.id.shopping_cart_order)
    RelativeLayout shoppingCartOrder;
    @InjectView(R.id.red_packet_money)
    TextView redPacketMoney;
    @InjectView(R.id.viewpager)
    FrameLayout viewpager;
    @InjectView(R.id.title_layout)
    LinearLayout titleLayout;
    @InjectView(R.id.red_packet_type_name)
    TextView redPacketTypeName;
    @InjectView(R.id.red_packet_type_iamge)
    ImageView redPacketTypeIamge;
    @InjectView(R.id.red_packet_order_name)
    TextView redPacketOrderName;
    @InjectView(R.id.red_packet_order_iamge)
    ImageView redPacketOrderIamge;
    @InjectView(R.id.iamge_background)
    ImageView iamgeBackground;

    RedPacketFragment mRedPacketFragment;


    private String mTypeSelect = "";
    private String mStartDate = "";
    private String mEndDate = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_red_packet, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();

    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("返现管理", getResources().getColor(R.color.white));
    }

    public void initView() {
        mRedPacketFragment = new RedPacketFragment();
        setDefaultFragment(mRedPacketFragment);
        refresh();
    }

    private void setDefaultFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.viewpager, fragment);
        transaction.commit();
    }

    @OnClick({R.id.red_packet_type, R.id.shopping_cart_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.red_packet_type:
                showWindowPacketType(R.id.title_layout);
                break;
            case R.id.shopping_cart_order:
                showWindowPacketDate(R.id.shopping_cart_order);
                break;
        }
    }


    public void refresh(){
        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        UserService api = ApiUtil.createDefaultApi(UserService.class);
        ApiUtil.doDefaultApi(api.getBackMoneyList(mTypeSelect,mStartDate,mEndDate,"1","10"), new HttpSucess<MoneyInfo>() {
            @Override
            public void onSucess(MoneyInfo moneyInfo) {
                redPacketMoney.setText(moneyInfo.getBackMoney()+"元");
                mRedPacketFragment.setData(moneyInfo.getList());
            }
        }, viewControl);
    }

    //显示时间
    private void showWindowPacketDate(int id) {
        PopupWindow popupWindow = new PopupWindow(getActivity());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_popwindow_redpacket_date, null);
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

        redPacketOrderName.setTextColor(getResources().getColor(R.color.green));
        redPacketOrderIamge.setImageDrawable(getResources().getDrawable(R.drawable.pull_down_up));
        iamgeBackground.setVisibility(View.VISIBLE);

        ViewHolderData viewHolder = new ViewHolderData(contentView);
        resizePikcer(viewHolder.dpDateStart);
        setDatePickerDividerColor(viewHolder.dpDateStart);
        resizePikcer(viewHolder.dpDateEnd);
        setDatePickerDividerColor(viewHolder.dpDateEnd);
        viewHolder.notarize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartDate = viewHolder.dpDateStart.getYear()+"-"+
                        (viewHolder.dpDateStart.getMonth()+1)+"-"+viewHolder.dpDateStart.getDayOfMonth();
                mEndDate = viewHolder.dpDateEnd.getYear()+"-"+
                        (viewHolder.dpDateEnd.getMonth()+1)+"-"+viewHolder.dpDateEnd.getDayOfMonth();
                java.text.DateFormat df=new java.text.SimpleDateFormat("yyyy-MM-dd");
                java.util.Calendar c1=java.util.Calendar.getInstance();
                java.util.Calendar c2=java.util.Calendar.getInstance();
                try
                {
                    c1.setTime(df.parse(mStartDate));
                    c2.setTime(df.parse(mEndDate));
                }catch(java.text.ParseException e){
                    System.err.println("格式不正确");
                }
                int result=c1.compareTo(c2);
                if(result>0){
                    showToast("起始时间不能比终止时间晚，请重新选择");
                    return;
                }
                iamgeBackground.setVisibility(View.GONE);
                popupWindow.dismiss();
                redPacketOrderName.setTextColor(getResources().getColor(R.color.black));
                redPacketOrderIamge.setImageDrawable(getResources().getDrawable(R.drawable.pull_down));

                refresh();
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                redPacketOrderName.setTextColor(getResources().getColor(R.color.black));
                redPacketOrderIamge.setImageDrawable(getResources().getDrawable(R.drawable.pull_down));
                iamgeBackground.setVisibility(View.GONE);
            }
        });
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
//        popupWindow.showAtLocation(getActivity().findViewById(id), Gravity.NO_GRAVITY, (location[0] + getActivity().findViewById(id).getMeasuredWidth() / 2) - popupWidth / 2, location[1] + (popupHeight / 2));
        popupWindow.showAsDropDown(getActivity().findViewById(id));
        popupWindow.setOnDismissListener(() -> {
            redPacketTypeName.setTextColor(getResources().getColor(R.color.black));
            redPacketTypeIamge.setImageDrawable(getResources().getDrawable(R.drawable.pull_down));
            iamgeBackground.setVisibility(View.GONE);
        });
        redPacketTypeName.setTextColor(getResources().getColor(R.color.green));
        redPacketTypeIamge.setImageDrawable(getResources().getDrawable(R.drawable.pull_down_up));
        iamgeBackground.setVisibility(View.VISIBLE);
        ViewHolder viewHolder = new ViewHolder(contentView);
        AdapterPopupWindowRedpacketType mAdapter = new AdapterPopupWindowRedpacketType();
        viewHolder.listView.setAdapter(mAdapter);
        ViewControl viewControl = ViewControlUtil.create2View(viewHolder.nestRefreshLayout,"未找到相关内容","未找到相关内容",R.drawable.hongbaoempty);
        NestRefreshManager<RedPacketPopupWindowTypeEntity> nestRefreshManager = new NestRefreshManager<>(viewHolder.nestRefreshLayout, viewControl, (page, perPage) -> {
            UserService api = ApiUtil.createDefaultApi(UserService.class);
            return api.getDictionaryByCode2("RED_TYPE").map(new HttpResultFunc<>());
        });
        nestRefreshManager.setPullLoadEnable(true);
        nestRefreshManager.setHttpError(e -> Log.i("test", e.toString()));
        nestRefreshManager.setCallBack((allList, currentList) -> {
            mAdapter.clear();
            mAdapter.addList(allList);
            mAdapter.setmTypeSelect(mTypeSelect);
            mAdapter.notifyDataSetChanged();
        });
        viewHolder.listView.setOnItemClickListener((parent, view, position, id1) -> {
            RedPacketPopupWindowTypeEntity entity = mAdapter.getItem(position);
            mTypeSelect = entity.getDicKey();
            popupWindow.dismiss();
            iamgeBackground.setVisibility(View.GONE);
            refresh();
        });
        nestRefreshManager.doApi();
    }

    static class ViewHolder {
        @InjectView(R.id.listView)
        ListView listView;
        @InjectView(R.id.nestRefreshLayout)
        NestRefreshLayout nestRefreshLayout;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    static class ViewHolderData {
        @InjectView(R.id.dp_date_start)
        DatePicker dpDateStart;
        @InjectView(R.id.dp_date_end)
        DatePicker dpDateEnd;
        @InjectView(R.id.notarize)
        TextView notarize;

        ViewHolderData(View view) {
            ButterKnife.inject(this, view);
        }
    }

    /**
     * 调整FrameLayout大小
     * @param tp
     */
    private void resizePikcer(FrameLayout tp){
        List<NumberPicker> npList = findNumberPicker(tp);
        for(NumberPicker np:npList){
            resizeNumberPicker(np, tp, npList.size());
        }
    }

    /**
     * 得到viewGroup里面的numberpicker组件
     * @param viewGroup
     * @return
     */
    private List<NumberPicker> findNumberPicker(ViewGroup viewGroup){
        List<NumberPicker> npList = new ArrayList<NumberPicker>();
        View child = null;
        if(null != viewGroup){
            for(int i = 0;i<viewGroup.getChildCount();i++){
                child = viewGroup.getChildAt(i);
                if(child instanceof NumberPicker){
                    npList.add((NumberPicker)child);
                }
                else if(child instanceof LinearLayout){
                    List<NumberPicker> result = findNumberPicker((ViewGroup)child);
                    if(result.size()>0){
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
    private void resizeNumberPicker(NumberPicker np, FrameLayout tp, int size){
        int width = tp.getMeasuredWidth() / size;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 0, 0, 0);
        np.setLayoutParams(params);
    }

    /**
     *
     * 设置时间选择器的分割线颜色
     * @param datePicker
     */
    private void setDatePickerDividerColor(DatePicker datePicker){
        // Divider changing:
        // 获取 mSpinners
        LinearLayout llFirst       = (LinearLayout) datePicker.getChildAt(0);

        // 获取 NumberPicker
        LinearLayout mSpinners      = (LinearLayout) llFirst.getChildAt(0);
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
