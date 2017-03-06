package com.emiancang.emiancang.my.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.litesuits.android.log.Log;
import com.mayigeek.frame.http.state.HttpError;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleFragment;
import com.emiancang.emiancang.bean.OrderInfo;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.my.MyIndentDetailsActivity;
import com.emiancang.emiancang.my.adapter.AdapterIndentMarket;
import com.emiancang.emiancang.service.UserService;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import rx.Observable;

/**
 * 销售订单
 * Created by 22310 on 2016/11/10.
 */

public class IndentMarketItemFragment extends TitleFragment {


    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;

    private String mEDdDdzt = "00";

    public IndentMarketItemFragment(){}

//    public IndentMarketItemFragment(String eDdDdzt){
//        this.mEDdDdzt = eDdDdzt;
//    }

    public static IndentMarketItemFragment newInstance(String eDbDdzt){
        IndentMarketItemFragment fragment = new IndentMarketItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("eDbDdzt", eDbDdzt);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mEDdDdzt =  bundle.getString("eDbDdzt");
    }

    private AdapterIndentMarket mAdapter= new AdapterIndentMarket();

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_collect_cotton, null);
        ButterKnife.inject(this, view);
        getHeadBar().hideHeader();
        initView();
        return view;
    }

    private void initView() {
        listView.setAdapter(mAdapter);
        ViewControl viewControl = ViewControlUtil.create2View(nestRefreshLayout,"当前没有订单","当前没有订单",R.drawable.icon_dingdan_empty);
        NestRefreshManager<OrderInfo> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, new NestRefreshManager.CreateApi<OrderInfo>() {
            @Override
            public Observable<List<OrderInfo>> run(int page, int perPage) {
                UserService api = ApiUtil.createDefaultApi(UserService.class);
                if(mEDdDdzt.equals("05"))
                    return api.getOrderList( "" + (page+1), "" + perPage,"2","01").map(new HttpResultFunc<>());
                else
                    return api.getOrderList( "" + (page+1), "" + perPage,"2",mEDdDdzt).map(new HttpResultFunc<>());

            }
        });
        nestRefreshManager.setPullLoadEnable(true);
        nestRefreshManager.setHttpError(new HttpError() {
            @Override
            public void onError(Throwable e) {
                Log.i("test", e.toString());
            }
        });
        nestRefreshManager.setCallBack(new NestRefreshManager.CallBack<OrderInfo>() {
            @Override
            public void call(List<? extends OrderInfo> allList, List<? extends OrderInfo> currentList) {
                mAdapter.clear();
                if(mEDdDdzt.equals("01")){
                    List<OrderInfo> newallList = new ArrayList<>();
                    for (OrderInfo orderInfo : allList){
                        if(orderInfo.geteDdFksfcs().equals("0")){
                            newallList.add(orderInfo);
                        }
                    }
                    mAdapter.addList(allList);
                }else if(mEDdDdzt.equals("05")){
                    List<OrderInfo> newallList = new ArrayList<>();
                    for (OrderInfo orderInfo : allList){
                        if(orderInfo.geteDdFksfcs().equals("1")){
                            newallList.add(orderInfo);
                        }
                    }
                    mAdapter.addList(allList);
                }else{
                    mAdapter.addList(allList);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        nestRefreshManager.doApi();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = createIntent(MyIndentDetailsActivity.class);
                intent.putExtra("id",mAdapter.getItem(position).getEDdId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
