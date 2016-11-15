package com.yoursecondworld.secondworld.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by cxj on 2016/9/27.
 */
public class VideoContainer extends RelativeLayout {

    public VideoContainer(Context context) {
        super(context);
    }

    public VideoContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //拿到推荐的宽和高
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //高度总是宽度的三分之二
        heightSize = widthSize * 2 / 3;

        setMeasuredDimension(widthSize, heightSize);

        measureChildren(MeasureSpec.makeMeasureSpec(widthSize,MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY));

    }

}
