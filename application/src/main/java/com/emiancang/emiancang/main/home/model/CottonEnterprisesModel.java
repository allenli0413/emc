package com.emiancang.emiancang.main.home.model;

import java.io.Serializable;

/**
 * Created by yuanyueqing on 2016/11/7.
 */

public class CottonEnterprisesModel implements Serializable {
    /**
     * {
     "page": {
     "toalPages": 2,						      //总页数
     "totalCount": 2					      //总条数
     },
     "result_code": 200,
     "result_msg": "OK",
     "result_info": [
     {
     custId:                           //企业id
     custName:xxx公司                //企业名称
     companyAddress: xxx              //企业地址
     distance：2km                    //与我的距离
     lng:                             //经度
     lat:                             //维度
     }…
     ]
     }
     */
    private String custId;
    private String custName;
    private String companyAddress;
    private String distance;
    private String lng;
    private String lat;

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
