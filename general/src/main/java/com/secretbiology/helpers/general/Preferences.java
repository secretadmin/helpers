package com.secretbiology.helpers.general;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Dexter for CommonLibraries .
 * Code is released under MIT license
 * <p>
 * Abstract preference class to handle shared preferences
 */

abstract class Preferences {
    private SharedPreferences prefs;

    Preferences(Context context) {
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    protected void put(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    protected void put(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    protected void put(String key, float value) {
        prefs.edit().putFloat(key, value).apply();
    }

    protected void put(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    protected String get(String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    protected int get(String key, int defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    protected boolean get(String key, boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

    protected float get(String key, float defaultValue) {
        return prefs.getFloat(key, defaultValue);
    }

    protected void delete(String key) {
        prefs.edit().remove(key).apply();
    }

    void clearAll() {
        prefs.edit().clear().apply();
    }

}
