package com.project.reviewservice.service.user;

import com.project.reviewservice.domain.user.User;
import com.project.reviewservice.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User login(String userId , String password) { //사용자 로그인을 한다.
        User user = userRepository.loginCheck(userId , password);

        return user;
    }

    public boolean isUserAlreadyExists(String userId) {

        return userRepository.findByUserId(userId).isPresent(); //존재하면 true , 아니면 false
    }

    public void join(User user) {

        userRepository.save(user);
    }
}
