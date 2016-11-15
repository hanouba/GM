package com.yoursecondworld.secondworld.modular.postDynamics.widget;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import xiaojinzi.view.RectEntity;

/**
 * Created by cxj on 2016/3/26.
 * 显示任何View的九宫格控件
 * 这个控件将如何测量和排列孩子的逻辑给抽取了出来,针对有些时候需要使用九宫格形式来展示的效果
 * 特别说明:此控件的包裹效果和填充父容器的效果是一样的,因为在本测量方法中并没有处理包裹的形式,也不能处理
 * 针对在listview的条目item中的时候,传入的高度的测量模式为:{@link MeasureSpec#UNSPECIFIED},此时高度就就根本孩子的个数来决定了
 * 因为不同的孩子格式,孩子的排列方式不一样
 */
public class ShowImagesViewForPostDynamics extends ViewGroup {

    public ShowImagesViewForPostDynamics(Context context) {
        this(context, null);
    }

    public ShowImagesViewForPostDynamics(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShowImagesViewForPostDynamics(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 自定义ViewGroup的工具类
     */
    private ViewDragHelper mViewDragHelper;

    /**
     * 上下文对象
     */
    private Context context = null;

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        this.context = context;
        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {

            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                if (child instanceof ImageView) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

        });
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent event)
//    {
//        return mViewDragHelper.shouldInterceptTouchEvent(event);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event)
//    {
//        mViewDragHelper.processTouchEvent(event);
//        return true;
//    }

    /**
     * 用于保存每一个孩子的在父容器的位置
     */
    private List<RectEntity> rectEntityList = new ArrayList<RectEntity>();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取推荐的宽高和计算模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();

        if (childCount > 9) {
            throw new RuntimeException("the chlid count can not > 9");
        }

        //出现在listView的item中,或者是高度包裹的时候
        if (heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST) {
            switch (childCount) {
                case 0:
                    heightSize = 0;
                    break;
                case 1:
                case 2:
                case 3:
                    heightSize = widthSize / 3;
                    break;
                case 4:
                case 5:
                case 6:
                    heightSize = widthSize * 2 / 3;
                    break;
                case 7:
                case 8:
                case 9:
                    heightSize = widthSize;
                    break;
            }
        }

        setMeasuredDimension(widthSize, heightSize);

        int row = 0;

        if (childCount % 3 == 0) {
            row = childCount / 3;
        } else {
            row = (childCount - childCount % 3) / 3 + 1;
        }

        for (int i = 0; i < childCount; i++) {
            getChildAt(i).measure(MeasureSpec.makeMeasureSpec((widthSize - getPaddingLeft() - getPaddingRight() - 2 * horizontalIntervalDistance) / 3, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec((heightSize - getPaddingTop() - getPaddingBottom() - (row - 1) * verticalIntervalDistance) / row, MeasureSpec.EXACTLY));
        }

    }

    /**
     * 安排孩子的位置
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        computeViewsLocation();
        // 循环集合中的各个菜单的位置信息,并让孩子到这个位置上
        for (int i = 0; i < getChildCount(); i++) {
            // 循环中的位置
            RectEntity e = rectEntityList.get(i);
            // 循环中的孩子
            View v = getChildAt(i);
            // 让孩子到指定的位置
            v.layout(e.leftX, e.leftY, e.rightX, e.rightY);
        }
    }


    //========================私有的方法 start===================

    /**
     * 每一行显示三个图片
     */
    private int column = 3;

    /**
     * 图片之间的间隔距离
     */
//    private int intervalDistance = 4;

    /**
     * 水平的间距
     */
    private int horizontalIntervalDistance;

    /**
     * 竖直的间距
     */
    private int verticalIntervalDistance;

    public int getHorizontalIntervalDistance() {
        return horizontalIntervalDistance;
    }

    public void setHorizontalIntervalDistance(int horizontalIntervalDistance) {
        this.horizontalIntervalDistance = horizontalIntervalDistance;
    }

    public int getVerticalIntervalDistance() {
        return verticalIntervalDistance;
    }

    public void setVerticalIntervalDistance(int verticalIntervalDistance) {
        this.verticalIntervalDistance = verticalIntervalDistance;
    }

    /**
     * 自身的宽和高
     */
    private int mWidth;
    private int mHeight;

    /**
     * 用于计算孩子们的位置信息
     */
    private void computeViewsLocation() {
        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }
        if (childCount == rectEntityList.size()) {
            return;
        }
        rectEntityList.clear();

        int left = 0;
        int top = getPaddingTop();

        for (int i = 0; i < childCount; i++) {
            RectEntity r = new RectEntity();

            if (i % 3 == 0) { //如果是每行的开头第一个
                left = getPaddingLeft();
                top += verticalIntervalDistance;
            }

            left += horizontalIntervalDistance;

            r.leftX = left;
            r.leftY = top;
            r.rightX = r.leftX + getChildAt(i).getMeasuredWidth();
            r.rightY = r.leftY + getChildAt(i).getMeasuredHeight();

            if (i % 3 == 2) { //如果是每行的最后一个
                top = r.rightY;
            }

            left = r.rightX;

            rectEntityList.add(r);
        }


    }

    //========================私有的方法 end=====================


    //========================暴露的方法 start=====================

    /**
     * 填充父容器的布局对象
     */
    private LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    public void addClildView(View v) { //fresco SimpleDraweeView
        this.addView(v);
        requestLayout();
    }

    //========================暴露的方法 end=====================

}
