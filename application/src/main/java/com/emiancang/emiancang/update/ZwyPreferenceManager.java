package com.emiancang.emiancang.update;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;

import com.emiancang.emiancang.uitl.ZwyContextKeeper;


/**
 * 配置信息保存类
 * 
 * @author ForLyp
 */
public class ZwyPreferenceManager {

	static public SharedPreferences getSharedPreferences() {
		Context context = ZwyContextKeeper.getInstance();
		SharedPreferences MyPreferences = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_WORLD_READABLE
						| Context.MODE_WORLD_WRITEABLE);
		return MyPreferences;
	}

	// demo
	public static boolean isNeedDelete() {
		return getSharedPreferences().getBoolean("is_need_delete", true);
	}

	public static void setNeedDelete(boolean flag) {
		SharedPreferences prefs = getSharedPreferences();
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("is_need_delete", flag);
		editor.commit();
	}

	private static int mVerCode;

	/**
	 * @methods name: getVersionCode
	 * @Descripition : 程序当前版本号
	 * @return ：
	 * @date ：2012-7-23 下午04:50:16
	 * @author ：wuxu
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static int getVersionCode() {
		if (mVerCode == 0) {
			PackageManager pm = ZwyContextKeeper.getInstance()
					.getPackageManager();
			PackageInfo pkInfo = null;
			try {
				pkInfo = pm.getPackageInfo(ZwyContextKeeper.getInstance()
						.getPackageName(),
						PackageManager.GET_UNINSTALLED_PACKAGES);
			} catch (NameNotFoundException e) {
			}
			if (pkInfo == null) {
				return 0;
			}

			mVerCode = pkInfo.versionCode;
		}
		return mVerCode;
	}

	private static String mVerName = null;

	public static String getVersionName() {
		if (mVerName == null) {
			PackageManager pm = ZwyContextKeeper.getInstance()
					.getPackageManager();
			PackageInfo pkInfo = null;
			try {
				pkInfo = pm.getPackageInfo(ZwyContextKeeper.getInstance()
						.getPackageName(),
						PackageManager.GET_UNINSTALLED_PACKAGES);
			} catch (NameNotFoundException e) {
			}
			if (pkInfo == null) {
				return null;
			}

			mVerName = pkInfo.versionName;
		}
		return mVerName;
	}

	// 得到一个随即时间已更新
	public static long getPlanUpdateTime(final Context context) {
		return getSharedPreferences().getLong("plan_update_time", 0);
	}

	// 设置随机时间
	public static void setPlanUpdateTime(final Context context, long status) {
		SharedPreferences prefs = getSharedPreferences();
		SharedPreferences.Editor editor = prefs.edit();
		editor.putLong("plan_update_time", status);
		editor.commit();
	}

	/**
	 * @Title: getUpdatepath
	 * @Description: 更新地址
	 * @param context
	 * @return
	 * @author wuxu
	 * @date 2012-5-16
	 */
	public static Element getUpdatepath(final Context context) {
		try {
			String data = getSharedPreferences().getString("update_path", "");
			if (data.length() == 0) {
				return null;
			}
			String[] tmp = data.split("####");
			Element element = new Element();
			element.mDownLoadPath = tmp[0];
			element.mDesc = tmp[1];
			element.mVersion = tmp[2];
            if(tmp.length>3&&!TextUtils.isEmpty(tmp[3])){
                element.mVersionCode = Integer.parseInt(tmp[3]);
            }

			return element;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * @Title: setUpdatePath
	 * @Description: 保存更新
	 * @param context
	 *            数据存入顺序依次为：包名+version+versionCode+下载地址+描述
	 * @param data
	 * @author wuxu
	 * @date 2012-5-16
	 */
	public static void setUpdatePath(final Context context, final Element data) {
		SharedPreferences prefs = getSharedPreferences();
		SharedPreferences.Editor editor = prefs.edit();
		StringBuilder sb = new StringBuilder();
		if (data.mDownLoadPath != null && data.mDownLoadPath.length() > 0) {
			sb.append(data.mDownLoadPath + "####");
		} else {
			sb.append("error" + "####");
		}
		if (data.mDesc != null) {
			if (data.mDesc.length() > 0) {
				sb.append(data.mDesc + "####");
			} else {
				sb.append("kong" + "####");
			}
		} else {
			sb.append("error");
		}
		if (data.mVersion != null) {
			if (data.mVersion.length() > 0) {
				sb.append(data.mVersion + "####");
			} else {
				sb.append("kong" + "####");
			}
		} else {
			sb.append("error");
		}
        if (data.mVersionCode > 0) {
            sb.append(data.mVersionCode + "####");
        } else {
            sb.append("0" + "####");
        }

		editor.putString("update_path", sb.toString());
		editor.commit();
	}

	/**
	 * @Title: getLastUpdateDate
	 * @Description: 上次更新时间
	 * @param context
	 * @return
	 * @author wuxu
	 * @date 2012-5-16
	 */
	public static String getLastUpdateDate(final Context context) {
		return getSharedPreferences().getString("last_update_date", null);
	}

	public static void setLastUpdateDate(final Context context,
			final String date) {
		SharedPreferences prefs = getSharedPreferences();
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("last_update_date", date);
		editor.commit();
	}

	public static long getLastShowUpdateDate(final Context context) {
		return getSharedPreferences().getLong("last_show_update_date", 0);
	}

	public static void setLastShowUpdateDate(final Context context,
			final long date) {
		SharedPreferences prefs = getSharedPreferences();
		SharedPreferences.Editor editor = prefs.edit();
		editor.putLong("last_show_update_date", date);
		editor.commit();
	}

	static long DAY = 1000 * 60 * 60 * 24;
	static long MINUTE = 1000 * 60;

	public static boolean shouldShowUpdate(Context context) {
		long last = getLastShowUpdateDate(context);
		long now = System.currentTimeMillis();
		if (last <= 0) {
			return true;
		}
		if (now / DAY != last / DAY) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean isNextDay(long last) {
		long now = System.currentTimeMillis();
		if (last <= 0) {
			return true;
		}
		if (now / DAY != last / DAY) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * @Title: isUpdateFlag
	 * @Description: 是否有更新
	 * @author wuxu
	 * @date 2012-5-11
	 */
	public static boolean isUpdateFlag(Context context) {
		// TODO Auto-generated method stub
		try {
			boolean value = getSharedPreferences().getBoolean("update_flag",
					false);
			return value;
		} catch (Exception e) {
			return true;
		}

	}

	public static void setUpdateFlag(final Context context, final boolean flag) {
		SharedPreferences prefs = getSharedPreferences();
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("update_flag", flag);
		editor.commit();
	}

	public static void setNetNotificationFlag(final boolean flag) {
		SharedPreferences prefs = getSharedPreferences();
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("net_notifacation_flag", flag);
		editor.commit();
	}

	public static boolean getNetNotificationFlag() {
		SharedPreferences prefs = getSharedPreferences();
		return prefs.getBoolean("net_notifacation_flag", true);
	}

	/**
	 * 设置广告的url地址
	 * 
	 * @param url
	 */
	public static void setAdImageUrl(String url) {
		SharedPreferences prefs = getSharedPreferences();
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("ad_image_url", url);
		editor.commit();
	}

	/**
	 * 得到广告图片的url地址
	 * 
	 * @return
	 */
	public static String getAdImageUrl() {
		SharedPreferences prefs = getSharedPreferences();
		return prefs.getString("ad_image_url", null);
	}
	
	/**
	 * 设置广告的url地址
	 * 
	 * @param
	 */
	public static void setAdTime(long time) {
		SharedPreferences prefs = getSharedPreferences();
		SharedPreferences.Editor editor = prefs.edit();
		editor.putLong("ad_end_time", time);
		editor.commit();
	}

	/**
	 * 得到广告图片的url地址
	 * 
	 * @return
	 */
	public static long getAdTime() {
		SharedPreferences prefs = getSharedPreferences();
		return prefs.getLong("ad_end_time", 0);
	}

	/**
	 * 打开过向导
	 * 
	 * @param
	 */
	public static void setGuideFlag(boolean flag) {
		SharedPreferences prefs = getSharedPreferences();
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("guide_flag", flag);
		editor.commit();
	}

	/**
	 * 是否打开过向导
	 * 
	 * @return
	 */
	public static boolean getGuideFlag() {
		SharedPreferences prefs = getSharedPreferences();
		return prefs.getBoolean("guide_flag", false);
	}

	public static void setStartTime(long time) {
		SharedPreferences prefs = getSharedPreferences();
		SharedPreferences.Editor editor = prefs.edit();
		editor.putLong("start_time", time);
		editor.commit();
	}

	public static long getStartTime() {
		SharedPreferences prefs = getSharedPreferences();
		return prefs.getLong("start_time", 0);
	}
	
	public static boolean getSoundFlag(){
		SharedPreferences prefs = getSharedPreferences();
		return prefs.getBoolean("sound_flag", true);
	}
	public static void setSoundFlag(boolean flag) {
		SharedPreferences prefs = getSharedPreferences();
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("sound_flag", flag);
		editor.commit();
	}
	
	public static long getLastOpenTime() {
		SharedPreferences prefs = getSharedPreferences();
		return prefs.getLong("last_open_time", 0);
	}
	public static void setLastOpenTime(long time) {
		SharedPreferences prefs = getSharedPreferences();
		SharedPreferences.Editor editor = prefs.edit();
		editor.putLong("last_open_time", time);
		editor.commit();
	}
	public static boolean getDistractionFreeFlag() {
		SharedPreferences prefs = getSharedPreferences();
		return prefs.getBoolean("DISTRACTION_FREE_FLAG", false);
	}
	public static void setDistractionFreeFlag(boolean flag) {
		SharedPreferences prefs = getSharedPreferences();
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("DISTRACTION_FREE_FLAG", flag);
		editor.commit();
	}
	public static boolean getNewMessageNotificationFlag() {
		SharedPreferences prefs = getSharedPreferences();
		return prefs.getBoolean("NEW_MESSAGE_NOTICATION_FLAG", true);
	}
	public static void setNewMessageNotificationFlag(boolean flag) {
		SharedPreferences prefs = getSharedPreferences();
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("NEW_MESSAGE_NOTICATION_FLAG", flag);
		editor.commit();
	}


    public static String getDistractionFreeStartTime(){
        SharedPreferences prefs = getSharedPreferences();
        return prefs.getString("FREE_START_TIME", "23:59:00");
	}
    public static void setDistractionFreeStartTime(String startTime) {
        SharedPreferences prefs = getSharedPreferences();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("FREE_START_TIME", startTime);
        editor.commit();
    }
	public static String getDistractionFreeEndTime(){
        SharedPreferences prefs = getSharedPreferences();
        return prefs.getString("FREE_END_TIME", "00:00:00");
	}
    public static void setDistractionFreeEndTime(String endTime) {
        SharedPreferences prefs = getSharedPreferences();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("FREE_END_TIME", endTime);
        editor.commit();
    }


}
