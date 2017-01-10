package com.secretbiology.helpers.general;

/**
 * Simple class which converts any date format to Date
 * Only thing you have to tell is if Month is first or Date is first
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateConverter {


    private static final String[][] datePatterns = {
            {"^\\d{1,2}:\\d{2}:\\d{2}\\s(?i)(am|pm)\\s\\d{1,2}\\s[a-z]{3}\\s\\d{2}$", "hh:mm:ss a dd MMM yy"},
            {"^\\d{1,2}:\\d{2}$", "HH:mm"},
            {"^\\d{1,2}:\\d{2}\\s(?i)(am|pm)$", "hh:mm a"},
            {"^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$", "HH:mm:ss"},

            {"^\\d{1,2}/\\d{1,2}/\\d{4}$", "dd/MM/yyyy"},
            {"^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/dd/MM"},
            {"^\\d{1,2}/(?i)[a-z]{3}/\\d{4}$", "dd/MMM/yyyy"},
            {"^\\d{1,2}/(?i)[a-z]{4,}/\\d{4}$", "dd/MMMM/yyyy"},
            {"^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "dd/MM/yyyy HH:mm"},
            {"^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/dd/MM HH:mm"},
            {"^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd/MM/yyyy HH:mm:ss"},
            {"^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/dd/MM HH:mm:ss"},
            {"^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}\\s(?i)(am|pm)$", "dd/MM/yyyy hh:mm a"},
            {"^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}\\s(?i)(am|pm)$", "dd/MM/yyyy hh:mm:ss a"},


            {"^\\d{4}\\s\\d{1,2}\\s\\d{1,2}$", "yyyy dd MM"},
            {"^\\d{1,2}\\s(?i)[a-z]{3}\\s\\d{4}$", "dd MMM yyyy"},
            {"^\\d{1,2}\\s(?i)[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy"},
            {"^\\d{1,2}\\s\\d{1,2}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MM yyyy HH:mm"},
            {"^\\d{4}\\s\\d{1,2}\\s\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy dd MM HH:mm"},
            {"^\\d{1,2}\\s\\d{1,2}\\s\\d{4}\\s\\d{1,2}:\\d{2}\\s(?i)(am|pm)$", "dd MM yyyy hh:mm a"},
            {"^\\d{4}\\s\\d{1,2}\\s\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy dd MM HH:mm:ss"},
            {"^\\d{1,2}\\s\\d{1,2}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MM yyyy HH:mm:ss"},
            {"^\\d{1,2}\\s\\d{1,2}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}\\s(?i)(am|pm)$", "dd MM yyyy hh:mm:ss a"},

            {"^\\d{8}", "ddMMyyyy"},
            {"^\\d{1,2}\\\\\\d{1,2}\\\\\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd\\MM\\yyyy HH:mm:ss"},
            {"^\\d{1,2}\\\\\\d{1,2}\\\\\\d{4}\\s\\d{1,2}:\\d{2}$", "dd\\MM\\yyyy HH:mm"},
            {"^\\d{1,2}\\\\\\d{1,2}\\\\\\d{4}$", "dd\\MM\\yyyy"},
            {"^\\d{4}\\\\\\d{1,2}\\\\\\d{1,2}$", "yyyy\\dd\\MM"},
    };

    /**
     * Main Date Parser
     *
     * @param mode       : Is month first of date
     * @param dateString : string which you want to convert
     * @return : Formatted String
     */

    private static String dateParser(ConverterMode mode, String dateString) {
        Boolean replaceDash = false;
        if (dateString.contains("-")) {
            dateString = dateString.replace("-", "/");
            replaceDash = true;
        }
        for (String[] currentPattern : datePatterns) {
            Pattern p = Pattern.compile(currentPattern[0]);
            Matcher m = p.matcher(dateString);
            if (m.find()) {
                String returnPattern = currentPattern[1];

                if (mode.equals(ConverterMode.MONTH_FIRST)) {
                    if (!returnPattern.contains("MMM") && !returnPattern.contains("MMMM")) {
                        returnPattern = returnPattern.replaceAll("\\bMM\\b", "XX");
                        returnPattern = returnPattern.replaceAll("\\bdd\\b", "MM");
                        returnPattern = returnPattern.replaceAll("\\bXX\\b", "dd");
                    }
                }
                if (replaceDash) {
                    return returnPattern.replace("/", "-");
                } else {
                    return returnPattern;
                }
            }
        }
        return "null";
    }


    /**
     * Converts string of any format to Date
     *
     * @param mode       : Date first or Month First
     * @param dateString : String you want to Convert
     * @return : Date objec
     * @throws ParseException
     */

    public static Date convertToDate(ConverterMode mode, String dateString) throws ParseException {
        String format = dateParser(mode, dateString);
        Date now = new Date();
        if (format.toLowerCase().contains("h") && format.toLowerCase().contains("y")) {
            return new SimpleDateFormat(format, Locale.getDefault()).parse(dateString);
        } else if (format.toLowerCase().contains("h") && !format.toLowerCase().contains("y")) {
            String dateFormat = new SimpleDateFormat("dd/MM/yyyy ", Locale.getDefault()).format(now);

            dateFormat = dateFormat + dateString;
            return new SimpleDateFormat("dd/MM/yyyy " + format, Locale.getDefault()).parse(dateFormat);
        } else if (!format.toLowerCase().contains("h") && format.toLowerCase().contains("y")) {
            String timeFormat = new SimpleDateFormat(" HH:mm:ss", Locale.getDefault()).format(now);
            timeFormat = dateString + timeFormat;
            return new SimpleDateFormat(format + " HH:mm:ss", Locale.getDefault()).parse(timeFormat);
        } else {
            return new SimpleDateFormat(format, Locale.getDefault()).parse(dateString);
        }
    }

    /**
     * Converts string to calender
     *
     * @param mode       : Month first or date
     * @param dateString : String you want to convert
     * @return : Calender object
     * @throws ParseException
     */
    public static Calendar convertToCalender(ConverterMode mode, String dateString) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(convertToDate(mode, dateString));
        return calendar;
    }

    /**
     * Converts calender into given format
     *
     * @param calendar: Calender object
     * @param format    : format in which you need string
     * @return : String with given format
     */

    public static String convertToString(Calendar calendar, String format) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(new Date(calendar.getTimeInMillis()));
    }

    /**
     * Converts Date into given format
     *
     * @param date:  Date object
     * @param format : format in which you need string
     * @return : String with given format
     */

    public static String convertToString(Date date, String format) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(date);
    }

    /**
     * Converts Calendar object to Date
     *
     * @param calendar: calendar object
     * @return : date
     */
    public static Date toDate(Calendar calendar) {
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * Converts Date Object to calendar
     *
     * @param date : date object
     * @return: calender
     */
    public static Calendar toCalender(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        return cal;
    }

    /**
     * @param calendar : Input Calendar
     * @param newTime  : New Time
     * @return : Old calendar with time changed
     * @throws ParseException
     */
    public static Calendar changeTime(Calendar calendar, String newTime) throws ParseException {
        Calendar currentCal = convertToCalender(ConverterMode.DATE_FIRST, newTime);
        calendar.set(Calendar.HOUR_OF_DAY, currentCal.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, currentCal.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, currentCal.get(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, currentCal.get(Calendar.MILLISECOND));
        return calendar;
    }

    /**
     * @param calendar: Input Calendar
     * @param mode      : Mode
     * @param newDate   : New Date
     * @return : New calendar with date changed
     * @throws ParseException
     */
    public static Calendar changeDate(Calendar calendar, ConverterMode mode, String newDate) throws ParseException {
        Calendar currentCal = convertToCalender(mode, newDate);
        calendar.set(Calendar.YEAR, currentCal.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, currentCal.get(Calendar.MONTH));
        calendar.set(Calendar.DATE, currentCal.get(Calendar.DATE));
        return calendar;
    }

    /**
     * @param date    : Input date
     * @param newTime : New time
     * @return : Date object with time changed
     * @throws ParseException
     */

    public static Date changeTime(Date date, String newTime) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return new Date(changeTime(calendar, newTime).getTimeInMillis());
    }

    /**
     * @param date    : Old date
     * @param mode    : Mode
     * @param newDate : New Date
     * @return : Date object with date changed
     * @throws ParseException
     */
    public static Date changeDate(Date date, ConverterMode mode, String newDate) throws ParseException {
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(date);
        return new Date(changeDate(currentCal, mode, newDate).getTimeInMillis());
    }

    /**
     * @param calList: Calender List
     * @return : Sorted List
     */

    public static List<Calendar> sortCalendars(List<Calendar> calList) {
        Collections.sort(calList, new Comparator<Calendar>() {
            @Override
            public int compare(Calendar c1, Calendar c2) {
                return c1.compareTo(c2);
            }
        });
        return calList;
    }

    /**
     * @param dateList : List of Dates
     * @return sorted List
     */

    public static List<Date> sortDates(List<Date> dateList) {
        Collections.sort(dateList, new Comparator<Date>() {
            @Override
            public int compare(Date c1, Date c2) {
                return c1.compareTo(c2);
            }
        });
        return dateList;
    }

    /**
     * @param mode       : Mode of DateFormat
     * @param stringList : List of String
     * @return : Sorted List
     */

    public static List<String> sortStrings(final ConverterMode mode, List<String> stringList) {
        Collections.sort(stringList, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                try {
                    return convertToCalender(mode, s1).compareTo(convertToCalender(mode, s2));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        return stringList;
    }


}
