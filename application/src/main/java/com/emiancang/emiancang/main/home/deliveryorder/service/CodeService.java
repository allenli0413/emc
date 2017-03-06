package com.emiancang.emiancang.main.home.deliveryorder.service;

import com.emiancang.emiancang.http.HttpResult;
import com.emiancang.emiancang.main.home.deliveryorder.model.CodeModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Author: liyh
 * E-mail: liyh@zhiwyl.com
 * Phone: 15033990609
 * Date: 2017/2/28  Time: 17:32
 * Description:
 */

public interface CodeService {
    //        hth:合同号(必须参数)
    //        username:用户名(必须参数)

    @FormUrlEncoded
    @POST("sendMessage.mvc")
    Observable<HttpResult<CodeModel>> list(@Field("hth") String hth,
                                           @Field("username") String username);

}
