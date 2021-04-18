package com.latihan.posify.service.impl;

import com.latihan.posify.dto.request.EmailRequest;
import com.latihan.posify.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private  JavaMailSender mailSender;
    @Override
    @Async
    public void send(EmailRequest emailRequest) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("admin@posify.com");
            messageHelper.setTo(emailRequest.getRecipient());
            messageHelper.setSubject(emailRequest.getSubject());
            messageHelper.setText(emailRequest.getBody());
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            throw new MailException("Error occurred when sending mail to " + emailRequest.getRecipient(), e) {
            };
        }
    }
}
