package com.emiancang.emiancang.bean;

import java.io.Serializable;

/**
 * Created by 22310 on 2016/11/8.
 */

public class WarnEntity implements Serializable {

    /**
     * id : 1
     * title : 系统消息
     * sysContent : 系统消息测试
     * createrName : admin
     * createTime : 2016-11-30 12:00:45
     * isdeleted : 0
     */

    private String id;
    private String title;
    private String sysContent;
    private String createrName;
    private String createTime;
    private int isdeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSysContent() {
        return sysContent;
    }

    public void setSysContent(String sysContent) {
        this.sysContent = sysContent;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(int isdeleted) {
        this.isdeleted = isdeleted;
    }
}
