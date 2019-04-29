package com.toly1994.cubic.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.toly1994.cubic.analyze.HelpDraw;

public class PictureTestView extends View {
    private Point mCoo = new Point(100, 100);//坐标系
    private Picture mCooPicture;//坐标系canvas元件
    private Picture mGridPicture;//网格canvas元件
    private Paint mPaint;//主画笔

    public PictureTestView(Context context) {
        this(context,null);
    }

    public PictureTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        init();
    }

    private void init() {
        //初始化主画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(5);

        mCooPicture = HelpDraw.getCoo(getContext(), mCoo);
        mGridPicture = HelpDraw.getGrid(getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        HelpDraw.draw(canvas, mGridPicture, mCooPicture);
        canvas.save();

        drawPicture(canvas);
    }

    private void drawPicture(Canvas canvas) {
        canvas.translate(mCoo.x, mCoo.y);
        //创建Picture对象
        Picture picture = new Picture();
        //确定picture产生的Canvas元件的大小，并生成Canvas元件
        Canvas recodingCanvas = picture.beginRecording(canvas.getWidth(), canvas.getHeight());
        //Canvas元件的操作
        recodingCanvas.drawRect(100, 0, 200, 100, mPaint);
        recodingCanvas.drawRect(0, 100, 100, 200, mPaint);
        recodingCanvas.drawRect(200, 100, 300, 200, mPaint);
        //Canvas元件绘制结束
        picture.endRecording();

        canvas.save();
        canvas.drawPicture(picture);//使用picture的Canvas元件

        canvas.translate(0, 300);
        canvas.drawPicture(picture);
        canvas.restore();

        canvas.save();
        canvas.translate(750, 0);
        canvas.drawPicture(picture);
        canvas.restore();

        canvas.translate(750, 300);
        picture.draw(canvas);
        canvas.restore();
    }
}
