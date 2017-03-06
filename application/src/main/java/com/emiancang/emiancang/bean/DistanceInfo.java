package com.emiancang.emiancang.bean;

/**
 * Created by 22310 on 2016/11/28.
 */

public class DistanceInfo {
    private String id;
    private String name;
    private boolean select;

    public DistanceInfo(String id, String name, boolean select) {
        this.id = id;
        this.name = name;
        this.select = select;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
