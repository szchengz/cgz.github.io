package com.bdp.horoscope;

import com.bdp.bazi.BaZi;
import com.bdp.util.Date4jUtil;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Created by cgz on 2019-05-30 22:36
 * 描述：
 */
public class Paipan {

    private static enum SEX {
        MAN,
        WOMAN;
    }

    private static final String[] _60GANZHI = {
            "甲子", "乙丑", "丙寅", "丁卯", "戊辰", "己巳", "庚午", "辛未", "壬申", "癸酉",
            "甲戌", "乙亥", "丙子", "丁丑", "戊寅", "己卯", "庚辰", "辛巳", "壬午", "癸未",
            "甲申", "乙酉", "丙戌", "丁亥", "戊子", "己丑", "庚寅", "辛卯", "壬辰", "癸巳",
            "甲午", "乙未", "丙申", "丁酉", "戊戌", "己亥", "庚子", "辛丑", "壬寅", "癸卯",
            "甲辰", "乙巳", "丙午", "丁未", "戊申", "己酉", "庚戌", "辛亥", "壬子", "癸丑",
            "甲寅", "乙卯", "丙辰", "丁巳", "戊午", "己未", "庚申", "辛酉", "壬戌", "癸亥"
    };


    /**
     * 找数组中月柱起始位置
     *
     * @param yuezhu
     * @return
     */
    public int getMonthZhuStart(String yuezhu) {

        int start = -1;
        for (int i = 0; i < _60GANZHI.length; i++) {
            if (yuezhu.equals(_60GANZHI[i])) {
                start = i;
                break;
            }
        }
        return start;
    }

    //顺行排大运
    private String[] shunDayun(String yuezhu) {
        String[] dayunStringArray = new String[8];//取八个
        int start = getMonthZhuStart(yuezhu);
        if (start == -1)
            return null;
         else
            start++;

        for (int i = 0; i < 8; i++) {
            dayunStringArray[i] = _60GANZHI[(start + i) % _60GANZHI.length];
        }
        return dayunStringArray;
    }

    //逆行排大运
    private String[] niDayun(String yuezhu) {

        String[] dayunStringArray = new String[8];//取八个

        int start = getMonthZhuStart(yuezhu);
        if (start == -1)
            return null;
         else
            start--;

        for (int i = 0; i < 8; i++) {
            dayunStringArray[i] = _60GANZHI[(start - i) % _60GANZHI.length];
        }
        return dayunStringArray;
    }
    //大运用月柱排

    public String[] dayun(String nianzhu, String yuezhu, SEX isman) {
        String[] dayunStringArray = null;
        if (yuezhu == null || yuezhu.length() != 2) {
            return null;
        }
        //甲、丙、戊、庚、壬之年为阳，乙、丁、己、辛、癸之年为阴
        //阴年生男子（或阳年生女子），大运逆行否则顺行
        if (nianzhu.startsWith("甲") || nianzhu.startsWith("丙") || nianzhu.startsWith("戊") || nianzhu.startsWith("庚")
                || nianzhu.startsWith("庚") || nianzhu.startsWith("壬")) {
            if (isman == SEX.MAN) {//顺行
                dayunStringArray = shunDayun(yuezhu);
            } else {
                dayunStringArray = niDayun(yuezhu);
            }

        } else {
            if (isman == SEX.MAN) {
                dayunStringArray = niDayun(yuezhu);
            } else {
                dayunStringArray = shunDayun(yuezhu);
            }

        }
        return dayunStringArray;
    }

    public static void main(String[] args) {
//        String birth = "1981-05-24 06";
        String birth = "2004-02-03 06";
//        String birth = "2004-02-04 06";
//        String birth = "2004-02-04 06";
        try {
            Calendar cal = Date4jUtil.getCalendarFromString(birth, "yyyy-MM-dd HH");
            BaZi lunar = new BaZi(cal);
            int time = cal.get(Calendar.HOUR_OF_DAY) / 2;

            String baziStr = lunar.getYearGanZhi(time);

            String[] baziStrChar = baziStr.split(",");
            //我修改原来的，用,分割
            String ganziyear = baziStrChar[0];//年柱
            String ganzimonth = baziStrChar[1];//月柱
            String ganziday = baziStrChar[2];//日柱
            String ganzitime = baziStrChar[3];//时柱

            Paipan paipan = new Paipan();
            String[] array = paipan.dayun(ganziyear, ganzimonth, SEX.MAN);
            StringBuilder sbDayun = new StringBuilder();
            for (String s : array){
                sbDayun.append(s + " ");
            }

            System.out.println("公历【" +  birth + "】");
            System.out.println("农历【" + lunar.toString() + "】");
            System.out.println("八字【" +  baziStr + "】");
            System.out.println("农历生肖【" + lunar.animalsYear() + "】");
            System.out.println("大运【" + sbDayun + "】");

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
