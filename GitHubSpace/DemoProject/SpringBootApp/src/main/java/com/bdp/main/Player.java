package com.bdp.main;

import org.springframework.stereotype.Service;

/**
 * Created by cgz on 2019-04-20 12:01
 * 描述：
 */

@Service
public class Player {
    public  String getWelcomeMsg(String name){
        return "欢迎您:"+name+"，欢迎参加石头、剪刀、布的游戏!!!";
    }

}
