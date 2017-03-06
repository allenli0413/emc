package com.emiancang.emiancang.main.home.businesshelp.service;

import com.emiancang.emiancang.bean.PublishEntity;
import com.emiancang.emiancang.bean.ResEntity;
import com.emiancang.emiancang.http.HttpResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yuanyueqing on 2016/11/22.
 */

public interface BusinessHelpPublishService {
    /**
     * 类型	eCgxqType[参数做调整]	需求类型--0：购买需求，1：销售需求
     内容	eCgxqAppxqxq
     */
    @FormUrlEncoded
    @POST("home/sendTradingDemand")
    Observable<HttpResult<PublishEntity>> publish(@Field("eCgxqType") String eCgxqType,
                                                  @Field("eCgxqAppxqxq") String eCgxqAppxqxq);
}
