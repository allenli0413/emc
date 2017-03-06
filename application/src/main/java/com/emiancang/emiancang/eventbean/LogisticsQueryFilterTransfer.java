package com.emiancang.emiancang.eventbean;

import java.io.Serializable;

/**
 * Created by yuanyueqing on 2017/2/23.
 */

public class LogisticsQueryFilterTransfer implements Serializable {

    String startDate;
    String endDate;
    String startAddressId;
    String startAddressName;
    String endAddressId;
    String endAddressName;
    String lowPrice;
    String highPrice;
    boolean road = false;
    boolean train = false;

    public LogisticsQueryFilterTransfer(String startAddressId, String endAddressId, String startAddressName, String endAddressName, String endDate, String highPrice, String lowPrice, boolean road, String startDate, boolean train) {
        this.startAddressId = startAddressId;
        this.endAddressId = endAddressId;
        this.startAddressName = startAddressName;
        this.endAddressName = endAddressName;
        this.endDate = endDate;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.road = road;
        this.startDate = startDate;
        this.train = train;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public boolean isRoad() {
        return road;
    }

    public void setRoad(boolean road) {
        this.road = road;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public boolean isTrain() {
        return train;
    }

    public void setTrain(boolean train) {
        this.train = train;
    }

    public String getEndAddressId() {
        return endAddressId;
    }

    public void setEndAddressId(String endAddressId) {
        this.endAddressId = endAddressId;
    }

    public String getEndAddressName() {
        return endAddressName;
    }

    public void setEndAddressName(String endAddressName) {
        this.endAddressName = endAddressName;
    }

    public String getStartAddressId() {
        return startAddressId;
    }

    public void setStartAddressId(String startAddressId) {
        this.startAddressId = startAddressId;
    }

    public String getStartAddressName() {
        return startAddressName;
    }

    public void setStartAddressName(String startAddressName) {
        this.startAddressName = startAddressName;
    }
}
