package com.emiancang.emiancang.main.home.qualityquery.model;

import java.io.Serializable;

/**
 * Created by yuanyueqing on 2016/11/11.
 */

public class FilterGridviewModel implements Serializable {

    String areaId ;

    String areaName;

    boolean isSelected;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
