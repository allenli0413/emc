package com.emiancang.emiancang.service;

import com.emiancang.emiancang.bean.AppVersionInfo;
import com.emiancang.emiancang.bean.QueryUser;
import com.emiancang.emiancang.bean.RedpaperEnity;
import com.emiancang.emiancang.bean.StatusEntity;
import com.iflytek.thirdparty.F;
import com.emiancang.emiancang.bean.AptitudeInfo;
import com.emiancang.emiancang.bean.City;
import com.emiancang.emiancang.bean.CollectCottonEnterpriseEntity;
import com.emiancang.emiancang.bean.CollectCottonEntity;
import com.emiancang.emiancang.bean.CompanyDetails;
import com.emiancang.emiancang.bean.CompanyErrorInfo;
import com.emiancang.emiancang.bean.ContactEntity;
import com.emiancang.emiancang.bean.Dictionary;
import com.emiancang.emiancang.bean.EnterpriseInfo;
import com.emiancang.emiancang.bean.ForgetPassword;
import com.emiancang.emiancang.bean.MoneyInfo;
import com.emiancang.emiancang.bean.NearEnterprise;
import com.emiancang.emiancang.bean.OrbindSalesman;
import com.emiancang.emiancang.bean.OrderDetail;
import com.emiancang.emiancang.bean.OrderInfo;
import com.emiancang.emiancang.bean.ProvinceModel;
import com.emiancang.emiancang.bean.PushValidate;
import com.emiancang.emiancang.bean.RedPacketPopupWindowTypeEntity;
import com.emiancang.emiancang.bean.ResEntity;
import com.emiancang.emiancang.bean.SalesmanMangeEntity;
import com.emiancang.emiancang.bean.ServiceEntity;
import com.emiancang.emiancang.bean.ShoppingCartEntity;
import com.emiancang.emiancang.bean.WarehouseNameEntity;
import com.emiancang.emiancang.bean.WarnEntity;
import com.emiancang.emiancang.http.HttpResult;
import com.emiancang.emiancang.main.home.qualityquery.model.FilterGridviewModel;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserLogin;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.adapter.rxjava.Result;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface UserService {

    //------------------------------------------------------
    //神马接口

    /**
     * 获取验证码
     * @param shouji
     * @param yzmType
     * @return
     */
    @GET("generateYzm.mvc")
    Observable<HttpResult<ResEntity>> verification(@Query("shouji") String shouji,
                                              @Query("yzmType") String yzmType);
    /**
     * 注册
     * @param eSjzcXm
     * @param yzm
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("appregister.mvc")
    Observable<HttpResult<UserLogin>> register(@Field("shouji") String eSjzcXm,
                                            @Field("yzm") String yzm,
                                            @Field("yqm") String yqm,
                                                   @Field("password") String password);

    /**
     * 发放红包金额	sendMoney	正整数	10
     接受红包的用户openid	openid	微信用户静默授权某公众号	Ssx2j3s
     发放红包类型【比传参数】	sendType	1：邀请码红包；2：新用户红包；3：交易成功后发的红包；4：企业纠错红包；	1
     红包发放事由描述【比传参数】	sendReason	发放红包类型（1、2、3、4）分别对应不同的描述：
     1>邀请码
     2>新用户注册
     3>交易成功【订单号】
     4>企业信息纠错	交易成功【114110】

     接收手机用户id	appUserId	接收人app用户内码
     红包发放人用户id	sendUserId	用户内码	Shdhf7sjsj
     注册邀请码	invitedCode	给邀请用户发红包	ADXH32

     必传参数：sendType、sendReason；
     发邀请码红包还需要传的参数：invitedCode；
     发新注册用户红包还需要传的参数：appUserId；
     发企业信息纠错红包还需要传的参数：appUserId、sendMoney；
     发交易成功后发的红包还需要传的参数：appUserId、sendMoney；
     */
    @FormUrlEncoded
    @POST("backend/webchatpay/sendredpack")
    Observable<HttpResult<RedpaperEnity>> redpaper(@Field("sendType") String sendType, @Field("sendReason") String sendReason,
                                                   @Field("invitedCode") String invitedCode,
                                                   @Field("sendMoney") String sendMoney,
                                                   @Field("appUserId") String appUserId);

    /**
     * 完善用户信息
     * @return
     */
    @Multipart
    @POST("completeregisterinfo.mvc")
    Observable<HttpResult<UserLogin>> completeregisterinfo(@Part MultipartBody.Part Image);

    /**
     * 登录
     * @param eSjzcXm
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("applogin.mvc")
    Observable<HttpResult<UserLogin>> login(@Field("shouji") String eSjzcXm,
                                            @Field("password") String password,
                                            @Field("loginType") String loginType);


    /**
     * 修改密码
     * @param eSjzcXm
     * @param password
     * @param loginType
     * @return
     */
    @FormUrlEncoded
    @POST("forgetpassword.mvc")
    Observable<HttpResult<ResEntity>> forgetpassword(@Field("shouji") String eSjzcXm,
                                            @Field("password") String password,
                                            @Field("yzm") String loginType);

    /**
     * 删除购物车
     * @param gwcId
     * @param tszt
     * @return
     */
    @FormUrlEncoded
    @POST("removeGwc.mvc")
    Observable<HttpResult<ResEntity>> removeGwc(@Field("gwcId") String gwcId,
                                                @Field("tszt") String tszt);

    /**
     * 购物车下单
     * @param productId
     * @param gwcId
     * @return
     */
    @FormUrlEncoded
    @POST("addOrder.mvc")
    Observable<HttpResult<StatusEntity>> addOrder(@Field("productId") String productId,
                                                  @Field("gwcId") String gwcId);

    /**
     * 购物车推送校验
     * @param gwcId
     * @return
     */
    @FormUrlEncoded
    @POST("gwcPushValidate.mvc")
    Observable<HttpResult<PushValidate>> gwcPushValidate(@Field("gwcId") String gwcId);



    /**
     * 推送至企业购物车
     * @param gwcId
     * @return
     */
    @FormUrlEncoded
    @POST("pushToQyGwc.mvc")
    Observable<HttpResult<ResEntity>> pushToQyGwc(@Field("gwcId") String gwcId);

    /**
     * 获取企业LOGO
     * @param custId
     * @return
     */
    @GET("getQyLogo.vc")
    Observable<HttpResult<ForgetPassword>> getQyLogo(@Query("custId") String custId);


    //--------------------------------------------------------------------------------
    //内部接口

    /**
     * 5.24.	更新用户推送标识接口
     * @param clientid
     * @return
     */
    @FormUrlEncoded
    @POST("my/refreshClientid")
    Observable<HttpResult<ResEntity>> refreshClientid(@Field("clientid") String clientid,
                                                      @Field("registertelephone") String registertelephone,
                                                      @Field("password") String password);

    /**
     * 5.23.	修改环信用户密码
     * @param imusername
     * @param oldpassword
     * @param newpassword
     * @return
     */
    @FormUrlEncoded
    @POST("my/updateHuanxinPassword")
    Observable<HttpResult<ResEntity>> updateHuanxinPassword(@Field("imusername") String imusername,
                                                      @Field("oldpassword") String oldpassword,
                                                      @Field("newpassword") String newpassword);

    /**
     * 5.2.	更改用户姓名
     * @param eSjzcXm
     * @return
     */
    @FormUrlEncoded
    @POST("my/changeUsername")
    Observable<HttpResult<User>> updateName(@Field("eSjzcXm") String eSjzcXm);

    /**
     * 系统消息
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("my/getSysMessageList")
    Observable<HttpResult<List<WarnEntity>>> getSysMessageList(@Field("pageIndex") String pageIndex,
                                                         @Field("pageSize") String pageSize);

    /**
     * 6.18.	获取客服列表接口
     * @return
     */
    @FormUrlEncoded
    @POST("home/getCustomServiceList")
    Observable<HttpResult<List<ServiceEntity>>> getCustomServiceList(@Field("uid") String pageIndex);


    /**
     * 查询账号信息
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("my/getAccountInfomation")
    Observable<HttpResult<User>> getAccountInfomation(@Field("uid") String id);


    /**
     * 用户登录更新设备clientid
     * @return
     */
    @FormUrlEncoded
    @POST("my/updateClientidForLogin")
    Observable<HttpResult<ResEntity>> updateClientidForLogin(@Field("clientid") String clientid);

    /**
     * 	校验原手机验证码
     * @param passcode
     * @return
     */
    @FormUrlEncoded
    @POST("my/checkPasscode")
    Observable<HttpResult<ResEntity>> checkPasscode(@Field("passcode") String passcode);

    /**
     * 更改手机号
     * @param eSjzcSjh
     * @param passcode
     * @return
     */
    @FormUrlEncoded
    @POST("my/changeMobile")
    Observable<HttpResult<User>> changeMobile(@Field("eSjzcSjh") String eSjzcSjh,
                                                   @Field("passcode") String passcode);

    /**
     * 查询企业信息
     * @param custId
     * @return
     */
    @FormUrlEncoded
    @POST("my/getEnterpriseInfo")
    Observable<HttpResult<EnterpriseInfo>> getEnterpriseInfo(@Field("custId") String custId);

    /**
     *5.8.	根据企业名称查询(业务员账号)查询企业是否注册
     * @param custName
     * @return
     */
    @FormUrlEncoded
    @POST("my/getByEnterpriseName")
    Observable<HttpResult<ResEntity>> getByEnterpriseName(@Field("custName") String custName);

    /**
     *4.1.	查询字典接口
     * @param dicCode
     * @return
     */
    @FormUrlEncoded
    @POST("home/getDictionaryByCode")
    Observable<HttpResult<List<Dictionary>>> getDictionaryByCode(@Field("dicCode") String dicCode);

    /**
     *4.2.	获取城市列表
     * @return
     */
    @FormUrlEncoded
    @POST("home/getCityList")
    Observable<HttpResult<List<City>>> getCityList(@Field("areaId") String areaId);

    @FormUrlEncoded
    @POST("home/getCityList")
    Observable<HttpResult<List<City>>> getAllCityList(@Field("uid") String uid);

    @FormUrlEncoded
    @POST("home/getProvinceList")
    Observable<HttpResult<List<City>>> getProvinceList(@Field("uid") String uid);

    @FormUrlEncoded
    @POST("home/getCDList")
    Observable<HttpResult<ProvinceModel>> getCDList(@Field("uid") String uid);

    /**
     *5.9.	查询仓库名称列表
     * @param hyCkbCkmc
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("my/getHYckbList")
    Observable<HttpResult<List<WarehouseNameEntity>>> getHYckbList(@Field("hyCkbCkmc") String hyCkbCkmc,
                                                                   @Field("pageIndex") String pageIndex,
                                                                   @Field("pageSize") String pageSize);

    /**
     *5.10.	提交企业基本信息
     * @param custName
     * @param type
     * @param eparchyCode
     * @param companyAddress
     * @param hyCkbNm
     * @param eCompanyJd
     * @param eCompanyWd
     * @param groupContactPhone
     * @return
     */
    @FormUrlEncoded
    @POST("my/submitBaseInfo")
    Observable<HttpResult<EnterpriseInfo>> submitBaseInfo(@Field("custId") String custId,
                                                          @Field("custName") String custName,
                                                          @Field("type") String type,
                                                          @Field("eparchyCode") String eparchyCode,
                                                          @Field("companyAddress") String companyAddress,
                                                          @Field("hyCkbNm") String hyCkbNm,
                                                          @Field("eCompanyJd") String eCompanyJd,
                                                          @Field("eCompanyWd") String eCompanyWd,
                                                          @Field("groupContactPhone") String groupContactPhone);

    /**
     *5.13.	查询棉花收藏列表
     * @param hyCkbCkmc
     * @param pageIndex
     * @param lng
     * @param lat
     * @return
     */
    @FormUrlEncoded
    @POST("my/getCollectCottonList")
    Observable<HttpResult<List<CollectCottonEntity>>> getCollectCottonList(@Field("pageIndex") String hyCkbCkmc,
                                                                           @Field("pageSize") String pageIndex,
                                                                           @Field("lng") String lng,
                                                                           @Field("lat") String lat);

    /**
     *5.14.	查询棉企收藏列表
     * @param hyCkbCkmc
     * @param pageIndex
     * @param lng
     * @param lat
     * @return
     */
    @FormUrlEncoded
    @POST("my/getCollectCompanyList")
    Observable<HttpResult<List<CollectCottonEnterpriseEntity>>> getCollectCompanyList(@Field("pageIndex") String hyCkbCkmc,
                                                                                      @Field("pageSize") String pageIndex,
                                                                                      @Field("lng") String lng,
                                                                                      @Field("lat") String lat);

    /**
     *4.1.	查询字典接口
     * @param dicCode
     * @return
     */
    @FormUrlEncoded
    @POST("home/getDictionaryByCode")
    Observable<HttpResult<List<RedPacketPopupWindowTypeEntity>>> getDictionaryByCode2(@Field("dicCode") String dicCode);


    /**
     * 提交企业资质信息
     * @param szhygsyyzz
     * @param szhyqyrzsqs
     * @return
     */
    @Multipart
    @POST("my/submitAptitudeInfo")
    Observable<HttpResult<AptitudeInfo>> submitAptitudeInfo(@Part MultipartBody.Part szhygsyyzz,
                                                            @Part MultipartBody.Part szhyqyrzsqs);

    /**
     *5.11.	提交企业资质信息
     * @param gszzImg
     * @param qyrzsqs
     * @param qyzzjgImg
     * @param swdjzhImg
     * @return
     */
    @Multipart
    @POST("my/submitAptitudeInfo")
    Observable<HttpResult<AptitudeInfo>> submitAptitudeInfo2(@Part MultipartBody.Part gszzImg,
                                                             @Part MultipartBody.Part qyrzsqs,
                                                             @Part MultipartBody.Part qyzzjgImg,
                                                             @Part MultipartBody.Part swdjzhImg);
    /**
     *5.12.	查询返现列表
     * @param ezHbtype
     * @param startDate
     * @param endDate
     * @param hyCkbCkmc
     * @param pageIndex
     * @return
     */
    @FormUrlEncoded
    @POST("my/getBackMoneyList")
    Observable<HttpResult<MoneyInfo>> getBackMoneyList(@Field("ezHbtype") String ezHbtype,
                                                       @Field("startDate") String startDate,
                                                       @Field("endDate") String endDate,
                                                       @Field("pageIndex") String hyCkbCkmc,
                                                       @Field("pageSize") String pageIndex);

    /**
     *5.6.	获取业务员（管理员账号）
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST("my/getSalesmanList")
    Observable<HttpResult<List<SalesmanMangeEntity>>> getSalesmanListMobile(@Field("mobile") String mobile);

    /**
     *5.6.	获取业务员（管理员账号）
     * @param custId
     * @return
     */
    @FormUrlEncoded
    @POST("my/getSalesmanList")
    Observable<HttpResult<List<SalesmanMangeEntity>>> getSalesmanListCustId(@Field("custId") String custId);

    /**
     *5.7.	绑定/解除业务员（管理员账号）
     * @param type
     * @param salesmanNm
     * @param custId
     * @return
     */
    @FormUrlEncoded
    @POST("my/rmOrbindSalesman")
    Observable<HttpResult<OrbindSalesman>> rmOrbindSalesman(@Field("type") String type,
                                                            @Field("salesmanNm") String salesmanNm,
                                                            @Field("custId") String custId);

    /**
     * 获取订单列表
     * @param pageIndex
     * @param pageSize
     * @param orderType
     * @param eDdDdzt
     * @return
     */
    @FormUrlEncoded
    @POST("my/getOrderList")
    Observable<HttpResult<List<OrderInfo>>> getOrderList(@Field("pageIndex") String pageIndex,
                                                   @Field("pageSize") String pageSize,
                                                   @Field("orderType") String orderType,
                                                   @Field("eDdDdzt") String eDdDdzt);

    /**
     * 查询订单详情
     * @param eDdId
     * @return
     */
    @FormUrlEncoded
    @POST("my/getOrderDetails")
    Observable<HttpResult<OrderDetail>> getOrderDetails(@Field("eDdId") String eDdId);


    /**
     * 查询购物车列表
     * @param lng
     * @param lat
     * @return
     */
    @FormUrlEncoded
    @POST("my/getShoppingCartList")
    Observable<HttpResult<List<ShoppingCartEntity>>> getShoppingCartList(@Field("lng") String lng,
                                                                   @Field("lat") String lat);

    /**
     * 取消收藏
     * @param type
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("my/cancelCollect")
    Observable<HttpResult<ResEntity>> cancelCollect(@Field("type") String type,
                                                            @Field("id") int id);




    /**
     * 查询棉企列表
     * @param lng
     * @param lat
     * @param type
     * @param sort
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("home/getNearEnterprise")
    Observable<HttpResult<List<NearEnterprise>>> getNearEnterprise(@Field("custName") String custName,
                                                                   @Field("eparchyCode") String eparchyCode,
                                                                   @Field("eparchyName") String eparchyName,
                                                                   @Field("lng") String lng,
                                                                   @Field("lat") String lat,
                                                                   @Field("type") String type,
                                                                   @Field("sort") String sort,
                                                                   @Field("pageIndex") String pageIndex,
                                                                   @Field("pageSize") String pageSize);

    /**
     * 经度	lng
     维度	lat
     距离	distance;		50
     根据距离排序	sort	0=由近及远，1=由远及近
     */
    @FormUrlEncoded
    @POST("home/getNearEnterpriseByDistance")
    Observable<HttpResult<List<NearEnterprise>>> getNearEnterpriseByDistance(@Field("lng") String lng,
                                                                             @Field("lat") String lat,
                                                                             @Field("distance") String distance,
                                                                             @Field("sort") String sort);

//    /**
//     * 棉花企业详情
//     * @param custId
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("home/getCompanyDetails")
//    Observable<HttpResult<CompanyDetails>> getCompanyDetails(@Field("custId") String custId);

    /**
     * 棉花企业详情
     * @param custId
     * @return
     */
    @GET("home/getCompanyDetails")
    Observable<HttpResult<CompanyDetails>> getCompanyDetails(@Query("custId") String custId);

    /**
     * 棉花企业详情
     * @param custId
     * @return
     */
    @GET("home/getCompanyDetails")
    Observable<HttpResult<CompanyDetails>> getCompanyDetails(@Query("custId") String custId,
                                                             @Query("token") String token,
                                                             @Query("eSjzcNm") String eSjzcNm,
                                                             @Query("nonce") String nonce,
                                                             @Query("timeStamp") String timeStamp,
                                                             @Query("sign") String sign);

    /**
     * 收藏棉花、棉企
     * @param type
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("my/collect")
    Observable<HttpResult<ResEntity>> collect(@Field("type") String type,
                                                   @Field("id") String id);

    /**
     * 收藏棉花、棉企
     * @param type
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("my/cancelCollect")
    Observable<HttpResult<ResEntity>> cancelCollect(@Field("type") String type,
                                              @Field("id") String id);


    /**
     * 企业信息纠错
     * @param companyId
     * @param finderroeContent
     * @return
     */
    @FormUrlEncoded
    @POST("home/submitErrorInfo")
    Observable<HttpResult<ResEntity>> submitErrorInfo(@Field("companyId") String companyId,
                                                      @Field("finderroeContent") String finderroeContent);

    /**
     *6.18.	企业纠错详情接口
     * @param companyId
     * @return
     */
    @FormUrlEncoded
    @POST("home/getCompanyErrorInfo")
    Observable<HttpResult<CompanyErrorInfo>> getCompanyErrorInfo(@Field("companyId") String companyId);

    /**
     * 6.24.查询app版本信息
     * 用户内码	eSjzcNm
     */
    @FormUrlEncoded
    @POST("home/getAppVersion")
    Observable<HttpResult<AppVersionInfo>> getAppVersion(@Field("uid") String uid);

    /**
     * 6.28.根据手机号查询用户昵称和头像
     * @param eSjzcSjh 手机号
     * @return
     */
    @FormUrlEncoded
    @POST("home/getNameAndTx")
    Observable<HttpResult<QueryUser>> getNameAndTx(@Field("eSjzcSjh") String eSjzcSjh);

}