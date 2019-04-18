package com.social.articleservice.async;

import common.questionAnswer.Question;
import common.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class AnswerEvent extends Event {
    @Autowired
    RestTemplate restTemplate;
    private int systemId = 1;
    private User user;
    private Question q;

    public void handle() {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("sender", systemId);
        params.add("receiver", user.getUserName());
        params.add("content", "您的问题有一条新的回答:<a href=\"http://127.0.0.1:8080/question/" + q.getQuestionId() + "\">" + q.getQuestionTitle() + "</a>");
        this.restTemplate.postForObject("http://127.0.0.1:8081/letter/send", params, Integer.class);
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
