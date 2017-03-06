package com.emiancang.emiancang.main.home.redpapercotton.service;

import com.emiancang.emiancang.http.HttpResult;
import com.emiancang.emiancang.main.home.redpapercotton.model.RedPaperCottonModel;
import com.emiancang.emiancang.main.home.redpapercotton.model.RedPaperCottonProductDetailGetModel;
import com.emiancang.emiancang.main.home.redpapercotton.model.RedPaperCottonProductDetailModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yuanyueqing on 2016/11/14.
 */

public interface RedPaperCottonService {
    @FormUrlEncoded
    @POST("show/list")
    Observable<HttpResult<List<RedPaperCottonModel>>> list(@Field("cate") String cate,
                                                           @Field("page") String page,
                                                           @Field("perPage") String perPage,
                                                           @Field("search") String search);

    @FormUrlEncoded
    @POST("home/getProductDetailsById")
    Observable<HttpResult<RedPaperCottonProductDetailModel>> product_detail(@Field("ProductId") String ProductId);

    @GET("home/getCompanyDetails2")
    Observable<HttpResult<RedPaperCottonProductDetailGetModel>> product_detail_get(@Query("ProductId") String ProductId);
}
