package com.yoursecondworld.secondworld.modular.statusNotification.fragment.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.modular.statusNotification.enity.ZanListEntity;

import java.util.List;

import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewHolder;

/**
 * Created by cxj on 2016/7/23.
 */
public class ZanFragmentAdapter extends CommonRecyclerViewAdapter<ZanListEntity> {

    /**
     * 构造函数
     *
     * @param context 上下文
     * @param data    显示的数据
     */
    public ZanFragmentAdapter(Context context, List<ZanListEntity> data) {
        super(context, data);
    }

    @Override
    public void convert(CommonRecyclerViewHolder h, ZanListEntity entity, int position) {
        SimpleDraweeView icon = h.getView(R.id.sdv_frag_zan_for_status_notifi_item_icon);
        icon.setImageURI(Uri.parse(entity.getUser_head_image() + Constant.HEADER_SMALL_IMAGE));

        h.setText(R.id.tv_name, entity.getUser_nickname());

        h.setText(R.id.tv_frag_zan_for_status_notifi_item_text_content, "原文:" + entity.getSource_article_content());
    }

    @Override
    public int getLayoutViewId(int viewType) {
        return R.layout.frag_zan_for_status_notifi_item;
    }
}
