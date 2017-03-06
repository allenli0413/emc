package com.emiancang.emiancang.main.home.model;

import java.io.Serializable;

/**
 * Created by yuanyueqing on 2016/12/19.
 */

public class ScanModel implements Serializable {
    /**
     * {"hyJysjNm":null,
     * "custId":null,
     * "hyJysjBh":null,
     * "hyJysjPh":null,
     * "hyJysjTm":null,
     * "hyJysjZmdj":null,
     * "hyJysjMz":null,
     * "hyJysjJz":null,
     * "hyJysjHcl":null,
     * "hyJysjPz":null,
     * "hyJysjPj":null,
     * "hyJysjCdj":null,
     * "hyJysjMklzj":null,
     * "hyJysjJglx":null,
     * "hyJysjYph":null,
     * "hyJysjCpmc":null,
     * "hyJysjScrq":null,
     * "hyJysjJyjgmc":null,
     * "hyJysjCdz":null,
     * "hyJysjMklz":null,
     * "hyJysjHzl":null,
     * "hyJysjZgzl":null,
     * "hyJysjMkldc":null,
     * "hyJysjYsj":null,
     * "hyJysjCdzqdzs":null,
     * "hyJysjDqzs":null,
     * "hyJysjFsl":null,
     * "hyJysjHd":null,
     * "hyJysjDlsc":null,
     * "hyJysjDlbqd":null,
     * "hyJysjCsxs":null,
     * "hyJysjCd":null,
     * "hyJysjJyrq":null,
     * "hyJysjBz":null,
     * "hyJysjTjsj":null,
     * "hyJysjJgnd":null}
     */
    private String custId;
    private String hyJysjPh;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getHyJysjPh() {
        return hyJysjPh;
    }

    public void setHyJysjPh(String hyJysjPh) {
        this.hyJysjPh = hyJysjPh;
    }
}
