package com.bdp.horoscope;


/**
 * Created by cgz on 2019-06-13 16:02
 * 描述：
 */
public class PaiDayun {

    public static final String[] JIAZHI = {
            "甲子", "乙丑", "丙寅", "丁卯", "戊辰", "己巳", "庚午", "辛未", "壬申", "癸酉",
            "甲戌", "乙亥", "丙子", "丁丑", "戊寅", "己卯", "庚辰", "辛巳", "壬午", "癸未",
            "甲申", "乙酉", "丙戌", "丁亥", "戊子", "己丑", "庚寅", "辛卯", "壬辰", "癸巳",
            "甲午", "乙未", "丙申", "丁酉", "戊戌", "己亥", "庚子", "辛丑", "壬寅", "癸卯",
            "甲辰", "乙巳", "丙午", "丁未", "戊申", "己酉", "庚戌", "辛亥", "壬子", "癸丑",
            "甲寅", "乙卯", "丙辰", "丁巳", "戊午", "己未", "庚申", "辛酉", "壬戌", "癸亥"
    };
    /**
     * 找数组中月柱起始位置
     * @param yuezhu
     * @return
     */
    public static int getYueZhuStart(String yuezhu) {

        int start = -1;
        for (int i = 0; i < JIAZHI.length; i++) {
            if (yuezhu.equals(JIAZHI[i])) {
                start = i;
                break;
            }
        }
        return start;
    }

    //顺行排大运
    private static String[] shunDaYun(String yuezhu) {

        String[] dayunStringArray = new String[8];//取八个

        int start = getYueZhuStart(yuezhu);
        if (start == -1) {
            return null;
        } else {
            start++;
        }
        for (int i = 0; i < 8; i++) {
            dayunStringArray[i] = JIAZHI[(start + i) % JIAZHI.length];
        }

        return dayunStringArray;
    }

    //逆行排大运
    private static String[] niDaYun(String yuezhu) {
        String[] dayunStringArray = new String[8];//取八个

        int start = getYueZhuStart(yuezhu);
        if (start == -1) {
            return null;
        } else {
            start--;
        }
        for (int i = 0; i < 8; i++) {
            dayunStringArray[i] = JIAZHI[(start - i) % JIAZHI.length];
        }
        return dayunStringArray;
    }


    /**
     *
     * @param yearYinYang 年的阴阳 0阴 1阳
     * @param sex 性别 0女 1男
     * @return
     */
    public static String[] dayun(int yearYinYang, int sex, String yuezhu) {
        if(yearYinYang + sex == 1){
            return niDaYun(yuezhu);
        } else {
            return shunDaYun(yuezhu);
        }
    }

    public static String[] dayun(String nianzhu, String yuezhu, int sex) {
        String[] dayunStringArray = null;
        if (yuezhu == null || yuezhu.length() != 2) {
            return null;
        }


        //甲、丙、戊、庚、壬之年为阳，乙、丁、己、辛、癸之年为阴
        //阴年生男子（或阳年生女子），大运逆行否则顺行
        if (nianzhu.startsWith("甲") || nianzhu.startsWith("丙") || nianzhu.startsWith("戊")
                || nianzhu.startsWith("庚") || nianzhu.startsWith("庚")
                || nianzhu.startsWith("壬")) {
            if (sex == 1) {//顺行
                dayunStringArray = shunDaYun(yuezhu);
            } else {
                dayunStringArray = niDaYun(yuezhu);
            }

        } else {
            if (sex == 1) {
                dayunStringArray = niDaYun(yuezhu);
            } else {
                dayunStringArray = shunDaYun(yuezhu);
            }

        }
        return dayunStringArray;
    }

    public static void main(String[] args) {

    }

}
