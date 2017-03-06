package com.emiancang.emiancang.my;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.litesuits.common.utils.EncryptionUtils;
import com.litesuits.common.utils.InputMethodUtils;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.zwyl.ZwyOSInfo.sign;

/**
 * Created by 22310 on 2016/11/7.
 */

public class MyUserUpdateName extends TitleActivity {

    @InjectView(R.id.register_pwd_et)
    EditText registerPwdEt;
    @InjectView(R.id.iv_xianshi)
    LinearLayout ivXianshi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_user_update_name, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(UserManager.getInstance().getUser().getESjzcXm()))
            registerPwdEt.setText(UserManager.getInstance().getUser().getESjzcXm());
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("联系人", getResources().getColor(R.color.white));
        getHeadBar().showRightText();
        getHeadBar().setRightTitle("确定", getResources().getColor(R.color.white));
        getHeadBar().hideLeftImage();
        getHeadBar().showLeftText();
        getHeadBar().setLeftTitle("取消");
        getHeadBar().setLeftOnClickListner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodUtils.hideSoftInput(getActivity());
                finish();
            }
        });
        getHeadBar().setRightOnClickListner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = registerPwdEt.getText().toString().trim();
                if(!TextUtils.isEmpty(name)){
                    ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
                    UserService api = ApiUtil.createDefaultApi(UserService.class);
                    ApiUtil.doDefaultApi(api.updateName(name), new HttpSucess<User>() {
                        @Override
                        public void onSucess(User user) {
                            User u = UserManager.getInstance().getUser();
                            user.setToken(u.getToken());
                            user.setPassword(u.getPassword());
                            UserManager.getInstance().add(user);
                            showToast("修改成功");
                            finish();
                        }
                    }, viewControl);
                }else{
                    showToast("联系人姓名不能为空");
                }
            }
        });
    }

    @OnClick(R.id.iv_xianshi)
    public void onClick() {
        registerPwdEt.setText("");
    }
}
