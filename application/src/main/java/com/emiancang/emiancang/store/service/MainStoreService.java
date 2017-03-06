package com.emiancang.emiancang.store.service;

import com.emiancang.emiancang.http.HttpResult;
import com.emiancang.emiancang.store.model.MainStoreModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yuanyueqing on 2016/11/14.
 */

public interface MainStoreService {
    @FormUrlEncoded
    @POST("show/list")
    Observable<HttpResult<List<MainStoreModel>>> list(@Field("cate") String cate,
                                                      @Field("page") String page,
                                                      @Field("perPage") String perPage,
                                                      @Field("search") String search);
}
