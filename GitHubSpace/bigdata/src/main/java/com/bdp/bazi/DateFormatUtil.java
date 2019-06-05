package com.bdp.bazi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by cgz on 2019-06-05 23:26
 * 描述：
 */
public class DateFormatUtil {

    public static Date getFirstDayOfMonth(int year, int month) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month-1);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天

        return c.getTime();

    }

    public static Date getAfterMonthDate(Date d){
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.MONTH, 1);
        return c.getTime();
    }

    public static void main(String[] args) {
        Date d = DateFormatUtil.getFirstDayOfMonth(2019,6);
        System.out.println(d.toLocaleString());
        System.out.println( DateFormatUtil.getAfterMonthDate(d).toLocaleString() );
    }
}
