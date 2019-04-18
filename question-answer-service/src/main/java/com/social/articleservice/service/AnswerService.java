package com.social.articleservice.service;

import com.social.articleservice.async.AnswerEvent;
import com.social.articleservice.async.Consumer;
import com.social.articleservice.dao.AnswerDao;
import common.feign.QuestionAnswerFeign;
import common.feign.UserFeign;
import common.questionAnswer.Answer;
import common.questionAnswer.Question;
import common.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AnswerService {
    @Autowired
    AnswerDao answerDao;
    @Autowired
    Consumer consumer;
    @Autowired
    QuestionAnswerFeign questionAnswerFeign;
    @Autowired
    UserFeign userFeign;

    //添加回答  private int userId;
    //    private int entityId;
    //    private int entityType;(0-问题  1-答案)
    //    private String answerContent;
    public int addAnswer(int entityType, int userId, int entityId, String content) {
        Answer answer = new Answer();
        answer.setEntityId(entityId);
        answer.setEntityType(entityType);
        answer.setUserId(userId);
        answer.setAnswerContent(content);
        answerDao.insertAnswer(answer);
        //entityType == 0回答的是问题
        if (entityType == 0) {
            //获取问题和作者
            Question q = questionAnswerFeign.getQuestionById(answer.getEntityId());
            User user = userFeign.getUser(q.getUserId());
            //发布事件，
            AnswerEvent e = new AnswerEvent();
            e.setQ(q);
            e.setUser(user);
            consumer.getQueue().add(e);
        }
        return answer.getAnswerId();
    }


    //分页获取回答(10)
    public List<Answer> getAnswer(int entityType, int entityId, int page, int count) {
        List<Answer> answers = answerDao.selectAnswerByPage(entityType, entityId, count * (page - 1), count);
        return answers;
    }

    //根据实体id获取回答
    public List<Answer> getAnswer(int entityType, int entityId) {
        List<Answer> answers = answerDao.selectAllAnswer(entityType, entityId);
        return answers;
    }
}