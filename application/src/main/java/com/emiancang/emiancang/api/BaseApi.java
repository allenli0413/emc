package com.emiancang.emiancang.api;

import android.text.TextUtils;
import android.widget.Toast;

import com.zwyl.http.Apis;
import com.emiancang.emiancang.App;
import com.emiancang.emiancang.requestcheck.RequestCheckable;
import com.emiancang.emiancang.user.UserManager;

/**
 * @author weixy@zhiwy.com
 * @version V1.0
 * @Description: com.zwyl.yinfu.api
 * @date 2015/10/14 11:57
 */
public class BaseApi extends Apis {

    {   //初始化签名参数
//        Apis.init("qixing123#");
        Apis.init("12345678");
    }

    //上传参数检测
    RequestCheckable checkable;

    public void setCheckable(RequestCheckable checkable) {
        this.checkable = checkable;
    }

    //    public static final String HOST_DEMO = "";//借用一个可用接口 测试demoapi
//    public static final String HOST_DEMO = "http://192.168.31.187:9090";//借用一个可用接口 测试demoapi
//    public static final String HOST_TEST = "http://app.hulum.cn";
    public static final String HOST_TEST = "http://test.hulum.cn";
//    public static final String HOST_TEST = "http://172.16.20.95:8080";

    public static final String HOST = HOST_TEST;

    @Override
    protected String getToken() {
        return UserManager.INSTANCE.getToken();
    }

    /**
     * 开始调用Api
     */
    public void start() {
        if (checkable == null) {
            run();
        } else {
            String checkInfo = checkable.getCheckInfo();
            if (!TextUtils.isEmpty(checkInfo)) {
                Toast.makeText(App.getContext(), checkInfo, Toast.LENGTH_SHORT).show();
            } else {
                run();
            }
        }
    }
}
