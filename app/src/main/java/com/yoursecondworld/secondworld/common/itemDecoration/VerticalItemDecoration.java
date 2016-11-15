package com.yoursecondworld.secondworld.common.itemDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import xiaojinzi.autolayout.utils.AutoUtils;

/**
 * Created by cxj on 2016/7/21.
 * 通用的竖直的条目装饰,为了显示出有一个分割线的效果
 */
public class VerticalItemDecoration extends RecyclerView.ItemDecoration {

    private int vSpace;

    public VerticalItemDecoration() {
        vSpace = AutoUtils.getPercentHeightSize(2);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) > 0) {
            outRect.top = vSpace;
        }
    }

}
