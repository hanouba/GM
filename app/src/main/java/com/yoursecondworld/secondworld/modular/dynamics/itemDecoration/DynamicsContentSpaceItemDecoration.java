package com.yoursecondworld.secondworld.modular.dynamics.itemDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by cxj on 2016/7/6.
 */
public class DynamicsContentSpaceItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 竖直方向的间距
     */
    private int vSpace;


    public DynamicsContentSpaceItemDecoration(int vSpace) {
        this.vSpace = vSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = vSpace;
    }

}
