package com.emiancang.emiancang.main.home.businesshelp.service;

import com.emiancang.emiancang.http.HttpResult;
import com.emiancang.emiancang.main.home.businesshelp.model.BusinessHelpModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yuanyueqing on 2016/11/9.
 */

public interface BusinessHelpService {
    /**
     * 当前页	pageIndex
     每页条数	pageSize
     操作类型	type	0=查询所有的，1=查询我发布的	?type类型值
     */
    @FormUrlEncoded
    @POST("home/getTradingDemandList")
    Observable<HttpResult<List<BusinessHelpModel>>> list(@Field("pageIndex") String pageIndex,
                                                         @Field("pageSize") String pageSize,
                                                         @Field("type") String type);
}
