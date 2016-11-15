package com.yoursecondworld.secondworld.modular.postDynamics.fragment.selectEmoji.adapter;

import android.content.Context;
import android.content.Intent;

import com.yoursecondworld.secondworld.R;

import java.util.List;

import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewHolder;

/**
 * Created by cxj on 2016/7/30.
 * 显示表情的适配器
 */
public class SelectEmojiFragmentAdapter extends CommonRecyclerViewAdapter<Integer> {
    /**
     * 构造函数
     *
     * @param context 上下文
     * @param data    显示的数据
     */
    public SelectEmojiFragmentAdapter(Context context, List<Integer> data) {
        super(context, data);
    }

    @Override
    public void convert(CommonRecyclerViewHolder h, Integer entity, int position) {
        h.setImage(R.id.iv, entity);
    }

    @Override
    public int getLayoutViewId(int viewType) {
        return R.layout.frag_select_emoji_for_post_dynamics_item;
    }
}
