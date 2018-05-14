package com.qhgrain.mapper;

import com.qhgrain.pojo.User;
import com.qhgrain.vo.UserPageVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    @Insert("insert into user (user_name,login_name,pwd,create_date) values (#{user.userName},#{user.loginName},#{user.pwd},#{user.createDate})")
    public void userAdd(@Param("user") User u);

    @Select("select user_id userId,user_name userName,login_name loginName,pwd,DATE_FORMAT(create_date,'%Y-%m-%d %H:%i:%S') createDate " +
            "from user where login_name = #{loginName}")
    public User getUserByloginName(@Param("loginName") String loginName);

    @Delete("delete from user where user_id = #{userId}")
    public void userDel(@Param("userId") Integer userId);

    @Update("update user set pwd = #{newPwd} where user_id = #{userId}")
    public void updPwd(@Param("userId") Integer userId, @Param("newPwd") String newPwd);

    @Select("select user_id userId,user_name userName,login_name loginName,pwd,DATE_FORMAT(create_date,'%Y-%m-%d %H:%i:%S') createDate " +
            "from user where user_id = #{userId}")
    public User getUserById(@Param("userId") Integer userId);

    @Select("<script>" +
            "select user_id userId,user_name userName,login_name loginName,DATE_FORMAT(create_date,'%Y-%m-%d %H:%i:%S') createDate " +
            "from user " +
            "where 1 = 1 " +
            "<if test = 'userName != null'>and user_name like CONCAT('%',#{userName},'%') </if>" +
            "<if test = 'loginName != null'>and login_name like CONCAT('%',#{loginName},'%') </if>" +
            "order by createDate desc " +
            "</script>")
    public List<UserPageVo> userList(@Param("userName") String userName, @Param("loginName") String loginName);
}
