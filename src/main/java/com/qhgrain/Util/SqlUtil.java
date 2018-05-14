package com.qhgrain.Util;

import org.apache.commons.lang3.StringUtils;

/**
 * 处理sql的工具类
 */
public class SqlUtil {

    public static String checkParm(String param){
        if (StringUtils.isNotEmpty(param)){
            param = param.replace("%", "\\%").replace("_", "\\_");
        }
        return param;
    }
}
