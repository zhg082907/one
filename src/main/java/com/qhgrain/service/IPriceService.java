package com.qhgrain.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.qhgrain.pojo.Price;
import com.qhgrain.vo.PricePageVo;

import java.util.List;

public interface IPriceService {
    /**
     * 批量插入操作
     * @param data
     * @param flag 插入标记 0导入 1新增 2变更
     * @param userId 当前登录人主键
     */
    public void batchAdd(JSONArray data,int flag,String userId);

    /**
     * 价格分页列表查询
     * @param beginDateOne
     * @param beginDateTwo
     * @param price
     * @param code
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<PricePageVo> pricePage(String beginDateOne, String beginDateTwo, Double price, String code, Integer status, Integer pageNum, Integer pageSize);

    /**
     * 价格导出数据查询,同时用于价格打印的数据查询
     * @param beginDateOne
     * @param beginDateTwo
     * @param price
     * @param code
     * @param status
     * @return
     */
    public List<PricePageVo> priceExport(String beginDateOne, String beginDateTwo, Double price, String code, Integer status);

    /**
     * 价格编码变更操作
     * @param userId
     */
    public void priceChangeCode(String userId);

    /**
     * 获取当前价格版本和价格编码
     * @return
     */
    public JSONObject getPriceVersionAndCode();

    /**
     * 判断价格在当前版本是否已存在
     * @param price
     * @return
     */
    public boolean priceValidate(double price);

    /**
     * 根据价格或者编码，获取当前版本下的价格编码信息，flag：0价格 1编码
     * @param flag 0价格 1编码
     * @param param
     * @return
     */
    public Price getPriceByPriceOrCode(Integer flag, String param);
}
