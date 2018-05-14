package com.qhgrain.controller;

import com.qhgrain.config.WebLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Api(description = "不符合条件的EXCEL控制器")
@RestController
@RequestMapping("/errorExcel")
public class ErrorExcelController {

    @WebLog
    @ApiOperation(value = "下载有错误信息的EXCEL",httpMethod = "GET",notes = "下载有错误信息的EXCEL")
    @RequestMapping(value = "/downloadErrorExcel", method = RequestMethod.GET)
    public void downloadErrorExcel(@RequestParam("newFileName") String newFileName, HttpServletRequest req, HttpServletResponse res){
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + newFileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File("tempDir/" + newFileName)));
            int len = 0;
            while((len = bis.read(buff))!=-1){
                os.write(buff, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
