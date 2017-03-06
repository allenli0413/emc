package com.emiancang.emiancang.main.home.qualityquery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.emiancang.emiancang.main.home.qualityquery.model.QualityQueryModel;
import com.mayigeek.frame.view.state.ViewControl;
import com.zwyl.Logger;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.main.home.qualityquery.adapter.QualityContrastAdapter;
import com.emiancang.emiancang.main.home.qualityquery.model.QualityContrastDetailModel;
import com.emiancang.emiancang.main.home.qualityquery.model.QualityContrastModel;
import com.emiancang.emiancang.main.home.qualityquery.service.QualityContrastService;
import com.emiancang.emiancang.store.model.MainStoreModel;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.view.InnerScrollListview;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by yuanyueqing on 2016/11/11.
 */

public class QualityContrastActivity extends TitleActivity {

    @InjectView(R.id.tv_quality_constrast_first)
    TextView tv_quality_constrast_first;
    @InjectView(R.id.tv_quality_constrast_second)
    TextView tv_quality_constrast_second;
    @InjectView(R.id.lv_quality_contrast)
    InnerScrollListview lv_quality_contrast;
    @InjectView(R.id.sv_quality_contrast)
    ScrollView sv_quality_contrast;

    QualityContrastAdapter adapter;

    QualityContrastModel model;

    MainStoreModel first;
    MainStoreModel second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_contrast);
        ButterKnife.inject(this);
        initHead();
        initData();
    }

    private void initData() {
        tv_quality_constrast_first.setVisibility(View.GONE);
        tv_quality_constrast_second.setVisibility(View.GONE);
        first = (MainStoreModel) getIntent().getSerializableExtra("0");
        second = (MainStoreModel) getIntent().getSerializableExtra("1");
        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        QualityContrastService api = ApiUtil.createDefaultApi(QualityContrastService.class, ApiUtil.SMURL);
        ApiUtil.doDefaultApi(api.detail(first.getGcmgyMhph()), data -> {
            ApiUtil.doDefaultApi(api.detail(second.getGcmgyMhph()), data1 -> {
                tv_quality_constrast_first.setText(first.getGcmgyMhph());
                tv_quality_constrast_second.setText(second.getGcmgyMhph());
                tv_quality_constrast_first.setVisibility(View.VISIBLE);
                tv_quality_constrast_second.setVisibility(View.VISIBLE);
                List<QualityContrastModel> list = getData(first, data, second, data1);
                adapter = new QualityContrastAdapter(list);
                lv_quality_contrast.setAdapter(adapter);
                sv_quality_contrast.smoothScrollTo(0, 0);
            },viewControl);
        },viewControl);
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(),R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("质量对比", getResources().getColor(R.color.white));
        getHeadBar().showLeftImage();
        getHeadBar().setLeftOnClickListner(v -> finish());
    }

    @OnClick({R.id.ll_quality_constrast_first, R.id.ll_quality_constrast_second})
    void onClick(View v){
        Intent intent = new Intent(getActivity(), QualityContrastDetailActivity.class);
        intent.putExtra("flag", 0);
        QualityQueryModel item = new QualityQueryModel();
        switch (v.getId()){
            case R.id.ll_quality_constrast_first:
                item.setGcmgyMhph(first.getGcmgyMhph());
                intent.putExtra("data", item);
                startActivity(intent);
                break;
            case R.id.ll_quality_constrast_second:
                item.setGcmgyMhph(second.getGcmgyMhph());
                intent.putExtra("data", item);
                startActivity(intent);
                break;
        }
    }

    public List getData(MainStoreModel one, QualityContrastDetailModel first, MainStoreModel two, QualityContrastDetailModel second) {
        List<QualityContrastModel> list = new ArrayList<>();
        model = obtainData("基本信息", "", "", "");
        list.add(model);
        model = obtainData("采摘方式：", first.getCzfs(), "采摘方式：", second.getCzfs());
        list.add(model);
        model = obtainData("加工类型", first.getHy_jysj_lx(), "加工类型", second.getHy_jysj_lx());
        list.add(model);
        model = obtainData("加工企业", first.getHy_jgdw_jgdwmc(), "加工企业", second.getHy_jgdw_jgdwmc());
        list.add(model);
        model = obtainData("监管仓库", first.getHy_ckbmc(), "监管仓库", second.getHy_ckbmc());
        list.add(model);
        model = obtainData("公重(t)：", first.getHy_jysj_gdzl(), "公重(t)：", second.getHy_jysj_gdzl());
        list.add(model);
        model = obtainData("毛重(t)：", first.getHy_jysj_mz(), "毛重(t)：", second.getHy_jysj_mz());
        list.add(model);
        model = obtainData("皮重(kg)：", first.getHy_jysj_pz(), "皮重(kg)：", second.getHy_jysj_pz());
        list.add(model);
        model = obtainData("净重(t)：", first.getHy_jysj_jz(), "净重(t)：", second.getHy_jysj_jz());
        list.add(model);
        model = obtainData("平均回潮(%)：", first.getHy_jysj_hcl(), "平均回潮(%)：", second.getHy_jysj_hcl());
        list.add(model);
        model = obtainData("平均含杂(%)：", first.getHy_jysj_hzl(), "平均含杂(%)：", second.getHy_jysj_hzl());
        list.add(model);
        model = obtainData("各颜色级比例(%)", "", "", "");
        list.add(model);
        model = obtainData("颜色级：", first.getHy_jysj_ysj().equals("0") ? "无主体颜色级" : first.getHy_jysj_ysj(), "颜色级：", second.getHy_jysj_ysj().equals("0") ? "无主体颜色级" : second.getHy_jysj_ysj());
        list.add(model);
        model = obtainData("白棉1级", first.getBm1j(), "白棉1级", second.getBm1j());
        list.add(model);
        model = obtainData("白棉2级", first.getBm2j(), "白棉2级", second.getBm2j());
        list.add(model);
        model = obtainData("白棉3级", first.getBm3j(), "白棉3级", second.getBm3j());
        list.add(model);
        model = obtainData("白棉4级", first.getBm4j(), "白棉4级", second.getBm4j());
        list.add(model);
        model = obtainData("白棉5级", first.getBm5j(), "白棉5级", second.getBm5j());
        list.add(model);
        model = obtainData("淡点污棉1级", first.getDdwm1j(), "淡点污棉1级", second.getDdwm1j());
        list.add(model);
        model = obtainData("淡点污棉2级", first.getDdwm2j(), "淡点污棉2级", second.getDdwm2j());
        list.add(model);
        model = obtainData("淡点污棉3级", first.getDdwm3j(), "淡点污棉3级", second.getDdwm3j());
        list.add(model);
        model = obtainData("淡黄染棉1级", first.getDhrm1j(), "淡黄染棉1级", second.getDhrm1j());
        list.add(model);
        model = obtainData("淡黄染棉2级", first.getDhrm2j(), "淡黄染棉2级", second.getDhrm2j());
        list.add(model);
        model = obtainData("淡黄染棉3级", first.getDhrm3j(), "淡黄染棉3级", second.getDhrm3j());
        list.add(model);
        model = obtainData("黄染棉1级", first.getHrm1j(), "黄染棉1级", second.getHrm1j());
        list.add(model);
        model = obtainData("黄染棉2级", first.getHrm2j(), "黄染棉2级", second.getHrm2j());
        list.add(model);
        model = obtainData("各长度比例(%)", "", "", "");
        list.add(model);
        model = obtainData("长度级(平均长度)：", first.getHy_jysj_cdz(), "长度级(平均长度)：", second.getHy_jysj_cdz());
        list.add(model);
        model = obtainData("29mm  ", first.getCdj29(), "29mm  ", second.getCdj29());
        list.add(model);
        model = obtainData("28mm  ", first.getCdj28(), "28mm  ", second.getCdj28());
        list.add(model);
        model = obtainData("27mm  ", first.getCdj27(), "27mm  ", second.getCdj27());
        list.add(model);
        model = obtainData("26mm  ", first.getCdj26(), "26mm  ", second.getCdj26());
        list.add(model);
        model = obtainData("各马克隆值级比例(%)", "", "", "");
        list.add(model);
        model = obtainData("马克隆值级(平均值)：", first.getHy_jysj_mkldc_zj(), "马克隆值级(平均值)：", second.getHy_jysj_mkldc_zj());
        list.add(model);
        model = obtainData("B2  ", first.getMklzb2(), "B2  ", second.getMklzb2());
        list.add(model);
        model = obtainData("C2  ", first.getMklzc2(), "C2  ", second.getMklzc2());
        list.add(model);
        model = obtainData("A  ", first.getMklza(), "A  ", second.getMklza());
        list.add(model);
        model = obtainData("B2  ", first.getMklzb2(), "B2  ", second.getMklzb2());
        list.add(model);
        model = obtainData("长度整齐值(%)", "", "", "");
        list.add(model);
        model = obtainData("平均值：", first.getHy_jysj_cdzqdzs(), "平均值：", second.getHy_jysj_cdzqdzs());
        list.add(model);
        model = obtainData("最大值  ", first.getCdzqd_max(), "最大值  ", second.getCdzqd_max());
        list.add(model);
        model = obtainData("最小值  ", first.getCdzqd_min(), "最小值  ", second.getCdzqd_min());
        list.add(model);
        model = obtainData("断裂比强度(cN/tex)", "", "", "");
        list.add(model);
        model = obtainData("平均值：", first.getHy_jysj_dlbqd(), "平均值：", second.getHy_jysj_dlbqd());
        list.add(model);
        model = obtainData("最小值  ", first.getDlbqd_min(), "最小值  ", second.getDlbqd_min());
        list.add(model);
        model = obtainData("最大值  ", first.getDlbqd_max(), "最大值  ", second.getDlbqd_max());
        list.add(model);
        model = obtainData("轧工质量比例(%)", "", "", "");
        list.add(model);
        model = obtainData("轧工质量：", "P2", "轧工质量：", "P2");
        list.add(model);
        model = obtainData("P2", first.getZgzl_p2(), "P2", second.getZgzl_p2());
        list.add(model);
        model = obtainData("Rd(%)", "", "", "");
        list.add(model);
        model = obtainData("平均值：", first.getHy_jysj_fsl(), "平均值：", second.getHy_jysj_fsl());
        list.add(model);
        model = obtainData("b+", "", "", "");
        list.add(model);
        model = obtainData("平均值：", first.getHy_jysj_hd(), "平均值", second.getHy_jysj_hd());
        list.add(model);

        return list;
    }

    private QualityContrastModel obtainData(String title1, String content1, String title2, String content2) {
        QualityContrastModel model = new QualityContrastModel();
        model.setTitle(title1);
        model.setTitle_another(title2);
        model.setContent(TextUtils.isEmpty(content1) ? "- - -" : content1);
        model.setContent_another(TextUtils.isEmpty(content2) ? "- - -" : content2);
        return model;
    }
}
