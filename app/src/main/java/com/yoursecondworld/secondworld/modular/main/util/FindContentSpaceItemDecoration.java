package com.yoursecondworld.secondworld.modular.main.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yoursecondworld.secondworld.common.Constant;

import xiaojinzi.autolayout.utils.AutoUtils;

/**
 * Created by cxj on 2016/7/6.
 */
public class FindContentSpaceItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 水平方向的间距
     */
    private int vSpace;
    private int hSpace;


    public FindContentSpaceItemDecoration(int vSpace) {
        this.vSpace = vSpace;
        hSpace = AutoUtils.getPercentWidthSize(Constant.DYNAMICS_ITEM_LEFT_AND_RIGHT_MARGIN);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //如果不是第一个
        if (parent.getChildAdapterPosition(view) > 0) {
            outRect.bottom = vSpace;
        }
    }
}
