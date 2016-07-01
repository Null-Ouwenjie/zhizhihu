package com.ouwenjie.zhizhihu.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 */
public class TimeUtil {

    public static final SimpleDateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static final SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_FORMAT);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    public static long getCurrentUnixTimeStamp() {
        long curM = getCurrentTimeInLong();
        String curS = (curM + "").substring(0, 10);
        return Long.parseLong(curS);
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @return
     */
    public static String getCurrentTimeInString() {
        return getCurrentTimeInString(DEFAULT_FORMAT);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }

    /**
     * 按传入来的时间格式得到当前的时间的时间格式
     *
     * @return
     */
    public static String getCustomTime(String sdf) {

//        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sDateFormat = new SimpleDateFormat(sdf);
        String date = sDateFormat.format(new Date());
        return date;

    }

    /**
     * @param sdf     比如 yyyy-MM-dd hh:mm:ss
     * @param dateStr 比如 2016-02-02 10:12:12
     * @return
     */
    public static long getLongTime(String sdf, String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(sdf);
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return -1;
        } else {
            return date.getTime();
        }
    }

    /**
     * @param sdf     比如 new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
     * @param dateStr 比如 2016-02-02 10:12:12
     * @return
     */
    public static long getLongTime(SimpleDateFormat sdf, String dateStr) {
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return -1;
        } else {
            return date.getTime();
        }
    }

    /**
     * 不同日期格式转换
     *
     * @param date
     * @param originSDF 原本的日期格式
     * @param targetSDF 要转换的日期格式
     * @return
     */
    public static String transformDate(String date, String originSDF, String targetSDF) {

        try {

            SimpleDateFormat sdf = new SimpleDateFormat(originSDF, Locale.getDefault());
            Date dateTemp = sdf.parse(date);

            sdf = new SimpleDateFormat(targetSDF, Locale.getDefault());
            String tragetDate = sdf.format(dateTemp);

            return tragetDate;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 两个时间相差距离多少秒
     *
     * @param str1 时间参数 1 格式：yyyy-MM-dd hh:mm:ss
     * @param str2 时间参数 2 格式：yyyy-MM-dd hh:mm:ss
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }

            sec = diff / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return String.valueOf(sec);
    }

    /**
     * 两个时间之间相差距离多少天
     *
     * @param firstDate  时间参数 1：
     * @param secondDate 时间参数 2：
     * @return 相差天数
     */
    public static long getDistanceDays(String firstDate, String secondDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;
        Date two;
        long days = 0;
        try {
            one = df.parse(firstDate);
            two = df.parse(secondDate);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 判断两个日期先后
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return boolean true 表示beginDate小于或等于endDate
     */
    public static boolean successivelyDate(String beginDate, String endDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date bt = sdf.parse(beginDate);
            Date et = sdf.parse(endDate);

            if (bt.before(et)) {
                return true; //表示beginDate小于endDate
            } else if (bt.equals(et)) {
                return true; //表示beginDate等于endDate
            } else {
                return false;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return true;
    }
}
