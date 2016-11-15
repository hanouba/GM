package com.yoursecondworld.secondworld.modular.main.util;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;

import java.util.List;

/**
 * Created by cxj on 2016/7/6.
 */
public class FindPopularStarSpaceItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 水平方向的间距
     */
    private int hSpace;

    private List<NewUser> popularStars;

    private Context context;

    public FindPopularStarSpaceItemDecoration(Context context, int hSpace, List<NewUser> popularStars) {
        this.popularStars = popularStars;
        this.hSpace = hSpace;
        this.context = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.left = hSpace;
            outRect.right = hSpace;
//        }
    }
}
