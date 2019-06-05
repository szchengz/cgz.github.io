package com.bdp.controller;

import com.bdp.entity.User;
import com.bdp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cgz on 2019-05-30 15:38
 * 描述：
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("getUser/{id}")
    public String GetUser(@PathVariable int id){
        if(userService.Sel(id) == null)
            return "NULL";
        else
            return userService.Sel(id).toString();
    }

}
