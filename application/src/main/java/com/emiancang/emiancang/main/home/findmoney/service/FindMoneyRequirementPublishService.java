package com.emiancang.emiancang.main.home.findmoney.service;

import com.emiancang.emiancang.bean.PublishEntity;
import com.emiancang.emiancang.bean.ResEntity;
import com.emiancang.emiancang.http.HttpResult;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yuanyueqing on 2016/11/18.
 */

public interface FindMoneyRequirementPublishService {
    /**
     * 类型	eJrsqJrfwlx	00:质押融资,01:交易配资，02:棉仓白条，03：运费补贴
     内容	eJrsqNr
     发布人id	eSjzcNm
     */
    @FormUrlEncoded
    @POST("home/sendFinancingDemand")
    Observable<HttpResult<PublishEntity>> list(@Field("eJrsqJrfwlx") String eJrsqJrfwlx,
                                                     @Field("eJrsqNr") String eJrsqNr);
}
