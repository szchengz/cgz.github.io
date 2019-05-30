package com.bdp.controller;

import com.bdp.entity.User;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cgz on 2019-05-30 15:38
 * 描述：
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/user")
public class UserController {

    @RequestMapping("getuser")
    public User getUser() {
        User user = new User();
        user.setName("test");
        return user;
    }

}
