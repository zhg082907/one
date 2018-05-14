package com.qhgrain.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.qhgrain.Util.Constant;
import com.qhgrain.Util.FileUtil;
import com.qhgrain.Util.UserUtil;
import com.qhgrain.config.WebLog;
import com.qhgrain.pojo.Price;
import com.qhgrain.service.IPriceService;
import com.qhgrain.vo.PriceImportVo;
import com.qhgrain.vo.PricePageVo;
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

@Api(description = "价格控制器")
@RestController
@RequestMapping("/price")
public class PriceController {
    @Autowired
    private IPriceService priceService;

    @WebLog
    @ApiOperation(value = "价格导入",httpMethod = "POST",notes = "价格导入")
    @RequestMapping(value = "/priceImport", method = RequestMethod.POST)
    @ResponseBody
    public synchronized Object priceImport(@RequestParam("file") MultipartFile file , HttpServletRequest request){
        if(file == null || file.isEmpty()){
            return null;
        }
        JSONObject jo = FileUtil.priceImport(file, 0, 1, PriceImportVo.class);
        int i = jo.getIntValue("code");
        if(i == 0){
            //数据解析成功
            JSONArray data = jo.getJSONArray("data");
            if(data != null && data.size() > 0){
                //在业务层，整合数据，执行插入操作
                this.priceService.batchAdd(data, Constant.BATCHADD_FLAG, UserUtil.getUserId(request));
            }
            jo.remove("data");
        }
        return jo;
    }

    @WebLog
    @ApiOperation(value = "价格导出",httpMethod = "GET",notes = "价格导出")
    @RequestMapping(value = "/priceExport", method = RequestMethod.GET)
    public void priceExport(@RequestParam("beginDateOne") String beginDateOne,
                            @RequestParam("beginDateTwo") String beginDateTwo,
                            @RequestParam("price") Double price,
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
        if(StringUtils.isEmpty(code)){
            code = null;
        }
        //根据条件，获取导出的数据
        List<PricePageVo> list = this.priceService.priceExport(beginDateOne,beginDateTwo,price,code,status);
        //将获取到的数据执行导出
        FileUtil.exportExcel(list,null,null,PricePageVo.class,Constant.PRICE_CODE_FILENAME,response);
    }

    @WebLog
    @ApiOperation(value = "价格分页列表",httpMethod = "GET",notes = "价格分页列表")
    @RequestMapping(value = "/pricePage", method = RequestMethod.GET)
    @ResponseBody
    public Object pricePage(@RequestParam("beginDateOne") String beginDateOne,
                            @RequestParam("beginDateTwo") String beginDateTwo,
                            @RequestParam("price") Double price,
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
        PageInfo<PricePageVo> pi = this.priceService.pricePage(beginDateOne,beginDateTwo,price,code,status,pageNum,pageSize);
        //将分页信息反馈前端
        return pi;
    }

    @WebLog
    @ApiOperation(value = "价格导入模板下载",httpMethod = "GET",notes = "价格导入模板下载")
    @RequestMapping(value = "/priceTemplate", method = RequestMethod.GET)
    public void priceTemplate(HttpServletRequest request,HttpServletResponse response){
        //封装价格模板
        List<PriceImportVo> list = new ArrayList<PriceImportVo>();
        PriceImportVo vo = new PriceImportVo();
        vo.setPrice("");
        //下载模板
        FileUtil.exportExcel(list,null,null,PriceImportVo.class,Constant.PRICE_TEMPLATE_NAME,response);
    }

    @WebLog
    @ApiOperation(value = "变更编码",httpMethod = "GET",notes = "变更编码")
    @RequestMapping(value = "/priceChangeCode", method = RequestMethod.GET)
    @ResponseBody
    public synchronized Object priceChangeCode(HttpServletRequest request){
        //业务层，对当前生效的价格版本下的价格和编码进行变更
        this.priceService.priceChangeCode(UserUtil.getUserId(request));
        //请求成功，则说明变更操作成功，返回null即可
        return null;
    }

    @WebLog
    @ApiOperation(value = "价格打印",httpMethod = "GET",notes = "价格打印")
    @RequestMapping(value = "/pricePrint", method = RequestMethod.GET)
    @ResponseBody
    public Object pricePrint(@RequestParam("beginDateOne") String beginDateOne,
                             @RequestParam("beginDateTwo") String beginDateTwo,
                             @RequestParam("price") Double price,
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
        if(StringUtils.isEmpty(code)){
            code = null;
        }
        //根据条件，获取打印的数据
        List<PricePageVo> list = this.priceService.priceExport(beginDateOne,beginDateTwo,price,code,status);
        //将打印数据反馈前端
        return list;
    }

    @WebLog
    @ApiOperation(value = "获取价格版本和价格编码",httpMethod = "GET",notes = "获取价格版本和价格编码，用于价格新增界面显示版本号和编码")
    @RequestMapping(value = "/getPriceVersionAndCode", method = RequestMethod.GET)
    @ResponseBody
    public Object getPriceVersionAndCode(HttpServletRequest request){
        //在业务层获取当前价格版本和价格编码
        JSONObject jo = this.priceService.getPriceVersionAndCode();
        //反馈前端
        return jo;
    }

    @WebLog
    @ApiOperation(value = "价格新增",httpMethod = "GET",notes = "价格新增")
    @RequestMapping(value = "/priceAdd", method = RequestMethod.GET)
    @ResponseBody
    public synchronized Object priceAdd(@RequestParam("price") String p, HttpServletRequest request){
        //过滤参数
        if(StringUtils.isEmpty(p)){
            return null;
        }
        double price = Double.parseDouble(p);
        //在业务层判断，是否存在，防止校验时没有，但是提交的时候，已经被人提交过了同样的价格
        Boolean flag = this.priceService.priceValidate(price);
        if(!flag){
            //不存在，继续执行插入
            JSONArray ja = new JSONArray();
            JSONObject jo = new JSONObject();
            jo.put("price",price);
            ja.add(jo);
            //在业务层整合价格信息，执行插入
            this.priceService.batchAdd(ja,Constant.ADD_FLAG,UserUtil.getUserId(request));
        }
        //操作成功，直接返回null
        return null;
    }

    @WebLog
    @ApiOperation(value = "价格校验",httpMethod = "GET",notes = "价格校验，判断输入的价格在当前价格版本是否已存在")
    @RequestMapping(value = "/priceValidate", method = RequestMethod.GET)
    @ResponseBody
    public Object priceValidate(@RequestParam("price") String p, HttpServletRequest request){
        JSONObject jo = new JSONObject();
        //过滤参数
        if(StringUtils.isEmpty(p)){
            jo.put("code",Constant.ZERO);
            return jo;
        }
        double price = Double.parseDouble(p);
        //在业务层判断，是否存在
        boolean flag = this.priceService.priceValidate(price);
        if(flag){
            //存在
            jo.put("code",Constant.ONE);
            jo.put("msg","价格已存在");
        }else{
            //不存在
            jo.put("code",Constant.ZERO);
        }
        return jo;
    }

    @WebLog
    @ApiOperation(value = "查询价格编码信息",httpMethod = "GET",notes = "根据价格或者编码，获取当前版本下的价格编码信息，flag：0价格 1编码")
    @RequestMapping(value = "/getPriceByPriceOrCode", method = RequestMethod.GET)
    @ResponseBody
    public Object getPriceByPriceOrCode(@RequestParam("flag") Integer flag,@RequestParam("param") String param){
        JSONObject jo = new JSONObject();
        jo.put("code",Constant.ONE);
        jo.put("msg","不存在");
        if(flag != null && StringUtils.isNotEmpty(param)){
            //在业务层，根据价格或者编码，获取当前版本下的价格编码信息
            Price price = this.priceService.getPriceByPriceOrCode(flag,param);
            if(price != null){
                jo.put("code",Constant.ZERO);
                jo.put("data",price);
                jo.remove("msg");
            }
        }
        return jo;
    }
}
