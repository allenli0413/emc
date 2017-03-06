package com.emiancang.emiancang.main.home.deliveryorder.model;

import java.io.Serializable;

/**
 * Author: liyh
 * E-mail: liyh@zhiwyl.com
 * Phone: 15033990609
 * Date: 2017/2/23  Time: 15:05
 * Description:
 */

public class DeliveryOrderModel implements Serializable {
    /*  {
    "resultMsg": "ok",
    "resultCode": "200",
    "resultInfos": [
        {
            "hth": "CT201607060002",   //合同号
            "sfkjckd": "已开提货单",   //是否开具出库单
            "fkrq": "20160713",        //付款日期
            "sfkyfsyzm": "否",         //是否可以发送验证码
            "htqyrq": "20160708"       //签约日期
        },
            ]
        }*/
    public String hth;          //合同号
    public String sfkjckd;      //是否开具出库单
    public String fkrq;         //付款日期
    public String sfkyfsyzm;    //是否可以发送验证码
    public String htqyrq;       //签约日期


    public boolean isOpen;              //是否打开折叠
    public int CODE_STATE ;             //1,获取验证码  2,再次获取验证码, 3,不能获取验证码 4,倒计时显示
    public long remainTime;              //剩余倒计时的毫秒值
}
