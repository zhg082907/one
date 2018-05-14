package com.qhgrain.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qhgrain.Util.CodeUtil;
import com.qhgrain.Util.Constant;
import com.qhgrain.mapper.CustomerMapper;
import com.qhgrain.mapper.VersionMapper;
import com.qhgrain.pojo.Customer;
import com.qhgrain.pojo.Price;
import com.qhgrain.pojo.Version;
import com.qhgrain.service.ICustomerService;
import com.qhgrain.vo.CustomerPageVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("customerServiceImpl")
@Transactional
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private VersionMapper versionMapper;
    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 批量插入操作
     * @param data
     * @param flag 插入标记 0导入 1新增 2变更
     * @param userId 当前登录人主键
     */
    @Override
    public void batchAdd(JSONArray data, int flag, String userId) {
        //获取版本信息
        Version v = this.getCustomerVersion(flag,userId);
        //整合客商信息
        List<Customer> customers = this.getCustomers(data, v.getId(), flag, userId);
        //执行插入操作
        this.customerMapper.batchAdd(customers);
    }

    /**
     * 整合客商数据
     * @param data
     * @param id
     * @param flag 插入标记 0导入 1新增 2变更
     * @param userId
     * @return
     */
    private List<Customer> getCustomers(JSONArray data, Integer id, int flag, String userId) {
        //获取当前版本最新编码
        String code = this.customerMapper.getMaxCodeByVersionId(id);
        List<Customer> list = new ArrayList<Customer>();
        for (int i = 0; i < data.size(); i++) {
            Customer customer = new Customer();
            customer.setCustomer(data.getJSONObject(i).getString("customer"));
            customer.setFlag(flag);
            if(StringUtils.isEmpty(code)){
                //没有编号信息，也就是没有版本信息
                code = Constant.FIRST_CODE;
            }else{
                code = CodeUtil.getCode(code);
            }
            customer.setCode(code);
            customer.setVid(id);
            customer.setCreateDate(new SimpleDateFormat(Constant.DATE_FORMAT).format(new Date()));
            customer.setCreateId(userId);
            list.add(customer);
        }
        return list;
    }

    /**
     * 批量插入时，获取版本信息
     * @param flag 插入标记 0导入 1新增 2变更
     * @param userId
     * @return
     */
    private Version getCustomerVersion(int flag, String userId) {
        //获取当前生效版本信息
        Version v = this.versionMapper.getOnVersion(Constant.VERSION_TYPE_CUSTOMER,Constant.VERSION_ON);
        if(v != null){
            if(flag == Constant.BATCHADD_FLAG){
                //导入
                //创建新版本
                Version newVersion = new Version();
                newVersion.setCode(CodeUtil.getVersionCode(v.getCode()));
                newVersion.setType(Constant.VERSION_TYPE_CUSTOMER);
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
                //将旧版本更新为失效，失效时间赋值为新版本生效时间
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
            v.setType(Constant.VERSION_TYPE_CUSTOMER);
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
     * 客商导出数据查询,同时用于客商打印的数据查询
     * @param beginDateOne
     * @param beginDateTwo
     * @param customer
     * @param code
     * @param status
     * @return
     */
    @Override
    public List<CustomerPageVo> customerExport(String beginDateOne, String beginDateTwo, String customer, String code, Integer status) {
        List<CustomerPageVo> list = this.customerMapper.customerList(Constant.VERSION_TYPE_CUSTOMER,beginDateOne,beginDateTwo,customer,code,status);
        return list;
    }

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
    @Override
    public PageInfo<CustomerPageVo> customerPage(String beginDateOne, String beginDateTwo, String customer, String code, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<CustomerPageVo> list = this.customerMapper.customerList(Constant.VERSION_TYPE_CUSTOMER,beginDateOne,beginDateTwo,customer,code,status);
        PageInfo<CustomerPageVo> pi = new PageInfo<CustomerPageVo>(list);
        return pi;
    }

    /**
     * 客商编码变更操作
     * @param userId
     */
    @Override
    public void customerChangeCode(String userId) {
        //获取当前客商生效版本信息
        Version v = this.versionMapper.getOnVersion(Constant.VERSION_TYPE_CUSTOMER, Constant.VERSION_ON);
        if(v != null){
            //创建新版本
            Version newVersion = new Version();
            newVersion.setType(Constant.VERSION_TYPE_CUSTOMER);
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
            //获取对应的客商编码信息
            List<Customer> list = this.customerMapper.getCustomerListByVersionId(v.getId());
            if(list != null){
                //整合客商编码信息
                List<String> codes = new ArrayList<String>();
                for (Customer customer :list) {
                    codes.add(customer.getCode());
                }
                for (int i = 0; i < list.size(); i++) {
                    Customer c = list.get(i);
                    c.setVid(newVersion.getId());
                    String code = "";
                    int index = (int)(Math.random() * codes.size());
                    code = codes.get(index);
                    codes.remove(index);
                    c.setCode(code);
                    c.setCreateId(userId);
                    c.setCreateDate(date);
                    c.setFlag(Constant.CHANGE_FLAG);
                }
                //插入新客商编码信息
                this.customerMapper.batchAdd(list);
            }
        }
    }

    /**
     * 获取当前客商版本和客商编码
     * @return
     */
    @Override
    public JSONObject getCustomerVersionAndCode() {
        JSONObject jo = new JSONObject();
        String versionCode = "";
        String customerCode = "";
        //获取当前客商版本信息
        Version v = this.versionMapper.getOnVersion(Constant.VERSION_TYPE_CUSTOMER, Constant.VERSION_ON);
        if(v != null){
            versionCode = v.getCode();
            //获取当前版本下，最新客商编码
            String code = this.customerMapper.getMaxCodeByVersionId(v.getId());
            if(StringUtils.isEmpty(code)){
                //没有编号信息，给予初始化值
                customerCode = Constant.FIRST_CODE;
            }else{
                //获取下一个客商编码
                customerCode = CodeUtil.getCode(code);
            }
        }else{
            //没有版本信息
            //直接复制版本的初始化信息和编码的初始化信息
            versionCode = Constant.VERSION_FIRST_CODE;
            customerCode = Constant.FIRST_CODE;
        }
        jo.put("versionCode",versionCode);
        jo.put("customerCode",customerCode);
        return jo;
    }

    /**
     * 判断客商在当前版本是否已存在
     * @param customer
     * @return
     */
    @Override
    public Boolean customerValidate(String customer) {
        Boolean flag = false;
        //获取当前生效版本
        Version v = this.versionMapper.getOnVersion(Constant.VERSION_TYPE_CUSTOMER, Constant.VERSION_ON);
        if(v != null){
            //根据价格，获取当前版本的价格信息
            Customer c = this.customerMapper.getCustomerByVersionIdAndCustomer(v.getId(),customer);
            if(c != null){
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 根据客商或者编码，获取当前版本下的客商编码信息，flag：0客商 1编码
     * @param flag 0客商 1编码
     * @param param
     * @return
     */
    @Override
    public Customer getCustomerByCustomerOrCode(Integer flag, String param) {
        //获取当前版本信息
        Version v = this.versionMapper.getOnVersion(Constant.VERSION_TYPE_CUSTOMER, Constant.VERSION_ON);
        Customer c = null;
        if(v != null){
            //获取客商编码信息
            if(flag == 0){
                //客商为参数
                c = this.customerMapper.getCustomerByVersionIdAndCustomer(v.getId(), param);
            }else if(flag == 1){
                //编码为参数
                c = this.customerMapper.getCustomerByVersionIdAndCode(v.getId(), param);
            }
        }
        return c;
    }
}
