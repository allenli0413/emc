package com.emiancang.emiancang.main.home.model;

import java.io.Serializable;

/**
 * Created by yuanyueqing on 2016/11/22.
 */

public class BannerModel implements Serializable {
    /**
     * {
     "resultCode": 200,
     "resultMsg": "OK",
     "resultInfo": [
     {
     ezAimg: ,          //图片地址
     ezAurl:，          //活动链接地址
     }…
     ]
     }
     */
    private String ezAid;
    private String R;
    private String ezAimg;
    private String ezAurl;
    private String ezAtitle;

    public String getEzAid() {
        return ezAid;
    }

    public void setEzAid(String ezAid) {
        this.ezAid = ezAid;
    }

    public String getEzAtitle() {
        return ezAtitle;
    }

    public void setEzAtitle(String ezAtitle) {
        this.ezAtitle = ezAtitle;
    }

    public String getR() {
        return R;
    }

    public void setR(String r) {
        R = r;
    }

    public String getEzAimg() {
        return ezAimg;
    }

    public void setEzAimg(String ezAimg) {
        this.ezAimg = ezAimg;
    }

    public String getEzAurl() {
        return ezAurl;
    }

    public void setEzAurl(String ezAurl) {
        this.ezAurl = ezAurl;
    }
}
