package com.ms.user.producers;

import com.ms.user.domin.UserModel;
import com.ms.user.dtos.EmailDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

   final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value ="${broker.queue.email.name}" )
    private String routingKey;


    public void publishMessagem(UserModel userModel){
        var emailDto = new EmailDto();
        emailDto.setId(userModel.getId());
        emailDto.setEmailTo(userModel.getEmail());
        emailDto.setSubject("cadastro realizado com sucesso !");
        emailDto.setText(userModel.getName() + "seja bem vindo (A) !\n agradecemos o seu cadstro");

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }


}
