package com.emiancang.emiancang.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.zwyl.Logger;
import com.emiancang.emiancang.App;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.uitl.ZwyFileOption;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by yuanyueqing on 2016/11/14.
 */

public class MyWechatBindActivity extends TitleActivity {

    @InjectView(R.id.tv_my_wechat_bind_status)
    TextView tv_my_wechat_bind_status;
    @InjectView(R.id.iv_my_wechat_bind_status)
    ImageView iv_my_wechat_bind_status;
    @InjectView(R.id.iv_my_wechat_bind_pic)
    ImageView iv_my_wechat_bind_pic;
    @InjectView(R.id.ll_my_wechat_unbind_content)
    LinearLayout ll_my_wechat_unbind_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wechat_bind);
        ButterKnife.inject(this);
        initHead();
        initView();
    }

    private void initView() {
        if(UserManager.getInstance().getUser().getIsBindWechat() == 1) {
            tv_my_wechat_bind_status.setText("微信账号已绑定");
            iv_my_wechat_bind_status.setImageResource(R.drawable.weixin_btn);
            ll_my_wechat_unbind_content.setVisibility(View.GONE);
        }else{
            tv_my_wechat_bind_status.setText("微信账号未绑定");
            iv_my_wechat_bind_status.setImageResource(R.drawable.icon_wechat_wbd);
            ll_my_wechat_unbind_content.setVisibility(View.VISIBLE);
        }
        iv_my_wechat_bind_pic.setOnLongClickListener(v -> {
            ZwyFileOption.saveImageToGallery(getActivity(), convertViewToBitmap(iv_my_wechat_bind_pic), "微信公众号二维码");
            showToast("二维码保存成功");
            return false;
        });
    }

    private void initHead() {
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        Util.setStatusBarColor(getActivity(),R.color.green);
        getHeadBar().setCenterTitle("微信绑定", getResources().getColor(R.color.white));
    }

    public static Bitmap convertViewToBitmap(View view){
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

         return bitmap;
    }

}
