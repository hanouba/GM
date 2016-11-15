package com.yoursecondworld.secondworld.modular.main.popupWindow.share;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import xiaojinzi.autolayout.utils.AutoUtils;

/**
 * Created by cxj on 2016/7/6.
 */
public class SharePopupWindowSpaceItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 水平方向的间距
     */
    private int hSpace;

    public SharePopupWindowSpaceItemDecoration() {
        //80通过设计图的宽度减去n个条目宽度,再除以（2n）得到
        hSpace = AutoUtils.getPercentWidthSize(80);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = hSpace;
        outRect.right = hSpace;
    }
}
