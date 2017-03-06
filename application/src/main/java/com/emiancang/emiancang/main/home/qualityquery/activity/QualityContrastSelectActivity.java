package com.emiancang.emiancang.main.home.qualityquery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.litesuits.android.log.Log;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.main.home.qualityquery.adapter.FilterGridviewAdapter;
import com.emiancang.emiancang.main.home.qualityquery.adapter.QualityContrastSelectAdapter;
import com.emiancang.emiancang.main.home.qualityquery.model.FilterGridviewModel;
import com.emiancang.emiancang.main.home.qualityquery.model.QualityContrastSelectModel;
import com.emiancang.emiancang.main.home.qualityquery.service.QualityContrastSelectService;
import com.emiancang.emiancang.store.model.MainStoreModel;
import com.emiancang.emiancang.uitl.Util;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;

/**
 * Created by yuanyueqing on 2016/11/9.
 */

public class QualityContrastSelectActivity extends TitleActivity {

    @InjectView(R.id.tv_quality_constrast_select_selected)
    TextView tv_quality_constrast_select_selected;

    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.nestRefreshLayout)
    NestRefreshLayout nestRefreshLayout;

    QualityContrastSelectAdapter adapter;

    List<FilterGridviewModel> length_list;
    List<FilterGridviewModel> ma_list;
    List<FilterGridviewModel> strength_list;
    List<FilterGridviewModel> origin_list;
    List<FilterGridviewModel> sortList;
    List<FilterGridviewModel> typeList;
    List<FilterGridviewModel> yearList;
    List<FilterGridviewModel> colorLevelList;

    String length = "";
    String ma = "";
    String strength = "";
    String origin = "";
    String sort = "";
    String type = "";
    String pickType = "";
    String year = "";
    String color = "";

    double lat = 0;
    double lng = 0;

    ViewControl viewControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_contrast_select);
        ButterKnife.inject(this);
        viewControl = ViewControlUtil.create2View(nestRefreshLayout, "没找到推荐棉花", "没找到推荐棉花",R.drawable.icon_content_empty);
        getData();
        initHead();
        initData();
    }

    private void getData() {
        lat = getIntent().getDoubleExtra("lat", 0);
        lng = getIntent().getDoubleExtra("lng", 0);
        length_list = (List<FilterGridviewModel>) getIntent().getSerializableExtra("length");
        ma_list = (List<FilterGridviewModel>) getIntent().getSerializableExtra("ma");
        strength_list = (List<FilterGridviewModel>) getIntent().getSerializableExtra("strength");
        origin_list = (List<FilterGridviewModel>) getIntent().getSerializableExtra("origin");
        sortList = (List<FilterGridviewModel>) getIntent().getSerializableExtra("sort");
        typeList = (List<FilterGridviewModel>) getIntent().getSerializableExtra("type");
        yearList = (List<FilterGridviewModel>) getIntent().getSerializableExtra("year");
        colorLevelList = (List<FilterGridviewModel>) getIntent().getSerializableExtra("color");

        for (FilterGridviewModel filterGridviewModel : length_list) {
            if(filterGridviewModel.isSelected())
                length = length + filterGridviewModel.getAreaName() + ",";
        }
        if(length != null && length.length() > 0 && length.substring(length.length() - 1, length.length()).equals(","))
            length = length.substring(0, length.length()-1);

        for (FilterGridviewModel filterGridviewModel : ma_list) {
            if(filterGridviewModel.isSelected())
                ma = ma + filterGridviewModel.getAreaName() + ",";
        }
        if(ma != null && ma.length() > 0 && ma.substring(ma.length() - 1, ma.length()).equals(","))
            ma = ma.substring(0, ma.length()-1);

        for (FilterGridviewModel filterGridviewModel : strength_list) {
            if(filterGridviewModel.isSelected())
                strength = strength + filterGridviewModel.getAreaName() + ",";
        }
        if(strength != null && strength.length() > 0 && strength.substring(strength.length() - 1, strength.length()).equals(","))
            strength = strength.substring(0, strength.length()-1);

        for (FilterGridviewModel filterGridviewModel : origin_list) {
            if(filterGridviewModel.isSelected())
                origin = origin + filterGridviewModel.getAreaName() + ",";
        }
        if(origin != null && origin.length() > 0 && origin.substring(origin.length() - 1, origin.length()).equals(","))
            origin = origin.substring(0, origin.length()-1);

        for (FilterGridviewModel filterGridviewModel : sortList) {
            if(filterGridviewModel.isSelected()){
                if(filterGridviewModel.getAreaName().equals("综合排序")){
                    sort = "0";
                }
                if(filterGridviewModel.getAreaName().equals("价格最低")){
                    sort = "1";
                }
                if(filterGridviewModel.getAreaName().equals("价格最高")){
                    sort = "2";
                }
                if(filterGridviewModel.getAreaName().equals("存放仓库")){
                    sort = "3";
                }
                if(filterGridviewModel.getAreaName().equals("加工厂")){
                    sort = "4";
                }
                if(filterGridviewModel.getAreaName().equals("发布时间")){
                    sort = "5";
                }
                if(filterGridviewModel.getAreaName().equals("供货商")){
                    sort = "6";
                }
            }
        }

        for (int i = 0; i < typeList.size(); i++) {
            if(i < 2 && typeList.get(i).isSelected())
                pickType = pickType + typeList.get(i).getAreaName() + ",";
            if(i >= 2 && typeList.get(i).isSelected())
                type = type + typeList.get(i).getAreaName() + ",";
        }
        if(pickType != null && pickType.length() > 0 && pickType.substring(pickType.length() - 1, pickType.length()).equals(","))
            pickType = pickType.substring(0, pickType.length()-1);
        if(type != null && type.length() > 0 && type.substring(type.length() - 1, type.length()).equals(","))
            type = type.substring(0, type.length()-1);

        for (FilterGridviewModel filterGridviewModel : yearList) {
            if(filterGridviewModel.isSelected())
                year = year + filterGridviewModel.getAreaName() + ",";
        }
        if(year != null && year.length() > 0 && year.substring(year.length() - 1, year.length()).equals(","))
            year = year.substring(0, year.length()-1);

        for (FilterGridviewModel filterGridviewModel : colorLevelList) {
            if(filterGridviewModel.isSelected())
                color = color + filterGridviewModel.getAreaName() + ",";
        }
        if(color != null && color.length() > 0 && color.substring(color.length() - 1, color.length()).equals(","))
            color = color.substring(0, color.length()-1);

    }

    private void initData() {
        adapter = new QualityContrastSelectAdapter();
        listView.setAdapter(adapter);

        NestRefreshManager<MainStoreModel> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayout, viewControl, (page, perPage) -> {
            QualityContrastSelectService api = ApiUtil.createDefaultApi(QualityContrastSelectService.class);
            return api.list("", "0", length, ma, strength, origin, type, pickType, year, color, "", "", "", "", "", "", "", "", "", "", "", "", sort, lng + "", lat + "", "" + (page + 1), "" + perPage).map(new HttpResultFunc<>());
        });
        nestRefreshManager.setPullLoadEnable(true);
        nestRefreshManager.setHttpError(e -> Log.i("test", e.toString()));
        nestRefreshManager.setCallBack((allList, currentList) -> {
            adapter.clear();
            adapter.addList(allList);
            adapter.notifyDataSetChanged();
            setNum();
        });
        nestRefreshManager.doApi();

        listView.setOnItemClickListener((parent, view, position, id) -> {
//            Intent intent = new Intent(this, QualityContrastDetailActivity.class);
//            intent.putExtra("data", adapter.getList().get(position));
//            startActivity(intent);
            if(tv_quality_constrast_select_selected.getText().toString().trim().equals("2") && !adapter.getList().get(position).isSelected()){
                showToast("只能选择两项");
                return;
            }
            adapter.getList().get(position).setSelected(!adapter.getList().get(position).isSelected());
            adapter.notifyDataSetInvalidated();
            setNum();
        });
    }

    private void setNum() {
        int num = 0;
        for (MainStoreModel mainStoreModel : adapter.getList()) {
            if(mainStoreModel.isSelected())
                num++;
        }
        tv_quality_constrast_select_selected.setText(num+"");
    }

    private void initHead() {
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        Util.setStatusBarColor(getActivity(),R.color.green);
        getHeadBar().setCenterTitle("数据对比", getResources().getColor(R.color.white));
        getHeadBar().showLeftText();
        getHeadBar().setLeftTitle("取消", getResources().getColor(R.color.white));
        getHeadBar().setLeftOnClickListner(v -> finish());
        getHeadBar().showRightText();
        getHeadBar().setRightTitle("确认", getResources().getColor(R.color.white));
        getHeadBar().setRightOnClickListner(v -> {
            if(!tv_quality_constrast_select_selected.getText().toString().trim().equals("2")){
                showToast("请选择两项");
                return;
            }
            Intent intent = new Intent(getActivity(), QualityContrastActivity.class);
            int num = 0;
            for (MainStoreModel mainStoreModel : adapter.getList()) {
                if(mainStoreModel.isSelected()){
                    intent.putExtra(num+"", mainStoreModel);
                    num++;
                }
            }
            startActivity(intent);
        });
    }
}
