package com.secretbiology.helpers.general;

/**
 * Simple class which converts any date format to Date
 * Only thing you have to tell is if Month is first or Date is first
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateConverter {


    private static final String[][] datePatterns = {
            {"\\d{1,2}:\\d{2}:\\d{2}\\s(?i)(am|pm)\\s\\d{1,2}\\s[a-z]{3}\\s\\d{2}", "hh:mm:ss a dd MMM yy"},
            {"\\d{1,2}:\\d{2}", "HH:mm"},
            {"\\d{1,2}:\\d{2}\\s(?i)(am|pm)", "hh:mm a"},
            {"([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$", "HH:mm:ss"},

            {"\\d{1,2}/\\d{1,2}/\\d{4}", "dd/MM/yyyy"},
            {"\\d{4}/\\d{1,2}/\\d{1,2}", "yyyy/dd/MM"},
            {"\\d{1,2}/(?i)[a-z]{3}/\\d{4}", "dd/MMM/yyyy"},
            {"\\d{1,2}/(?i)[a-z]{4,}/\\d{4}", "dd/MMMM/yyyy"},
            {"\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}", "dd/MM/yyyy HH:mm"},
            {"\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}", "yyyy/dd/MM HH:mm"},
            {"\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}", "dd/MM/yyyy HH:mm:ss"},
            {"\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}", "yyyy/dd/MM HH:mm:ss"},
            {"\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}\\s(?i)(am|pm)", "dd/MM/yyyy hh:mm a"},
            {"\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}\\s(?i)(am|pm)", "dd/MM/yyyy hh:mm:ss a"},


            {"\\d{4}\\s\\d{1,2}\\s\\d{1,2}", "yyyy dd MM"},
            {"\\d{1,2}\\s(?i)[a-z]{3}\\s\\d{4}", "dd MMM yyyy"},
            {"\\d{1,2}\\s(?i)[a-z]{4,}\\s\\d{4}", "dd MMMM yyyy"},
            {"\\d{1,2}\\s\\d{1,2}\\s\\d{4}\\s\\d{1,2}:\\d{2}", "dd MM yyyy HH:mm"},
            {"\\d{4}\\s\\d{1,2}\\s\\d{1,2}\\s\\d{1,2}:\\d{2}", "yyyy dd MM HH:mm"},
            {"\\d{1,2}\\s\\d{1,2}\\s\\d{4}\\s\\d{1,2}:\\d{2}\\s(?i)(am|pm)", "dd MM yyyy hh:mm a"},
            {"\\d{4}\\s\\d{1,2}\\s\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}", "yyyy dd MM HH:mm:ss"},
            {"\\d{1,2}\\s\\d{1,2}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}", "dd MM yyyy HH:mm:ss"},
            {"\\d{1,2}\\s\\d{1,2}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}\\s(?i)(am|pm)", "dd MM yyyy hh:mm:ss a"},

            {"\\d{8}", "ddMMyyyy"},
            {"\\d{1,2}\\\\\\d{1,2}\\\\\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}", "dd\\MM\\yyyy HH:mm:ss"},
            {"\\d{1,2}\\\\\\d{1,2}\\\\\\d{4}\\s\\d{1,2}:\\d{2}", "dd\\MM\\yyyy HH:mm"},
            {"\\d{1,2}\\\\\\d{1,2}\\\\\\d{4}", "dd\\MM\\yyyy"},
            {"\\d{4}\\\\\\d{1,2}\\\\\\d{1,2}", "yyyy\\dd\\MM"},
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
}
