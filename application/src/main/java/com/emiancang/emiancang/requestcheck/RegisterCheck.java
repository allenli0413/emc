package com.emiancang.emiancang.requestcheck;

import android.text.TextUtils;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 注册检测   验证码登陆检测
 * @date 15-7-16 上午3:04
 */
public class RegisterCheck extends PhoneCheck {
    String phone;
    String password;
    String verificationCode;
    String repeatPassword;


    public RegisterCheck(String phone, String verificationCode, String password, String repeatPassword) {
        super(phone);
        this.phone = phone;
        this.password = password;
        this.verificationCode = verificationCode;
        this.repeatPassword = repeatPassword;
    }

    @Override
    public String getCheckInfo() {
        String check = phone(phone);
        if (!TextUtils.isEmpty(check)) {
            return check;
        }

        if (TextUtils.isEmpty(verificationCode)) {
            return "请输入短信验证码！";
        }
        if(verificationCode.indexOf(" ") != -1){
            return "验证码不能有空格";
        }

        if (TextUtils.isEmpty(password)) {
            return "请输入您的密码！";
        }

        if (!password.equals(repeatPassword)) {
            return "您两次输入的密码不一致！";
        }

        if(password.indexOf(" ") != -1){
            return "密码不能有空格";
        }

        int passwordSize = password.length();
        if (passwordSize < 6 || passwordSize > 20) {
            return "请输入6~20位的密码！";
        }
        return null;

    }
}
