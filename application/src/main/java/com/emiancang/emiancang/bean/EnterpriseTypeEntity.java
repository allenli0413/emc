package com.emiancang.emiancang.bean;

import com.emiancang.emiancang.http.HttpResult;
import com.emiancang.emiancang.http.ShowItem;
import com.emiancang.emiancang.http.ShowService;

import java.util.List;

import retrofit2.http.Field;
import rx.Observable;

/**
 * Created by 22310 on 2016/11/8.
 */

public class EnterpriseTypeEntity {
    private String id;
    private String enterprise_type_name;

    public EnterpriseTypeEntity() {
    }

    public EnterpriseTypeEntity(String id, String enterprise_type_name) {
        this.id = id;
        this.enterprise_type_name = enterprise_type_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnterprise_type_name() {
        return enterprise_type_name;
    }

    public void setEnterprise_type_name(String enterprise_type_name) {
        this.enterprise_type_name = enterprise_type_name;
    }

}
