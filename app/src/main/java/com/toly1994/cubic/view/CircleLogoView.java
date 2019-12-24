package com.toly1994.cubic.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.toly1994.cubic.R;

public class CircleLogoView extends View {

    private int radius = 0;
    private Paint paint_bg;
    private Bitmap bitmap_bg;
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

    private Paint paint_nameZh;
    private Bitmap bitmap_nameZh;
    private int nameZhAlpha = 0;
    private Paint paint_nameEn;
    private Bitmap bitmap_nameEn;
    private int nameEnAlpha = 0;
    private Paint paint_ad;
    private Bitmap bitmap_ad;
    private int adAlpha = 0;

    private Paint paint_rain;
    private Bitmap bitmap_rain;
    private PointF pointF_rain0,pointF_rain1,pointF_rain2;

    private int mWidth;
    private int mHeight;

    private boolean stop = false;
    public interface OnPlayFinishListener {
        void onFinish();
    }

    private OnPlayFinishListener finishListener;

    public void setFinishListener(OnPlayFinishListener finishListener) {
        this.finishListener = finishListener;
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            invalidate();
            start();
        }
    };

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
                    if (degreesWai>-30f && nameZhAlpha<=250){
                        nameZhAlpha = nameZhAlpha + 15;
                    }
                    if (nameZhAlpha==255 && nameEnAlpha<=250){
                        nameEnAlpha = nameEnAlpha + 15;
                    }
                    if (nameEnAlpha==255 && adAlpha<=250){
                        adAlpha = adAlpha + 15;
                    }
                    if (adAlpha==255 && pointF_rain0.x>-mWidth/2){
                        pointF_rain0.x = pointF_rain0.x - 40;
                        pointF_rain0.y = pointF_rain0.y + 40;

                        pointF_rain1.x = pointF_rain1.x - 20;
                        pointF_rain1.y = pointF_rain1.y + 20;

                        pointF_rain2.x = pointF_rain2.x - 15;
                        pointF_rain2.y = pointF_rain2.y + 15;
                    }
                    if (adAlpha==255 && pointF_rain0.x<=-mWidth/2){
                        stop = true;
                    }
                    if (finishListener!=null && stop){
                        finishListener.onFinish();
                        return;
                    }

                    paint_nameZh.setAlpha(nameZhAlpha);
                    paint_nameEn.setAlpha(nameEnAlpha);
                    paint_ad.setAlpha(adAlpha);
                }
                mHandler.sendEmptyMessage(0);
            }
        },100);
    }

    public void destory(){
        bitmap_bg.recycle();
        bitmap_neicu.recycle();
        bitmap_neixi.recycle();
        bitmap_wai.recycle();
        bitmap_logo.recycle();
        bitmap_nameZh.recycle();
        bitmap_nameEn.recycle();
        bitmap_ad.recycle();
        mHandler.removeCallbacksAndMessages(null);
    }

    public CircleLogoView(Context context) {
        this(context,null);
    }

    public CircleLogoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        paint_bg = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_bg.setStyle(Paint.Style.FILL);
        bitmap_bg = BitmapFactory.decodeResource(context.getResources(), R.mipmap.splash_bg);

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

        paint_nameZh = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_nameZh.setStyle(Paint.Style.FILL);
        bitmap_nameZh = BitmapFactory.decodeResource(context.getResources(), R.mipmap.splash_name);

        paint_nameEn = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_nameEn.setStyle(Paint.Style.FILL);
        bitmap_nameEn = BitmapFactory.decodeResource(context.getResources(), R.mipmap.splash_name_en);

        paint_ad = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_ad.setStyle(Paint.Style.FILL);
        bitmap_ad = BitmapFactory.decodeResource(context.getResources(), R.mipmap.splash_txt);

        paint_nameZh.setAlpha(nameZhAlpha);
        paint_nameEn.setAlpha(nameEnAlpha);
        paint_ad.setAlpha(adAlpha);

        paint_rain = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_rain.setStyle(Paint.Style.STROKE);
        bitmap_rain = BitmapFactory.decodeResource(context.getResources(), R.mipmap.splash_rain);
        pointF_rain0 = new PointF();
        pointF_rain1 = new PointF();
        pointF_rain2 = new PointF();
    }

    private void initRadius(int widthSize,int heightSize){
        if (widthSize<heightSize){
            radius = widthSize/3;
        } else {
            radius = heightSize/3;
        }
    }

    private void initPonitF(int widthSize,int heightSize) {
        if (widthSize<heightSize){
            pointF_rain0.x = widthSize;
            pointF_rain0.y = -widthSize;

            pointF_rain1.x = widthSize;
            pointF_rain2.x = widthSize;

            pointF_rain1.y = -widthSize-0.5f*widthSize;
            pointF_rain2.y = -widthSize+0.5f*widthSize;
        } else {
            pointF_rain0.x = heightSize;
            pointF_rain0.y = -heightSize;

            pointF_rain1.x = heightSize;
            pointF_rain2.x = heightSize;

            pointF_rain1.y = -heightSize-0.5f*heightSize;
            pointF_rain2.y = -heightSize+0.5f*heightSize;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        float varScaleW = (float) mWidth/bitmap_bg.getWidth();
        float varScaleH = (float) mHeight/bitmap_bg.getHeight();
        RectF rectFbg = new RectF(0f
                ,0f
                ,(float) bitmap_bg.getWidth()*varScaleW
                ,(float) bitmap_bg.getHeight()*varScaleH);
        canvas.drawBitmap(bitmap_bg,null,rectFbg,paint_bg);
        canvas.translate(mWidth/2f,mHeight/3f);

        canvas.save();
        canvas.scale(scaleLogo,scaleLogo);
        RectF rectFlogo = new RectF(-(radius*bitmap_logo.getWidth()/bitmap_wai.getWidth()),-(radius*bitmap_logo.getHeight()/bitmap_wai.getHeight()),(radius*bitmap_logo.getWidth()/bitmap_wai.getWidth()),(radius*bitmap_logo.getHeight()/bitmap_wai.getHeight()));
        canvas.drawBitmap(bitmap_logo,null,rectFlogo,paint_logo);
        canvas.restore();

        if (scaleLogo>=1f){
            RectF rectFneiXi = new RectF(-(radius*bitmap_neicu.getWidth()/bitmap_wai.getWidth())-5
                    ,0+5
                    ,(radius*bitmap_neicu.getWidth()/bitmap_wai.getWidth()*45/100)-5
                    ,(radius*bitmap_neicu.getHeight()/bitmap_wai.getHeight())+5);
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
            RectF rectFwai = new RectF(-radius,-radius,radius,radius);
            canvas.drawBitmap(bitmap_wai,null,rectFwai,paint_wai);
            canvas.restore();
        }

        canvas.save();
        RectF rectFnameZh = new RectF(-(bitmap_nameZh.getWidth()/2)
                ,1.3f*radius
                ,(bitmap_nameZh.getWidth()/2)
                ,1.3f*radius+bitmap_nameZh.getHeight());
        canvas.drawBitmap(bitmap_nameZh,null,rectFnameZh,paint_nameZh);

        RectF rectFnameEn = new RectF(5-(bitmap_nameEn.getWidth()/2)
                ,rectFnameZh.bottom+0.5f*bitmap_nameZh.getHeight()
                ,(bitmap_nameEn.getWidth()/2)
                ,rectFnameZh.bottom+0.5f*bitmap_nameZh.getHeight()+bitmap_nameEn.getHeight());
        canvas.drawBitmap(bitmap_nameEn,null,rectFnameEn,paint_nameEn);

        RectF rectFad = new RectF(-(bitmap_ad.getWidth()/2)
                ,radius*2+1.6f*bitmap_nameZh.getHeight()
                ,(bitmap_ad.getWidth()/2)
                ,radius*2+1.6f*bitmap_nameZh.getHeight()+bitmap_nameEn.getHeight());
        canvas.drawBitmap(bitmap_ad,null,rectFad,paint_ad);
        canvas.restore();

        canvas.save();
        canvas.drawBitmap(bitmap_rain,pointF_rain0.x,pointF_rain0.y,paint_rain);
        canvas.drawBitmap(bitmap_rain,pointF_rain1.x,pointF_rain1.y,paint_rain);
        canvas.drawBitmap(bitmap_rain,pointF_rain2.x,pointF_rain2.y,paint_rain);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        initRadius(mWidth,mHeight);
        initPonitF(mWidth,mHeight);

        setMeasuredDimension(mWidth,mHeight);
    }
}
