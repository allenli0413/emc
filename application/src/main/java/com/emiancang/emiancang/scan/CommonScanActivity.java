package com.emiancang.emiancang.scan;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.main.home.qualityquery.activity.QualityContrastDetailActivity;
import com.emiancang.emiancang.main.home.service.ScanService;
import com.emiancang.emiancang.store.model.MainStoreModel;
import com.emiancang.emiancang.uitl.Constant;
import com.emiancang.emiancang.uitl.zxing.ScanListener;
import com.emiancang.emiancang.uitl.zxing.ScanManager;
import com.emiancang.emiancang.uitl.zxing.decode.DecodeThread;
import com.emiancang.emiancang.uitl.zxing.decode.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yuanyueqing on 2016/11/15.
 */

public final class CommonScanActivity extends Activity implements ScanListener, View.OnClickListener {
    static final String TAG = CommonScanActivity.class.getSimpleName();
    SurfaceView scanPreview = null;
    View scanContainer;
    View scanCropView;
    ImageView scanLine;
    ScanManager scanManager;
    TextView iv_light;
    TextView qrcode_g_gallery;
    TextView qrcode_ic_back;
    final int PHOTOREQUESTCODE = 1111;

    @InjectView(R.id.service_register_rescan)
    Button rescan;
    @InjectView(R.id.scan_image)
    ImageView scan_image;
    @InjectView(R.id.authorize_return)
    ImageView authorize_return;
    private int scanMode;//扫描模型（条形，二维码，全部）

    @InjectView(R.id.common_title_TV_center)
    TextView title;
    @InjectView(R.id.scan_hint)
    TextView scan_hint;
    @InjectView(R.id.tv_scan_result)
    TextView tv_scan_result;



    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_scan_code);
        ButterKnife.inject(this);
        scanMode=getIntent().getIntExtra(Constant.REQUEST_SCAN_MODE,Constant.REQUEST_SCAN_MODE_ALL_MODE);
        initView();
    }

    void initView() {
        switch (scanMode){
            case DecodeThread.BARCODE_MODE:
                title.setText(R.string.scan_barcode_title);
                scan_hint.setText(R.string.scan_barcode_hint);
                break;
            case DecodeThread.QRCODE_MODE:
                title.setText(R.string.scan_qrcode_title);
                scan_hint.setText(R.string.scan_qrcode_hint);
                break;
            case DecodeThread.ALL_MODE:
                title.setText(R.string.scan_allcode_title);
                scan_hint.setText(R.string.scan_allcode_hint);
                break;
        }
        scanPreview = (SurfaceView) findViewById(R.id.capture_preview);
        scanContainer = findViewById(R.id.capture_container);
        scanCropView = findViewById(R.id.capture_crop_view);
        scanLine = (ImageView) findViewById(R.id.capture_scan_line);
        qrcode_g_gallery = (TextView) findViewById(R.id.qrcode_g_gallery);
        qrcode_g_gallery.setOnClickListener(this);
        qrcode_ic_back = (TextView) findViewById(R.id.qrcode_ic_back);
        qrcode_ic_back.setOnClickListener(this);
        iv_light = (TextView) findViewById(R.id.iv_light);
        iv_light.setOnClickListener(this);
        rescan.setOnClickListener(this);
        authorize_return.setOnClickListener(this);
        //构造出扫描管理器
        scanManager = new ScanManager(this, scanPreview, scanContainer, scanCropView, scanLine, scanMode,this);
    }

    @Override
    public void onResume() {
        super.onResume();
        scanManager.onResume();
        rescan.setVisibility(View.INVISIBLE);
        scan_image.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        scanManager.onPause();
    }
    /**
     *
     */
    public void scanResult(Result rawResult, Bundle bundle) {
        //扫描成功后，扫描器不会再连续扫描，如需连续扫描，调用reScan()方法。
        //scanManager.reScan();
//		Toast.makeText(that, "result="+rawResult.getText(), Toast.LENGTH_LONG).show();

        if (!scanManager.isScanning()) { //如果当前不是在扫描状态
            //设置再次扫描按钮出现
            rescan.setVisibility(View.VISIBLE);
            scan_image.setVisibility(View.VISIBLE);
            Bitmap barcode = null;
            byte[] compressedBitmap = bundle.getByteArray(DecodeThread.BARCODE_BITMAP);
            if (compressedBitmap != null) {
                barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
                barcode = barcode.copy(Bitmap.Config.ARGB_8888, true);
            }
            scan_image.setImageBitmap(barcode);
        }
        rescan.setVisibility(View.VISIBLE);
        scan_image.setVisibility(View.VISIBLE);
        tv_scan_result.setVisibility(View.VISIBLE);
        tv_scan_result.setText("结果："+rawResult.getText());
        getBarInfo(rawResult);
    }

    private void getBarInfo(Result rawResult) {
        ViewControl viewControl = ViewControlUtil.create2Dialog(this);
        ScanService api = ApiUtil.createDefaultApi(ScanService.class);
        ApiUtil.doDefaultApi(api.barInfo(rawResult.getText()), data -> {
            if(TextUtils.isEmpty(data.getHyJysjPh())){
                Toast.makeText(this, "没找到该批棉花", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(this, QualityContrastDetailActivity.class);
            intent.putExtra("flag", 1);
            intent.putExtra("data", data);
            startActivity(intent);
        }, viewControl);
    }

    void startScan() {
        if (rescan.getVisibility() == View.VISIBLE) {
            rescan.setVisibility(View.INVISIBLE);
            scan_image.setVisibility(View.GONE);
            scanManager.reScan();
        }
    }

    @Override
    public void scanError(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        //相机扫描出错时
        if(e.getMessage()!=null&&e.getMessage().startsWith("相机")){
            scanPreview.setVisibility(View.INVISIBLE);
        }
    }

    public void showPictures(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String photo_path;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTOREQUESTCODE:
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = this.getContentResolver().query(data.getData(), proj, null, null, null);
                    if (cursor.moveToFirst()) {
                        int colum_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        photo_path = cursor.getString(colum_index);
                        if (photo_path == null) {
                            photo_path = Utils.getPath(getApplicationContext(), data.getData());
                        }
                        scanManager.scanningImage(photo_path);
                    }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qrcode_g_gallery:
                showPictures(PHOTOREQUESTCODE);
                break;
            case R.id.iv_light:
                scanManager.switchLight();
                break;
            case R.id.qrcode_ic_back:
                finish();
                break;
            case R.id.service_register_rescan://再次开启扫描
                startScan();
                break;
            case R.id.authorize_return:
                finish();
                break;
            default:
                break;
        }
    }

}
