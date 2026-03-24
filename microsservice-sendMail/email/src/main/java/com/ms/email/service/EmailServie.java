package com.ms.email.service;

import com.ms.email.domin.EmailModel;
import com.ms.email.enums.StatusEmail;
import com.ms.email.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EmailServie {


    final EmailRepository repository;



    final JavaMailSender emailSender;



    public EmailServie(EmailRepository repository, JavaMailSender emailSender) {
        this.repository = repository;
        this.emailSender = emailSender;
    }

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    @Transactional
    public EmailModel sendEmail(EmailModel emailModel){
        try {
            emailModel.setSendDateEmail(LocalDateTime.now());
            emailModel.setEmailFrom(emailFrom);


            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailModel.getEmailTO());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.ENVIADO);

        }catch (MailSendException e){
            emailModel.setStatusEmail(StatusEmail.ERROR);
        }finally {
            return repository.save(emailModel);
        }

    }
}
