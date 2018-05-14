package com.qhgrain.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qhgrain.Util.CodeUtil;
import com.qhgrain.Util.Constant;
import com.qhgrain.mapper.PriceMapper;
import com.qhgrain.mapper.VersionMapper;
import com.qhgrain.pojo.Price;
import com.qhgrain.pojo.Version;
import com.qhgrain.service.IPriceService;
import com.qhgrain.vo.PricePageVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service("priceServiceImpl")
@Transactional
public class PriceServiceImpl implements IPriceService {
    @Autowired
    private VersionMapper versionMapper;
    @Autowired
    private PriceMapper priceMapper;

    /**
     * 批量插入操作
     * @param data
     * @param flag 插入标记 0导入 1新增 2变更
     * @param userId 当前登录人主键
     */
    @Override
    public void batchAdd(JSONArray data, int flag, String userId) {
        //获取版本信息
        Version v = this.getPriceVersion(flag,userId);
        //整合价格信息
        List<Price> prices = this.getPrices(data, v.getId(), flag, userId);
        //执行插入操作
        this.priceMapper.batchAdd(prices);
    }

    /**
     * 整合价格数据
     * @param data
     * @param id
     * @param flag 插入标记 0导入 1新增 2变更
     * @return
     */
    private List<Price> getPrices(JSONArray data,Integer id, int flag, String userId) {
        //获取当前版本最新编码
        String code = this.priceMapper.getMaxCodeByVersionId(id);
        List<Price> list = new ArrayList<Price>();
        for (int i = 0; i < data.size(); i++) {
            Price price = new Price();
            price.setPrice(data.getJSONObject(i).getDouble("price"));
            price.setFlag(flag);
            if(StringUtils.isEmpty(code)){
                //没有编号信息，也就是没有版本信息
                code = Constant.FIRST_CODE;
            }else{
                code = CodeUtil.getCode(code);
            }
            price.setCode(code);
            price.setVid(id);
            price.setCreateDate(new SimpleDateFormat(Constant.DATE_FORMAT).format(new Date()));
            price.setCreateId(userId);
            list.add(price);
        }
        return list;
    }

    /**
     * 批量插入时，获取版本信息
     * @param flag 插入标记 0导入 1新增 2变更
     * @param userId 登录人主键
     * @return
     */
    private Version getPriceVersion(int flag , String userId) {
        //获取当前生效版本信息
        Version v = this.versionMapper.getOnVersion(Constant.VERSION_TYPE_PRICE,Constant.VERSION_ON);
        if(v != null){
            if(flag == Constant.BATCHADD_FLAG){
                //导入
                //创建新版本
                Version newVersion = new Version();
                newVersion.setCode(CodeUtil.getVersionCode(v.getCode()));
                newVersion.setType(Constant.VERSION_TYPE_PRICE);
                newVersion.setStatus(Constant.VERSION_ON);
                SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_FORMAT);
                String date = format.format(new Date());
                String newDate = date;
                try {
                    newDate = format.format(format.parse(date).getTime() + 1000);
                }catch (Exception e){}
                newVersion.setBeginDate(newDate);
                newVersion.setCreateDate(newDate);
                newVersion.setCreateId(userId);
                //将旧版本更新为失效
                this.versionMapper.updVersionStatus(v.getId(),Constant.VERSION_OFF,date);
                //插入新版本
                this.versionMapper.add(newVersion);
                v = newVersion;
            }
        }else{
            //当前无生效的版本，也就是还没有生成版本信息
            //创建一个V1.0版本信息
            v = new Version();
            v.setCode(Constant.VERSION_FIRST_CODE);
            v.setType(Constant.VERSION_TYPE_PRICE);
            v.setStatus(Constant.VERSION_ON);
            String date = new SimpleDateFormat(Constant.DATE_FORMAT).format(new Date());
            v.setBeginDate(date);
            v.setCreateDate(date);
            v.setCreateId(userId);
            this.versionMapper.add(v);
        }
        return v;
    }

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
    @Override
    public PageInfo<PricePageVo> pricePage(String beginDateOne, String beginDateTwo, Double price, String code, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<PricePageVo> list = this.priceMapper.priceList(Constant.VERSION_TYPE_PRICE,beginDateOne,beginDateTwo,price,code,status);
        PageInfo<PricePageVo> pi = new PageInfo<PricePageVo>(list);
        return pi;
    }

    /**
     * 价格导出数据查询,同时用于价格打印的数据查询
     * @param beginDateOne
     * @param beginDateTwo
     * @param price
     * @param code
     * @param status
     * @return
     */
    @Override
    public List<PricePageVo> priceExport(String beginDateOne, String beginDateTwo, Double price, String code, Integer status) {
        List<PricePageVo> list = this.priceMapper.priceList(Constant.VERSION_TYPE_PRICE,beginDateOne,beginDateTwo,price,code,status);
        return list;
    }

    /**
     * 价格编码变更操作
     * @param userId
     */
    @Override
    public void priceChangeCode(String userId) {
        //获取当前价格生效版本信息
        Version v = this.versionMapper.getOnVersion(Constant.VERSION_TYPE_PRICE, Constant.VERSION_ON);
        if(v != null){
            //创建新版本
            Version newVersion = new Version();
            newVersion.setType(Constant.VERSION_TYPE_PRICE);
            newVersion.setCode(CodeUtil.getVersionCode(v.getCode()));
            newVersion.setStatus(Constant.VERSION_ON);
            SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_FORMAT);
            String date = format.format(new Date());
            String newDate = date;
            try {
                newDate = format.format(format.parse(date).getTime() + 1000);
            }catch (Exception e){}
            newVersion.setBeginDate(newDate);
            newVersion.setCreateDate(newDate);
            newVersion.setCreateId(userId);
            //更新旧版本
            this.versionMapper.updVersionStatus(v.getId(),Constant.VERSION_OFF,date);
            //插入新版本
            this.versionMapper.add(newVersion);
            //获取对应的价格编码信息
            List<Price> list = this.priceMapper.getPriceListByVersionId(v.getId());
            if(list != null){
                //整合价格编码信息
                List<String> codes = new ArrayList<String>();
                for (Price price :list) {
                    codes.add(price.getCode());
                }
                for (int i = 0; i < list.size(); i++) {
                    Price p = list.get(i);
                    p.setVid(newVersion.getId());
                    String code = "";
                    int index = (int)(Math.random() * codes.size());
                    code = codes.get(index);
                    codes.remove(index);
                    p.setCode(code);
                    p.setCreateId(userId);
                    p.setCreateDate(date);
                    p.setFlag(Constant.CHANGE_FLAG);
                }
                //插入新价格编码信息
                this.priceMapper.batchAdd(list);
            }
        }
    }

    /**
     * 获取当前价格版本和价格编码
     * @return
     */
    @Override
    public JSONObject getPriceVersionAndCode() {
        JSONObject jo = new JSONObject();
        String versionCode = "";
        String priceCode = "";
        //获取当前价格版本信息
        Version v = this.versionMapper.getOnVersion(Constant.VERSION_TYPE_PRICE, Constant.VERSION_ON);
        if(v != null){
            versionCode = v.getCode();
            //获取当前版本下，最新价格编码
            String code = this.priceMapper.getMaxCodeByVersionId(v.getId());
            if(StringUtils.isEmpty(code)){
                //没有编号信息，给予初始化值
                priceCode = Constant.FIRST_CODE;
            }else{
                //获取下一个价格编码
                priceCode = CodeUtil.getCode(code);
            }
        }else{
            //没有版本信息
            //直接复制版本的初始化信息和编码的初始化信息
            versionCode = Constant.VERSION_FIRST_CODE;
            priceCode = Constant.FIRST_CODE;
        }
        jo.put("versionCode",versionCode);
        jo.put("priceCode",priceCode);
        return jo;
    }

    /**
     * 判断价格在当前版本是否已存在
     * @param price
     * @return
     */
    @Override
    public boolean priceValidate(double price) {
        boolean flag = false;
        //获取当前生效版本
        Version v = this.versionMapper.getOnVersion(Constant.VERSION_TYPE_PRICE, Constant.VERSION_ON);
        if(v != null){
            //根据价格，获取当前版本的价格信息
            Price p = this.priceMapper.getPriceByVersionIdAndPrice(v.getId(),price);
            if(p != null){
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 根据价格或者编码，获取当前版本下的价格编码信息，flag：0价格 1编码
     * @param flag 0价格 1编码
     * @param param
     * @return
     */
    @Override
    public Price getPriceByPriceOrCode(Integer flag, String param) {
        //获取当前版本信息
        Version v = this.versionMapper.getOnVersion(Constant.VERSION_TYPE_PRICE, Constant.VERSION_ON);
        Price p = null;
        if(v != null){
            //获取价格编码信息
            if(flag == 0){
                //价格为参数
                try{
                    double price = Double.parseDouble(param);
                    p = this.priceMapper.getPriceByVersionIdAndPrice(v.getId(), price);
                }catch (Exception e){
                    return null;
                }
            }else if(flag == 1){
                //编码为参数
                p = this.priceMapper.getPriceByVersionIdAndCode(v.getId(), param);
            }
        }
        return p;
    }
}
