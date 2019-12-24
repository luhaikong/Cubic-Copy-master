package com.toly1994.cubic.event;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class EventViewB extends View {

    public EventViewB(Context context) {
        super(context);
    }

    public EventViewB(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i("event","-----------dispatchTouchEvent----------viewB");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("event","-----------onTouchEvent----------viewB");
        return super.onTouchEvent(event);
    }
}
