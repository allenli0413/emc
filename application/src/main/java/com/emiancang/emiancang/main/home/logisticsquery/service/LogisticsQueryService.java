package com.emiancang.emiancang.main.home.logisticsquery.service;

import com.emiancang.emiancang.http.HttpResult;
import com.emiancang.emiancang.main.home.logisticsquery.model.LogisticsQueryModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yuanyueqing on 2016/11/9.
 */

public interface LogisticsQueryService {
    /**
     * 当前页	pageIndex
     每页条数	pageSize
     操作类型	type	0=查询所有的，1=查询我发布的	Integer[“我的”物流列表也是用这个接口，在参数中加入type=1即可]
     */
    @FormUrlEncoded
    @POST("home/getServiceList")
    Observable<HttpResult<List<LogisticsQueryModel>>> list(@Field("pageIndex") String pageIndex,
                                                           @Field("pageSize") String pageSize,
                                                           @Field("type") String type);
}
