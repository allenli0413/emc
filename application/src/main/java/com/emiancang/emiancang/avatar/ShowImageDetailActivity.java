package com.emiancang.emiancang.avatar;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zwyl.BaseActivity;
import com.emiancang.emiancang.base.SelectImagInfo;
import com.emiancang.emiancang.uitl.ZwyFileOption;
import com.zwyl.http.SimpleToastViewContorl;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.http.SimpleResponHandler;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

import static com.emiancang.emiancang.base.ZwyCameraActivity.imageType;

/**
 * Created by wuxu on 15/2/12.
 */
public class ShowImageDetailActivity extends BaseActivity implements View.OnClickListener {

    String iamgePath;
    int type;
    @InjectView(R.id.btn_cancel)
    TextView btnCancel;
    @InjectView(R.id.btn_confirm)
    TextView btnConfirm;
    @InjectView(R.id.bottom_button_view)
    LinearLayout bottomButtonView;
    @InjectView(R.id.normal_image)
    ProgressBar normalImage;
    @InjectView(R.id.frsco_img)
//    SimpleDraweeView frscoImg;
            ImageView frscoImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_image_detail_viwe);
        ButterKnife.inject(this);
        iamgePath = getIntent().getStringExtra("image_path");
        type = getIntent().getIntExtra("imageType",0);

//        getHeadBar().setCenterTitle(R.string.picture);
        intiView();
    }

    private void intiView() {
        this.findViewById(R.id.btn_cancel).setOnClickListener(this);
        this.findViewById(R.id.btn_confirm).setOnClickListener(this);
        if (!TextUtils.isEmpty(iamgePath)) {
            String file_path = "file://" + iamgePath;
            frscoImg.setImageURI(Uri.parse(file_path));
//            ImageLoaderManager.getInstance().displayImage(file_path,
//                    frscoImg);
        } else {
            String file_path = "res://" + R.drawable.default_avater;
            frscoImg.setImageURI(Uri.parse(file_path));
//            ImageLoaderManager.getInstance().displayImage(file_path,
//                    frscoImg);
        }
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_cancel:
                ZwyFileOption.deleteFile(iamgePath);
                finish();
                break;
            case R.id.btn_confirm:
                EventBus.getDefault().post(new SelectImagInfo(iamgePath, type));
                finish();
                break;
        }
    }

}
