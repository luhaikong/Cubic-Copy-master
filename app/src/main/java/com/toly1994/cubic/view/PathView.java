package com.toly1994.cubic.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class PathView extends View {

    private Paint paint;
    /**
     * 内存中创建的Canvas
     */
    private Canvas mCanvas;
    /**
     * mCanvas绘制内容在其上
     */
    private Bitmap mBitmap;
    private PorterDuffXfermode xfermode;

    private int radius = 0;
    private int padding = 0;
    private Context cxt;

    private Paint paint0;
    private Paint paint1;
    private Paint paint2;
    private Paint paint3;
    private Paint paint4;

    public PathView(Context context) {
        this(context,null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.cxt = context;
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC);

        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        paint.setColor(Color.parseColor("#FF0000"));

        paint0 = new Paint();
        paint0.setAntiAlias(true);
        paint0.setStyle(Paint.Style.STROKE);
        paint0.setStrokeWidth(20);
        paint0.setColor(cxt.getResources().getColor(android.R.color.holo_green_dark));

        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(20);
        paint1.setColor(cxt.getResources().getColor(android.R.color.holo_blue_dark));

        paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(20);
        paint2.setColor(cxt.getResources().getColor(android.R.color.holo_orange_dark));

        paint3 = new Paint();
        paint3.setAntiAlias(true);
        paint3.setStyle(Paint.Style.STROKE);
        paint3.setStrokeWidth(20);
        paint3.setColor(cxt.getResources().getColor(android.R.color.holo_red_dark));

        paint4 = new Paint();
        paint4.setAntiAlias(true);
        paint4.setStyle(Paint.Style.STROKE);
        paint4.setStrokeWidth(20);
        paint4.setColor(cxt.getResources().getColor(android.R.color.holo_purple));
    }

    /**
     * 画圆
     * @param canvas
     */
    private void drawCir(Canvas canvas){
        Path path = new Path();
        path.addCircle(0,0,radius, Path.Direction.CW);
        path.setFillType(Path.FillType.WINDING);
        canvas.drawPath(path, paint);
    }

    /**
     * 画星
     * @param canvas
     */
    private void drawStar(Canvas canvas){
        canvas.drawRect(-radius,-radius,0,radius,paint);

        // 初始化bitmap
        mBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.translate(radius,radius);

        Path pathStar = new Path();

        Point point1 = new Point();
        point1.x = 0;
        point1.y = -radius;

        Point point2 = new Point();
        point2.x = (int) (radius * Math.cos(Math.toRadians(18)));
        point2.y = (int) (radius * Math.sin(-Math.toRadians(18)));

        Point point3 = new Point();
        point3.x = (int) (radius * Math.cos(Math.toRadians(54)));
        point3.y = (int) (radius * Math.sin(Math.toRadians(54)));

        Point point4 = new Point();
        point4.x = (int) (radius * Math.cos(Math.toRadians(126)));
        point4.y = (int) (radius * Math.sin(Math.toRadians(126)));

        Point point5 = new Point();
        point5.x = (int) (radius * Math.cos(Math.toRadians(198)));
        point5.y = (int) (radius * Math.sin(Math.toRadians(198)));

//        pathStar.moveTo(point1.x,point1.y);
//        pathStar.lineTo(point2.x,point2.y);
//        pathStar.lineTo(point3.x,point3.y);
//        pathStar.lineTo(point4.x,point4.y);
//        pathStar.lineTo(point5.x,point5.y);
//        pathStar.lineTo(0,-radius);
//        canvas.drawPath(pathStar,paint);


        Path pathStar0 = new Path();
        pathStar0.moveTo(point1.x,point1.y);
        pathStar0.lineTo(point3.x,point3.y);
        pathStar0.lineTo(point5.x,point5.y);
        pathStar0.lineTo(point2.x,point2.y);
        pathStar0.lineTo(point4.x,point4.y);
        pathStar0.lineTo(point1.x,point1.y);
        mCanvas.drawPath(pathStar0,paint);

        paint.setXfermode(xfermode);
        paint.setColor(cxt.getResources().getColor(android.R.color.holo_blue_dark));
        canvas.drawBitmap(mBitmap,-radius,-radius,paint);
//        draw5Point(canvas);
    }

    /**
     * 绘制五个点
     * @param canvas
     */
    private void draw5Point(Canvas canvas){
        Path path = new Path();
        canvas.drawPoint(0,0,paint);
        path.moveTo(0,0);
        path.lineTo(0,-radius);
        canvas.drawPath(path,paint);
        canvas.drawPoint(0,-radius,paint0);
        canvas.rotate(360/5);
        path.moveTo(0,0);
        path.lineTo(0,-radius);
        canvas.drawPath(path,paint);
        canvas.drawPoint(0,-radius,paint1);
        canvas.rotate(360/5);
        path.moveTo(0,0);
        path.lineTo(0,-radius);
        canvas.drawPath(path,paint);
        canvas.drawPoint(0,-radius,paint2);
        canvas.rotate(360/5);
        path.moveTo(0,0);
        path.lineTo(0,-radius);
        canvas.drawPath(path,paint);
        canvas.drawPoint(0,-radius,paint3);
        canvas.rotate(360/5);
        path.moveTo(0,0);
        path.lineTo(0,-radius);
        canvas.drawPath(path,paint);
        canvas.drawPoint(0,-radius,paint4);
    }

    /**
     * 绘制半圆
     * @param canvas
     */
    private void drawCirBan(Canvas canvas){
        canvas.save();
        int layerId = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawRect(-radius,-radius,0,radius,paint);
//        canvas.restoreToCount(layerId);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(radius+padding,radius+padding);

//        drawCirBan(canvas);
//        drawCir(canvas);
        drawStar(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int totalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int totalHeight = MeasureSpec.getSize(heightMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        padding = paddingTop;
        if (padding>paddingRight){
            padding = paddingRight;
        }
        if (padding>paddingBottom){
            padding = paddingBottom;
        }
        if (padding>paddingLeft){
            padding = paddingLeft;
        }

        if (totalWidth > totalHeight){
            radius = totalHeight/2-padding;
        } else {
            radius = totalWidth/2-padding;
        }

        setMeasuredDimension(totalWidth,totalHeight);
    }
}
