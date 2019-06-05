package com.bdp.bazi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
/**
 * Created by cgz on 2019-06-05 23:23
 * 描述：
 */
public class SolarTermsUtil {
    private static final String[] SolarTerm = { "小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑",
            "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至" };

    private static final String[] SolarTermCode = { "23", "24" , "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13",
            "14", "15", "16", "17", "18", "19", "20", "21", "22" };

    /**
     * 春季 ： 立春，雨水，惊蛰，春分，清明，谷雨
     */
    private static final String[] springCodes = {"01", "02", "03", "04", "05", "06"};

    /**
     * 夏季 ： 立夏，小满，芒种，夏至，小暑，大暑
     */
    private static final String[] summerCodes = {"07", "08", "09", "10", "11", "12"};

    /**
     * 秋季 ：立秋，处暑，白露，秋分，寒露，霜降
     */
    private static final String[] autumnCodes = {"13", "14", "15", "16", "17", "18"};

    /**
     * 冬季 ： 立冬，小雪，大雪，冬至，小寒，大寒
     */
    private static final String[] winterCodes = {"19", "20", "21", "22", "23", "24"};

    /**
     * 计算得到公历的年份
     */
    private int gregorianYear;

    /**
     * 计算得到公历的月份
     */
    private int gregorianMonth;

    /**
     * 用于计算得到公历的日期
     */
    private int gregorianDate;

    private int chineseYear;
    private int chineseMonth;
    private int chineseDate;

    // 初始日，公历农历对应日期：
    // 公历 1901 年 1 月 1 日，对应农历 4598 年 11 月 11 日
    private static int baseYear = 1901;
    private static int baseMonth = 1;
    private static int baseDate = 1;
    private static int baseIndex = 0;
    private static int baseChineseYear = 4598 - 1;
    private static int baseChineseMonth = 11;
    private static int baseChineseDate = 11;
    private static char[] daysInGregorianMonth = {31, 28, 31, 30, 31, 30, 31,
            31, 30, 31, 30, 31};

    private int sectionalTerm;
    private int principleTerm;

    private static char[][] sectionalTermMap = {
            {7, 6, 6, 6, 6, 6, 6, 6, 6, 5, 6, 6, 6, 5, 5, 6, 6, 5, 5, 5, 5, 5,
                    5, 5, 5, 4, 5, 5},
            {5, 4, 5, 5, 5, 4, 4, 5, 5, 4, 4, 4, 4, 4, 4, 4, 4, 3, 4, 4, 4, 3,
                    3, 4, 4, 3, 3, 3},
            {6, 6, 6, 7, 6, 6, 6, 6, 5, 6, 6, 6, 5, 5, 6, 6, 5, 5, 5, 6, 5, 5,
                    5, 5, 4, 5, 5, 5, 5},
            {5, 5, 6, 6, 5, 5, 5, 6, 5, 5, 5, 5, 4, 5, 5, 5, 4, 4, 5, 5, 4, 4,
                    4, 5, 4, 4, 4, 4, 5},
            {6, 6, 6, 7, 6, 6, 6, 6, 5, 6, 6, 6, 5, 5, 6, 6, 5, 5, 5, 6, 5, 5,
                    5, 5, 4, 5, 5, 5, 5},
            {6, 6, 7, 7, 6, 6, 6, 7, 6, 6, 6, 6, 5, 6, 6, 6, 5, 5, 6, 6, 5, 5,
                    5, 6, 5, 5, 5, 5, 4, 5, 5, 5, 5},
            {7, 8, 8, 8, 7, 7, 8, 8, 7, 7, 7, 8, 7, 7, 7, 7, 6, 7, 7, 7, 6, 6,
                    7, 7, 6, 6, 6, 7, 7},
            {8, 8, 8, 9, 8, 8, 8, 8, 7, 8, 8, 8, 7, 7, 8, 8, 7, 7, 7, 8, 7, 7,
                    7, 7, 6, 7, 7, 7, 6, 6, 7, 7, 7},
            {8, 8, 8, 9, 8, 8, 8, 8, 7, 8, 8, 8, 7, 7, 8, 8, 7, 7, 7, 8, 7, 7,
                    7, 7, 6, 7, 7, 7, 7},
            {9, 9, 9, 9, 8, 9, 9, 9, 8, 8, 9, 9, 8, 8, 8, 9, 8, 8, 8, 8, 7, 8,
                    8, 8, 7, 7, 8, 8, 8},
            {8, 8, 8, 8, 7, 8, 8, 8, 7, 7, 8, 8, 7, 7, 7, 8, 7, 7, 7, 7, 6, 7,
                    7, 7, 6, 6, 7, 7, 7},
            {7, 8, 8, 8, 7, 7, 8, 8, 7, 7, 7, 8, 7, 7, 7, 7, 6, 7, 7, 7, 6, 6,
                    7, 7, 6, 6, 6, 7, 7}};
    private static char[][] sectionalTermYear = {
            {13, 49, 85, 117, 149, 185, 201, 250, 250},
            {13, 45, 81, 117, 149, 185, 201, 250, 250},
            {13, 48, 84, 112, 148, 184, 200, 201, 250},
            {13, 45, 76, 108, 140, 172, 200, 201, 250},
            {13, 44, 72, 104, 132, 168, 200, 201, 250},
            {5, 33, 68, 96, 124, 152, 188, 200, 201},
            {29, 57, 85, 120, 148, 176, 200, 201, 250},
            {13, 48, 76, 104, 132, 168, 196, 200, 201},
            {25, 60, 88, 120, 148, 184, 200, 201, 250},
            {16, 44, 76, 108, 144, 172, 200, 201, 250},
            {28, 60, 92, 124, 160, 192, 200, 201, 250},
            {17, 53, 85, 124, 156, 188, 200, 201, 250}};
    private static char[][] principleTermMap = {
            {21, 21, 21, 21, 21, 20, 21, 21, 21, 20, 20, 21, 21, 20, 20, 20,
                    20, 20, 20, 20, 20, 19, 20, 20, 20, 19, 19, 20},
            {20, 19, 19, 20, 20, 19, 19, 19, 19, 19, 19, 19, 19, 18, 19, 19,
                    19, 18, 18, 19, 19, 18, 18, 18, 18, 18, 18, 18},
            {21, 21, 21, 22, 21, 21, 21, 21, 20, 21, 21, 21, 20, 20, 21, 21,
                    20, 20, 20, 21, 20, 20, 20, 20, 19, 20, 20, 20, 20},
            {20, 21, 21, 21, 20, 20, 21, 21, 20, 20, 20, 21, 20, 20, 20, 20,
                    19, 20, 20, 20, 19, 19, 20, 20, 19, 19, 19, 20, 20},
            {21, 22, 22, 22, 21, 21, 22, 22, 21, 21, 21, 22, 21, 21, 21, 21,
                    20, 21, 21, 21, 20, 20, 21, 21, 20, 20, 20, 21, 21},
            {22, 22, 22, 22, 21, 22, 22, 22, 21, 21, 22, 22, 21, 21, 21, 22,
                    21, 21, 21, 21, 20, 21, 21, 21, 20, 20, 21, 21, 21},
            {23, 23, 24, 24, 23, 23, 23, 24, 23, 23, 23, 23, 22, 23, 23, 23,
                    22, 22, 23, 23, 22, 22, 22, 23, 22, 22, 22, 22, 23},
            {23, 24, 24, 24, 23, 23, 24, 24, 23, 23, 23, 24, 23, 23, 23, 23,
                    22, 23, 23, 23, 22, 22, 23, 23, 22, 22, 22, 23, 23},
            {23, 24, 24, 24, 23, 23, 24, 24, 23, 23, 23, 24, 23, 23, 23, 23,
                    22, 23, 23, 23, 22, 22, 23, 23, 22, 22, 22, 23, 23},
            {24, 24, 24, 24, 23, 24, 24, 24, 23, 23, 24, 24, 23, 23, 23, 24,
                    23, 23, 23, 23, 22, 23, 23, 23, 22, 22, 23, 23, 23},
            {23, 23, 23, 23, 22, 23, 23, 23, 22, 22, 23, 23, 22, 22, 22, 23,
                    22, 22, 22, 22, 21, 22, 22, 22, 21, 21, 22, 22, 22},
            {22, 22, 23, 23, 22, 22, 22, 23, 22, 22, 22, 22, 21, 22, 22, 22,
                    21, 21, 22, 22, 21, 21, 21, 22, 21, 21, 21, 21, 22}};
    private static char[][] principleTermYear = {
            {13, 45, 81, 113, 149, 185, 201},
            {21, 57, 93, 125, 161, 193, 201},
            {21, 56, 88, 120, 152, 188, 200, 201},
            {21, 49, 81, 116, 144, 176, 200, 201},
            {17, 49, 77, 112, 140, 168, 200, 201},
            {28, 60, 88, 116, 148, 180, 200, 201},
            {25, 53, 84, 112, 144, 172, 200, 201},
            {29, 57, 89, 120, 148, 180, 200, 201},
            {17, 45, 73, 108, 140, 168, 200, 201},
            {28, 60, 92, 124, 160, 192, 200, 201},
            {16, 44, 80, 112, 148, 180, 200, 201},
            {17, 53, 88, 120, 156, 188, 200, 201}};

    private static char[] chineseMonths = {
            // 农历月份大小压缩表，两个字节表示一年。两个字节共十六个二进制位数，
            // 前四个位数表示闰月月份，后十二个位数表示十二个农历月份的大小。
            0x00, 0x04, 0xad, 0x08, 0x5a, 0x01, 0xd5, 0x54, 0xb4, 0x09, 0x64,
            0x05, 0x59, 0x45, 0x95, 0x0a, 0xa6, 0x04, 0x55, 0x24, 0xad, 0x08,
            0x5a, 0x62, 0xda, 0x04, 0xb4, 0x05, 0xb4, 0x55, 0x52, 0x0d, 0x94,
            0x0a, 0x4a, 0x2a, 0x56, 0x02, 0x6d, 0x71, 0x6d, 0x01, 0xda, 0x02,
            0xd2, 0x52, 0xa9, 0x05, 0x49, 0x0d, 0x2a, 0x45, 0x2b, 0x09, 0x56,
            0x01, 0xb5, 0x20, 0x6d, 0x01, 0x59, 0x69, 0xd4, 0x0a, 0xa8, 0x05,
            0xa9, 0x56, 0xa5, 0x04, 0x2b, 0x09, 0x9e, 0x38, 0xb6, 0x08, 0xec,
            0x74, 0x6c, 0x05, 0xd4, 0x0a, 0xe4, 0x6a, 0x52, 0x05, 0x95, 0x0a,
            0x5a, 0x42, 0x5b, 0x04, 0xb6, 0x04, 0xb4, 0x22, 0x6a, 0x05, 0x52,
            0x75, 0xc9, 0x0a, 0x52, 0x05, 0x35, 0x55, 0x4d, 0x0a, 0x5a, 0x02,
            0x5d, 0x31, 0xb5, 0x02, 0x6a, 0x8a, 0x68, 0x05, 0xa9, 0x0a, 0x8a,
            0x6a, 0x2a, 0x05, 0x2d, 0x09, 0xaa, 0x48, 0x5a, 0x01, 0xb5, 0x09,
            0xb0, 0x39, 0x64, 0x05, 0x25, 0x75, 0x95, 0x0a, 0x96, 0x04, 0x4d,
            0x54, 0xad, 0x04, 0xda, 0x04, 0xd4, 0x44, 0xb4, 0x05, 0x54, 0x85,
            0x52, 0x0d, 0x92, 0x0a, 0x56, 0x6a, 0x56, 0x02, 0x6d, 0x02, 0x6a,
            0x41, 0xda, 0x02, 0xb2, 0xa1, 0xa9, 0x05, 0x49, 0x0d, 0x0a, 0x6d,
            0x2a, 0x09, 0x56, 0x01, 0xad, 0x50, 0x6d, 0x01, 0xd9, 0x02, 0xd1,
            0x3a, 0xa8, 0x05, 0x29, 0x85, 0xa5, 0x0c, 0x2a, 0x09, 0x96, 0x54,
            0xb6, 0x08, 0x6c, 0x09, 0x64, 0x45, 0xd4, 0x0a, 0xa4, 0x05, 0x51,
            0x25, 0x95, 0x0a, 0x2a, 0x72, 0x5b, 0x04, 0xb6, 0x04, 0xac, 0x52,
            0x6a, 0x05, 0xd2, 0x0a, 0xa2, 0x4a, 0x4a, 0x05, 0x55, 0x94, 0x2d,
            0x0a, 0x5a, 0x02, 0x75, 0x61, 0xb5, 0x02, 0x6a, 0x03, 0x61, 0x45,
            0xa9, 0x0a, 0x4a, 0x05, 0x25, 0x25, 0x2d, 0x09, 0x9a, 0x68, 0xda,
            0x08, 0xb4, 0x09, 0xa8, 0x59, 0x54, 0x03, 0xa5, 0x0a, 0x91, 0x3a,
            0x96, 0x04, 0xad, 0xb0, 0xad, 0x04, 0xda, 0x04, 0xf4, 0x62, 0xb4,
            0x05, 0x54, 0x0b, 0x44, 0x5d, 0x52, 0x0a, 0x95, 0x04, 0x55, 0x22,
            0x6d, 0x02, 0x5a, 0x71, 0xda, 0x02, 0xaa, 0x05, 0xb2, 0x55, 0x49,
            0x0b, 0x4a, 0x0a, 0x2d, 0x39, 0x36, 0x01, 0x6d, 0x80, 0x6d, 0x01,
            0xd9, 0x02, 0xe9, 0x6a, 0xa8, 0x05, 0x29, 0x0b, 0x9a, 0x4c, 0xaa,
            0x08, 0xb6, 0x08, 0xb4, 0x38, 0x6c, 0x09, 0x54, 0x75, 0xd4, 0x0a,
            0xa4, 0x05, 0x45, 0x55, 0x95, 0x0a, 0x9a, 0x04, 0x55, 0x44, 0xb5,
            0x04, 0x6a, 0x82, 0x6a, 0x05, 0xd2, 0x0a, 0x92, 0x6a, 0x4a, 0x05,
            0x55, 0x0a, 0x2a, 0x4a, 0x5a, 0x02, 0xb5, 0x02, 0xb2, 0x31, 0x69,
            0x03, 0x31, 0x73, 0xa9, 0x0a, 0x4a, 0x05, 0x2d, 0x55, 0x2d, 0x09,
            0x5a, 0x01, 0xd5, 0x48, 0xb4, 0x09, 0x68, 0x89, 0x54, 0x0b, 0xa4,
            0x0a, 0xa5, 0x6a, 0x95, 0x04, 0xad, 0x08, 0x6a, 0x44, 0xda, 0x04,
            0x74, 0x05, 0xb0, 0x25, 0x54, 0x03};

    /**
     * 用于保存24节气
     */
    private static String[] principleTermNames = {"大寒", "雨水", "春分", "谷雨",
            "小满", "夏至", "大暑", "处暑", "秋分", "霜降", "小雪", "冬至"};
    private static String[] principleTermCodes = {"24", "02", "04", "06",
            "08", "10", "12",  "14", "16", "18","20","22"};
    /**
     * 用于保存24节气
     */
    private static String[] sectionalTermNames = {"小寒", "立春", "惊蛰", "清明",
            "立夏", "芒种", "小暑", "立秋", "白露", "寒露", "立冬", "大雪"};
    private static String[] sectionalTermCodes = {"23", "01", "03", "05",
            "07", "09", "11", "13", "15", "17", "19",  "21"};

    public SolarTermsUtil(Calendar calendar) {
        gregorianYear = calendar.get(Calendar.YEAR);
        gregorianMonth = calendar.get(Calendar.MONTH) + 1;
        gregorianDate = calendar.get(Calendar.DATE);
        computeChineseFields();
        computeSolarTerms();
    }

    public int computeChineseFields() {
        if (gregorianYear < 1901 || gregorianYear > 2100)
            return 1;
        int startYear = baseYear;
        int startMonth = baseMonth;
        int startDate = baseDate;
        chineseYear = baseChineseYear;
        chineseMonth = baseChineseMonth;
        chineseDate = baseChineseDate;
        // 第二个对应日，用以提高计算效率
        // 公历 2000 年 1 月 1 日，对应农历 4697 年 11 月 25 日
        if (gregorianYear >= 2000) {
            startYear = baseYear + 99;
            startMonth = 1;
            startDate = 1;
            chineseYear = baseChineseYear + 99;
            chineseMonth = 11;
            chineseDate = 25;
        }
        int daysDiff = 0;
        for (int i = startYear; i < gregorianYear; i++) {
            daysDiff += 365;
            if (isGregorianLeapYear(i))
                daysDiff += 1; // leap year
        }
        for (int i = startMonth; i < gregorianMonth; i++) {
            daysDiff += daysInGregorianMonth(gregorianYear, i);
        }
        daysDiff += gregorianDate - startDate;

        chineseDate += daysDiff;
        int lastDate = daysInChineseMonth(chineseYear, chineseMonth);
        int nextMonth = nextChineseMonth(chineseYear, chineseMonth);
        while (chineseDate > lastDate) {
            if (Math.abs(nextMonth) < Math.abs(chineseMonth))
                chineseYear++;
            chineseMonth = nextMonth;
            chineseDate -= lastDate;
            lastDate = daysInChineseMonth(chineseYear, chineseMonth);
            nextMonth = nextChineseMonth(chineseYear, chineseMonth);
        }
        return 0;
    }

    public int computeSolarTerms() {
        if (gregorianYear < 1901 || gregorianYear > 2100)
            return 1;
        sectionalTerm = sectionalTerm(gregorianYear, gregorianMonth);
        principleTerm = principleTerm(gregorianYear, gregorianMonth);
        return 0;
    }

    public static int sectionalTerm(int y, int m) {
        if (y < 1901 || y > 2100)
            return 0;
        int index = 0;
        int ry = y - baseYear + 1;
        while (ry >= sectionalTermYear[m - 1][index])
            index++;
        int term = sectionalTermMap[m - 1][4 * index + ry % 4];
        if ((ry == 121) && (m == 4))
            term = 5;
        if ((ry == 132) && (m == 4))
            term = 5;
        if ((ry == 194) && (m == 6))
            term = 6;
        return term;
    }

    public static int principleTerm(int y, int m) {
        if (y < 1901 || y > 2100)
            return 0;
        int index = 0;
        int ry = y - baseYear + 1;
        while (ry >= principleTermYear[m - 1][index])
            index++;
        int term = principleTermMap[m - 1][4 * index + ry % 4];
        if ((ry == 171) && (m == 3))
            term = 21;
        if ((ry == 181) && (m == 5))
            term = 21;
        return term;
    }

    /**
     * 用于判断输入的年份是否为闰年
     *
     * @param year 输入的年份
     * @return true 表示闰年
     */
    public static boolean isGregorianLeapYear(int year) {
        boolean isLeap = false;
        if (year % 4 == 0)
            isLeap = true;
        if (year % 100 == 0)
            isLeap = false;
        if (year % 400 == 0)
            isLeap = true;
        return isLeap;
    }

    public static int daysInGregorianMonth(int y, int m) {
        int d = daysInGregorianMonth[m - 1];
        if (m == 2 && isGregorianLeapYear(y))
            d++; // 公历闰年二月多一天
        return d;
    }

    public static int daysInChineseMonth(int y, int m) {
        // 注意：闰月 m < 0
        int index = y - baseChineseYear + baseIndex;
        int v = 0;
        int l = 0;
        int d = 30;
        if (1 <= m && m <= 8) {
            v = chineseMonths[2 * index];
            l = m - 1;
            if (((v >> l) & 0x01) == 1)
                d = 29;
        } else if (9 <= m && m <= 12) {
            v = chineseMonths[2 * index + 1];
            l = m - 9;
            if (((v >> l) & 0x01) == 1)
                d = 29;
        } else {
            v = chineseMonths[2 * index + 1];
            v = (v >> 4) & 0x0F;
            if (v != Math.abs(m)) {
                d = 0;
            } else {
                d = 29;
                for (int i = 0; i < bigLeapMonthYears.length; i++) {
                    if (bigLeapMonthYears[i] == index) {
                        d = 30;
                        break;
                    }
                }
            }
        }
        return d;
    }

    public static int nextChineseMonth(int y, int m) {
        int n = Math.abs(m) + 1;
        if (m > 0) {
            int index = y - baseChineseYear + baseIndex;
            int v = chineseMonths[2 * index + 1];
            v = (v >> 4) & 0x0F;
            if (v == m)
                n = -m;
        }
        if (n == 13)
            n = 1;
        return n;
    }

    // 大闰月的闰年年份
    private static int[] bigLeapMonthYears = {6, 14, 19, 25, 33, 36, 38, 41,
            44, 52, 55, 79, 117, 136, 147, 150, 155, 158, 185, 193};

    /**
     * 用于获取24节气的值
     *
     * @return 24节气的值
     */
    public String getSolartermsName() {
        String str = "";
        String gm = String.valueOf(gregorianMonth);
        if (gm.length() == 1)
            gm = ' ' + gm;
        String cm = String.valueOf(Math.abs(chineseMonth));
        if (cm.length() == 1)
            cm = ' ' + cm;
        String gd = String.valueOf(gregorianDate);
        if (gd.length() == 1)
            gd = ' ' + gd;
        String cd = String.valueOf(chineseDate);
        if (cd.length() == 1)
            cd = ' ' + cd;
        if (gregorianDate == sectionalTerm) {
            str = sectionalTermNames[gregorianMonth - 1];
        } else if (gregorianDate == principleTerm) {
            str = principleTermNames[gregorianMonth - 1];
        }
        return str;
    }

    /**
     * 用于获取24节气的编码
     *
     * @return 24节气的编码
     */
    public String getSolartermsCode() {
        String str = "";
        String gm = String.valueOf(gregorianMonth);
        if (gm.length() == 1)
            gm = ' ' + gm;
        String cm = String.valueOf(Math.abs(chineseMonth));
        if (cm.length() == 1)
            cm = ' ' + cm;
        String gd = String.valueOf(gregorianDate);
        if (gd.length() == 1)
            gd = ' ' + gd;
        String cd = String.valueOf(chineseDate);
        if (cd.length() == 1)
            cd = ' ' + cd;
        if (gregorianDate == sectionalTerm) {
            str = sectionalTermCodes[gregorianMonth - 1];
        } else if (gregorianDate == principleTerm) {
            str = principleTermCodes[gregorianMonth - 1];
        }
        return str;
    }

    /**
     * 获取当前节气后边三个节气的编码
     * @param args
     */
    public static String[] getAdjacentThreeSolarTermCodes(String currentCode){
        int[] index = new int[4];
        for(int i=0 ; i<24 ; i++){
            if(currentCode.equals(SolarTermCode[i])){
                index[0] = i;
                break;
            }
        }
        index[1] = (index[0]+1) % 24 ;
        index[2] = (index[0]+2) % 24 ;
        index[3] = (index[0]+3) % 24 ;

        return new String[]{SolarTermCode[index[0]],SolarTermCode[index[1]],SolarTermCode[index[2]],SolarTermCode[index[3]]};
    }

    /**
     * 根据日期，计算所在节气季度
     * @return 1   春季
     *            2   夏季
     *         3   秋季
     *         4   冬季
     *         0   计算出现错误
     */
    public static int getSolarQuarter(Date date){
        Calendar today = Calendar.getInstance();
        today.setTime(date);
        SolarTermsUtil solarTermsUtil = new SolarTermsUtil(today);
        String solarTermsCode = solarTermsUtil.getSolartermsCode();
        for(int i=0;i<=30;i++){
            if("".equals(solarTermsCode)){
                today.add(Calendar.DATE, -1);// 日期减1
                solarTermsUtil = new SolarTermsUtil(today);
                solarTermsCode = solarTermsUtil.getSolartermsCode();
            }else{
                break;
            }
        }
        for(int i=1; i<=4; i++){
            if(i==1){
                for(int index=0,size=springCodes.length; index<size; index++){
                    if(solarTermsCode.equals(springCodes[index])){
                        return 1;
                    }
                }
            }else if(i==2){
                for(int index=0,size=summerCodes.length; index<size; index++){
                    if(solarTermsCode.equals(summerCodes[index])){
                        return 2;
                    }
                }
            }else if(i==3){
                for(int index=0,size=autumnCodes.length; index<size; index++){
                    if(solarTermsCode.equals(autumnCodes[index])){
                        return 3;
                    }
                }
            }else if(i==4){
                for(int index=0,size=winterCodes.length; index<size; index++){
                    if(solarTermsCode.equals(winterCodes[index])){
                        return 4;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 获取指定月份中 包含的节气
     * @param year
     * @param month
     * @return 节气名称列表
     */
    public static ArrayList<String> getSolarTermsOfMonth(int year, int month) {
        ArrayList<String> termsList = new ArrayList<String>();
        Date date1 = DateFormatUtil.getFirstDayOfMonth(year, month);
        Date date2 = DateFormatUtil.getAfterMonthDate(date1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        SolarTermsUtil solarTermsUtil = null;
        String solarTerms = "";
        while(cal.getTime().getTime() < date2.getTime()) {
            solarTermsUtil = new SolarTermsUtil(cal);
            solarTerms = solarTermsUtil.getSolartermsName();
            if(!"".equals(solarTerms)) {
                termsList.add(solarTerms);
            }
            cal.add(Calendar.DATE, +1);// 日期+1
        }
        return termsList;
    }

    public static void main(String[] args) {
        /*Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        SolarTermsUtil solarTermsUtil = new SolarTermsUtil(today);
        String solarTerms = solarTermsUtil.getSolartermsName();
        String solarTermsCode = solarTermsUtil.getSolartermsCode();
        for(int i=0;i<=30;i++){
            if("".equals(solarTerms)){
                today.add(Calendar.DATE, -1);// 日期减1
                solarTermsUtil = new SolarTermsUtil(today);
                solarTerms = solarTermsUtil.getSolartermsName();
                solarTermsCode = solarTermsUtil.getSolartermsCode();
            }else{
                break;
            }
        }
        System.out.println(solarTerms+":"+solarTermsCode);

        for(String s : solarTermsUtil.getAdjacentThreeSolarTermCodes(solarTermsCode)){
            System.out.println(s);
        }
        //当前日期所在 阴历季度
        System.out.println(getSolarQuarter(DateFormatUtil.stringToDate("yyyy-MM-dd HH:mm:ss","2017-8-6 00:00:01")));*/

        System.out.println(getSolarTermsOfMonth(2019, 5));
    }
}
