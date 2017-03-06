package com.emiancang.emiancang.my.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleFragment;
import com.emiancang.emiancang.bean.MoneyInfo;
import com.emiancang.emiancang.my.adapter.AdapterRedpacket;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import rx.Observable;

/**
 * Created by 22310 on 2016/11/10.
 */

public class RedPacketFragment extends TitleFragment {


    @InjectView(R.id.listView)
    ListView listView;

    private AdapterRedpacket mAdapter = new AdapterRedpacket();

    List<MoneyInfo.ListBean> mData;

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_red_packet, null);
        ButterKnife.inject(this, view);
        getHeadBar().hideHeader();
        initView();
        return view;
    }

    private void initView() {
        mData = new ArrayList<>();

        listView.setAdapter(mAdapter);
        mAdapter.addList(mData);
        mAdapter.notifyDataSetChanged();
    }

    public void setData(List<MoneyInfo.ListBean> data){
        this.mData = data;
        mAdapter.clear();
        mAdapter.addList(mData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
