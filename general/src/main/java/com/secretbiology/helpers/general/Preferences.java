package com.secretbiology.helpers.general;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;

/**
 * Created by Dexter for CommonLibraries .
 * Code is released under MIT license
 * <p>
 * Abstract preference class to handle shared preferences
 */

public abstract class Preferences {
    private SharedPreferences prefs;


    public Preferences(Context context) {
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String setDefaultString() {
        return "DefaultString";
    }

    public int setDefaultInt() {
        return -1;
    }

    public float setDefaultFloat() {
        return -1;
    }

    public long setDefaultLong() {
        return -1;
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

    protected void put(String key, long value) {
        prefs.edit().putLong(key, value).apply();
    }


    protected void put(String key, Set<String> value) {
        prefs.edit().putStringSet(key, value).apply();
    }

    protected String get(String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    protected String getString(String key) {
        return prefs.getString(key, setDefaultString());
    }

    protected int get(String key, int defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    protected int getInt(String key) {
        return prefs.getInt(key, setDefaultInt());
    }

    protected boolean get(String key, boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

    protected float get(String key, float defaultValue) {
        return prefs.getFloat(key, defaultValue);
    }

    protected float getFloat(String key) {
        return prefs.getFloat(key, setDefaultFloat());
    }

    protected long get(String key, long defaultValue) {
        return prefs.getLong(key, defaultValue);
    }

    protected long getLong(String key) {
        return prefs.getLong(key, setDefaultLong());
    }

    protected Set<String> get(String key, Set<String> defaultValue) {
        return prefs.getStringSet(key, defaultValue);
    }

    protected void delete(String key) {
        prefs.edit().remove(key).apply();
    }

    protected void clearAll() {
        prefs.edit().clear().apply();
    }

}
