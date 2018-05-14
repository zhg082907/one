package com.qhgrain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 销售报表导入VO类
 */
public class ReportImportVo {
    @Excel(name = "出库方式",isImportField = "true")
    private String outWay;//出库方式
    @Excel(name = "出库通知单号",isImportField = "true")
    private String outNoticeNum;//出库通知单号
    @Excel(name = "开票日期",isImportField = "true")
    private String billingDate;//开票日期
    @Excel(name = "出库日期",isImportField = "true")
    private String outDate;//出库日期
    @Excel(name = "客商",isImportField = "true")
    private String customer;//客商
    @Excel(name = "粮油品种",isImportField = "true")
    private String grainOilKind;//粮油品种
    @Excel(name = "商品名称",isImportField = "true")
    private String itemName;//商品名称
    @Excel(name = "等级",isImportField = "true")
    private String grade;//等级
    @Excel(name = "规格(kg)",isImportField = "true")
    private Integer spec;//规格
    @Excel(name = "件数",isImportField = "true")
    private Integer num;//件数
    @Excel(name = "出库仓号",isImportField = "true")
    private String whNum;//出库仓号
    @Excel(name = "出库货位",isImportField = "true")
    private String location;//出库货位
    @Excel(name = "单价(元/kg)",isImportField = "true")
    private String unitPrice;//单价

    public String getOutWay() {
        return outWay;
    }

    public void setOutWay(String outWay) {
        this.outWay = outWay;
    }

    public String getOutNoticeNum() {
        return outNoticeNum;
    }

    public void setOutNoticeNum(String outNoticeNum) {
        this.outNoticeNum = outNoticeNum;
    }

    public String getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(String billingDate) {
        this.billingDate = billingDate;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getGrainOilKind() {
        return grainOilKind;
    }

    public void setGrainOilKind(String grainOilKind) {
        this.grainOilKind = grainOilKind;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getSpec() {
        return spec;
    }

    public void setSpec(Integer spec) {
        this.spec = spec;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getWhNum() {
        return whNum;
    }

    public void setWhNum(String whNum) {
        this.whNum = whNum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
}
