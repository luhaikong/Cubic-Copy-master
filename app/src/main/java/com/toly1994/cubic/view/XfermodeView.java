package com.toly1994.cubic.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;

import com.toly1994.cubic.R;

@SuppressLint("AppCompatCustomView")
public class XfermodeView extends ImageView {

    private Paint paint;
    private PorterDuffXfermode porterDuffXfermode;

    private int mWidth;
    private int mHeight;

    private int direct = 0;//0:发出（右），1:接收（左）

    public XfermodeView(Context context) {
        this(context,null);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (null != drawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            canvas.drawBitmap(drawAll(mWidth,mHeight,bitmap),0,0,paint);
        } else {
            super.onDraw(canvas);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (width<height){
            mWidth = width;
            mHeight = width;
        } else {
            mWidth = height;
            mHeight = height;
        }
        setMeasuredDimension(mWidth,mHeight);
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
        invalidate();
    }

    private Bitmap drawAll(int w, int h, Bitmap image){
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.FILL);
        int layer = canvas.saveLayer(0, 0, w, h, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(drawImage(w, h, image),0,0,p);
        p.setXfermode(porterDuffXfermode);
        if (direct==1){
            canvas.drawBitmap(drawShapeLeft1(w, h),0,0,p);
        } else {
            canvas.drawBitmap(drawShapeRight1(w, h),0,0,p);
        }
        canvas.restoreToCount(layer);
        return bitmap;
    }

    private Bitmap drawImage(int w, int h, Bitmap image){
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.FILL);
        RectF rectF = new RectF(0,0,mWidth,mHeight);
        canvas.drawBitmap(image,null,rectF,p);
        return bitmap;
    }

    /**
     * 绘制圆形背景 右
     * @param w
     * @param h
     * @return
     */
    private Bitmap drawShapeRight(int w,int h){
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.FILL);
        p.setColor(getResources().getColor(R.color.chat_item_bg));
        canvas.drawCircle(w/2f,w/2f,w/2f,p);
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        canvas.drawRect(w/2f,w/2f,w,w,p);
        return bitmap;
    }

    /**
     * 绘制圆角矩形背景 右
     * @param w
     * @param h
     * @return
     */
    private Bitmap drawShapeRight1(int w,int h){
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.FILL);
        p.setColor(getResources().getColor(R.color.chat_item_bg));
        canvas.drawRoundRect(0,0,w,h,w/5f,h/5f,p);
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        canvas.drawRect(w/2f,w/2f,w,h,p);
        return bitmap;
    }

    /**
     * 绘制圆形背景 左
     * @param w
     * @param h
     * @return
     */
    private Bitmap drawShapeLeft(int w,int h){
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.FILL);
        p.setColor(getResources().getColor(R.color.chat_item_bg));
        canvas.drawCircle(w/2f,w/2f,w/2f,p);
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        canvas.drawRect(0,w/2f,w/2f,w,p);
        return bitmap;
    }

    /**
     * 绘制圆角矩形背景 左
     * @param w
     * @param h
     * @return
     */
    private Bitmap drawShapeLeft1(int w,int h){
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.FILL);
        p.setColor(getResources().getColor(R.color.chat_item_bg));
        canvas.drawRoundRect(0,0,w,h,w/5f,h/5f,p);
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        canvas.drawRect(0,w/2f,w/2f,h,p);
        return bitmap;
    }
}
