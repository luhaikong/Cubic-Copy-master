package com.toly1994.cubic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.toly1994.cubic.view.XfermodeView;

public class XfActivity extends AppCompatActivity {

    XfermodeView xfermodeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xf);
        xfermodeView = findViewById(R.id.cus_view);
        xfermodeView.setDirect(1);
    }
}
