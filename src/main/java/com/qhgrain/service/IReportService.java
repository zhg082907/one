package com.qhgrain.service;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.qhgrain.pojo.Customer;
import com.qhgrain.pojo.Price;
import com.qhgrain.pojo.Report;
import com.qhgrain.pojo.Version;
import com.qhgrain.vo.ReportPageVo;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

public interface IReportService {
    /**
     * 销售报表分页列表查询
     * @param billingDateOne
     * @param billingDateTwo
     * @param outDateOne
     * @param outDateTwo
     * @param outWay
     * @param grainOilKind
     * @param itemName
     * @param customer
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<ReportPageVo> reportPage(String billingDateOne, String billingDateTwo, String outDateOne, String outDateTwo, String outWay, String grainOilKind, String itemName, String customer, Integer pageNum, Integer pageSize);

    /**
     * 销售报表导出
     * @param billingDateOne
     * @param billingDateTwo
     * @param outDateOne
     * @param outDateTwo
     * @param outWay
     * @param grainOilKind
     * @param itemName
     * @param customer
     * @return
     */
    public List<ReportPageVo> reportExport(String billingDateOne, String billingDateTwo, String outDateOne, String outDateTwo, String outWay, String grainOilKind, String itemName, String customer);

    /**
     * 销售报表批量插入
     * @param list
     */
    public void batchAdd(List<Report> list,List<String> gokList);

    /**
     * 获取指定key值集合，用来导入时，判断数据是否已存在
     * @return
     */
    public Set<String> getKeys();

    /**
     * 获取价格版本集合
     * @return
     */
    public List<Version> getPriceVersions();

    /**
     * 获取客商版本集合
     * @return
     */
    public List<Version> getCustomerVersions();

    /**
     * 获取价格集合
     * @return
     */
    public List<Price> getPrices();

    /**
     * 获取客商集合
     * @return
     */
    public List<Customer> getCustomers();

    /**
     * 获取粮油品种集合
     * @return
     */
    public List<String> getGokList();
}
