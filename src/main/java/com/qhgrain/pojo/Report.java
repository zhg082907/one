package com.qhgrain.pojo;

/**
 * 销售报表实体类
 */
public class Report {
    private Integer id;//销售报表记录主键
    private String outWay;//出库方式
    private String outNoticeNum;//出库通知单号
    private String billingDate;//开票日期
    private String outDate;//出库日期
    private String customer;//客商
    private String grainOilKind;//粮油品种
    private String itemName;//商品名称
    private String grade;//等级
    private Integer spec;//规格
    private Integer num;//件数
    private String whNum;//仓号
    private String location;//货位
    private Double unitPrice;//单价
    private Double totalPrice;//总价
    private String createId;//创建人主键
    private String createDate;//创建时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
