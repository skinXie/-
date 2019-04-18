package com.social.userservice.async;

import common.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class FollowEvent implements Event {
    @Autowired
    RestTemplate restTemplate;
    private int systemId = 1;
    private User receiver;
    private User user;

    public void handle() {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("sender", systemId);
        params.add("receiver", receiver.getUserName());
        params.add("content", "用户" + user.getUserName() + "关注了你");
        this.restTemplate.postForObject("http://127.0.0.1:8081/letter/send", params, Integer.class);
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
