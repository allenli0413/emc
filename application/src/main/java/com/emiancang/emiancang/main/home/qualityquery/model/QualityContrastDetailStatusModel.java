package com.emiancang.emiancang.main.home.qualityquery.model;

/**
 * Created by yuanyueqing on 2016/12/6.
 */

public class QualityContrastDetailStatusModel {
    /**
     * {
     "result_code": 200,
     "result_msg": "OK",
     "result_info": {
     sfsc:0：否，1：是			// 是否收藏
     sfgm:0：否，1：是			// 是否购买
     sfbwz:0：否，1：是			// 是否帮我找
     sfsc:0：否，1：是			// 是否收藏
     hyGzcpNm    					// 收藏主键
     eZSid  			  			// 帮我找主键
     }
     */
    private String sfsc;
    private String sfgm;
    private String sfbwz;
    private String hyGzcpNm;
    private String eZSid;

    public String getSfsc() {
        return sfsc;
    }

    public void setSfsc(String sfsc) {
        this.sfsc = sfsc;
    }

    public String geteZSid() {
        return eZSid;
    }

    public void seteZSid(String eZSid) {
        this.eZSid = eZSid;
    }

    public String getHyGzcpNm() {
        return hyGzcpNm;
    }

    public void setHyGzcpNm(String hyGzcpNm) {
        this.hyGzcpNm = hyGzcpNm;
    }

    public String getSfbwz() {
        return sfbwz;
    }

    public void setSfbwz(String sfbwz) {
        this.sfbwz = sfbwz;
    }

    public String getSfgm() {
        return sfgm;
    }

    public void setSfgm(String sfgm) {
        this.sfgm = sfgm;
    }
}
