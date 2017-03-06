package com.emiancang.emiancang.eventbean;

/**
 * Created by 22310 on 2016/12/14.
 */

public class EnterpriseAccountTransfer {

    public final static int ENTERPRISENAME = 0;
    public final static int ENTERPRISETYPE = 1;
    public final static int ENTERPRISELOCATION = 2;
    public final static int ENTERPRISESITE = 3;
    public final static int ENTERNUMBER = 4;

    private String name;
    private int type;
    private String number;

    public EnterpriseAccountTransfer(String name, int type, String number) {
        this.name = name;
        this.type = type;
        this.number = number;
    }

    public EnterpriseAccountTransfer(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
