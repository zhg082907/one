package com.qhgrain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * 销售报表分页列表数据VO类，同时用于销售报表导出操作
 */
public class ReportPageVo implements Serializable{
    @Excel(name = "出库方式",width = 15)
    private String outWay;//出库方式
    @Excel(name = "出库通知单号",width = 20)
    private String outNoticeNum;//出库通知单号
    @Excel(name = "开票日期",width = 20)
    private String billingDate;//开票日期
    @Excel(name = "出库日期",width = 20)
    private String outDate;//出库日期
    @Excel(name = "客商",width = 30)
    private String customer;//客商
    @Excel(name = "粮油品种",width = 15)
    private String grainOilKind;//粮油品种
    @Excel(name = "商品名称",width = 20)
    private String itemName;//商品名称
    @Excel(name = "等级",width = 10)
    private String grade;//等级
    @Excel(name = "规格(kg)",width = 10)
    private Integer spec;//规格
    @Excel(name = "件数",width = 10)
    private Integer num;//件数
    @Excel(name = "出库仓号",width = 15)
    private String whNum;//出库仓号
    @Excel(name = "出库货位",width = 15)
    private String location;//出库货位
    @Excel(name = "单价(元/kg)",width = 15)
    private Double unitPrice;//单价
    @Excel(name = "总价(元)",width = 15)
    private Double totalPrice;//总价

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

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
