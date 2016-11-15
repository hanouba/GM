package com.yoursecondworld.secondworld.modular.userDetail.itemDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yoursecondworld.secondworld.common.Constant;

import xiaojinzi.autolayout.utils.AutoUtils;

/**
 * Created by cxj on 2016/7/6.
 */
public class UserDetailContentSpaceItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 水平方向的间距
     */
    private int vSpace;

    /**
     * 默认的情况是两个头
     */
    public int headerSize = 1;


    public UserDetailContentSpaceItemDecoration(int vSpace) {
        this.vSpace = vSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) > headerSize - 1) {
            outRect.top = vSpace;
        }
    }
}
