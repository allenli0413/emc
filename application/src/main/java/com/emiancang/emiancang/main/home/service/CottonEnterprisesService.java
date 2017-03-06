package com.emiancang.emiancang.main.home.service;

import com.emiancang.emiancang.http.HttpResult;
import com.emiancang.emiancang.main.home.model.CottonEnterprisesModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yuanyueqing on 2016/11/18.
 */

public interface CottonEnterprisesService {
    /**
     * 企业名称	custName	字符串
     企业所在地区	eparchyName	地区名称	新疆
     地区code	eparchyCode	选择地区，则传入地区code
     省名称	province	如果定位，则传入省市县的名称
     市名称	city
     县名称	countie
     经度	lng
     维度	lat
     企业类型	type	<option value="2c055dif4StF605">加工企业</option>
     <option value="5pS7F8DUx847681">贸易企业</option>
     <option value="52H7Bqj4AJ56XTG">纺织企业</option>
     <option value="3b18fO2ql14I8J0">仓库企业</option>
     <option value="A6SkN56pM5Fh115">物流企业</option>
     <option value="fi51hma234tHh2s">交易市场</option>
     根据距离排序	sort	0=由近及远，1=由远及近
     当前页	pageIndex
     每页条数	pageSize
     */
    @FormUrlEncoded
    @POST("home/getNearEnterprise")
    Observable<HttpResult<List<CottonEnterprisesModel>>> list(@Field("custName") String custName,
                                                              @Field("eparchyName") String eparchyName,
                                                              @Field("eparchyCode") String eparchyCode,
                                                              @Field("province") String province,
                                                              @Field("city") String city,
                                                              @Field("countie") String countie,
                                                              @Field("lng") String lng,
                                                              @Field("lat") String lat,
                                                              @Field("type") String type,
                                                              @Field("sort") String sort,
                                                              @Field("pageIndex") String pageIndex,
                                                              @Field("pageSize") String pageSize);
}
