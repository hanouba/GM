package com.yoursecondworld.secondworld.modular.prepareModule.selectPlayer.entity;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by cxj on 2016/7/6.
 */
public class PlayerSpaceItemDecoration extends RecyclerView.ItemDecoration {
    /**
     * 竖直方向的间距
     */
    private int vSpace;

    /**
     * 水平方向的间距
     */
    private int hSpace;

    public PlayerSpaceItemDecoration(int vSpace, int hSpace) {
        this.vSpace = vSpace;
        this.hSpace = hSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //获取到孩子的位置
        int position = parent.getChildAdapterPosition(view);
        if (position % 3 != 0) {
            outRect.left = hSpace;
        }
        if(position >= 3){
            outRect.top = vSpace;
        }


    }
}
