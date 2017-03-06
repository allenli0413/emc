package com.emiancang.emiancang.bean;

/**
 * Created by 22310 on 2016/11/21.
 */

public class ForgetPassword {

    /**
     * resMsg : 发送成功！
     * resCode : 1
     */

    private String resMsg;
    private String resCode;
    private String logoImg;
    /**
     * isRegister : 0
     */

    private String isRegister;

    /**
     * isRegister : 0
     */

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }


    public String getIsRegister() {
        return isRegister;
    }

    public void setIsRegister(String isRegister) {
        this.isRegister = isRegister;
    }

    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg;
    }
}
