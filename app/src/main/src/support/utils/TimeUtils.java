package com.linky.heaven.support.utils;

import java.util.Calendar;

/**
 * Created by linky on 16-2-4.
 */
public class TimeUtils {

    public static boolean isSameDay(long now, long lastTime) {

        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTimeInMillis(now);

        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTimeInMillis(lastTime);

        return nowCalendar.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR)
                && nowCalendar.get(Calendar.MONTH) == dateCalendar.get(Calendar.MONTH)
                && nowCalendar.get(Calendar.DATE) == dateCalendar.get(Calendar.DATE);
    }
}
