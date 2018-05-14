package com.qhgrain.config;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Result<T> {
    private int code = 0;//状态码
    private String msg = "";//状态信息
    private T data = null;//业务信息
    private String time = "";//响应时间

    public Result() {
        this(0, "", null);
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
