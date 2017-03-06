package com.emiancang.emiancang.requestcheck;

import android.text.TextUtils;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 注册检测   验证码登陆检测
 * @date 15-7-16 上午3:04
 */
public class PasswordCheck implements RequestCheckable {
    String oldpassword;
    String newpassword;
    String repeatnewpassword;


    public PasswordCheck(String oldpassword, String newpassword, String repeatnewpassword) {

        this.oldpassword = oldpassword;
        this.newpassword = newpassword;
        this.repeatnewpassword = repeatnewpassword;
    }

    @Override
    public String getCheckInfo() {

        if (TextUtils.isEmpty(oldpassword)) {
            return "请输入您的旧密码！";
        }

        if (TextUtils.isEmpty(newpassword)) {
            return "请输入您的新密码！";
        }

        int passwordSize = newpassword.length();
        if (passwordSize < 6 || passwordSize > 16) {
            return "请输入6~16位的密码！";
        }

        if (TextUtils.isEmpty(repeatnewpassword)) {
            return "请输入确认密码！";
        }

        if (!newpassword.equals(repeatnewpassword)) {
            return "您两次输入的密码不一致！";
        }
        return null;
    }
}
