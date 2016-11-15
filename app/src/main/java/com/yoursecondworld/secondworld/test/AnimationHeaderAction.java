package com.yoursecondworld.secondworld.test;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by cxj on 2016/7/29.
 */
public interface AnimationHeaderAction {

    /**
     * 手指按下的时候
     *
     * @param rv
     */
    void onTouchDown(RecyclerView rv);

    /**
     * @param rv     列表控件
     * @param moveDy 手指移动的总距离,从按下到目前的移动总距离
     */
    void onTouchMoving(RecyclerView rv, float moveDy);

    /**
     * 手指抬起了
     *
     * @param rv
     */
    void onTouchUp(RecyclerView rv);

    /**
     * 完成数据加载
     */
    void completeLoadData();

    /**
     * 加载数据失败
     */
    void loadDataError();

    /**
     * 获取头部的试图
     *
     * @return
     */
    View getHeaderView();

}
