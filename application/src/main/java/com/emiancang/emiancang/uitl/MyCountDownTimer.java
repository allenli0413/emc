package com.emiancang.emiancang.uitl;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.widget.TextView;

/*
验证码倒计时
 */
public class MyCountDownTimer extends CountDownTimer {

	public interface CountDownTimerClickListener{
		public void TimerFinish();
	}

	private TextView mTextView;
	private CountDownTimerClickListener mListener;

	public MyCountDownTimer(long millisInFuture, long countDownInterval,
								 TextView text) {
		super(millisInFuture, countDownInterval);
		this.mTextView = text;
	}

	public void setListener(CountDownTimerClickListener listener){
		mListener = listener;
	}

	@Override
	public void onTick(long millisUntilFinished) {
		mTextView.setEnabled(false);
		mTextView.setText(millisUntilFinished / 1000 + getTickTxt());
	}

	@Override
	public void onFinish() {
		mTextView.setEnabled(true);
		mTextView.setText("获取验证码");
		if(mListener != null)
			mListener.TimerFinish();
	}
private String mTxt;

	@NonNull
	public void setTickTxt(String txt) {
		mTxt = txt;
	}
	public String  getTickTxt() {
		return mTxt ;
	}

}
