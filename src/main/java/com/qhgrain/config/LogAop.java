package com.qhgrain.config;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author zhg
 * 请求代理，处理访问信息，异常信息
 */
@Aspect
@Component
public class LogAop {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(com.qhgrain.config.WebLog)")
    public void webLog() {

    }

    //环绕通知,处理请求和异常
    @Around("webLog()")
    public Object arround(ProceedingJoinPoint pjp) {
        logger.info("-------------分------------------割---------------------线---------------");
        //获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("CLASS_METHOD : " + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(pjp.getArgs()));
        //处理异常
        Object o;
        try {
            o = pjp.proceed();
        } catch (Throwable e) {
            ExceptionPojo ep = new ExceptionPojo();
            ep.setExceptionName(e.getClass().getName());
            ep.setExceptionMessage(e.getMessage());
            o = new Result<ExceptionPojo>(1, "no", ep);
            logger.info("RETURN : " + JSON.toJSONString(o, SerializerFeature.WriteMapNullValue));
            return o;
        }
        o = new Result<Object>(0, "ok", o);
        logger.info("RETURN : " + JSON.toJSONString(o, SerializerFeature.WriteMapNullValue));
        return o;
    }
}  
