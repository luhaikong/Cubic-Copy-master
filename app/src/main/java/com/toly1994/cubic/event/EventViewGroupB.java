package com.toly1994.cubic.event;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class EventViewGroupB extends LinearLayout {

    public EventViewGroupB(Context context) {
        super(context);
    }

    public EventViewGroupB(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("event","-----------dispatchTouchEvent----------viewGroupB");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("event","-----------onInterceptTouchEvent----------viewGroupB");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("event","-----------onTouchEvent----------viewGroupB");
        return super.onTouchEvent(event);
    }
}
