package com.emiancang.emiancang.update;


import android.view.View;

import com.emiancang.emiancang.App;
import com.emiancang.emiancang.bean.AppVersionInfo;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.service.UserService;
import com.litesuits.common.utils.PackageUtil;
import com.mayigeek.frame.http.state.HttpSucess;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ZwyHttpClientUtils {
	public static final String TAG = "ZwyHttpClientUtils";

	private static int CONNECTION_TIME_OUT = 30 * 1000;
	private static int SOCKET_TIME_OUT = 30 * 1000;


	public interface ZwyUploadListener {
		public void onStart(String aUrl);

		public void onError(String aUrl, String aInfo);

		public void onProgressChanged(String aUrl, long aValue);

		public void onEnd(String aUrl, String aInfo);
	}

	public interface ZwyDownloadListener {
		void onProgressChanged(String name, int progress);

		void onError(String error);
	}

	public static boolean downLoadFile(String aUrl, String aLocalPath,
			String notification, ZwyDownloadListener listener) {
		// aUrl= "http://www.wowchina.com/download/ma.apk";
		try {
			File file = new File(aLocalPath);
			if (file.exists()) {
				// return true;
				file.delete();
				file.createNewFile();
			} else {
				String path = file.getParent();
				new File(path).mkdirs();
				file.createNewFile();
			}
			URL url = new URL(aUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/octet-stream");
			conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			conn.setRequestProperty("Charset", "UTF-8");
			conn.connect();

			int size = conn.getContentLength();
			FileOutputStream fos = new FileOutputStream(aLocalPath);
			byte[] buffer = new byte[1024];
			int len = 0;
			int temp = -1;
			int progress = 0;
			int hasRead = 0;

			while ((len = conn.getInputStream().read(buffer)) >= 0) {
				hasRead += len;
				progress = hasRead * 100 / size;
				if (temp != progress) {
					if (listener != null) {
						listener.onProgressChanged(notification, progress);
					}
				}
				temp = progress;
				fos.write(buffer, 0, len);
			}
			fos.flush();
			fos.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (listener != null)
				listener.onError(notification);
			return false;
		}
		return true;
	}

	public static String getUpdateInfo(String aUrl) {
		String res = null;
		try {
			URL url = new URL(aUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/octet-stream");
			conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			conn.setRequestProperty("Charset", "UTF-8");
			conn.connect();
			StringBuilder sb = new StringBuilder();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = conn.getInputStream().read(buffer)) >= 0) {
				sb.append(new String(buffer, 0, len));
			}
			// System.out.println(sb.toString());
			res = sb.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return res;
	}

}
