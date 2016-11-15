package com.yoursecondworld.secondworld.modular.statusNotification.fragment.itemDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import xiaojinzi.autolayout.utils.AutoUtils;

/**
 * Created by cxj on 2016/7/23.
 */
public class ZanItemDecoration extends RecyclerView.ItemDecoration {

    private int vSpace;

    public ZanItemDecoration() {
        this.vSpace = AutoUtils.getPercentHeightSize(30);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) > 0) {
            outRect.top = vSpace;
        }
    }
}
