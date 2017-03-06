package com.emiancang.emiancang.uitl;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuanyueqing on 2016/6/24.
 *
 * 金额输入格式
 */
public class EditInputFilter implements InputFilter {
    /**
     * 最大值取一百万啦
     */
    public static double MAX_VALUE = 100000;
    /**
     * 最大数字，我们取int型最大值
     */
//    public static final int MAX_VALUE = 2147483647;
    /**
     * 小数点后的数字的位数
     */
    public static int PONTINT_LENGTH = 2;
    Pattern p;
    public EditInputFilter(){
        p = Pattern.compile("[0-9]*");   //除数字外的其他的
    }
    public EditInputFilter(double MAX_VALUE){
        p = Pattern.compile("[0-9]*");   //除数字外的其他的
        this.MAX_VALUE = MAX_VALUE;
    }
    public EditInputFilter(int point_length){
        p = Pattern.compile("[0-9]*");   //除数字外的其他的
        this.PONTINT_LENGTH = point_length;
    }
    /**
     *  source    新输入的字符串
     *  start    新输入的字符串起始下标，一般为0
     *  end    新输入的字符串终点下标，一般为source长度-1
     *  dest    输入之前文本框内容
     *  dstart    原内容起始坐标，一般为0
     *  dend    原内容终点坐标，一般为dest长度-1
     */
    @Override
    public CharSequence filter(CharSequence src, int start, int end,
                               Spanned dest, int dstart, int dend) {
        String oldtext =  dest.toString();
//验证删除等按键
        if ("".equals(src.toString())) {
            return null;
        }
//验证非数字或者小数点的情况
        Matcher m = p.matcher(src);
        if(oldtext.equals("") && src.equals(".")){
                return "";
        }
        if(oldtext.contains(".")){
//已经存在小数点的情况下，只能输入数字
            if(!m.matches()){
                return null;
            }
        }else{
//未输入小数点的情况下，可以输入小数点和数字
            if(!m.matches() && src.equals(".") ){
                return null;
            }
            if(!m.matches() && !src.equals(".")){
                return "";
            }
        }
//验证输入金额的大小
        if(!src.toString().equals("")){
            double dold = Double.parseDouble(oldtext+src.toString());
            if((dest.toString()+src.toString()).equals("0"))
                return dest.subSequence(dstart, dend);
            if(dold > MAX_VALUE){
                return dest.subSequence(dstart, dend);
            }else if(dold == MAX_VALUE){
                if(src.toString().equals(".")){
                    return dest.subSequence(dstart, dend);
                }
            }
        }
//验证小数位精度是否正确
        if(oldtext.contains(".")){
            int index = oldtext.indexOf(".");
            int len = dend - index;
//小数位只能2位
            if(len > PONTINT_LENGTH){
                CharSequence newText = dest.subSequence(dstart, dend);
                return newText;
            }
        }
        return dest.subSequence(dstart, dend) +src.toString();
    }
}