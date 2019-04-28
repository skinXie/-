package com.social.userservice.dao;

import common.user.Follow;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface FollowDao {
    //根据用户id查询该用户关注的实体id集合
    @Select("select * from follow where user_id = #{userId} and type=#{type}")
    List<Follow> selectFollowedId(@Param("userId") int userId, @Param("type") String type);

    //增加一条关注
    @Insert("insert into follow (user_id,entity_id,type) values (#{userId},#{entityId},#{type})")
    boolean insertGuanzhu(@Param("user_id") int userId, @Param("entity_id") int entityId, String type);

    //取消一条关注
    @Delete("delete  from follow where user_id=#{userId} and entity_id=#{entityId} and type=#{type}")
    boolean deleteGuanzhu(@Param("user_id") int userId, @Param("entity_id") int entityId, String type);

    //判断是否存在关注记录
    @Select("select * from follow where user_id=#{userId} and entity_id=#{entityId} and type=#{type}")
    Follow isFollow(@Param("userId") int userId, @Param("entityId") int entityId, @Param("type") String type);

    //根据实体id查询关注记录
    @Select("select * from follow where entity_id=#{entityId} and type=#{type}")
    ArrayList<Follow> selectFollowsByEntityId(@Param("entity_id") int entityId, @Param("type") String type);
}
