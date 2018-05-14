package com.qhgrain.vo;

/**
 * 用户分页列表数据VO类
 */
public class UserPageVo {
    private Integer userId;//用户主键
    private String userName;//用户姓名
    private String loginName;//登录名
    private String createDate;//创建时间

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
