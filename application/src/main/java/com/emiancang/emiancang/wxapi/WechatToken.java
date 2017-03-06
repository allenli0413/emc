package com.emiancang.emiancang.wxapi;

/**
 * Created by wuxu on 15/5/12.
 */
public class WechatToken {
    String token;
    public WechatToken(String token){
        this.token = token;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
