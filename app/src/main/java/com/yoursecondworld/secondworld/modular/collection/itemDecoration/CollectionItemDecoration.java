package com.yoursecondworld.secondworld.modular.collection.itemDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import xiaojinzi.autolayout.utils.AutoUtils;

/**
 * Created by cxj on 2016/7/21.
 * 收藏里面的条目之间的装饰者
 */
public class CollectionItemDecoration extends RecyclerView.ItemDecoration {

    private int vSpace;

    public CollectionItemDecoration() {
        vSpace = AutoUtils.getPercentHeightSize(30);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) > 0) {
            outRect.top = vSpace;
        }
    }

}
