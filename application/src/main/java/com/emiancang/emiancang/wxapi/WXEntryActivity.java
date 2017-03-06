package com.emiancang.emiancang.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.sdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zwyl.BaseActivity;
import com.emiancang.emiancang.decode.ProgressDialogManager;

import de.greenrobot.event.EventBus;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {


	// IWXAPI 是第三方app和微信通信的openapi接口
	public IWXAPI wxApi;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		 setContentView(R.layout.entry);
		// 通过WXAPIFactory工厂，获取IWXAPI的实例
		wxApi = WXAPIFactory.createWXAPI(this, WeiChatConstants.APP_ID, false);
		wxApi.registerApp(WeiChatConstants.APP_ID);
		wxApi.handleIntent(getIntent(), this);

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		setIntent(intent);
		wxApi.handleIntent(intent, this);
	}

	// 微信发送请求到第三方应用时，会回调到该方法
	@Override
	public void onReq(BaseReq req) {
		switch (req.getType()) {
		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
			goToGetMsg();
			break;
		case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
			goToShowMsg((ShowMessageFromWX.Req) req);
			break;
		default:
			break;
		}
	}

	// 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
	@Override
	public void onResp(BaseResp resp) {
//		int result = 0;
		String result = "未知错误";
//		Logger.i("wechat", resp.errStr.toString());
//		Toast.makeText(this, resp.errCode+"", Toast.LENGTH_LONG).show();

		if(resp==null){
			return;
		}
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
//			result = R.string.errcode_success;
			result= "发送成功";
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
//			result = R.string.errcode_cancel;
			result= "发送取消";
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
//			result = R.string.errcode_deny;
			result= "发送被拒绝";
			break;
		default:
			result = "";
			break;
		}

		if(resp.errCode == BaseResp.ErrCode.ERR_OK){
			try {

				Bundle bundle = new Bundle();
				resp.toBundle(bundle);
				String token = bundle.getString("_wxapi_sendauth_resp_token");
				if(TextUtils.isEmpty(token)){
					Toast.makeText(this, result, Toast.LENGTH_LONG).show();
			Toast.makeText(this, "授权失败，请重试", Toast.LENGTH_LONG).show();
				}else{
					EventBus.getDefault().post(new WechatToken(token));
				}
			}catch (Exception e){
				ProgressDialogManager.cancelWaiteDialog();
			}
		}



		finish();
	}

	private void goToGetMsg() {
		finish();
	}

	private void goToShowMsg(ShowMessageFromWX.Req showReq) {
		WXMediaMessage wxMsg = showReq.message;
		WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;

		StringBuffer msg = new StringBuffer(); // 组织一个待显示的消息内容
		msg.append("description: ");
		msg.append(wxMsg.description);
		msg.append("\n");
		msg.append("extInfo: ");
		msg.append(obj.extInfo);
		msg.append("\n");
		msg.append("filePath: ");
		msg.append(obj.filePath);
		finish();
	}
}