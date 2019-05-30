package com.bdp.service;

import com.bdp.entity.User;
import com.bdp.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cgz on 2019-05-30 23:23
 * 描述：
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;
    public User Sel(int id){
        return userMapper.Sel(id);
    }


}
