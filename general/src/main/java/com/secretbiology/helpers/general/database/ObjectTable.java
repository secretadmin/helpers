package com.secretbiology.helpers.general.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;

import com.google.gson.Gson;

public class ObjectTable {


    private SQLiteDatabase db;
    private Operator operator;
    private Gson gson = new Gson();
    private static final String ColumnName = "SerializedStrings";
    private String tableName;

    public ObjectTable(Operator operator, String tableName) {
        this.tableName = tableName;
        this.operator = operator;
        this.db = operator.getWritableDatabase();
    }

    public static void make(String tableName, SQLiteDatabase db) {
        String tableString = "CREATE TABLE " + tableName + " ( id INTEGER PRIMARY KEY, " + ColumnName + " TEXT)";
        db.execSQL(tableString);
        Log.i("Database", "Object Table " + tableName + " created");
    }

    public long add(Object object) {
        ContentValues values = new ContentValues();
        values.put(ColumnName, gson.toJson(object));
        long id = db.insert(tableName, null, values);
        operator.closeDatabase();
        return id;
    }

    public SparseArray<Object> getAll(Class cls) {
        String selectQuery = "SELECT  * FROM " + tableName;
        SparseArray<Object> list = new SparseArray<Object>();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                list.put(c.getInt(0), gson.fromJson(c.getString(1), cls));
            } while (c.moveToNext());
        }
        c.close();
        operator.closeDatabase();
        return list;
    }

    public long update(int id, Object object) {
        ContentValues values = new ContentValues();
        values.put(ColumnName, gson.toJson(object));
        long updated = db.update(tableName, values, " id = ?",
                new String[]{String.valueOf(id)});
        operator.closeDatabase();
        return updated;
    }

    public void delete(int id) {
        db.delete(tableName, "id = ?", new String[]{String.valueOf(id)});
        operator.closeDatabase();
    }

    public void clearAll() {
        db.execSQL("DELETE FROM " + tableName);
        operator.closeDatabase();
    }

    public Object get(int id, Class cls) {
        Cursor cursor = db.query(tableName, new String[]{"id", ColumnName}, " id = ?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        Object object = null;
        if (cursor != null) {
            cursor.moveToFirst();
            object = gson.fromJson(cursor.getString(1), cls);
            cursor.close();
        }
        operator.closeDatabase();
        return object;
    }

    public Cursor executeCustom(String query) {
        Log.i("ObjectTable", "Close the cursor after using executeCustom.");
        return db.rawQuery(query, null);
    }

    public void drop() {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        Log.i("ObjectTable", "Table" + tableName + " dropped.");
    }

    public static void drop(String tableName, SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        Log.i("ObjectTable", "Table" + tableName + " dropped.");
    }

    public void removeOldEntries(long allowedEntries) {
        Cursor c = db.rawQuery("SELECT * FROM " + tableName, null);
        long allCount = c.getCount();
        c.close();
        if (allCount > allowedEntries) {
            allowedEntries = allCount - allowedEntries;
            String Query = "DELETE FROM " + tableName + " WHERE id IN (SELECT id FROM " + tableName + " ORDER BY id ASC LIMIT " + allowedEntries + ")";
            db.execSQL(Query);
        }
        operator.closeDatabase();
    }
}
