package com.emiancang.emiancang.http;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ShowService {
    @FormUrlEncoded
    @POST("show/list")
    Observable<HttpResult<List<ShowItem>>> list(@Field("cate") String cate,
                                                @Field("page") String page,
                                                @Field("perPage") String perPage,
                                                @Field("search") String search);
}