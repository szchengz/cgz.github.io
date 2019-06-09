package com.bdp.horoscope;

import com.bdp.util.Date4jUtil;

import java.util.*;

/**
 * Created by cgz on 2019-06-07 14:09
 * 描述：
 */
public class SolarTermsUtil {

    private static final double D = 0.2422;
    private final static Map<String, Integer[]> INCREASE_OFFSETMAP = new HashMap<String, Integer[]>();//+1偏移
    private final static Map<String, Integer[]> DECREASE_OFFSETMAP = new HashMap<String, Integer[]>();//-1偏移

    /**
     * 24节气
     **/
    private static enum SolarTermsEnum {
        LICHUN,//--立春
        YUSHUI,//--雨水
        JINGZHE,//--惊蛰
        CHUNFEN,//春分
        QINGMING,//清明
        GUYU,//谷雨
        LIXIA,//立夏
        XIAOMAN,//小满
        MANGZHONG,//芒种
        XIAZHI,//夏至
        XIAOSHU,//小暑
        DASHU,//大暑
        LIQIU,//立秋
        CHUSHU,//处暑
        BAILU,//白露
        QIUFEN,//秋分
        HANLU,//寒露
        SHUANGJIANG,//霜降
        LIDONG,//立冬
        XIAOXUE,//小雪
        DAXUE,//大雪
        DONGZHI,//冬至
        XIAOHAN,//小寒
        DAHAN;//大寒
    }

    static {
        DECREASE_OFFSETMAP.put(SolarTermsUtil.SolarTermsEnum.YUSHUI.name(), new Integer[]{2026});//雨水
        INCREASE_OFFSETMAP.put(SolarTermsUtil.SolarTermsEnum.CHUNFEN.name(), new Integer[]{2084});//春分
        INCREASE_OFFSETMAP.put(SolarTermsUtil.SolarTermsEnum.XIAOMAN.name(), new Integer[]{2008});//小满
        INCREASE_OFFSETMAP.put(SolarTermsUtil.SolarTermsEnum.MANGZHONG.name(), new Integer[]{1902});//芒种
        INCREASE_OFFSETMAP.put(SolarTermsUtil.SolarTermsEnum.XIAZHI.name(), new Integer[]{1928});//夏至
        INCREASE_OFFSETMAP.put(SolarTermsUtil.SolarTermsEnum.XIAOSHU.name(), new Integer[]{1925, 2016});//小暑
        INCREASE_OFFSETMAP.put(SolarTermsUtil.SolarTermsEnum.DASHU.name(), new Integer[]{1922});//大暑
        INCREASE_OFFSETMAP.put(SolarTermsUtil.SolarTermsEnum.LIQIU.name(), new Integer[]{2002});//立秋
        INCREASE_OFFSETMAP.put(SolarTermsUtil.SolarTermsEnum.BAILU.name(), new Integer[]{1927});//白露
        INCREASE_OFFSETMAP.put(SolarTermsUtil.SolarTermsEnum.QIUFEN.name(), new Integer[]{1942});//秋分
        INCREASE_OFFSETMAP.put(SolarTermsUtil.SolarTermsEnum.SHUANGJIANG.name(), new Integer[]{2089});//霜降
        INCREASE_OFFSETMAP.put(SolarTermsUtil.SolarTermsEnum.LIDONG.name(), new Integer[]{2089});//立冬
        INCREASE_OFFSETMAP.put(SolarTermsUtil.SolarTermsEnum.XIAOXUE.name(), new Integer[]{1978});//小雪
        INCREASE_OFFSETMAP.put(SolarTermsUtil.SolarTermsEnum.DAXUE.name(), new Integer[]{1954});//大雪
        DECREASE_OFFSETMAP.put(SolarTermsUtil.SolarTermsEnum.DONGZHI.name(), new Integer[]{1918, 2021});//冬至

        INCREASE_OFFSETMAP.put(SolarTermsUtil.SolarTermsEnum.XIAOHAN.name(), new Integer[]{1982});//小寒
        DECREASE_OFFSETMAP.put(SolarTermsUtil.SolarTermsEnum.XIAOHAN.name(), new Integer[]{2019});//小寒

        INCREASE_OFFSETMAP.put(SolarTermsUtil.SolarTermsEnum.DAHAN.name(), new Integer[]{2082});//大寒
    }

    //定义一个二维数组，第一维数组存储的是20世纪的节气C值，第二维数组存储的是21世纪的节气C值,0到23个，依次代表立春、雨水...大寒节气的C值
    private static final double[][] CENTURY_ARRAY = {{4.6295, 19.4599, 6.3826, 21.4155, 5.59, 20.888, 6.318, 21.86, 6.5, 22.2, 7.928, 23.65, 8.35, 23.95, 8.44, 23.822, 9.098, 24.218, 8.218, 23.08, 7.9, 22.6, 6.11, 20.84}, {3.87, 18.73, 5.63, 20.646, 4.81, 20.1, 5.52, 21.04, 5.678, 21.37, 7.108, 22.83, 7.5, 23.13, 7.646, 23.042, 8.318, 23.438, 7.438, 22.36, 7.18, 21.94, 5.4055, 20.12}};

    /**
     * @param year 年份
     * @param name 节气的名称
     * @return 返回节气是相应月份的第几天
     */
    public static int getSolarTermNum(int year, String name) {

        double centuryValue = 0;//节气的世纪值，每个节气的每个世纪值都不同
        name = name.trim().toUpperCase();
        int ordinal = SolarTermsUtil.SolarTermsEnum.valueOf(name).ordinal();

        int centuryIndex = -1;
        if (year >= 1901 && year <= 2000) {//20世纪
            centuryIndex = 0;
        } else if (year >= 2001 && year <= 2100) {//21世纪
            centuryIndex = 1;
        } else {
            throw new RuntimeException("不支持此年份：" + year + "，目前只支持1901年到2100年的时间范围");
        }
        centuryValue = CENTURY_ARRAY[centuryIndex][ordinal];
        int dateNum = 0;
/**
 * 计算 num =[Y*D+C]-L这是传说中的寿星通用公式
 * 公式解读：年数的后2位乘0.2422加C(即：centuryValue)取整数后，减闰年数
 */
        int y = year % 100;//步骤1:取年分的后两位数
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {//闰年
            if (ordinal == SolarTermsUtil.SolarTermsEnum.XIAOHAN.ordinal() ||
                    ordinal == SolarTermsUtil.SolarTermsEnum.DAHAN.ordinal() ||
                    ordinal == SolarTermsUtil.SolarTermsEnum.LICHUN.ordinal() ||
                    ordinal == SolarTermsUtil.SolarTermsEnum.YUSHUI.ordinal()) {
//注意：凡闰年3月1日前闰年数要减一，即：L=[(Y-1)/4],因为小寒、大寒、立春、雨水这两个节气都小于3月1日,所以 y = y-1
                y = y - 1;//步骤2
            }
        }
        dateNum = (int) (y * D + centuryValue) - (int) (y / 4);//步骤3，使用公式[Y*D+C]-L计算
        dateNum += specialYearOffset(year, name);//步骤4，加上特殊的年分的节气偏移量
        return dateNum;
    }

    /**
     * 特例,特殊的年分的节气偏移量,由于公式并不完善，所以算出的个别节气的第几天数并不准确，在此返回其偏移量
     *
     * @param year 年份
     * @param name 节气名称
     * @return 返回其偏移量
     */
    public static int specialYearOffset(int year, String name) {
        int offset = 0;
        offset += getOffset(DECREASE_OFFSETMAP, year, name, -1);
        offset += getOffset(INCREASE_OFFSETMAP, year, name, 1);

        return offset;
    }

    public static int getOffset(Map<String, Integer[]> map, int year, String name, int offset) {
        int off = 0;
        Integer[] years = map.get(name);
        if (null != years) {
            for (int i : years) {
                if (i == year) {
                    off = offset;
                    break;
                }
            }
        }
        return off;
    }

    public static String solarTermToString(int year) {
        StringBuffer sb = new StringBuffer();
        sb.append("---").append(year);
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {//闰年
            sb.append(" 闰年");
        } else {
            sb.append(" 平年");
        }

        sb.append("\n")
                .append("立春：2月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.LICHUN.name()))
                .append("日,雨水：2月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.YUSHUI.name()))
                .append("日,惊蛰:3月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.JINGZHE.name()))
                .append("日,春分:3月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.CHUNFEN.name()))
                .append("日,清明:4月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.QINGMING.name()))
                .append("日,谷雨:4月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.GUYU.name()))
                .append("日,立夏:5月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.LIXIA.name()))
                .append("日,小满:5月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.XIAOMAN.name()))
                .append("日,芒种:6月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.MANGZHONG.name()))
                .append("日,夏至:6月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.XIAZHI.name()))
                .append("日,小暑:7月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.XIAOSHU.name()))
                .append("日,大暑:7月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.DASHU.name()))
                .append("日,\n立秋:8月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.LIQIU.name()))
                .append("日,处暑:8月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.CHUSHU.name()))
                .append("日,白露:9月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.BAILU.name()))
                .append("日,秋分:9月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.QIUFEN.name()))
                .append("日,寒露:10月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.HANLU.name()))
                .append("日,霜降:10月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.SHUANGJIANG.name()))
                .append("日,立冬:11月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.LIDONG.name()))
                .append("日,小雪:11月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.XIAOXUE.name()))
                .append("日,大雪:12月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.DAXUE.name()))
                .append("日,冬至:12月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.DONGZHI.name()))
                .append("日,小寒:1月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.XIAOHAN.name()))
                .append("日,大寒:1月").append(getSolarTermNum(year, SolarTermsUtil.SolarTermsEnum.DAHAN.name()));

        return sb.toString();
    }

    public static String getSolarTermNumStr(int year, String name){
        int d = getSolarTermNum(year, name);
        if(d < 10)
            return "0"+d;
        else
            return ""+d;
    }

    //获取某年某节气的日期
    public static Date getSolarTermDate(int year, String name){
        Date date = null;
        String day = getSolarTermNumStr(year, name);
        switch (name){
            case "XIAOHAN":
                date = Date4jUtil.toDate(year + "-01-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.XIAOHAN.name()), "yyyy-MM-dd");
                break;
            case "DAHAN":
                date = Date4jUtil.toDate(year + "-01-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.DAHAN.name()), "yyyy-MM-dd");
                break;
            case "LICHUN":
                date = Date4jUtil.toDate(year + "-02-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.LICHUN.name()), "yyyy-MM-dd");
                break;
            case "YUSHUI":
                date = Date4jUtil.toDate(year + "-02-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.YUSHUI.name()), "yyyy-MM-dd");
                break;
            case "JINGZHE":
                date = Date4jUtil.toDate(year + "-03-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.JINGZHE.name()), "yyyy-MM-dd");
                break;
            case "CHUNFEN":
                date = Date4jUtil.toDate(year + "-03-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.CHUNFEN.name()), "yyyy-MM-dd");
                break;
            case "QINGMING":
                date = Date4jUtil.toDate(year + "-04-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.QINGMING.name()), "yyyy-MM-dd");
                break;
            case "GUYU":
                date = Date4jUtil.toDate(year + "-04-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.GUYU.name()), "yyyy-MM-dd");
                break;
            case "LIXIA":
                date = Date4jUtil.toDate(year + "-05-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.LIXIA.name()), "yyyy-MM-dd");
                break;
            case "XIAOMAN":
                date = Date4jUtil.toDate(year + "-05-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.XIAOMAN.name()), "yyyy-MM-dd");
                break;
            case "MANGZHONG":
                date = Date4jUtil.toDate(year + "-06-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.MANGZHONG.name()), "yyyy-MM-dd");
                break;
            case "XIAZHI":
                date = Date4jUtil.toDate(year + "-06-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.XIAZHI.name()), "yyyy-MM-dd");
                break;
            case "XIAOSHU":
                date = Date4jUtil.toDate(year + "-07-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.XIAOSHU.name()), "yyyy-MM-dd");
                break;
            case "DASHU":
                date = Date4jUtil.toDate(year + "-07-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.DASHU.name()), "yyyy-MM-dd");
                break;
            case "LIQIU":
                date = Date4jUtil.toDate(year + "-08-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.LIQIU.name()), "yyyy-MM-dd");
                break;
            case "CHUSHU":
                date = Date4jUtil.toDate(year + "-08-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.CHUSHU.name()), "yyyy-MM-dd");
                break;
            case "BAILU":
                date = Date4jUtil.toDate(year + "-09-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.BAILU.name()), "yyyy-MM-dd");
                break;
            case "QIUFEN":
                date = Date4jUtil.toDate(year + "-09-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.QIUFEN.name()), "yyyy-MM-dd");
                break;
            case "HANLU":
                date = Date4jUtil.toDate(year + "-10-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.HANLU.name()), "yyyy-MM-dd");
                break;
            case "SHUANGJIANG":
                date = Date4jUtil.toDate(year + "-10-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.SHUANGJIANG.name()), "yyyy-MM-dd");
                break;
            case "LIDONG":
                date = Date4jUtil.toDate(year + "-11-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.LIDONG.name()), "yyyy-MM-dd");
                break;
            case "XIAOXUE":
                date = Date4jUtil.toDate(year + "-11-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.XIAOXUE.name()), "yyyy-MM-dd");
                break;
            case "DAXUE":
                date = Date4jUtil.toDate(year + "-12-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.DAXUE.name()), "yyyy-MM-dd");
                break;
            case "DONGZHI":
                date = Date4jUtil.toDate(year + "-12-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.DONGZHI.name()), "yyyy-MM-dd");
                break;
        }
        return date;
    }

    public static List<Date> getSolarTerm(int year) {
        List<Date> list = new ArrayList<>();
        list.add(Date4jUtil.toDate((year-1) + "-12-" + getSolarTermNumStr((year-1), SolarTermsUtil.SolarTermsEnum.DAXUE.name()), "yyyy-MM-dd"));
//        list.add(Date4jUtil.toDate((year-1) + "-12-" + getSolarTermNumStr((year-1), SolarTermsUtil.SolarTermsEnum.DONGZHI.name()), "yyyy-MM-dd"));
        list.add(Date4jUtil.toDate(year + "-01-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.XIAOHAN.name()), "yyyy-MM-dd"));
//        list.add(Date4jUtil.toDate(year + "-01-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.DAHAN.name()), "yyyy-MM-dd"));
        list.add(Date4jUtil.toDate(year + "-02-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.LICHUN.name()), "yyyy-MM-dd"));
//        list.add(Date4jUtil.toDate(year + "-02-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.YUSHUI.name()), "yyyy-MM-dd"));
        list.add(Date4jUtil.toDate(year + "-03-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.JINGZHE.name()), "yyyy-MM-dd"));
//        list.add(Date4jUtil.toDate(year + "-03-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.CHUNFEN.name()), "yyyy-MM-dd"));
        list.add(Date4jUtil.toDate(year + "-04-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.QINGMING.name()), "yyyy-MM-dd"));
//        list.add(Date4jUtil.toDate(year + "-04-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.GUYU.name()), "yyyy-MM-dd"));
        list.add(Date4jUtil.toDate(year + "-05-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.LIXIA.name()), "yyyy-MM-dd"));
//        list.add(Date4jUtil.toDate(year + "-05-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.XIAOMAN.name()), "yyyy-MM-dd"));
        list.add(Date4jUtil.toDate(year + "-06-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.MANGZHONG.name()), "yyyy-MM-dd"));
//        list.add(Date4jUtil.toDate(year + "-06-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.XIAZHI.name()), "yyyy-MM-dd"));
        list.add(Date4jUtil.toDate(year + "-07-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.XIAOSHU.name()), "yyyy-MM-dd"));
//        list.add(Date4jUtil.toDate(year + "-07-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.DASHU.name()), "yyyy-MM-dd"));
        list.add(Date4jUtil.toDate(year + "-08-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.LIQIU.name()), "yyyy-MM-dd"));
//        list.add(Date4jUtil.toDate(year + "-08-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.CHUSHU.name()), "yyyy-MM-dd"));
        list.add(Date4jUtil.toDate(year + "-09-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.BAILU.name()), "yyyy-MM-dd"));
//        list.add(Date4jUtil.toDate(year + "-09-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.QIUFEN.name()), "yyyy-MM-dd"));
        list.add(Date4jUtil.toDate(year + "-10-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.HANLU.name()), "yyyy-MM-dd"));
//        list.add(Date4jUtil.toDate(year + "-10-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.SHUANGJIANG.name()), "yyyy-MM-dd"));
        list.add(Date4jUtil.toDate(year + "-11-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.LIDONG.name()), "yyyy-MM-dd"));
//        list.add(Date4jUtil.toDate(year + "-11-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.XIAOXUE.name()), "yyyy-MM-dd"));
        list.add(Date4jUtil.toDate(year + "-12-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.DAXUE.name()), "yyyy-MM-dd"));
//        list.add(Date4jUtil.toDate(year + "-12-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.DONGZHI.name()), "yyyy-MM-dd"));
        list.add(Date4jUtil.toDate((year+1) + "-01-" + getSolarTermNumStr((year+1), SolarTermsUtil.SolarTermsEnum.XIAOHAN.name()), "yyyy-MM-dd"));
        return list;
    }


    public static Map<String, String> getSolarTermByYear(int year) {
        Map<String, String> map = new IdentityHashMap<>();
        map.put("小寒", year + "-01-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.XIAOHAN.name()));
        map.put("大寒", year + "-01-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.DAHAN.name()));
        map.put("立春", year + "-02-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.LICHUN.name()));
        map.put("雨水", year + "-02-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.YUSHUI.name()));
        map.put("惊蛰", year + "-03-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.JINGZHE.name()));
        map.put("春分", year + "-03-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.CHUNFEN.name()));
        map.put("清明", year + "-04-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.QINGMING.name()));
        map.put("谷雨", year + "-04-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.GUYU.name()));
        map.put("立夏", year + "-05-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.LIXIA.name()));
        map.put("小满", year + "-05-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.XIAOMAN.name()));
        map.put("芒种", year + "-06-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.MANGZHONG.name()));
        map.put("夏至", year + "-06-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.XIAZHI.name()));
        map.put("小暑", year + "-07-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.XIAOSHU.name()));
        map.put("大暑", year + "-07-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.DASHU.name()));
        map.put("立秋", year + "-08-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.LIQIU.name()));
        map.put("处暑", year + "-08-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.CHUSHU.name()));
        map.put("白露", year + "-09-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.BAILU.name()));
        map.put("秋分", year + "-09-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.QIUFEN.name()));
        map.put("寒露", year + "-10-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.HANLU.name()));
        map.put("霜降", year + "-10-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.SHUANGJIANG.name()));
        map.put("立冬", year + "-11-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.LIDONG.name()));
        map.put("小雪", year + "-11-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.XIAOXUE.name()));
        map.put("大雪", year + "-12-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.DAXUE.name()));
        map.put("冬至", year + "-12-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.DONGZHI.name()));
        return map;
    }


    /**返回24节气的日期*/
    public static String[] getSolarTermDateByYear(int year) {

        String[] array = new String[24];
        array[0] = year + "-02-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.LICHUN.name());
        array[1] = year + "-02-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.YUSHUI.name());
        array[2] = year + "-03-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.JINGZHE.name());
        array[3] = year + "-03-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.CHUNFEN.name());
        array[4] = year + "-04-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.QINGMING.name());
        array[5] = year + "-04-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.GUYU.name());
        array[6] = year + "-05-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.LIXIA.name());
        array[7] = year + "-05-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.XIAOMAN.name());
        array[8] = year + "-06-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.MANGZHONG.name());
        array[9] = year + "-06-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.XIAZHI.name());
        array[10] = year + "-07-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.XIAOSHU.name());
        array[11] = year + "-07-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.DASHU.name());
        array[12] = year + "-08-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.LIQIU.name());
        array[13] = year + "-08-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.CHUSHU.name());
        array[14] = year + "-09-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.BAILU.name());
        array[15] = year + "-09-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.QIUFEN.name());
        array[16] = year + "-10-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.HANLU.name());
        array[17] = year + "-10-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.SHUANGJIANG.name());
        array[18] = year + "-11-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.LIDONG.name());
        array[19] = year + "-11-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.XIAOXUE.name());
        array[20] = year + "-12-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.DAXUE.name());
        array[21] = year + "-12-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.DONGZHI.name());
        array[22] = year + "-01-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.XIAOHAN.name());
        array[23] = year + "-01-" + getSolarTermNumStr(year, SolarTermsUtil.SolarTermsEnum.DAHAN.name());

        return array;
    }

    public static Date getNextSorlarDate(Date mydate, boolean isBack){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mydate);
        int year = calendar.get(Calendar.YEAR);

        List<Date> list = getSolarTerm(year);
        int size = list.size();
        if(isBack) {
            //往后
            for (int i = size-1; i > -1; i--) {
                Date d = list.get(i);
                if(d.getTime() <= mydate.getTime())
                    return d;
            }
        } else {
            for (int i = 0; i < size; i++) {
                Date d = list.get(i);
                if(d.getTime() >= mydate.getTime())
                    return d;
            }
        }
        return null;
    }

    public static int getDays(Date mydate, boolean isBack){

        Date d = getNextSorlarDate(mydate, isBack);

        long days = 0L;

        if(isBack) {
            days = (mydate.getTime() - d.getTime()) / (1000 * 60 * 60 * 24);
        } else {
            days = (d.getTime() - mydate.getTime()) / (1000 * 60 * 60 * 24);
        }

        return (int)days;
    }

    public static String getSy(int days){
        int year = days / 3;
        int month = days % 3;
        return year + "年" + (month * 4) + "月";
    }

    //获取起运岁数
    public static String getQiYunTime(String bith, int sex){
        Date date = Date4jUtil.toDate(bith, "yyyy-MM-dd");

        return null;
    }

    public static void main(String[] args) {
//        for (int year = 1901; year < 2050; year++) {
//            System.out.println(solarTermToString(year));
//        }

//        System.out.println(solarTermToString(2019));
//
//        String[] ss = getSolarTermDateByYear(2019);
//        for(String s : ss){
//            System.out.println(s);
//        }

//        List<Date> list = getSolarTerm(1994);
//        for (Date d : list) {
//            System.out.println(Date4jUtil.formatDate(d));
//        }

//        String thedateStr = "1980-01-01";
//        String thedateStr = "2013-07-13";
//        String thedateStr = "2004-01-22";
        String thedateStr = "2004-02-19";
//        String thedateStr = "1981-04-21";
//        String thedateStr = "1979-11-19";
        Date date = Date4jUtil.toDate(thedateStr, "yyyy-MM-dd");

        Date d0 = getNextSorlarDate(date, false);
        Date d1 = getNextSorlarDate(date, true);
        int n0 = getDays(date, false);
        int n1 = getDays(date, true);
        String sy0 = getSy(n0);
        String sy1 = getSy(n1);
        System.out.printf("%s下一个节气日期是%s(相差%d天),上运%s\n", thedateStr, Date4jUtil.formatDate(d0), n0, sy0);
        System.out.printf("%s上一个节气日期是%s(相差%d天),上运%s", thedateStr, Date4jUtil.formatDate(d1), n1, sy1);
//
//        System.out.println(Date4jUtil.formatDate(d));
//        System.out.println(getDays(date, false));
//
//        System.out.println(Date4jUtil.formatDate(d1));
//        System.out.println(getDays(date, true));


//            int day = getSolarTermNum(2019, SolarTermsEnum.MANGZHONG.name());
//            System.out.println(day);

    }
}