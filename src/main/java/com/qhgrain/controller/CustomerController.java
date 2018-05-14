package com.qhgrain.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.qhgrain.Util.Constant;
import com.qhgrain.Util.FileUtil;
import com.qhgrain.Util.UserUtil;
import com.qhgrain.config.WebLog;
import com.qhgrain.pojo.Customer;
import com.qhgrain.pojo.Price;
import com.qhgrain.service.ICustomerService;
import com.qhgrain.vo.CustomerImportVo;
import com.qhgrain.vo.CustomerPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Api(description = "客商控制器")
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @WebLog
    @ApiOperation(value = "客商导入",httpMethod = "POST",notes = "客商导入")
    @RequestMapping(value = "/customerImport", method = RequestMethod.POST)
    @ResponseBody
    public synchronized Object customerImport(@RequestParam("file") MultipartFile file , HttpServletRequest request){
        if(file == null || file.isEmpty()){
            return null;
        }
        JSONObject jo = FileUtil.customerImport(file, 0, 1, CustomerImportVo.class);
        int i = jo.getIntValue("code");
        if(i == 0){
            //数据解析成功
            JSONArray data = jo.getJSONArray("data");
            if(data != null && data.size() > 0){
                //在业务层，整合数据，执行插入操作
                this.customerService.batchAdd(data, Constant.BATCHADD_FLAG, UserUtil.getUserId(request));
            }
            jo.remove("data");
        }
        return jo;
    }

    @WebLog
    @ApiOperation(value = "客商导出",httpMethod = "GET",notes = "客商导出")
    @RequestMapping(value = "/customerExport", method = RequestMethod.GET)
    public void customerExport(@RequestParam("beginDateOne") String beginDateOne,
                               @RequestParam("beginDateTwo") String beginDateTwo,
                               @RequestParam("customer") String customer,
                               @RequestParam("code") String code,
                               @RequestParam("status") Integer status,
                               HttpServletRequest request,
                               HttpServletResponse response){
        //过滤参数
        if(StringUtils.isEmpty(beginDateOne)){
            beginDateOne = null;
        }
        if(StringUtils.isEmpty(beginDateTwo)){
            beginDateTwo = null;
        }
        if(StringUtils.isEmpty(customer)){
            customer = null;
        }
        if(StringUtils.isEmpty(code)){
            code = null;
        }
        //根据条件，获取导出的数据
        List<CustomerPageVo> list = this.customerService.customerExport(beginDateOne,beginDateTwo,customer,code,status);
        //将获取到的数据执行导出
        FileUtil.exportExcel(list,null,null,CustomerPageVo.class,Constant.CUSTOMER_CODE_FILENAME,response);
    }

    @WebLog
    @ApiOperation(value = "客商分页列表",httpMethod = "GET",notes = "客商分页列表")
    @RequestMapping(value = "/customerPage", method = RequestMethod.GET)
    @ResponseBody
    public Object customerPage(@RequestParam("beginDateOne") String beginDateOne,
                               @RequestParam("beginDateTwo") String beginDateTwo,
                               @RequestParam("customer") String customer,
                               @RequestParam("code") String code,
                               @RequestParam("status") Integer status,
                               @RequestParam("pageNum") Integer pageNum,
                               @RequestParam("pageSize") Integer pageSize,
                               HttpServletRequest request){
        //过滤参数
        if(StringUtils.isEmpty(beginDateOne)){
            beginDateOne = null;
        }
        if(StringUtils.isEmpty(beginDateTwo)){
            beginDateTwo = null;
        }
        if(StringUtils.isEmpty(customer)){
            customer = null;
        }
        if(StringUtils.isEmpty(code)){
            code = null;
        }
        if(pageNum == null){
            pageNum = Constant.PAGE_NUM;
        }
        if(pageSize == null){
            pageSize = Constant.PAGE_SIZE;
        }
        //根据条件，在业务层执行分页查询
        PageInfo<CustomerPageVo> pi = this.customerService.customerPage(beginDateOne,beginDateTwo,customer,code,status,pageNum,pageSize);
        //将分页信息反馈前端
        return pi;
    }

    @WebLog
    @ApiOperation(value = "客商导入模板下载",httpMethod = "GET",notes = "客商导入模板下载")
    @RequestMapping(value = "/customerTemplate", method = RequestMethod.GET)
    public void customerTemplate(HttpServletRequest request,HttpServletResponse response){
        //封装客商模板
        List<CustomerImportVo> list = new ArrayList<CustomerImportVo>();
        CustomerImportVo vo = new CustomerImportVo();
        vo.setCustomer("");
        //下载模板
        FileUtil.exportExcel(list,null,null,CustomerImportVo.class,Constant.CUSTOMER_TEMPLATE_NAME,response);
    }

    @WebLog
    @ApiOperation(value = "变更编码",httpMethod = "GET",notes = "变更编码")
    @RequestMapping(value = "/customerChangeCode", method = RequestMethod.GET)
    @ResponseBody
    public synchronized Object customerChangeCode(HttpServletRequest request){
        //业务层，对当前生效的客商版本下的客商和编码进行变更
        this.customerService.customerChangeCode(UserUtil.getUserId(request));
        //请求成功，则说明变更操作成功，返回null即可
        return null;
    }

    @WebLog
    @ApiOperation(value = "客商打印",httpMethod = "GET",notes = "客商打印")
    @RequestMapping(value = "/customerPrint", method = RequestMethod.GET)
    @ResponseBody
    public Object customerPrint(@RequestParam("beginDateOne") String beginDateOne,
                                @RequestParam("beginDateTwo") String beginDateTwo,
                                @RequestParam("customer") String customer,
                                @RequestParam("code") String code,
                                @RequestParam("status") Integer status,
                                HttpServletRequest request){
        //过滤参数
        if(StringUtils.isEmpty(beginDateOne)){
            beginDateOne = null;
        }
        if(StringUtils.isEmpty(beginDateTwo)){
            beginDateTwo = null;
        }
        if(StringUtils.isEmpty(customer)){
            customer = null;
        }
        if(StringUtils.isEmpty(code)){
            code = null;
        }
        //根据条件，获取打印的数据
        List<CustomerPageVo> list = this.customerService.customerExport(beginDateOne,beginDateTwo,customer,code,status);
        //将打印数据反馈前端
        return list;
    }

    @WebLog
    @ApiOperation(value = "获取客商版本和客商编码",httpMethod = "GET",notes = "获取客商版本和客商编码，用于客商新增界面显示版本号和编码")
    @RequestMapping(value = "/getCustomerVersionAndCode", method = RequestMethod.GET)
    @ResponseBody
    public Object getCustomerVersionAndCode(HttpServletRequest request){
        //在业务层获取当前客商版本和客商编码
        JSONObject jo = this.customerService.getCustomerVersionAndCode();
        //反馈前端
        return jo;
    }

    @WebLog
    @ApiOperation(value = "客商新增",httpMethod = "GET",notes = "客商新增")
    @RequestMapping(value = "/customerAdd", method = RequestMethod.GET)
    @ResponseBody
    public synchronized Object customerAdd(@RequestParam("customer") String c, HttpServletRequest request){
        //过滤参数
        if(StringUtils.isEmpty(c)){
            return null;
        }
        //在业务层判断，是否存在，防止校验时没有，但是提交的时候，已经被人提交过了同样的客商
        Boolean flag = this.customerService.customerValidate(c);
        if(!flag){
            //不存在，继续执行插入
            JSONArray ja = new JSONArray();
            JSONObject jo = new JSONObject();
            jo.put("customer",c);
            ja.add(jo);
            //在业务层整合客商信息，执行插入
            this.customerService.batchAdd(ja,Constant.ADD_FLAG,UserUtil.getUserId(request));
        }
        //操作成功，直接返回null
        return null;
    }

    @WebLog
    @ApiOperation(value = "客商校验",httpMethod = "GET",notes = "客商校验，判断输入的客商在当前客商版本是否已存在")
    @RequestMapping(value = "/customerValidate", method = RequestMethod.GET)
    @ResponseBody
    public Object customerValidate(@RequestParam("customer") String customer, HttpServletRequest request){
        JSONObject jo = new JSONObject();
        //过滤参数
        if(StringUtils.isEmpty(customer)){
            jo.put("code",Constant.ZERO);
            return jo;
        }
        //在业务层判断，是否存在
        Boolean flag = this.customerService.customerValidate(customer);
        if(flag){
            //存在
            jo.put("code",Constant.ONE);
            jo.put("msg","客商已存在");
        }else{
            //不存在
            jo.put("code",Constant.ZERO);
        }
        return jo;
    }

    @WebLog
    @ApiOperation(value = "查询客商编码信息",httpMethod = "GET",notes = "根据客商或者编码，获取当前版本下的客商编码信息，flag：0客商 1编码")
    @RequestMapping(value = "/getCustomerByCustomerOrCode", method = RequestMethod.GET)
    @ResponseBody
    public Object getCustomerByCustomerOrCode(@RequestParam("flag") Integer flag,@RequestParam("param") String param){
        JSONObject jo = new JSONObject();
        jo.put("code",Constant.ONE);
        jo.put("msg","不存在");
        if(flag != null && StringUtils.isNotEmpty(param)){
            //在业务层，根据客商或者编码，获取当前版本下的客商编码信息
            Customer customer = this.customerService.getCustomerByCustomerOrCode(flag,param);
            if(customer != null){
                jo.put("code",Constant.ZERO);
                jo.put("data",customer);
                jo.remove("msg");
            }
        }
        return jo;
    }
}
