package com.secretbiology.helpers.general.sql;

/**
 * Specialize class to represent Column-Value pair of specific row inside table
 */
public class TableLocation {

    private final String TAG = getClass().getSimpleName();
    private String columnName;
    private Column.ColumnType columnType;
    private int columnIndex;
    private Object value;
    private String tableName;

    /**
     * @param tableName : Name of Table
     * @param column    : Column WITH values (Important to use column with values, else it will throw NullPointerException;
     */
    public TableLocation(String tableName, Column column) {
        this.tableName = tableName;
        columnName = column.getName();
        columnType = column.getType();
        columnIndex = column.getIndex();
        if (column.getData() != null) {
            value = column.getData();
        } else {
            throw new NullPointerException("Error in " + TAG + " :While retrieving row data, you are trying to access value for column \'" + columnName + "\' which is not set ");
        }
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Column.ColumnType getColumnType() {
        return columnType;
    }

    public void setColumnType(Column.ColumnType columnType) {
        this.columnType = columnType;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIdex) {
        this.columnIndex = columnIdex;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
