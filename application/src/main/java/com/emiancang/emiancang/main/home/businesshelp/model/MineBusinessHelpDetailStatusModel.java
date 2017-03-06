package com.emiancang.emiancang.main.home.businesshelp.model;

import java.util.List;

/**
 * Created by yuanyueqing on 2016/11/9.
 */

public class MineBusinessHelpDetailStatusModel {
    /**
     * {
     "result_code": 200,
     "result_msg": "OK",
     "result_info": {
     ecgxq：{
     eCgxqType[明确返回值名称]: 	0：购买需求，1：销售需求//需求类型
     eCgxqAppxqxq：  				   //需求内容
     eCgxqCjsj:		2016-11-17 14:29		//创建时间

     },
     efkxxList:[
     {
     eFkxxFksj：			2016-11-16 00:00:00//处理时间	date类型
     }
     ...
     ]

     },
     }
     */

    private Content ecgxq;
    private List<EfxkkList> efkxxList;

    public Content getEcgxq() {
        return ecgxq;
    }

    public void setEcgxq(Content ecgxq) {
        this.ecgxq = ecgxq;
    }

    public List<EfxkkList> getEfkxxList() {
        return efkxxList;
    }

    public void setEfkxxList(List<EfxkkList> efkxxList) {
        this.efkxxList = efkxxList;
    }

    public class Content{
        private String eCgxqType;
        private String eCgxqAppxqxq;
        private String eCgxqCjsj;

        public String geteCgxqAppxqxq() {
            return eCgxqAppxqxq;
        }

        public void seteCgxqAppxqxq(String eCgxqAppxqxq) {
            this.eCgxqAppxqxq = eCgxqAppxqxq;
        }

        public String geteCgxqCjsj() {
            return eCgxqCjsj;
        }

        public void seteCgxqCjsj(String eCgxqCjsj) {
            this.eCgxqCjsj = eCgxqCjsj;
        }

        public String geteCgxqType() {
            return eCgxqType;
        }

        public void seteCgxqType(String eCgxqType) {
            this.eCgxqType = eCgxqType;
        }
    }

    public class EfxkkList{
        private String eFkxxFksj;
        private String eFkxxFknr;
        private String eFkxxId;

        public String geteFkxxFknr() {
            return eFkxxFknr;
        }

        public void seteFkxxFknr(String eFkxxFknr) {
            this.eFkxxFknr = eFkxxFknr;
        }

        public String geteFkxxId() {
            return eFkxxId;
        }

        public void seteFkxxId(String eFkxxId) {
            this.eFkxxId = eFkxxId;
        }

        public String geteFkxxFksj() {
            return eFkxxFksj;
        }

        public void seteFkxxFksj(String eFkxxFksj) {
            this.eFkxxFksj = eFkxxFksj;
        }
    }
}
