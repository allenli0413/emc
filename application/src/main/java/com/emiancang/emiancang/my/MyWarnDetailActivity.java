package com.emiancang.emiancang.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.bean.WarnEntity;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.User;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 22310 on 2016/11/14.
 */

public class MyWarnDetailActivity extends TitleActivity {
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.date)
    TextView date;
    @InjectView(R.id.content)
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_warn_detail, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();

    }

    private void initView() {
        Intent intent = this.getIntent();
        WarnEntity warn=(WarnEntity)intent.getSerializableExtra("warn");

        if(warn != null){
            title.setText(warn.getTitle());
            date.setText(warn.getCreateTime());
            content.setText(warn.getSysContent());
        }
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("系统消息", getResources().getColor(R.color.white));
    }
}
