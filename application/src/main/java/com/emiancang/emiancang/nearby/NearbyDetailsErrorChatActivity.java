package com.emiancang.emiancang.nearby;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.bean.CompanyErrorInfo;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.main.home.qualityquery.model.HelpMeContentModel;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.Util;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 22310 on 2016/11/13.
 */

public class NearbyDetailsErrorChatActivity extends TitleActivity {


    @InjectView(R.id.rightAvatar)
    SimpleDraweeView rightAvatar;
    @InjectView(R.id.rightMessage)
    TextView rightMessage;
    @InjectView(R.id.right_Message_layout)
    RelativeLayout rightMessageLayout;
    @InjectView(R.id.rightDate)
    TextView rightDate;
    @InjectView(R.id.rightPanel)
    RelativeLayout rightPanel;
    @InjectView(R.id.leftAvatar)
    SimpleDraweeView leftAvatar;
    @InjectView(R.id.leftMessage)
    TextView leftMessage;
    @InjectView(R.id.leftDate)
    TextView leftDate;
    @InjectView(R.id.leftPanel)
    RelativeLayout leftPanel;
    @InjectView(R.id.left_Message_layout)
    RelativeLayout leftMessageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_nearby_details_error_chat, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();
    }

    private void initView() {
        String id = getStringExtra("id");
        HelpMeContentModel data = (HelpMeContentModel) getIntent().getSerializableExtra("data");
        if (data != null) {
            if (!TextUtils.isEmpty(data.geteZFbrYhtx()))
                rightAvatar.setImageURI(Uri.parse(data.geteZFbrYhtx()));
            rightMessage.setText(data.geteZSinfo());
            rightDate.setText(data.geteZScreateTime());
            if (TextUtils.isEmpty(data.geteZSreplyInfo())) {
                leftPanel.setVisibility(View.INVISIBLE);
            } else {
                if (!TextUtils.isEmpty(data.geteZHfrYhtx()))
                    leftAvatar.setImageURI(Uri.parse(data.geteZHfrYhtx()));
                leftPanel.setVisibility(View.VISIBLE);
                leftMessage.setText(data.geteZSreplyInfo());
                leftDate.setText(data.geteZReplyTime());
            }
        }
        if (!TextUtils.isEmpty(id)) {
            ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
            UserService api = ApiUtil.createDefaultApi(UserService.class);
            ApiUtil.doDefaultApi(api.getCompanyErrorInfo(id), new HttpSucess<CompanyErrorInfo>() {
                @Override
                public void onSucess(CompanyErrorInfo companyErrorInfo) {
                    if (!TextUtils.isEmpty(companyErrorInfo.getFinderrorUserYhtx()))
                        rightAvatar.setImageURI(Uri.parse(companyErrorInfo.getFinderrorUserYhtx()));
                    rightMessage.setText(companyErrorInfo.getFinderroeContent());
                    rightDate.setText(companyErrorInfo.getFinderroeDate());
                    if (TextUtils.isEmpty(companyErrorInfo.getReplyContent())) {
                        leftAvatar.setVisibility(View.INVISIBLE);
                        leftMessageLayout.setVisibility(View.INVISIBLE);
                        leftMessage.setVisibility(View.INVISIBLE);
                        leftDate.setVisibility(View.INVISIBLE);
                    } else {
                        if (!TextUtils.isEmpty(companyErrorInfo.getFinderrorReplyYhtx()))
                            leftAvatar.setImageURI(Uri.parse(companyErrorInfo.getFinderrorReplyYhtx()));
                        leftMessage.setVisibility(View.VISIBLE);
                        leftMessageLayout.setVisibility(View.VISIBLE);
                        leftDate.setVisibility(View.VISIBLE);
                        leftMessage.setText(companyErrorInfo.getReplyContent());
                        leftDate.setText(companyErrorInfo.getReplyDate());
                    }
                }
            }, viewControl);
        }
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("企业信息纠错", getResources().getColor(R.color.white));
    }
}
