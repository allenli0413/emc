package com.emiancang.emiancang.bean;

import java.io.Serializable;

/**
 * Created by 22310 on 2016/11/22.
 */

public class City implements Serializable {

    /**
     * areaName : 北京
     * areaId : 100
     */

    private String areaName;
    private int areaId;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }
}
