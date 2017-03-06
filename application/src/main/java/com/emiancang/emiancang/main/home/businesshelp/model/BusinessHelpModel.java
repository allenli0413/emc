package com.emiancang.emiancang.main.home.businesshelp.model;

import java.io.Serializable;

/**
 * Created by yuanyueqing on 2016/11/8.
 */

public class BusinessHelpModel implements Serializable {
    /**
     * {
     "result_code": 200,
     "result_msg": "OK",
     "result_info": [
     {
     eCgxqId：     	 //需求id
     eCgxqType[参数做调整完善需求类型返回值]：1		//需求类型--0：购买需求，1：销售需求
     eCgxqAppxqxq：     //需求内容
     eCgxqCjsj:       //时间
     eCgxqFkzt:       //状态
     eCgxqCjrName:       发布人姓名
     },

     ]
     }
     */
    private String eCgxqId;
    private String eCgxqType;
    private String eCgxqAppxqxq;
    private String eCgxqCjsj;
    private String eCgxqFkzt;
    private String eCgxqCjrName;

    public String geteCgxqAppxqxq() {
        return eCgxqAppxqxq;
    }

    public void seteCgxqAppxqxq(String eCgxqAppxqxq) {
        this.eCgxqAppxqxq = eCgxqAppxqxq;
    }

    public String geteCgxqCjrName() {
        return eCgxqCjrName;
    }

    public void seteCgxqCjrName(String eCgxqCjrName) {
        this.eCgxqCjrName = eCgxqCjrName;
    }

    public String geteCgxqCjsj() {
        return eCgxqCjsj;
    }

    public void seteCgxqCjsj(String eCgxqCjsj) {
        this.eCgxqCjsj = eCgxqCjsj;
    }

    public String geteCgxqFkzt() {
        return eCgxqFkzt;
    }

    public void seteCgxqFkzt(String eCgxqFkzt) {
        this.eCgxqFkzt = eCgxqFkzt;
    }

    public String geteCgxqId() {
        return eCgxqId;
    }

    public void seteCgxqId(String eCgxqId) {
        this.eCgxqId = eCgxqId;
    }

    public String geteCgxqType() {
        return eCgxqType;
    }

    public void seteCgxqType(String eCgxqType) {
        this.eCgxqType = eCgxqType;
    }
}
