package com.emiancang.emiancang.main.home.deliveryorder.service;

import com.emiancang.emiancang.http.HttpResult;
import com.emiancang.emiancang.main.home.deliveryorder.model.DeliveryOrderModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Author: liyh
 * E-mail: liyh@zhiwyl.com
 * Phone: 15033990609
 * Date: 2017/2/23  Time: 16:28
 * Description:
 */

public interface DeliveryOrderService {
/*    当前页	pageIndex
    每页条数	pageSize
    客户名称	custName	必传
    合同号	hth*/
@FormUrlEncoded
@POST("getHtInfos.mvc")
Observable<HttpResult<List<DeliveryOrderModel>>> list(@Field("pageIndex") String pageIndex,
                                                      @Field("pageSize") String pageSize,
                                                      @Field("custName") String custName,
                                                      @Field("hth") String hth);
}
