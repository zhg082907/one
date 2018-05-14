package com.qhgrain.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qhgrain.Util.Constant;
import com.qhgrain.mapper.*;
import com.qhgrain.pojo.Customer;
import com.qhgrain.pojo.Price;
import com.qhgrain.pojo.Report;
import com.qhgrain.pojo.Version;
import com.qhgrain.service.IReportService;
import com.qhgrain.vo.ReportPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service("reportServiceImpl")
@Transactional
public class ReportServiceImpl implements IReportService {
    @Autowired
    private VersionMapper versionMapper;
    @Autowired
    private PriceMapper priceMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private GokMapper gokMapper;

    /**
     * 售报表分页列表查询
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
    @Override
    public PageInfo<ReportPageVo> reportPage(String billingDateOne, String billingDateTwo, String outDateOne, String outDateTwo, String outWay, String grainOilKind, String itemName, String customer, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ReportPageVo> list = this.reportMapper.reportList(billingDateOne,billingDateTwo,outDateOne,outDateTwo,outWay,grainOilKind,itemName,customer);
        PageInfo<ReportPageVo> pi = new PageInfo<ReportPageVo>(list);
        return pi;
    }

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
    @Override
    public List<ReportPageVo> reportExport(String billingDateOne, String billingDateTwo, String outDateOne, String outDateTwo, String outWay, String grainOilKind, String itemName, String customer) {
        List<ReportPageVo> list = this.reportMapper.reportList(billingDateOne,billingDateTwo,outDateOne,outDateTwo,outWay,grainOilKind,itemName,customer);
        return list;
    }

    /**
     * 销售报表批量插入
     * @param list
     */
    @Override
    public void batchAdd(List<Report> list ,List<String> gokList) {
        //维护粮油品种数据
        if (gokList.size() > 0){
            this.gokMapper.batchAdd(gokList);
        }
        //批量插入
        this.reportMapper.batchAdd(list);
    }

    /**
     * 获取指定key值集合，用来导入时，判断数据是否已存在
     * @return
     */
    @Override
    public Set<String> getKeys() {
        Set<String> set = this.reportMapper.getKeys();
        return set;
    }

    /**
     * 获取价格版本集合，用于导入时，判断价格版本是否存在
     * @return
     */
    @Override
    public List<Version> getPriceVersions() {
        List<Version> vps = this.versionMapper.getVersions(Constant.VERSION_TYPE_PRICE);
        return vps;
    }

    /**
     * 获取客商版本集合，用于导入时，判断客商版本是否处在
     * @return
     */
    @Override
    public List<Version> getCustomerVersions() {
        List<Version> vcs = this.versionMapper.getVersions(Constant.VERSION_TYPE_CUSTOMER);
        return vcs;
    }

    /**
     * 获取价格集合，用于导入时，判断价格编码是否存在
     * @return
     */
    @Override
    public List<Price> getPrices() {
        List<Price> ps = this.priceMapper.priceAll();
        return ps;
    }

    /**
     * 获取客商集合，用于导入时，判断客商编码是否存在
     * @return
     */
    @Override
    public List<Customer> getCustomers() {
        List<Customer> cs = this.customerMapper.customerAll();
        return cs;
    }

    /**
     * 获取粮油品种集合
     * @return
     */
    @Override
    public List<String> getGokList() {
        List<String> gokList = this.gokMapper.getGokList();
        return gokList;
    }
}
