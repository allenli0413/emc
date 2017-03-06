package com.emiancang.emiancang.jpush;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.jpushdemo.MyReceiver;
import com.litesuits.android.log.Log;
import com.zwyl.Logger;
import com.emiancang.emiancang.bean.JpushInfo;
import com.emiancang.emiancang.uitl.JsonUtil;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;

import de.greenrobot.event.EventBus;


/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 极光推送
 * @date 2015/9/23 12:58
 */
public class JpushReceiver extends MyReceiver {

    //     用户点击打开了通知
    @Override
    public void processCustomMessage(Context context, String message, String extras) {
        //处理自定义消息
        Logger.i("Jpush", "Jpush");
        //打开自定义的Activity   先进入我的页面 再进入通知页面
        Log.i("JPush", "json:extras" + extras.toString());
        JpushInfo info = JsonUtil.fromJson(extras, JpushInfo.class);

        //根据不同类型点击通知进入不同页面
        Intent intent = new Intent();
        Class clazz;
        int tag = 0;
    }

    //           接收到推送下来的通知的ID:
    @Override
    public void processCustomMessage(Context context, String extra) {
        super.processCustomMessage(context, extra);
        if (TextUtils.isEmpty(extra)) {
            return;
        }
        Log.i("JPush", "接收到推送下来的通知  json:extras" + extra.toString());
        //  key:cn.jpush.android.EXTRA, value:{"message_type":"2"}
        JpushInfo info = JsonUtil.fromJson(extra, JpushInfo.class);
        switch (info.getMessage_type()) {
            //1：系统消息，2：评论，3：赞我，4：@我的，5：新粉丝
            case 1:
                addUnreadNum(context, "unread_sys_msg");
                break;
            case 2:
                addUnreadNum(context, "unread_review");

                break;
            case 3:
                addUnreadNum(context, "unread_zan");

                break;
            case 4:
                addUnreadNum(context, "unread_atme");

                break;
            case 5:
                addUnreadNum(context, "unread_new_fans");

                break;
            default:
                break;
        }
    }

    private void addUnreadNum(Context context, String key) {
        int num_before = SharedPrefsUtil.getValue(context, key, 0);
        SharedPrefsUtil.putValue(context, key, num_before + 1);
    }

    @Override
    public void processNotification(Context context) {
        super.processNotification(context);
        //处理加小红点通知
//        EventBus.getDefault().post(new NoticeUnreadEvent(true));
    }
}
