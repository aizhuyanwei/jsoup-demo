/**
 * BEYONDSOFT.COM INC
 */
package com.demo.jsoup.common;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间工具类
 *
 * @author: zyw9527
 * @version: v1.0  Created in 2019年03月15日  14:21 by zyw9527
 */
public class DateUtils {
    public static final String DEFAULT = "yyyy/MM/dd";

    public static final String YYYYMMDD = "yyyy-MM-dd";

    public static final String YYYY = "yyyy";

    public static final String YYYYMMDD_NO_SEPARATOR = "yyyyMMdd";

    public static final String YYYYMMDDHH = "yyyy-MM-dd HH";

    public static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";

    public static final String YYYYMMDD000000 = "yyyy-MM-dd 00:00:00";

    public static final String YYYYMMDDHH0000 = "yyyy-MM-dd HH:00:00";

    public static final String YYYYMMDDHHMM00 = "yyyy-MM-dd HH:mm:00";

    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYYMMDDHHMMDOT = "yyyy.MM.dd HH:mm";

    public static final String CHN_YMD = "yyyy年MM月dd日";

    public static final String CHN_YMD_HH = "yyyy-MM-dd HH时";

    public static final String CHN_YMD_HHMM = "yyyy年MM月dd日HH:mm";

    public static final String CHN_MDHHMM = "MM月dd日HH时mm分";

    public static final String CHN_MD = "MM月dd日";

    /**
     * date转换成string
     *
     * @param date    Date对象
     * @param pattern 转换格式
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null || StringUtils.isBlank(pattern)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * Calendar转换成string
     *
     * @param calendar Calendar对象
     * @param pattern  转换格式
     * @return
     */
    public static String format(Calendar calendar, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(calendar.getTime());
    }

    /**
     * string转换Date
     *
     * @param datestring 日期格式字符串
     * @param pattern    转换格式
     * @return Date对象
     */
    public static Date parse2Date(String datestring, String pattern) {
        if (StringUtils.isBlank(pattern) || StringUtils.isBlank(datestring)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(datestring);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * string转换Calender
     *
     * @param datestring 日期格式字符串
     * @param pattern    转换格式
     * @return Calendar对象
     */
    public static Calendar parse2Calendar(String datestring, String pattern) {
        if (StringUtils.isBlank(pattern) || StringUtils.isBlank(datestring)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(datestring));
            return calendar;
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取传入时间所属月的首日
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calendar.getTime();
        return firstDayOfMonth;
    }

    /**
     * 获取传入时间所属月的末尾日
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDayOfMonth = calendar.getTime();
        return lastDayOfMonth;
    }

    /**
     * 往后倒退年份
     *
     * @return
     */
    public static Date getBackYear(Integer num) {
        Calendar calendar = Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH);
        calendar.set(year - num, month, 1);
        return calendar.getTime();
    }

    /**
     * 往后倒退年份到12月31日
     *
     * @return
     */
    public static Date getBackEndYear(Integer num) {
        Calendar calendar = Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        calendar.set(year - num, Calendar.DECEMBER, 31);
        return calendar.getTime();
    }

    /**
     * 往后倒退年份到1月1日
     *
     * @return
     */
    public static Date getBackStartYear(Integer num) {
        Calendar calendar = Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        calendar.set(year - num, Calendar.JANUARY, 1);
        return calendar.getTime();
    }

    /**
     * 往前倒退num天
     *
     * @return
     */
    public static Date getBackDay(Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -num);
        return calendar.getTime();
    }

    /**
     * 将日期时间归至零点
     *
     * @param date
     * @return
     */
    public static Date dateToBeginOfDay(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }
        return null;
    }

    /**
     * 秒转成天数
     *
     * @param mm
     * @return
     */
    public static Long convert2Day(Long mm) {
        if (null == mm) {
            return 0L;
        }
        Long day = mm / (60 * 60 * 24);
        return day;
    }

    /**
     * 天数转成秒数
     *
     * @param day
     * @return
     */
    public static Long convert2mill(Long day) {
        if (null == day) {
            return 0L;
        }
        return day * 24 * 60 * 60;
    }

    /**
     * 判断某个时间是否超过hour小时
     *
     * @param date 时间
     * @param hour 小时数
     * @return true:超时  false:未超时
     */
    public static boolean isTimeOut(Date date, Integer hour) {
        long cha = System.currentTimeMillis() - date.getTime();
        double result = cha * 1.0 / (1000 * 60 * 60);
        if (result > hour) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取自适应时间
     * @return
     */
    public static Date obtainUTC(){
        // 1、取得本地时间：
        Calendar cal = Calendar.getInstance();
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return cal.getTime();
    }
    /**
     *字符串转时间
     *
     * @param  dateStr 时间
     */
    public static Date formatYYYYMMDD(String dateStr) {

        return parse2Date(dateStr, YYYYMMDD);

    }

    /**
     * 判断某个时间是否超过hour小时
     *
     * @param  dateStr 时间
     */
    public static Date parseExelDate(String dateStr) {

        Calendar calendar = new GregorianCalendar(1900,0,-1);
        calendar.add(Calendar.DATE, Integer.valueOf(dateStr));
        return calendar.getTime();

    }
    /**
     * 时间转换为字符串  2018/08/09
     *
     * @param  date 时间
     */
    public static String format(Date date) {

        return format(date, DEFAULT);

    }

    /**
     *时间转字符串
     *
     * @param  dateStr 时间
     */
    public static Date format(String dateStr) {

        return parse2Date(dateStr, DEFAULT);

    }


    /**
     * 时间 加减
     *
     * @param  date 时间
     * @param  num  推几个小时  可以为负
     *
     */
    public static Date plusHours(Date date,Integer num) {
        Instant instant = date.toInstant();

        ZoneId zoneId = ZoneId.systemDefault();//A time-zone ID, such as {@code Europe/Paris}.(时区)
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        //小时加减
        LocalDateTime dateTime = localDateTime.plusHours(num);
        ZonedDateTime zdt = dateTime.atZone(zoneId);

        Date newDate = Date.from(zdt.toInstant());

        return newDate;

    }

}
