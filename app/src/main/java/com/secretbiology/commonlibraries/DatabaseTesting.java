package com.secretbiology.commonlibraries;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.secretbiology.helpers.general.sql.Column;
import com.secretbiology.helpers.general.sql.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * NCBSinfo © 2016, Secret Biology
 * https://github.com/NCBSinfo/NCBSinfo
 * Created by Rohit Suratekar on 12-08-16.
 */
public class DatabaseTesting extends SQLiteOpenHelper {

    static String DATABASE_NAME = "xyz";

    public DatabaseTesting(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseTesting(Context context) {
        super(context, DATABASE_NAME, null, 9);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        List<Column> columnList = new ArrayList<>();
        columnList.add(new Column("id", Column.ColumnType.PRIMARY_INTEGER));
        columnList.add(new Column("name", Column.ColumnType.TEXT));
        columnList.add(new Column("department", Column.ColumnType.INTEGER));
        Table table = new Table(db, "TestTable", columnList);
        table.make();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("TAG", "Upgraded database");
        List<Column> columnList = new ArrayList<>();
        columnList.add(new Column("id", Column.ColumnType.PRIMARY_INTEGER));
        columnList.add(new Column("name", Column.ColumnType.TEXT));
        columnList.add(new Column("department", Column.ColumnType.INTEGER));
        Table table = new Table(sqLiteDatabase, "TestTable", columnList);
        table.drop();
        onCreate(sqLiteDatabase);
    }

}
