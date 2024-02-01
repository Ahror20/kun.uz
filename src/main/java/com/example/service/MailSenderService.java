package com.example.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromAccount;

    public boolean sendEmail(String toAccount, String subject, String text) {
//        try {
//            SimpleMailMessage msg = new SimpleMailMessage();
//            msg.setTo(toAccount);
//            msg.setFrom(fromAccount);
//            msg.setSubject(subject);
//            msg.setText(text);
//            javaMailSender.send(msg);
//            return true;
//        }catch (RuntimeException e){
//            e.printStackTrace();
//            throw new RuntimeException();
//        }
        //}
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromAccount);

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(msg);
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
