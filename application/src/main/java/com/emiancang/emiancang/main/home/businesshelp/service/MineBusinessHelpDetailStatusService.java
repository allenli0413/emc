package com.emiancang.emiancang.main.home.businesshelp.service;

import com.emiancang.emiancang.http.HttpResult;
import com.emiancang.emiancang.main.home.businesshelp.model.MineBusinessHelpDetailStatusModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yuanyueqing on 2016/11/9.
 */

public interface MineBusinessHelpDetailStatusService {
    /**
     * 买卖帮标识	eCgxqId	bfbe5e0e0c284e5ea5fe7568c703f53e	ID查询
     */
    @FormUrlEncoded
    @POST("home/getTradingDemandDetails")
    Observable<HttpResult<MineBusinessHelpDetailStatusModel>> list(@Field("eCgxqId") String eCgxqId);
}
