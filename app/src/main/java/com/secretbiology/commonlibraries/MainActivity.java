package com.secretbiology.commonlibraries;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.secretbiology.helpers.general.ConverterMode;
import com.secretbiology.helpers.general.DateConverter;
import com.secretbiology.helpers.general.Log;
import com.secretbiology.helpers.general.database.Column;
import com.secretbiology.helpers.general.database.Row;
import com.secretbiology.helpers.general.database.Table;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Table table = new DataManager(getBaseContext()).getTable("ee5");
//        strings.add("11:00");
//        strings.add("14:00");
//        strings.add("18:30");
        try {
            Log.inform(DateConverter.convertToString(DateConverter.convertToCalender(ConverterMode.DATE_FIRST, "18:30"), "hh:mm a"));
            Log.inform(DateConverter.convertToCalender(ConverterMode.DATE_FIRST,"18:30"));
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
