package com.social.articleservice.async;

import common.questionAnswer.Question;
import common.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class QuestionEvent extends Event {
    @Autowired
    RestTemplate restTemplate;
    private int systemId = 1;
    private User user;
    private List<User> followUsers;
    private Question q;

    //向关注我的用户进行推送消息
    public void handle() {
        for (User follow : followUsers) {
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            params.add("sender", systemId);
            params.add("receiver", follow.getUserName());
            params.add("content", "您关注的用户" + user.getUserName() + "有新的问题发布了:<a href=\"http://127.0.0.1:8080/question/" + q.getQuestionId() + "\">" + q.getQuestionTitle() + "</a>");
            this.restTemplate.postForObject("http://127.0.0.1:8081/letter/send", params, Integer.class);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getFollowUsers() {
        return followUsers;
    }

    public void setFollowUsers(List<User> followUsers) {
        this.followUsers = followUsers;
    }

    public Question getQ() {
        return q;
    }

    public void setQ(Question q) {
        this.q = q;
    }
}
