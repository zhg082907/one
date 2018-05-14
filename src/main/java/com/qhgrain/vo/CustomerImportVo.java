package com.qhgrain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * 客商导入VO类,同时用于客商导入模板
 */
public class CustomerImportVo implements Serializable {

    @Excel(name = "客商",orderNum = "0",width = 20)
    private String customer;//客商

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
