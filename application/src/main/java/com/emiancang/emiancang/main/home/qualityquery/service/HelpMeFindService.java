package com.emiancang.emiancang.main.home.qualityquery.service;

import com.emiancang.emiancang.bean.ResEntity;
import com.emiancang.emiancang.http.HttpResult;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yuanyueqing on 2016/11/23.
 */

public interface HelpMeFindService {
    /**
     * 批号	eZUph
     发布内容	eZSinfo
     */
    @FormUrlEncoded
    @POST("home/sendEZSearchDemand")
    Observable<HttpResult<ResEntity>> submit(@Field("eZUph") String eZUph,
                                             @Field("eZSinfo") String eZSinfo);
}
