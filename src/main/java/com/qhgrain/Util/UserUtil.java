package com.qhgrain.Util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 获取当前登录用户信息工具类
 */
public class UserUtil {

    public static String getUserId(HttpServletRequest request){
        String userId = "";
        Object obj = request.getAttribute("userId");
        if(obj != null){
            userId = obj.toString();
        }
        return userId;
    }

    public static String getToken(Integer userId){
        String uuid = UUID.randomUUID().toString().replace("-","");
        String token = uuid + userId;
        return token;
    }

    //存储登录人令牌的map集合
    public static Map<Integer,String> map = new HashMap<Integer,String>();
}
