package com.emiancang.emiancang.main.home.findmoney.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.eventbean.FindMoneyPublishTypeTransfer;
import com.emiancang.emiancang.main.home.findmoney.adapter.FindMoneyPublishTypeSelectAdapter;
import com.emiancang.emiancang.main.home.findmoney.model.FindMoneyPublishTypeSelectModel;
import com.emiancang.emiancang.uitl.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by yuanyueqing on 2016/11/23.
 */

public class FindMoneyPublishTypeSelectActivity extends TitleActivity {

    @InjectView(R.id.lv_find_money_publish_type_select)
    ListView lv_find_money_publish_type_select;

    FindMoneyPublishTypeSelectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_money_publish_type_select);
        String type = getIntent().getStringExtra("type");
        initHead();
        initData(type);
    }

    private void initData(String type) {
        List<FindMoneyPublishTypeSelectModel> list = getData();
        adapter = new FindMoneyPublishTypeSelectAdapter(list);
        lv_find_money_publish_type_select.setAdapter(adapter);
        lv_find_money_publish_type_select.setOnItemClickListener((parent, view, position, id) -> {
            for (int i = 0; i < adapter.getList().size(); i++) {
                if( i == position ){
                    adapter.getList().get(i).setSelected(!adapter.getList().get(i).isSelected());
                } else {
                    adapter.getList().get(i).setSelected(false);
                }
            }
            EventBus.getDefault().post(new FindMoneyPublishTypeTransfer(adapter.getList().get(position).getName()));
            adapter.notifyDataSetInvalidated();
        });

        if(type.equals("质押融资")) {
            adapter.getList().get(0).setSelected(true);
            adapter.notifyDataSetInvalidated();
        }
        if(type.equals("白条")) {
            adapter.getList().get(1).setSelected(true);
            adapter.notifyDataSetInvalidated();
        }
        if(type.equals("运费补贴")) {
            adapter.getList().get(2).setSelected(true);
            adapter.notifyDataSetInvalidated();
        }
        if(type.equals("交易配资")) {
            adapter.getList().get(3).setSelected(true);
            adapter.notifyDataSetInvalidated();
        }
    }

    private List<FindMoneyPublishTypeSelectModel> getData() {
        List<FindMoneyPublishTypeSelectModel> list = new ArrayList<FindMoneyPublishTypeSelectModel>();
        FindMoneyPublishTypeSelectModel first = new FindMoneyPublishTypeSelectModel();
        first.setName("质押融资");
        first.setSelected(false);
        list.add(first);
        FindMoneyPublishTypeSelectModel second = new FindMoneyPublishTypeSelectModel();
        second.setName("白条");
        second.setSelected(false);
        list.add(second);
        FindMoneyPublishTypeSelectModel third = new FindMoneyPublishTypeSelectModel();
        third.setName("运费补贴");
        third.setSelected(false);
        list.add(third);
        FindMoneyPublishTypeSelectModel fourth = new FindMoneyPublishTypeSelectModel();
        fourth.setName("交易配资");
        fourth.setSelected(false);
        list.add(fourth);
        return list;
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(),R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("类型", getResources().getColor(R.color.white));
        getHeadBar().showLeftImage();
        getHeadBar().setLeftOnClickListner(v -> finish());
    }
}
