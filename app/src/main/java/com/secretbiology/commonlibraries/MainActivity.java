package com.secretbiology.commonlibraries;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.secretbiology.helpers.general.Log;
import com.secretbiology.helpers.general.database.Column;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Column> columns = new ArrayList<>();
        columns.add(new Column("name", Column.TYPE.TEXT, "yohi"));
        columns.add(new Column("vr", Column.TYPE.TEXT, "ooo"));
        // new DataManager(getBaseContext()).getTable("ee5").add(new Row(columns));

        //SparseArray<Object> list = new DataManager(getBaseContext()).getTable("ee2").getAll(DatabaseTesting.class);
        Log.test(new DataManager(getBaseContext()).getTable("ee5").getAll().size());

    }
}
