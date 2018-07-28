package com.houchins.andy.tracker.model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Helper for date conversions.
 */

public class DateHelper {
    private static final long MS_PER_DAY = 1000 * 60 * 1440;

    public static int getDaysSinceEpoch(int year, int month, int day) {
        Date d = getDate(year, month, day);
        return (int) ((d.getTime() + (MS_PER_DAY / 2)) / MS_PER_DAY);
    }

    public static int getDaysSinceEpoch(Date date) {
        return (int) ((date.getTime() + (MS_PER_DAY / 2)) / MS_PER_DAY);
    }

    public static int getDaysSinceEpoch() {
        // TODO: needs to be fixed to return days based on current timezone
        return (int) ((System.currentTimeMillis() + (MS_PER_DAY / 2)) / MS_PER_DAY);
    }

    public static Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(year, month - 1, day, 12, 0, 0); // zero-based month
        calendar.set(Calendar.MILLISECOND, 0);
        return new Date(calendar.getTime().getTime());
    }

    public static Date getDate(int days) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(days * MS_PER_DAY);
        return new Date(calendar.getTime().getTime());
    }

    public Date getDate() {
        return getDate(getDaysSinceEpoch());
    }
}
