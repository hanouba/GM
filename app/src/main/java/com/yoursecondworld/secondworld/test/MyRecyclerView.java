package com.yoursecondworld.secondworld.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewHolder;

/**
 * Created by cxj on 2016/7/29.
 */
public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(Context context) {
        this(context, null);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOverScrollMode(MyRecyclerView.OVER_SCROLL_NEVER);
        setFadingEdgeLength(0);
    }

    private int scrollAllY;

    private float downY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        System.out.println("onTouchEvent" + e.getRawY());
        int action = e.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN: //按下的时候
                downY = e.getRawY();
                if (animationHeader != null) {
                    animationHeader.onTouchDown(this);
                }
                break;
            case MotionEvent.ACTION_MOVE: //移动的时候
                if (animationHeader != null) {
                    animationHeader.onTouchMoving(this, e.getRawY() - downY);
                }
                break;
            case MotionEvent.ACTION_UP: //抬起的时候
                if (animationHeader != null) {
                    animationHeader.onTouchUp(this);
                }
                break;
        }


        return super.onTouchEvent(e);
    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    public void setAdapter(CommonRecyclerViewAdapter adapter) {
        super.setAdapter(new WrapAdapter(adapter));
    }

    private AnimationHeaderAction animationHeader;

    /**
     * 设置一个动画的头部
     *
     * @param animationHeader
     */
    public void setAnimationHeader(AnimationHeaderAction animationHeader) {
        this.animationHeader = animationHeader;
    }

    /**
     * 在原有的适配器之上再封装一封,用于显示下拉刷新动画的item
     */
    private class WrapAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {

        private CommonRecyclerViewAdapter adapter;

        private int headerViewType = 31 * 11;

        public WrapAdapter(CommonRecyclerViewAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (animationHeader == null || viewType != headerViewType) {
                return adapter.onCreateViewHolder(parent, viewType);
            } else {
                CommonRecyclerViewHolder vh = new CommonRecyclerViewHolder(animationHeader.getHeaderView());
                return vh;
            }
        }

        @Override
        public int getItemViewType(int position) {
            System.out.println("getItemViewType.position = " + position);
            if (position == 0 && animationHeader != null) {
                return headerViewType;
            }
            return adapter.getItemViewType(animationHeader == null ? position : position - 1);
        }

        @Override
        public void onBindViewHolder(CommonRecyclerViewHolder holder, int position) {
            if (position == 0 && animationHeader != null) {
                return;
            }
            adapter.onBindViewHolder(holder, animationHeader == null ? position : position - 1);
        }

        @Override
        public int getItemCount() {
            return animationHeader == null ? adapter.getItemCount() : adapter.getItemCount() + 1;
        }
    }

    private class WrapViewHolder extends CommonRecyclerViewHolder{

        /**
         * 构造函数
         *
         * @param itemView
         */
        public WrapViewHolder(View itemView) {
            super(itemView);
        }

    }

}
