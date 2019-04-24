package com.social.articleservice.dao;

import com.social.articleservice.typehandle.ListTypeHandle;
import common.questionAnswer.Question;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper

public interface QuestionDao {

    //查询用户的提问
    @Select({"select * from question where user_id=#{userId}"})
    @Results(id = "list-varchar", value = {
            @Result(jdbcType = JdbcType.VARCHAR, typeHandler = ListTypeHandle.class, column = "tag", property = "tag", javaType = List.class),
    })
    List<Question> selectQuestionByUserId(int userId);
    //查询所有问题

    @Select("select * from question")
    @ResultMap({"list-varchar"})
    List<Question> selectAllQuestion();

    //分页查询问题
    @Select("select * from question limit #{start},#{count}")
    @ResultMap({"list-varchar"})
    List<Question> selectQuestionByPage(@Param("start") int start, @Param("count") int count);

    //增加一个问题
    @Insert("insert into question(user_id,question_title,question_content,question_date,tag,visit_time)" +
            "values(#{userId},#{questionTitle},#{questionContent},#{questionDate},#{tag},#{visitTime})")
    @ResultMap({"list-varchar"})
    @Options(useGeneratedKeys = true, keyProperty = "questionId")
    boolean insertQuestion(Question question);

    //根据问题id查询问题
    @Select("select * from question where question_id =#{questionId}")
    @ResultMap({"list-varchar"})
    Question selectQuestionByQuestionId(int questionId);

    //增加浏览数
    @Update("update question set visit_time=#{visitTime} where question_id=#{questionId}")
    int updateVisitTime(@Param("visitTime") int visitTime, @Param("questionId") int questionId);
}
