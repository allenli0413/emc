package com.emiancang.emiancang.bean;

/**
 * Created by 22310 on 2016/12/5.
 */

public class MessageInfo {


    /**
     * sys_msg :
     * trade_msg :
     * order_msg :
     * pay_msg :
     * goods_power_msg :
     * financing_msg :
     */

    private String sys_msg;
    private String trade_msg;
    private String order_msg;
    private String financing_msg;
    private String id;
    private String type;
    private String title;
    private String login_remind;
    private String invitedcode_msg;


    public String getSys_msg() {
        return sys_msg;
    }

    public void setSys_msg(String sys_msg) {
        this.sys_msg = sys_msg;
    }

    public String getTrade_msg() {
        return trade_msg;
    }

    public void setTrade_msg(String trade_msg) {
        this.trade_msg = trade_msg;
    }

    public String getOrder_msg() {
        return order_msg;
    }

    public void setOrder_msg(String order_msg) {
        this.order_msg = order_msg;
    }

    public String getFinancing_msg() {
        return financing_msg;
    }

    public void setFinancing_msg(String financing_msg) {
        this.financing_msg = financing_msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogin_remind() {
        return login_remind;
    }

    public void setLogin_remind(String login_remind) {
        this.login_remind = login_remind;
    }

    public String getInvitedcode_msg() {
        return invitedcode_msg;
    }

    public void setInvitedcode_msg(String invitedcode_msg) {
        this.invitedcode_msg = invitedcode_msg;
    }
}
