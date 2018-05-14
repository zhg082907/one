package com.qhgrain.Util;

public class Test {
    public static void main(String[] args) {
        String rg = "^\\d+(?:\\.\\d{1,5})?$";
        String str = "-1.11111";
        System.out.println(str.matches(rg));

        String sql = "测试%_";
        sql = sql.replace("%", "\\%").replace("_", "\\_");
        System.out.println(sql);
    }
}
