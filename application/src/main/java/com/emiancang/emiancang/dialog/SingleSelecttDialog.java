package com.emiancang.emiancang.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.litesuits.common.utils.ObjectUtils;
import com.zwyl.ZwyOSInfo;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.uitl.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 单个wheel选择对话框
 * @date 2014/12/23 12:01
 */
public class SingleSelecttDialog<T> extends BaseDialog {

    private Activity mActivity;
    private WheelView mWheel;

    ArrayList<T> mDataList = new ArrayList<T>();
    private Adapter<T> mDataAdapter;
    private View mBtnCancel;
    private View mBtnConfirm;
    private TextView mDialogTitle;

    public SingleSelecttDialog(Activity activity, ArrayList<T> mDataList) {
        this(activity, mDataList, null);
    }

    public SingleSelecttDialog(Activity activity, ArrayList<T> mDataList, String defult) {
        super(activity, R.style.dialog);

        this.mActivity = activity;
        if (mDataList != null) {
            this.mDataList.addAll(mDataList);
        }
        setContentView(R.layout.zwyl_base_dialog);
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth());
        getWindow().setAttributes(p);
        getWindow().setGravity(Gravity.BOTTOM);

        onCreateView();

        mBtnCancel = findViewById(R.id.btn_cancel);
        mBtnConfirm = findViewById(R.id.btn_confirm);
        mBtnCancel.setOnClickListener(defaultListener);
        mBtnConfirm.setOnClickListener(defaultListener);

        mDialogTitle = (TextView) findViewById(R.id.txt_dialog_title);
        mDialogTitle.setText(ObjectUtils.nullStrToEmpty(defult));
    }


    View.OnClickListener defaultListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    public void setOnCancelListener(View.OnClickListener listener) {
        mBtnCancel.setOnClickListener(listener);
    }

    public void setOnConfirmListener(View.OnClickListener listener) {
        mBtnConfirm.setOnClickListener(listener);
    }


    public void onCreateView() {
        mWheel = (WheelView) findViewById(R.id.wheel_view);
        setWheelViewStyle(mWheel, 3);
        mDataAdapter = new Adapter<T>(mActivity, "");
        mDataAdapter.addList(mDataList);
        mWheel.setViewAdapter(mDataAdapter);
        mWheel.addChangingListener(mListener);

    }

    OnWheelChangedListener mListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            mWheel.invalidateWheel(true);
        }
    };

    private void setWheelViewStyle(WheelView wheelView, int count) {

        wheelView.setWheelBackground(R.drawable.wheel_bg_holo);
        wheelView.setWheelForeground(R.drawable.wheel_val_holo);
        wheelView.setShadowColor(
                mActivity.getResources().getColor(R.color.transparent),
                mActivity.getResources().getColor(R.color.transparent),
                mActivity.getResources().getColor(R.color.transparent));
        wheelView.setVisibleItems(count);
    }


    /**
     * Adapter for countries
     */
    private class Adapter<T> extends AbstractWheelTextAdapter {
        List<T> mList = new ArrayList<T>();
        private String tag;

        public void addList(List<T> dataList) {
            if (dataList != null) {
                this.mList.clear();
                this.mList.addAll(dataList);
            }
        }

        /**
         * Constructor
         */
        protected Adapter(Context context, String tag) {
            super(context, R.layout.zwyl_base_time_item_view, NO_RESOURCE);
            this.tag = tag;
            setItemTextResource(R.id.item_text);
            setTextColor(mActivity.getResources().getColor(
                    R.color.black));
            setUnSelectTextColor(mActivity.getResources().getColor(
                    R.color.black));
        }

        @Override
        public void unSelectTextView(TextView textView) {
            if (ZwyOSInfo.getPhoneWidth() > 1080) {
                textView.setTextSize(DensityUtil.px2dip(textView.getContext(),
                        textView.getContext().getResources().getDimension(R.dimen.T2)));
            } else {
                textView.setTextSize(DensityUtil.px2dip(textView.getContext(),
                        textView.getContext().getResources().getDimension(R.dimen.T2)));
            }
        }

        @Override
        public void SelectTextView(TextView textView) {
            if (ZwyOSInfo.getPhoneWidth() > 1080) {
                textView.setTextSize(DensityUtil.px2dip(textView.getContext(),
                        textView.getContext().getResources().getDimension(R.dimen.T1)));
            } else {
                textView.setTextSize(DensityUtil.px2dip(textView.getContext(),
                        textView.getContext().getResources().getDimension(R.dimen.T1)));
            }
        }


        public T getItem(int index) {
            return mList.get(index);
        }

        @Override
        public int getItemsCount() {
            return mList.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return mList.get(index) + tag;
        }

        @Override
        public void notifyDataChangedEvent() {
            super.notifyDataChangedEvent();
        }
    }

    public T getSelectData() {
        return mDataAdapter.getItem(mWheel.getCurrentItem());
    }

    public int getPosition() {
        return mWheel.getCurrentItem();
    }

}
