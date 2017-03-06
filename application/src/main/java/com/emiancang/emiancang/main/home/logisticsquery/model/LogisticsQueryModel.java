package com.emiancang.emiancang.main.home.logisticsquery.model;

/**
 * Created by yuanyueqing on 2016/11/9.
 */

public class LogisticsQueryModel {
    /**
     * {
     "result_code": 200,
     "result_msg": "OK",
     "result_info": [
     {
     eYhtx:		 // 用户头像
     eSjzcXm:      //姓名
     eSjzcSjh：     //手机号
     statrAddr:      //起运地
     statrAddrName:	 // 起运地名称[这里增加地址名称参数]
     endAddr:       //到达地
     endAddrName:	 // 止运地名称
     servicePrice：   //价格
     startDate：     //运输日期
     endDate：       //到达日期
     },

     ]
     }
     */
    private String eYhtx;
    private String eSjzcXm;
    private String eSjzcSjh;
    private String statrAddr;
    private String statrAddrName;
    private String endAddr;
    private String endAddrName;
    private String servicePrice;
    private String startDate;
    private String endDate;

    public String getEndAddrName() {
        return endAddrName;
    }

    public void setEndAddrName(String endAddrName) {
        this.endAddrName = endAddrName;
    }

    public String geteYhtx() {
        return eYhtx;
    }

    public void seteYhtx(String eYhtx) {
        this.eYhtx = eYhtx;
    }

    public String getStatrAddrName() {
        return statrAddrName;
    }

    public void setStatrAddrName(String statrAddrName) {
        this.statrAddrName = statrAddrName;
    }

    public String getEndAddr() {
        return endAddr;
    }

    public void setEndAddr(String endAddr) {
        this.endAddr = endAddr;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String geteSjzcSjh() {
        return eSjzcSjh;
    }

    public void seteSjzcSjh(String eSjzcSjh) {
        this.eSjzcSjh = eSjzcSjh;
    }

    public String geteSjzcXm() {
        return eSjzcXm;
    }

    public void seteSjzcXm(String eSjzcXm) {
        this.eSjzcXm = eSjzcXm;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStatrAddr() {
        return statrAddr;
    }

    public void setStatrAddr(String statrAddr) {
        this.statrAddr = statrAddr;
    }
}
