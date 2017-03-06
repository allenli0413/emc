package com.emiancang.emiancang.main.home.findmoney.model;

import java.util.List;

/**
 * Created by yuanyueqing on 2016/11/8.
 */

public class FindMoneyDetailStatusModel {
    /**
     * {
     "result_code": 200,
     "result_msg": "OK",
     "result_info": {
     content：{
     eJrsqJrfwlx:"01"     //需求类型00:物流需求01:采购需求02:金融需求
     eJrsqSqsj："2016-10-10 10:10"			//申请时间	字符串类型
     eJrsqNr： "内容。。。"   			    //需求内容
     },
     efxkkList:[
     {
     eFkxxFksj：					 //处理时间	date类型
     eFkxxFknr："反馈内容。。。"		 //处理结果
     }
     ...
     ]

     },
     }
     */

    private Content content;
    private List<EfxkkList> efxkkList;

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public List<EfxkkList> getEfxkkList() {
        return efxkkList;
    }

    public void setEfxkkList(List<EfxkkList> efxkkList) {
        this.efxkkList = efxkkList;
    }

    class Content{
        private String eJrsqJrfwlx;
        private String eJrsqSqsj;
        private String eJrsqNr;

        public String geteJrsqJrfwlx() {
            return eJrsqJrfwlx;
        }

        public void seteJrsqJrfwlx(String eJrsqJrfwlx) {
            this.eJrsqJrfwlx = eJrsqJrfwlx;
        }

        public String geteJrsqNr() {
            return eJrsqNr;
        }

        public void seteJrsqNr(String eJrsqNr) {
            this.eJrsqNr = eJrsqNr;
        }

        public String geteJrsqSqsj() {
            return eJrsqSqsj;
        }

        public void seteJrsqSqsj(String eJrsqSqsj) {
            this.eJrsqSqsj = eJrsqSqsj;
        }
    }

    public class EfxkkList{
        private String eFkxxFksj;
        private String eFkxxFknr;

        public String geteFkxxFknr() {
            return eFkxxFknr;
        }

        public void seteFkxxFknr(String eFkxxFknr) {
            this.eFkxxFknr = eFkxxFknr;
        }

        public String geteFkxxFksj() {
            return eFkxxFksj;
        }

        public void seteFkxxFksj(String eFkxxFksj) {
            this.eFkxxFksj = eFkxxFksj;
        }
    }
}
