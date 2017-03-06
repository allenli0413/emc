package com.emiancang.emiancang.bean;

import java.io.Serializable;

/**
 * Created by yuanyueqing on 2017/2/21.
 */

public class QueryUser implements Serializable {
    /**
     * {
     "resultMsg" : "ok",
     "resultCode" : 200,
     "len" : 0,
     "resultInfo" :
     {
     eYhtx:							// 用户头像
     eSjzcXm:						// 用户昵称
     }
     "page" : null,
     "dataLength" : 0,
     "existsData" : false,
     "success" : true
     }
     */
    private String eYhtx;
    private String eSjzcXm;

    public String geteSjzcXm() {
        return eSjzcXm;
    }

    public void seteSjzcXm(String eSjzcXm) {
        this.eSjzcXm = eSjzcXm;
    }

    public String geteYhtx() {
        return eYhtx;
    }

    public void seteYhtx(String eYhtx) {
        this.eYhtx = eYhtx;
    }
}
