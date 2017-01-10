package com.secretbiology.helpers.general.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Operator extends SQLiteOpenHelper {

    Context context;

    public Operator(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public Operator(Context context, String name, int version) {
        super(context, name, null, version);
        this.context = context;
    }

    //Force to override
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    //Force to override
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    private static Operator instance;

    private int mOpenCounter;

    public SQLiteDatabase mDatabase;


    public static synchronized Operator getInstance(Context context, String name, int version) {
        if (instance == null) {
            instance = new Operator(context.getApplicationContext(), name, version);
        }
        return instance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        mOpenCounter++;
        if (mOpenCounter == 1) {
            // Opening new database
            mDatabase = instance.getWritableDatabase();
        }
        return mDatabase;
    }

    synchronized void closeDatabase() {
        mOpenCounter--;
        if (mOpenCounter == 0) {
            // Closing database
            mDatabase.close();
        }
    }


}
