package com.emiancang.emiancang.uitl;

import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * Created by yuanyueqing on 2017/1/22.
 */

public class DoubiUtils {
    public static String hehe(String distantce){
        String result = "";
        if(TextUtils.isEmpty(distantce)){
            return  result;
        }
        if(Integer.parseInt(distantce) > 1000){
            double distance_d = Double.parseDouble(distantce) / 1000;
            DecimalFormat df = new DecimalFormat("######0.00");
            result = df.format(distance_d) + "km";
        } else {
            result = distantce + "m";
        }
        return  result;
    }
    public static String hehe(int distantce){
        String result = "";
        if(distantce > 1000){
            double distance_d = Double.parseDouble(distantce + "") / 1000;
            DecimalFormat df = new DecimalFormat("######0.00");
            result = df.format(distance_d) + "km";
        } else {
            result = distantce + "m";
        }
        return  result;
    }
}
