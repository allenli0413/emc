package com.zwyl.sql;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.litesuits.orm.db.assit.QueryBuilder;

import java.util.ArrayList;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 处理数据库，抽象出一些方法，替换其他开源数据库会比较方便
 * @date 2015/1/28 16:40
 */
public class LiteSql {

    public static String TAG = "LiteSql";

    private Context mContext;

    public LiteSql(Context context) {
        mContext = context;
    }

    /**
     * 获取默认数据库
     */
    private DataBase getDataBase() {
        return LiteOrm.newInstance(mContext, "base.db");
    }


    /**
     * 根据关键字查询数据
     *
     * @param cls   查询的实体类
     * @param key   　查询数据库key
     * @param value 对应查询的ｖａｌｕｅ
     * @return 所有查询数据
     */
    public <T> ArrayList<T> query(Class<T> cls, String key, String value) {
        DataBase dataBase = getDataBase();
        QueryBuilder queryBuilder = QueryBuilder.get(cls);
        queryBuilder.where(key + "=?", new String[]{value});
        return dataBase.query(queryBuilder);
    }

    /**
     * 根据数据类型，查询所有数据
     *
     * @param cls 　查询的实体类
     * @return 所有数据
     */
    public <T> ArrayList<T> query(Class<T> cls) {
        DataBase dataBase = getDataBase();
        return dataBase.queryAll(cls);
    }

    /**
     * @param cls 　查询的实体类
     * @param id  　唯一id
     * @return 查询对应的实体
     */
    public <T> T query(Class<T> cls, long id) {
        DataBase dataBase = getDataBase();
        return dataBase.queryById(id, cls);
    }

    /**
     * 根据查询的实体类，查询所有个数
     */
    public <T> long queryCount(Class<T> cls) {
        DataBase dataBase = getDataBase();
        return dataBase.queryCount(cls);
    }


    /**
     * 插入数据集合
     *
     * @param list 要插入的数据集合
     */
    public <T> void insert(ArrayList<T> list) {
        DataBase dataBase = getDataBase();
        dataBase.insert(list);
    }

    /**
     * 插入单个数据
     *
     * @param data 对应的数据
     */
    public <T> void insert(T data) {
        DataBase dataBase = getDataBase();
        dataBase.insert(data);
    }

    /**
     * 删除对应的数据
     * @param data 对应的数据
     */
    public <T> void delete(T data){
        DataBase dataBase = getDataBase();
        dataBase.delete(data);
    }

    /**
     * 删除对应的数据
     * @param list 对应的数据集合
     */
    public <T> void delete(ArrayList<T> list){
        DataBase dataBase = getDataBase();
        dataBase.delete(list);
    }


    /**
     * 删除制定类型所有数据
     *
     */
    public <T>void deleteAll(Class<T> cls){
        DataBase dataBase = getDataBase();
        dataBase.deleteAll(cls);
    }

    /**
     * 更新对应的数据，如果没有，则创建
     * @param data 对应的数据
     */
    public <T> void update(T data){
        DataBase dataBase = getDataBase();
        dataBase.save(data);
    }

    /**
     * 更新对应的数据，如果没有，则创建
     * @param list 对应的数据
     */
    public <T> void update(ArrayList<T> list){
        DataBase dataBase = getDataBase();
        dataBase.save(list);
    }

}
