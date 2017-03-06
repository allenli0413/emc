package com.emiancang.emiancang.main.home.qualityquery.model;

import java.io.Serializable;

/**
 * Created by yuanyueqing on 2016/12/19.
 */

public class QualityQueryModel implements Serializable {
    /**
     * {
     "page": {
     "toalPages": 2,						      //总页数
     "totalCount": 2					      //总条数
     },
     "result_code": 200,
     "result_msg": "OK",
     "result_info": [
     {
     gcmgyMhph : 25110012309                 //批号
     hyJysjZbs: 168                		 	  //包数
     ztYsj:     		淡黄染棉1级				  // 主体颜色级
     ztYsj2:		31:0.54%,12:95.16%,21:4.3%  //颜色级别
     [原本的颜色级分为两个字段]  hyJysjCdz               				  //长度
     mklz:  4.5                               //马值
     hyJysjHcl:  4.5                           //回潮
     hyJysjHzl:  4.5                           //含杂
     hyJysjDlbqd:  4.5                         //断裂比强度
     hyJysjCd:  安徽棉                   	  //产地
     hyJysjJglx:1:锯齿，2：皮辊			 // 加工类型


     }…
     ]
     }
     */
    String gcmgyMhph;
    String hyJysjZbs;
    String ztYsj;
    String ztYsj2;
    String hyJysjCdz;
    String mklz;
    String hyJysjHcl;
    String hyJysjHzl;
    String hyJysjDlbqd;
    String hyJysjCd;
    String hyJysjJglx;
    boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getGcmgyMhph() {
        return gcmgyMhph;
    }

    public void setGcmgyMhph(String gcmgyMhph) {
        this.gcmgyMhph = gcmgyMhph;
    }

    public String getHyJysjCd() {
        return hyJysjCd;
    }

    public void setHyJysjCd(String hyJysjCd) {
        this.hyJysjCd = hyJysjCd;
    }

    public String getHyJysjCdz() {
        return hyJysjCdz;
    }

    public void setHyJysjCdz(String hyJysjCdz) {
        this.hyJysjCdz = hyJysjCdz;
    }

    public String getHyJysjDlbqd() {
        return hyJysjDlbqd;
    }

    public void setHyJysjDlbqd(String hyJysjDlbqd) {
        this.hyJysjDlbqd = hyJysjDlbqd;
    }

    public String getHyJysjHcl() {
        return hyJysjHcl;
    }

    public void setHyJysjHcl(String hyJysjHcl) {
        this.hyJysjHcl = hyJysjHcl;
    }

    public String getHyJysjHzl() {
        return hyJysjHzl;
    }

    public void setHyJysjHzl(String hyJysjHzl) {
        this.hyJysjHzl = hyJysjHzl;
    }

    public String getHyJysjJglx() {
        return hyJysjJglx;
    }

    public void setHyJysjJglx(String hyJysjJglx) {
        this.hyJysjJglx = hyJysjJglx;
    }

    public String getHyJysjZbs() {
        return hyJysjZbs;
    }

    public void setHyJysjZbs(String hyJysjZbs) {
        this.hyJysjZbs = hyJysjZbs;
    }

    public String getMklz() {
        return mklz;
    }

    public void setMklz(String mklz) {
        this.mklz = mklz;
    }

    public String getZtYsj2() {
        return ztYsj2;
    }

    public void setZtYsj2(String ztYsj2) {
        this.ztYsj2 = ztYsj2;
    }

    public String getZtYsj() {
        return ztYsj;
    }

    public void setZtYsj(String ztYsj) {
        this.ztYsj = ztYsj;
    }
}
