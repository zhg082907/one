package com.qhgrain.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.qhgrain.pojo.Customer;
import com.qhgrain.vo.CustomerPageVo;

import java.util.List;

public interface ICustomerService {
    /**
     * 批量插入操作
     * @param data
     * @param flag 插入标记 0导入 1新增 2变更
     * @param userId 当前登录人主键
     */
    public void batchAdd(JSONArray data, int flag, String userId);

    /**
     * 客商导出数据查询,同时用于客商打印的数据查询
     * @param beginDateOne
     * @param beginDateTwo
     * @param customer
     * @param code
     * @param status
     * @return
     */
    public List<CustomerPageVo> customerExport(String beginDateOne, String beginDateTwo, String customer, String code, Integer status);

    /**
     * 客商分页列表查询
     * @param beginDateOne
     * @param beginDateTwo
     * @param customer
     * @param code
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<CustomerPageVo> customerPage(String beginDateOne, String beginDateTwo, String customer, String code, Integer status, Integer pageNum, Integer pageSize);

    /**
     * 客商编码变更操作
     * @param userId
     */
    public void customerChangeCode(String userId);

    /**
     * 获取当前客商版本和客商编码
     * @return
     */
    public JSONObject getCustomerVersionAndCode();

    /**
     * 判断客商在当前版本是否已存在
     * @param c
     * @return
     */
    public Boolean customerValidate(String c);

    /**
     * 根据客商或者编码，获取当前版本下的客商编码信息，flag：0客商 1编码
     * @param flag 0客商 1编码
     * @param param
     * @return
     */
    public Customer getCustomerByCustomerOrCode(Integer flag, String param);
}
