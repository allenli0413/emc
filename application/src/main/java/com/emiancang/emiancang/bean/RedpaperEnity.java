package com.emiancang.emiancang.bean;

/**
 * Created by yuanyueqing on 2017/2/9.
 */

public class RedpaperEnity {
    /**
     * 返回结果：
     {
     "resultCode": 200,
     "resultMsg": "OK",
     "resultInfo": [
     {
     code: "0"message: "sendMoney必须为数字"
     }
     ]
     }

     接口返回的所有resultInfo情况：
     "code", "0"  "message","用户内码不能为空"
     "code", "0"  "message","新用户注册没有红包"
     "code", "0"  "message","用户内码不能为空"
     "code", "0"  "message","发送金额不能为空"
     "code", "0"  "message","发送金额为数字"
     "code", "0"  "message","openid必传参数"
     "code", "1"  "message", "红包发送成功"
     "code", "2"  "message", "系统异常信息"
     */
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
