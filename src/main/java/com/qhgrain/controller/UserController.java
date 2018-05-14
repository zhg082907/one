package com.qhgrain.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.qhgrain.Util.Constant;
import com.qhgrain.Util.SqlUtil;
import com.qhgrain.config.Result;
import com.qhgrain.config.WebLog;
import com.qhgrain.pojo.User;
import com.qhgrain.service.IUserService;
import com.qhgrain.vo.UserPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(description = "用户管理控制器")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @WebLog
    @ApiOperation(value = "用户新增",httpMethod = "GET",notes = "用户新增")
    @RequestMapping(value = "/userAdd", method = RequestMethod.GET)
    @ResponseBody
    public Object userAdd(@RequestParam("userName") String userName,
                          @RequestParam("loginName") String loginName,
                          @RequestParam("pwd") String pwd,
                          HttpServletRequest request){
        JSONObject jo = new JSONObject();
        //过滤参数
        if(StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(loginName) && StringUtils.isNotEmpty(pwd)){
            //校验登录名是否已存在
            User u = this.userService.getUserByLoginName(loginName);
            if(u != null){
                //已存在
                jo.put("code",Constant.ONE);
                jo.put("msg","登录名已存在");
            }else{
                //不存在，在业务层，封装用户实体，执行新增
                this.userService.userAdd(userName,loginName,pwd);
                jo.put("code",Constant.ZERO);
                jo.put("msg","操作成功");
            }
        }else{
            jo.put("code",Constant.TWO);
            jo.put("msg","参数不可为空");
        }
        return jo;
    }

    @WebLog
    @ApiOperation(value = "用户删除",httpMethod = "GET",notes = "用户删除")
    @RequestMapping(value = "/userDel", method = RequestMethod.GET)
    @ResponseBody
    public Object userDel(@RequestParam("userId") Integer userId,
                          HttpServletRequest request){
        //过滤参数
        if(userId != null){
            //在业务层，执行删除操作
            this.userService.userDel(userId);
        }
        //操作成功，直接返回null，即请求成功就是操作成功
        return null;
    }

    @WebLog
    @ApiOperation(value = "修改密码",httpMethod = "GET",notes = "修改密码")
    @RequestMapping(value = "/updPwd", method = RequestMethod.GET)
    @ResponseBody
    public Object updPwd(@RequestParam("userId") Integer userId,
                         @RequestParam("oldPwd") String oldPwd,
                         @RequestParam("newPwd") String newPwd,
                         HttpServletRequest request){
        JSONObject jo = new JSONObject();
        //过滤参数
        if(userId != null && StringUtils.isNotEmpty(oldPwd) && StringUtils.isNotEmpty(newPwd)){
            //校验用户旧密码是否正确
            User u = this.userService.getUserById(userId);
            if(u != null){
                if(oldPwd.equals(u.getPwd())){
                    //密码正确，在业务层，执行修改密码操作
                    this.userService.updPwd(userId,oldPwd,newPwd);
                    jo.put("code",Constant.ZERO);
                    jo.put("msg","操作成功");
                }else {
                    jo.put("code",Constant.ONE);
                    jo.put("msg","原密码不正确");
                }
            }else{
                jo.put("code",Constant.TWO);
                jo.put("msg","查询不到用户");
            }
        }else{
            jo.put("code",Constant.THREE);
            jo.put("msg","参数不可为空");
        }
        return jo;
    }

    @WebLog
    @ApiOperation(value = "用户分页列表",httpMethod = "GET",notes = "用户分页列表")
    @RequestMapping(value = "/userPage", method = RequestMethod.GET)
    @ResponseBody
    public Object userPage(@RequestParam("userName") String userName,
                           @RequestParam("loginName") String loginName,
                           @RequestParam("pageNum") Integer pageNum,
                           @RequestParam("pageSize") Integer pageSize,
                           HttpServletRequest request){
        //过滤参数
        if(StringUtils.isEmpty(userName)){
            userName = null;
        }else {
            userName = SqlUtil.checkParm(userName);
        }
        if(StringUtils.isEmpty(loginName)){
            loginName = null;
        }else {
            loginName = SqlUtil.checkParm(loginName);
        }
        if(pageNum == null){
            pageNum = Constant.PAGE_NUM;
        }
        if(pageSize == null){
            pageSize = Constant.PAGE_SIZE;
        }
        //在业务层，查询用户分页列表
        PageInfo<UserPageVo> pi = this.userService.userPage(userName,loginName,pageNum,pageSize);
        //反馈前端
        return pi;
    }
}
