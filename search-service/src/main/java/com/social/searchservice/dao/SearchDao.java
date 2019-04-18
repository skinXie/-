package com.social.searchservice.dao;

import common.search.QuestionSearch;
import common.search.UserSearch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SearchDao {
    @Select(" SELECT COUNT(question_id) answer_time,question_id,question_title,visit_time,question_date,tag,a.user_id,user_name,head_url " +
            " FROM question a " +
            " LEFT JOIN USER b ON a.user_id = b.user_id " +
            " LEFT JOIN answer c on a.question_id=c.entity_id " +
            " GROUP BY question_id ")
    List<QuestionSearch> selectQuestionSearch();

    @Select("SELECT user_id,user_name,head_url FROM USER")
    List<UserSearch> selectUserSearch();
}
