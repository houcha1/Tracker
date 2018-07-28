package com.houchins.andy.tracker.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Date;

/**
 * Created by Lisa on 6/2/2018.
 */
@RunWith(JUnit4.class)
public class DateHelperTest {

    @Test
    public void testGetDays() {
        long days = DateHelper.getDaysSinceEpoch(1981, 7, 31);
        Date d1 = DateHelper.getDate(1981, 7, 31);
        Date d2 = DateHelper.getDate((int) days);
        System.out.println(days);
        System.out.println(d1);
        System.out.println(d2);
        System.out.println(DateHelper.getDate((int) days + 6));
        System.out.println(DateHelper.getDate(200001));
    }
}