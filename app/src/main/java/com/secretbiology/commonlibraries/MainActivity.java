package com.secretbiology.commonlibraries;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.secretbiology.helpers.general.ConverterMode;
import com.secretbiology.helpers.general.DateConverter;

import java.text.ParseException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String s = getString(R.string.date);
        try {
            Log.i("Input :", "here " + DateConverter.convertToDate(ConverterMode.MONTH_FIRST, "12/12/2017"));
            Log.i("Input :", "here " + DateConverter.convertToDate(ConverterMode.MONTH_FIRST, "6\\12\\2017"));
            Log.i("Input :", "here " + DateConverter.convertToDate(ConverterMode.MONTH_FIRST, "6 january 2017"));
            Log.i("Input :", "here " + DateConverter.convertToDate(ConverterMode.MONTH_FIRST, "01:00 PM"));

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
