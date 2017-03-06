package com.emiancang.emiancang.login;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.emiancang.emiancang.service.EasemobService;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.igexin.sdk.PushManager;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.App;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.bean.ResEntity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.hx.activity.MainActivity;
import com.emiancang.emiancang.hx.db.DemoDBManager;
import com.emiancang.emiancang.hx.db.EaseUser;
import com.emiancang.emiancang.requestcheck.LoginCheck;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserLogin;
import com.emiancang.emiancang.user.UserManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.emiancang.emiancang.App.TOKEN;
import static com.emiancang.emiancang.App.USERID;
import static com.emiancang.emiancang.R.drawable.show;
import static com.zwyl.App.getContext;

/**
 * 登录
 */
public class LoginActivity extends TitleActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";


    @InjectView(R.id.login_phone)
    EditText loginPhone;
    @InjectView(R.id.login_pwd)
    EditText loginPwd;
    @InjectView(R.id.tv_forgetpassword)
    TextView tvForgetpassword;
    @InjectView(R.id.login)
    TextView login;
    @InjectView(R.id.iv_xianshi)
    ImageView ivXianshi;

    boolean hide = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        initHead();
        Util.setStatusBarColor(getActivity(), R.color.green);
        login.setEnabled(false);
        loginPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = loginPhone.getText().toString().trim();
//                if(Util.compileExChar(str)){
//                    str = str.substring(0,str.length()-1);
//                    loginPhone.setText(str);
//                    loginPhone.setSelection(str.length());
//                }
//                refreshButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(loginPhone.getText().toString().trim()))
                    login.setEnabled(true);
                else
                    login.setEnabled(false);
            }
        });

        loginPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                refreshButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void refreshButton(){
        if(loginPhone.getText().toString().trim().length() == 11 && !TextUtils.isEmpty(loginPwd.getText().toString().trim())){
            login.setEnabled(true);
        }else{
            login.setEnabled(false);
        }
    }

    private void initHead() {
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("登录", getResources().getColor(R.color.white));
        getHeadBar().showRightText();
        getHeadBar().setRightTitle("注册", getResources().getColor(R.color.white));
        getHeadBar().setRightOnClickListner(v -> {
            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            startActivity(intent);
        });
    }

    @OnClick({R.id.tv_forgetpassword, R.id.login,R.id.iv_xianshi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forgetpassword:
                Intent forgetpasswordActivityIntent = new Intent(LoginActivity.this, ForgetPasswrodActivity.class);
                startActivity(forgetpasswordActivityIntent);
                break;
            case R.id.login:
                String phone = loginPhone.getText().toString().trim();
                String password = loginPwd.getText().toString().trim();
                LoginCheck loginCheck = new LoginCheck(phone, password);
//                String info = loginCheck.getCheckInfo();
//                if (!TextUtils.isEmpty(info)) {
//                    showToast(info);
//                    return;
//                }
                if(TextUtils.isEmpty(phone)){
                    showToast("请输入账号或手机号");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    showToast("请输入密码");
                    return;
                }
                ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
                UserService api = ApiUtil.createDefaultApi(UserService.class,ApiUtil.SMURL);
                ApiUtil.doDefaultApi(api.login(phone,password,"0"), user -> {
                    if(user.getResCode().equals("0")){
                        App.USERID = user.getE_sjzc_nm();
                        App.TOKEN = user.getToken();
                        PushManager pushManager = PushManager.getInstance();
                        ViewControl viewControl1 = ViewControlUtil.create2Dialog(getActivity());
                        UserService api1 = ApiUtil.createDefaultApi(UserService.class);
                        ApiUtil.doDefaultApi(api1.updateClientidForLogin(pushManager.getClientid(LoginActivity.this)), resEntity -> {
                            ViewControl viewControl11 = ViewControlUtil.create2Dialog(getActivity());
                            UserService api11 = ApiUtil.createDefaultApi(UserService.class);
                            ApiUtil.doDefaultApi(api11.getAccountInfomation(user.getE_sjzc_nm()), user1 -> {
                                user1.setToken(App.TOKEN);
                                user1.setPassword(password);
                                App.USERID = "";
                                App.TOKEN = "";
                                UserManager.getInstance().add(user1);
                                App.getInstance().getEasemobBinder().getService().login(user1.getESjzcSjh());
                                finish();
                            }, viewControl11);
                        }, viewControl1);
//                            DemoDBManager.getInstance().closeDB();
//                            App.getInstance().setCurrentUserName(phone);
//                            // 调用sdk登陆方法登陆聊天服务器
//                            Log.d(TAG, "EMClient.getInstance().login");
//                            EMClient.getInstance().login(phone, App.HXPASSWORD, new EMCallBack() {
//
//                                @Override
//                                public void onSuccess() {
//                                    Log.d(TAG, "login: onSuccess");
//                                    // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
//                                    // ** manually load all local groups and
//                                    EMClient.getInstance().groupManager().loadAllGroups();
//                                    EMClient.getInstance().chatManager().loadAllConversations();
//                                    getFriends();
//                                    Looper.prepare();
//
//                                    Looper.loop();
//                                }
//
//                                @Override
//                                public void onProgress(int progress, String status) {
//                                    Log.d(TAG, "login: onProgress");
//                                }
//
//                                @Override
//                                public void onError(final int code, final String message) {
//                                    Log.d(TAG, "login: onError: " + code);
//                                    runOnUiThread(new Runnable() {
//                                        public void run() {
//                                            Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
//                                                    Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                }
//                            });
                    }else{
                        showToast(user.getResMsg());
                    }
                }, viewControl);
                break;
            case R.id.iv_xianshi:
                if (hide) {
                    ivXianshi.setImageResource(R.drawable.hide);
                    loginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    ivXianshi.setImageResource(show);
                    loginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                hide = !hide;
                loginPwd.postInvalidate();
                loginPwd.setSelection(loginPwd.getText().toString().length());
            break;
        }
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
//    long back_time;
//
//    /**
//     * 退出
//     */
//    public void onBackPressed() {
//        if (System.currentTimeMillis() - back_time > 5 * 1000) {
//            back_time = System.currentTimeMillis();
//            showToast(R.string.finish_toast);
//            return;
//        }
//        ActivityManager.getInstance().exitAll();
//        super.onBackPressed();
//    }

}
