package com.emiancang.emiancang.bean;

/**
 * Created by 22310 on 2016/11/8.
 */

public class CollectCottonEntity {

    /**
     hyGzcpNm：                         //内码
     productId：                          //棉花产品id
     custId:                               //公司id
     hyJysjPh: 25110012309                 //批号
     publishDate                              //发布时间
     hyJysjYsj：淡黄染棉1级                  //颜色级别
     hyJysjCdz:   28毫米                            //长度
     hyJysjDlbqd：  27.3                               //强力
     hyJysjMklz:  4.5                               //马值
     custName                                //供货商
     productPrice:                             //仓库自提价=报价？
     hyCkbmc:                                //存放仓库
     eSpbsHbje:                              //红包金额
     distance：2km                            //与我的距离
     hyJysjZbs:                               //包数
     jsfs:                                    //结算方式 0公重，1毛重
     isOnlineTrade:                         // 可在线交易0：否，1：是
     */

    private String HY_JYSJ_MKLZ;
    private String hyJysjDlbqd;
    private String eSpbsHbje;
    private String distance;
    private String publishDate;
    private String hyGzcpNm;
    private String hyJysjCdz;
    private String custName;
    private String PRODUCT_TYPE;
    private String hyJysjYsj;
    private int ROWNO;
    private String hyCkbmc;
    private String hyJysjZbs;
    private String hyJysjPh;
    private double productPrice;
    private String productId;
    private String custId;
    private String hyJysjMklz;
    private String jsfs;
    private String isOnlineTrade;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String geteSpbsHbje() {
        return eSpbsHbje;
    }

    public void seteSpbsHbje(String eSpbsHbje) {
        this.eSpbsHbje = eSpbsHbje;
    }

    public String getHyJysjMklz() {
        return hyJysjMklz;
    }

    public void setHyJysjMklz(String hyJysjMklz) {
        this.hyJysjMklz = hyJysjMklz;
    }

    public String getIsOnlineTrade() {
        return isOnlineTrade;
    }

    public void setIsOnlineTrade(String isOnlineTrade) {
        this.isOnlineTrade = isOnlineTrade;
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

    public String getHY_JYSJ_MKLZ() {
        return HY_JYSJ_MKLZ;
    }

    public void setHY_JYSJ_MKLZ(String HY_JYSJ_MKLZ) {
        this.HY_JYSJ_MKLZ = HY_JYSJ_MKLZ;
    }

    public String getHyJysjDlbqd() {
        return hyJysjDlbqd;
    }

    public void setHyJysjDlbqd(String hyJysjDlbqd) {
        this.hyJysjDlbqd = hyJysjDlbqd;
    }

    public String getESpbsHbje() {
        return eSpbsHbje;
    }

    public void setESpbsHbje(String eSpbsHbje) {
        this.eSpbsHbje = eSpbsHbje;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getHyGzcpNm() {
        return hyGzcpNm;
    }

    public void setHyGzcpNm(String hyGzcpNm) {
        this.hyGzcpNm = hyGzcpNm;
    }

    public String getHyJysjCdz() {
        return hyJysjCdz;
    }

    public void setHyJysjCdz(String hyJysjCdz) {
        this.hyJysjCdz = hyJysjCdz;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getPRODUCT_TYPE() {
        return PRODUCT_TYPE;
    }

    public void setPRODUCT_TYPE(String PRODUCT_TYPE) {
        this.PRODUCT_TYPE = PRODUCT_TYPE;
    }

    public String getHyJysjYsj() {
        return hyJysjYsj;
    }

    public void setHyJysjYsj(String hyJysjYsj) {
        this.hyJysjYsj = hyJysjYsj;
    }

    public int getROWNO() {
        return ROWNO;
    }

    public void setROWNO(int ROWNO) {
        this.ROWNO = ROWNO;
    }

    public String getHyCkbmc() {
        return hyCkbmc;
    }

    public void setHyCkbmc(String hyCkbmc) {
        this.hyCkbmc = hyCkbmc;
    }

    public String getHyJysjZbs() {
        return hyJysjZbs;
    }

    public void setHyJysjZbs(String hyJysjZbs) {
        this.hyJysjZbs = hyJysjZbs;
    }

    public String getHyJysjPh() {
        return hyJysjPh;
    }

    public void setHyJysjPh(String hyJysjPh) {
        this.hyJysjPh = hyJysjPh;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
