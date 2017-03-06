package com.emiancang.emiancang.main.home.deliveryorder.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emiancang.emiancang.App;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.main.home.deliveryorder.listener.DeliveryOrderItemListener;
import com.emiancang.emiancang.main.home.deliveryorder.model.DeliveryOrderModel;
import com.emiancang.emiancang.uitl.MyCountDownTimer;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Author: liyh
 * E-mail: liyh@zhiwyl.com
 * Phone: 15033990609
 * Date: 2017/2/23  Time: 11:45
 * Description:
 */

public class DeliveryOrderListAdapter extends BaseAdapter {
    private List<DeliveryOrderModel> data = new ArrayList<>();
    private DeliveryOrderItemListener mListener;
    private MyCountDownTimer mTimer;
    public boolean hasCountDown;
    private long mValue;

    public DeliveryOrderListAdapter() {
    }

    public void clear() {
        data.clear();
    }

    public void addList(List<? extends DeliveryOrderModel> allList) {
        for (DeliveryOrderModel deliveryOrderModel : allList) {
            mValue = SharedPrefsUtil.getValue(App.getContext(), deliveryOrderModel.hth, 0L);
            if (mValue > 0L) {//验证码被点击过
                long remainTime = System.currentTimeMillis() - mValue;
                Logger.i("" + remainTime);
                if (remainTime <= 60000) { //正在倒计时
                    for (DeliveryOrderModel model : allList) {
                        if (deliveryOrderModel.hth.equals(model.hth)) {
                            model.CODE_STATE = 5;
                            deliveryOrderModel.remainTime = remainTime;
                        } else {
                            switch (model.CODE_STATE) {
                                case 1:
                                    model.CODE_STATE = 3;
                                    break;
                                case 2:
                                    model.CODE_STATE = 4;
                                    break;
                            }
                        }
                    }
                } else {//倒计时结束
                    deliveryOrderModel.CODE_STATE = 2;
                }
            }
        }
        data.addAll(allList);
    }

    public void setDeliveryOrderItemOnClickListener(DeliveryOrderItemListener listener) {
        mListener = listener;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public DeliveryOrderModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(App.getContext(), R.layout.item_delivery_order_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DeliveryOrderModel model = getItem(position);
        holder.mTxtAgreementNum.setText(model.hth);
        holder.mTxtAgreementNum.setTag(model);
        holder.mTxtAgreementNum.setOnClickListener(v ->
                mListener.numOnClick(v));

        switch (model.CODE_STATE) {
            case 1: //获取验证码,可点击
                holder.mTxtCode.setText("获取验证码");
                holder.mTxtCode.setBackgroundResource(R.drawable.shape_rectangle_button_green);
                holder.mTxtCode.setClickable(true);
                break;
            case 2: //再次获取验证码,可点击
                holder.mTxtCode.setText("再次获取");
                holder.mTxtCode.setBackgroundResource(R.drawable.shape_rectangle_button_orange);
                holder.mTxtCode.setClickable(true);
                break;
            case 3:  //获取验证码,不可点击
                holder.mTxtCode.setText("获取验证码");
                holder.mTxtCode.setBackgroundResource(R.drawable.shape_rectangle_button_gray);
                holder.mTxtCode.setClickable(false);
                break;
            case 4: ////再次获取验证码,不可可点击
                holder.mTxtCode.setText("再次获取");
                holder.mTxtCode.setBackgroundResource(R.drawable.shape_rectangle_button_gray);
                holder.mTxtCode.setClickable(false);
                break;
            case 5: //倒计时
                if (!hasCountDown) {
                    mTimer = new MyCountDownTimer(model.remainTime, 1000L, holder.mTxtCode);
                    mTimer.setTickTxt("秒后重试");
                    mTimer.setListener(() -> {
                        mListener.finishCountDown(data);
                    });
                    holder.mTxtCode.setBackgroundResource(R.drawable.shape_rectangle_button_gray);
                    holder.mTxtCode.setClickable(false);
                    mTimer.start();
                    hasCountDown = true;
                }
                break;
        }

        holder.mTxtCode.setTag(model);
        holder.mTxtCode.setOnClickListener(v -> {
            if (model.CODE_STATE >= 3) {
                return;
            } else {
                mListener.codeOnClick(v, data, mTimer);
            }
        });

        holder.mImgArrow.setImageResource(model.isOpen ? R.drawable.icon_tihuo_sjt : R.drawable.icon_tihuo_xjt);
        holder.mImgArrow.setTag(model);
        holder.mImgArrow.setOnClickListener(v -> {
            mListener.arrowOnClick(v);
            //            if (!hasCountDown) {
            //            }
        });
        holder.mLlAgreementChild.setVisibility(model.isOpen ? View.VISIBLE : View.GONE);
        String signDate = model.htqyrq;

        setState(holder.mLlContainerQyriq, signDate, holder.mImgArrow1);

        String payDate = model.fkrq;

        setState(holder.mLlContainerFkrq, payDate, holder.mImgArrow2);

        String sfkjckd = model.sfkjckd;

        setLastState(holder.mLlContainerSfkjckd, sfkjckd);

        return convertView;
    }


    private void setState(LinearLayout container, String content, ImageView imgArrow) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View childAt = container.getChildAt(i);
            if (childAt instanceof TextView) {
                TextView tv = (TextView) childAt;
                tv.setTextColor(TextUtils.isEmpty(content) ? 0xffcccccc : 0xff333333);
                if (i == 1) {
                    tv.setText(getNewDate(content));
                }
            }
        }
        imgArrow.setImageResource(TextUtils.isEmpty(content) ? R.drawable.icon_tihuo_jtwwc : R.drawable.icon_tihuo_jtwc);
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */

    public String getNewDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String newDate = sdf.format(date);
        return newDate;
    }

    private void setLastState(LinearLayout container, String content) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View childAt = container.getChildAt(i);
            if (childAt instanceof TextView) {
                TextView tv = (TextView) childAt;
                tv.setTextColor(TextUtils.equals(content, "已开提货单") ? 0xff333333 : 0xffcccccc);
                if (i == 1) {
                    tv.setText(content);
                }
            }
        }
    }


    static class ViewHolder {
        @InjectView(R.id.txt_agreement)
        TextView mTxtAgreement;
        @InjectView(R.id.txt_agreement_num)
        TextView mTxtAgreementNum;
        @InjectView(R.id.txt_code)
        TextView mTxtCode;
        @InjectView(R.id.img_arrow)
        ImageView mImgArrow;
        @InjectView(R.id.txt_date)
        TextView mTxtDate;
        @InjectView(R.id.llContainerQyriq)
        LinearLayout mLlContainerQyriq;
        @InjectView(R.id.img_arrow1)
        ImageView mImgArrow1;
        @InjectView(R.id.txt_pay_date)
        TextView mTxtPayDate;
        @InjectView(R.id.llContainerFkrq)
        LinearLayout mLlContainerFkrq;
        @InjectView(R.id.img_arrow2)
        ImageView mImgArrow2;
        @InjectView(R.id.txt_is_delivery)
        TextView mTxtIsDelivery;
        @InjectView(R.id.llContainerSfkjckd)
        LinearLayout mLlContainerSfkjckd;
        @InjectView(R.id.ll_agreement_child)
        LinearLayout mLlAgreementChild;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
