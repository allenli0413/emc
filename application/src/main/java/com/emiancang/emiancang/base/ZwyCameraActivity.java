package com.emiancang.emiancang.base;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.emiancang.emiancang.uitl.ImageResourseUtil;
import com.zwyl.BaseActivity;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.avatar.ShowImageDetailActivity;
import com.emiancang.emiancang.uitl.ZwyContextKeeper;
import com.emiancang.emiancang.uitl.ZwyFileOption;
import com.emiancang.emiancang.uitl.ZwyImageUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 拍照处理逻辑
 * 
 * @author ForLyp
 */
public class ZwyCameraActivity extends BaseActivity {
	static public boolean enable_zoom = false;
	static public int imageType = 0;

	public static final int NONE = 0;

	public static final int PHOTOHRAPH = 1;// 拍照

	public static final int PHOTOZOOM = 2; // 缩放

	public static final int PHOTORESOULT = 3;// 结果

	public static final int PHOTO_TAKE = 101;
	public static final int PHOTO_PICK = 102;
	public static final int PHOTO_END = 103;
	int crop = 128;
	public static final String IMAGE_UNSPECIFIED = "image/*";
	ImageView mImage;

	static String mImgFile;
//	public static String action = ZwyContextKeeper.getInstance()
//			.getPackageName() + "camera";

	// 0选择图片，1拍照
	public static int type = 0;

	static String getValidFile() {
		if (mImgFile == null) {
			File path = null;
			String file_path = ZwyContextKeeper.getSavePath()+"image/";
			path = new File(file_path);
			if (!path.exists()) {
				ZwyFileOption.createFile(path);
			}

			mImgFile = path.getAbsolutePath() + "/"
					+ System.currentTimeMillis() + ".jpg";
		}
		return mImgFile;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		setContentView(R.layout.zwyl_base_camera_layout);
		mImage = (ImageView) findViewById(R.id.img_result);
		mImgFile = null;
		getValidFile();
		if (type == 0) {
			pickPhoto();
		} else {
			takePhoto();
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		enable_zoom = false;

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (enable_zoom) {
			// 头像
			handleActivtyResult(requestCode, resultCode, data);
		} else {
			// 其他
			handleActivtyResult2(requestCode, resultCode, data);
		}
		super.onActivityResult(requestCode, resultCode, data);

	}

	/**
	 * 头像处理结果
	 * 
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 * @author ForLyp
	 */
	void handleActivtyResult(final int requestCode, final int resultCode,
			final Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == PHOTO_TAKE) {
				// 拍照
				new Thread() {
					public void run() {
						try {
							sleep(1000);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						zoomPhoto(Uri.fromFile(new File(mImgFile)));
					};
				}.start();

			} else if (requestCode == PHOTO_PICK) {
				// 相册
				zoomPhoto(data.getData());
			} else if (requestCode == PHOTO_END) {
				// 裁剪成功
				Bundle extras = data.getExtras();
				Bitmap bitmap = (Bitmap) extras.get("data");
				// Bitmap bitmap = BitmapFactory.decodeFile(mImgFile);

				try {
					ZwyImageUtils.saveMyBitmap(bitmap, getValidFile());    //图像保存本地
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				convertAndSave();
				finish();
			}
		} else {
			showToast("图片选择失败");
			finish();
		}

	}

	PictureCallback jpeg = new PictureCallback() {

		public void onPictureTaken(byte[] data, Camera camera) {
			//
			Bitmap bMap;
			try {//

				bMap = BitmapFactory.decodeByteArray(data, 0, data.length);
				Bitmap bMapRotate;
				Configuration config = getResources().getConfiguration();
				if (config.orientation == 1) { // 坚拍
					Matrix matrix = new Matrix();
					matrix.reset();
					matrix.postRotate(270);
					bMapRotate = Bitmap.createBitmap(bMap, 0, 0,
							bMap.getWidth(), bMap.getHeight(), matrix, true);
					bMap = bMapRotate;
				}

				//
				Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
				File file = new File(getValidFile());
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(file));
				bMap.compress(Bitmap.CompressFormat.JPEG, 100, bos);// 将图片压缩到流中
				bos.flush();// 输出
				bos.close();// 关闭
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	};

	void handleActivtyResult2(final int requestCode, final int resultCode,
			final Intent data) {
		Bitmap bitmap = null;
		if (resultCode == Activity.RESULT_OK) {

			if (requestCode == PHOTO_PICK) {
				Uri uri = data.getData();
				bitmap = setImage(uri);
				if (bitmap == null) {
					finish();
					return;
				}
				try {
					ZwyImageUtils.saveMyBitmap(bitmap, getValidFile());
				} catch (IOException e) { // TODO Auto-generated catch block
					e.printStackTrace();
				}
				//
				bitmap.recycle();
				bitmap = null;

			} else if (requestCode == PHOTO_TAKE) {
				File image = new File(getValidFile());
				// DisplayMetrics dm = getResources().getDisplayMetrics();
				bitmap = ZwyImageUtils.getFitableBitmap(960, 1280,
						getValidFile());
				if (bitmap == null) {
					finish();
					return;
				}
				try {
					ZwyImageUtils.saveMyBitmap(bitmap, image.getAbsolutePath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //
				bitmap.recycle();
				bitmap = null;
			}
			convertAndSave();
		} else {
			finish();
		}

	}

	private Bitmap setImage(Uri mImageCaptureUri) {

		// 不管是拍照还是选择图片每张图片都有在数据中存储也存储有对应旋转角度orientation值
		// 所以我们在取出图片是把角度值取出以便能正确的显示图片,没有旋转时的效果观看
		String filePath = ImageResourseUtil.getImageAbsolutePath(getActivity(), mImageCaptureUri);
		if (filePath != null) {
			Bitmap bitmap = ZwyImageUtils.getFitableBitmap(960, 1280,
					filePath);
			return bitmap;
		}

//		ContentResolver cr = this.getContentResolver();
//		Cursor cursor = cr.query(mImageCaptureUri, null, null, null, null);// 根据Uri从数据库中找
//		if (cursor != null) {
//			cursor.moveToFirst();// 把游标移动到首位，因为这里的Uri是包含ID的所以是唯一的不需要循环找指向第一个就是了
//			String filePath = cursor.getString(cursor.getColumnIndex("_data"));// 获取图片路
//			// String orientation = cursor.getString(cursor
//			// .getColumnIndex("orientation"));// 获取旋转的角度
//			cursor.close();
//			if (filePath != null) {
//				Bitmap bitmap = ZwyImageUtils.getFitableBitmap(960, 1280,
//						filePath);
//				return bitmap;
//			}
//		}
		return null;
	}

	void zoomPhoto(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");//Android系统自带的一个图片剪裁页面，点击确定之后就会得到一张图片
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		intent.putExtra("scale", "false");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 4);
		intent.putExtra("aspectY", 4);
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);
		// intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTO_END);

	}

	public static void compressBmpToFile(Bitmap bmp, File file) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 80;// 个人喜欢从80开始,
		bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		while (baos.toByteArray().length / 1024 > 100) {
			baos.reset();
			options -= 10;
			bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void convertAndSave() {
		Runnable run = new Runnable() {

			@Override
			public void run() {
                Intent intent = createIntent(ShowImageDetailActivity.class);
                intent.putExtra("image_path",mImgFile);
                intent.putExtra("imageType",imageType);
                startActivity(intent);
				finish();
			}
		};
		new Thread(run).start();
	}

	void pickPhoto() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				IMAGE_UNSPECIFIED);
		startActivityForResult(intent, PHOTO_PICK);
	}

	void takePhoto() {
		// //强制切横
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
		startActivityForResult(intent, ZwyCameraActivity.PHOTO_TAKE);
	}

	private Uri getTempUri() {
		return Uri.fromFile(getTempFile());
	}

	private File getTempFile() {
		return new File(mImgFile);
	}

	static public boolean isSDCARDMounted() {
		String status = Environment.getExternalStorageState();

		if (status.equals(Environment.MEDIA_MOUNTED))
			return true;
		return false;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
}
