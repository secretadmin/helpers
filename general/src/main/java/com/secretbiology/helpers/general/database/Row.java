package com.secretbiology.helpers.general.database;

import java.util.HashMap;
import java.util.List;

public class Row {
    private int key;
    private List<Column> columns;

    public Row(int key, List<Column> columns) {
        this.key = key;
        this.columns = columns;
    }

    public Row(List<Column> columns) {
        this.key = 0;
        this.columns = columns;
    }

    public int getKey() {
        return key;
    }

    public HashMap<String, Object> getMap() {
        HashMap<String, Object> map = new HashMap<>();
        for (Column c : columns) {
            map.put(c.getName(), c.getValue());
        }
        return map;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
