package com.emiancang.emiancang.main.home.service;

import com.emiancang.emiancang.http.HttpResult;
import com.emiancang.emiancang.main.home.model.CottonRecommendModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yuanyueqing on 2016/11/18.
 */

public interface CottonRecommendService {
    /**
     *经度	lng
     维度	lat
     当前页	pageIndex
     每页条数	pageSize
     */
    @FormUrlEncoded
    @POST("home/getNearCotton")
    Observable<HttpResult<List<CottonRecommendModel>>> list(@Field("lng") String lng,
                                                            @Field("lat") String lat,
                                                            @Field("pageIndex") String pageIndex,
                                                            @Field("pageSize") String pageSize);
}
