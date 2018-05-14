package com.qhgrain.vo;

import com.qhgrain.pojo.Customer;
import com.qhgrain.pojo.Price;

import java.util.List;

/**
 * 版本价格客商信息VO类
 */
public class VersionVo {
    private Integer id;//版本主键
    private Integer type;//版本类型 0价格 1客商
    private String code;//版本编码
    private Integer status;//版本状态 0正常 1失效
    private String beginDate;//生效时间
    private String endDate;//失效时间
    private String createDate;//创建时间
    private String createId;//创建人主键
    private List<Price> prices;//价格实体集合
    private List<Customer> customers;//客商实体集合

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
