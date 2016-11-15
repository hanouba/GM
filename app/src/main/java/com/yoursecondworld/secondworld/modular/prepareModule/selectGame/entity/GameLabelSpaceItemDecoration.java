package com.yoursecondworld.secondworld.modular.prepareModule.selectGame.entity;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by cxj on 2016/7/6.
 */
public class GameLabelSpaceItemDecoration extends RecyclerView.ItemDecoration {
    /**
     * 竖直方向的间距
     */
    private int vSpace;

    /**
     * 水平方向的间距
     */
    private int hSpace;

    /**
     * 构造方法
     *
     * @param vSpace
     * @param hSpac
     */
    public GameLabelSpaceItemDecoration(int vSpace, int hSpac) {
        this.vSpace = vSpace;
        this.hSpace = hSpac;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = hSpace;
        outRect.right = hSpace;
    }

}
