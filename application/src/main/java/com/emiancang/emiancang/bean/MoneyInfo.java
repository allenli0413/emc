package com.emiancang.emiancang.bean;

import java.util.List;

/**
 * Created by 22310 on 2016/11/23.
 */

public class MoneyInfo {


    /**
     * backMoney : 2000.00
     * list : [{"ezHblid":null,"ezHbluid":null,"ezHbType":"3","ezHblmoney":2000,"ezHblcreateTime":"2016-11-22 10:21:58","ezHblcreateUid":null,"ezHbObj":"66211141127","dicValue":"支付红包"}]
     */

    private String backMoney;
    /**
     * ezHblid : null
     * ezHbluid : null
     * ezHbType : 3
     * ezHblmoney : 2000
     * ezHblcreateTime : 2016-11-22 10:21:58
     * ezHblcreateUid : null
     * ezHbObj : 66211141127
     * dicValue : 支付红包
     */

    private List<ListBean> list;

    public String getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(String backMoney) {
        this.backMoney = backMoney;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private Object ezHblid;
        private Object ezHbluid;
        private String ezHbType;
        private int ezHblmoney;
        private String ezHblcreateTime;
        private Object ezHblcreateUid;
        private String ezHbObj;
        private String dicValue;

        public Object getEzHblid() {
            return ezHblid;
        }

        public void setEzHblid(Object ezHblid) {
            this.ezHblid = ezHblid;
        }

        public Object getEzHbluid() {
            return ezHbluid;
        }

        public void setEzHbluid(Object ezHbluid) {
            this.ezHbluid = ezHbluid;
        }

        public String getEzHbType() {
            return ezHbType;
        }

        public void setEzHbType(String ezHbType) {
            this.ezHbType = ezHbType;
        }

        public int getEzHblmoney() {
            return ezHblmoney;
        }

        public void setEzHblmoney(int ezHblmoney) {
            this.ezHblmoney = ezHblmoney;
        }

        public String getEzHblcreateTime() {
            return ezHblcreateTime;
        }

        public void setEzHblcreateTime(String ezHblcreateTime) {
            this.ezHblcreateTime = ezHblcreateTime;
        }

        public Object getEzHblcreateUid() {
            return ezHblcreateUid;
        }

        public void setEzHblcreateUid(Object ezHblcreateUid) {
            this.ezHblcreateUid = ezHblcreateUid;
        }

        public String getEzHbObj() {
            return ezHbObj;
        }

        public void setEzHbObj(String ezHbObj) {
            this.ezHbObj = ezHbObj;
        }

        public String getDicValue() {
            return dicValue;
        }

        public void setDicValue(String dicValue) {
            this.dicValue = dicValue;
        }
    }
}
