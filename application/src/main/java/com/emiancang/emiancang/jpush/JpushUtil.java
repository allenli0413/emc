package com.emiancang.emiancang.jpush;

import android.os.Handler;

import com.zwyl.Logger;
import com.emiancang.emiancang.App;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 极光工具
 * @date 15-9-9 上午10:48
 */
public enum JpushUtil {
    INSTANCES;

    private static final int MSG_SET_ALIAS = 1001;

    public static JpushUtil getInstances(){
        return INSTANCES;
    }


    public void setAlias(String alias){
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));

    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
            }
            Logger.d("Jpush", logs);
        }

    };

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
//                    Log.d(TAG, "Set alias in handler.");
                    JPushInterface.setAliasAndTags(App.getContext(), (String) msg.obj, null, mAliasCallback);
                    break;
                default:
//                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };
}
