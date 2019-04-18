package com.social.articleservice.dao;

import common.questionAnswer.Answer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AnswerDao {
    //分页查询回答(entity_type=0为问题  entity_type=1为答案)
    @Select("select * from answer where entity_id=#{entityId} and entity_type=#{entityType} limit #{start},#{count}")
    List<Answer> selectAnswerByPage(@Param("entityType") int entityType, @Param("entityId") int entityId, @Param("start") int start, @Param("count") int count);

    //添加回答
    @Insert("insert into answer(user_id,entity_id,entity_type,answer_content)" +
            "values(#{userId},#{entityId},#{entityType},#{answerContent})")
    @Options(keyProperty = "answerId", useGeneratedKeys = true)
    boolean insertAnswer(Answer answer);

    //根据实体Id查询所有回答
    @Select("select * from answer where entity_id=#{entityId} and entity_type=#{entityType}")
    List<Answer> selectAllAnswer(@Param("entityType") int entityType, @Param("entityId") int entityId);
}
