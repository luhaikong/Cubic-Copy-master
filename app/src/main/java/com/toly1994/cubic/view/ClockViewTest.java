package com.toly1994.cubic.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.toly1994.cubic.R;
import com.toly1994.cubic.analyze.HelpDraw;

import java.util.Calendar;

public class ClockViewTest extends View {

    private Point mCoo;//坐标系
    private Paint mPaint;//主画笔

    private Paint mHelpPint;
    private Paint hPaint;
    private Paint hzPaint,mzPaint,szPaint;

    private final static int defaultRadius = 300;

    public ClockViewTest(Context context) {
        this(context,null);
    }

    public ClockViewTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setLayerType(LAYER_TYPE_SOFTWARE,null);
        init();//初始化
    }

    public void start(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        },1000);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            invalidate();
            start();
        }
    };

    private void init() {
        mCoo = new Point(defaultRadius, defaultRadius);
        //初始化主画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        //初始化辅助
        mHelpPint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHelpPint.setColor(Color.BLACK);
        mHelpPint.setStyle(Paint.Style.STROKE);
        mHelpPint.setStrokeWidth(10);

        hPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        hPaint.setTextSize(40);
        hPaint.setStrokeWidth(5);
        hPaint.setStyle(Paint.Style.FILL);
        hPaint.setTextAlign(Paint.Align.CENTER);
        hPaint.setColor(Color.BLUE);

        hzPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        hzPaint.setColor(Color.RED);
        hzPaint.setStrokeWidth(15);
        hzPaint.setStyle(Paint.Style.FILL);

        mzPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mzPaint.setColor(Color.GREEN);
        mzPaint.setStrokeWidth(10);
        mzPaint.setStyle(Paint.Style.FILL);

        szPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        szPaint.setColor(Color.YELLOW);
        szPaint.setStrokeWidth(5);
        szPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mCoo.x,mCoo.y);

        drawClock(canvas);

        drawHMSz(canvas);
    }

    private void drawClock(Canvas canvas){
        canvas.save();
        canvas.drawCircle(0,0,defaultRadius,mHelpPint);

        for (int i=0;i<60;i++){
            if (i%5==0){
                canvas.drawLine(defaultRadius-40,0,defaultRadius,0,mHelpPint);
            } else {
                canvas.drawLine(defaultRadius-20,0,defaultRadius,0,mHelpPint);
            }
            canvas.rotate(6);
        }

        canvas.drawText("12",0,-(defaultRadius-80),hPaint);
        canvas.drawText("3",defaultRadius-60,15,hPaint);
        canvas.drawText("6",0,defaultRadius-50,hPaint);
        canvas.drawText("9",-(defaultRadius-60),15,hPaint);
        canvas.restore();
    }

    private void drawHMSz(Canvas canvas){
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int min = Calendar.getInstance().get(Calendar.MINUTE);
        int sec = Calendar.getInstance().get(Calendar.SECOND);
        canvas.save();
        canvas.rotate(-90);
        float degH = hour>12?30*(hour-12)+30*((float)min/60):30*hour+30*((float)min/60);
        canvas.rotate(degH);
        canvas.drawLine(-50,0,defaultRadius-150,0,hzPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(-90);
        float degM = 6*min;
        canvas.rotate(degM);
        canvas.drawLine(-50,0,defaultRadius-100,0,mzPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(-90);
        float degS = 6*sec;
        canvas.rotate(degS);
        canvas.drawLine(-50,0,defaultRadius-50,0,szPaint);
        canvas.restore();

        canvas.drawText(hour+"时"+min+"分"+sec+"秒",0,defaultRadius-140,hPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getWidthSize(defaultRadius, widthMeasureSpec);
        int height = getHeightSize(defaultRadius, heightMeasureSpec);

        if (width < height) {
            height = width;
        } else {
            width = height;
        }

        setMeasuredDimension(width, height);
    }

    private int getHeightSize(int defaultSize, int measureSpec){
        int mySize = 0;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize*2;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    private int getWidthSize(int defaultSize, int measureSpec){
        int mySize = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize*2;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }
}
