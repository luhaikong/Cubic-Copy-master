package com.toly1994.cubic.event;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class EventViewGroupA extends LinearLayout {

    public EventViewGroupA(Context context) {
        super(context);
    }

    public EventViewGroupA(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("event","-----------dispatchTouchEvent----------viewGroupA");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("event","-----------onInterceptTouchEvent----------viewGroupA");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("event","-----------onTouchEvent----------viewGroupA");
        return super.onTouchEvent(event);
    }
}
