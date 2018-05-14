package com.qhgrain.controller;

import com.alibaba.fastjson.JSONObject;
import com.qhgrain.Util.Constant;
import com.qhgrain.Util.UserUtil;
import com.qhgrain.config.WebLog;
import com.qhgrain.pojo.User;
import com.qhgrain.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(description = "登录控制器")
@RestController
public class LoginController {
    @Autowired
    private IUserService userService;

    @WebLog
    @ApiOperation(value = "登录",httpMethod = "GET",notes = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public Object login(@RequestParam("loginName") String loginName,
                        @RequestParam("pwd") String pwd,
                        HttpServletRequest request){
        JSONObject jo = new JSONObject();
        //过滤参数
        if(StringUtils.isNotEmpty(loginName) && StringUtils.isNotEmpty(pwd)){
            //判断用户是否存在
            User u = this.userService.getUserByLoginName(loginName);
            if(u != null){
                //存在
                //判断密码是否正确
                if(pwd.equals(u.getPwd())){
                    //正确
                    //生成token
                    String token = UserUtil.getToken(u.getUserId());
                    //放入map集合
                    UserUtil.map.put(u.getUserId(),token);
                    jo.put("code", Constant.ZERO);
                    jo.put("token",token);
                    jo.put("userId",u.getUserId());
                    jo.put("userName",u.getUserName());
                }else{
                    //不正确
                    jo.put("code",Constant.ONE);
                    jo.put("msg","账号密码不匹配");
                }
            }else{
                //不存在
                jo.put("code",Constant.TWO);
                jo.put("msg","账号不存在");
            }
        }else{
            jo.put("code",Constant.THREE);
            jo.put("msg","参数不可为空");
        }
        return jo;
    }

}
