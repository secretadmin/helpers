package com.secretbiology.helpers.general.sql;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents Row of sqlight table
 * Mandatory inputs : SQLiteDatabase, Table Name, List of Columns in Table
 * <p/>
 * To Access specific row, you need to provide one of additional information
 * (1) ColumnName-Value pair
 * (2) Entire Column with values.
 * <p/>
 * You can access row information in the form of TableLocations from getItems() method. These are nothing but list of Column-Value pairs for each position
 * into specific row inside table. This will contains all basic properties of Column (Name, Type, Index) as well as it's value
 */

public class Row {

    private final String TAG = getClass().getSimpleName();
    private List<Column> columnNames;
    private List<TableLocation> rowItems;
    private SQLiteDatabase db;
    private String tableName;

    /**
     * Points to row in table where 'ColumnName' has value 'value'.
     *
     * @param db         : SQLite Database
     * @param tableName  : Table Name
     * @param Column     : List of Columns in current table
     * @param ColumnName : Name of Column from Column-Value pair
     * @param value      : Value of data from Column-Value Pair
     */

    public Row(SQLiteDatabase db, String tableName, List<Column> Column, String ColumnName, Object value) {
        this.db = db;
        this.columnNames = Column;
        this.tableName = tableName;
        rowItems = new ArrayList<>();

        Cursor cursor = db.query(tableName, makeColumnString(), ColumnName + "=?",
                new String[]{String.valueOf(value)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < Column.size(); i++) {
                rowItems.add(getLocations(Column.get(i), cursor, i));
            }
            cursor.close();
        }
    }

    /**
     * Represents row in the table where values of all columns are known
     *
     * @param db        : SQLite Database
     * @param tableName : Table Name
     * @param Column    : List of Columns in current table WITH values
     *                  (However you can use column without values if you are retrieving all rows from table)
     */

    public Row(SQLiteDatabase db, String tableName, List<Column> Column) {
        this.db = db;
        this.columnNames = Column;
        this.tableName = tableName;

        if (Column.get(0).getData() != null) {
            rowItems = new ArrayList<>();
            for (Column c : Column) {
                rowItems.add(new TableLocation(tableName, c));
            }
        }
    }

    private TableLocation getLocations(Column Column, Cursor cursor, int position) {

        switch (Column.getType()) {
            case TEXT:
                return new TableLocation(tableName, new Column(Column.getName(), Column.getType(), cursor.getString(position)));

            case INTEGER:
                return new TableLocation(tableName, new Column(Column.getName(), Column.getType(), cursor.getInt(position)));

            case PRIMARY_INTEGER:
                return new TableLocation(tableName, new Column(Column.getName(), Column.getType(), cursor.getInt(position)));


        }
        throw new NullPointerException("Table is empty");
    }

    /**
     * @return List of TableLocations from given rows.
     */
    public List<TableLocation> getItems() {
        if (rowItems.size() != 0) {
            return rowItems;
        } else
            throw new NullPointerException("Error in " + TAG + " :No values are set in the list of columns provided in constructor");

    }

    /**
     * @return List of all rows with respective TableLocation Lists
     */
    public List<List<TableLocation>> getAllRows() {
        List<List<TableLocation>> returnList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + tableName;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                List<TableLocation> loc = new ArrayList<>();
                for (int i = 0; i < columnNames.size(); i++) {
                    loc.add(getLocations(columnNames.get(i), cursor, i));
                }
                returnList.add(loc);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return returnList;
    }

    private String[] makeColumnString() {
        String[] array = new String[columnNames.size()];
        for (int i = 0; i < columnNames.size(); i++) {
            array[i] = columnNames.get(i).getName();
        }
        return array;
    }
}
