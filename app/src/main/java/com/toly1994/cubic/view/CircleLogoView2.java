package com.toly1994.cubic.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.toly1994.cubic.R;

public class CircleLogoView2 extends View {

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
    private PointF pointF_rain0_end,pointF_rain1_end,pointF_rain2_end;

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



    public void start(){
        startLogoScale();
        startNeiCuRotate();
        startWaiRotate();
        startNameZhAlpha();
        startNameEnAlpha();
        startAdAlpha();
        startRain();
        startAnimSet();
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
    }

    public CircleLogoView2(Context context) {
        this(context,null);
    }

    public CircleLogoView2(Context context, @Nullable AttributeSet attrs) {
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
        pointF_rain0_end = new PointF();
        pointF_rain1_end = new PointF();
        pointF_rain2_end = new PointF();
    }

    private void initRadius(int widthSize,int heightSize){
        if (widthSize<heightSize){
            radius = widthSize/3;
        } else {
            radius = heightSize/3;
        }
    }

    private void initPointF(int widthSize,int heightSize) {
        if (widthSize<heightSize){
            pointF_rain0.x = widthSize;
            pointF_rain0.y = -widthSize;
            pointF_rain0_end.x = -widthSize/2f;
            pointF_rain0_end.y = widthSize/2f;

            pointF_rain1.x = widthSize;
            pointF_rain1.y = -widthSize-0.5f*widthSize;
            pointF_rain1_end.x = widthSize/2f;
            pointF_rain1_end.y = 100;

            pointF_rain2.x = widthSize;
            pointF_rain2.y = -widthSize+0.5f*widthSize;
            pointF_rain2_end.x = -widthSize/2f;
            pointF_rain2_end.y = 0;
        } else {
            pointF_rain0.x = heightSize;
            pointF_rain0.y = -heightSize;
            pointF_rain0_end.x = -heightSize/2f;
            pointF_rain0_end.y = -heightSize;

            pointF_rain1.x = heightSize;
            pointF_rain1.y = -heightSize-0.5f*heightSize;
            pointF_rain1_end.x = -heightSize/2f;
            pointF_rain1_end.y = 0;

            pointF_rain2.x = heightSize;
            pointF_rain2.y = -heightSize+0.5f*heightSize;
            pointF_rain2_end.x = -heightSize/2f;
            pointF_rain2_end.y = heightSize+0.5f*heightSize;
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
        initPointF(mWidth,mHeight);

        setMeasuredDimension(mWidth,mHeight);
    }



    private void startAnimSet(){
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(anim1).after(anim0);
        animSet.play(anim1).with(anim2);
        animSet.play(anim3).after(anim1);
        animSet.play(anim4).after(anim3);
        animSet.play(anim5).after(anim4);
        animSet.play(animRain0).after(anim5);
        animSet.play(animRain0).with(animRain1);
        animSet.play(animRain1).with(animRain2);

        animSet.start();
    }

    private ValueAnimator anim0;   //Logo动画
    private ValueAnimator anim1;   //内粗动画
    private ValueAnimator anim2;   //外圈动画
    private ValueAnimator anim3;   //nameZh动画
    private ObjectAnimator anim4;   //nameEn动画
    private ValueAnimator anim5;   //ad动画
    private ValueAnimator animRain0;   //雨动画
    private ValueAnimator animRain1;   //雨动画
    private ValueAnimator animRain2;   //雨动画

    private void startLogoScale(){
        anim0 = ObjectAnimator.ofFloat(0.1f,1f);
        anim0.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scaleLogo = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim0.setDuration(2000);
    }

    private void startNeiCuRotate(){
        PropertyValuesHolder pvNeiCu = PropertyValuesHolder.ofFloat("rotate",60f,0f);
        anim1 = ObjectAnimator.ofPropertyValuesHolder(pvNeiCu);
        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degreesNeicu = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim1.setDuration(1000);
    }

    private void startWaiRotate(){
        PropertyValuesHolder pvWai = PropertyValuesHolder.ofFloat("rotate",-60f,0f);
        anim2 = ValueAnimator.ofPropertyValuesHolder(pvWai);
        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degreesWai = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim2.setDuration(1000);
    }

    private void startNameZhAlpha(){
        anim3 = ValueAnimator.ofInt(0,255);
        anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                nameZhAlpha = (int) animation.getAnimatedValue();
                paint_nameZh.setAlpha(nameZhAlpha);
                invalidate();
            }
        });
        anim3.setDuration(1000);
    }

    private void setNameEnAlpha(int a){
        nameEnAlpha = a;
        paint_nameEn.setAlpha(nameEnAlpha);
        invalidate();
    }

    private void startNameEnAlpha(){
        anim4 = ObjectAnimator.ofInt(this,"nameEnAlpha",0,255);
        anim4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setNameEnAlpha((Integer) animation.getAnimatedValue());
            }
        });
        anim4.setDuration(1000);
    }

    private void startAdAlpha(){
        anim5 = ValueAnimator.ofInt(0,255);
        anim5.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                adAlpha = (int) animation.getAnimatedValue();
                paint_ad.setAlpha(adAlpha);
                invalidate();
            }
        });
        anim5.setDuration(1000);
    }

    private void startRain(){
        animRain0 = ValueAnimator.ofObject(new RainEvaluator(),pointF_rain0,pointF_rain0_end);
        animRain0.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pointF_rain0 = (PointF) animation.getAnimatedValue();
                invalidate();
            }
        });
        animRain0.setDuration(3000);

        animRain1 = ValueAnimator.ofObject(new RainEvaluator(),pointF_rain1,pointF_rain1_end);
        animRain1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pointF_rain1 = (PointF) animation.getAnimatedValue();
                invalidate();
            }
        });
        animRain1.setDuration(3000);

        animRain2 = ValueAnimator.ofObject(new RainEvaluator(),pointF_rain2,pointF_rain2_end);
        animRain2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pointF_rain2 = (PointF) animation.getAnimatedValue();
                invalidate();
            }
        });
        animRain2.setDuration(3000);
    }

    private class RainEvaluator implements TypeEvaluator {

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            PointF startPoint = (PointF) startValue;
            PointF endPoint = (PointF) endValue;

            float x = startPoint.x + fraction * (endPoint.x - startPoint.x);
            float y = startPoint.y + fraction * (endPoint.y - startPoint.y);
            PointF pointF = new PointF(x,y);
            return pointF;
        }
    }

}
