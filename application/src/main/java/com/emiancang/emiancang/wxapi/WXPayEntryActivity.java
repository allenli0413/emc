package com.emiancang.emiancang.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.bean.PayEvent;

import de.greenrobot.event.EventBus;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_framelayout);
        
    	api = WXAPIFactory.createWXAPI(this, WeiChatConstants.APP_ID);

        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		//Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle(R.string.app_tip);
//			builder.setMessage(getString(R.string.pay_result_callback_msg, resp.errStr + ";code=" + String.valueOf(resp.errCode)));
//			builder.show();
			switch (resp.errCode){
				case 0://支付成功
					//需从后台确认结果
					Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
					EventBus.getDefault().post(new PayEvent());
					break;
				case -1://支付失败
					Toast.makeText(this,"支付失败",Toast.LENGTH_SHORT).show();
					EventBus.getDefault().post(new PayEvent(false));
					break;
				case -2://支付取消
					Toast.makeText(this,"取消支付",Toast.LENGTH_SHORT).show();
					EventBus.getDefault().post(new PayEvent(false));
					break;
			}
			finish();
		}
	}
}