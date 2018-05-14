package com.qhgrain.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qhgrain.Util.Constant;
import com.qhgrain.mapper.UserMapper;
import com.qhgrain.pojo.User;
import com.qhgrain.service.IUserService;
import com.qhgrain.vo.UserPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("userServiceImpl")
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByLoginName(String loginName) {
        User u = this.userMapper.getUserByloginName(loginName);
        return u;
    }

    @Override
    public void userAdd(String userName, String loginName, String pwd) {
        User u = new User();
        u.setUserName(userName);
        u.setLoginName(loginName);
        u.setPwd(pwd);
        u.setCreateDate(new SimpleDateFormat(Constant.DATE_FORMAT).format(new Date()));
        this.userMapper.userAdd(u);
    }

    @Override
    public void userDel(Integer userId) {
        this.userMapper.userDel(userId);
    }

    @Override
    public User getUserById(Integer userId) {
        User u = this.userMapper.getUserById(userId);
        return u;
    }

    @Override
    public void updPwd(Integer userId, String oldPwd, String newPwd) {
        this.userMapper.updPwd(userId,newPwd);
    }

    @Override
    public PageInfo<UserPageVo> userPage(String userName, String loginName, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<UserPageVo> list = this.userMapper.userList(userName,loginName);
        PageInfo<UserPageVo> pi = new PageInfo<UserPageVo>(list);
        return pi;
    }
}
