package common.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.SimpleMailMessage;
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
        mailSender.setPassword("ddzooqvmuqbibfig");
        mailSender.setHost("smtp.qq.com");
        mailSender.setProtocol("smtp");
        mailSender.setDefaultEncoding("utf8");
    }

    public boolean sendMail(String to, String subject, String content, Boolean isHtml) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom("904174171@qq.com", "社交问答");
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, isHtml);
            mailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
