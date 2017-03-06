package com.emiancang.emiancang.main.home.findmoney.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuanyueqing on 2016/11/8.
 */

public class FindMoneyModel implements Serializable {
    /**
     * {
     "resultCode": 200,
     "resultMsg": "OK",
     "resultInfo": [
     {
     eJrsqId：     //资金需求id
     eJrsqJrfwlx:   //需求类型00:质押融资,01:交易配资，02:棉仓白条，03：运费补贴
     eJrsqNr：    //需求内容
     eJrsqSqsj:    //申请时间
     eJrsqFkzt:    //反馈状态

     },

     ]
     }
     */
    private String eJrsqId;
    private String eJrsqJrfwlx;
    private String eJrsqNr;
    private String eJrsqSqsj;
    private String eJrsqFkzt;

    public String geteJrsqFkzt() {
        return eJrsqFkzt;
    }

    public void seteJrsqFkzt(String eJrsqFkzt) {
        this.eJrsqFkzt = eJrsqFkzt;
    }

    public String geteJrsqId() {
        return eJrsqId;
    }

    public void seteJrsqId(String eJrsqId) {
        this.eJrsqId = eJrsqId;
    }

    public String geteJrsqJrfwlx() {
        return eJrsqJrfwlx;
    }

    public void seteJrsqJrfwlx(String eJrsqJrfwlx) {
        this.eJrsqJrfwlx = eJrsqJrfwlx;
    }

    public String geteJrsqNr() {
        return eJrsqNr;
    }

    public void seteJrsqNr(String eJrsqNr) {
        this.eJrsqNr = eJrsqNr;
    }

    public String geteJrsqSqsj() {
        return eJrsqSqsj;
    }

    public void seteJrsqSqsj(String eJrsqSqsj) {
        this.eJrsqSqsj = eJrsqSqsj;
    }

}
