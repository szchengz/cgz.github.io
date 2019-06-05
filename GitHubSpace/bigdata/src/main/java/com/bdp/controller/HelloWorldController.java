package com.bdp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * Created by cgz on 2019-05-30 15:43
 * 描述：
 */
@RestController
public class HelloWorldController {

    @Value("${minTimes}")
    private BigDecimal minTimes;

    @Value("${minTimesDesc}")
    private String minTimesDesc;

    @RequestMapping("/hello")
    public String index() {
        return "Hello World " + minTimes + "   " + minTimesDesc;
    }

}
