package com.social.userservice.async;

import common.util.MailSend;

import java.util.UUID;

public class ActiveEvent implements Event {
    MailSend mailSend;
    int userId;
    String email;

    @Override
    public void handle() {
    }

    public ActiveEvent(MailSend mailSend, int userId, String email) {
        this.mailSend = mailSend;
        this.userId = userId;
        this.email = email;
    }
}
