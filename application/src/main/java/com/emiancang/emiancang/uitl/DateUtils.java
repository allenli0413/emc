package com.emiancang.emiancang.uitl;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 22310 on 2016/4/20.
 */
public class DateUtils {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT1 = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sf = null;

    /*获取系统时间 格式为："yyyy/MM/dd "*/
    public static String getCurrentDate() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return sf.format(d);
    }

    /*时间戳转换成字符窜*/
    public static String getDateToString(long time) {

        String date = new java.text.SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new java.util.Date(time * 1000));
        return date;
    }

    /*时间戳转换成字符窜*/
    public static String getDateToString(String timestamp) {
        if (!TextUtils.isEmpty(timestamp)) {
            Long mlong = Long.parseLong(timestamp);
            return getDateToString(mlong);
        } else
            return "";
    }

    /*时间戳转换成字符窜*/
    public static String getDateFromTimestamp(long timestamp) {
        return DEFAULT_DATE_FORMAT1.format(new Date(timestamp * 1000));
    }

    /*时间戳转换成字符窜*/
    public static String getDateFromString(String timestamp) {
        if (!TextUtils.isEmpty(timestamp)) {
            Long mlong = Long.parseLong(timestamp);
            return getDateFromTimestamp(mlong);
        } else
            return "";
    }

    /*时间戳转换成字符窜*/
    public static String getDateToString2(long time) {

        String date = new java.text.SimpleDateFormat("yyyy.MM.dd").format(new java.util.Date(time * 1000));
        return date;
    }

    /*将字符串转为时间戳*/
    public static long getStringToDate(String time) {
        sf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 获取两个时间的时间查 如1天2小时30分钟
     */
    public static String getDatePoor(String endDate, String nowDate) {
        sf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date end = new Date();
        Date now = new Date();
        try {
            end = sf.parse(endDate);
            now = sf.parse(nowDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long nn = 24 * 60 * 60 * 30 * 365;
        long nm = 24 * 60 * 60 * 30;
        long nd = 24 * 60 * 60;
        long nh = 60 * 60;
        long nmm = 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = (now.getTime() - end.getTime()) / 1000;

        long year = diff / nn;
        if (year > 0) {
            return year + "年前";
        } else {
            long month = diff / nm;
            if (month > 0) {
                return month + "月前";
            } else {
                long day = diff / nd;
                if (day > 0) {
                    return day + "天前";
                } else {
                    long hour = diff / nh;
                    if (hour > 0) {
                        return hour + "小时前";
                    } else {
                        long min = diff / nmm;
                        if (min > 0) {
                            return min + "分钟前";
                        } else {
                            return "刚刚";
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取两个时间的时间查 如1天2小时30分钟 与当前时间相距
     */
    public static String getDatePoor(long endDate) {
        String date = getDateToString(endDate);
        return getDatePoor(date, getCurrentDate());
    }

}
