package com.emiancang.emiancang.main.home.service;

import com.emiancang.emiancang.http.HttpResult;
import com.emiancang.emiancang.main.home.model.BannerModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yuanyueqing on 2016/11/22.
 */

public interface BannerService {
    @POST("home/getActiveList")
    Observable<HttpResult<List<BannerModel>>> list();
}
