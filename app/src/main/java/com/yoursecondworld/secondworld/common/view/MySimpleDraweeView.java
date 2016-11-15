package com.yoursecondworld.secondworld.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by cxj on 2016/9/15.
 */
public class MySimpleDraweeView extends SimpleDraweeView {

    public MySimpleDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public MySimpleDraweeView(Context context) {
        super(context);
    }

    public MySimpleDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //拿到推荐的宽和高
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);

        //高度总是宽度的三分之二
        heightSize = widthSize * 2 / 3;

        setMeasuredDimension(widthSize, heightSize);

    }

}
