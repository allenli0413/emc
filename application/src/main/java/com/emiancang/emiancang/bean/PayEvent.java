package com.emiancang.emiancang.bean;

/**
 * @author weixy@zhiwy.com
 * @version V1.0
 * @Description: com.zwy.data
 * @date 2015/12/4 14:02
 */
public class PayEvent {
    boolean isSuccessed;

    public PayEvent() {
        this.isSuccessed = true;
    }

    public PayEvent(boolean isSuccessed) {
        this.isSuccessed = isSuccessed;
    }

    public boolean isSuccessed() {
        return isSuccessed;
    }
}
