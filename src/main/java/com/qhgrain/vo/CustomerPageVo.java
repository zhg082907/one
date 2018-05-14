package com.qhgrain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * 价格分页列表的数据VO类，同时用于价格导出
 */
public class CustomerPageVo implements Serializable{
    @Excel(name = "版本号",width = 20)
    private String versionCode;//版本号
    @Excel(name = "生效开始时间",width = 35)
    private String beginDate;//生效开会时间
    @Excel(name = "生效截止时间",width = 35)
    private String endDate;//生效截止时间
    @Excel(name = "客商",width = 35)
    private String customer;//客商
    @Excel(name = "编码",width = 20)
    private String customerCode;//价格编码

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
}
