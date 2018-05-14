package com.qhgrain.Util;

/**
 * 版本号和编号生成工具类
 */
public class CodeUtil {

    /**
     * 根据当前版本号，生成下一个版本号
     * @param code
     * @return
     */
    public static String getVersionCode(String code){
        String str = code.replace("V", "");
        int a = Integer.parseInt(str.substring(0,str.length() - 2));
        int b = Integer.parseInt(str.substring(str.length() - 1));
        if(b < 9){
            b++;
        }else {
            b = 0;
            a++;
        }
        return "V" + a + "." + b;
    }

    /**
     * 根据当前编码，生成下一个编码
     * @param code
     * @return
     */
    public static String getCode(String code){
        char[] chars = code.toCharArray();
        char a = chars[0];
        int b = Integer.parseInt(code.substring(1,code.length() - 1));
        int c = Integer.parseInt(code.substring(code.length() - 1,code.length()));
        if(a < 90){
            a++;
        }else {
            a = 65;
            if(c < 9){
                c++;
            }else{
                c = 1;
                b++;
            }
        }
        return "" + a + b + c;
    }
}
