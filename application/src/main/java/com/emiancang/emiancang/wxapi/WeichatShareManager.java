package com.emiancang.emiancang.wxapi;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.Toast;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXMusicObject;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.emiancang.emiancang.App;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.uitl.ZwyContextKeeper;
import com.emiancang.emiancang.uitl.ZwyImageUtils;
import com.emiancang.emiancang.uitl.ZwyNetworkUtils;

import java.io.File;
import java.net.URL;

/**
 * 微信分享调用类
 * 
 * @author Administrator
 * 
 */
public class WeichatShareManager {
	Activity activity;
	private IWXAPI api;
	private static final int THUMB_SIZE = 110;

	/**
	 * 在设置view之前调用实例化方法
	 * 
	 * @param activity
	 */
	public WeichatShareManager(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		api = WXAPIFactory.createWXAPI(activity, WeiChatConstants.APP_ID);
		api.registerApp(WeiChatConstants.APP_ID);
	}

	String TEST_IMAGE;

	private void initImagePath() {
		try {
			TEST_IMAGE = ZwyContextKeeper.getSDCardPath() + "share.jpg";
			File file = new File(TEST_IMAGE);
			if (!file.exists()) {
				file.createNewFile();
				Bitmap pic = BitmapFactory.decodeResource(
						activity.getResources(), R.drawable.logo_real);
				ZwyImageUtils.saveMyBitmap(pic, TEST_IMAGE);
			}
		} catch (Exception t) {
			t.printStackTrace();
			TEST_IMAGE = null;
		}
	}

	/**
	 * 分享文字
	 * 
	 * @param content
	 *            分享内容
	 * @param moment_flag
	 *            是否到朋友圈
	 */
	public void shareText(final String content, final boolean moment_flag) {
		WXTextObject textObj = new WXTextObject();
		textObj.text = content;

		// 用WXTextObject对象初始化一个WXMediaMessage对象
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = textObj;
		// 发送文本类型的消息时，title字段不起作用
		// msg.title = "Will be ignored";
		msg.description = content;

		// 构造一个Req
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
		req.message = msg;
		req.scene = moment_flag ? SendMessageToWX.Req.WXSceneTimeline
				: SendMessageToWX.Req.WXSceneSession;

		// 调用api接口发送数据到微信
		sendReq(req);
	}

	/**
	 * 分享图片
	 * @param res 图片的资源id
	 * @param moment_flag 是否分享到朋友圈 true 分享到朋友圈
	 */
	public void shareImageRes(int res, final boolean moment_flag) {
		Bitmap bmp = BitmapFactory.decodeResource(activity.getResources(), res);
		shareImageBitmap(bmp, moment_flag);
	}

	/**
	 * 分享图片
	 * @param bmp 图片
	 * @param moment_flag 是否分享到朋友圈 true 分享到朋友圈
	 */
	public void shareImageBitmap(Bitmap bmp, final boolean moment_flag) {
		WXImageObject imgObj = new WXImageObject(bmp);
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj;

		Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE,
				THUMB_SIZE, true);
//		bmp.recycle();
		msg.thumbData = Util.bmpToByteArray(thumbBmp, true); // 设置缩略图

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		req.scene = moment_flag ? SendMessageToWX.Req.WXSceneTimeline
				: SendMessageToWX.Req.WXSceneSession;
		sendReq(req);
	}

	/**
	 * 分享图片
	 * @param path 图片的绝对地址
	 * @param moment_flag 是否分享到朋友圈
	 */
	public void shareImagePath(String path, final boolean moment_flag) {
		File file = new File(path);
		if (!file.exists()) {
			String tip = activity.getString(R.string.send_img_file_not_exist);
			showToast(tip + " path = " + path);
			return;
		}

		WXImageObject imgObj = new WXImageObject();
		imgObj.setImagePath(path);

		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj;

		Bitmap bmp = BitmapFactory.decodeFile(path);
		Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE,
				THUMB_SIZE, true);
		bmp.recycle();
		msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		req.scene = moment_flag ? SendMessageToWX.Req.WXSceneTimeline
				: SendMessageToWX.Req.WXSceneSession;
//		api.sendReq(req);
		sendReq(req);

	}

	/**
	 * 分享图片
	 * @param url 图片的网络地址（非本地）
	 * @param moment_flag 是否分享到朋友圈
	 */
	public void shareImageUrl(String url, final boolean moment_flag) {
		if (!ZwyNetworkUtils.isNetCanUse()) {
			showToast(activity.getString(R.string.net_error));
			return;
		}
		try {
			WXImageObject imgObj = new WXImageObject();
			imgObj.imageUrl = url;

			WXMediaMessage msg = new WXMediaMessage();
			msg.mediaObject = imgObj;

			Bitmap bmp = BitmapFactory.decodeStream(new URL(url).openStream());
			Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE,
					THUMB_SIZE, true);
			bmp.recycle();
			msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

			SendMessageToWX.Req req = new SendMessageToWX.Req();
			req.transaction = buildTransaction("img");
			req.message = msg;
			req.scene = moment_flag ? SendMessageToWX.Req.WXSceneTimeline
					: SendMessageToWX.Req.WXSceneSession;
//			api.sendReq(req);
			sendReq(req);

		} catch (Exception e) {
			showToast(activity
					.getString(R.string.send_img_url_not_exist));
			e.printStackTrace();
		}
	}

	/**
	 * 分享网络音乐
	 * @param musicUrl 音乐地址
	 * @param musicTitle 音乐标题
	 * @param musicDescription 音乐描述
	 * @param moment_flag 是否分享到朋友圈
	 */
	public void shareMusic(String musicUrl, String musicTitle,
			String musicDescription, boolean moment_flag) {
		WXMusicObject music = new WXMusicObject();
		// music.musicUrl = "http://www.baidu.com";
		music.musicUrl = musicUrl;
		// music.musicUrl="http://120.196.211.49/XlFNM14sois/AKVPrOJ9CBnIN556OrWEuGhZvlDF02p5zIXwrZqLUTti4o6MOJ4g7C6FPXmtlh6vPtgbKQ==/31353278.mp3";

		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = music;
		msg.title = musicTitle;
		msg.description = musicDescription;

		Bitmap thumb = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.logo_real);
		msg.thumbData = Util.bmpToByteArray(thumb, true);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("music");
		req.message = msg;
		req.scene = moment_flag ? SendMessageToWX.Req.WXSceneTimeline
				: SendMessageToWX.Req.WXSceneSession;
//		api.sendReq(req);
		sendReq(req);

	}

	/**
	 * 分享网页
	 * @param webpageUrl 网页地址(非空)
	 * @param title 标题
	 * @param description 描述
	 * @param moment_flag 是否分享到朋友圈
	 * ps:默认图片
	 */
	public void shareWebPage(String webpageUrl, String title,
			String description, Bitmap thumb,boolean moment_flag) {
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = webpageUrl;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = title;
		if (TextUtils.isEmpty(description)) {
			msg.description = title;
		} else {
			msg.description = description;
		}
        if(thumb==null){
            thumb = BitmapFactory.decodeResource(activity.getResources(),
					R.drawable.logo_real);
        }
        Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb, 150, 150, true);
//        thumb.recycle();
        msg.setThumbImage(thumbBmp);

		msg.thumbData = Util.bmpToByteArray(thumb, true);
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = moment_flag ? SendMessageToWX.Req.WXSceneTimeline
				: SendMessageToWX.Req.WXSceneSession;
		sendReq(req);

	}
    /**
     * 分享网页
     * @param webpageUrl 网页地址(非空)
     * @param title 标题
     * @param description 描述
     * @param moment_flag 是否分享到朋友圈
     * ps:默认图片
     */
    public void shareWebPage(String webpageUrl, String title,
                             String description,boolean moment_flag) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = webpageUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        if (TextUtils.isEmpty(description)) {
            msg.description = title;
        } else {
            msg.description = description;
        }
       Bitmap thumb = null;
        if(thumb==null){
            thumb = BitmapFactory.decodeResource(activity.getResources(),
					R.drawable.logo_real);
        }
        Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb, 150, 150, true);

        msg.setThumbImage(thumbBmp);

        msg.thumbData = Util.bmpToByteArray(thumb, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = moment_flag ? SendMessageToWX.Req.WXSceneTimeline
                : SendMessageToWX.Req.WXSceneSession;
        sendReq(req);

    }

	/**
	 * 分享网页
	 * @param webpageUrl 网页地址
	 * @param title 标题
	 * @param description 描述
	 * @param imageRes 图片资源
	 * @param moment_flag 是否分享到朋友圈
	 */
	public void shareWebPage(String webpageUrl, String title,
			String description, int imageRes, boolean moment_flag) {
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = webpageUrl;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = title;
		if (TextUtils.isEmpty(description)) {
			msg.description = title;
		} else {
			msg.description = description;
		}
		Bitmap thumb = BitmapFactory.decodeResource(activity.getResources(),
				imageRes);
		msg.thumbData = Util.bmpToByteArray(thumb, true);
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = moment_flag ? SendMessageToWX.Req.WXSceneTimeline
				: SendMessageToWX.Req.WXSceneSession;
		sendReq(req);

	}

	/**
	 * 分享网页
	 * @param webpageUrl 网页地址
	 * @param title 标题
	 * @param description 描述
	 * @param imagePath 图片本地路径
	 * @param moment_flag 是否分享到朋友圈
	 */
	public void shareWebPage(String webpageUrl, String title,
			String description, String imagePath, boolean moment_flag) {
		File file = new File(imagePath);
		if (!file.exists()) {
			String tip = activity.getString(R.string.send_img_file_not_exist);
			showToast(tip + " path = " + imagePath);
			return;
		}
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = webpageUrl;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = title;
		if (TextUtils.isEmpty(description)) {
			msg.description = title;
		} else {
			msg.description = description;
		}
		Bitmap bmp = BitmapFactory.decodeFile(imagePath);
		Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE,
				THUMB_SIZE, true);
		bmp.recycle();
		msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = moment_flag ? SendMessageToWX.Req.WXSceneTimeline
				: SendMessageToWX.Req.WXSceneSession;
		sendReq(req);

	}
	
	private boolean sendReq(SendMessageToWX.Req req){
		boolean flag = false;
		if(api.isWXAppInstalled()){
			flag = api.sendReq(req);
		} else {
			Toast.makeText(activity, "没有安装微信", Toast.LENGTH_SHORT).show();
		}
		return flag;
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	void showToast(String content) {
		Toast.makeText(App.getContext(), content,
				Toast.LENGTH_SHORT).show();
	}
	void showToast(int content) {
		Toast.makeText(App.getContext(), content,
				Toast.LENGTH_SHORT).show();
	}
	
}
