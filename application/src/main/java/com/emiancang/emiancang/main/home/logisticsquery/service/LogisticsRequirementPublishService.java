package com.emiancang.emiancang.main.home.logisticsquery.service;

import com.emiancang.emiancang.bean.ResEntity;
import com.emiancang.emiancang.http.HttpResult;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yuanyueqing on 2016/11/18.
 */

public interface LogisticsRequirementPublishService {
    /**
     * 起运地	statrAddr	传最后一级的编码即可	String
     到达地	endAddr	传最后一级的编码即可
     起运地-省	statrProvince Name	起运地-省名称	河北省
     到达地-省	endProvince Name	到达地-省名称	河南省
     起运地名称	statrAddrName	海淀区
     止运地名称	endAddrName	朝阳区
     起运地-县	statrDistrictName	起运地-县名称	涿鹿县
     到达地-县	endDistrictName	到达地-县名称	朝阳区
     价格起	servicePrice		double[去掉价格止字段]
     起运日期	startDate	Date	=
     到达日期	endDate	date
     */
    @FormUrlEncoded
    @POST("home/sendService")
    Observable<HttpResult<ResEntity>> list(@Field("statrAddr") String statrAddr,
                                           @Field("endAddr") String endAddr,
                                           @Field("statrProvinceName") String statrProvinceName,
                                           @Field("endProvinceName") String endProvinceName,
                                           @Field("statrAddrName") String statrAddrName,
                                           @Field("endAddrName") String endAddrName,
                                           @Field("statrDistrictName") String statrDistrictName,
                                           @Field("endDistrictName") String endDistrictName,
                                           @Field("servicePrice") String servicePrice,
                                           @Field("servicePriceS") String servicePriceS,
                                           @Field("startDate") String startDate,
                                           @Field("endDate") String endDate);
}
