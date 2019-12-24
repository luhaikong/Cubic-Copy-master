package com.toly1994.cubic.view;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

public class CustomPropertyAnimationView extends View {


    private static final float RADIUS = 25; //小球半径
    private CustomPoint currentPointBlue; //蓝色的小球
    private CustomPoint currentPointRed; //红色的小球
    private Paint mPaint; //小球的画笔
    private static final int BOUNDARY = 70; //白色的边界长度
    private ValueAnimator anim; //动画

    private boolean isFirst = true;


    public CustomPropertyAnimationView(Context context) {
        this(context, null);
    }

    public CustomPropertyAnimationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomPropertyAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (currentPointBlue == null) {
            currentPointBlue = new CustomPoint(getWidth() / 2f, getHeight() / 2f);
            currentPointRed = new CustomPoint(getWidth() / 2f, getHeight() / 2f);
            drawCircle(canvas); //画圆
            if (isFirst) {
                initAnimation(); //开始动画
                isFirst = false;
            }
        } else {
            drawCircle(canvas);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int width;
        int height;
        if (mode == MeasureSpec.EXACTLY) { //表示确定的值或者MATCH_PARENT
            width = size;
        } else { //表示WARP_CONTENT
            width = (int) (2 * RADIUS + 2 * BOUNDARY + getPaddingLeft() + getPaddingRight());
        }
        mode = MeasureSpec.getMode(heightMeasureSpec);
        size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            height = size;
        } else { //表示WARP_CONTENT
            height = (int) (4 * RADIUS + getPaddingTop() + getPaddingBottom());
        }

        setMeasuredDimension(width, height);
    }

    //外部调用的地方可以控制动画的开始、暂停与停止
    public void startCustomAnim() {
        if (!anim.isStarted() || anim.isPaused()) {
            anim.start();
        }
    }

    public void stopCustomAnim() {
        if (anim.isStarted()) {
            anim.end();
        }
    }

    public void pauseCustomAnim() {
        if (!anim.isPaused()) {
            anim.pause();
        }
    }

    //画圆
    private void drawCircle(Canvas canvas) {
        float blueX = currentPointBlue.getX();
        float redX = Math.abs(currentPointBlue.getX() - getWidth() / 2f) + getWidth() / 2f;
        currentPointRed.setX(redX + getWidth() / 2f);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(blueX, getHeight() / 2f, RADIUS, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(redX, getHeight() / 2f, RADIUS, mPaint);
    }

    //开始动画
    private void initAnimation() {
        CustomPoint startPoint = new CustomPoint(getWidth() / 2, getHeight() / 2);
        CustomPoint endPoint = new CustomPoint(getWidth() / 2 - 2 * RADIUS, getHeight() / 2);
        anim = ValueAnimator.ofObject(new CustomPointEvaluator(), startPoint, endPoint, startPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPointBlue = (CustomPoint) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.setDuration(600);
        anim.setRepeatCount(Animation.INFINITE);
    }

    private class CustomPointEvaluator implements TypeEvaluator {

        /**
         *
         * @param fraction  系数
         * @param startValue  起始值
         * @param endValue  终点值
         * @return
         */
        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            CustomPoint startPoint = (CustomPoint) startValue;
            CustomPoint endPoint = (CustomPoint) endValue;
            float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
            float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());
            CustomPoint point = new CustomPoint(x, y);
            return point;
        }
    }

    private class CustomPoint{


        private float x;
        private float y;

        public CustomPoint(float x, float y) {
            this.x = x; this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }
}
