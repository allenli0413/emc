package com.emiancang.emiancang.serch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zwyl.App;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.qq.QQConstants;
import com.emiancang.emiancang.wxapi.WeichatShareManager;


/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 分享    新浪 微信 QQ分享  禁止新浪 5.0会出错   extends SinaShareActivity
 * @date 15-9-21 上午11:12
 */
public class ShareActivity extends TitleActivity {


    WeichatShareManager weichatShareManager;//为什么不写成单例模式？？？
    Tencent mTencent;

    /**
     * 分享图片到微信好友（朋友圈）
     */
    public void shareWechat(Bitmap bitmap, boolean mount_flag) {
        WeichatShareManager weichatShareManager = new WeichatShareManager(getActivity());
        weichatShareManager.shareImageBitmap(bitmap, mount_flag);
//          weichatShareManager.shareText("test",mount_flag);
    }

    /**
     * 分享应用(下载地址)到微信好友（朋友圈） 网页
     * moment_flag 是否分享到朋友圈
     */
    public void shareWechat(String webpageUrl, String title,
                            String description, boolean moment_flag) {
        WeichatShareManager weichatShareManager = new WeichatShareManager(getActivity());
        weichatShareManager.shareWebPage(webpageUrl, title, description, moment_flag);
    }

    /**
     * 默认分享
     *
     * @param moment_flag 是否分享到朋友圈
     */
    public void shareWechat(boolean moment_flag) {
        WeichatShareManager weichatShareManager = new WeichatShareManager(getActivity());
        String webpageUrl = "http://www.ebtang.com/";
        String title = getString(R.string.app_name);
        String description = getString(R.string.app_name);
        weichatShareManager.shareWebPage(webpageUrl, title, description, moment_flag);
    }

    public void shareWechat(String text, boolean mount_flag) {
        WeichatShareManager weichatShareManager = new WeichatShareManager(getActivity());
        weichatShareManager.shareText(text, mount_flag);
    }

    /**
     * 分享图片到新浪微博
     */
//    public void shareSina(Bitmap bitmap) {
//        shareImage(bitmap);
//    }
//
//    public void shareSina(String text) {
//        shareText(text);
//    }

    /**
     * 分享到QQ  根据具体需求再做改动☆☆
     */

    //分享图文消息
    public void shareQQ() {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享的类型。图文分享(普通分享)填Tencent.SHARE_TO_QQ_TYPE_DEFAULT
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");//分享的消息摘要，最长40个字。
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.qq.com/news/1.html");//这条分享消息被好友点击后的跳转URL。
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用222222");//手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替

        shareQQ(params);
    }

    //纯图片
    public void shareQQ(String url) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, url);
        shareQQ(params);
    }

    //分享音乐
    public void shareQQ(String url, String str2, String str3) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.qq.com/news/1.html");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, "音乐链接");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用222222");
        shareQQ(params);
    }

    /**
     * @param appname 应用名称
     * @param url     应用下载地址
     * @param str2    要分享的标题
     * @param str3    要分享的摘要
     */
    //分享应用
    public void shareQQ(String appname, String url, String str2, String str3) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, str2);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, str3);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appname);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, 0);
        shareQQ(params);
    }
    /**
     * @param appname 应用名称
     * @param url     应用下载地址
     * @param str2    要分享的标题
     * @param str3    要分享的摘要
     */
    //分享应用
    public void shareQQQone(String appname, String url, String str2, String str3) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, str2);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, str3);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appname);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        shareQQ(params);
    }

    public void shareQQ(Bundle params) {
        mTencent = Tencent.createInstance(QQConstants.APP_ID, App.getContext());
        //分享额外选项，两种类型可选（默认是不隐藏分享到QZone按钮且不自动打开分享到QZone的对话框）：
        // QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN，分享时自动打开分享到QZone的对话框。
        // QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE，分享时隐藏分享到QZone按钮
        mTencent.shareToQQ(getActivity(), params, iUiListener);
    }

    IUiListener iUiListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
//            EventBus.getDefault().post(new WeiChatShareInfo(true));
        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != mTencent)
            mTencent.onActivityResult(requestCode, resultCode, data);
        else
            super.onActivityResult(requestCode, resultCode, data);
    }

}
