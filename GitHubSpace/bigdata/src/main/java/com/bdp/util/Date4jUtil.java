package com.bdp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by cgz on 2019-06-07 20:05
 * 描述：
 */
public class Date4jUtil {

    private final static String DEFAULT_DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**返回星期*/
    public static String getWeekStr(Date date) {
        return formatDate(date, "EE");
    }


    public static Date now() {
        return new Date();
    }
    public static String nowStr() {
        return nowStr("yyyy-MM-dd HH:mm:ss");
    }
    public static String nowStr(String format) {
        return formatDate(new Date(), format);
    }

    /**返回星期*/
    public static String getYearMonth(Date date) {
        return formatDate(date, "yyyyMM");
    }

    public static String formatDate(Date date){
        DateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATA_FORMAT);
        return dateFormat.format(date);
    }


    /**
     * 获取下几天的日期（0点的日期）
     */
    public static Date getNextDay(int days){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getDay(){
        return getNextDay(0);
    }

    /**
     * 获取前几天的日期（0点的日期）
     * @param days
     * @return
     */
    public static Date getPreDay(int days){
        return getNextDay(0 - days);
    }

    public static Date getNextMin(int min) {
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getPreMin(int min){
        return getNextMin(0 - min);
    }

    public static String formatDate(long times){
        DateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATA_FORMAT);
        return dateFormat.format(new Date(times));
    }

    public static String formatDate(Date date, String format){
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String formatDate(String srcDate, String srcFormat, String aimFormat) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(srcFormat);
        Date date = dateFormat.parse(srcDate);

        return formatDate(date, aimFormat);
    }

    public static boolean isToday(Date date) {
        String format = "yyyyMM" ;
        if (formatDate(date, format).equals(formatDate(new Date(), format))){
            return true;
        }
        return false;
    }

    public static Date getMinDate(Date date1, Date date2) {
        return date1.getTime() > date2.getTime() ? date2 : date1;
    }

    public static Date getMaxDate(Date date1, Date date2) {
        return date1.getTime() < date2.getTime() ? date2 : date1;
    }

    public static boolean gt(Date date1, Date date2) {
        return date1.getTime() - date2.getTime() >= 0 ? true : false;
    }
    public static boolean lt(Date date1, Date date2) {
        return date1.getTime() - date2.getTime() <= 0 ? true : false;
    }


    public static String formatTimes(long tims, String format){
        return formatDate(new Date(tims), format);
    }

    public static Date toDate(String dateStr, String format) {



        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static Date toDate(String dateStr) {
        return toDate(dateStr, DEFAULT_DATA_FORMAT);
    }

    public static String getSequenceNumber() {
//        Date d = new Date();
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
//        String str=sdf.format(d);
        String haomiao=String.valueOf(System.currentTimeMillis());
        return haomiao;
    }

    public static Date getDateFromSequenceNumber(String seqNumber) {
//        20190122 1652 25 904002
        String datatimeStr = seqNumber.substring(0,14);
        return toDate(datatimeStr, "yyyyMMddHHmmss");
    }

    public static Date add(Date dt, int days) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
//        rightNow.add(Calendar.YEAR,-1); //日期减1年
//        rightNow.add(Calendar.MONTH,3);//日期加3个月
        //日期加10天
        rightNow.add(Calendar.DAY_OF_YEAR,days);

        return rightNow.getTime();

    }

    /**
     *
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     *
     * @param date
     * @return
     */
    public static int getQuarter(Date date) {

        int season = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }

    /**
     * 取得当天日期是周几
     * @param date
     * @return
     */
    public static int getWeekDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week_of_year = c.get(Calendar.DAY_OF_WEEK);
        return week_of_year - 1;
    }

    /**
     * 传回农历 y年的生肖
     */
    public final static String animalsYear(int year) {
        final String[] Animals = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇",
                "马", "羊", "猴", "鸡", "狗", "猪"};
        return Animals[(year - 4) % 12];
    }

    // ====== 传入 月日的offset 传回干支, 0=甲子
    public final static String cyclicalmYear(int year) {
        int num = year - 1900 + 36;
        final String[] Gan = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚",
                "辛", "壬", "癸"};
        final String[] Zhi = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午",
                "未", "申", "酉", "戌", "亥"};
        return (Gan[num % 10] + Zhi[num % 12]);
    }

    public static Calendar getCalendarFromString(String dateStr, String dateFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(dateStr));
        return cal;
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        Date d = Date4jUtil.getPreMin(3);
        System.out.println(Date4jUtil.formatDate(d));

    }
}
