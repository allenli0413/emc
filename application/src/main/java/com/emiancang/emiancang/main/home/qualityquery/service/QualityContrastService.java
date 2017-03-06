package com.emiancang.emiancang.main.home.qualityquery.service;

import com.emiancang.emiancang.http.HttpResult;
import com.emiancang.emiancang.main.home.qualityquery.model.QualityContrastDetailModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yuanyueqing on 2016/12/1.
 */

public interface QualityContrastService {
    /**
     * ph	批号	VARCHAR2	非空
     */
    @FormUrlEncoded
    @POST("getGjsj.mvc")
    Observable<HttpResult<QualityContrastDetailModel>> detail(@Field("ph") String ph);
}
