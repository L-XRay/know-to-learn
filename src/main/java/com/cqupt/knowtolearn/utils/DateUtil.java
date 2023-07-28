package com.cqupt.knowtolearn.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ray
 * @date 2022/5/25 00:30
 */
public class DateUtil {
    /**
     * 日期格式化string
     *
     * @param date
     * @return
     */
    public static String getDateString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdfDatetime = new SimpleDateFormat("yyyy-MM-dd");
        return sdfDatetime.format(date);
    }

    /**
     * 日期格式化string 时分秒
     *
     * @param date
     * @return
     */
    public static String getDateTime(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdfDatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdfDatetime.format(date);
    }

    public static Date strToDate(String dateStr, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException("日期转换失败");
        }
    }

    public static Date timestampToDate(long timestamp) {
        return new Date(timestamp * 1000);
    }
}
