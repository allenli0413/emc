package com.emiancang.emiancang.nearby;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.bean.ResEntity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.Util;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 22310 on 2016/11/13.
 */

public class NearbyDetailsErrorActivity extends TitleActivity {
    @InjectView(R.id.nearby_error_content)
    EditText nearbyErrorContent;
    @InjectView(R.id.nearby_error_submit)
    TextView nearbyErrorSubmit;

    private String companyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_nearby_details_error, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();
    }

    private void initView() {
        companyId = getStringExtra("id");
        nearbyErrorSubmit.setEnabled(false);
        nearbyErrorContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(nearbyErrorContent.getText().toString().trim()))
                    nearbyErrorSubmit.setEnabled(false);
                else
                    nearbyErrorSubmit.setEnabled(true);
            }
        });
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("企业信息纠错", getResources().getColor(R.color.white));
    }

    @OnClick(R.id.nearby_error_submit)
    public void onClick() {
        String finderroeContent = nearbyErrorContent.getText().toString().trim();
        if(TextUtils.isEmpty(finderroeContent)){
            showToast("请输入纠错内容再提交");
            return;
        }

        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        UserService api = ApiUtil.createDefaultApi(UserService.class);
        ApiUtil.doDefaultApi(api.submitErrorInfo(companyId,finderroeContent), resEntity -> {
            showToast("提交成功");
            Intent errorChat = createIntent(NearbyDetailsErrorChatActivity.class);
            errorChat.putExtra("id", companyId);
            startActivity(errorChat);
            finish();
        }, viewControl);

    }
}
