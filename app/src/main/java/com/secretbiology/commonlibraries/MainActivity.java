package com.secretbiology.commonlibraries;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.secretbiology.helpers.general.General;
import com.secretbiology.helpers.general.sql.Column;
import com.secretbiology.helpers.general.sql.Table;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     /*   Log.i("Tag", General.timeStamp());
        DatabaseTesting data = new DatabaseTesting(getBaseContext());
        List<Column> columnList = new ArrayList<>();
        columnList.add(new Column("id", Column.ColumnType.PRIMARY_INTEGER, 1));
        columnList.add(new Column("name", Column.ColumnType.TEXT, "zeuuusdfroth"));
        columnList.add(new Column("department", Column.ColumnType.INTEGER, 345345));
        Table table = new Table(data.getWritableDatabase(), "TestTable", columnList);
        Log.i("TTT", table.getAllRows().size()+"");*/



    }
}
