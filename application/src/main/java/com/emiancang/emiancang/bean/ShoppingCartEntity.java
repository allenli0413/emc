package com.emiancang.emiancang.bean;

/**
 * Created by 22310 on 2016/11/28.
 */

public class ShoppingCartEntity {

    /**
     {
     custId：                           //企业id
     "hyJysjDlbqd":"29.61",              //强度
     "eSpbsHbje":"",                     //红包金额
     "distance":0,                       //距离
     "eGwcTsrMc":"",                     //业务员名称
     "publishDate":"2015-12-29 14:19:56",     //发布时间
     "hyJysjCdz":"29.7",                  // 长度
     "hyJysjMklz":"4.5",                  //马值
     "custName":"",                      //供货商
     "eGwcDj":13,                       //单价
     "eGwcId":"2015",                    //购物车id
     "hyJysjYsj":"白棉3级",               //颜色级
     "eGwcTsrDh":"",                     //业务员电话
     "hyCkbmc":"新疆生产建设兵团奎屯储运有限公司",  //存储仓库
     "eGwcJsfs":"0",                       //结算方式 0公重，1毛重
     "eGwcPh":"65626151059"             //批号
     hyJysjZbs：                          //包数
     productId：                           //产品id
     eGwcSftszqy： //是否推送至企业 0未推送；1已推送；2已驳回；3无需推送
     eGwcZt:       //购物车状态 0:未下单 ；1:已下单
     }
     */

    private String custId;
    private String hyJysjDlbqd;
    private String eSpbsHbje;
    private int distance;
    private int productId;
    private String eGwcTsrMc;
    private String publishDate;
    private String hyJysjCdz;
    private String eGwcSftszqy;
    private String hyJysjMklz;
    private String custName;
    private int eGwcDj;
    private String eGwcId;
    private String hyJysjYsj;
    private String eGwcZt;
    private String eGwcTsrDh;
    private String hyCkbmc;
    private String hyJysjZbs;
    private String eGwcJsfs;
    private String eGwcPh;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public int geteGwcDj() {
        return eGwcDj;
    }

    public void seteGwcDj(int eGwcDj) {
        this.eGwcDj = eGwcDj;
    }

    public String geteGwcId() {
        return eGwcId;
    }

    public void seteGwcId(String eGwcId) {
        this.eGwcId = eGwcId;
    }

    public String geteGwcJsfs() {
        return eGwcJsfs;
    }

    public void seteGwcJsfs(String eGwcJsfs) {
        this.eGwcJsfs = eGwcJsfs;
    }

    public String geteGwcPh() {
        return eGwcPh;
    }

    public void seteGwcPh(String eGwcPh) {
        this.eGwcPh = eGwcPh;
    }

    public String geteGwcSftszqy() {
        return eGwcSftszqy;
    }

    public void seteGwcSftszqy(String eGwcSftszqy) {
        this.eGwcSftszqy = eGwcSftszqy;
    }

    public String geteGwcTsrDh() {
        return eGwcTsrDh;
    }

    public void seteGwcTsrDh(String eGwcTsrDh) {
        this.eGwcTsrDh = eGwcTsrDh;
    }

    public String geteGwcTsrMc() {
        return eGwcTsrMc;
    }

    public void seteGwcTsrMc(String eGwcTsrMc) {
        this.eGwcTsrMc = eGwcTsrMc;
    }

    public String geteGwcZt() {
        return eGwcZt;
    }

    public void seteGwcZt(String eGwcZt) {
        this.eGwcZt = eGwcZt;
    }

    public String geteSpbsHbje() {
        return eSpbsHbje;
    }

    public void seteSpbsHbje(String eSpbsHbje) {
        this.eSpbsHbje = eSpbsHbje;
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

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getEGwcTsrMc() {
        return eGwcTsrMc;
    }

    public void setEGwcTsrMc(String eGwcTsrMc) {
        this.eGwcTsrMc = eGwcTsrMc;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getHyJysjCdz() {
        return hyJysjCdz;
    }

    public void setHyJysjCdz(String hyJysjCdz) {
        this.hyJysjCdz = hyJysjCdz;
    }

    public String getEGwcSftszqy() {
        return eGwcSftszqy;
    }

    public void setEGwcSftszqy(String eGwcSftszqy) {
        this.eGwcSftszqy = eGwcSftszqy;
    }

    public String getHyJysjMklz() {
        return hyJysjMklz;
    }

    public void setHyJysjMklz(String hyJysjMklz) {
        this.hyJysjMklz = hyJysjMklz;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public int getEGwcDj() {
        return eGwcDj;
    }

    public void setEGwcDj(int eGwcDj) {
        this.eGwcDj = eGwcDj;
    }

    public String getEGwcId() {
        return eGwcId;
    }

    public void setEGwcId(String eGwcId) {
        this.eGwcId = eGwcId;
    }

    public String getHyJysjYsj() {
        return hyJysjYsj;
    }

    public void setHyJysjYsj(String hyJysjYsj) {
        this.hyJysjYsj = hyJysjYsj;
    }

    public String getEGwcZt() {
        return eGwcZt;
    }

    public void setEGwcZt(String eGwcZt) {
        this.eGwcZt = eGwcZt;
    }

    public String getEGwcTsrDh() {
        return eGwcTsrDh;
    }

    public void setEGwcTsrDh(String eGwcTsrDh) {
        this.eGwcTsrDh = eGwcTsrDh;
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

    public String getEGwcJsfs() {
        return eGwcJsfs;
    }

    public void setEGwcJsfs(String eGwcJsfs) {
        this.eGwcJsfs = eGwcJsfs;
    }

    public String getEGwcPh() {
        return eGwcPh;
    }

    public void setEGwcPh(String eGwcPh) {
        this.eGwcPh = eGwcPh;
    }
}
