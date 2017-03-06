package com.emiancang.emiancang.main.home.findmoney.service;

import com.emiancang.emiancang.http.HttpResult;
import com.emiancang.emiancang.main.home.findmoney.model.FindMoneyModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yuanyueqing on 2016/11/9.
 */

public interface FindMoneySerivce {
    @FormUrlEncoded
    @POST("home/getFinancingDemandList")
    Observable<HttpResult<List<FindMoneyModel>>> list(@Field("pageIndex") String pageIndex,
                                                      @Field("pageSize") String pageSize);
}
