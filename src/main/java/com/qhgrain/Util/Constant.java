package com.qhgrain.Util;

/**
 * 常量类
 */
public class Constant {
    //插入标记 0导入 1新增 2变更
    public static final int BATCHADD_FLAG = 0;
    public static final int ADD_FLAG = 1;
    public static final int CHANGE_FLAG = 2;
    //版本状态标记 0生效 1失效
    public static final int VERSION_ON = 0;
    public static final int VERSION_OFF = 1;
    //版本类型标记 0价格 1客商
    public static final int VERSION_TYPE_PRICE = 0;
    public static final int VERSION_TYPE_CUSTOMER = 1;
    //版本初始编码
    public static final String VERSION_FIRST_CODE = "V1.0";
    //时间格式
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    //初始编码
    public static final String FIRST_CODE = "A01";
    //价格编码表名
    public static final String PRICE_CODE_FILENAME = "价格编码表.xls";
    //价格模板名
    public static final String PRICE_TEMPLATE_NAME = "价格模板.xls";
    //客商编码表名
    public static final String CUSTOMER_CODE_FILENAME = "客商编码表.xls";
    //客商模板名
    public static final String CUSTOMER_TEMPLATE_NAME = "客商模板.xls";
    //销售报表表名
    public static final String REPORT_FILENAME = "销售报表.xls";
    //分页查询默认值
    public static final int PAGE_NUM = 1;
    public static final int PAGE_SIZE = 10;
    //状态码
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
}
