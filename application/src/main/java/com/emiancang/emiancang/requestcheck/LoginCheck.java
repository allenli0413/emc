package com.emiancang.emiancang.requestcheck;

import android.text.TextUtils;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 登录检测
 * @date 15-7-16 上午3:04
 */
public class LoginCheck extends PhoneCheck {
    String phone;
    String password;

    public LoginCheck(String phone, String password) {
        super(phone);
        this.phone = phone;
        this.password = password;
    }

    @Override
    public String getCheckInfo() {
        String check = phone(phone);
        if (!TextUtils.isEmpty(check)) {
            return check;
        }

        if (TextUtils.isEmpty(password)) {
            return "请输入您的密码！";
        }

        return null;
    }
}
