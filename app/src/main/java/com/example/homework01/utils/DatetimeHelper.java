package com.example.homework01.utils;

import java.time.temporal.JulianFields;
import java.util.Calendar;
import java.util.Date;

public class DatetimeHelper {
    public static int getJulianDay(Calendar cal) {
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH); //TODO: Note January returns 0 (but maybe 1???)
        int date = cal.get(Calendar.DATE);
        return (1461 * (year + 4800 + (month - 14) / 12)) / 4
                + (367 * (month - 2 - 12 * ((month - 14) / 12))) / 12
                - (3 * ((year + 4900 + (month - 14) / 12) / 100)) / 4 + date - 32075;
    }

    public static int getJulianDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return getJulianDay(cal);
    }

    public static Calendar getCalendarFromJulianDay(int julianDay) {
        return Calendar.getInstance();
    }
}
