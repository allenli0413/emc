package com.emiancang.emiancang.requestcheck;

import android.text.TextUtils;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 电话号码检测
 * @date 15-7-16 上午4:01
 */
public class PhoneCheck implements RequestCheckable {

    String phone;

    public PhoneCheck(String phone) {
        this.phone = phone;
    }

    /**
     * 手机号码检查
     */
    protected String phone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return "请输入您的手机号！";
        }
        if (phone.length() != 11) {
            return "请输入正确的手机号！";
        }
        return null;
    }

    @Override
    public String getCheckInfo() {
        return phone(phone);
    }
}
