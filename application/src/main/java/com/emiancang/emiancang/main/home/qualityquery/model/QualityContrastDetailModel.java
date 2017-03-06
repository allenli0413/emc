package com.emiancang.emiancang.main.home.qualityquery.model;

/**
 * Created by yuanyueqing on 2016/12/1.
 */

public class QualityContrastDetailModel {
    /**
     * {
     "resultCode": 200,
     "resultMsg": "OK",
     "resultInfo": {
     PRODUCT_PRICE：仓库自提价
     HY_JYSJ_JYRQ：检验日期
     HY_JYSJ_JYJGMC：检验机构
     ？？？？？全部检验
     HY_JYSJ_CKQY：仓库企业  //不确定是否是公检仓库
     hy_ckbmc：存储仓库

     hy_jgdw_jgdwmc:加工单位（加工厂）,
     hy_jysj_lx：” 锯齿细绒棉”// 加工类型


     czfs:’手摘棉’//机采棉，采摘方式
     hy_ckbmc:“新疆农资集团北疆农佳乐有限责任公司（原西库）//仓库”

     "hy_jysj_ysj"：主体颜色级
     "hy_jysj_ysj_zj"："白棉3级@73.12%,白棉2级@26.88%"（主题颜色级子集）（不建议用该字段，可以直接用用处理过的）
     "bm1j"："1",//白棉一级%
     "bm21j"："1",//白棉二级%
     "bm3j"："1",//白棉三级%
     "bm4j"："1",//白棉四级%
     "bm5j"："1",//白棉五级 %
     "ddwm1j"："1",//淡点污棉1级%
     "ddwm2j": "1", // 淡点污棉2级%
     ddwm3j: "1", // 淡点污棉3级%
     dhrm1j: "1",//淡黄染棉1级
     dhrm2j: "1",//淡黄染棉2级
     dhrm3j: "1",//淡黄染棉3级 %
     hrm1j: "1",//黄染棉1级%
     hrm2j: "1",//黄染棉2级%
     hy_jysj_cdz:‘1’, // 长度平均值
     cdj25："1",//长度25mm的棉花所占的%
     cdj26："1",//长度26mm的棉花所占的%
     cdj27："1",//长度27mm的棉花所占的%
     cdj28："1",//长度28mm的棉花所占的%
     cdj29："1",//长度29mm的棉花所占的%
     cdj30："1",//长度30mm的棉花所占的%
     cdj31："1",//长度31mm的棉花所占的%
     cdj32："1",//长度32mm的棉花所占的%
     hy_jysj_mkldc_zj :“B3”,//马克隆主体级    
     mklzc1: "1", //马克隆c1占比
     mklb1："1",//马克隆b1占比
     mklza: null, //马克隆a占比
     mklzb2："1"//马克隆b2占比
     mklzc2："1"//马克隆c2占比
     mklzsdt: "[4.4,4.7,4.4,4.4,4.8,4.3,4.3,4.4,4.2,4.7,4.9,4.4,4.3,4.9,4.2,4.3,4.4,4.4,4.3,4.6,4.3,4.4,4.8,4.3,4.9,4.4,4.3,4.3,4.3,4.6,4.6,4.7,4.7,4.6,4.9,4.6,4.4,4.4,4.8,4.4,4.5,4.3,4.3,4.6,4.4,4.9,4.5,4.7,4.5,4.4,4.5,4.6,4.7,4.3,4.8,4.4,4.5,4.6,4.6,4.5,4.6,4.5,4.5,4.4,4.6,4.8,4.3,4.4,4.8,4.5,4.3,4.8,4.3,4.4,4.9,4.4,4.4,4.8,4.6,5.0,4.5,4.9,4.4,4.4,4.4,4.4,4.4,4.7,4.4,4.5,4.4,4.7,4.3,4.7,4.6,4.2,4.4,4.4,4.4,4.7,4.8,4.3,4.6,4.4,4.8,4.8,4.3,4.7,4.6,4.4,4.4,4.6,4.9,5.0,4.4,4.6,4.5,4.7,4.7,4.6,4.7,4.4,4.9,4.4,4.4,4.8,4.3,4.7,4.6,4.4,4.6,4.4,4.4,4.3,4.4,4.6,4.4,4.8,4.8,4.5,4.9,4.4,4.5,4.5,4.2,4.2,4.3,4.9,4.7,4.4,4.4,4.3,4.4,4.5,4.4,4.8,4.8,4.3,4.6,4.3,4.4,4.5,4.4,4.4,4.9,4.9,4.2,4.5,4.8,4.8,4.9,4.7,4.6,4.6,4.4,4.6,4.5,4.5,4.8,4.5,4.4,4.2,4.7,4.3,4.4,4.3,]"//包马克龙值汇总
     zgzl_p1："1",//,轧工厂质量p1占比
     zgzl_p2："1",,//轧工厂质量p1占比
     zgzl_p3："1",//轧工厂质量p1占比
     Dlbqdsdt："[29.6,29.8,30.9,30.5,30.1,29.4,29.5,29.3,28.3,30.4,29.8,30.2,29.1,29.8,29.2,30.6,29.1,31.0,30.7,30.6,30.6,28.1,29.3,28.8,29.1,30.2,28.1,29.8,29.3,30.8,30.1,30.6,29.8,29.8,29.4,30.0,30.6,31.3,29.6,31.4,30.0,31.5,29.3,30.1,30.9,29.7,29.3,30.4,30.7,29.8,29.2,29.3,29.2,28.1,29.8,28.6,29.9,30.5,29.7,30.1,29.9,30.6,29.7,29.3,30.5,28.8,29.8,28.6,28.6,31.4,29.7,30.1,30.1,29.0,28.4,30.5,30.5,28.5,29.4,29.6,29.1,27.6,29.8,28.8,29.3,29.1,29.4,28.9,30.7,29.7,30.0,28.4,29.2,29.4,30.0,28.5,30.1,30.2,28.8,29.5,29.6,29.7,29.5,29.1,29.7,28.6,30.2,29.7,30.8,29.4,28.6,29.3,28.9,29.1,29.8,30.1,30.6,28.6,29.8,29.7,29.3,30.8,30.1,28.4,30.1,30.8,28.9,28.9,30.3,29.5,29.6,30.5,28.6,29.8,29.6,30.3,29.7,29.2,30.0,29.6,29.6,29.7,30.8,30.6,30.3,30.0,29.2,30.0,30.7,30.3,30.3,31.0,30.0,30.2,29.9,28.6,28.6,30.1,29.6,29.7,29.7,29.3,30.1,30.8,29.8,27.9,30.1,31.2,28.8,28.7,27.8,29.6,28.6,28.4,28.8,29.1,29.4,31.0,30.1,30.3,29.2,30.3,28.6,29.9,29.5,30.2,]" ,//强力累计图（断裂比强度）
     长整齐度(%)
    cdzqd_min: "81.6",//长度整齐度最大值
    cdzqd_max: "85.3",//长度整齐度最小值
    hy_jysj_dlbqdzs:””//平均值
    断裂比强度
            "dlbqd_max":"31.5",//断裂比强度最大
            "dlbqd_min":"27.6"//断裂比前度最小
    hy_jysj_dlbqd：‘29.68’//主体断裂比强度,断裂比平局均值
    hy_jysj_hd:// b 灰度
    hy_jysj_fsl:” 80.55”// Rd（%）
    hy_jysj_hcl:”8.2”//平均回潮率8.2%
    hy_jysj_hzl:” 2.3”//平均含杂率率2.3%
    hy_jysj_mz：42620,//合计毛重 42620kg
    hy_jysj_gdzl: 42579，//合计公定重量42579kg
    hy_jysj_zbs:186,//包数
    hy_jysj_pz:246,//合计皮重246kg
    hy_jysj_jz:42374,//合计净重42374kg
     */

    String PRODUCT_PRICE;
    String HY_JYSJ_JYRQ;
    String HY_JYSJ_JYJGMC;
    String HY_JYSJ_CKQY;
    String hy_ckbmc;
    String hy_jgdw_jgdwmc;
    String hy_jysj_lx;
    String czfs;
    String hy_jysj_ysj;
    String hy_jysj_ysj_zj;
    String bm1j;
    String bm2j;
    String bm3j;
    String bm4j;
    String bm5j;
    String ddwm1j;
    String ddwm2j;
    String ddwm3j;
    String dhrm1j;
    String dhrm2j;
    String dhrm3j;
    String hrm1j;
    String hrm2j;
    String hy_jysj_cdz;
    String cdj25;
    String cdj26;
    String cdj27;
    String cdj28;
    String cdj29;
    String cdj30;
    String cdj31;
    String cdj32;
    String hy_jysj_mkldc_zj;
    String mklzc1;
    String mklzb1;
    String mklza;
    String mklzb2;
    String mklzc2;
    String mklzsdt;
    String zgzl_p1;
    String zgzl_p2;
    String zgzl_p3;
    String dlbqdsdt;
    String cdzqd_min;
    String cdzqd_max;
    String hy_jysj_dlbqdzs;
    String dlbqd_max;
    String dlbqd_min;
    String hy_jysj_dlbqd;
    String hy_jysj_hd;
    String hy_jysj_fsl;
    String hy_jysj_hcl;
    String hy_jysj_hzl;
    String hy_jysj_mz;
    String hy_jysj_gdzl;
    String hy_jysj_zbs;
    String hy_jysj_pz;
    String hy_jysj_jz;
    String hy_jysj_cdzqdzs;

    public String getCzfs() {
        return czfs;
    }

    public void setCzfs(String czfs) {
        this.czfs = czfs;
    }

    public String getHy_ckbmc() {
        return hy_ckbmc;
    }

    public void setHy_ckbmc(String hy_ckbmc) {
        this.hy_ckbmc = hy_ckbmc;
    }

    public String getHy_jgdw_jgdwmc() {
        return hy_jgdw_jgdwmc;
    }

    public void setHy_jgdw_jgdwmc(String hy_jgdw_jgdwmc) {
        this.hy_jgdw_jgdwmc = hy_jgdw_jgdwmc;
    }

    public String getHY_JYSJ_CKQY() {
        return HY_JYSJ_CKQY;
    }

    public void setHY_JYSJ_CKQY(String HY_JYSJ_CKQY) {
        this.HY_JYSJ_CKQY = HY_JYSJ_CKQY;
    }

    public String getHY_JYSJ_JYJGMC() {
        return HY_JYSJ_JYJGMC;
    }

    public void setHY_JYSJ_JYJGMC(String HY_JYSJ_JYJGMC) {
        this.HY_JYSJ_JYJGMC = HY_JYSJ_JYJGMC;
    }

    public String getHY_JYSJ_JYRQ() {
        return HY_JYSJ_JYRQ;
    }

    public void setHY_JYSJ_JYRQ(String HY_JYSJ_JYRQ) {
        this.HY_JYSJ_JYRQ = HY_JYSJ_JYRQ;
    }

    public String getHy_jysj_lx() {
        return hy_jysj_lx;
    }

    public void setHy_jysj_lx(String hy_jysj_lx) {
        this.hy_jysj_lx = hy_jysj_lx;
    }

    public String getPRODUCT_PRICE() {
        return PRODUCT_PRICE;
    }

    public void setPRODUCT_PRICE(String PRODUCT_PRICE) {
        this.PRODUCT_PRICE = PRODUCT_PRICE;
    }

    public String getBm1j() {
        return bm1j;
    }

    public void setBm1j(String bm1j) {
        this.bm1j = bm1j;
    }

    public String getBm2j() {
        return bm2j;
    }

    public void setBm2j(String bm2j) {
        this.bm2j = bm2j;
    }

    public String getBm3j() {
        return bm3j;
    }

    public void setBm3j(String bm3j) {
        this.bm3j = bm3j;
    }

    public String getBm4j() {
        return bm4j;
    }

    public void setBm4j(String bm4j) {
        this.bm4j = bm4j;
    }

    public String getBm5j() {
        return bm5j;
    }

    public void setBm5j(String bm5j) {
        this.bm5j = bm5j;
    }

    public String getCdj25() {
        return cdj25;
    }

    public void setCdj25(String cdj25) {
        this.cdj25 = cdj25;
    }

    public String getCdj26() {
        return cdj26;
    }

    public void setCdj26(String cdj26) {
        this.cdj26 = cdj26;
    }

    public String getCdj27() {
        return cdj27;
    }

    public void setCdj27(String cdj27) {
        this.cdj27 = cdj27;
    }

    public String getCdj28() {
        return cdj28;
    }

    public void setCdj28(String cdj28) {
        this.cdj28 = cdj28;
    }

    public String getCdj29() {
        return cdj29;
    }

    public void setCdj29(String cdj29) {
        this.cdj29 = cdj29;
    }

    public String getCdj30() {
        return cdj30;
    }

    public void setCdj30(String cdj30) {
        this.cdj30 = cdj30;
    }

    public String getCdj31() {
        return cdj31;
    }

    public void setCdj31(String cdj31) {
        this.cdj31 = cdj31;
    }

    public String getCdj32() {
        return cdj32;
    }

    public void setCdj32(String cdj32) {
        this.cdj32 = cdj32;
    }

    public String getCdzqd_max() {
        return cdzqd_max;
    }

    public void setCdzqd_max(String cdzqd_max) {
        this.cdzqd_max = cdzqd_max;
    }

    public String getCdzqd_min() {
        return cdzqd_min;
    }

    public void setCdzqd_min(String cdzqd_min) {
        this.cdzqd_min = cdzqd_min;
    }

    public String getDdwm1j() {
        return ddwm1j;
    }

    public void setDdwm1j(String ddwm1j) {
        this.ddwm1j = ddwm1j;
    }

    public String getDdwm2j() {
        return ddwm2j;
    }

    public void setDdwm2j(String ddwm2j) {
        this.ddwm2j = ddwm2j;
    }

    public String getDdwm3j() {
        return ddwm3j;
    }

    public void setDdwm3j(String ddwm3j) {
        this.ddwm3j = ddwm3j;
    }

    public String getDhrm1j() {
        return dhrm1j;
    }

    public void setDhrm1j(String dhrm1j) {
        this.dhrm1j = dhrm1j;
    }

    public String getDhrm2j() {
        return dhrm2j;
    }

    public void setDhrm2j(String dhrm2j) {
        this.dhrm2j = dhrm2j;
    }

    public String getDhrm3j() {
        return dhrm3j;
    }

    public void setDhrm3j(String dhrm3j) {
        this.dhrm3j = dhrm3j;
    }

    public String getDlbqd_max() {
        return dlbqd_max;
    }

    public void setDlbqd_max(String dlbqd_max) {
        this.dlbqd_max = dlbqd_max;
    }

    public String getDlbqd_min() {
        return dlbqd_min;
    }

    public void setDlbqd_min(String dlbqd_min) {
        this.dlbqd_min = dlbqd_min;
    }

    public String getDlbqdsdt() {
        return dlbqdsdt;
    }

    public void setDlbqdsdt(String dlbqdsdt) {
        this.dlbqdsdt = dlbqdsdt;
    }

    public String getHrm1j() {
        return hrm1j;
    }

    public void setHrm1j(String hrm1j) {
        this.hrm1j = hrm1j;
    }

    public String getHrm2j() {
        return hrm2j;
    }

    public void setHrm2j(String hrm2j) {
        this.hrm2j = hrm2j;
    }

    public String getHy_jysj_cdz() {
        return hy_jysj_cdz;
    }

    public void setHy_jysj_cdz(String hy_jysj_cdz) {
        this.hy_jysj_cdz = hy_jysj_cdz;
    }

    public String getHy_jysj_dlbqd() {
        return hy_jysj_dlbqd;
    }

    public void setHy_jysj_dlbqd(String hy_jysj_dlbqd) {
        this.hy_jysj_dlbqd = hy_jysj_dlbqd;
    }

    public String getHy_jysj_dlbqdzs() {
        return hy_jysj_dlbqdzs;
    }

    public void setHy_jysj_dlbqdzs(String hy_jysj_dlbqdzs) {
        this.hy_jysj_dlbqdzs = hy_jysj_dlbqdzs;
    }

    public String getHy_jysj_fsl() {
        return hy_jysj_fsl;
    }

    public void setHy_jysj_fsl(String hy_jysj_fsl) {
        this.hy_jysj_fsl = hy_jysj_fsl;
    }

    public String getHy_jysj_gdzl() {
        return hy_jysj_gdzl;
    }

    public void setHy_jysj_gdzl(String hy_jysj_gdzl) {
        this.hy_jysj_gdzl = hy_jysj_gdzl;
    }

    public String getHy_jysj_hcl() {
        return hy_jysj_hcl;
    }

    public void setHy_jysj_hcl(String hy_jysj_hcl) {
        this.hy_jysj_hcl = hy_jysj_hcl;
    }

    public String getHy_jysj_hd() {
        return hy_jysj_hd;
    }

    public void setHy_jysj_hd(String hy_jysj_hd) {
        this.hy_jysj_hd = hy_jysj_hd;
    }

    public String getHy_jysj_hzl() {
        return hy_jysj_hzl;
    }

    public void setHy_jysj_hzl(String hy_jysj_hzl) {
        this.hy_jysj_hzl = hy_jysj_hzl;
    }

    public String getHy_jysj_jz() {
        return hy_jysj_jz;
    }

    public void setHy_jysj_jz(String hy_jysj_jz) {
        this.hy_jysj_jz = hy_jysj_jz;
    }

    public String getHy_jysj_mkldc_zj() {
        return hy_jysj_mkldc_zj;
    }

    public void setHy_jysj_mkldc_zj(String hy_jysj_mkldc_zj) {
        this.hy_jysj_mkldc_zj = hy_jysj_mkldc_zj;
    }

    public String getHy_jysj_mz() {
        return hy_jysj_mz;
    }

    public void setHy_jysj_mz(String hy_jysj_mz) {
        this.hy_jysj_mz = hy_jysj_mz;
    }

    public String getHy_jysj_pz() {
        return hy_jysj_pz;
    }

    public void setHy_jysj_pz(String hy_jysj_pz) {
        this.hy_jysj_pz = hy_jysj_pz;
    }

    public String getHy_jysj_ysj() {
        return hy_jysj_ysj;
    }

    public void setHy_jysj_ysj(String hy_jysj_ysj) {
        this.hy_jysj_ysj = hy_jysj_ysj;
    }

    public String getHy_jysj_ysj_zj() {
        return hy_jysj_ysj_zj;
    }

    public void setHy_jysj_ysj_zj(String hy_jysj_ysj_zj) {
        this.hy_jysj_ysj_zj = hy_jysj_ysj_zj;
    }

    public String getHy_jysj_zbs() {
        return hy_jysj_zbs;
    }

    public void setHy_jysj_zbs(String hy_jysj_zbs) {
        this.hy_jysj_zbs = hy_jysj_zbs;
    }

    public String getMklza() {
        return mklza;
    }

    public void setMklza(String mklza) {
        this.mklza = mklza;
    }

    public String getMklzb1() {
        return mklzb1;
    }

    public void setMklzb1(String mklzb1) {
        this.mklzb1 = mklzb1;
    }

    public String getMklzb2() {
        return mklzb2;
    }

    public void setMklzb2(String mklzb2) {
        this.mklzb2 = mklzb2;
    }

    public String getMklzc1() {
        return mklzc1;
    }

    public void setMklzc1(String mklzc1) {
        this.mklzc1 = mklzc1;
    }

    public String getMklzc2() {
        return mklzc2;
    }

    public void setMklzc2(String mklzc2) {
        this.mklzc2 = mklzc2;
    }

    public String getMklzsdt() {
        return mklzsdt;
    }

    public void setMklzsdt(String mklzsdt) {
        this.mklzsdt = mklzsdt;
    }

    public String getZgzl_p1() {
        return zgzl_p1;
    }

    public void setZgzl_p1(String zgzl_p1) {
        this.zgzl_p1 = zgzl_p1;
    }

    public String getZgzl_p2() {
        return zgzl_p2;
    }

    public void setZgzl_p2(String zgzl_p2) {
        this.zgzl_p2 = zgzl_p2;
    }

    public String getZgzl_p3() {
        return zgzl_p3;
    }

    public void setZgzl_p3(String zgzl_p3) {
        this.zgzl_p3 = zgzl_p3;
    }

    public String getHy_jysj_cdzqdzs() {
        return hy_jysj_cdzqdzs;
    }

    public void setHy_jysj_cdzqdzs(String hy_jysj_cdzqdzs) {
        this.hy_jysj_cdzqdzs = hy_jysj_cdzqdzs;
    }
}
