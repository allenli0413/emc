package com.emiancang.emiancang.main.home.logisticsquery.service;

import com.emiancang.emiancang.http.HttpResult;
import com.emiancang.emiancang.main.home.logisticsquery.model.LogisticsQuerySecondDetailModel;
import com.emiancang.emiancang.main.home.logisticsquery.model.LogisticsQuerySecondModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yuanyueqing on 2017/2/23.
 */

public interface LogisticsQuerySecondService {
    /**
     * 6.29.查询物流--线路--列表
     当前页	pageIndex
     每页条数	pageSize
     线路名称	serviceName
     有效时间起	startDate
     有效时间止	endDate
     运输方式	transportation	运输方式1:公路2：铁路
     价格起	startPrice
     价格止	endPrice
     起运地	statrAddr	地区代码
     到达地	endAddr	地区代码
     */
    @FormUrlEncoded
    @POST("home/getServiceXlList")
    Observable<HttpResult<List<LogisticsQuerySecondModel>>> list(@Field("pageIndex") String pageIndex,
                                                                 @Field("pageSize") String pageSize,
                                                                 @Field("serviceName") String serviceName,
                                                                 @Field("startDate") String startDate,
                                                                 @Field("endDate") String endDate,
                                                                 @Field("transportation") String transportation,
                                                                 @Field("startPrice") String startPrice,
                                                                 @Field("endPrice") String endPrice,
                                                                 @Field("statrAddr") String statrAddr,
                                                                 @Field("endAddr") String endAddr);

    /**
     * 6.30.查询物流--线路--详情
     * serviceId	物流id
     */
    @FormUrlEncoded
    @POST("home/getServiceXlById")
    Observable<HttpResult<LogisticsQuerySecondDetailModel>> detail(@Field("serviceId") String serviceId);
}
