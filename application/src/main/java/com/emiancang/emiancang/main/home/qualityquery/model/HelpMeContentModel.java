package com.emiancang.emiancang.main.home.qualityquery.model;

import java.io.Serializable;

/**
 * Created by yuanyueqing on 2016/12/19.
 */

public class HelpMeContentModel implements Serializable {
    /**
     * {
     "result_code": 200,
     "result_msg": "OK",
     "result_info": {
     eZSid:		    //帮我找id
     eZSinfo:        //发布的内容
     eZScreateTime： //发布的时间
     eZSreplyInfo：  //回复的内容
     eZReplyTime：  //回复的时间
     eZFbrYhtx：http://192.168.4.172:9080/emc//upload/eSjzc/yutx/xjl.jpg//发布人头像
     eZHfrYhtx：	 //回复人头像
     }
     */
    String eZSid;
    String eZSinfo;
    String eZScreateTime;
    String eZSreplyInfo;
    String eZReplyTime;
    String eZFbrYhtx;
    String eZHfrYhtx;

    public String geteZFbrYhtx() {
        return eZFbrYhtx;
    }

    public void seteZFbrYhtx(String eZFbrYhtx) {
        this.eZFbrYhtx = eZFbrYhtx;
    }

    public String geteZHfrYhtx() {
        return eZHfrYhtx;
    }

    public void seteZHfrYhtx(String eZHfrYhtx) {
        this.eZHfrYhtx = eZHfrYhtx;
    }

    public String geteZReplyTime() {
        return eZReplyTime;
    }

    public void seteZReplyTime(String eZReplyTime) {
        this.eZReplyTime = eZReplyTime;
    }

    public String geteZScreateTime() {
        return eZScreateTime;
    }

    public void seteZScreateTime(String eZScreateTime) {
        this.eZScreateTime = eZScreateTime;
    }

    public String geteZSid() {
        return eZSid;
    }

    public void seteZSid(String eZSid) {
        this.eZSid = eZSid;
    }

    public String geteZSinfo() {
        return eZSinfo;
    }

    public void seteZSinfo(String eZSinfo) {
        this.eZSinfo = eZSinfo;
    }

    public String geteZSreplyInfo() {
        return eZSreplyInfo;
    }

    public void seteZSreplyInfo(String eZSreplyInfo) {
        this.eZSreplyInfo = eZSreplyInfo;
    }
}
