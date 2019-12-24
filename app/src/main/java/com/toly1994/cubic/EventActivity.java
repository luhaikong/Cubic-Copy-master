package com.toly1994.cubic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.toly1994.cubic.event.EventViewA;
import com.toly1994.cubic.event.EventViewB;
import com.toly1994.cubic.utils.ClickUtils;

public class EventActivity extends AppCompatActivity {

    private EventViewB eventViewB;
    private EventViewA eventViewA;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        eventViewB = findViewById(R.id.eventViewB);
        ClickUtils.applyPressedViewScale(eventViewB);

    }
}
