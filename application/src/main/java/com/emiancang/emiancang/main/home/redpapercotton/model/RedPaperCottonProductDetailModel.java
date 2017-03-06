package com.emiancang.emiancang.main.home.redpapercotton.model;

/**
 * Created by yuanyueqing on 2017/1/11.
 */

public class RedPaperCottonProductDetailModel {
    /**
     * {
     "resultMsg" : "ok",
     "resultCode" : 200,
     "len" : 0,
     "resultInfo" :
     {

     PRODUCT_PRICE":12.85,"hyCkbWd":"","gcmgyMhph":"66051141021","productId":8024,"jsfs":"0","hyCkbJd":"","hyJysjJyrq":"2014-10-31","hyJysjJgdw":"","hyCkbNm":"1000377","hyJysjJyjgmc":"新疆巴州纤维检验所","hyJysjCkqy":"","hyCkbCkmc":"新疆伊犁州陆德棉麻有限责任公司（二库）"},"page":null,"dataLength":0,"existsData":true,"success":true}
     productId						// 产品id
     gcmgyMhph					// 批号
     productPrice:                   // 仓库自提价
     jsfs:   		                // 计费方式
     hyJysjJyjgmc					// 检验机构名称
     hyJysjJyrq						// 检验日期
     hyJysjJgdw						// 加工单位
     hyCkbCkNm:                   // 仓库代码
     hyCkbCkmc:                   // 仓库名称
     hyCkbJd:				     	// 经度
     hyCkbWd:				     	// 维度
     }

     "page" : null,
     "dataLength" : 0,
     "existsData" : false,
     "success" : true
     */
    private String productId;
    private String gcmgyMhph;
    private String productPrice;
    private String jsfs;
    private String hyJysjJyjgmc;
    private String hyJysjJyrq;
    private String hyJysjJgdw;
    private String hyCkbCkNm;
    private String hyCkbCkmc;
    private String hyCkbJd;
    private String hyCkbWd;

    public String getGcmgyMhph() {
        return gcmgyMhph;
    }

    public void setGcmgyMhph(String gcmgyMhph) {
        this.gcmgyMhph = gcmgyMhph;
    }

    public String getHyCkbCkmc() {
        return hyCkbCkmc;
    }

    public void setHyCkbCkmc(String hyCkbCkmc) {
        this.hyCkbCkmc = hyCkbCkmc;
    }

    public String getHyCkbCkNm() {
        return hyCkbCkNm;
    }

    public void setHyCkbCkNm(String hyCkbCkNm) {
        this.hyCkbCkNm = hyCkbCkNm;
    }

    public String getHyCkbJd() {
        return hyCkbJd;
    }

    public void setHyCkbJd(String hyCkbJd) {
        this.hyCkbJd = hyCkbJd;
    }

    public String getHyCkbWd() {
        return hyCkbWd;
    }

    public void setHyCkbWd(String hyCkbWd) {
        this.hyCkbWd = hyCkbWd;
    }

    public String getHyJysjJgdw() {
        return hyJysjJgdw;
    }

    public void setHyJysjJgdw(String hyJysjJgdw) {
        this.hyJysjJgdw = hyJysjJgdw;
    }

    public String getHyJysjJyjgmc() {
        return hyJysjJyjgmc;
    }

    public void setHyJysjJyjgmc(String hyJysjJyjgmc) {
        this.hyJysjJyjgmc = hyJysjJyjgmc;
    }

    public String getHyJysjJyrq() {
        return hyJysjJyrq;
    }

    public void setHyJysjJyrq(String hyJysjJyrq) {
        this.hyJysjJyrq = hyJysjJyrq;
    }

    public String getJsfs() {
        return jsfs;
    }

    public void setJsfs(String jsfs) {
        this.jsfs = jsfs;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
