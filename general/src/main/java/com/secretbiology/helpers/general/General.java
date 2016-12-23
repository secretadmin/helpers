package com.secretbiology.helpers.general;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.ImageView;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * NCBSinfo © 2016, Secret Biology
 * https://github.com/NCBSinfo/NCBSinfo
 * Created by Rohit Suratekar on 11-08-16.
 */
public class General {

    /**
     * Checks if network is available.
     * Warning: it won't check if internet is available or not.
     *
     * @param context : application context
     * @return : true if network is available
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Checks if user is using proxy
     *
     * @return true if there is any proxy on the network
     */
    public static boolean isOnProxy() {
        List<Proxy> proxyList = ProxySelector.getDefault().select(URI.create("http://www.google.com"));
        return proxyList.size() <= 0 || proxyList.get(0).address() != null;
    }

    /**
     * Get current proxy address
     *
     * @return : returns proxy address of network if it is on proxy
     */
    public static String getProxyAddress() {
        List<Proxy> proxyList = ProxySelector.getDefault().select(URI.create("http://www.google.com"));
        if (proxyList.size() <= 0 || proxyList.get(0).address() != null) {
            return proxyList.get(0).address().toString();
        } else return "null";
    }

    /**
     * Generates random integer within given range
     *
     * @param min : minimum value
     * @param max : maximum value
     * @return : random integer between min and max
     */
    public static int randInt(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    /**
     * @return : Current timestamp
     */
    public static String timeStamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss a d MMM yy", Locale.getDefault());
        return formatter.format(new Date());
    }

    /**
     * Creates timestamp with given format
     *
     * @param format : Time Format
     * @return : Returns timestamp in given time format
     */
    public static String timeStamp(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        return formatter.format(new Date());
    }

    /**
     * Gets color with compatibility issues
     *
     * @param context: For getting resources
     * @param color    : color resource
     * @return : int color
     */
    public static int getColor(Context context, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(color, context.getTheme());
        } else {
            return context.getResources().getColor(color);
        }
    }

    /**
     * Converts simple time into readable format
     *
     * @param date : Date
     * @return : Readable time in String
     */
    public static String makeReadableTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        DateTime timestamp = new DateTime(date);
        DateTime currentTime = new DateTime(new Date());
        Interval interval = new Interval(timestamp, currentTime);
        int Minute = interval.toPeriod().getMinutes();
        int Hours = interval.toPeriod().getHours();
        int Days = interval.toPeriod().getDays();
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
        } else return formatter.format(date);
    }

    private static final String CharSpace = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static SecureRandom randomFunc = new SecureRandom();

    /**
     * Returns random alpha-numerical string
     *
     * @param len : Length of string
     * @return random alpha-numerical string of given length
     */

    public static String randomString(int len) {
        StringBuilder builder = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            builder.append(CharSpace.charAt(randomFunc.nextInt(CharSpace.length())));
        }
        return builder.toString();
    }

    /**
     * Makes Imageview grayscale
     *
     * @param view : Imageview which will be converted into Grayscale (black and white)
     */

    public static void makeGrayScale(ImageView view) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        view.setColorFilter(filter);
    }

}
