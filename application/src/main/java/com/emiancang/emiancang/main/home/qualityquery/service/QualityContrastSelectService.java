package com.emiancang.emiancang.main.home.qualityquery.service;

import com.emiancang.emiancang.http.HttpResult;
import com.emiancang.emiancang.main.home.qualityquery.model.QualityContrastSelectModel;
import com.emiancang.emiancang.store.model.MainStoreModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yuanyueqing on 2016/11/11.
 */

public interface QualityContrastSelectService {
    /**
     * 批号	gcmgyMhph	批号
     是否红包棉	eSpbsSfhbm[修改字段名称]	0=否，1=是
     长度	cdj	28mm
     马值	mklz	B
     强力	ql	s3
     产地	areaId	100127
     棉花类型	className	"长绒棉，细绒棉"这里直接传字符串	可以多选‘，’逗号分割
     棉花类型-采摘方式	mhlx	1,2     1：手摘棉；2：机采棉	可以多选‘，’逗号分割
     年份	scnds	2016,2015,2017	可以多选，’逗号分割
     主体颜色级	ztYsj[修改参数名称及参数值]	11，21,31	可以多选，’逗号分割
     回潮区间	minHcl[这里原来是用逗号‘，’分割的字符串来标记范围，现在改为传入两个参数，一个最大值，一个最小值
     以下类似]	6
     回潮区间	maxHcl	8
     含杂区间	minHzl	0.8
     含杂区间	maxHzl	1.0
     断裂比强度范围	minDlbqd	27
     断裂比强度范围	maxDlbqd	29.91
     长度整齐度范围	minCdzqdzs	83
     长度整齐度范围	maxCdzqdzs	85
     马克隆值范围	minMklz	4
     马克隆值范围	maxMklz	4.4
     价格范围	minJg	12.85
     价格范围	maxJg	13
     排序	sort	0=综合排序，1=价格最低，2=价格最高，3=存放仓库，4=加工厂，5=发布时间，6=供货商
     当前页	pageIndex
     每页条数	pageSize
     */
    @FormUrlEncoded
    @POST("home/getProductList")
    Observable<HttpResult<List<MainStoreModel>>> list(@Field("gcmgyMhph") String gcmgyMhph,
                                                      @Field("eSpbsSfhbm") String eSpbsSfhbm,
                                                      @Field("cdj") String cdj,
                                                      @Field("mklz") String mklz,
                                                      @Field("ql") String ql,
                                                      @Field("areaId") String areaId,
                                                      @Field("className") String className,
                                                      @Field("mhlx") String mhlx,
                                                      @Field("scnds") String scnds,
                                                      @Field("ztYsj") String ztYsj,
                                                      @Field("minHcl") String minHcl,
                                                      @Field("maxHcl") String maxHcl,
                                                      @Field("minHzl") String minHzl,
                                                      @Field("maxHzl") String maxHzl,
                                                      @Field("minDlbqd") String minDlbqd,
                                                      @Field("maxDlbqd") String maxDlbqd,
                                                      @Field("minCdzqdzs") String minCdzqdzs,
                                                      @Field("maxCdzqdzs") String maxCdzqdzs,
                                                      @Field("minMklz") String minMklz,
                                                      @Field("maxMklz") String maxMklz,
                                                      @Field("minJg") String minJg,
                                                      @Field("maxJg") String maxJg,
                                                      @Field("sort") String sort,
                                                      @Field("lng") String lng,
                                                      @Field("lat") String lat,
                                                      @Field("pageIndex") String pageIndex,
                                                      @Field("pageSize") String pageSize);
}
