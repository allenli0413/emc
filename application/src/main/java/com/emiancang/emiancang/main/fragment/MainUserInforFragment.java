package com.emiancang.emiancang.main.fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emiancang.emiancang.eventbean.SalesmanBindTransfer;
import com.emiancang.emiancang.my.MySettingActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleFragment;
import com.emiancang.emiancang.base.TitleHeaderBar;
import com.emiancang.emiancang.bean.ResEntity;
import com.emiancang.emiancang.eventbean.CityTransfer;
import com.emiancang.emiancang.eventbean.SYSMSGTransfer;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.login.LoginActivity;
import com.emiancang.emiancang.login.StartActivity;
import com.emiancang.emiancang.my.MyCollectManage;
import com.emiancang.emiancang.my.MyIndentManage;
import com.emiancang.emiancang.my.MyRedPacketActivity;
import com.emiancang.emiancang.my.MyShoppingCartAdmin;
import com.emiancang.emiancang.my.MyShoppingCartSalesman;
import com.emiancang.emiancang.my.MyUserDataActivity;
import com.emiancang.emiancang.my.MyWarnActivity;
import com.emiancang.emiancang.my.MyWechatBindActivity;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 个人信息选项卡
 * Created by 22310 on 2016/4/8.
 */
public class MainUserInforFragment extends TitleFragment {

    @InjectView(R.id.my_user_data_avater)
    SimpleDraweeView myUserDataAvater;
    @InjectView(R.id.tv_main_user_info)
    TextView tv_main_user_info;
    @InjectView(R.id.my_user_name)
    TextView myUserName;
    @InjectView(R.id.my_user_phone)
    TextView myUserPhone;
    @InjectView(R.id.icon_my_user_data_avater_goto)
    TextView iconMyUserDataAvaterGoto;
    @InjectView(R.id.warn)
    ImageView warn;
    @InjectView(R.id.my_return)
    LinearLayout myReturn;
    @InjectView(R.id.my_collect)
    RelativeLayout myCollect;
    @InjectView(R.id.user_data)
    RelativeLayout userData;
    @InjectView(R.id.my_indent_payment)
    TextView myIndentPayment;
    @InjectView(R.id.my_indent_delivery)
    TextView myIndentDelivery;
    @InjectView(R.id.my_indent_accomplish)
    TextView myIndentAccomplish;
    @InjectView(R.id.my_indent_violate)
    TextView myIndentViolate;
    @InjectView(R.id.my_indent)
    TextView myIndent;
    @InjectView(R.id.icon_my_shopping_cart)
    ImageView iconMyShoppingCart;
    @InjectView(R.id.my_shopping_cart_number)
    TextView myShoppingCartNumber;
    @InjectView(R.id.icon_my_shopping_cart_goto)
    ImageView iconMyShoppingCartGoto;
    @InjectView(R.id.my_shopping_cart)
    RelativeLayout myShoppingCart;
    @InjectView(R.id.icon_my_wechat)
    ImageView iconMyWechat;
    @InjectView(R.id.my_wechat_state)
    TextView myWechatState;
    @InjectView(R.id.icon_my_wechat_goto)
    ImageView iconMyWechatGoto;
    @InjectView(R.id.my_wechat)
    RelativeLayout myWechat;
    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.icon_my_connection)
    ImageView iconMyConnection;
    @InjectView(R.id.my_connection_content)
    TextView myConnectionContent;
    @InjectView(R.id.my_connection)
    RelativeLayout myConnection;
    @InjectView(R.id.icon_my_cooperation)
    ImageView iconMyCooperation;
    @InjectView(R.id.my_cooperation_content)
    TextView myCooperationContent;
    @InjectView(R.id.my_cooperation)
    RelativeLayout myCooperation;
    @InjectView(R.id.icon_mine_setting)
    ImageView iconMineSetting;
    @InjectView(R.id.mine_setting)
    RelativeLayout mineSetting;

    private AlertDialog mShowLoginDialog;

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_main_user, null);
        ButterKnife.inject(this, view);
        EventBus.getDefault().register(this);
        initHead();
        initView();
        return view;
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
    }

    private void initView() {
        myConnection.setOnClickListener(onClickNoUserLogin());
        myCooperation.setOnClickListener(onClickNoUserLogin());
        mineSetting.setOnClickListener(onClickNoUserLogin());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @OnClick({R.id.user_data, R.id.my_indent, R.id.my_shopping_cart, R.id.my_return, R.id.my_collect, R.id.my_wechat, R.id.warn,R.id.my_indent_payment,R.id.my_indent_delivery,R.id.my_indent_violate,R.id.my_indent_accomplish})
    public void onClick(View view) {
        if (!UserManager.getInstance().isLogin()) {
            isLogin();
            return;
        }
        switch (view.getId()) {
            case R.id.user_data:
                //用户数据
                startActivity(createIntent(MyUserDataActivity.class));
                break;
            case R.id.my_indent:
                //订单管理
                Intent all = createIntent(MyIndentManage.class);
                all.putExtra("type",0);
                startActivity(all);
                break;
            case R.id.my_shopping_cart:
                //购物车管理
                if(UserManager.getInstance().getUser().getESjzcHylb().equals("1")){
                    //管理员
                    startActivity(createIntent(MyShoppingCartAdmin.class));
                }else{
                    startActivity(createIntent(MyShoppingCartSalesman.class));
                }
                break;
            case R.id.my_return:
                //返现管理
                startActivity(createIntent(MyRedPacketActivity.class));
                break;
            case R.id.my_collect:
                //收藏管理
                startActivity(createIntent(MyCollectManage.class));
                break;
            case R.id.my_wechat:
                //微信绑定
                startActivity(createIntent(MyWechatBindActivity.class));
                break;
            case R.id.warn:
                //消息中心
                startActivity(createIntent(MyWarnActivity.class));
                break;
            case R.id.my_indent_payment:
                Intent payment = createIntent(MyIndentManage.class);
                payment.putExtra("type",1);
                startActivity(payment);
                break;
            case R.id.my_indent_delivery:
                Intent delivery = createIntent(MyIndentManage.class);
                delivery.putExtra("type",2);
                startActivity(delivery);
                break;
            case R.id.my_indent_accomplish:
                Intent accomplish = createIntent(MyIndentManage.class);
                accomplish.putExtra("type",3);
                startActivity(accomplish);
                break;
            case R.id.my_indent_violate:
                Intent violate = createIntent(MyIndentManage.class);
                violate.putExtra("type",4);
                startActivity(violate);
                break;
        }
    }

    public View.OnClickListener onClickNoUserLogin() {
        return v -> {
            switch (v.getId()) {
                case R.id.my_connection:
                    //联系我们
                    Intent connectionPhone = new Intent(Intent.ACTION_DIAL);
                    connectionPhone.setData(Uri.parse("tel:" + getResources().getString(R.string.phone_connection)));
                    startActivity(connectionPhone);
                    break;
                case R.id.my_cooperation:
                    //我要合作
                    Intent cooperationPhone = new Intent(Intent.ACTION_DIAL);
                    cooperationPhone.setData(Uri.parse("tel:" + getResources().getString(R.string.phone_cooperation)));
                    startActivity(cooperationPhone);
                    break;
                case R.id.mine_setting:
                    //设置
                    startActivity(createIntent(MySettingActivity.class));
                    break;
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserManager.getInstance().isLogin()) {
            myUserName.setVisibility(View.VISIBLE);
            myUserPhone.setVisibility(View.VISIBLE);
            tv_main_user_info.setVisibility(View.GONE);
            //先赋值当前用户的信息
            User u = UserManager.getInstance().getUser();
            refreshUser(u);
            //再请求数据刷新
            ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
            UserService api = ApiUtil.createDefaultApi(UserService.class);
            ApiUtil.doDefaultApi(api.getAccountInfomation(UserManager.getInstance().getUser().getESjzcNm()), user -> {
                user.setToken(u.getToken());
                user.setPassword(u.getPassword());
                UserManager.getInstance().add(user);
                refreshUser(user);
            }, viewControl);
        } else {
            myUserName.setVisibility(View.GONE);
            myUserPhone.setVisibility(View.GONE);
            tv_main_user_info.setVisibility(View.VISIBLE);
            myUserName.setText("请登录");
            myUserPhone.setVisibility(View.INVISIBLE);
            myUserDataAvater.setImageDrawable(getResources().getDrawable(R.drawable.icon_photo_holder));
        }
    }

    public void onEventMainThread(SYSMSGTransfer sysmsgTransfer) {
        if(sysmsgTransfer.isType())
            warn.setImageDrawable(getResources().getDrawable(R.drawable.icon_pro_message));
        else
            warn.setImageDrawable(getResources().getDrawable(R.drawable.icon_pro_nomessage));
    }

    public void onEventMainThread(SalesmanBindTransfer salesmanBindTransfer){
        if (UserManager.getInstance().isLogin()) {
            //先赋值当前用户的信息
            User u = UserManager.getInstance().getUser();
            refreshUser(u);
            //再请求数据刷新
            ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
            UserService api = ApiUtil.createDefaultApi(UserService.class);
            ApiUtil.doDefaultApi(api.getAccountInfomation(UserManager.getInstance().getUser().getESjzcNm()), user -> {
                user.setToken(u.getToken());
                user.setPassword(u.getPassword());
                UserManager.getInstance().add(user);
                refreshUser(user);
            }, viewControl);
        }
    }

    //判断用户是否登陆
    private void isLogin() {
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_servicelist_dialog, null);
        TextView title_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_title_tv);
        TextView content_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_content_tv);
        TextView verify_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_verify_tv);
        TextView cancel_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_cancel_tv);
        title_tv.setText("提示");
        content_tv.setText("未登录状态，没有权限查看该内容，是否选择登录？");
        verify_tv.setOnClickListener(v -> {
            //登录页面
            startActivity(createIntent(LoginActivity.class));
            mShowLoginDialog.dismiss();
        });
        cancel_tv.setOnClickListener(v -> mShowLoginDialog.dismiss());
        mShowLoginDialog = new AlertDialog.Builder(getActivity()).setView(dialogView).create();
        mShowLoginDialog.show();
    }

    @Override
    public TitleHeaderBar initHeadBar(ViewGroup parent) {
        return null;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    public void refreshUser(User user){
        if(user.getESjzcHylb().equals("0")) {
            myUserName.setText(user.getESjzcXm());
        } else {
            myUserName.setText(user.getCustName());
        }
        myUserPhone.setText(user.getESjzcSjh());
        myUserPhone.setVisibility(View.VISIBLE);
        //用户头像
        if (!TextUtils.isEmpty(user.getEYhtx())) {
            myUserDataAvater.setImageURI(Uri.parse(user.getEYhtx()));
        }else{
            myUserDataAvater.setImageDrawable(getResources().getDrawable(R.drawable.icon_photo_holder));
        }
        if(user.getIsBindWechat() == 0)
            myWechatState.setText("未绑定");
        else
            myWechatState.setText("已绑定");
        myShoppingCartNumber.setText(user.getShoppingCartNum() + "");
    }
}
