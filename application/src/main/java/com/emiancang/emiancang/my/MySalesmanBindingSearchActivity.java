package com.emiancang.emiancang.my;

import android.app.AlertDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.emiancang.emiancang.eventbean.SalesmanBindTransfer;
import com.litesuits.android.log.Log;
import com.mayigeek.frame.http.state.HttpError;
import com.mayigeek.frame.http.state.HttpFinish;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.zwyl.BaseActivity;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.bean.OrbindSalesman;
import com.emiancang.emiancang.bean.SalesmanMangeEntity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.my.adapter.AdapterBindingContacts;
import com.emiancang.emiancang.my.adapter.AdapterBindingContactsSearch;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.UserManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import de.greenrobot.event.EventBus;
import rx.Observable;

/**
 * Created by 22310 on 2016/11/9.
 */

public class MySalesmanBindingSearchActivity extends BaseActivity {


    @InjectView(R.id.salesman_search)
    EditText salesmanSearch;
    @InjectView(R.id.salesman_cancel)
    TextView salesmanCancel;
    @InjectView(R.id.listView_search)
    ListView listViewSearch;
    @InjectView(R.id.nestRefreshLayout_search)
    NestRefreshLayout nestRefreshLayoutSearch;
    private AdapterBindingContacts mContactsAdapter;
    private AdapterBindingContactsSearch mContactsSearchAdapter;
    private AlertDialog mShowLoginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_salesman_binding_search, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();
    }


    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
    }

    private void initView() {
        salesmanSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = salesmanSearch.getText().toString().trim();
                if(TextUtils.isEmpty(search)){
                    refreshContactsSearch("");
                    nestRefreshLayoutSearch.setVisibility(View.VISIBLE);
                }else{
                    if(search.length() == 11){
                        //获取服务器刷新数据
                        nestRefreshLayoutSearch.setVisibility(View.VISIBLE);
                        refreshContactsSearch(search);
                    }else{
                        nestRefreshLayoutSearch.setVisibility(View.GONE);

                    }
                }
            }
        });
        String phone = getIntent().getStringExtra("phone");
        if(TextUtils.isEmpty(phone))
            refreshContactsSearch("");
        else
            salesmanSearch.setText(phone);
        listViewSearch.setOnItemClickListener((parent, view, position, id) -> binding(mContactsSearchAdapter.getItem(position).getESjzcNm()));
    }

    public void refreshContactsSearch(String search) {
        mContactsSearchAdapter = new AdapterBindingContactsSearch();
        listViewSearch.setAdapter(mContactsSearchAdapter);
        ViewControl viewControl = ViewControlUtil.create2View(nestRefreshLayoutSearch,"没有搜索出业务员信息","没有搜索出业务员信息",R.drawable.icon_content_empty);
        NestRefreshManager<SalesmanMangeEntity> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayoutSearch, viewControl, (page, perPage) -> {
            UserService api = ApiUtil.createDefaultApi(UserService.class);
            return api.getSalesmanListMobile(search).map(new HttpResultFunc<>());
        });
        nestRefreshManager.setPullLoadEnable(true);
        nestRefreshManager.setHttpError(e -> Log.i("test", e.toString()));
        nestRefreshManager.setCallBack((allList, currentList) -> {
            mContactsSearchAdapter.clear();
            mContactsSearchAdapter.addList(allList);
            mContactsSearchAdapter.notifyDataSetChanged();
        });
        nestRefreshManager.doApi();
    }

    @OnClick(R.id.salesman_cancel)
    public void onClick() {
        finish();
    }

    private void binding(String id) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.activity_servicelist_dialog, null);
        TextView title_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_title_tv);
        TextView content_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_content_tv);
        TextView verify_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_verify_tv);
        TextView cancel_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_cancel_tv);
        title_tv.setText("业务员绑定");
        content_tv.setText("是否要将业务员姓名（电话号码）的与本公司进行绑定？");
        cancel_tv.setText("不绑定");
        verify_tv.setText("绑定");
        cancel_tv.setTextColor(this.getResources().getColor(R.color.red));
        cancel_tv.setOnClickListener(v -> mShowLoginDialog.dismiss());
        verify_tv.setOnClickListener(v -> {
            ViewControl viewControl = ViewControlUtil.create2Dialog(MySalesmanBindingSearchActivity.this);
            UserService api = ApiUtil.createDefaultApi(UserService.class);
            ApiUtil.doDefaultApi(api.rmOrbindSalesman("1", id, UserManager.getInstance().getUser().getESjzcHynm()), orbindSalesman -> {
                mShowLoginDialog.dismiss();
                switch (orbindSalesman.getBandCode()){
                    case "0":
                        showToast("业务员绑定成功");
                        EventBus.getDefault().post(new SalesmanBindTransfer());
                        finish();
                        break;
                    case "1":
                        showToast("业务员解绑成功");
                        break;
                    case "2":
                        showToast("要绑定的用户不存在");
                        break;
                    case "3":
                        showToast("要绑定的用户已经是其他企业的业务员，不能绑定再次绑定");
                        break;
                    case "4":
                        showToast("不能解绑自己的账号");
                        break;
                }
            }, () -> {
                if(mShowLoginDialog.isShowing())
                    mShowLoginDialog.dismiss();
            }, viewControl);
        });
        mShowLoginDialog = new AlertDialog.Builder(this).setView(dialogView).create();
        mShowLoginDialog.show();
    }
}
