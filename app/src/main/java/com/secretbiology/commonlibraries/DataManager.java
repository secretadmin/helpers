package com.secretbiology.commonlibraries;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.secretbiology.helpers.general.database.Column;
import com.secretbiology.helpers.general.database.ObjectTable;
import com.secretbiology.helpers.general.database.Operator;
import com.secretbiology.helpers.general.database.Table;

import java.util.ArrayList;
import java.util.List;

public class DataManager extends Operator {

    static String n = "kk";
    static int y = 6;

    public DataManager(Context context, String name, int version) {
        super(context, name, version);
    }

    public DataManager(Context context) {
        super(context, n, null, y);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
        ObjectTable.make("ee", db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);

        Log.i("Database", "Database upgraded to " + newVersion);
        Table.drop("ee4", db);
        Table.make("ee5", db, getCol());


    }

    public static List<Column> getCol() {
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("id", Column.TYPE.PRIMARY_INTEGER));
        columns.add(new Column("name", Column.TYPE.TEXT));
        columns.add(new Column("vr", Column.TYPE.TEXT));

        return columns;
    }

    public Table getTable(String name) {
        return new Table(this, name, getCol());
    }
}
