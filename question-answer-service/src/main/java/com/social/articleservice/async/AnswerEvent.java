package com.social.articleservice.async;

import common.feign.UserFeign;
import common.questionAnswer.Question;
import common.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class AnswerEvent extends Event {
    @Autowired
    UserFeign userFeign;
    private int systemId = 1;
    private User user;
    private Question q;

    public void handle() {
        String content = "您的问题有一条新的回答:<a href=\"http://127.0.0.1:8080/question/" + q.getQuestionId() + "\">" + q.getQuestionTitle() + "</a>";
        userFeign.sendLetter(systemId, user.getUserName(), content);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQ() {
        return q;
    }

    public void setQ(Question q) {
        this.q = q;
    }
}
