package com.emiancang.emiancang.store.model;

import java.io.Serializable;

/**
 * Created by yuanyueqing on 2016/11/14.
 */

public class MainStoreModel implements Serializable {
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

     publishDate                              //发布时间
     ztYsj:     		淡黄染棉1级				  // 主体颜色级
     ztYsj2:		31:0.54%,12:95.16%,21:4.3%  //颜色级别
     [原本的颜色级分为两个字段]  hyJysjCdz               				  //长度
     ql：  27.3                               //强力
     mklz:  4.5                               //马值
     hyJysjHcl:  4.5                           //回潮
     hyJysjHzl:  4.5                           //含杂
     hyJysjDlbqd:  4.5                         //断裂比强度
     hyJysjCd:  安徽棉                   	  //产地
     mhlx:[增加棉花类型]					1手摘棉 ，2机采棉// 棉花类型
     custName                                //供货商
     productPrice:                             //仓库自提价=报价？ 没有
     hyCkbmc                                //存放仓库
     eSpbsSfhbm[增加字段]：0：否，1：是				  // 是否红包棉
     eSpbsHbje[修改字段名称]:                              //红包金额
     distance：2km                            //与我的距离
     isOnlineTrade:                         // 可在线交易0：否，1：是
     }…
     ]
     }
     */

    String gcmgyMhph;
    String hyJysjZbs;
    String publishDate;
    String ztYsj;
    String ztYsj2;
    String hyJysjCdz;
    String ql;
    String mklz;
    String hyJysjHcl;
    String hyJysjHzl;
    String hyJysjDlbqd;
    String hyJysjCd;
    String mhlx;
    String custName;
    String productId;
    String productPrice;
    String hyCkbmc;
    String eSpbsSfhbm;
    String eSpbsHbje;
    String distance;
    String isOnlineTrade;
    String custId;
    boolean selected;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String geteSpbsHbje() {
        return eSpbsHbje;
    }

    public void seteSpbsHbje(String eSpbsHbje) {
        this.eSpbsHbje = eSpbsHbje;
    }

    public String geteSpbsSfhbm() {
        return eSpbsSfhbm;
    }

    public void seteSpbsSfhbm(String eSpbsSfhbm) {
        this.eSpbsSfhbm = eSpbsSfhbm;
    }

    public String getGcmgyMhph() {
        return gcmgyMhph;
    }

    public void setGcmgyMhph(String gcmgyMhph) {
        this.gcmgyMhph = gcmgyMhph;
    }

    public String getHyCkbmc() {
        return hyCkbmc;
    }

    public void setHyCkbmc(String hyCkbmc) {
        this.hyCkbmc = hyCkbmc;
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

    public String getHyJysjZbs() {
        return hyJysjZbs;
    }

    public void setHyJysjZbs(String hyJysjZbs) {
        this.hyJysjZbs = hyJysjZbs;
    }

    public String getMhlx() {
        return mhlx;
    }

    public void setMhlx(String mhlx) {
        this.mhlx = mhlx;
    }

    public String getMklz() {
        return mklz;
    }

    public void setMklz(String mklz) {
        this.mklz = mklz;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getQl() {
        return ql;
    }

    public void setQl(String ql) {
        this.ql = ql;
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

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getIsOnlineTrade() {
        return isOnlineTrade;
    }

    public void setIsOnlineTrade(String isOnlineTrade) {
        this.isOnlineTrade = isOnlineTrade;
    }

    public MainStoreModel() {
    }

    public MainStoreModel(String custId, String custName, String distance, String eSpbsHbje, String eSpbsSfhbm, String gcmgyMhph, String hyCkbmc, String hyJysjCd, String hyJysjCdz, String hyJysjDlbqd, String hyJysjHcl, String hyJysjHzl, String hyJysjZbs, String isOnlineTrade, String mhlx, String mklz, String productId, String productPrice, String publishDate, String ql, boolean selected, String ztYsj2, String ztYsj) {
        this.custId = custId;
        this.custName = custName;
        this.distance = distance;
        this.eSpbsHbje = eSpbsHbje;
        this.eSpbsSfhbm = eSpbsSfhbm;
        this.gcmgyMhph = gcmgyMhph;
        this.hyCkbmc = hyCkbmc;
        this.hyJysjCd = hyJysjCd;
        this.hyJysjCdz = hyJysjCdz;
        this.hyJysjDlbqd = hyJysjDlbqd;
        this.hyJysjHcl = hyJysjHcl;
        this.hyJysjHzl = hyJysjHzl;
        this.hyJysjZbs = hyJysjZbs;
        this.isOnlineTrade = isOnlineTrade;
        this.mhlx = mhlx;
        this.mklz = mklz;
        this.productId = productId;
        this.productPrice = productPrice;
        this.publishDate = publishDate;
        this.ql = ql;
        this.selected = selected;
        this.ztYsj2 = ztYsj2;
        this.ztYsj = ztYsj;
    }
}
