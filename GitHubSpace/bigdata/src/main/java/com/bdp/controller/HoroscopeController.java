package com.bdp.controller;

import com.alibaba.fastjson.JSON;
import com.bdp.horoscope.Bazi;
import com.bdp.util.Date4jUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by cgz on 2019-06-13 16:36
 * 描述：
 */

@RestController
@RequestMapping("/horoscope")
public class HoroscopeController {

//    , method = RequestMethod.POST
    @RequestMapping(value = "/info")
    public String info(@RequestParam(value = "year", required = true) int year,
                              @RequestParam(value = "month", required = true) int month,
                              @RequestParam(value = "day", required = true) int day,
                              @RequestParam(value = "hour", required = true) int hour,
                              @RequestParam(value = "sex", required = true) int sex
    ) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);

        Bazi bazi = new Bazi(calendar.getTime(), sex);
        Map<String, Object> map = bazi.getInfo();

        return JSON.toJSONString(map);
    }

}
