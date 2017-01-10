package com.secretbiology.helpers.general;

public class Log {

    /**
     * Shows testing log
     */
    public static void testLog() {
        android.util.Log.i("SecretBiology", "Test Log");
    }

    /**
     * Shows testing log with message
     *
     * @param message : Message to display
     */

    public static void testLog(String message) {
        if (message == null) {
            android.util.Log.i("SecretBiology", "Submitted message is null");
        } else if (message.length() == 0) {
            android.util.Log.i("SecretBiology", "Submitted message is with 0 length");
        } else {
            android.util.Log.i("SecretBiology", message);
        }
    }

    /**
     * Shows test log with integer
     *
     * @param number : Number to show
     */

    public static void testLog(int number) {
        android.util.Log.i("SecretBiology", String.valueOf(number));
    }

    /**
     * Shows test log with double
     *
     * @param number : Double number
     */
    public static void testLog(double number) {
        android.util.Log.i("SecretBiology", String.valueOf(number));
    }

    /***
     * Shows test log with class
     *
     * @param cls : Desired class
     */
    public static void testLog(Class cls) {
        android.util.Log.i("SecretBiology", cls.toString());
    }

    public static void errorLog(String message) {
        if (message == null) {
            android.util.Log.e("SecretBiology", "Submitted message is null");
        } else if (message.length() == 0) {
            android.util.Log.e("SecretBiology", "Submitted message is with 0 length");
        } else {
            android.util.Log.e("SecretBiology", message);
        }

    }

}
