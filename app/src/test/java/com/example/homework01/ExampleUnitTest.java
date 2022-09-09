package com.example.homework01;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.homework01.utils.DatetimeHelper;
import com.example.homework01.utils.SQLiteCRUDHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        // assertEquals(4, 2 + 2);
        Calendar calendar = new GregorianCalendar(2000, 10 , 13);
        System.out.println(calendar.get((Calendar.YEAR)) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(DatetimeHelper.getJulianDay(calendar));
        System.out.println(DatetimeHelper.getJulianDay(new Date()));
    }

}