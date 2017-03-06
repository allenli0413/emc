package com.emiancang.emiancang.http;

import com.emiancang.emiancang.user.User;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface UserService {
    @FormUrlEncoded
    @POST("user/login")
    Observable<HttpResult<User>> login(@Field("password") String password, @Field("username") String username);
}