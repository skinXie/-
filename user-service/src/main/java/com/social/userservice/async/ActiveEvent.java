package com.social.userservice.async;

import com.social.userservice.util.MailSend;

import java.util.UUID;

public class ActiveEvent implements Event {
    MailSend mailSend;
    int userId;
    String email;

    @Override
    public void handle() {
        String code = UUID.randomUUID().toString().substring(0, 16);
        String content = "<html><head></head><body><h1>感谢注册，请点击以下链接激活您的账号</h1>"
                + "<h3><a href='http://localhost:8081/user/active?code="
                + code + "&token=" + userId + "</href></h3></body></html>";
        mailSend.sendMail(email, "激活邮件", content);
    }

    public ActiveEvent(MailSend mailSend, int userId, String email) {
        this.mailSend = mailSend;
        this.userId = userId;
        this.email = email;
    }
}
