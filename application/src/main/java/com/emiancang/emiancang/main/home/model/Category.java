package com.emiancang.emiancang.main.home.model;

/**
 * Created by yuanyueqing on 2017/2/22.
 */

public enum Category {
        QUALITYQUERY("查质量"),
        LOGISTICSQUERY("查物流"),
        FINDMONEY("找资金"),
        BUSINESSHELP("买卖帮"),
        BILOFLADING("提货单"),
        DESCRIBECOTTON("描述棉"),
        GREENCARDCOTTON("绿卡棉");


        Category(String name) {
            this.name = name;
        }
        String name;

        public String getName() {
            return name;
        }
}
