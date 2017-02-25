package com.secretbiology.helpers.general.TimeUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Code from : https://www.mkyong.com/java/java-time-elapsed-in-days-hours-minutes-seconds/
 */

public class TimeLeft {
    private Date startDate;
    private Date endDate;
    private int years;
    private int months;
    private int days;
    private int hours;
    private int minutes;
    private int seconds;
    private int milliseconds;

    public TimeLeft(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        long diff = Math.abs(endDate.getTime() - startDate.getTime());
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long monthsInMilli = daysInMilli * 30;
        long yearsInMilli = monthsInMilli * 12;

        long elapsedYears = diff / yearsInMilli;
        diff = diff % yearsInMilli;
        long elapsedMonths = diff / monthsInMilli;
        diff = diff % monthsInMilli;
        long elapsedDays = diff / daysInMilli;
        diff = diff % daysInMilli;
        long elapsedHours = diff / hoursInMilli;
        diff = diff % hoursInMilli;
        long elapsedMinutes = diff / minutesInMilli;
        diff = diff % minutesInMilli;
        long elapsedSeconds = diff / secondsInMilli;

        this.years = (int) elapsedYears;
        this.months = (int) elapsedMonths;
        this.days = (int) elapsedDays;
        this.minutes = (int) elapsedMinutes;
        this.seconds = (int) elapsedSeconds;
        this.hours = (int) elapsedHours;
        this.milliseconds = (int) diff;
    }


    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Calendar getStartCalendar() {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(this.startDate);
        return c1;
    }

    public Calendar getEndCalendar() {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(this.endDate);
        return c1;
    }

    public int getYears() {
        return years;
    }

    public int getMonths() {
        return months;
    }

    public int getDays() {
        return days;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMilliseconds() {
        return milliseconds;
    }
}
