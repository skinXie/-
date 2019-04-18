package com.social.userservice.dao;

import common.user.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {
    //增加一个用户
    @Insert("insert into user(account,password,user_name,salt,head_url,active_code,mailbox)" +
            " values(#{account},#{password},#{userName},#{salt},#{headUrl},#{activeCode},#{mailbox})")
    @Options(keyProperty = "userId", useGeneratedKeys = true)
    boolean insertUser(User user);

    //根据id查询用户
    @Select("select * from user where user_id =#{userId}")
    User selectUserById(int userId);

    //根据用户昵称查询用户
    @Select("select * from user where user_name =#{userName}")
    User selectUserByUserName(String userName);

    //根据账号查询用户
    @Select("select * from user where account =#{account}")
    User selectUserByAccount(String account);

    //更新用户信息
    @Update("update user set password=#{password},user_name=#{userName},head_url=#{headUrl},mailbox=#{mailbox} where account=#{account}")
    int updateUser(User user);


}
