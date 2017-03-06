package com.emiancang.emiancang.login.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.Holder;
import com.tencent.connect.UserInfo;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.login.GuideActivity;
import com.emiancang.emiancang.main.MainActivity;
import com.emiancang.emiancang.uitl.JsonUtil;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;
import com.zwyl.http.SimpleHttpResponHandler;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Sai on 15/8/4.
 * 图片加载
 */
public class NetworkGuideView implements Holder<Integer> {


    private GuideActivity mActivity;

    public NetworkGuideView(){}

    public NetworkGuideView(ConvenientBanner cb, GuideActivity activity){
        mConvenientBanner = cb;
        this.mActivity = activity;
    }

    @InjectView(R.id.guide_item_bg)
    ImageView guideItemBg;
    @InjectView(R.id.btn_login_register)
    Button btnLoginRegister;
    @InjectView(R.id.guide_layout)
    LinearLayout guideLayout;

    private Context mContext;
    private ConvenientBanner mConvenientBanner;

    @Override
    public View createView(Context context) {
        mContext = context;
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        View view = View.inflate(context, R.layout.adapter_guide_item, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer drawable) {
        guideItemBg.setBackground(context.getResources().getDrawable(drawable));
        if (position == 1) {
            guideLayout.setVisibility(View.VISIBLE);
            btnLoginRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.startActivity(mActivity.createIntent(MainActivity.class));
                    mActivity.finish();
                }
            });
        }else{
            guideLayout.setVisibility(View.GONE);
        }
    }

}
