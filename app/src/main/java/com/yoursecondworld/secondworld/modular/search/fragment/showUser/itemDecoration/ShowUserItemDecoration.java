package com.yoursecondworld.secondworld.modular.search.fragment.showUser.itemDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import xiaojinzi.autolayout.utils.AutoUtils;

/**
 * Created by cxj on 2016/7/21.
 * 条目之间的装饰者
 */
public class ShowUserItemDecoration extends RecyclerView.ItemDecoration {

    private int vSpace;

    public ShowUserItemDecoration() {
        vSpace = AutoUtils.getPercentHeightSize(2);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) > 0) {
            outRect.top = vSpace;
        }
    }

}
