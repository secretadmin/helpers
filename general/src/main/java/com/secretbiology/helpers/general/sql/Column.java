package com.secretbiology.helpers.general.sql;

/**
 * Represents columns in Sqlight table.
 * Each column MUST have two properties : Name and Type of Data it holds
 * Value of Column is optional and can be set later on if needed
 */
public class Column {

    public enum ColumnType {PRIMARY_INTEGER, TEXT, INTEGER}

    private final String TAG = getClass().getSimpleName();
    private String name;
    private ColumnType type;
    private int index;
    private Object object;

    /**
     * Use this for generation of table as you don't need to provide value for each column in this constructor
     *
     * @param name : Column Name
     * @param type : Type of data (from enum ColumnType)
     */
    public Column(String name, ColumnType type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Use this to add or set value for any column
     *
     * @param name   : Column Name
     * @param type   : Type of data (from enum ColumnType)
     * @param object : Data ( String or Integer )
     */

    public Column(String name, ColumnType type, Object object) {
        this.name = name;
        this.type = type;
        setData(object);
    }

    /**
     * @return Name of Column
     */
    public String getName() {
        return name;
    }

    /**
     * @return Index of Column in given table
     */
    protected int getIndex() {
        return index;
    }

    protected void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return Column type
     */
    public ColumnType getType() {
        return type;
    }

    public void setData(Object data) {
        switch (type) {
            case TEXT:
                if (!(data instanceof String)) {
                    throw new NoSuchFieldError(name + " accepts only String data in :" + TAG);
                }
                break;
            case INTEGER:
                if (!(data instanceof Integer)) {
                    throw new NoSuchFieldError(name + " accepts only Integer data in :" + TAG);
                }
                break;
            case PRIMARY_INTEGER:
                if (!(data instanceof Integer)) {
                    throw new NoSuchFieldError(name + " accepts only Integer data in :" + TAG);
                }
                break;
            default:
                throw new NullPointerException("Error in " + TAG + " :Unknown data format for :" + name);
        }
        this.object = data;
    }

    /**
     * @return : Object value from Column
     */
    public Object getData() {
        if (this.object != null) {
            return this.object;
        } else throw new NullPointerException("Error in " + TAG + " :No value is set for " + name);
    }


}
