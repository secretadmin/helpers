package com.secretbiology.helpers.general.database;

public class Column {

    public enum TYPE {INTEGER_PRIMARY_KEY, INTEGER, TEXT, BLOB, REAL}

    private String name;
    private TYPE type;
    private Object value;

    public Column(String name, TYPE type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public Column(String name, TYPE type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
