package com.toly1994.cubic.view;

import android.content.Context;
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

import com.toly1994.cubic.analyze.HelpDraw;

import java.util.Calendar;

public class ClockView extends View {

    private Point mCoo = new Point(500, 500);//坐标系
    private Picture mCooPicture;//坐标系canvas元件
    private Picture mGridPicture;//网格canvas元件
    private Paint mPaint;//主画笔

    private Paint mHelpPint;
    private Paint hPaint;
    private Paint hzPaint,mzPaint,szPaint;

    public ClockView(Context context) {
        this(context,null);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
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

    public void destory(){
        handler = null;
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
        //初始化主画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mCooPicture = HelpDraw.getCoo(getContext(), mCoo);
        mGridPicture = HelpDraw.getGrid(getContext());

        //初始化辅助
        mHelpPint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHelpPint.setColor(Color.BLACK);
        mHelpPint.setStyle(Paint.Style.STROKE);
        mHelpPint.setStrokeWidth(10);

        hPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        hPaint.setTextSize(60);
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
        HelpDraw.draw(canvas, mGridPicture, mCooPicture);
        canvas.save();
        canvas.translate(mCoo.x,mCoo.y);

        drawClock(canvas);

        drawSz(canvas);
        drawMz(canvas);
        drawHz(canvas);
    }

    private void drawClock(Canvas canvas){
        canvas.save();
        canvas.drawCircle(0,0,300,mHelpPint);

        for (int i=0;i<60;i++){
            if (i%5==0){
                canvas.drawLine(260,0,300,0,mHelpPint);
            } else {
                canvas.drawLine(280,0,300,0,mHelpPint);
            }
            canvas.rotate(6);
        }

        canvas.drawText("12",0,-330,hPaint);
        canvas.drawText("3",330,15,hPaint);
        canvas.drawText("6",0,330,hPaint);
        canvas.drawText("9",-330,15,hPaint);
        canvas.restore();
    }

    private void drawHz(Canvas canvas){
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int min = Calendar.getInstance().get(Calendar.MINUTE);
        canvas.drawText("时："+hour,500,300,hPaint);
        canvas.save();
        canvas.rotate(-90);
        float deg = hour>12?30*(hour-12)+30*((float)min/60):30*hour+30*((float)min/60);
        canvas.rotate(deg);
        canvas.drawLine(-50,0,150,0,hzPaint);
        canvas.restore();
    }

    private void drawMz(Canvas canvas){
        int min = Calendar.getInstance().get(Calendar.MINUTE);
        canvas.drawText("分："+min,500,200,hPaint);
        canvas.save();
        canvas.rotate(-90);
        float deg = 6*min;
        canvas.rotate(deg);
        canvas.drawLine(-50,0,200,0,mzPaint);
        canvas.restore();
    }

    private void drawSz(Canvas canvas){
        int sec = Calendar.getInstance().get(Calendar.SECOND);
        canvas.drawText("秒："+sec,500,100,hPaint);
        canvas.save();
        canvas.rotate(-90);
        float deg = 6*sec;
        canvas.rotate(deg);
        canvas.drawLine(-50,0,250,0,szPaint);
        canvas.restore();
    }

}
