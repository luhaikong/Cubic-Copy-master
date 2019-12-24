package com.toly1994.cubic.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.toly1994.cubic.R;

public class CircleLogoViewTop extends View {

    private int radius = 0;
    private Paint paint_neicu;
    private Bitmap bitmap_neicu;
    private Paint paint_neixi;
    private Bitmap bitmap_neixi;
    private Paint paint_wai;
    private Bitmap bitmap_wai;
    private Paint paint_logo;
    private Bitmap bitmap_logo;
    private float degreesWai = -60f;
    private float degreesNeicu = 60f;
    private float scaleLogo = 0.1f;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            invalidate();
            start();
        }
    };

    public interface OnPlayFinishListener {
        void onFinish();
    }

    private OnPlayFinishListener finishListener;

    public void setFinishListener(OnPlayFinishListener finishListener) {
        this.finishListener = finishListener;
    }

    public void start(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (scaleLogo<1f){
                    scaleLogo = scaleLogo + 0.1f;
                } else {
                    if (degreesWai<0f){
                        degreesWai = degreesWai + 5f;
                    }
                    if (degreesNeicu>0f){
                        degreesNeicu = degreesNeicu - 5f;
                    }
                }
                mHandler.sendEmptyMessage(0);
            }
        },100);
    }

    public void destory(){
        mHandler.removeCallbacksAndMessages(null);
    }

    public CircleLogoViewTop(Context context) {
        this(context,null);
    }

    public CircleLogoViewTop(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        paint_logo = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_logo.setStyle(Paint.Style.FILL);
        bitmap_logo = BitmapFactory.decodeResource(context.getResources(), R.mipmap.splash_logo);

        paint_wai = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_wai.setStyle(Paint.Style.FILL);
        bitmap_wai = BitmapFactory.decodeResource(context.getResources(), R.mipmap.splash_wai);

        paint_neixi = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_neixi.setStyle(Paint.Style.FILL);
        bitmap_neixi = BitmapFactory.decodeResource(context.getResources(), R.mipmap.splash_neixi);

        paint_neicu = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_neicu.setStyle(Paint.Style.FILL);
        bitmap_neicu = BitmapFactory.decodeResource(context.getResources(), R.mipmap.splash_neicu);
    }

    private void initRadius(int widthSize,int heightSize){
        if (widthSize<heightSize){
            radius = widthSize/2;
        } else {
            radius = heightSize/2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(radius,radius);

        canvas.save();
        canvas.scale(scaleLogo,scaleLogo);
//        canvas.drawBitmap(bitmap_logo,-(bitmap_logo.getWidth()/2),-(bitmap_logo.getHeight()/2),paint_logo);
        RectF rectFlogo = new RectF(-(radius*bitmap_logo.getWidth()/bitmap_wai.getWidth()),-(radius*bitmap_logo.getHeight()/bitmap_wai.getHeight()),(radius*bitmap_logo.getWidth()/bitmap_wai.getWidth()),(radius*bitmap_logo.getHeight()/bitmap_wai.getHeight()));
        canvas.drawBitmap(bitmap_logo,null,rectFlogo,paint_logo);
        canvas.restore();

        if (scaleLogo>=1f){
//            canvas.drawBitmap(bitmap_neixi,-(bitmap_logo.getWidth()/2+65),260-(bitmap_logo.getHeight()/2),paint_neixi);
            RectF rectFneiXi = new RectF(-(radius*bitmap_neicu.getWidth()/bitmap_wai.getWidth())
                    ,0
                    ,(radius*bitmap_neicu.getWidth()/bitmap_wai.getWidth()*45/100)
                    ,(radius*bitmap_neicu.getHeight()/bitmap_wai.getHeight()));
            canvas.drawBitmap(bitmap_neixi,null,rectFneiXi,paint_neixi);

            canvas.save();
            canvas.rotate(degreesNeicu);
//            canvas.drawBitmap(bitmap_neicu,-(bitmap_neicu.getWidth()/2+5),-(bitmap_neicu.getWidth()/2+10),paint_neicu);
            RectF rectFneiCu = new RectF(-(radius*bitmap_neicu.getWidth()/bitmap_wai.getWidth())
                    ,-(radius*bitmap_neicu.getHeight()/bitmap_wai.getHeight())
                    ,(radius*bitmap_neicu.getWidth()/bitmap_wai.getWidth())
                    ,(radius*bitmap_neicu.getHeight()/bitmap_wai.getHeight()));
            canvas.drawBitmap(bitmap_neicu,null,rectFneiCu,paint_neicu);
            canvas.restore();

            canvas.save();
            canvas.rotate(degreesWai);
//            canvas.drawBitmap(bitmap_wai,-radius,-radius,paint_wai);
            RectF rectFwai = new RectF(-radius,-radius,radius,radius);
            canvas.drawBitmap(bitmap_wai,null,rectFwai,paint_wai);
            canvas.restore();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        initRadius(widthSize,heightSize);
        setMeasuredDimension(widthSize,heightSize);
    }
}
