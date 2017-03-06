package com.emiancang.emiancang.service;

import com.emiancang.emiancang.bean.EnterpriseTypeEntity;
import com.emiancang.emiancang.http.HttpResult;
import com.emiancang.emiancang.http.ShowItem;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface EnterpriseTypeService {
    @FormUrlEncoded
    @POST("show/list")
    Observable<HttpResult<List<EnterpriseTypeEntity>>> list(@Field("cate") String cate,
                                                            @Field("page") String page,
                                                            @Field("perPage") String perPage,
                                                            @Field("search") String search);
}