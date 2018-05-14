package com.qhgrain.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.qhgrain.Util.Constant;
import com.qhgrain.Util.FileUtil;
import com.qhgrain.Util.UserUtil;
import com.qhgrain.config.WebLog;
import com.qhgrain.pojo.Customer;
import com.qhgrain.pojo.Price;
import com.qhgrain.pojo.Report;
import com.qhgrain.pojo.Version;
import com.qhgrain.service.IReportService;
import com.qhgrain.vo.ReportImportVo;
import com.qhgrain.vo.ReportPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(description = "销售报表控制器")
@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private IReportService reportService;

    @WebLog
    @ApiOperation(value = "销售报表分页列表",httpMethod = "GET",notes = "销售报表分页列表")
    @RequestMapping(value = "/reportPage", method = RequestMethod.GET)
    @ResponseBody
    public Object reportPage(@RequestParam("billingDateOne") String billingDateOne,
                             @RequestParam("billingDateTwo") String billingDateTwo,
                             @RequestParam("outDateOne") String outDateOne,
                             @RequestParam("outDateTwo") String outDateTwo,
                             @RequestParam("outWay") String outWay,
                             @RequestParam("grainOilKind") String grainOilKind,
                             @RequestParam("itemName") String itemName,
                             @RequestParam("customer") String customer,
                             @RequestParam("pageNum") Integer pageNum,
                             @RequestParam("pageSize") Integer pageSize,
                             HttpServletRequest request){
        //过滤参数
        if(StringUtils.isEmpty(billingDateOne)){
            billingDateOne = null;
        }
        if(StringUtils.isEmpty(billingDateTwo)){
            billingDateTwo = null;
        }
        if(StringUtils.isEmpty(outDateOne)){
            outDateOne = null;
        }
        if(StringUtils.isEmpty(outDateTwo)){
            outDateTwo = null;
        }
        if(StringUtils.isEmpty(outWay)){
            outWay = null;
        }
        if(StringUtils.isEmpty(grainOilKind)){
            grainOilKind = null;
        }
        if(StringUtils.isEmpty(itemName)){
            itemName = null;
        }
        if(StringUtils.isEmpty(customer)){
            customer = null;
        }
        if(pageNum == null){
            pageNum = Constant.PAGE_NUM;
        }
        if(pageSize == null){
            pageSize = Constant.PAGE_SIZE;
        }
        //根据条件，在业务层执行分页查询
        PageInfo<ReportPageVo> pi = this.reportService.reportPage(billingDateOne,billingDateTwo,outDateOne,outDateTwo,outWay,grainOilKind,itemName,customer,pageNum,pageSize);
        //将分页信息反馈前端
        return pi;
    }

    @WebLog
    @ApiOperation(value = "销售报表导出",httpMethod = "GET",notes = "销售报表导出")
    @RequestMapping(value = "/reportExport", method = RequestMethod.GET)
    public void reportExport(@RequestParam("billingDateOne") String billingDateOne,
                             @RequestParam("billingDateTwo") String billingDateTwo,
                             @RequestParam("outDateOne") String outDateOne,
                             @RequestParam("outDateTwo") String outDateTwo,
                             @RequestParam("outWay") String outWay,
                             @RequestParam("grainOilKind") String grainOilKind,
                             @RequestParam("itemName") String itemName,
                             @RequestParam("customer") String customer,
                             HttpServletRequest request,
                             HttpServletResponse response){
        //过滤参数
        if(StringUtils.isEmpty(billingDateOne)){
            billingDateOne = null;
        }
        if(StringUtils.isEmpty(billingDateTwo)){
            billingDateTwo = null;
        }
        if(StringUtils.isEmpty(outDateOne)){
            outDateOne = null;
        }
        if(StringUtils.isEmpty(outDateTwo)){
            outDateTwo = null;
        }
        if(StringUtils.isEmpty(outWay)){
            outWay = null;
        }
        if(StringUtils.isEmpty(grainOilKind)){
            grainOilKind = null;
        }
        if(StringUtils.isEmpty(itemName)){
            itemName = null;
        }
        if(StringUtils.isEmpty(customer)){
            customer = null;
        }
        //根据条件，获取导出的数据
        List<ReportPageVo> list = this.reportService.reportExport(billingDateOne,billingDateTwo,outDateOne,outDateTwo,outWay,grainOilKind,itemName,customer);
        //将获取到的数据执行导出
        FileUtil.exportExcel(list,null,null,ReportPageVo.class,Constant.REPORT_FILENAME,response);
    }

    @WebLog
    @ApiOperation(value = "销售报表导入",httpMethod = "POST",notes = "销售报表导入")
    @RequestMapping(value = "/reportImport", method = RequestMethod.POST)
    @ResponseBody
    public synchronized Object reportImport(@RequestParam("file") MultipartFile file , HttpServletRequest request) {
        if(file == null || file.isEmpty()){
            return null;
        }
        //存储集合
        List<Report> rs = new ArrayList<Report>();
        //响应信息
        JSONObject jo = new JSONObject();
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        try {
            ExcelImportResult<ReportImportVo> result = ExcelImportUtil.importExcelMore(file.getInputStream(), ReportImportVo.class, params);
            List<ReportImportVo> list = result.getList();
            if (list != null && list.size() > 0){
                //获取已存在销售报表数据
                Set<String> keys = this.reportService.getKeys();
                //获取价格版本集合
                List<Version> vps = this.reportService.getPriceVersions();
                //获取客商版本集合
                List<Version> vcs = this.reportService.getCustomerVersions();
                //获取价格集合
                List<Price> ps = this.reportService.getPrices();
                //获取客商集合
                List<Customer> cs = this.reportService.getCustomers();
                //获取excel对象
                Workbook book = result.getWorkbook();
                //获取sheet页签对象
                Sheet sheet = book.getSheetAt(0);
                //去重集合
                HashSet<String> hs = new HashSet<String>();
                //存储粮油品种集合
                HashSet<String> gokHs = new HashSet<String>();
                //解析是否成功标记
                int flag = 0;
                for (int i = 0; i < list.size(); i++) {
                    ReportImportVo vo = list.get(i);
                    Row row = sheet.getRow(i + 2);
                    String value = vo.getOutNoticeNum()+vo.getOutDate()+vo.getGrainOilKind()+vo.getItemName()+vo.getGrade()+vo.getSpec()+vo.getWhNum()+vo.getLocation();
                    int count = 0;
                    //校验是否重复
                    if (!hs.add(value)){
                        flag++;
                        row.createCell(14).setCellValue("数据重复");
                    }
                    //校验是否存在
                    if(keys.contains(value)){
                        flag++;
                        row.createCell(14).setCellValue("数据已存在");
                    }
                    //校验编码是否存在
                    long billingDate = new SimpleDateFormat(Constant.DATE_FORMAT).parse(vo.getBillingDate()).getTime();
                    Double unitPrice = null;
                    String customer = null;
                    for (Version v : vps){
                        long beginDate = new SimpleDateFormat(Constant.DATE_FORMAT).parse(v.getBeginDate()).getTime();
                        long endDate = 0;
                        if(v.getStatus() == 0){
                            endDate = System.currentTimeMillis();
                        }else{
                            endDate = new SimpleDateFormat(Constant.DATE_FORMAT).parse(v.getEndDate()).getTime();
                        }
                        if(billingDate >= beginDate && billingDate <= endDate){
                            for (Price p : ps) {
                                if(v.getId() == p.getVid() && p.getCode().equals(vo.getUnitPrice())){
                                    unitPrice = p.getPrice();
                                }
                            }
                        }
                    }
                    for (Version v : vcs) {
                        long beginDate = new SimpleDateFormat(Constant.DATE_FORMAT).parse(v.getBeginDate()).getTime();
                        long endDate = 0;
                        if(v.getStatus() == 0){
                            endDate = System.currentTimeMillis();
                        }else{
                            endDate = new SimpleDateFormat(Constant.DATE_FORMAT).parse(v.getEndDate()).getTime();
                        }
                        if(billingDate >= beginDate && billingDate <= endDate){
                            for (Customer c : cs) {
                                if(v.getId() == c.getVid() && c.getCode().equals(vo.getCustomer())){
                                    customer = c.getCustomer();
                                }
                            }
                        }
                    }
                    if (unitPrice == null){
                        flag++;
                        row.createCell(14).setCellValue("价格编码不存在");
                    }
                    if (customer == null){
                        flag++;
                        row.createCell(14).setCellValue("客商编码不存在");
                    }
                    if (unitPrice == null && customer == null){
                        flag++;
                        row.createCell(14).setCellValue("价格和客商编码均不存在");
                    }
                    if (unitPrice != null && customer != null){
                        Report r = new Report();
                        r.setBillingDate(vo.getBillingDate());
                        r.setCreateDate(new SimpleDateFormat(Constant.DATE_FORMAT).format(new Date()));
                        r.setCreateId(UserUtil.getUserId(request));
                        r.setCustomer(customer);
                        r.setGrade(vo.getGrade());
                        r.setGrainOilKind(vo.getGrainOilKind());
                        r.setItemName(vo.getItemName());
                        r.setLocation(vo.getLocation());
                        Integer num = vo.getNum();
                        r.setNum(num);
                        r.setOutDate(vo.getOutDate());
                        r.setOutNoticeNum(vo.getOutNoticeNum());
                        r.setOutWay(vo.getOutWay());
                        Integer spec = vo.getSpec();
                        r.setSpec(spec);
                        r.setWhNum(vo.getWhNum());
                        r.setUnitPrice(unitPrice);
                        r.setTotalPrice((double) Math.round((num * spec * unitPrice) * 100000) / 100000);
                        rs.add(r);
                    }
                    //添加粮油品种
                    gokHs.add(vo.getGrainOilKind());
                }
                if(flag == 0){
                    //数据解析成功
                    if(rs != null && rs.size() > 0){
                        //在业务层，执行插入操作
                        List<String> gokList = new ArrayList<String>(gokHs);
                        this.reportService.batchAdd(rs,gokList);
                    }
                    jo.put("code",Constant.ZERO);
                    jo.put("msg","操作成功");
                }else{
                    FileUtil.makeResult(flag,jo,book,result);
                }
            }
        } catch (Exception e) {
            jo.put("code",Constant.TWO);
            jo.put("msg","文件有误");
        }
        return jo;
    }

    /**
     *
     * @param request
     * @return
     */
    @WebLog
    @ApiOperation(value = "获取粮油品种集合",httpMethod = "GET",notes = "获取粮油品种集合")
    @RequestMapping(value = "/getGokList", method = RequestMethod.GET)
    @ResponseBody
    public Object getGokList(HttpServletRequest request) {
        List<String> gokList = this.reportService.getGokList();
        return gokList;
    }
}
