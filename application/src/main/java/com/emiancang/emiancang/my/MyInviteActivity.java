package com.emiancang.emiancang.my;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;


import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.LoginBaseActivity;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.UserManager;
import com.emiancang.emiancang.view.ShareWindow;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.emiancang.emiancang.update.UpdateManager.url;


/**
 * Created by 22310 on 2016/11/7.
 */

public class MyInviteActivity extends LoginBaseActivity {

    @InjectView(R.id.invite_number)
    TextView inviteNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_my_invite, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(),R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("邀请码",getResources().getColor(R.color.white));
        getHeadBar().showRightImage();
        getHeadBar().setRightImage(R.drawable.share_white);
        getHeadBar().setRightOnClickListner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShareWindow();
            }
        });
        inviteNumber.setText(UserManager.getInstance().getUser().getE_yqm());
    }


    private void showShareWindow() {
        ShareWindow shareWindow = new ShareWindow(getActivity(), v -> {
            switch (v.getId()) {
                case R.id.qq_share:
                    shareUrlToQQ(UserManager.getInstance().getUser().getE_yqm(), "分享一个e棉仓的注册码，大家快来注册！", url);
                    break;
                case R.id.wechat_share:
                    shareWebpageWechat(UserManager.getInstance().getUser().getE_yqm(), "分享一个e棉仓的注册码，大家快来注册！", url, true);
                    break;
                case R.id.wechatfriend_share:
                    shareWebpageWechat(UserManager.getInstance().getUser().getE_yqm(), "分享一个e棉仓的注册码，大家快来注册！", url, false);
                    break;
                case R.id.qzone_share:
                    shareUrlToQZone(UserManager.getInstance().getUser().getE_yqm(), "分享一个e棉仓的注册码，大家快来注册！", url);
                    break;
                case R.id.weibo_share:
//                    shareSina(url, UserManager.getInstance().getUser().getE_yqm(), "分享一个e棉仓的注册码，大家快来注册！", "分享一个e棉仓的注册码，大家快来注册！", null);
                    Bitmap thumb = null;
                    if(thumb==null){
                        thumb = BitmapFactory.decodeResource(getActivity().getResources(),
                                R.drawable.logo_real);
                    }
                    Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb, 50, 50, true);
                    shareWebpage(createWebpageObj(UserManager.getInstance().getUser().getE_yqm(), "分享一个e棉仓的注册码，大家快来注册！", thumbBmp, url), createTextObj("分享一个e棉仓的注册码，大家快来注册！"), createImageObj(thumbBmp));
                    thumb.recycle();
                    thumbBmp.recycle();
                    break;
            }
        });
        shareWindow.showAtLocation(findViewById(R.id.invite_number), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }
}
