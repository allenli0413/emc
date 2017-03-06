package com.emiancang.emiancang.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.igexin.sdk.PushManager;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.zwyl.ActivityManager;
import com.emiancang.emiancang.App;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.SelectImagInfo;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.bean.AptitudeInfo;
import com.emiancang.emiancang.bean.ResEntity;
import com.emiancang.emiancang.dialog.ImageSelectDialog;
import com.emiancang.emiancang.eventbean.PerfectUserNameTransfer;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.hx.db.DemoDBManager;
import com.emiancang.emiancang.hx.db.EaseUser;
import com.emiancang.emiancang.main.MainActivity;
import com.emiancang.emiancang.my.EnterpriseCheckActivity;
import com.emiancang.emiancang.my.MyPerfectUserName;
import com.emiancang.emiancang.my.MyWechatBindActivity;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserLogin;
import com.emiancang.emiancang.user.UserManager;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.litesuits.common.assist.LogReader.open;

/**
 * Created by 22310 on 2016/11/9.
 */

public class PerfectUserDataActivity extends TitleActivity {
    private static final String TAG = "PerfectUserDataActivity";


    public final int UPLOAD_AVATAR_TYPE = 10000;

    @InjectView(R.id.iv_nicheng_goto)
    ImageView ivNichengGoto;
    @InjectView(R.id.perfect_user_avater)
    SimpleDraweeView perfectUserAvater;
    @InjectView(R.id.perfect_user_avater_layout)
    RelativeLayout perfectUserAvaterLayout;
    @InjectView(R.id.user_name_goto)
    ImageView userNameGoto;
    @InjectView(R.id.perfect_user_name)
    TextView perfectUserName;
    @InjectView(R.id.perfect_user_name_layout)
    RelativeLayout perfectUserNameLayout;
    @InjectView(R.id.perfect_submit)
    TextView perfectSubmit;

    private String mAvatarUrl = "";
    private int type = -1;
    private String mName = "";
    private String mPhone = "";
    private String mPassword = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_perfect_userdata, null);
        setContentView(view);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
        initHead();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        type = getIntExtra("type");
        mPhone = getStringExtra("phone");
        mPassword = getStringExtra("pwd");
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("完善个人信息", getResources().getColor(R.color.white));
        getHeadBar().hideLeftImage();
    }

    long back_time;
    /**
     * 退出
     */
    public void onBackPressed() {
        if (System.currentTimeMillis() - back_time > 5 * 1000) {
            back_time = System.currentTimeMillis();
            showToast(R.string.finish_toast);
            return;
        }
        ActivityManager.getInstance().exitAll();
        super.onBackPressed();
    }

    @OnClick({R.id.perfect_user_avater_layout, R.id.perfect_user_name_layout, R.id.perfect_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.perfect_user_avater_layout:
                openAvatarDialog();
                break;
            case R.id.perfect_user_name_layout:
                Intent intent = createIntent(MyPerfectUserName.class);
                intent.putExtra("name",mName);
                startActivity(intent);
                break;
            case R.id.perfect_submit:
                if(TextUtils.isEmpty(mName)){
                    showToast("请设置用户姓名");
                    return;
                }
                if(TextUtils.isEmpty(mAvatarUrl)){
                    showToast("请设置用户头像");
                    return;
                }
                ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
                Map<String,String> map = new HashMap<>();
                map.put("name",mName);
                ApiUtil.mapMultipart = map;
                UserService service = ApiUtil.createDefaultApi(UserService.class,ApiUtil.SMURL);
                File file = new File(mAvatarUrl);
                if(file.exists()){
                    RequestBody ImageRevenueFile =
                            RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"),file);
                    MultipartBody.Part ImageRevenuebody = MultipartBody.Part.createFormData("Image", file.getName(), ImageRevenueFile);
                    ApiUtil.doDefaultApi(service.completeregisterinfo(ImageRevenuebody), new HttpSucess<UserLogin>() {
                        @Override
                        public void onSucess(UserLogin userLogin) {
                            if(userLogin.getResCode().equals("0")){
                                ApiUtil.mapMultipart = null;
                                if(type != -1){
                                    startActivity(createIntent(MainActivity.class));
                                    finish();
                                }else{
                                    ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
                                    UserService api = ApiUtil.createDefaultApi(UserService.class);
                                    ApiUtil.doDefaultApi(api.getAccountInfomation(mPhone), new HttpSucess<User>() {
                                        @Override
                                        public void onSucess(User user) {
                                            App.USERID = "";
                                            App.TOKEN = "";
                                            user.setToken(App.TOKEN);
                                            user.setPassword(mPassword);
                                            UserManager.getInstance().add(user);
                                            startActivity(createIntent(MainActivity.class));
                                            App.getInstance().getEasemobBinder().getService().regist(user.getESjzcSjh(),PerfectUserDataActivity.this, false);
                                            finish();
                                        }
                                    }, viewControl);
//                                    // close it before login to make sure DemoDB not overlap
//                                    DemoDBManager.getInstance().closeDB();
//                                    // reset current user name before login
//                                    App.getInstance().setCurrentUserName(mPhone);
//                                    // 调用sdk登陆方法登陆聊天服务器
//                                    Log.d(TAG, "EMClient.getInstance().login");
//                                    EMClient.getInstance().login(mPhone, App.HXPASSWORD, new EMCallBack() {
//
//                                        @Override
//                                        public void onSuccess() {
//                                            Log.d(TAG, "login: onSuccess");
//                                            // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
//                                            // ** manually load all local groups and
//                                            EMClient.getInstance().groupManager().loadAllGroups();
//                                            EMClient.getInstance().chatManager().loadAllConversations();
//                                            getFriends();
//
//                                        }
//
//                                        @Override
//                                        public void onProgress(int progress, String status) {
//                                            Log.d(TAG, "login: onProgress");
//                                        }
//
//                                        @Override
//                                        public void onError(final int code, final String message) {
//                                            Log.d(TAG, "login: onError: " + code);
//                                            runOnUiThread(new Runnable() {
//                                                public void run() {
//                                                    Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
//                                                            Toast.LENGTH_SHORT).show();
//                                                }
//                                            });
//                                        }
//                                    });
                                }

                            }else{
                                showToast(userLogin.getResMsg());
                            }
                        }
                    }, viewControl);
                }
                break;
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
            final String avatar_url = imageInfo.getImagePath();
            if (!TextUtils.isEmpty(avatar_url)) {
                open = true;
                mAvatarUrl = avatar_url;
                //设置头像
                perfectUserAvater.setImageURI(Uri.fromFile(new File(avatar_url)));
            }
        }
    }

    public void onEventMainThread(PerfectUserNameTransfer perfectUserNameTransfer) {
        mName = perfectUserNameTransfer.getName();
        perfectUserName.setText(mName);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    private  void  getFriends(){
        try {
            List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
            Map<String ,EaseUser> users=new HashMap<String ,EaseUser>();
            for(String username:usernames){
                EaseUser user=new EaseUser(username);
                users.put(username, user);
            }
            App.getInstance().setContactList(users);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }
}
