package com.emiancang.emiancang.main.home.service;

import com.emiancang.emiancang.http.HttpResult;
import com.emiancang.emiancang.main.home.model.ScanModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yuanyueqing on 2016/12/19.
 */

public interface ScanService {
    /**
     * 棉包条形码	barCode	必传
     */
    @FormUrlEncoded
    @POST("home/getHyJysjInfoByBarCode")
    Observable<HttpResult<ScanModel>> barInfo(@Field("barCode") String barCode);
}
