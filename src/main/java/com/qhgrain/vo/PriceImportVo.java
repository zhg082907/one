package com.qhgrain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * 价格导入VO类,同时用于价格导入模板
 */
public class PriceImportVo implements Serializable {

    @Excel(name = "价格",orderNum = "0",width = 20)
    private String price;//价格

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
