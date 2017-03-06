package com.emiancang.emiancang.my;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.main.MainActivity;
import com.emiancang.emiancang.uitl.Util;

import butterknife.InjectView;


/**
 * Created by 22310 on 2016/11/7.
 */

public class EnterpriseCheckActivity extends TitleActivity {

    private int type = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_enterprise_check, null);
        setContentView(view);
        initHead();

    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(),R.color.green);
        type = getIntExtra("type");
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("企业资质",getResources().getColor(R.color.white));
        getHeadBar().setLeftOnClickListner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == -1)
                    startActivity(createIntent(MainActivity.class));
                finish();
            }
        });
    }
}
