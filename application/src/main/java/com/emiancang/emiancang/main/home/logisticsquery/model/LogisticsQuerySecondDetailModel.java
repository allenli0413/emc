package com.emiancang.emiancang.main.home.logisticsquery.model;

/**
 * Created by yuanyueqing on 2017/2/23.
 */

public class LogisticsQuerySecondDetailModel {
    /**
     * {
     "result_code": 200,
     "result_msg": "OK",
     "result_info": [
     serviceCustName:		 				// 企业名称
     statrAddr:      							// 起运地
     statrAddrName:	 							// 起运地名称
     endAddr:       							// 到达地
     endAddrName:	 							// 止运地名称
     servicePrice：   							// 收费标准
     transportcapacity:    400.000000  		// 运输能力--重量	   				   transportation:		   1:公路，2：铁路  // 运输方式
     startDate：     							// 有效时间起
     endDate：       							// 有效时间止

     eSjzcXm：     							// 联系人
     eSjzcSjh：       							// 联系电话

     companyAddress：     					// 企业地址
     groupContactPhone：     					// 联系电话
     faxNbr：             						//传真
     postCode：     							// 邮政编码
     email：       							// 电子邮件
     ]
     }
     */

    private String serviceCustName;
    private String statrAddr;
    private String statrAddrName;
    private String endAddr;
    private String endAddrName;
    private String servicePrice;
    private String transportcapacity;
    private String transportation;
    private String startDate;
    private String endDate;
    private String eSjzcXm;
    private String eSjzcSjh;
    private String companyAddress;
    private String groupContactPhone;
    private String faxNbr;
    private String postCode;
    private String email;

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndAddr() {
        return endAddr;
    }

    public void setEndAddr(String endAddr) {
        this.endAddr = endAddr;
    }

    public String getEndAddrName() {
        return endAddrName;
    }

    public void setEndAddrName(String endAddrName) {
        this.endAddrName = endAddrName;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String geteSjzcSjh() {
        return eSjzcSjh;
    }

    public void seteSjzcSjh(String eSjzcSjh) {
        this.eSjzcSjh = eSjzcSjh;
    }

    public String geteSjzcXm() {
        return eSjzcXm;
    }

    public void seteSjzcXm(String eSjzcXm) {
        this.eSjzcXm = eSjzcXm;
    }

    public String getFaxNbr() {
        return faxNbr;
    }

    public void setFaxNbr(String faxNbr) {
        this.faxNbr = faxNbr;
    }

    public String getGroupContactPhone() {
        return groupContactPhone;
    }

    public void setGroupContactPhone(String groupContactPhone) {
        this.groupContactPhone = groupContactPhone;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getServiceCustName() {
        return serviceCustName;
    }

    public void setServiceCustName(String serviceCustName) {
        this.serviceCustName = serviceCustName;
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

    public String getStatrAddr() {
        return statrAddr;
    }

    public void setStatrAddr(String statrAddr) {
        this.statrAddr = statrAddr;
    }

    public String getStatrAddrName() {
        return statrAddrName;
    }

    public void setStatrAddrName(String statrAddrName) {
        this.statrAddrName = statrAddrName;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public String getTransportcapacity() {
        return transportcapacity;
    }

    public void setTransportcapacity(String transportcapacity) {
        this.transportcapacity = transportcapacity;
    }
}
