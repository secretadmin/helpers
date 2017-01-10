package com.secretbiology.helpers.general;

import android.app.Activity;

import java.util.Arrays;

public class Log {

    private final static String TAG = "SecretBiology";

    //Test Logs

    public static void test() {
        android.util.Log.d(TAG, "Test Log");
    }

    public static void test(Object o) {
        android.util.Log.d(TAG, getString(o));
    }

    public static void test(Activity activity) {
        android.util.Log.d(activity.getLocalClassName(), "Test Log");
    }

    public static void test(Activity activity, Object o) {
        android.util.Log.d(activity.getLocalClassName(), getString(o));
    }

    public static void test(Object... objects) {
        String fullString = "";
        for (Object o : objects) {
            fullString = fullString + getString(o) + "  ";
        }
        android.util.Log.d(TAG, fullString);
    }


    public static void test(String tag, Object... objects) {
        String fullString = "";
        for (Object o : objects) {
            fullString = fullString + getString(o) + "  ";
        }
        android.util.Log.d(tag, fullString);
    }

    public static void test(Activity activity, Object... objects) {
        String fullString = "";
        for (Object o : objects) {
            fullString = fullString + getString(o) + "  ";
        }
        android.util.Log.d(activity.getLocalClassName(), fullString);
    }


    //Information Logs


    public static void inform() {
        android.util.Log.i(TAG, "Test Log");
    }

    public static void inform(Object o) {
        android.util.Log.i(TAG, getString(o));
    }

    public static void inform(Activity activity) {
        android.util.Log.i(activity.getLocalClassName(), "Test Log");
    }

    public static void inform(Activity activity, Object o) {
        android.util.Log.i(activity.getLocalClassName(), getString(o));
    }

    public static void inform(Object... objects) {
        String fullString = "";
        for (Object o : objects) {
            fullString = fullString + getString(o) + "  ";
        }
        android.util.Log.i(TAG, fullString);
    }


    public static void inform(String tag, Object... objects) {
        String fullString = "";
        for (Object o : objects) {
            fullString = fullString + getString(o) + "  ";
        }
        android.util.Log.i(tag, fullString);
    }

    public static void inform(Activity activity, Object... objects) {
        String fullString = "";
        for (Object o : objects) {
            fullString = fullString + getString(o) + "  ";
        }
        android.util.Log.i(activity.getLocalClassName(), fullString);
    }

    //Error Logs


    public static void error() {
        android.util.Log.e(TAG, "Oops! Something went wrong");
    }

    public static void error(Object o) {
        android.util.Log.e(TAG, getString(o));
    }

    public static void error(Activity activity) {
        android.util.Log.e(activity.getLocalClassName(), "Oops! Something went wrong");
    }

    public static void error(Activity activity, Object o) {
        android.util.Log.e(activity.getLocalClassName(), getString(o));
    }


    public static void error(Object... objects) {
        String fullString = "";
        for (Object o : objects) {
            fullString = fullString + getString(o) + "  ";
        }
        android.util.Log.e(TAG, fullString);
    }


    public static void error(String tag, Object... objects) {
        String fullString = "";
        for (Object o : objects) {
            fullString = fullString + getString(o) + "  ";
        }
        android.util.Log.e(tag, fullString);
    }

    public static void error(Activity activity, Object... objects) {
        String fullString = "";
        for (Object o : objects) {
            fullString = fullString + getString(o) + "  ";
        }
        android.util.Log.e(activity.getLocalClassName(), fullString);
    }


    private static String getString(Object o) {
        if (o == null) {
            return "Object is null";
        } else if (o instanceof String) {
            if (((String) o).length() == 0) {
                return "Submitted string length is 0";
            } else {
                return (String) o;
            }
        } else if (o instanceof String[]) {
            return Arrays.toString((String[]) o);
        } else if (o instanceof int[]) {
            return Arrays.toString((int[]) o);
        } else if (o instanceof float[]) {
            return Arrays.toString((float[]) o);
        } else if (o instanceof double[]) {
            return Arrays.toString((double[]) o);
        } else {
            return String.valueOf(o);
        }
    }

}
