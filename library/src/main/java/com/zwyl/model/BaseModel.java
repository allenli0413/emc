package com.zwyl.model;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 数据库需要
 * @date 2015/1/28 18:00
 */
public class BaseModel {
    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
    @Column("_id")
    private long _id;

    public long getmId() {
        return _id;
    }

    public void setmId(long id) {
        this._id = id;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "id=" + _id +
                '}';
    }
}
