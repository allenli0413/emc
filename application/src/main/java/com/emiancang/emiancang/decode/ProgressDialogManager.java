package com.emiancang.emiancang.decode;

import android.app.Activity;
import android.app.Dialog;
import android.widget.TextView;

import com.emiancang.emiancang.R;


public class ProgressDialogManager {
	static Dialog waiteDialog;
	static Activity activity;

	public static void cancelWaiteDialog() {
		if (waiteDialog != null && waiteDialog.isShowing()
				&& !activity.isFinishing()) {
			waiteDialog.dismiss();
			waiteDialog = null;
			activity = null;
		}
	}

	public static boolean isShowing(Activity context){
		if(activity!=null&&activity.equals(context)){
			return true;
		}
		return false;
	}
	public static void showWaiteDialog(Activity context, String message) {
		activity = context;
		waiteDialog = new Dialog(activity, R.style.selectorDialog);
		waiteDialog.setContentView(R.layout.my_progress_view);
		waiteDialog.setCanceledOnTouchOutside(false);
		TextView messageText = (TextView)waiteDialog.findViewById(R.id.message);
		messageText.setText(message);
		if (activity != null && !waiteDialog.isShowing()
				&& !activity.isFinishing()) {
			waiteDialog.show();
		}
	}
}
