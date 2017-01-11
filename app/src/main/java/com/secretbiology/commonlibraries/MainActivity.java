package com.secretbiology.commonlibraries;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.secretbiology.helpers.general.Log;
import com.secretbiology.helpers.general.database.Column;
import com.secretbiology.helpers.general.database.Row;
import com.secretbiology.helpers.general.database.Table;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Table table = new DataManager(getBaseContext()).getTable("ee5");
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("name", Column.TYPE.TEXT, "iio"));
        columns.add(new Column("vr", Column.TYPE.TEXT, "odfo"));
        //table.add(columns);
        for (Row r : table.getAll()) {
            for (Column c : r.getColumns()) {
                Log.test(this, c.getName(), c.getValue());
            }
        }


    }
}
