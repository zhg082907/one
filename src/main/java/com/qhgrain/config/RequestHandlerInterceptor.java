package com.qhgrain.config;

import com.alibaba.fastjson.JSONObject;
import com.qhgrain.Util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class RequestHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        //token是否为空
        if(StringUtils.isNotEmpty(token)){
            //否
            //token是否符合规则
            if(this.checkToken(token)){
                //是
                //token是否存在
                String str = token.substring(32);
                int userId = Integer.parseInt(str);
                if(UserUtil.map.containsKey(userId) && UserUtil.map.get(userId).equals(token)){
                    //是
                    //将userId放入request中
                    request.setAttribute("userId",userId);
                    //放行
                    return true;
                }
            }
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null ;
        try{
            JSONObject res = new JSONObject();
            res.put("code",403);
            res.put("msg","无权访问");
            out = response.getWriter();
            out.append(res.toString());
        } catch (Exception e){
            e.printStackTrace();
            response.sendError(500);
        }
        return false;
    }

    private boolean checkToken(String token) {
        String str = token.substring(32);
        try{
            Integer.parseInt(str);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
