package com.emiancang.emiancang.eventbean;

/**
 * Created by 22310 on 2016/12/14.
 */

public class SYSMSGTransfer {
    private boolean type;

    public SYSMSGTransfer(boolean type) {
        this.type = type;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
