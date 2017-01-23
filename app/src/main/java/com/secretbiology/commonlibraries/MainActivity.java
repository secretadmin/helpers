package com.secretbiology.commonlibraries;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.secretbiology.helpers.general.views.InputMultiAutoCompleteView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputMultiAutoCompleteView view = (InputMultiAutoCompleteView) findViewById(R.id.test_text);
        view.setError("this one");

    }
}
