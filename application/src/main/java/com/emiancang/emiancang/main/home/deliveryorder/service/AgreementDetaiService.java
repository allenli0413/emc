package com.emiancang.emiancang.main.home.deliveryorder.service;

import com.emiancang.emiancang.http.HttpResult;
import com.emiancang.emiancang.main.home.deliveryorder.model.AgreementDetailModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Author: liyh
 * E-mail: liyh@zhiwyl.com
 * Phone: 15033990609
 * Date: 2017/2/24  Time: 12:06
 * Description:
 */

public interface AgreementDetaiService {
    /*    spCkhtgzHth	合同号*/
    @FormUrlEncoded
    @POST("home/getHth")
    Observable<HttpResult<AgreementDetailModel>> list(@Field("spCkhtgzHth") String spCkhtgzHth);

}
