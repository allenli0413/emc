package com.emiancang.emiancang.http;

public class HttpResult<T> {
    private String page;
    private String resultCode;
    private String resultMsg;
    private T resultInfo;
    private T resultInfos;

    public T getResultInfos() {
        return resultInfos;
    }

    public void setResultInfos(T resultInfos) {
        this.resultInfos = resultInfos;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
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

    public T getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(T resultInfo) {
        this.resultInfo = resultInfo;
    }

}