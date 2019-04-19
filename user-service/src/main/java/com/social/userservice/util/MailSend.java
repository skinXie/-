package com.social.userservice.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;


@Component
public class MailSend implements InitializingBean {
    private JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    @Override
    public void afterPropertiesSet() throws Exception {
        mailSender.setUsername("904174171@qq.com");
        mailSender.setPassword("zxcv1234");
        mailSender.setHost("smtp.qq.com");
        mailSender.setProtocol("smtp");
        mailSender.setPort(465);
        mailSender.setDefaultEncoding("utf8");
    }

    public boolean sendMail(String to, String subject, String content) {
        try {
            String nick = MimeUtility.encodeText("社交问答");
            InternetAddress from = new InternetAddress(nick + "<904174171@qq.com>");
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);

            mimeMessageHelper.setText(content);
            mailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
