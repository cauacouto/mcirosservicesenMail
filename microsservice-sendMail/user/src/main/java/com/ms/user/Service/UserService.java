package com.ms.user.Service;

import com.ms.user.Repository.UserRepository;
import com.ms.user.domin.UserModel;
import com.ms.user.producers.UserProducer;
import org.springframework.stereotype.Service;

@Service
public class UserService {




    private final UserRepository repository;

    private final UserProducer userProducer;

    public UserService( UserRepository repository, UserProducer userProducer) {
        this.repository = repository;
        this.userProducer = userProducer;
    }

    public UserModel saveUsers(UserModel userModel){
        userModel = repository.save(userModel);
        userProducer.publishMessagem(userModel);
        return userModel;


    }
}
