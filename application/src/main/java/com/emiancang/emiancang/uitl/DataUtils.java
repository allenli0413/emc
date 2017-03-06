package com.emiancang.emiancang.uitl;

import com.emiancang.emiancang.R;
import com.emiancang.emiancang.main.home.qualityquery.adapter.FilterGridviewAdapter;
import com.emiancang.emiancang.main.home.qualityquery.model.FilterGridviewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanyueqing on 2017/1/12.
 */

public class DataUtils {

    public static final int TYPE = 0;
    public static final int YEAR = 1;
    public static final int COLORLEVEL = 2;

    public static List getData(int origin){
            List<FilterGridviewModel> list = new ArrayList<>();
            switch (origin){
                case R.id.ll_store_main_length:
                    for (int i = 0; i < 9; i++) {
                        FilterGridviewModel model = new FilterGridviewModel();
                        model.setAreaName((25 + i) + "mm");
                        model.setSelected(false);
                        list.add(model);
                    }
                    break;
                case R.id.ll_store_main_ma:
                    FilterGridviewModel first = new FilterGridviewModel();
                    first.setAreaName("C1");
                    first.setSelected(false);
                    list.add(first);
                    FilterGridviewModel second = new FilterGridviewModel();
                    second.setAreaName("B1");
                    second.setSelected(false);
                    list.add(second);
                    FilterGridviewModel third = new FilterGridviewModel();
                    third.setAreaName("A");
                    third.setSelected(false);
                    list.add(third);
                    FilterGridviewModel fourth = new FilterGridviewModel();
                    fourth.setAreaName("B2");
                    fourth.setSelected(false);
                    list.add(fourth);
                    FilterGridviewModel fifth = new FilterGridviewModel();
                    fifth.setAreaName("C2");
                    fifth.setSelected(false);
                    list.add(fifth);
                    break;
                case R.id.ll_store_main_strength:
                    for (int i = 0; i < 5; i++) {
                        FilterGridviewModel model = new FilterGridviewModel();
                        model.setAreaName("s" + (1 + i));
                        model.setSelected(false);
                        list.add(model);
                    }
                    break;
                case R.id.ll_store_main_origin:
                    FilterGridviewModel origin_first = new FilterGridviewModel();
                    origin_first.setAreaName("江苏省");
                    origin_first.setAreaId("100102");
                    origin_first.setSelected(false);
                    list.add(origin_first);
                    FilterGridviewModel origin_second = new FilterGridviewModel();
                    origin_second.setAreaName("河南省");
                    origin_second.setAreaId("100107");
                    origin_second.setSelected(false);
                    list.add(origin_second);
                    FilterGridviewModel origin_third = new FilterGridviewModel();
                    origin_third.setAreaName("山东省");
                    origin_third.setAreaId("100106");
                    origin_third.setSelected(false);
                    list.add(origin_third);
                    FilterGridviewModel origin_fourth = new FilterGridviewModel();
                    origin_fourth.setAreaName("浙江省");
                    origin_fourth.setAreaId("100103");
                    origin_fourth.setSelected(false);
                    list.add(origin_fourth);
                    FilterGridviewModel origin_fifth = new FilterGridviewModel();
                    origin_fifth.setAreaName("山西省");
                    origin_fifth.setAreaId("100104");
                    origin_fifth.setSelected(false);
                    list.add(origin_fifth);
                    FilterGridviewModel origin_sixth = new FilterGridviewModel();
                    origin_sixth.setAreaName("河北省");
                    origin_sixth.setAreaId("100105");
                    origin_sixth.setSelected(false);
                    list.add(origin_sixth);
                    FilterGridviewModel origin_seventh = new FilterGridviewModel();
                    origin_seventh.setAreaName("安徽省");
                    origin_seventh.setAreaId("100100");
                    origin_seventh.setSelected(false);
                    list.add(origin_seventh);
                    FilterGridviewModel origin_eighth = new FilterGridviewModel();
                    origin_eighth.setAreaName("湖北省");
                    origin_eighth.setAreaId("100108");
                    origin_first.setSelected(false);
                    list.add(origin_eighth);
                    FilterGridviewModel origin_ninth = new FilterGridviewModel();
                    origin_ninth.setAreaName("陕西省");
                    origin_ninth.setAreaId("100116");
                    origin_ninth.setSelected(false);
                    list.add(origin_ninth);
                    FilterGridviewModel origin_tenth = new FilterGridviewModel();
                    origin_tenth.setAreaName("湖南省");
                    origin_tenth.setAreaId("100109");
                    origin_tenth.setSelected(false);
                    list.add(origin_tenth);
                    FilterGridviewModel origin_eleven = new FilterGridviewModel();
                    origin_eleven.setAreaName("江西省");
                    origin_eleven.setAreaId("100110");
                    origin_eleven.setSelected(false);
                    list.add(origin_eleven);
                    FilterGridviewModel origin_twelve = new FilterGridviewModel();
                    origin_twelve.setAreaName("甘肃省");
                    origin_twelve.setAreaId("100117");
                    origin_twelve.setSelected(false);
                    list.add(origin_twelve);
                    FilterGridviewModel origin_thirteenth = new FilterGridviewModel();
                    origin_thirteenth.setAreaName("新疆");
                    origin_thirteenth.setAreaId("100127");
                    origin_thirteenth.setSelected(false);
                    list.add(origin_thirteenth);
                    FilterGridviewModel origin_fourteenth = new FilterGridviewModel();
                    origin_fourteenth.setAreaName("天津市");
                    origin_fourteenth.setAreaId("100134");
                    origin_fourteenth.setSelected(false);
                    list.add(origin_fourteenth);
                    FilterGridviewModel origin_fifteenth = new FilterGridviewModel();
                    origin_fifteenth.setAreaName("建设兵团");
                    origin_fifteenth.setAreaId("1001271019");
                    origin_fifteenth.setSelected(false);
                    list.add(origin_fifteenth);
                    break;
            }
            return list;
    }

    public static List getSortData() {
            List<FilterGridviewModel> sortList = new ArrayList<>();
            FilterGridviewModel first = new FilterGridviewModel();
            first.setAreaName("综合排序");
            first.setSelected(false);
            sortList.add(first);
            FilterGridviewModel second = new FilterGridviewModel();
            second.setAreaName("价格最低");
            second.setSelected(false);
            sortList.add(second);
            FilterGridviewModel third = new FilterGridviewModel();
            third.setAreaName("价格最高");
            third.setSelected(false);
            sortList.add(third);
            FilterGridviewModel fourth = new FilterGridviewModel();
            fourth.setAreaName("存放仓库");
            fourth.setSelected(false);
            sortList.add(fourth);
            FilterGridviewModel fifth = new FilterGridviewModel();
            fifth.setAreaName("加工厂");
            fifth.setSelected(false);
            sortList.add(fifth);
            FilterGridviewModel sixth = new FilterGridviewModel();
            sixth.setAreaName("发布时间");
            sixth.setSelected(false);
            sortList.add(sixth);
            FilterGridviewModel seventh = new FilterGridviewModel();
            seventh.setAreaName("供货商");
            seventh.setSelected(false);
            sortList.add(seventh);
        return sortList;
    }

    public static List getFilterData(int type) {
        List<FilterGridviewModel> list = new ArrayList<>();
        switch (type){
            case TYPE:
                list = new ArrayList<>();
                FilterGridviewModel first = new FilterGridviewModel();
                first.setAreaName("手摘棉");
                first.setSelected(false);
                list.add(first);
                FilterGridviewModel second = new FilterGridviewModel();
                second.setAreaName("机采棉");
                second.setSelected(false);
                list.add(second);
                FilterGridviewModel third = new FilterGridviewModel();
                third.setAreaName("细绒棉");
                third.setSelected(false);
                list.add(third);
                FilterGridviewModel fourth = new FilterGridviewModel();
                fourth.setAreaName("长绒棉");
                fourth.setSelected(false);
                list.add(fourth);
                break;
            case YEAR:
                FilterGridviewModel year1 = new FilterGridviewModel();
                year1.setAreaName("2011");
                year1.setSelected(false);
                list.add(year1);
                FilterGridviewModel year2 = new FilterGridviewModel();
                year2.setAreaName("2012");
                year2.setSelected(false);
                list.add(year2);
                FilterGridviewModel year3 = new FilterGridviewModel();
                year3.setAreaName("2013");
                year3.setSelected(false);
                list.add(year3);
                FilterGridviewModel year4 = new FilterGridviewModel();
                year4.setAreaName("2014");
                year4.setSelected(false);
                list.add(year4);
                FilterGridviewModel year5 = new FilterGridviewModel();
                year5.setAreaName("2015");
                year5.setSelected(false);
                list.add(year5);
                FilterGridviewModel year6 = new FilterGridviewModel();
                year6.setAreaName("2016");
                year6.setSelected(false);
                list.add(year6);
                FilterGridviewModel year7 = new FilterGridviewModel();
                year7.setAreaName("2017");
                year7.setSelected(false);
                list.add(year7);
                break;
            case COLORLEVEL:
                FilterGridviewModel c1 = new FilterGridviewModel();
                c1.setAreaName("11");
                c1.setSelected(false);
                list.add(c1);
                FilterGridviewModel c2 = new FilterGridviewModel();
                c2.setAreaName("21");
                c2.setSelected(false);
                list.add(c2);
                FilterGridviewModel c3 = new FilterGridviewModel();
                c3.setAreaName("31");
                c3.setSelected(false);
                list.add(c3);
                FilterGridviewModel c4 = new FilterGridviewModel();
                c4.setAreaName("41");
                c4.setSelected(false);
                list.add(c4);
                FilterGridviewModel c5 = new FilterGridviewModel();
                c5.setAreaName("51");
                c5.setSelected(false);
                list.add(c5);
                FilterGridviewModel c6 = new FilterGridviewModel();
                c6.setAreaName("12");
                c6.setSelected(false);
                list.add(c6);
                FilterGridviewModel c7 = new FilterGridviewModel();
                c7.setAreaName("22");
                c7.setSelected(false);
                list.add(c7);
                FilterGridviewModel c8 = new FilterGridviewModel();
                c8.setAreaName("32");
                c8.setSelected(false);
                list.add(c8);
                FilterGridviewModel c9 = new FilterGridviewModel();
                c9.setAreaName("13");
                c9.setSelected(false);
                list.add(c9);
                FilterGridviewModel c10 = new FilterGridviewModel();
                c10.setAreaName("23");
                c10.setSelected(false);
                list.add(c10);
                FilterGridviewModel c11 = new FilterGridviewModel();
                c11.setAreaName("33");
                c11.setSelected(false);
                list.add(c11);
                FilterGridviewModel c12 = new FilterGridviewModel();
                c12.setAreaName("14");
                c12.setSelected(false);
                list.add(c12);
                FilterGridviewModel c13 = new FilterGridviewModel();
                c13.setAreaName("24");
                c13.setSelected(false);
                list.add(c13);
                FilterGridviewModel c14 = new FilterGridviewModel();
                c14.setAreaName("无主体");
                c14.setSelected(false);
                list.add(c14);
                break;
        }
        return list;
    }

    public static List<FilterGridviewModel> getSortDataInQualityQuery() {
        List<FilterGridviewModel> sortList = new ArrayList<>();
        FilterGridviewModel fourth = new FilterGridviewModel();
        fourth.setAreaName("存放仓库");
        fourth.setSelected(false);
        sortList.add(fourth);
        FilterGridviewModel fifth = new FilterGridviewModel();
        fifth.setAreaName("加工厂");
        fifth.setSelected(false);
        sortList.add(fifth);
        FilterGridviewModel sixth = new FilterGridviewModel();
        sixth.setAreaName("发布时间");
        sixth.setSelected(false);
        sortList.add(sixth);
        return sortList;
    }
}
