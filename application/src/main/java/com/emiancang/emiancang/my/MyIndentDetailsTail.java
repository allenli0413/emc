package com.emiancang.emiancang.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.litesuits.android.log.Log;
import com.mayigeek.frame.http.state.HttpError;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.bean.IndentDetailsTailEntity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.my.adapter.AdapterIndentDetailsTail;
import com.emiancang.emiancang.uitl.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import rx.Observable;

/**
 * Created by 22310 on 2016/11/10.
 */

public class MyIndentDetailsTail extends TitleActivity {

    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;

    private AdapterIndentDetailsTail mAdapter= new AdapterIndentDetailsTail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_indent_details_tail, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("订单跟踪",getResources().getColor(R.color.white));
    }

    private void initView() {
        List<IndentDetailsTailEntity> data = (List<IndentDetailsTailEntity>) getIntent().getSerializableExtra("date");
        listView.setAdapter(mAdapter);
        mAdapter.addList(data);
        mAdapter.notifyDataSetChanged();
    }
}
