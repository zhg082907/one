package com.qhgrain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * 价格分页列表的数据VO类，同时用于价格导出
 */
public class PricePageVo implements Serializable{
    @Excel(name = "版本号",width = 20)
    private String versionCode;//版本号
    @Excel(name = "生效开始时间",width = 35)
    private String beginDate;//生效开会时间
    @Excel(name = "生效截止时间",width = 35)
    private String endDate;//生效截止时间
    @Excel(name = "价格",width = 20)
    private Double price;//价格
    @Excel(name = "编码",width = 20)
    private String priceCode;//价格编码

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPriceCode() {
        return priceCode;
    }

    public void setPriceCode(String priceCode) {
        this.priceCode = priceCode;
    }
}
