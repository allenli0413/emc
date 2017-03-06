package com.emiancang.emiancang.main.home.deliveryorder.model;

import java.io.Serializable;

/**
 * Author: liyh
 * E-mail: liyh@zhiwyl.com
 * Phone: 15033990609
 * Date: 2017/2/24  Time: 18:43
 * Description:
 */

public class CustomAgreementDetailModel implements Serializable {
    public String name;
    public String data;

    public CustomAgreementDetailModel(String name, String data) {
        this.name = name;
        this.data = data;
    }
}
