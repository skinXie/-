package com.social.articleservice.dao;

import common.questionAnswer.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionDao {
    //查询用户的提问
    @Select("select * from question where user_id=#{userId}")
    List<Question> selectQuestionByUserId(int userId);

    //查询所有问题
    @Select("select * from question")
    List<Question> selectAllQuestion();

    //分页查询问题
    @Select("select * from question limit #{start},#{count}")
    List<Question> selectQuestionByPage(@Param("start") int start, @Param("count") int count);

    //增加一个问题
    @Insert("insert into question(user_id,question_title,question_content,question_date,tag,visit_time)" +
            "values(#{userId},#{questionTitle},#{questionContent},#{questionDate},#{tag},#{visitTime})")
    @Options(useGeneratedKeys = true, keyProperty = "questionId")
    boolean insertQuestion(Question question);

    //根据问题id查询问题
    @Select("select * from question where question_id =#{questionId}")
    Question selectQuestionByQuestionId(int questionId);

    //增加浏览数
    @Update("update question set visit_time=#{visitTime} where question_id=#{questionId}")
    int updateVisitTime(@Param("visitTime") int visitTime, @Param("questionId") int questionId);
}
