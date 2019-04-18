package com.social.articleservice.dao;

import common.questionAnswer.Zan;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ZanDao {
    //查询赞的数量
    @Select("select count(*) from zan where zan_answerid =#{zanAnswerId}")
    int selectZanByAnswerId(int answerId);

    //增加一个赞
    @Insert("insert into zan (zan_userid,zan_answerid)values(#{zanUserId},#{zanAnswerId})")
    boolean insertZan(@Param("zanUserId") int zanUserId, @Param("zanAnswerId") int zanAnswerId);

    //查询点赞记录
    @Select("select * from zan where zan_answerid=#{answerId} and zan_userid=#{userId}")
    Zan selectZanByAnswerIdAndUserId(@Param("answerId") int answerId, @Param("userId") int userId);
}
