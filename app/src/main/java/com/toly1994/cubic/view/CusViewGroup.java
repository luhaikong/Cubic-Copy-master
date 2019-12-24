package com.toly1994.cubic.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CusViewGroup extends ViewGroup {

    //记录所有的子view，并且再按行来进行一个区别记录
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    //记录每行的最大高度
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    public CusViewGroup(Context context) {
        this(context, null);
    }

    public CusViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        init();
    }

    private void init() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取测量模式和测量大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        /**
         * 以下代码主要是针对测量模式是wrap_content时需要动态的计算，
         * 如果是一些具体的大小或者match_parent时根本不不需要写onMearsure
         */
        //整个自定义viewgroup的宽，高的最大值进行记录
        int width = 0;
        int height = 0;
        //判断是否要换行时使用
        int lineWidth = 0;
        int lineHeight = 0;

        //计算子view的个数
        int cCount = getChildCount();
        //for循环计算每个子view在viewgroup中的大小
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            //measureChild这个方法来计算子view的大小和mode，这个viewGroup定义的一个方法，是个很重要的方法
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            //得到child的lp，MarginLayoutParams可以得到Margin数据。
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            //measureChild方法过后，child.getMeasuredWidth()才能计算得到值，不然会为0
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            //判断是否需要换行
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                //需要换行了，记录宽的最大值
                width = Math.max(width, lineWidth);
                //换行后的新的lineWidth等于控件的宽
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                //不需要换行
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //最后一个view
            if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        //不同的mode进行一个简单的判断，viewGroup的真正的大小实际上就是通过这个方法进行设置的
        int mesureWidth = 0;
        int mesureHeight = 0;
        switch (modeWidth) {
            case MeasureSpec.EXACTLY:
                mesureWidth = sizeWidth;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                mesureWidth = width + getPaddingLeft() + getPaddingRight();
                break;
            default:
                break;
        }

        switch (modeHeight) {
            case MeasureSpec.EXACTLY:
                mesureHeight = sizeHeight;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                mesureHeight = height + getPaddingTop() + getPaddingBottom();
                break;
            default:
                break;
        }

        //存储测量好的宽和高
        setMeasuredDimension(mesureWidth,mesureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();

        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;

        // 存储每一行所有的childView
        List<View> lineViews = new ArrayList<View>();

        //计算子view的个数
        int cCount = getChildCount();

        //for循环计算每个子view在viewgroup中的大小
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            // 得到child的lp，MarginLayoutParams可以得到Margin数据。
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            // 如果已经需要换行
            if (childWidth + lp.leftMargin + lp.rightMargin + lineWidth > width) {
                // 记录这一行所有的View以及最大高度
                mLineHeight.add(lineHeight);
                // 将当前行的childView保存，然后开启新的ArrayList保存下一行的childView
                mAllViews.add(lineViews);
                lineWidth = 0;// 重置行宽
                lineViews = new ArrayList<View>();
            }
            /**
             * 如果不需要换行，则累加
             */
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            lineViews.add(child);
        }
        // 记录最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        int left = 0;
        int top = 0;
        // 得到总行数
        int lineNums = mAllViews.size();
        for (int i = 0; i < lineNums; i++) {
            // 每一行的所有的views
            lineViews = mAllViews.get(i);
            // 当前行的最大高度
            lineHeight = mLineHeight.get(i);

            // 遍历当前行所有的View
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                //计算childView的left,top,right,bottom
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                //以上所有的计算都是为了获取这四个参数，来进行子view的布局。
                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.rightMargin + lp.leftMargin;
            }
            left = 0;
            top += lineHeight;
        }
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
