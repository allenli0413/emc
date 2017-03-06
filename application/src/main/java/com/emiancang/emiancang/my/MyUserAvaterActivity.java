package com.emiancang.emiancang.my;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.App;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.SelectImagInfo;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.dialog.ImageSelectDialog;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserLogin;
import com.emiancang.emiancang.user.UserManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.litesuits.common.assist.LogReader.open;

/**
 * Created by 22310 on 2016/11/7.
 */

public class MyUserAvaterActivity extends TitleActivity {

    public final int UPLOAD_AVATAR_TYPE = 10000;
    @InjectView(R.id.my_user_avater)
    SimpleDraweeView myUserAvater;

    public String mAvatarUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_my_user_avater, null);
        setContentView(view);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
        initHead();
        initView();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("个人头像", getResources().getColor(R.color.white));
        getHeadBar().showRightImage();
        getHeadBar().setRightImage(R.drawable.icon_user_avater);
        getHeadBar().setRightOnClickListner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAvatarDialog();
            }
        });
    }

    private void initView() {
        //用户头像
        User user = UserManager.getInstance().getUser();
        //用户头像
        if (!TextUtils.isEmpty(user.getEYhtx())) {
            myUserAvater.setImageURI(Uri.parse(user.getEYhtx()));
        }
    }

    //头像
    private void openAvatarDialog() {
        ImageSelectDialog dialog = new ImageSelectDialog(this, true, UPLOAD_AVATAR_TYPE);
        dialog.show();
    }

    public void onEventMainThread(SelectImagInfo imageInfo) {
        if (imageInfo != null) {
            if (imageInfo.getType() != UPLOAD_AVATAR_TYPE) {
                return;
            }
            mAvatarUrl = imageInfo.getImagePath();
            if (!TextUtils.isEmpty(mAvatarUrl)) {
                open = true;
                //设置头像
//                String file_path = "file://" + avatar_url;
                myUserAvater.setImageURI(Uri.fromFile(new File(mAvatarUrl)));
                setUserAvater();
            }
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    public void setUserAvater(){
        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        Map<String,String> map = new HashMap<>();
        map.put("name",UserManager.getInstance().getUser().getESjzcXm());
        ApiUtil.mapMultipart = map;
        UserService service = ApiUtil.createDefaultApi(UserService.class,ApiUtil.SMURL);
        File file = new File(mAvatarUrl);
        if(file.exists()){
            RequestBody ImageRevenueFile =
                    RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part ImageRevenuebody = MultipartBody.Part.createFormData("Image", file.getName(), ImageRevenueFile);
            ApiUtil.doDefaultApi(service.completeregisterinfo(ImageRevenuebody), new HttpSucess<UserLogin>() {
                @Override
                public void onSucess(UserLogin userLogin) {
                    if(userLogin.getResCode().equals("0")){
                        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
                        UserService api = ApiUtil.createDefaultApi(UserService.class);
                        ApiUtil.doDefaultApi(api.getAccountInfomation(UserManager.getInstance().getUser().getESjzcNm()), new HttpSucess<User>() {
                            @Override
                            public void onSucess(User user) {
                                user.setToken(userLogin.getToken());
                                user.setPassword(UserManager.getInstance().getUser().getPassword());
                                UserManager.getInstance().add(user);
                                showToast("设置成功");
                                finish();
                            }
                        }, viewControl);
                    }else{
                        showToast(userLogin.getResMsg());
                    }
                }
            }, viewControl);
        }else{
            showToast("图片地址出错");
        }
    }
}
