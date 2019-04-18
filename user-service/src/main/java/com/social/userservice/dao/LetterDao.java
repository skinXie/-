package com.social.userservice.dao;

import common.user.Letter;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

@Mapper
public interface LetterDao {
    //添加站内信
    @Insert("insert into letter (sender_id,receiver_id,letter_content,letter_status)" +
            "values(#{senderId},#{receiverId},#{letterContent},#{letterStatus})")
    boolean insertLetter(Letter letter);

    //删除站内信(0为未读，1为已读)
    @Update("update letter set letter_status=1 where letter_id=#{letterId}")
    boolean deleteLetterByLetterId(int letterId);

    //查询未读的站内信
    @Select("select * from letter where receiver_id=#{receiverId} and letter_status = 0")
    ArrayList<Letter> selectUnreadLetterByUserId(int userId);

    //查询已读的站内信
    @Select("select * from letter where receiver_id=#{receiverId} and letter_status = 1")
    ArrayList<Letter> selectReadedLetterByUserId(int userId);

}
