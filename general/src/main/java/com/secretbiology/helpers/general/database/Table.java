package com.secretbiology.helpers.general.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private SQLiteDatabase db;
    private DatabaseManager databaseManager;
    private String tableName;
    private List<Column> columns;

    public Table(DatabaseManager databaseManager, String tableName, List<Column> columns) {
        this.tableName = tableName;
        this.databaseManager = databaseManager;
        this.db = databaseManager.getWritableDatabase();
        this.columns = columns;
    }

    public static void make(String tableName, SQLiteDatabase db, List<Column> columns) {
        String tableString = "CREATE TABLE " + tableName + " ( ";
        for (int i = 0; i < columns.size() - 1; i++) {
            tableString = tableString + columns.get(i).getName() + " " + String.valueOf(columns.get(i).getType()).replace("_", " ") + ",";
        }
        tableString = tableString + columns.get(columns.size() - 1).getName() + " "
                + String.valueOf(columns.get(columns.size() - 1).getType()).replace("_", " ") + ")";
        db.execSQL(tableString);
    }

    public long add(Row row) {
        ContentValues values = new ContentValues();
        for (Column c : row.getColumns()) {
            if (c.getValue() instanceof String) {
                values.put(c.getName(), (String) c.getValue());
            } else if (c.getValue() instanceof Integer) {
                values.put(c.getName(), (int) c.getValue());
            } else if (c.getValue() instanceof Double) {
                values.put(c.getName(), (double) c.getValue());
            } else {
                values.put(c.getName(), new Gson().toJson(c.getValue()));
            }
        }

        long id = db.insert(tableName, null, values);
        databaseManager.closeDatabase();
        return id;
    }

    public long add(List<Column> columnList) {
        ContentValues values = new ContentValues();
        for (Column c : columnList) {
            if (c.getValue() instanceof String) {
                values.put(c.getName(), (String) c.getValue());
            } else if (c.getValue() instanceof Integer) {
                values.put(c.getName(), (int) c.getValue());
            } else if (c.getValue() instanceof Double) {
                values.put(c.getName(), (double) c.getValue());
            } else {
                values.put(c.getName(), new Gson().toJson(c.getValue()));
            }
        }
        long id = db.insert(tableName, null, values);
        databaseManager.closeDatabase();
        return id;
    }


    public List<Row> getAll() {
        String selectQuery = "SELECT  * FROM " + tableName;
        List<Row> list = new ArrayList<>();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                List<Column> tempList = new ArrayList<>();
                for (int i = 0; i < columns.size(); i++) {
                    Column column = new Column(columns.get(i).getName(), columns.get(i).getType());
                    switch (columns.get(i).getType()) {
                        case INTEGER:
                            column.setValue(c.getInt(i));
                            break;
                        case INTEGER_PRIMARY_KEY:
                            column.setValue(c.getInt(i));
                            break;
                        case TEXT:
                            column.setValue(c.getString(i));
                            break;
                        case REAL:
                            column.setValue(c.getDouble(i));
                            break;
                        default:
                            column.setValue(c.getBlob(i));
                    }
                    tempList.add(column);
                }

                list.add(new Row(c.getInt(0), tempList));
            } while (c.moveToNext());
        }
        c.close();
        databaseManager.closeDatabase();
        return list;
    }

    public long update(Row row) {
        ContentValues values = new ContentValues();
        for (Column c : row.getColumns()) {
            if (c.getValue() instanceof String) {
                values.put(c.getName(), (String) c.getValue());
            } else if (c.getValue() instanceof Integer) {
                values.put(c.getName(), (int) c.getValue());
            } else if (c.getValue() instanceof Double) {
                values.put(c.getName(), (double) c.getValue());
            } else {
                values.put(c.getName(), new Gson().toJson(c.getValue()));
            }
        }

        long updated = db.update(tableName, values, " id = ?",
                new String[]{String.valueOf(row.getKey())});
        databaseManager.closeDatabase();
        return updated;
    }

    public void delete(int id) {
        db.delete(tableName, "id = ?", new String[]{String.valueOf(id)});
        databaseManager.closeDatabase();
    }

    public void clearAll() {
        db.execSQL("DELETE FROM " + tableName);
        databaseManager.closeDatabase();
    }

    public Row get(int id, String indexColumnName) {
        Cursor cursor = db.query(tableName, getAllNames(), indexColumnName + "= ?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        Object object = null;
        Row row = null;
        if (cursor != null) {
            cursor.moveToFirst();
            List<Column> tempList = new ArrayList<>();
            for (int i = 0; i < columns.size() - 1; i++) {
                Column column = new Column(columns.get(i).getName(), columns.get(i).getType());
                switch (columns.get(i).getType()) {
                    case INTEGER:
                        column.setValue(cursor.getInt(i + 1));
                        break;
                    case INTEGER_PRIMARY_KEY:
                        column.setValue(cursor.getInt(i + 1));
                        break;
                    case TEXT:
                        column.setValue(cursor.getString(i + 1));
                        break;
                    case REAL:
                        column.setValue(cursor.getDouble(i + 1));
                        break;
                    default:
                        column.setValue(cursor.getBlob(i + 1));
                }
                tempList.add(column);
            }
            row = new Row(cursor.getInt(0), tempList);
            cursor.close();
        }
        databaseManager.closeDatabase();
        return row;
    }

    public Cursor executeCustom(String query) {
        Log.i("ObjectTable", "Close the cursor after using executeCustom.");
        return db.rawQuery(query, null);
    }

    public void drop() {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        Log.i("ObjectTable", "Table " + tableName + " dropped.");
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
        databaseManager.closeDatabase();
    }


    public static void drop(String tableName, SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        Log.i("ObjectTable", "Table " + tableName + " dropped.");
    }

    private String[] getAllNames() {
        String[] s = new String[columns.size()];
        for (int i = 0; i < columns.size() - 1; i++) {
            s[i] = columns.get(i).getName();
        }
        return s;
    }


}
