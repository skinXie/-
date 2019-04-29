package com.social.articleservice.async;

import common.feign.UserFeign;
import common.questionAnswer.Question;
import common.user.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
public class QuestionEvent extends Event {
    @Autowired
    UserFeign userFeign;
    private int systemId = 1;
    private User user;
    private List<User> followUsers;
    private Question q;

    //向关注我的用户进行推送消息
    public void handle() {
        for (User follow : followUsers) {
            String content = "您关注的用户" + user.getUserName() + "有新的问题发布了:<a href=\"http://127.0.0.1:8080/question/" + q.getQuestionId() + "\">" + q.getQuestionTitle() + "</a>";
            userFeign.sendLetter(systemId, follow.getUserName(), content);
        }
    }
}
