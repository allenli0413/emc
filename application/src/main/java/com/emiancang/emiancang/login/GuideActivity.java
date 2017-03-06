package com.emiancang.emiancang.login;

import android.os.Bundle;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.zwyl.BaseActivity;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.login.adapter.NetworkGuideView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by 22310 on 2016/6/2.
 */
public class GuideActivity extends BaseActivity {

//    @InjectView(R.id.convenientguide)
    ConvenientBanner convenientguide;

    private List<Integer> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.inject(this);
        convenientguide = (ConvenientBanner) findViewById(R.id.convenientguide);
        imageList = new ArrayList<Integer>();
        imageList.add(R.drawable.bg_login_guide_1);
        imageList.add(R.drawable.bg_login_guide_2);
        convenientguide.setPages(new CBViewHolderCreator<NetworkGuideView>() {
            @Override
            public NetworkGuideView createHolder() {
                return new NetworkGuideView(convenientguide,GuideActivity.this);
            }
        }, imageList).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageIndicator(new int[]{R.drawable.page_indicator, R.drawable.page_indicator_focused}).setCanLoop(false);
    }
}
