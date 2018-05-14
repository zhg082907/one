package com.qhgrain.Util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alibaba.fastjson.JSONObject;
import com.qhgrain.pojo.Customer;
import com.qhgrain.pojo.Price;
import com.qhgrain.pojo.Report;
import com.qhgrain.pojo.Version;
import com.qhgrain.service.IReportService;
import com.qhgrain.service.impl.ReportServiceImpl;
import com.qhgrain.vo.CustomerImportVo;
import com.qhgrain.vo.PriceImportVo;
import com.qhgrain.vo.ReportImportVo;
import com.qhgrain.vo.ReportPageVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileUtil {

    /**
     * 价格导入
     * @param file
     * @param titleRows
     * @param headerRows
     * @param pojoClass
     * @return
     */
    public static JSONObject priceImport(MultipartFile file, Integer titleRows, Integer headerRows, Class<PriceImportVo> pojoClass){
        JSONObject jo = new JSONObject();
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        try {
            ExcelImportResult<PriceImportVo> result = ExcelImportUtil.importExcelMore(file.getInputStream(), pojoClass, params);
            HashSet<String> hs = new HashSet<String>();
            int flag = 0;
            Workbook book = result.getWorkbook();
            Sheet sheet = book.getSheetAt(0);
            String rg = "^\\d+(?:\\.\\d{1,5})?$";
            for (Row row :sheet) {
                if(row.getRowNum() == 0){
                    continue;
                }
                Cell cell = row.getCell(0);
                String value = cell.toString();
                if(value.matches(rg)){
                    if (!hs.add(value)){
                        flag++;
                        row.createCell(1).setCellValue("重复");
                    }
                }else{
                    flag++;
                    row.createCell(1).setCellValue("非法");
                }
            }
            makeResult(flag,jo,book,result);
        } catch (Exception e) {
            jo.put("code",Constant.TWO);
            jo.put("msg","文件有误");
        }
        return jo;
    }

    /**
     * 客商导入
     * @param file
     * @param titleRows
     * @param headerRows
     * @param pojoClass
     * @return
     */
    public static JSONObject customerImport(MultipartFile file, Integer titleRows, Integer headerRows, Class<CustomerImportVo> pojoClass) {
        JSONObject jo = new JSONObject();
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        try {
            ExcelImportResult<CustomerImportVo> result = ExcelImportUtil.importExcelMore(file.getInputStream(), pojoClass, params);
            HashSet<String> hs = new HashSet<String>();
            int flag = 0;
            Workbook book = result.getWorkbook();
            Sheet sheet = book.getSheetAt(0);
            for (Row row :sheet) {
                if(row.getRowNum() == 0){
                    continue;
                }
                Cell cell = row.getCell(0);
                String value = cell.toString();
                if(StringUtils.isNotEmpty(value)){
                    if (!hs.add(value)){
                        flag++;
                        row.createCell(1).setCellValue("重复");
                    }
                }else{
                    flag++;
                    row.createCell(1).setCellValue("非法");
                }
            }
            makeResult(flag,jo,book,result);
        } catch (Exception e) {
            jo.put("code",Constant.TWO);
            jo.put("msg","文件有误");
        }
        return jo;
    }

    public static void makeResult(int flag, JSONObject jo, Workbook book, ExcelImportResult<?> result) {
        try{
            if(flag == 0){
                jo.put("code",Constant.ZERO);
                jo.put("msg","操作成功");
                jo.put("data",result.getList());
            }else{
                boolean isXSSFWorkbook = !(book instanceof HSSFWorkbook);
                String path = "tempDir";
                File savefile = new File(path);
                if (!savefile.exists()) {
                    savefile.mkdirs();
                }
                String fileName = "";
                SimpleDateFormat format = new SimpleDateFormat("yyyMMddHHmmss");
                fileName = format.format(new Date()) + "_" + Math.round(Math.random() * 100000.0D) + (isXSSFWorkbook ? ".xlsx" : ".xls");
                FileOutputStream fos = new FileOutputStream(path + "/" + fileName);
                book.write(fos);
                IOUtils.closeQuietly(fos);
                jo.put("code",Constant.ONE);
                jo.put("msg","数据有误");
                jo.put("newFileName",fileName);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,String fileName, HttpServletResponse response){
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,pojoClass,list);
        if (workbook != null);
        downLoadExcel(fileName, response, workbook);
    }
    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
