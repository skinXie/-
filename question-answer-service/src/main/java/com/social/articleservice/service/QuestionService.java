package com.social.articleservice.service;

import com.social.articleservice.async.Consumer;
import com.social.articleservice.async.QuestionEvent;
import com.social.articleservice.dao.QuestionDao;
import common.feign.UserFeign;
import common.questionAnswer.Question;
import common.tool.Tool;
import common.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    Consumer consumer;
    @Autowired
    UserFeign userFeign;

    //发布问题,推送消息
    public int askQuestion(String title, String content, List<String> tags, int userId) {
        Question question = new Question();
        question.setQuestionContent(content);
        question.setQuestionDate(new Date());
        question.setQuestionTitle(title);
        question.setUserId(userId);
        question.setVisitTime(0);
        question.setTag(tags);
        questionDao.insertQuestion(question);
        QuestionEvent e = new QuestionEvent();
        //请求关注的用户
        List<User> users = userFeign.getFollowUser(userId);
        //请求作者信息
        User user = userFeign.getUser(userId);
        //设置事件
        e.setFollowUsers(users);
        e.setQ(question);
        e.setUser(user);
        //添加事件
        consumer.getQueue().add(e);
        return question.getQuestionId();
    }

    //查询该用户的提问
    public List<Question> getQuestionsByUserId(int userId) {
        List<Question> questions = questionDao.selectQuestionByUserId(userId);
        return questions;
    }

    //分页查询问题
    public List<Question> getQuestiobByPage(int page, int count) {
        List<Question> questions = null;
        if (page == 0 || count == 0)
            questions = questionDao.selectAllQuestion();
        else
            questions = questionDao.selectQuestionByPage(count * (page - 1), count);
        return questions;
    }

    //根据问题的id获取问题
    public Question getQuestionByQuestionId(int id) {
        return questionDao.selectQuestionByQuestionId(id);
    }

    //增加浏览数
    public int addVisitTime(int time, int questionId) {
        return questionDao.updateVisitTime(time, questionId);
    }
}
