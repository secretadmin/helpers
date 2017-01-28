package com.secretbiology.helpers.general;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.ImageView;
import android.widget.Toast;

import com.secretbiology.helpers.general.TimeUtils.TimeLeft;

import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
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

    /**
     * Returns similarity between two strings.
     * It is output of amount of insertion, deletion or substitution required to get second string from first
     * 100 will be same strings
     *
     * @param s1  : first string
     * @param s2: second string
     * @return percentage similarity
     */

    public static int percentSimilarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2;
            shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) {
            return 100; /* both strings are zero length */
        }
        return (int) (((longerLength - editDistance(longer, shorter)) / (double) longerLength) * 100);
    }

    private static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }

    /**
     * Simple short Toast
     *
     * @param context : Context
     * @param message : Message to Display
     */
    public static void makeShortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Simple long Toast
     *
     * @param context : Context
     * @param message : Message to Display
     */

    public static void makeLongToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Converts Bitmap into circular bitmap
     *
     * @param source : Source Bitmap
     * @return : Circularize bitmap
     */

    public static Bitmap CircleTransform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    /**
     * Checks if string is an valid email address pattern
     *
     * @param email : String
     * @return : True or False
     */
    public static boolean isValidEmail(String email) {
        return email.trim().length() != 0 && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
