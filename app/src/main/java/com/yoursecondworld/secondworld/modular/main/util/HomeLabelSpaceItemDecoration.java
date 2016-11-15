package com.yoursecondworld.secondworld.modular.main.util;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import xiaojinzi.autolayout.utils.AutoUtils;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.GameLabel;

import java.util.List;

/**
 * Created by cxj on 2016/7/6.
 */
public class HomeLabelSpaceItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 水平方向的间距
     */
    private int hSpace;

    private List<String> labels;

    private Context context;

    private int frag_hot_dynamics_labels_right_arrow_size;

    public HomeLabelSpaceItemDecoration(Context context, int hSpace, List<String> labels) {
        this.labels = labels;
        this.hSpace = hSpace;
        this.context = context;
        int frag_hot_dynamics_labels_right_arrow_size = context.getResources().getDimensionPixelSize(R.dimen.frag_hot_dynamics_labels_right_arrow_size);
        this.frag_hot_dynamics_labels_right_arrow_size = AutoUtils.getPercentWidthSize(frag_hot_dynamics_labels_right_arrow_size);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = hSpace;
        if (parent.getChildAdapterPosition(view) == labels.size() - 1) { //如果是最后一个
            //右边的距离等于图标的大小,这样子既可以在滑动的时候产生遮盖的效果,
            outRect.right = frag_hot_dynamics_labels_right_arrow_size;
        } else {
            outRect.right = hSpace;
        }
    }

}
