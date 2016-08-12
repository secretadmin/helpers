package com.secretbiology.helpers.general.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Represents sqlight Table
 * Mandatory fields: SQLight Database, Name of Table
 */
public class Table {

    private final String TAG = getClass().getSimpleName();
    private String name;
    private List<Column> columns;
    private LinkedHashMap<String, Column> map;
    private SQLiteDatabase db;
    private String defaultTextValue = "null";
    private int defaultIntValue = 0;

    /**
     * Use this to create new table
     *
     * @param db      :SQLiteDatabase
     * @param name    : Name of Table
     * @param columns : List of all columns (Use same List again for any further table operations)
     */

    public Table(SQLiteDatabase db, String name, List<Column> columns) {
        this.db = db;
        this.name = name;
        List<Column> returnList = new ArrayList<>();
        //Convert to indexed columns
        for (int i = 0; i < columns.size(); i++) {
            Column c = columns.get(i);
            c.setIndex(i);
            returnList.add(c);
        }
        this.columns = returnList;
        this.map = new LinkedHashMap<>();
        for (Column c : columns) {
            this.map.put(c.getName(), c);
        }
    }


    /**
     * @return List of all Columns From given table
     */

    public List<Column> getAllColumns() {
        return columns;
    }

    /**
     * @return LinkedHashMap for columns with column name as key
     */
    public LinkedHashMap<String, Column> getMap() {
        return map;
    }

    /**
     * Get Column of Table
     *
     * @param name: Name of Table
     * @return : Column matching name
     */
    public Column getColumn(String name) {
        Column col = map.get(name);
        if (col != null) {
            return col;
        } else {
            throw new NoSuchElementException("Column " + name + " is not found in the table " + this.name);
        }
    }

    /**
     * Create new table
     */
    public void make() {
        if (columns != null) {
            Log.d(TAG, "Table creating process started");
            String tableString = "CREATE TABLE " + name + "( ";
            switch (columns.size()) {
                case 0:
                    throw new NullPointerException("Error in" + TAG + " :There should be at least one column in the table");
                case 1:
                    tableString = tableString + getLine(columns.get(0)) + ")";
                    break;
                default:
                    for (int i = 0; i < columns.size() - 1; i++) {
                        tableString = tableString + getLine(columns.get(i)) + ", ";
                    }
                    tableString = tableString + getLine(columns.get(columns.size() - 1)) + ")";
            }
            db.execSQL(tableString);
        } else {
            throw new NullPointerException("Error in" + TAG + " :No columns are provided. Use list of columns in constructor");
        }
    }


    private String getLine(Column column) {
        if (column.getType().equals(Column.ColumnType.TEXT)) {
            return column.getName() + " TEXT ";
        } else if (column.getType().equals(Column.ColumnType.PRIMARY_INTEGER)) {
            return column.getName() + " INTEGER PRIMARY KEY ";
        } else if (column.getType().equals(Column.ColumnType.INTEGER)) {
            return column.getName() + " INTEGER ";
        } else
            throw new NullPointerException("Error in" + TAG + "Unknown column type. Aborting table making");

    }

    /**
     * Get specific row from table from Unique Column-Value pair
     *
     * @param ColumnName : Name of Column
     * @param value      : Its value
     * @return : Value matching specific query
     * (If value is matching multiple queries, it will return first encounter)
     */
    public Row getRowByValue(String ColumnName, Object value) {
        return new Row(db, name, columns, ColumnName, value);
    }

    /**
     * @return All rows in the form of TableLocation
     */
    public List<LinkedHashMap<String, Column>> getAllRows() {
        return new Row(db, name, columns).getAllRows();
    }

    /**
     * @param ColumnList : List of columns WITH values
     * @return rowID
     */
    public long addRow(List<Column> ColumnList) {
        ContentValues values = new ContentValues();
        for (Column c : ColumnList) {
            switch (c.getType()) {
                case TEXT:
                    if (c.getData() != null) {
                        values.put(c.getName(), (String) c.getData());
                    } else {
                        Log.e(TAG, c.getName() + " : No value found for this column. Default text value will be used.");
                        values.put(c.getName(), defaultTextValue);
                    }
                    break;
                case PRIMARY_INTEGER:
                    values.put(c.getName(), 0);
                    break;
                case INTEGER:
                    if (c.getData() != null) {
                        values.put(c.getName(), (Integer) c.getData());
                    } else {
                        Log.e(TAG, c.getName() + " : No value found for this column. Default int value will be used.");
                        values.put(c.getName(), defaultIntValue);
                    }
                    break;
            }
        }
        return db.insert(name, null, values);
    }

    public String getDefaultTextValue() {
        return defaultTextValue;
    }

    public void setDefaultTextValue(String defaultTextValue) {
        this.defaultTextValue = defaultTextValue;
    }

    public int getDefaultIntValue() {
        return defaultIntValue;
    }

    public void setDefaultIntValue(int defaultIntValue) {
        this.defaultIntValue = defaultIntValue;
    }

    /**
     * Drops entire table
     */
    public void drop() {
        db.execSQL("DROP TABLE IF EXISTS " + name);
    }

    /**
     * Clears all content of table
     */
    public void clearAll() {
        db.execSQL("DELETE FROM " + name);
    }

    /**
     * Deletes row where "ColumnName" has value "Value"
     *
     * @param ColumnName : Name of Column
     * @param Value      : Value of specific Column
     */
    public void delete(String ColumnName, Object Value) {
        db.delete(name, ColumnName + " = ?", new String[]{String.valueOf(Value)});
    }

    /**
     * Updates row of table when provided uniqueColumn (generally Primary Integer Key)
     * This function will keep value from this column and update everything else provided by ColumnList
     *
     * @param ColumnList   : List of all columns WITH values
     * @param UniqueColumn : Name of Unique Column
     * @return : Number of rows affected
     */

    public long update(List<Column> ColumnList, String UniqueColumn) {
        Object uniqueValue = "";
        ContentValues values = new ContentValues();
        for (Column c : ColumnList) {

            uniqueValue = map.get(UniqueColumn).getData();

            switch (c.getType()) {
                case TEXT:
                    if (c.getData() != null) {
                        values.put(c.getName(), (String) c.getData());
                    } else {
                        Log.e(TAG, c.getName() + " : No value found for this column. Default text value will be used.");
                        values.put(c.getName(), defaultTextValue);
                    }
                    break;
                case PRIMARY_INTEGER:
                    values.put(c.getName(), 0);
                    break;
                case INTEGER:
                    if (c.getData() != null) {
                        values.put(c.getName(), (Integer) c.getData());
                    } else {
                        Log.e(TAG, c.getName() + " : No value found for this column. Default int value will be used.");
                        values.put(c.getName(), defaultIntValue);
                    }
                    break;
            }
        }
        return db.update(name, values, UniqueColumn + " = ?", new String[]{String.valueOf(uniqueValue)});
    }

    /**
     * Remove old entries from table if they are larger than "AllowedEntries"
     *
     * @param allowedEntries : Number of records to keep in table
     * @param UniqueColumn   : Name of any unique column (Generally Primary Integer Key)
     */
    public void removeOldEntries(long allowedEntries, String UniqueColumn) {
        Cursor c = db.rawQuery("SELECT * FROM " + name, null);
        long allCount = c.getCount();
        c.close();
        if (allCount > allowedEntries) {
            allowedEntries = allCount - allowedEntries;
            String Query = "DELETE FROM " + name + " WHERE " + UniqueColumn + " IN (SELECT " + UniqueColumn + " FROM " + name + " ORDER BY " + UniqueColumn + " ASC LIMIT " + allowedEntries + ")";
            db.execSQL(Query);
        }
    }

}
