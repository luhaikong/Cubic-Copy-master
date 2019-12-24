package com.toly1994.cubic.event;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class EventViewA extends View {

    public EventViewA(Context context) {
        super(context);
    }

    public EventViewA(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i("event","-----------dispatchTouchEvent----------viewA");
//        return super.dispatchTouchEvent(event);
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("event","-----------onTouchEvent----------viewA");
        return super.onTouchEvent(event);
    }
}
