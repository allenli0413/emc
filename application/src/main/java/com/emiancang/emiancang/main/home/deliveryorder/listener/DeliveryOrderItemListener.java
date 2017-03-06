package com.emiancang.emiancang.main.home.deliveryorder.listener;

import android.view.View;

import com.emiancang.emiancang.main.home.deliveryorder.model.DeliveryOrderModel;
import com.emiancang.emiancang.uitl.MyCountDownTimer;

import java.util.List;

/**
 * Author: liyh
 * E-mail: liyh@zhiwyl.com
 * Phone: 15033990609
 * Date: 2017/2/27  Time: 14:13
 * Description:
 */

public interface DeliveryOrderItemListener {
    void numOnClick(View v);
    void codeOnClick(View v, List<DeliveryOrderModel> data, MyCountDownTimer timer);
    void arrowOnClick(View v);
    void finishCountDown(List<DeliveryOrderModel> data);
}
