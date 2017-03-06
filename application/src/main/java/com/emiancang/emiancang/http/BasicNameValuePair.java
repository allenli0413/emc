package com.emiancang.emiancang.http;


/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: TODO
 * @date 16-9-2 上午10:45
 */
public class BasicNameValuePair {
    private String name;
    private String value;

    public BasicNameValuePair(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
