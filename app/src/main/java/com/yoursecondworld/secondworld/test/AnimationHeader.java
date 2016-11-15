package com.yoursecondworld.secondworld.test;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yoursecondworld.secondworld.R;

/**
 * Created by cxj on 2016/7/29.
 * 废弃
 */
public class AnimationHeader implements AnimationHeaderAction {

    private View headerView = null;

    private Context context;

    public AnimationHeader(Context context) {
        this.context = context;
    }

    private int height;

    @Override
    public void onTouchDown(RecyclerView rv) {
        height = 0;
//        height =  headerView.getHeight();
    }

    @Override
    public void onTouchMoving(RecyclerView rv, float moveDy) {
        ViewGroup.LayoutParams lp = headerView.getLayoutParams();
        lp.height = (int) (height + moveDy / 2);
        rv.requestLayout();
    }

    @Override
    public void onTouchUp(final RecyclerView rv) {
        ValueAnimator objectAnimator = ObjectAnimator//
                .ofInt(headerView.getHeight(), 0)//
                .setDuration(500);
        //设置更新数据的监听
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams lp = headerView.getLayoutParams();
                lp.height = value;
                rv.requestLayout();
            }
        });

        objectAnimator.start();
    }

    @Override
    public void completeLoadData() {

    }

    @Override
    public void loadDataError() {

    }

    @Override
    public View getHeaderView() {
        if (headerView == null) {
            headerView = View.inflate(context, R.layout.test, null);
        }
        return headerView;
    }
}
