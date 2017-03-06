package com.emiancang.emiancang.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MusicObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.VoiceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.exception.WeiboShareException;
import com.sina.weibo.sdk.utils.Utility;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.sina.AccessTokenKeeper;
import com.emiancang.emiancang.sina.Constants;

import java.io.File;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 新浪分享接口
 * @date 2014/9/23 20:30
 */
public abstract class SinaShareFragment extends TitleFragment implements
		IWeiboHandler.Response {
	@SuppressWarnings("unused")
	private static final String TAG = "WBShareActivity";
	private static final int THUMB_SIZE = 650;

	/**
	 * 微博微博分享接口实例
	 */
	private IWeiboShareAPI mWeiboShareAPI = null;

	/**
	 * 注意：SsoHandler 仅当 SDK 支持 SSO 时有效
	 */
	private SsoHandler mSsoHandler;

	/**
	 * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
	 */
	private Oauth2AccessToken mAccessToken;

	Bundle savedInstanceState;

	Handler mHandler = new Handler();

	/**
	 * @see {@link Activity#onCreate}
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.savedInstanceState = savedInstanceState;

	}

//	/**
//	 * @see {@link Activity#onNewIntent}
//	 */
//	@Override
//	public void onNewIntent(Intent intent) {
//		super.onNewIntent(intent);
//		// 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
//		// 来接收微博客户端返回的数据；执行成功，返回 true，并调用
//		// {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
//		mWeiboShareAPI.handleWeiboResponse(intent, this);
//	}

	/**
	 * 接收微客户端博请求的数据。 当微博客户端唤起当前应用并进行分享时，该方法被调用。
	 *
	 * @param baseResp
	 *            微博请求数据对象
	 * @see {@link IWeiboShareAPI#handleWeiboRequest}
	 */
	@Override
	public void onResponse(BaseResponse baseResp) {
		switch (baseResp.errCode) {
		case WBConstants.ErrorCode.ERR_OK:
			Toast.makeText(getActivity(), "分享成功", Toast.LENGTH_LONG).show();
			break;
		case WBConstants.ErrorCode.ERR_CANCEL:
			Toast.makeText(getActivity(), "取消分享", Toast.LENGTH_LONG).show();
			break;
		case WBConstants.ErrorCode.ERR_FAIL:
			Toast.makeText(getActivity(), "分享失败" + "Error Message: " + baseResp.errMsg,
					Toast.LENGTH_LONG).show();
			break;
		}
	}

	/**
	 * 分享文字
	 */
	public void shareText(String text) {
		sendMessage(createTextObj(text), null, null, null, null, null);
	}

	/**
	 * 分享文字
	 */
	public void shareText(TextObject textObject) {
		sendMessage(textObject, null, null, null, null, null);
	}

	/**
	 * 分享图片
	 */
	public void shareImage(ImageObject imageObject) {
		sendMessage(null, imageObject, null, null, null, null);
	}

	/**
	 * 分享图片
	 */
	public void shareImage(Bitmap bitmap) {
		sendMessage(null, createImageObj(bitmap), null, null, null, null);
	}

	/**
	 * 分享文字和图片
	 */
	public void shareTextAndImage(String text, Bitmap bitmap) {
		sendMessage(createTextObj(text), createImageObj(bitmap), null, null,
				null, null);
	}

	/**
	 * 分享文字和图片
	 * @param text
	 * @param imageRes 图片资源
	 */
	public void shareTextAndImage(String text, int imageRes) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), imageRes);
		sendMessage(createTextObj(text), createImageObj(bmp), null, null, null,
				null);
	}

	/**
	 * 微博分享问题和图片
	 * @param text 文字内容
	 * @param imagePath 图片地址
	 */
	public void shareTextAndImage(String text, String imagePath) {
		File file = new File(imagePath);
		if (!file.exists()) {
			String tip = "发送图片不存在";
			showToast(tip + " path = " + imagePath);
			return;
		}

		Bitmap bmp = BitmapFactory.decodeFile(imagePath);
//		Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE,
//				THUMB_SIZE, true);
//		bmp.recycle();
		sendMessage(createTextObj(text), createImageObj(bmp), null, null,
				null, null);
	}

	/**
	 * 分享文字和图片
	 */
	public void shareTextAndImage(TextObject textObject, ImageObject imageObject) {
		sendMessage(textObject, imageObject, null, null, null, null);
	}

	/**
	 * 分享网页
	 */
	public void shareWebpage(WebpageObject webpageObject,
							 TextObject textObject, ImageObject imageObject) {
		sendMessage(textObject, imageObject, webpageObject, null, null, null);
	}

	/**
	 * 分享音乐
	 */
	public void shareMusic(MusicObject musicObject, TextObject textObject,
						   ImageObject imageObject) {
		sendMessage(textObject, imageObject, null, musicObject, null, null);
	}

	/**
	 * 分享视频
	 */
	public void shareVideo(VideoObject videoObject, TextObject textObject,
						   ImageObject imageObject) {
		sendMessage(textObject, imageObject, null, null, videoObject, null);
	}

	/**
	 * 分享声音
	 */
	public void shareVoice(VoiceObject voiceObject, TextObject textObject,
						   ImageObject imageObject) {
		sendMessage(textObject, imageObject, null, null, null, voiceObject);
	}

	/**
	 * 第三方应用发送请求消息到微博，唤起微博分享界面。
	 *
	 * @see {@link #sendMultiMessage} 或者 {@link #sendSingleMessage}
	 */
	private void sendMessage(final TextObject textObject,
							 final ImageObject imageObject, final WebpageObject webpageObject,
							 final MusicObject musicObject, final VideoObject videoObject,
							 final VoiceObject voiceObject) {
		try {
			// 检查微博客户端环境是否正常，如果未安装微博，弹出对话框询问用户下载微博客户端
			if (mWeiboShareAPI.isWeiboAppInstalled()) {

				mAccessToken = AccessTokenKeeper.readAccessToken(getActivity());
				if (mAccessToken.isSessionValid()) {// Token可用
					mWeiboShareAPI.registerApp();
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							if (mWeiboShareAPI.isWeiboAppSupportAPI()) {
								int supportApi = mWeiboShareAPI
										.getWeiboAppSupportAPI();
								if (supportApi >= 10351 /*
														 * ApiUtils.
														 * BUILD_INT_VER_2_2
														 */) {
									sendMultiMessage(textObject, imageObject,
											webpageObject, musicObject,
											videoObject, voiceObject);
								} else {
									sendSingleMessage(textObject, imageObject,
											webpageObject, musicObject,
											videoObject/* , hasVoice */);
								}
							} else {
								showToast("此版本不支持");
							}
						}
					}, 100);
				} else {
					// 创建微博实例
					// 创建授权认证信息
					AuthInfo mAuthInfo = new AuthInfo(getActivity(), Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);

					mSsoHandler = new SsoHandler(getResponseActivity(),
							mAuthInfo);
					mSsoHandler.authorize(new WeiboAuthListener() {
						/**
						 * 微博认证授权回调类。 1. SSO 授权时，需要在 @link #onActivityResult 中调用
						 * {@link SsoHandler#authorizeCallBack}
						 * 后， 该回调才会被执行。 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
						 * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
						 * SharedPreferences 中。
						 */

						@Override
						public void onComplete(Bundle values) {
							// 从 Bundle 中解析 Token
							mAccessToken = Oauth2AccessToken
									.parseAccessToken(values);
							if (mAccessToken.isSessionValid()) {
								// 显示 Token

								// 保存 Token 到 SharedPreferences
								AccessTokenKeeper.writeAccessToken(
										getActivity(), mAccessToken);
								Toast.makeText(
										getActivity(),
										R.string.weibosdk_demo_toast_auth_success,
										Toast.LENGTH_SHORT).show();
								init();
								mWeiboShareAPI.registerApp();
								mHandler.postDelayed(new Runnable() {
									@Override
									public void run() {
										if (mWeiboShareAPI
												.isWeiboAppSupportAPI()) {
											int supportApi = mWeiboShareAPI
													.getWeiboAppSupportAPI();
											if (supportApi >= 10351 /*
																	 * ApiUtils.
																	 * BUILD_INT_VER_2_2
																	 */) {
												sendMultiMessage(textObject,
														imageObject,
														webpageObject,
														musicObject,
														videoObject,
														voiceObject);
											} else {
												sendSingleMessage(textObject,
														imageObject,
														webpageObject,
														musicObject,
														videoObject/*
																	 * ,
																	 * hasVoice
																	 */);
											}
										} else {
											showToast("此版本不支持");
										}
									}
								}, 200);

							} else {
								// 以下几种情况，您会收到 Code：
								// 1. 当您未在平台上注册的应用程序的包名与签名时；
								// 2. 当您注册的应用程序包名与签名不正确时；
								// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
								String code = values.getString("code");
								String message = getString(R.string.weibosdk_demo_toast_auth_failed);
								if (!TextUtils.isEmpty(code)) {
									message = message + "\nObtained the code: "
											+ code;
								}
								Toast.makeText(getActivity(), message,
										Toast.LENGTH_LONG).show();
							}
						}

						@Override
						public void onCancel() {
							Toast.makeText(getActivity(),
									R.string.weibosdk_demo_toast_auth_canceled,
									Toast.LENGTH_LONG).show();
						}

						@Override
						public void onWeiboException(WeiboException e) {
							Toast.makeText(getActivity(),
									"Auth exception : " + e.getMessage(),
									Toast.LENGTH_LONG).show();
						}
					});

				}

			}
		} catch (WeiboShareException e) {
			e.printStackTrace();
			 Toast.makeText(getActivity(), e.getMessage(),
			 Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 第三方应用发送请求消息到微博，唤起微博分享界面。 注意：当
	 * {@link IWeiboShareAPI#getWeiboAppSupportAPI()}
	 * >= 10351 时，支持同时分享多条消息， 同时可以分享文本、图片以及其它媒体资源（网页、音乐、视频、声音中的一种）。
	 *
	 * @param textObject
	 *            分享的内容是否为文本
	 * @param imageObject
	 *            分享的内容是否为图片
	 * @param webpageObject
	 *            分享的内容是否为网页
	 * @param musicObject
	 *            分享的内容是否为音乐
	 * @param videoObject
	 *            分享的内容是否为视频
	 * @param voiceObject
	 *            分享的内容是否为声音
	 */
	private void sendMultiMessage(TextObject textObject,
								  ImageObject imageObject, WebpageObject webpageObject,
								  MusicObject musicObject, VideoObject videoObject,
								  VoiceObject voiceObject) {

		// 1. 初始化微博的分享消息
		WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
		if (textObject != null) {
			weiboMessage.textObject = textObject;
		}

		if (imageObject != null) {
			weiboMessage.imageObject = imageObject;
		}

		// 用户可以分享其它媒体资源（网页、音乐、视频、声音中的一种）
		if (webpageObject != null) {
			weiboMessage.mediaObject = webpageObject;
		}
		if (musicObject != null) {
			weiboMessage.mediaObject = musicObject;
		}
		if (videoObject != null) {
			weiboMessage.mediaObject = videoObject;
		}
		if (voiceObject != null) {
			weiboMessage.mediaObject = voiceObject;
		}

		// 2. 初始化从第三方到微博的消息请求
		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
		// 用transaction唯一标识一个请求
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.multiMessage = weiboMessage;

		// 3. 发送请求消息到微博，唤起微博分享界面
		mWeiboShareAPI.sendRequest(getActivity(),request);
	}

	/**
	 * 第三方应用发送请求消息到微博，唤起微博分享界面。 当
	 * {@link IWeiboShareAPI#getWeiboAppSupportAPI()}
	 * < 10351 时，只支持分享单条消息，即 文本、图片、网页、音乐、视频中的一种，不支持Voice消息。
	 *
	 * @param textObject
	 *            分享的内容是否为文本
	 * @param imageObject
	 *            分享的内容是否为图片
	 * @param webpageObject
	 *            分享的内容是否为网页
	 * @param musicObject
	 *            分享的内容是否为音乐
	 * @param videoObject
	 *            分享的内容是否为视频
	 */
	private void sendSingleMessage(TextObject textObject,
								   ImageObject imageObject, WebpageObject webpageObject,
								   MusicObject musicObject, VideoObject videoObject/*
															 * , boolean
															 * hasVoice
															 */) {

		// 1. 初始化微博的分享消息
		// 用户可以分享文本、图片、网页、音乐、视频中的一种
		WeiboMessage weiboMessage = new WeiboMessage();
		if (textObject != null) {
			weiboMessage.mediaObject = textObject;
		}
		if (imageObject != null) {
			weiboMessage.mediaObject = imageObject;
		}
		if (webpageObject != null) {
			weiboMessage.mediaObject = webpageObject;
		}
		if (musicObject != null) {
			weiboMessage.mediaObject = musicObject;
		}
		if (videoObject != null) {
			weiboMessage.mediaObject = videoObject;
		}
		/*
		 * if (hasVoice) { weiboMessage.mediaObject = createVoiceObj(); }
		 */

		// 2. 初始化从第三方到微博的消息请求
		SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
		// 用transaction唯一标识一个请求
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.message = weiboMessage;

		// 3. 发送请求消息到微博，唤起微博分享界面
		mWeiboShareAPI.sendRequest(getActivity(),request);
	}

	/**
	 * 创建文本消息对象。
	 *
	 * @return 文本消息对象。
	 */
	public TextObject createTextObj(String text) {
		TextObject textObject = new TextObject();
		textObject.text = text;
		return textObject;
	}

	/**
	 * 创建图片消息对象。
	 *
	 * @return 图片消息对象。
	 */
	public ImageObject createImageObj(Bitmap bitmap) {
		ImageObject imageObject = new ImageObject();
		imageObject.setImageObject(bitmap);
		return imageObject;
	}

	/**
	 * 创建多媒体（网页）消息对象。
	 *
	 * @return 多媒体（网页）消息对象。
	 */
	public WebpageObject createWebpageObj(String title, String description,
										  Bitmap thumbBitmap, String shareUrl) {
		WebpageObject mediaObject = new WebpageObject();
		mediaObject.identify = Utility.generateGUID();
		mediaObject.title = title;
		mediaObject.description = description;

		// 设置 Bitmap 类型的图片到视频对象里
		mediaObject.setThumbImage(thumbBitmap);
		mediaObject.actionUrl = shareUrl;
		mediaObject.defaultText = "分享网页";
		return mediaObject;
	}

	/**
	 * 创建多媒体（音乐）消息对象。
	 *
	 * @return 多媒体（音乐）消息对象。
	 */
	public MusicObject createMusicObj(String title, String description,
									  Bitmap thumbBitmap, String shareUrl) {
		// 创建媒体消息
		MusicObject musicObject = new MusicObject();
		musicObject.identify = Utility.generateGUID();
		musicObject.title = title;
		musicObject.description = description;

		// 设置 Bitmap 类型的图片到视频对象里
		musicObject.setThumbImage(thumbBitmap);
		musicObject.actionUrl = shareUrl;
		musicObject.dataUrl = "www.weibo.com";
		musicObject.dataHdUrl = "www.weibo.com";
		musicObject.duration = 10;
		musicObject.defaultText = "分享音乐";
		return musicObject;
	}

	/**
	 * 创建多媒体（视频）消息对象。
	 *
	 * @return 多媒体（视频）消息对象。
	 */
	public VideoObject createVideoObj(String title, String description,
									  Bitmap thumbBitmap, String shareUrl) {
		// 创建媒体消息
		VideoObject videoObject = new VideoObject();
		videoObject.identify = Utility.generateGUID();
		videoObject.title = title;
		videoObject.description = description;

		// 设置 Bitmap 类型的图片到视频对象里
		videoObject.setThumbImage(thumbBitmap);
		videoObject.actionUrl = shareUrl;
		videoObject.dataUrl = "www.weibo.com";
		videoObject.dataHdUrl = "www.weibo.com";
		videoObject.duration = 10;
		videoObject.defaultText = "分享视频";
		return videoObject;
	}

	/**
	 * 创建多媒体（音频）消息对象。
	 *
	 * @return 多媒体（音乐）消息对象。
	 */
	public VoiceObject createVoiceObj(String title, String description,
									  Bitmap thumbBitmap, String shareUrl) {
		// 创建媒体消息
		VoiceObject voiceObject = new VoiceObject();
		voiceObject.identify = Utility.generateGUID();
		voiceObject.title = title;
		voiceObject.description = description;

		// 设置 Bitmap 类型的图片到视频对象里
		voiceObject.setThumbImage(thumbBitmap);
		voiceObject.actionUrl = shareUrl;
		voiceObject.dataUrl = "www.weibo.com";
		voiceObject.dataHdUrl = "www.weibo.com";
		voiceObject.duration = 10;
		voiceObject.defaultText = "分享音乐";
		return voiceObject;
	}

	/**
	 * 当 SSO 授权 Activity 退出时，该函数被调用。
	 *
	 * @see {@link Activity#onActivityResult}
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// SSO 授权回调
		// 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResult
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		init();
	}

	private void init() {
		// 创建微博分享接口实例
		mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(getActivity(), Constants.APP_KEY);

		// 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
		// 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
		// NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
		// mWeiboShareAPI.registerApp();

		// 如果未安装微博客户端，设置下载微博对应的回调
		if (!mWeiboShareAPI.isWeiboAppInstalled()) {
//			mWeiboShareAPI.
//					.registerWeiboDownloadListener(new IWeiboDownloadListener() {
//						@Override
//						public void onCancel() {
//							Toast.makeText(SinaShareActivity.this, "\t取消下载",
//									Toast.LENGTH_SHORT).show();
//						}
//					});
		}

		// 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
		// 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
		// 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
		// 失败返回 false，不调用上述回调
		if (savedInstanceState != null) {
			mWeiboShareAPI.handleWeiboResponse(getActivity().getIntent(), this);
		}
	}


    public Activity getResponseActivity(){
        return getActivity();
    }
}
