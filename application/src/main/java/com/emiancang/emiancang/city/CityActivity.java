package com.emiancang.emiancang.city;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.google.gson.reflect.TypeToken;
import com.litesuits.android.log.Log;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.bean.City;
import com.emiancang.emiancang.eventbean.CityTransfer;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.JsonUtil;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.uitl.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import de.greenrobot.event.EventBus;
import rx.Observable;

/**
 * Created by 22310 on 2016/11/13.
 */

public class CityActivity extends TitleActivity {

    @InjectView(R.id.filter_edit)
    ClearEditText filterEdit;
    @InjectView(R.id.rl_city_main)
    RelativeLayout rl_city_main;
    @InjectView(R.id.country_lvcountry)
    ListView countryLvcountry;
    @InjectView(R.id.dialog)
    TextView dialog;
    @InjectView(R.id.sidrbar)
    SideBar sidrbar;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;
    private SortAdapter adapter;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    private HistoryAdapter mAdapter;

    private ViewHolder mHeadHolder;

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    public AMapLocationClient mlocationClient = null;
    private AMapLocation aMapLocation;

    private int type = 0;

    ViewControl viewControl;
    NestRefreshLayout nestRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_city, null);
        setContentView(view);
        ButterKnife.inject(this);
        type = getIntExtra("type");
        nestRefreshLayout = (NestRefreshLayout) findViewById(R.id.nestRefreshLayout);
        viewControl = ViewControlUtil.create2View(nestRefreshLayout, "加载失败", "未获取到城市列表", R.drawable.icon_content_empty);
        initHead();
        initView();
        if(type != 2 && type != 3 && type != 4 && type != 5)
            initHeadHolder();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("选择城市", getResources().getColor(R.color.white));
        getHeadBar().hideRightImage();
    }

    private void initView() {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sidrbar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sidrbar.setTextView(dialog);
        refresh();

        View head = View.inflate(getActivity(), R.layout.activity_city_head, null);
        mHeadHolder = new ViewHolder(head);
        mHeadHolder.ll_city_location.setOnClickListener(v -> {
            EventBus.getDefault().post(new CityTransfer(type, mHeadHolder.cityLocation.getText().toString().trim(), ""));
            finish();
        });
        mHeadHolder.cityLocation.setOnClickListener(v -> {
            EventBus.getDefault().post(new CityTransfer(type, mHeadHolder.cityLocation.getText().toString().trim(), ""));
            finish();
        });
        if(type != 2 && type != 3 && type != 4 && type != 5)
            countryLvcountry.addHeaderView(head);
        SourceDateList = new ArrayList<>();
        adapter = new SortAdapter(this, SourceDateList);
        countryLvcountry.setAdapter(adapter);

        //设置右侧触摸监听
        sidrbar.setOnTouchingLetterChangedListener(s -> {
            //该字母首次出现的位置
            int position = adapter.getPositionForSection(s.charAt(0));
            if (position != -1) {
                countryLvcountry.setSelection(position);
            }

        });
        countryLvcountry.setOnItemClickListener((parent, view, position, id) -> {
            SortModel sortModel = (SortModel) adapter.getItem(position - countryLvcountry.getHeaderViewsCount());
            String history = SharedPrefsUtil.getValue(CityActivity.this,SharedPrefsUtil.CITYHISTORY,"");
//            List<String> list1 = JsonUtil.fromJson(history, new TypeToken<ArrayList<String>>() {}.getType());
            List<SortModel> list1 = JsonUtil.fromJson(history, new TypeToken<ArrayList<SortModel>>() {}.getType());
            if(TextUtils.isEmpty(history)){
                list1 = new ArrayList<SortModel>();
                list1.add(sortModel);
            }else{
                list1 = JsonUtil.fromJson(history, new TypeToken<ArrayList<SortModel>>() {}.getType());
                for (SortModel name: list1){
                    if(name.getName().equals(sortModel.getName())){
                        EventBus.getDefault().post(new CityTransfer(type, sortModel.getName(),sortModel.getId()));
                        finish();
                        return;
                    }
                }
                list1.add(sortModel);
            }
            SharedPrefsUtil.putValue(CityActivity.this,SharedPrefsUtil.CITYHISTORY,JsonUtil.toJson(list1));
            EventBus.getDefault().post(new CityTransfer(type, sortModel.getName(),sortModel.getId()));
            finish();
        });
        filterEdit = (ClearEditText) findViewById(R.id.filter_edit);
        //根据输入框输入值的改变来过滤搜索
        filterEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initLocation() {
        mlocationClient = new AMapLocationClient(getActivity());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true);
        //设置定位监听
        mlocationClient.setLocationListener(aMapLocation -> {
            this.aMapLocation = aMapLocation;
            mHeadHolder.cityLocation.setText(aMapLocation.getCity());
            mlocationClient.stopLocation();
        });
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    private void initHeadHolder(){
        String history = SharedPrefsUtil.getValue(this,SharedPrefsUtil.CITYHISTORY,"");
        if(TextUtils.isEmpty(history)){
            mHeadHolder.cityHistoryLayout.setVisibility(View.GONE);
        }else{
            mHeadHolder.cityHistoryLayout.setVisibility(View.VISIBLE);
            List<SortModel> list = JsonUtil.fromJson(history, new TypeToken<ArrayList<SortModel>>() {}.getType());
            mAdapter = new HistoryAdapter(list);
            mHeadHolder.cityHistory.setAdapter(mAdapter);
            mHeadHolder.cityHistory.setOnItemClickListener((parent, view, position, id) -> {
                SortModel sortModel = (SortModel) mAdapter.getItem(position);
                EventBus.getDefault().post(new CityTransfer(type, sortModel.getName(),sortModel.getId()));
                finish();
            });
        }
        mHeadHolder.cityRemove.setOnClickListener(v -> {
            SharedPrefsUtil.remove(CityActivity.this,SharedPrefsUtil.CITYHISTORY);
            mHeadHolder.cityHistoryLayout.setVisibility(View.GONE);
        });
    }


    public void refresh(){
        NestRefreshManager<City> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, (page, perPage) -> {
            UserService api = ApiUtil.createDefaultApi(UserService.class);
            return api.getAllCityList("").map(new HttpResultFunc<>());
        });
        nestRefreshManager.setPullLoadEnable(true);
        nestRefreshManager.setHttpError(e -> {
            rl_city_main.setVisibility(View.GONE);
            Log.i("test", e.toString());
        });
        nestRefreshManager.setCallBack((allList, currentList) -> {
            SourceDateList = filledData(allList);
            // 根据a-z进行排序源数据
            Collections.sort(SourceDateList, pinyinComparator);
            adapter.setList(SourceDateList);
            adapter.notifyDataSetChanged();
            rl_city_main.setVisibility(View.VISIBLE);
        });
        nestRefreshManager.doApi();
    }

    /**
     * 为ListView填充数据
     *
     * @param citys
     * @return
     */
    private List<SortModel> filledData(List<? extends City> citys) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < citys.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(citys.get(i).getAreaName());
            sortModel.setId(""+citys.get(i).getAreaId());
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(citys.get(i).getAreaName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }
        if(filterDateList.isEmpty()){
            showToast("没有此地址，请输入正确地址");
        }
        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

    public class HistoryAdapter extends BaseAdapter {

        private List<SortModel> data;

        public HistoryAdapter(List<SortModel> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(getActivity(), R.layout.adapter_city_item, null);
            TextView text = (TextView) convertView.findViewById(R.id.text);
            text.setText(data.get(position).getName());
            return convertView;
        }
    }

    static class ViewHolder {
        @InjectView(R.id.ll_city_location)
        LinearLayout ll_city_location;
        @InjectView(R.id.city_location)
        TextView cityLocation;
        @InjectView(R.id.city_remove)
        TextView cityRemove;
        @InjectView(R.id.city_history)
        GridView cityHistory;
        @InjectView(R.id.city_history_layout)
        LinearLayout cityHistoryLayout;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.isOpenGPS(this)){
            initLocation();
        }else{
            Utils.showGPSDialog(this);
        }
    }
}

