package com.emiancang.emiancang.bean;

/**
 * Created by yuanyueqing on 2017/2/14.
 */

public class AppVersionInfo {
    /**
     * {
     "resultMsg" : "ok",
     "resultCode" : 200,
     "len" : 0,
     "resultInfo" :
     {
     appVID						// id
     pname							// 名称
     vname:                        // 版本名称
     vcode:   		                // 版本编号
     content:                        // 内容
     downpath:  http://a/b/c       	// 下载地址
     isCompulsiveUpdate:0:否，1：是// 是否强制更新
     }

     "page" : null,
     "dataLength" : 0,
     "existsData" : false,
     "success" : true
     }
     */
    private String appVID;
    private String pname;
    private String vname;
    private String vcode;
    private String content;
    private String downpath;
    private String isCompulsiveUpdate;

    public String getAppVID() {
        return appVID;
    }

    public void setAppVID(String appVID) {
        this.appVID = appVID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDownpath() {
        return downpath;
    }

    public void setDownpath(String downpath) {
        this.downpath = downpath;
    }

    public String getIsCompulsiveUpdate() {
        return isCompulsiveUpdate;
    }

    public void setIsCompulsiveUpdate(String isCompulsiveUpdate) {
        this.isCompulsiveUpdate = isCompulsiveUpdate;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }
}
