package com.emiancang.emiancang.main.home.findmoney.service;

import com.emiancang.emiancang.http.HttpResult;
import com.emiancang.emiancang.main.home.findmoney.model.FindMoneyDetailStatusModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yuanyueqing on 2016/11/9.
 */

public interface FindMoneyDetailService {
    /**
     * @param eJrsqId 资金需求id
     * @return
     */
    @FormUrlEncoded
    @POST("home/getFinancingDemandDetails")
    Observable<HttpResult<FindMoneyDetailStatusModel>> list(@Field("eJrsqId") String eJrsqId);
}
