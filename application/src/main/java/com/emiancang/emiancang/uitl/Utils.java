/**
 * 
 */
package com.emiancang.emiancang.uitl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.litesuits.android.log.Log;
import com.emiancang.emiancang.App;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.login.LoginActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.igexin.sdk.PushService.context;
import static com.emiancang.emiancang.R.drawable.show;

/**
 * 辅助工具类
 * @创建时间： 2015年11月24日 上午11:46:50
 * @项目名称： AMapLocationDemo2.x
 * @author hongming.wang
 * @文件名称: Utils.java
 * @类型名称: Utils
 */
public class Utils {
	/**
	 *  开始定位
	 */
	public final static int MSG_LOCATION_START = 0;
	/**
	 * 定位完成
	 */
	public final static int MSG_LOCATION_FINISH = 1;
	/**
	 * 停止定位
	 */
	public final static int MSG_LOCATION_STOP= 2;
	
	public final static String KEY_URL = "URL";
	public final static String URL_H5LOCATION = "file:///android_asset/location.html";
	/**
	 * 根据定位结果返回定位信息的字符串
	 * @return
	 */
	public synchronized static String getLocationStr(AMapLocation location){
		if(null == location){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		//errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
		if(location.getErrorCode() == 0){
			sb.append("定位成功" + "\n");
			sb.append("定位类型: " + location.getLocationType() + "\n");
			sb.append("经    度    : " + location.getLongitude() + "\n");
			sb.append("纬    度    : " + location.getLatitude() + "\n");
			sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
			sb.append("提供者    : " + location.getProvider() + "\n");
			
			if (location.getProvider().equalsIgnoreCase(
					android.location.LocationManager.GPS_PROVIDER)) {
				// 以下信息只有提供者是GPS时才会有
				sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
				sb.append("角    度    : " + location.getBearing() + "\n");
				// 获取当前提供定位服务的卫星个数
				sb.append("星    数    : "
						+ location.getSatellites() + "\n");
			} else {
				// 提供者是GPS时是没有以下信息的
				sb.append("国    家    : " + location.getCountry() + "\n");
				sb.append("省            : " + location.getProvince() + "\n");
				sb.append("市            : " + location.getCity() + "\n");
				sb.append("城市编码 : " + location.getCityCode() + "\n");
				sb.append("区            : " + location.getDistrict() + "\n");
				sb.append("区域 码   : " + location.getAdCode() + "\n");
				sb.append("地    址    : " + location.getAddress() + "\n");
				sb.append("兴趣点    : " + location.getPoiName() + "\n");
			}
		} else {
			//定位失败
			sb.append("定位失败" + "\n");
			sb.append("错误码:" + location.getErrorCode() + "\n");
			sb.append("错误信息:" + location.getErrorInfo() + "\n");
			sb.append("错误描述:" + location.getLocationDetail() + "\n");
		}
		return sb.toString();
	}


	public static Bitmap drawableToBitmap(Drawable drawable) {
		if(drawable.getIntrinsicWidth() < 0 || drawable.getIntrinsicHeight() < 0)
			return null;
		Bitmap bitmap = Bitmap.createBitmap(
				drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(),
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		//canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;

	}

	public static Bitmap getBitmapFromUri(Context context, Uri uri)
	{
		try
		{
			// 读取uri所在的图片
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
			return bitmap;
		}
		catch (Exception e)
		{
			Log.e("[Android]", e.getMessage());
			Log.e("[Android]", "目录为：" + uri);
			e.printStackTrace();
			return null;
		}
	}

	// 判断是否为手机号
	public static boolean isPhone(String inputText) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
		//		Pattern p = Pattern.compile("^1(3[0-9]|4[5-7]|5[0-35-9]|7[0-9]|8[0-9])+[0-9]{8}");
		//		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\\\d{8}");
		Matcher m = p.matcher(inputText);
		return m.matches();
	}

	// 全数字正则
	public static boolean isNumOnly(String content){
		Pattern p = Pattern.compile("^[0-9]*$");
		Matcher matcher = p.matcher(content);
		return matcher.matches();
	}

	// 是否开启GPS
	public static boolean isOpenGPS(Context context){
		LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public static AlertDialog mShowLoginDialog = null;
	public static void showGPSDialog(Activity activity){
		View dialogView = LayoutInflater.from(activity).inflate(R.layout.activity_servicelist_dialog, null);
		TextView title_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_title_tv);
		TextView content_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_content_tv);
		TextView verify_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_verify_tv);
		TextView cancel_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_cancel_tv);
		title_tv.setText("GPS提示");
		content_tv.setText("当前GPS定位没有开启，是否要去开启");
		verify_tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				try
				{
					App.getContext().startActivity(intent);
				} catch(ActivityNotFoundException ex)
				{
					// The Android SDK doc says that the location settings activity
					// may not be found. In that case show the general settings.
					// General settings activity
					intent.setAction(Settings.ACTION_SETTINGS);
					try {
						App.getContext().startActivity(intent);
					} catch (Exception e) {
					}
				}
				mShowLoginDialog.dismiss();
			}
		});
		cancel_tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mShowLoginDialog.dismiss();
			}
		});
		mShowLoginDialog = new AlertDialog.Builder(activity).setView(dialogView).create();
		mShowLoginDialog.show();
	}
}
