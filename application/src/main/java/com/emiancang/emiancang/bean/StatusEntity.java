package com.emiancang.emiancang.bean;

/**
 * Created by yuanyueqing on 2017/1/23.
 */

public class StatusEntity {
    /**
     * 报血警了
     * {"resultMsg":"OK","resultCode":"200","resultInfo":{"msg":"采购商资金账户异常","status":"error"}}
     */
    private String msg;
    private String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
