package com.emiancang.emiancang.bean;

/**
 * Created by yuanyueqing on 2016/11/23.
 */

public class PublishEntity {

    String eCgxqId;
    boolean success;
    String resultCode;
    String resultMsg;

    public String geteCgxqId() {
        return eCgxqId;
    }

    public void seteCgxqId(String eCgxqId) {
        this.eCgxqId = eCgxqId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
