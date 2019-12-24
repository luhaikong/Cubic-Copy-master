package com.toly1994.cubic.event;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class EventViewGroupAF extends LinearLayout {

    public EventViewGroupAF(Context context) {
        super(context);
    }

    public EventViewGroupAF(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("event","-----------dispatchTouchEvent----------viewGroupAF");
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("event","-----------onInterceptTouchEvent----------viewGroupAF");
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("event","-----------onTouchEvent----------viewGroupAF");
        return super.onTouchEvent(event);
    }
}
