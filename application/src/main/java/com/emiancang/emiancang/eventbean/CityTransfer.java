package com.emiancang.emiancang.eventbean;

/**
 * Created by 22310 on 2016/12/12.
 */

public class CityTransfer {
    private int type;//0,1
    private String name;
    private String id;

    public CityTransfer(int type, String name, String id) {
        this.type = type;
        this.name = name;
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
