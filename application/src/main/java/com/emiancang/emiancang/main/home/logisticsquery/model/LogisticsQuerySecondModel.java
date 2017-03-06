package com.emiancang.emiancang.main.home.logisticsquery.model;

/**
 * Created by yuanyueqing on 2017/2/22.
 */

public class LogisticsQuerySecondModel {
    /**
     * {
     "result_code": 200,
     "result_msg": "OK",
     "result_info": [
     {
     serviceId:	 				// 物流线路id
     serviceName:	 			// 线路名称
     serviceCustName:          // 发布公司名称
     transportation:	1:公路，2：铁路	 // 运输方式
     servicePrice：   			// 价格
     startDate：     			// 有效时间起
     endDate：       			// 有效时间止
     },
     ]
     }
     */
    private String serviceId;
    private String serviceName;
    private String serviceCustName;
    private String transportation;
    private String servicePrice;
    private String startDate;
    private String endDate;

    private String custName;
    private String companyAddress;
    private String startAddrName;
    private String endAddrName;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getServiceCustName() {
        return serviceCustName;
    }

    public void setServiceCustName(String serviceCustName) {
        this.serviceCustName = serviceCustName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getEndAddrName() {
        return endAddrName;
    }

    public void setEndAddrName(String endAddrName) {
        this.endAddrName = endAddrName;
    }

    public String getStartAddrName() {
        return startAddrName;
    }

    public void setStartAddrName(String startAddrName) {
        this.startAddrName = startAddrName;
    }
}
