package com.emiancang.emiancang.main.home.qualityquery.service;

import com.emiancang.emiancang.bean.ResEntity;
import com.emiancang.emiancang.http.HttpResult;
import com.emiancang.emiancang.main.home.qualityquery.model.CottonCollectModel;
import com.emiancang.emiancang.main.home.qualityquery.model.HelpMeContentModel;
import com.emiancang.emiancang.main.home.qualityquery.model.QualityContrastDetailStatusModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yuanyueqing on 2016/12/6.
 */

public interface QualityContrastDetailStatusService {
    /**
     * productId	产品id		8594
     gcmgyMhph	批号		66190141018
     */
    @FormUrlEncoded
    @POST("home/checkProductIsCollect")
    Observable<HttpResult<QualityContrastDetailStatusModel>> check(@Field("productId") String productId,
                                                                   @Field("gcmgyMhph") String gcmgyMhph);

    /**
     *productId	产品id
     */
    @FormUrlEncoded
    @POST("getIsAddGwc.mvc")
    Observable<HttpResult<ResEntity>> isInCart(@Field("productId") String productId);

    /**
     * productId	产品id
     */
    @FormUrlEncoded
    @POST("addGwc.mvc")
    Observable<HttpResult<ResEntity>> addCart(@Field("productId") String productId);

    @FormUrlEncoded
    @POST("my/collect")
    Observable<HttpResult<CottonCollectModel>> collect(@Field("type") String type,
                                                       @Field("id") String id);

    @FormUrlEncoded
    @POST("my/cancelCollect")
    Observable<HttpResult<ResEntity>> collectCancel(@Field("type") String type,
                                                    @Field("id") String id);

    /**
     * 主键id	eZSid	Stirng	614d2def86554d6ea984adb01e49b097
     */
    @FormUrlEncoded
    @POST("home/getEZSearchDetails")
    Observable<HttpResult<HelpMeContentModel>> findQuery(@Field("eZSid") String eZSid);
}
