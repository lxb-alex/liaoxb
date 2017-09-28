package com.liaoxb.common.utils;

import org.apache.commons.lang.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/9/27 14:37:37
 */
public class DateUtil extends DateFormatUtils {

    /**
     * 将Long格式时间转换为 Date 时间格式<br/>
     * @param longDate 一串纯数字格式的时间
     * @return
     */
    public static Date longToDate(long longDate){
        Timestamp ts = new Timestamp(longDate);
        Date date = ts;
        return date;
    }

    /**
     * @param date 格式为Thu Dec 17 00:00:00 CST 2015的时间（TimeStamp）
     * @return 正常时间格式（date）
     */
    public static Date timeStampToDate(Date date){
        long longDate = date.getTime();
        Timestamp ts = new Timestamp(longDate);
        Date d = ts;
        return d;
    }

    /**
     * 将string时间类型转换 “YYYY-MM-dd”格式输出
     * @param timestr {nanos=0, time=1452873600000, minutes=0, seconds=0, hours=0, month=0, year=116, timezoneOffset=-480, day=6, date=16}
     * @return YYYY-MM-dd 格式的时间字符串
     */
    public static String strTostr(String timestr){
        timestr = timestr.substring(timestr.indexOf("e=")+2, timestr.indexOf(", minutes"));
        long longDate = Long.valueOf(timestr);
        Date date = DateUtil.longToDate(longDate);
        String dateStr = DateUtil.dateToStr(date, "YYYY-MM-dd");
        return dateStr;
    }

    /**
     * 将日期转换为字符串
     * @param date 时间
     * @param format 转换后显示格式
     * @return 格式化后的时间字符串
     */
    public static String dateToStr(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date).toString();
    }

    /**
     * 将字符串时间转为时间格式
     * @param dateStr 时间格式的字符串
     */
    public static Date strToDate(String dateStr) {
        Date dateTemp = null;
        try {
            dateStr = dateStr.substring(0, 10);
            StringTokenizer token = new StringTokenizer(dateStr, "-");
            int year = Integer.parseInt(token.nextToken());
            int month = Integer.parseInt(token.nextToken()) - 1;
            int day = Integer.parseInt(token.nextToken());
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day);
            dateTemp = cal.getTime();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return dateTemp;
    }

}
