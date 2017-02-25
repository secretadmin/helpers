package com.secretbiology.helpers.general.TimeUtils;

import com.secretbiology.helpers.general.General;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class TimeUtils {

    public static final int MIN_YEAR = 1900;
    public static final int MAX_YEAR = 9999;

    public static int getSeconds(Date startDate, Date endDate) {
        return (int) TimeUnit.MILLISECONDS.toSeconds(startDate.getTime() - endDate.getTime());
    }

    public static int getMinutes(Date startDate, Date endDate) {
        return (int) TimeUnit.MILLISECONDS.toMinutes(startDate.getTime() - endDate.getTime());
    }

    public static int getHours(Date startDate, Date endDate) {
        return (int) TimeUnit.MILLISECONDS.toHours(startDate.getTime() - endDate.getTime());
    }

    public static int getDays(Date startDate, Date endDate) {
        return (int) TimeUnit.MILLISECONDS.toDays(startDate.getTime() - endDate.getTime());
    }

    public static TimeLeft getTimeLeft(Date startDate, Date endDate) {
        return new TimeLeft(startDate, endDate);
    }

    /**
     * Converts simple time into readable format
     */
    public static String makeReadableTime(Date statDate, Date endDate) {
        TimeLeft timeLeft = new TimeLeft(statDate, endDate);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        int Minute = timeLeft.getMinutes();
        int Hours = timeLeft.getHours();
        int Days = timeLeft.getDays();
        if (Days == 0) {
            if (Hours == 0) {
                if (Minute == 0) {
                    return "few seconds ago";
                } else if (Minute == 1) {
                    return "a minute ago";
                } else return Minute + " minutes ago";
            } else if (Hours == 1) {
                return "an hour ago";
            } else return Hours + " hours ago";
        } else if (Days == 1) {
            return "yesterday";
        } else if (Days > 1 && Days < 6) {
            return Days + " days ago";
        } else return formatter.format(statDate);
    }

    public static Calendar getRandomCalender() {
        return getRandCalWithinRange(MIN_YEAR, MAX_YEAR);
    }

    public static Calendar getRandCalWithinYears(int yearTolerance) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return getRandCalWithinRange(currentYear - yearTolerance, currentYear + yearTolerance);
    }

    public static Calendar getAnyDateInPastYears(int numberOfYears) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return getRandCalWithinRange(currentYear - numberOfYears, currentYear);
    }


    public static Calendar getRandCalWithinRange(int minYear, int maxYear) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, General.randInt(minYear, maxYear));
        cal.set(Calendar.DAY_OF_YEAR, General.randInt(1, 366));
        cal.set(Calendar.HOUR_OF_DAY, General.randInt(1, 24));
        cal.set(Calendar.MINUTE, General.randInt(1, 60));
        cal.set(Calendar.SECOND, General.randInt(1, 60));
        return cal;
    }

}
