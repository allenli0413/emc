package com.emiancang.emiancang.bean;

import java.io.Serializable;

/**
 * Created by 22310 on 2016/11/8.
 */

public class IndentDetailsTailEntity implements Serializable {
    private String id;
    private String name;
    private String date;
    private boolean isArrive;

    public IndentDetailsTailEntity() {
    }

    public IndentDetailsTailEntity(String id, String name, String date, boolean isArrive) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.isArrive = isArrive;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isArrive() {
        return isArrive;
    }

    public void setArrive(boolean arrive) {
        isArrive = arrive;
    }
}
