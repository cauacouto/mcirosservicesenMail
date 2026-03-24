package com.ms.email.consumers;

import com.ms.email.EmailDto;
import com.ms.email.domin.EmailModel;
import com.ms.email.service.EmailServie;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final EmailServie servie;

    public EmailConsumer(EmailServie servie) {
        this.servie = servie;
    }

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailDto dto){
      var emailModel = new EmailModel();
        BeanUtils.copyProperties(dto,emailModel);
        servie.sendEmail(emailModel);
    }
}
