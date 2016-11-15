package com.yoursecondworld.secondworld.common;

import java.util.List;

import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;

/**
 * Created by cxj on 2016/9/12.
 * 通知适配器,针对RecyclerView的适配器
 */
public class AdapterNotify {

    /**
     * 数据刷新的通知
     *
     * @param realData 真正的集合数据
     * @param data     刚加载成功的集合数据
     * @param adapter  适配器
     * @param <T>
     */
    public static <T> void notifyFreshData(List<T> realData, List<T> data, CommonRecyclerViewAdapter adapter) {

        int size = realData.size();

        realData.clear();

        adapter.notifyItemRangeRemoved(0 + adapter.getHeaderCounts(), size);

        realData.addAll(data);

        adapter.notifyItemRangeInserted(0 + adapter.getHeaderCounts(), data.size());

    }

    /**
     * 数据添加的通知
     *
     * @param realData 真正的集合数据
     * @param data     刚加载成功的集合数据
     * @param adapter  适配器
     * @param <T>
     */
    public static <T> void notifyAppendData(List<T> realData, List<T> data, CommonRecyclerViewAdapter adapter) {

        int size = realData.size();

        realData.addAll(data);

        adapter.notifyItemRangeInserted(size + adapter.getHeaderCounts(), data.size());

    }

}
