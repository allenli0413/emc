package com.emiancang.emiancang.my;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.litesuits.android.log.Log;
import com.mayigeek.frame.http.state.HttpError;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.bean.WarehouseNameEntity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.my.adapter.AdapterWarehouseName;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.JsonUtil;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;
import com.emiancang.emiancang.uitl.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import rx.Observable;

import static android.R.id.list;

/**
 * Created by 22310 on 2016/11/7.
 */

public class WarehouseNameActivity extends TitleActivity {


    @InjectView(R.id.iamge)
    ImageView leftImage;
    @InjectView(R.id.confirm)
    TextView confirm;
    @InjectView(R.id.edit_title_search_middle)
    EditText editTitleSearchMiddle;
    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;

    private AdapterWarehouseName mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_warehouse_name, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("仓库名称", getResources().getColor(R.color.white));
        getHeadBar().hideHeader();
    }

    private void initView() {
        leftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAdapter = new AdapterWarehouseName();
        listView.setAdapter(mAdapter);
        if(TextUtils.isEmpty(SharedPrefsUtil.getValue(WarehouseNameActivity.this,SharedPrefsUtil.WAREHOUSEID,""))){
            confirm.setText("确定");
        }else{
            ArrayList<String> list = JsonUtil.fromJson(SharedPrefsUtil.getValue(WarehouseNameActivity.this,SharedPrefsUtil.WAREHOUSEID,""),new TypeToken<List<String>>() {}.getType());
            if(list.size() == 0){
                confirm.setText("确定");
            }else{
                confirm.setText("确定("+list.size()+")");
            }
            mAdapter.setSelectList(list);
            mAdapter.notifyDataSetChanged();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WarehouseNameEntity entity = mAdapter.getItem(position);
                if(TextUtils.isEmpty(SharedPrefsUtil.getValue(WarehouseNameActivity.this,SharedPrefsUtil.WAREHOUSEID,""))){
                    List<String> list = new ArrayList<String>();
                    list.add(entity.getHyCkbNm());
                    SharedPrefsUtil.putValue(WarehouseNameActivity.this,SharedPrefsUtil.WAREHOUSEID,JsonUtil.toJson(list));
                }else{
                    ArrayList<String> list = JsonUtil.fromJson(SharedPrefsUtil.getValue(WarehouseNameActivity.this,SharedPrefsUtil.WAREHOUSEID,""),new TypeToken<List<String>>() {}.getType());
                    for (int i  = 0; i<list.size();i++){
                        String item = list.get(i);
                        if(entity.getHyCkbNm().equals(item)){
                            list.remove(i);
                            SharedPrefsUtil.putValue(WarehouseNameActivity.this,SharedPrefsUtil.WAREHOUSEID,JsonUtil.toJson(list));
                            if(list.size() == 0){
                                confirm.setText("确定");
                            }else{
                                confirm.setText("确定("+list.size()+")");
                            }
                            mAdapter.setSelectList(list);
                            mAdapter.notifyDataSetChanged();
                            return;
                        }
                    }
                    list.add(entity.getHyCkbNm());
                    SharedPrefsUtil.putValue(WarehouseNameActivity.this,SharedPrefsUtil.WAREHOUSEID,JsonUtil.toJson(list));
                }
                ArrayList<String> list = JsonUtil.fromJson(SharedPrefsUtil.getValue(WarehouseNameActivity.this,SharedPrefsUtil.WAREHOUSEID,""),new TypeToken<List<String>>() {}.getType());
                if(list.size() == 0){
                    confirm.setText("确定");
                }else{
                    confirm.setText("确定("+list.size()+")");
                }
                mAdapter.setSelectList(list);
                mAdapter.notifyDataSetChanged();
            }
        });
        editTitleSearchMiddle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = editTitleSearchMiddle.getText().toString().trim();
                if (!TextUtils.isEmpty(search)) {
                    //获取服务器刷新数据
                    mAdapter.clear();
                    refresh(search);
                } else {
                    mAdapter.clear();
                    refresh("");
                }
            }
        });
        refresh("");
    }

    public void refresh(String search) {
        ViewControl viewControl = ViewControlUtil.create2View(nestRefreshLayout);
        NestRefreshManager<WarehouseNameEntity> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, new NestRefreshManager.CreateApi<WarehouseNameEntity>() {
            @Override
            public Observable<List<WarehouseNameEntity>> run(int page, int perPage) {
                UserService api = ApiUtil.createDefaultApi(UserService.class);
                return api.getHYckbList(search, "" + (page + 1), "" + perPage).map(new HttpResultFunc<>());
            }
        });
        nestRefreshManager.setPullLoadEnable(true);
        nestRefreshManager.setHttpError(new HttpError() {
            @Override
            public void onError(Throwable e) {
                Log.i("test", e.toString());
            }
        });
        nestRefreshManager.setCallBack(new NestRefreshManager.CallBack<WarehouseNameEntity>() {
            @Override
            public void call(List<? extends WarehouseNameEntity> allList, List<? extends WarehouseNameEntity> currentList) {
                mAdapter.clear();
                mAdapter.addList(allList);
                mAdapter.notifyDataSetChanged();
            }
        });
        nestRefreshManager.doApi();
    }

}
