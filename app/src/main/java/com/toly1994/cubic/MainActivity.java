package com.toly1994.cubic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.toly1994.cubic.view.ClockView;

public class MainActivity extends AppCompatActivity {

    ClockView clockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clockView = findViewById(R.id.cus_view);
        clockView.start();
    }
}
