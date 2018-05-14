package com.qhgrain.service;

import com.github.pagehelper.PageInfo;
import com.qhgrain.pojo.User;
import com.qhgrain.vo.UserPageVo;

public interface IUserService {
    public void userAdd(String userName, String loginName, String pwd);

    public void userDel(Integer userId);

    public void updPwd(Integer userId, String oldPwd, String newPwd);

    public User getUserById(Integer userId);

    public User getUserByLoginName(String loginName);

    public PageInfo<UserPageVo> userPage(String userName, String loginName, Integer pageNum, Integer pageSize);
}
