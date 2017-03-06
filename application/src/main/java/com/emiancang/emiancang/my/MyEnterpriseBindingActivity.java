package com.emiancang.emiancang.my;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
 * Created by 22310 on 2016/11/7.
 */

public class MyEnterpriseBindingActivity extends TitleActivity {

    @InjectView(R.id.invite_number)
    TextView inviteNumber;
    @InjectView(R.id.enterprise_quer_content)
    EditText enterpriseQuerContent;
    @InjectView(R.id.enterprise_query)
    TextView enterpriseQuery;
    @InjectView(R.id.enterprise_name)
    TextView enterpriseName;
    @InjectView(R.id.enterprise_result)
    TextView enterpriseResult;
    @InjectView(R.id.layout_query_result)
    LinearLayout layoutQueryResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_my_enterprise_binding, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("企业绑定", getResources().getColor(R.color.white));
    }

    @OnClick(R.id.enterprise_query)
    public void onClick() {
        String content = enterpriseQuerContent.getText().toString().trim();
        if(TextUtils.isEmpty(content)){
            showToast("查询内容不能为空");
            return;
        }

        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        UserService api = ApiUtil.createDefaultApi(UserService.class);
        ApiUtil.doDefaultApi(api.getByEnterpriseName(content), new HttpSucess<ResEntity>() {
            @Override
            public void onSucess(ResEntity resEntity) {
                if(resEntity.getIsRegister().equals("0")){
                    layoutQueryResult.setVisibility(View.VISIBLE);
                    enterpriseName.setText(content);
                    enterpriseResult.setText("该企业未被注册，您可申请成为企业账户");
                }else{
                    enterpriseName.setText(content);
                    enterpriseResult.setText("该企业已被注册");
                }
            }
        }, viewControl);
    }
}
