package com.emiancang.emiancang.main.home.qualityquery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.emiancang.emiancang.main.home.qualityquery.model.QualityContrastDetailStatusModel;
import com.emiancang.emiancang.main.home.qualityquery.service.QualityContrastDetailStatusService;
import com.emiancang.emiancang.nearby.NearbyDetailsErrorChatActivity;
import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.main.home.qualityquery.service.HelpMeFindService;
import com.emiancang.emiancang.uitl.Util;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by yuanyueqing on 2016/11/23.
 */

public class HelpMeFindActivity extends TitleActivity {

    @InjectView(R.id.edit_help_me_find_content)
    EditText edit_help_me_find_content;

    String ph = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_me_find);
        ButterKnife.inject(this);
        initHead();
        ph = getIntent().getStringExtra("ph");
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(),R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("帮我找", getResources().getColor(R.color.white));
        getHeadBar().showLeftImage();
        getHeadBar().setLeftOnClickListner(v -> finish());
    }

    @OnClick(R.id.tv_help_me_find_submit)
    void onClick(View view){
        String content = edit_help_me_find_content.getText().toString().trim();
        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        HelpMeFindService api = ApiUtil.createDefaultApi(HelpMeFindService.class);
        ApiUtil.doDefaultApi(api.submit(ph, content), data -> {
            //TODO 跳转客服页
//            startActivity(createIntent(HelpMeFindResultActivity.class));
            judgeFind();
//            finish();
        },viewControl);
    }

    private void judgeFind() {
        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        QualityContrastDetailStatusService api = ApiUtil.createDefaultApi(QualityContrastDetailStatusService.class);
        ApiUtil.doDefaultApi(api.check("", ph),data -> {
            if(data.getSfbwz().equals("0")){
                Intent intent = createIntent(HelpMeFindActivity.class);
                intent.putExtra("ph", ph);
                startActivity(intent);
            }
            if(data.getSfbwz().equals("1")){
                goToFind(data);
            }
        },viewControl);
    }

    private void goToFind(QualityContrastDetailStatusModel data) {
        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        QualityContrastDetailStatusService api = ApiUtil.createDefaultApi(QualityContrastDetailStatusService.class);
        ApiUtil.doDefaultApi(api.findQuery(data.geteZSid()), data1 -> {
            //TODO 带data跳转到假对话页
            Intent errorChat = createIntent(NearbyDetailsErrorChatActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", data1);
            errorChat.putExtras(bundle);
            startActivity(errorChat);
            finish();
        }, viewControl);
    }
}
