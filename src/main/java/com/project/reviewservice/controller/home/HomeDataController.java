package com.project.reviewservice.controller.home;

import com.project.reviewservice.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeDataController {

    @Autowired
    UserService userService;

    @GetMapping("/checkUserId")
    public boolean isUserAlreadyExists(@RequestParam("userId") String userId) {

        System.out.println("[아이디 중복검사 체크] 입력받은 아이디 : " + userId);

        return userService.isUserAlreadyExists(userId);
    }
}
